### 虚拟机字节码执行引擎
代码编译的结果从本地机器码转变为字节码, 是存储格式发展的一小步, 却是编程语言发展的一大步

#### 概述
执行引擎是 Java 虚拟机最核心的组成部分之一, 虚拟机是一个相对于物理机的概念, 这两种机器都有代码执行能力, 其区别是物理机的执行引擎是直接建立在处理器, 硬件, 指令集和操作系统层面上的, 而虚拟机的执行引擎则是自己实现的, 因此可以自行制定指令集与执行引擎的结构体系, 并且能够执行那些不被硬件直接支持的指令集格式  
在 Java 虚拟机规范中制定的了字节码执行引擎的概念模型, 在不同的虚拟机实现里, 执行引擎在执行 Java 代码的时候可能会有解释执行 (解释器执行) 和编译执行 (通过即使编译器产生本地代码执行)  两种选择; 但所有的 Java 虚拟机的执行引擎都是一致的: 输入的是字节码文件, 处理过程是字节码解析的等效过程, 输出的是执行结果

#### 执行时栈帧结构
栈帧 (Stack Frame) 是用于支持虚拟机进行方法调用和方法执行的数据结构, 它是虚拟机运行时数据区中的虚拟机栈 (Virtual Machine Stack) 的栈元素; 栈帧存储了方法的局部变量表, 操作数栈, 动态连接和方法返回地址等信息; 每一个方法从调用开始至执行完成的过程, 都对应着一个栈帧在虚拟机栈中从入栈到出栈的过程  
每一个栈帧都包括了局部变量表, 操作数栈, 动态连接, 方法返回地址和一些额外的附加信息; 在编译程序代码时, 栈帧中需要多大的局部变量表, 多深的操作数栈都已经完全确定了, 并且写入到方法表的 Code 属性中, 因此一个栈帧需要分配多少内存, 不会受到程序运行期变量数据的影响, 而仅仅取决于具体的虚拟机实现  
一个线程中的方法调用链可能会很长, 很多方法都同时处于执行状态; 对于执行引擎来说, 在活动线程中, 只有位于栈顶的栈帧才是有效的, 称为当前栈帧, 与这个栈帧相关联的方法称为当前方法; 执行引擎运行的所有字节码指令都只针对当前栈帧进行操作

#####局部变量表  
局部变量表 (Local Variable Table) 是一组变量值存储空间, 用于存放方法参数和方法内部定义的局部变量; 在 Java 程序编译为 Class 文件时就在方法的 Code 属性的 max_locals 数据项中确定了该方法所需要分配的局部变量表的最大容量  
局部变量表的容量以变量槽 (Variable Slot) 为最小单位, 虚拟机规范中并没有明确指明一个 Slot 应占用的内存空间大小, 只是很有导向性地说到每个 Slot 都应该能存放一个 boolean, byte, char, short, int, float, reference 或 returnAddress 类型的数据; 这 8 种数据类型都可以使用 32 位或更小的物理内存来存放, 但这种描述与明确指出 "每个 Slot 占用 32 位长度的内存空间" 是有一定差别的  
所说的 8 种数据类型前 6 种按照 Java 语言种对应数据类型的概念去理解即可, 第 7 种 reference 类型表示对一个对象实例的引用, 虚拟机规范既没有说明它的长度, 也没有明确指出这种引用应有怎样的结构; 但一般来说, 虚拟机实现至少都应当能通过这个引用做到两点: 一是从此引用中直接或间接的查找到对象在 Java 堆中的数据存放的起始地址索引, 二是此引用中直接或间接的查找到对象所属数据类型在方法区中的存储的类型信息, 否则无法实现 Java 语言规范定义的语法约束; 第 8 种即 returnAddress 类型已经很少见了, 它是位字节指令 jsr, jsr_w 和 ret 服务的, 指向了一条字节码指令的地址, 很古老的 Java 虚拟机曾经使用这几条指令来实现异常处理, 现在已经由异常表代替  
对于 64 位的数据类型, 虚拟机会以高位对齐的方式位其分配两个连续的 Slot 空间, Java 语言中明确的 (reference 类型则可能是 32 位也可能是 64 位) 64 位数据类型只有 long 和 duoble 两种; 这里把 long 和 double 数据类型分割存储的做法与 "long 和 double 的非原子性协定" 中把一次 long 和 double 数据类型读写分割成两次 32 位读写的做法类似; 由于局部变量表建立在线程的堆栈上, 是线程私有的数据, 无论读写两个连续的 Slot 是否位原子操作, 都不会引起数据安全问题  
在方法执行时, 虚拟机是使用局部变量表完成参数值到参数变量列表的传递过程, 如果执行的是实例方法 (非 static 的方法), 那局部变量表中第 0 位索引的 Slot 默认是用于传递方法所属实例的引用, 在方法中可以通过关键字 "this" 来访问到这个隐含的参数, 其余参数则按照参数表顺序排列, 参数表分配完毕后, 再根据方法体内部定义的变量顺序和作用域分配其余 Slot  
为了尽可能节省栈帧空间, 局部变量表中的 Slot 是可以重用的, 方法体中定义的变量, 其作用域并不一定会覆盖整个方法体, 如果当前字节码 PC 计数器的值已经超出了某个变量的作用域, 那这个变量对应的 Slot 就可以交给其他变量使用; 这样的设计 除了节省栈帧空间以外, 还伴随着一些额外的副作用; 例如, 在某些情况下, Slot 复用会直接影响到系统的垃圾收集行为
```
public static void main(String[] args) {
    byte[] placeHolder = new byte[64 * 1024 * 1024];
    System.gc();
}

// GC 信息如下
[GC (System.gc())  68865K->66288K(125952K), 0.0007677 secs]
[Full GC (System.gc())  66288K->66222K(125952K), 0.0178481 secs]
```
执行 System.gc() 时, 变量 placeHolder 还在作用域内, 虚拟机自然是不会回收 placeHolder 的内存的; 继续把代码修改如下
```
public static void main(String[] args) {
    {
        byte[] placeHolder = new byte[64 * 1024 * 1024];
    }
    System.gc();
}

// GC 信息如下
[GC (System.gc())  68865K->66320K(125952K), 0.0011311 secs]
[Full GC (System.gc())  66320K->66222K(125952K), 0.0045486 secs]
```
placeHolder 作用域被限制在花括号内, 从代码逻辑上讲, 在执行 System.gc() 时, placeHolder 已经不能再被访问了, 当内存仍然还没有被回收; 继续修改代码如下
```
public static void main(String[] args) {
    {
        byte[] placeHolder = new byte[64 * 1024 * 1024];
    }
    int a = 0;
    System.gc();
}

// GC 信息如下
[GC (System.gc())  68865K->66320K(125952K), 0.0011931 secs]
[Full GC (System.gc())  66320K->686K(125952K), 0.0045929 secs]
```
这个修改看起来很莫名奇妙, 但是运行发现 GC 时 placeHolder 的内存真正的被回收了  
placeHolder 能被回收的根本原因是: 局部变量表中的 Slot 是否还存有关于 placeHolder 数据对象的引用; 第一次修改中, 代码虽然已经离开了 placeHolder 的作用域, 但在此之后, 没有任何对局部变量表的读写操作, placeHolder 原本所占用的 Slot 还没有被其他变量所复用, 所以作为 GC Root 一部分的局部变量表仍然保持着对它的关联; 这种关联没有即时被打断在大部分情况下影响都很轻微  
关于局部变量表, 还有一点可能会对实际开发产生影响, 就是局部变量不像前面介绍的类变量那样存在 "准备阶段", 类变量有两次赋初始值的过程, 因此在初始化阶段没有为类变量赋值, 类变量仍然具有一个确定的初始值; 但局部变量就不一样, 如果一个局部变量定义了但没有赋初始值是不能使用的

##### 操作数栈
操作数栈 (Operand Stack) 也常称为操作栈, 是一个后入先出 (Last In First Out, LIFO) 栈; 同局部变量表一样, 操作数栈的最大深度也在编译的时候写入到 Code 属性的 max_stacks 数据项中; 操作数栈的每一个元素可以是任意的 Java 数据类型, 包括 long 和 double; 32 位数据类型占栈容量为 1, 64 位数据类型所占栈的容量为 2; 在方法执行的任何时候, 操作数栈的深度都不会超过 max_stacks数据项中设定的最大值
当一个方法刚刚开始执行的时候, 着方法的操作数栈是空的, 在方法的执行过程中, 会有各种字节码指令往操作数栈中写入和提取内容, 也就是入栈/出栈操作; 操作数栈中元素的数据类型必须与字节码指令的序列严格匹配, 在编译程序代码时, 编译器要严格保证这一点, 在类校验阶段的数据流分析中还要再次验证  
在概念模型中, 两个栈帧作为虚拟机栈的元素, 是完全互相独立的; 但在大多数虚拟机的实现里都会做一些优处理, 令两个栈帧出现一部分重叠; 让下面栈帧的部分操作数栈与上面栈帧的部分局部变量表重叠在一起, 这样在进行方法调用时就可以共用一部分数据, 无须进行额外的参数赋值传递; Java 虚拟机的解释执行引擎称为 "基于栈的执行引擎", 其中所指的 "栈" 就是操作数栈

##### 动态连接
每个栈帧都包含一个指向运行时常量池中该栈帧所属方法的引用, 持有这个引用是为了支持方法调用过程中的动态连接 (Dynamic Linking); Class 文记的常量池中存有大量的符号引用, 字节码中的方法调用指令就以常量池中指向方法的符号引用作为参数; 这些符号引用一部分会在类加载阶段或第一次使用的时候就转化为直接引用, 这种转化称为静态连接; 另外一部分将在每一次运行期间转化为直接引用, 这部分称为动态连接

##### 方法返回地址
当一个方法开始执行后, 只有两种方式可以退出这个方法
- 执行引擎遇到任意一个方法返回的字节码指令, 这个时候可能会有返回值传递给上层的方法调用者 (调用当前方法的方法称为调用者), 是否有返回值和返回值的类型将根据遇到何种方法返回指令来决定, 这种退出方法的方式称为正常完成出口 (Normal Method Invocation Completion)
- 在方法执行过程中遇到了异常, 并且这个异常没有在方法体内得到处理, 无论是 Java 虚拟机内存产生的异常, 还是代码中使用 athrow 字节码指令产生的异常, 只要在本方法的异常表中没有搜索到匹配的异常处理器, 就会导致方法退出, 这种退出方法的方式称为异常完成出口 (Abrupt Method Invocation Completion); 一个方法使用异常完成出口的方式, 是不会给它的上层调用者产生任何返回值的

##### 附加信息
虚拟机规范允许具体的虚拟机实现增加一些规范中没有描述的信息到栈帧中, 例如调试相关的信息, 这部分信息完全取决于具体的虚拟机实现

#### 方法调用
方法调用并不等同于方法执行, 方法调用阶段唯一的任务就是确定被调用方法的版本 (即调用哪一个方法), 暂时还不涉及方法内部的具体运行过程; Class 文件的编译过程中不包含传统编译中的连接步骤, 一切方法调用在 Class 文件里存储的都只是符号引用, 而不是方法在实际运行时内存布局的入口地址 (相当于直接引用); 这个特性给 Java 带来了更强大的动态扩展能力, 但也使 Java 方法调用过程变得相对复杂起来, 需要在类加载期间, 甚至到运行期间才能确定目标方法的直接引用

##### 解析
所有方法调用中的目标方法在 Class 文件里都是一个常量池中的符号引用, 在类加载的解析阶段, 会将其中的一部分符号引用转化为直接引用, 这种解析能成立的前提是: 方法在程序真正运行之前就有一个可确定的调用版本, 并且这个方法的调用版本在运行期是不可改变的; 换句话说, 调用目标在程序代码写好, 编译器进行编译时就必须确定下来, 这类方法的调用称为解析 (Resolution ) 调用
在 Java 语言中符合 "编译期可知, 运行期不可变" 要求的方法, 主要包括静态方法和私有方法两类, 前者与类型直接关联, 后者在外部不可访问, 这两种方法各自的特点决定了它们不可能通过继承或别的方式重写其他版本, 因此它们都合适在类加载阶段进行解析; 与之对应的是, 在 Java 虚拟机里提供了 5 条方法调用字节码指令, 分别如下
- invokestatic: 调用静态方法
- invokespecial: 调用实例构造器 <init> 方法, 私有方法和父类方法
- invokevirtual: 调用所有的虚方法
- invokeinterface: 调用接口方法, 会在运行时再确定一个实现此接口的对象
- invokedynamic: 先在运行时动态解析出调用点限定符所引用的方法, 然后再执行该方法, 在此之前的 4 条调用指令,　分派逻辑是固化在 Java 虚拟机内部的, 而 invokedynamic 指令的分派逻辑是由用户所设定的引导方法决定的

只要能被 invokestatic 和 invokespecial 指令调用的方法, 都可以在解析阶段中确定唯一的调用版本, 符合这个条件的有静态分方法, 私有方法, 实例构造器, 父类方法 4 类, 它们在类加载的时候就会把符号引用解析为该方法的直接引用; 这些方法可以称为非虚方法, 与之相对的其他方法称为虚方法 (除去 final 方法)  
Java 中的非虚方法除了使用 invokestatic, invokespecial 调用的方法之外, 还有一种就是被 final 修饰的方法; 虽然 final 方法是使用 invokevirtual 指令来调用的, 但是由于它无法被覆盖, 没有其他版本, 所以也无须对方法接收者进行多态选择, 又或者说多态的选择结果肯定是唯一的, 在 Java 语言规范中明确说明了 final 方法是一种非虚方法  
解析调用一定是个静态的过程, 在编译期间就完全确定, 在类装载的解析阶段就会把涉及的符号引用全部转变为可确定的直接引用, 不会延迟到运行期再去完成; 而分派 (Dispatch) 调用则可能是静态的也可能是动态的, 根据分派依据的 [宗量] 数可分为单分派和多分派; 因此这两类分派方式的两两组合就构成了静态单分派, 静态多分派, 动态单分配, 动态多分派 4 中分派组合情况

##### 分派
Java 是一门面向对象的程序语言, 因为 Java 具备面向对象的三个基本特征: 继承, 封装, 多态; 分派调用过程将会解释多态性的一些最基本的体现, 如重写和重载在 Java 虚拟机中是如何实现的, Java 虚拟机如何确定正确的目标方法  

###### 静态分派
```
public class StaticDispatch {

    static abstract class Human {}

    static class Man extends Human {}

    static class Woman extends Human {}

    public void sayHello (Human guy) {
        System.out.println("Hello, guy!");
    }

    public void sayHello (Man guy) {
        System.out.println("Hello, gentleman!");
    }

    public void sayHello (Woman guy) {
        System.out.println("Hello, lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sd = new StaticDispatch();
        sd.sayHello(man);
        sd.sayHello(woman);
    }

}
```
在 `Human man = new Man()` 的代码中, 我们把 Human 的变量称为静态类型 (Static Type), 或者叫做外观类型 (Apprent Type), 后面的 Man 则称为变量的实际类型 (Actual Type); 静态类和实际类型在程序中都会发生一些变化, 区别是静态类型的变化仅仅在使用时发生, 变量本身的静态类型不会被改变, 并且最终的静态类型是在编译期可知的; 而实际类型变化的结果在运行期才可确定, 编译器在编译程序的时候并不知道一个对象的实际类型是什么
```
// 实际类型变化, 在运行期才可知
Human man = new Man();
Human woman = new Woman();
// 静态类型变化
sd.sayHello((Man) man);
sd.sayHello((Woman) woman);
```
main() 中两次 sayHello() 方法调用, 在方法接收者已经确定的情况下, 使用哪个重载版本就完全取决于传入参数的数量和数据类型; 代码中可以定义了两个静态类型相同但是实际类型不同的变量, 但虚拟机 (准确的应该说编译器) 在重载时是通过参数静态类型而不是实际类型作为判定依据的; 并且静态类型是编译器可知的, Javac 编译器会根据参数的静态类型决定使用哪个重载版本, 所以选择了 sayHello(Human) 作为调用目标, 并把这个方法的符号引用写到 main() 方法里的两条 invokevirtual 指令的参数中  
所有依赖静态类型来定位方法执行版本的分派动作称为静态分派; 静态分派的典型应用是方法重载, 静态分派发生在编译阶段, 因此确定静态分派的动作实际上不是由虚拟机来执行的; 另外, 编译器虽然能确定出方法的重载版本, 但在很多情况下这个重载的版本并不是 "唯一的", 往往只能确定一个 "更合适的" 版本; 产生这种模糊结论的主要原因是字面量不需要定义, 所以字面量没有显式的静态类型, 它的静态类型只能通过语言上的规则去理解和推断
```
public class OverLoad {

    public static void sayHello(Object obj) {
        System.out.println("Hello, Object!");
    }

    public static void sayHello(int arg) {
        System.out.println("Hello, int!");
    }

    public static void sayHello(long arg) {
        System.out.println("Hello, long!");
    }

    public static void sayHello(Character arg) {
        System.out.println("Hello, Character!");
    }

    public static void sayHello(char arg) {
        System.out.println("Hello, char!");
    }

    /**
     * 基本类型和包装类型的数组参数会冲突
     * @param arg
     */
    public static void sayHello(char... arg) {
        System.out.println("Hello, char...!");
    }

    /**
     * Character 的同级接口会冲突
     * @param arg
     */
    public static void sayHello(Serializable arg) {
        System.out.println("Hello, Serializable!");
    }

    public static void main(String[] args) {
        sayHello('a');
    }

}
```
重载方法匹配优先级为: char -> int -> long -> [float -> double ->] Character -> Serializable|Comparable<Character> -> Object -> char...|Character...   
首先 'a' 是一个 char 类型, 其次 'a' 也可以代表数字 97, 所以也可以发生自动类型转换, 去匹配 int 等参数, 但是不能自动类型转换为 boolean, byte 和 short 等不兼容的转换; 当去掉所有基本类型参数的方法, 'a' 会发生自动装箱, 匹配 Character 参数的方法; 继续匹配实现接口的的方法, 同级的实现接口的优先级是相同的; 继续则是继承父类, 如果有多层父类, 则优先级是重下往上优先级递减的, 即使传入的参数值是 null 时, 这个规则仍然适用; 再继续则是 'a' 基本类型数组和包装类型数组, 这两个优先级是相同的  
以上代码演示了编译期间选择静态分派目标的过程, 这个过程也是 Java 语言实现方法重载的本质; 演示代码属于极端的例子, 除了用做面试题为难求职者以外, 在实际工作中几乎不可能有实际用途, 大部分这样极端的重载研究都可算是真正的 "关于茴香豆的茴有几种写法的研究"; 另外, 解析和分派这两者之间的关系并不是二选一的排他关系, 它们是在不同的层次上去筛选确定目标方法的过程; 静态方法会在类记载期就会进行解析, 而静态方法显然也使可以拥有重载版本的, 选择重载版本的过程也是通过静态分派完成的  

###### 动态分派
```
public class DynamicDispatch {

    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Man Say Hello!");
        }
    }

    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Woman Say Hello!");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
        man = new Woman();
        man.sayHello();
    }

}
```
动态分派和多态性的另外一个体现 --- 重写有着很密切的关联; 以上方法调用显然不能再根据静态类型来确定, javap 字节码的找到方法调用的 invokevirtual 指令, 原因就需要从 invokevirtual 指令的多态查找过程说起; invokevirtual 指令的运行时解析过程大致分为以下几个步骤
- 找到操作数栈顶的第一个元素所指向的对象的实际类型, 记作 C
- 如果在类型 C 中找到与常量中描述符和简单名称都相符的方法, 则进行访问权限校验, 如果通过则返回这个方法的直接引用, 查找过程结束; 如果不通过, 则返回 java.lang.IllegalAccessError 异常
- 否则, 按照继承关系从下往上依次对 C 的各个父类进行第 2 步的搜索和验证过程
- 如果始终没有找到合适的方法, 则抛出 java.lang.IllegalAccessError 异常

由于 invokevirtual 指令执行的第一步就是在运行期确定接受者的实际类型, 所以两次调用中的 invokevirtual 指令把常量池中的类方法引用解析到了不同的直接引用上, 这个过程就是 Java 语言中方法重写的本质; 这种在运行期根据实际类型确定方法执行版本的分派过程称为动态分派

###### 单分派与多分派
方法的接收者和方法的参数统称为方法的宗量, 根据分派基于多少种宗量, 可以将分派划分为单分派和多分派两种; 单分派是根据一个宗量对目标方法进行选择, 多分派则是根据多于一个宗量对目标方法进行选择
```
public class Dispatch {

    static class QQ {}

    static class _360 {}

    public static class Father {

        public void hardChoice(QQ arg) {
            System.out.println("Father choose qq!");
        }

        public void hardChoice(_360 arg) {
            System.out.println("Father choose 360!");
        }
    }

    public static class Son extends Father {

        @Override
        public void hardChoice(QQ arg) {
            System.out.println("Father choose qq!");
        }

        @Override
        public void hardChoice(_360 arg) {
            System.out.println("Father choose 360!");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }

}
```
编译阶段编译器的选择过程, 也就是静态分派的过程; 这时选择目标方法的依据有两点: 一是静态类型是 Father 和 Son, 二是方法参数是 QQ 还是 360; 这次选择结果的最终产物是产生了两条 invokevirtual 指令, 两条指令的参数分别为常量池种指向 Father.hardChoice(360) 和 Father.hardChoice(QQ) 方法的符号引用; 因为是根据两个宗量进行选择, 所以 Java 语言的静态分派属于多分派类型  
运行阶段虚拟机的选择, 也就是动态分派的过程; 在执行 son.hardChoice(new QQ()) 代码时, 更准确的说是执行对应的 invokevirtual 指令时; 由于编译期已经决定了目标方法的签名必须为 hardChoice(QQ), 虚拟机此时不会关心传递过来的参数到底是什么, 因为这时参数的静态类型和实际类型对方法的选择都不会构成任何影响, 唯一可以影响虚拟机选择的因素只有此方法的接收者的实际类型是 Father 还是 Son; 因为只有一个宗量作为选择依据, 所以 Java 语言的动态分派属于单分派类型  
所以可以总结为: Java 语言是一门静态多分派, 动态单分派的语言

###### 虚拟机动态分派的实现
