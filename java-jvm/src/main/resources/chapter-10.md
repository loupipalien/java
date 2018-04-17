### 早期 (编译期) 优化
从计算机程序出现的第一天起, 对效率的追求就是程序天生的坚定信仰, 这个过程犹如一场没有终点, 永不停歇的 F1 方程式竞赛, 程序员是车手, 技术平台则是赛道上飞驰的赛车

#### 概述
Java 语言的 "编译期" 其实是一段 "不确定" 的操作过程, 因为它可能是指一个前端编译器把 \*.java 文件转变为 \*.class 文件的过程; 也可能是值虚拟机的后端运行期编译器 (JIT 编译器, Just In Time Compiler) 把字节码变成机器码的过程; 还有可能是只使用静态提前编译器 (AOT 编译器, Ahead Of Time Compiler) 直接把 \*.java 文件编译成本地机器代码的过程; 以下是这 3 类编译过程中一些比较有代表性的编译器
- 前端编译器: Sun 的 Javac, Eclipse JDT 中的增量式编译器 (ECJ)
- JIT 编译器: HotSpot VM 的 C1, C2 编译器
- AOT 编译器: GNU Compiler for the Java (GCJ), Excelsior JET

Javac 这类编译器对代码的运行效率几乎没有任何优化措施 (JDK 1.3 之后, Javac 的 -O 优化参数就不再有意义); 虚拟机设计团队把对性能的优化集中到了后端的即时编译器中, 这样可以让哪些不是由 Javac 产生的 Class 文件 (如 JRuby, Groovy 等语言的 Class 文件) 也同样能享受到编译器优化带来的好处; 但是 Javac 做了许多针对 Java 语言编码过程的优化措施来改善程序员的编码风格和提高编码效率; 相当多新生的 Java 语法特性, 都是靠编译器的 "语法糖" 来实现, 而不是依赖虚拟机的底层改进支持; 可以说, Java 中的即时编译器在运行期的优化过程对于程序运行来说更重要, 而前端编译器在编译期的优化过程对于程序编码来说关系更加密切  

#### Javac 编译器
Javac 编译器是由 Java 语言编写的程序

##### Javac 的源码与调试
TODO...

##### 解析和填充符号表
解析步骤由 com.sun.tools.javac.main.JavaComplier.parseFiles() 方法完成, 解析步骤包括了词法分析和语法分析两个过程
- 词法, 语法分析
词法分析是将源代码中的字符流转变为标记 (Token) 集合, 单个字符是程序编写过程中的最小元素, 而标记则是编译过程中的最小元素; 关键字, 变量名, 字面量, 运算符都可以成为标记; 在 Javac 的源码中, 词法分析过程是由 com.sun.tools.javac.parser.Scanner 类来实现的
语法分析是根据 Token 序列构造抽象语法树的过程, 抽象语法树 (Abstract Synatax Tree, AST) 是一种用来描述程序代码语法结构的树形表示方式, 语法树的每一个节点都代表着程序代码中的一个语法结构 (Construct), 例如包, 类型, 修饰符, 运算符, 接口, 返回值至代码注释等都可以是一个语法结构; 在 Javac 的源码中, 语法分析过程是由 com.sun.tools.javac.parser.Parser 类实现的, 这个阶段产生的抽象语法树由 com.sun.tools.javac.tree.JCTree 类表示
- 填充符号表
符号表 (Symbol Table) 是由一组符号地址和符号信息构成的表格, 符号表中所登记的信息在编译的不同阶段都要用到; 在语义分析中, 符号表所登记的内容将用于语义检查和产生中间代码, 在目标代码生成阶段, 当对符号名进行地址分配时, 符号表是地址分配的依据; 在 Javac 源代码中, 由 com.sun.tools.javac.main.JavaComplier.enterTree() 方法触发, 填充符号表的过程由 com.sun.tools.javac.comp.Enter 类实现, 此过程的出口是一个待处理列表 (To Do List), 包含了每一个编译单元的抽象语法树的顶级节点以及 package-info.java (如果存在的话) 的顶级节点

##### 注解处理器
JDK 1.5 之后, Java 语言提供了对注解 (Annotation) 的支持, 这些注解与普通的 Java 代码一样, 是在运行期间发挥作用的; 在 JDK 1.6 中实现了 JSR-269 规范, 提供了一组插入式注解处理器的标准 API 在编译期间对注解进行处理, 可以把它看作是一组编译器的插件, 在这些插件里面, 可以读取, 修改, 添加抽象语法树中的任意元素; 如果这些插件在处理注解期间对语法树进行了修改, 编译器将回到解析及填充符号表的过程重新处理, 直到所有插入式注解处理器都没有再对语法树进行修改为止, 每一次循环称为一个 Round  
在 Javac 源码中, 插入式注解处理器的初始化过程是在  com.sun.tools.javac.main.JavaComplier.initProccessAnnotation() 方法中完成的, 而它的执行过程则是在 proccessAnnotation() 方法中完成的, 这个方法判断是否还有新的注解处理器需要执行, 如果有的话, 通过 com.sun.tools.javac.processing.JavacProcessingEnvironment 类的 doProcessing() 方法生成一个 JavaComplier 对象对编译的后续步骤处理

##### 语义分析与字节码生成
语法分析之后, 编译器获得了程序代码的抽象语法树表示, 语法树能表示一个结构正确的源程序的抽象, 但无法保证源程序是符合逻辑的; 而语义分析的主要任务是对结构上正确的源程序进行上下文有关性质的审查, 如进行类型审查等
- 标注检查
Javac 的编译过程中, 语义分析过程分为标注检查以及数据及控制流分析两个步骤, 分别由 com.sun.tools.javac.main.JavaComplier.attribute() 和 com.sun.tools.javac.main.JavaComplier.flow() 方法完成; 标注检查步骤检查的内容包括变量使用前是否已被声明, 变量与赋值之间的数据类型是否能够匹配等; 标注检查步骤在 Javac 源码中的实现类是 com.sun.tools.javac.comp.Attr 和 com.sun.tools.javac.comp.Check 类
- 数据及控制流分析
数据及控制流分析是对程序上下文逻辑更进一步的验证, 它可以检查出程序局部变量在使用前是否赋值, 方法的每条路径是否都有返回值, 是否所有的受查异常都被正确处理了等问题; 编译时期的数据及控制流分析与类加载时的数据及控制流分析的目的基本上是一致的, 但是校验范围有所区别, 有些校验项只有在编译期或运行期才能进行; 在 Javac 的源码中, 数据及控制流分析的入口是 com.sun.tools.javac.main.JavaComplier.flow(), 实现是 com.sun.tools.javac.comp.Flow 类
```
public void foo(final int arg) {
    final int var = 0;
    // ...
}

public void foo(int arg) {
    int var = 0;
    // ...
}

/*
局部变量与字段 (实例变量, 类变量) 是有区别的, 它在常量池中没有 CONSTANT_Fieldref_info 的符号引用, 自然就没有访问标志的信息, 甚至可能连名称都不会保留下来 (取决于编译时的选项), 自然在 Class 文件中不可能知道局部变量是不是声明为 final 了; 因此, 将局部变量声明为 final, 对运行期是没有影响的, 变量的不变性仅仅由编译器在编译期间保障
*/
```
- 解语法糖
语法糖 (Syntactic Sugar), 是由英国计算机科学家皮得.约翰.兰达 (Peter J.Landin) 发明的一个术语, 指在计算机语言中添加的某种语法, 这种语法对语言的功能并没有影响, 但更方便程序员使用; 通常来说, 使用语法糖能够增加程序的可读性, 从而减少程序代码出错的机会; 在 Javac 的源码中, 解语法糖的过程由 com.sun.tools.javac.main.JavaComplier.desugar() 触发, 在 com.sun.tools.javac.comp.TransTypes 和 com.sun.tools.javac.comp.Lower 类中完成

##### 字节码生成
