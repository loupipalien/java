### Java 内存模型与线程
并发处理的广泛应用是使得 Amdahl 定律代替摩尔定律成为计算机性能发展源动力的根本原因, 也是人类 "压榨" 计算机运行能力的最有力的武器

#### 概述
在软件业发展初期, 程序编写都是以算法为主的, 程序员会把数据和过程分别作为独立的部分来考虑, 数据代表问题空间中的客体, 程序代码则用于处理这些数据, 这种思维方式直接站在计算机的角度去抽象和解决问题, 称为面向过程的编程思想; 与此相对的是, 面向对象的编程思想是站在现实世界的角度去抽象和解决问题, 它把数据和行为都看作是对象的一部分, 这样能以符合现实世界的思维方式来编写和组织程序

#### 线程安全
<<Java concurrency in Practice>> 的作者 Brian Goetz 对线程安全有一个比较恰当的定义: 当多个线程访问一个对象时, 如果不用考虑这些线程在运行时环境下的调度和交替执行, 也不需要进行额外的同步, 或者在调用方进行任何其他的协调操作, 调用这个对象的行为都可以获得正确的结果, 那这个对象是线程安全的  
这个定义比较严谨, 它要求线程安全的代码都必须具备一个特征: 代码本身封装了所有必要的正确性保障手段 (如互斥同步等), 令调用者无须关心多线程的问题, 更无须自己采用任何措施来保证多线程的正确调用; 这一点看似简单实则很难做到, 在大多数场景中都会将这个定义弱化一些, 如果把 "调用这个对象的行为" 限定为 "单次调用", 这个定义的其他描述也成立的话, 就可以称它是线程安全的了

#####  Java 语言中的线程安全
这里讨论的线程安全, 就限定于多个线程之间存在共享数据访问这个前提, 因为如果一段代码根本不会与其他线程共享数据, 那么从线程安全的角度来看, 程序是串行执行还是多线程执行对它来说并没有任何区别; 在这里可以不把线程安全当作一个非真即假的二元排他项来看待, 按照线程安全的 "安全程度" 由强至弱来排序, 可以将 Java 语言各种操作共享的数据分为以下五类: 不可变, 绝对线程安全, 相对线程安全, 线程兼容, 线程对立

###### 不可变
在 Java 语言中 (特指 JDK 1.5 以后, 即 Java 内存模型被修正以后的 Java 语言), 不可变 (Immutable) 的对象一定是线程安全的, 无论是对象的方法实现还是方法的调用者, 都不需要再采取任何的线程安全保障措施; final 关键字带来的可见性时曾提到过, 只要一个不可变的对象被正确的构建出来 (没有发生 this 引用逃逸现象), 那其外部的可见状态永远也不会改变, 永远也不会看到它在多个线程中处于不一致的状态; 不可变带来的安全性最简单和纯粹  
Java 语言中, 如果共享数据是一个基本数据类型, 那么只要在定义时使用 final 关键字修饰它就可以保证它是不可变的; 如果共享数据是一个对象, 那就需要保证对象的行为不会对其状态产生任何影响才行; 例如 java.lang.String 类的对象就是一个典型的不可变对象, 调用它的 substring(), replace(), concat() 等方法都不会改变它原来的值, 只会返回一个新构造的字符串对象; 保证对象行为不影响自己状态的途径有很多种, 其中最简单的就是把对象中带有状态的变量都申明为 final, 这样在构造函数结束以后, 它就是不可变的; 例如 java.lang.Integer
```
/**
 * The value of the {@code Integer}.
 *
 * @serial
 */
private final int value;

/**
 * Constructs a newly allocated {@code Integer} object that
 * represents the specified {@code int} value.
 *
 * @param   value   the value to be represented by the
 *                  {@code Integer} object.
 */
public Integer(int value) {
    this.value = value;
}
```
在 Java API 中符合不可变要求的类型, 除了以上提到的 String 之外, 常用的还有枚举类, 以及 java.lang.Number 的部分子类

###### 绝对线程安全
绝对的线程安全完全满足 Brian Goetz 给出的线程安全的定义, 这个定义其实是很严格的, 一个类要达到 "不管运行时环境如何, 调用者都不需要任何额外的同步措施" 通常需要付出很大的甚至是不切实际的代价; Java API 中标注是线程安全的类, 大多数都不是绝对的线程安全; 例如 java.util.Vector 是一个线程安全的容器, 但即使它的所有方法都被修饰成同步, 也不意味着调用它的时候永远都不用需要同步手段了, 示例如下
```
private static Vector<Integer> vector = new Vector<>();

public static void main(String[] args) {
    while (true) {
        for (int i = 0; i < 10; i++) {
            vector.add(i);
        }

        Thread removeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            }
        });

        Thread printThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < vector.size(); i++) {
                    System.out.println(vector.get(i));
                }
            }
        });

        removeThread.start();
        printThread.start();

        // 不要同时产生过多的线程, 否则会导致操作系统假死
        while (Thread.activeCount() > 20);
    }
}
```
TODO... JDK 8 未抛出错...

###### 相对线程安全
相对线程安全就是通常意义上说讲的线程安全, 它需要保证这个对象单独的操作是线程安全的, 在调用的时候不需要做额外的保障措施, 但是对于一些特定的顺序的连续调用, 就可能需要调用端使用额外的同步 手段来保证调用的正确性; Java 语言中, 大部分线程安全类都属于这种类型, 如 Vector, HashTable, Collections 的 synchronizedCollection() 方法包装的集合等

###### 线程兼容
线程兼容是指对象本身并不是线程安全的, 但是可以通过在调用端正确的使用同步手段来保证对象在并发环境中可以安全的使用, 平常所说的一个类不是线程安全的, 绝大多数指的是这种情况

###### 线程对立
线程对立是指无论调用端是否采取了同步措施, 都无法在多线程环境中并发使用代码, 由于 Java 语言天生具备多线程特性, 线程对立这种排斥多线程的代码是很少出现的, 而且通常都是有害的, 应当尽量避免  
一个线程对立的例子就是 Thread 类的 suspend() 和 resume() 方法, 如果有两个线程同时持有一个线程对象, 一个尝试去中断, 一个尝试去恢复线程, 如果并发执行的话, 如论调用时是否进行了同步, 目标线程都是存在死锁风险的, 如果 suspend() 中断的线程就是即将要执行 resume() 的那个线程, 那肯定是要产生死锁的; 也是这个原因, suspend() 和 resume() 已被声明为废弃了; 常见的线程对立操作还有 System.setIn(), System.setOut() 和 System.runFinalizersOnExit() 等

##### 线程安全的实现方法
