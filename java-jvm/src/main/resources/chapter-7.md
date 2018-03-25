### 虚拟机类加载机制
代码编译的结果从本地机器码转变为字节码, 是存储格式发展的一小步, 却是编程语言发展的一大步

#### 概述
虚拟机把描述类的数据从 Class 文件加载到内存, 并对数据进行校验, 转换解析和初始化, 最终形成可以被虚拟机直接使用的 Java 类型, 这就是虚拟机的类加载机制  
与那些在编译时需要进行连接工作的语言不同, Java 语言里的类型加载, 连接, 初始化过程都是在程序运行期间完成的, 这种策略虽然会令类加载时稍微增加一些性能开销, 但是会为 Java 应用程序提供高度的灵活性, Java 天生可以动态扩展的语言特性就是依赖运行期动态加载和动态连接这个特点实现的  

#### 类加载的时机
类从被加载到虚拟机内存中, 到卸载出内存为止, 它的整个生命周期包括: 加载 (Loading), 验证 (Verification), 准备 (Preparation), 解析 (Resolution), 初始化 (Initialization), 使用 (Using), 卸载 (Unloading) 7 个阶段; 其中验证, 准备, 解析 3 个部分统称为连接 (Linking)  
加载, 验证, 准备, 初始化和卸载这 5 个阶段的顺序是确定的, 类的加载过程必须按照这种顺序按部就班的开始, 而解析阶段则不一定: 它在某些情况下可以在初始化阶段之后再开始, 这是为了支持 Java 语言的运行时绑定 (也称为动态绑定或晚期绑定); 什么情况下需要开始类加载过程的第一个阶段: 加载? Java 虚拟机规范中并没有进行强制约束, 这由虚拟机的具体实现来自由把握; 但对于初始化阶段, 虚拟机规范则是严格规定了**有且只有** 5 种情况必须立即对类进行 "初始化" (加载, 验证, 准备自然要在此之前进行)  
- 遇到 new, getstatic, putstatic, invokestatic 这 4 条字节码指令是, 如果类没有进行过初始化, 则需要先触发初始化; 生成这 4 条指令的最常见的 Java 代码场景是: 使用 new 关键字实例化对象时, 读取或设置一个类的静态字段 (被 final 修饰, 已在编译器把结果放入常量池的静态字段除外) 时, 以及调用一个类的静态方法时
- 使用 java.lang.reflect 包的方法对类进行反射调用的时候, 如果类没有进行过初始化则需要先触发初始化
- 当初始化一个类时, 如果发现其父类还没有进行过初始化, 则需要先触发其父类的初始化
- 当虚拟机启动时, 用户需要指定一个执行的主类 (包含 main 方法的那个类), 虚拟机会先初始化这个主类
- 当使用 JDK 1.7 的动态语言支持时, 如果一个 java.lang.invoke.MethodHandle 实例最后解析结果 REF_getStatic, REF_putStatic, REF_invokeStatic 的方法句柄, 并且这个方法句柄所对应的类还没有进行过初始化, 则需要先触发其初始化

对于以上 5 种会触发类进行初始化的行为称为对一个类进行主动引导; 除此之外, 所有引用类的方式都不会触发初始化, 称为被动引用