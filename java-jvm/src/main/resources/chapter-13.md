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
如何实现线程安全与代码编写有很大关系, 但虚拟机提供的同步和锁机制也起到了非常重要的作用

###### 互斥同步
互斥同步 (Mutual Exclusion & synchronization) 是常见的一种并发正确性保障手段, 同步是指在多线程并发访问下共享数据时, 保证共享数据在同一时刻只被一个 (或者是一些, 使用信号量的时候) 线程使用; 互斥是实现同步的一种手段, 临界区 (Critical Section), 互斥量 (Mutex), 信号量 (Semaphore) 都是实现互斥实现方式; 互斥是方法, 同步是目的  
在 Java 中, 最基本的互斥同步手段就是 synchronized 关键字, synchronized 关键字经过编译之后, 会在同步块的前后分别形成 monitorenter 和 monitorexit 这两个字节码指令, 这两个字节码都需要一个 reference 类型的参数来指明要锁定和解锁的对象; 如果 Java 程序中 synchronized 明确制定了对象参数, 那就是这个对象的 reference; 如果没有明确指定, 那就根据 synchronized 修饰的实例方法还是类方法, 取对对应的对象实例或 Class 对象来作为锁对象  
根据虚拟机规范的要求, 在执行 monitorenter 指令时, 首先要尝试获取对象的锁; 如果这个对象没被锁定, 或者当前线程已经拥有了那个对象的锁, 把锁的计数器加 1, 相应的在执行 monitorexit 指令时会将锁计数器减 1, 当计数器为 0 时, 锁就被释放; 如果获取对象锁失败, 那当前线程就要阻塞等待, 直到对象锁被另一个线程释放为止  
在虚拟机规范对 monitorenter 和 monitorexit 的行为描述中, 有两点需要被注意: 一是 synchronized 同步块对同一条线程来说是可重入的, 不会出现自己把自己锁死的问题; 二是同步块在已进入的线程执行完之前, 会阻塞后面其他线程的进入; 由于 Java 的线程是映射到操作系统的原生线程之上的, 如果要阻塞或者唤醒一个线程, 都需要操作系统来完成, 这就需要从用户态转换到核心态中, 而状态转换需要消耗更多的处理器时间; 对于代码简单的同步块 (如被 synchronized 修饰的 getter/setter 方法), 状态转换消耗的时间有可能比用户代码执行的时间还长; 所以 synchronized 是 Java 语言中一个重量级的操作, 建议在确实有必要的情况下才使用这种操作; 虚拟机本身对此也会进行一些优化, 譬如在通知操作系统阻塞线程之前加入一段自旋等待过程, 避免频繁的切换到核心态中  
除了 synchronized 之外, 还可以使用 java.util.concurrent 包种风格的重入锁 (ReentrantLock) 来实现同步, ReentrantLock 和 synchronized 在使用上很相似, 它们都具备一样的线程重入特性, 只是在代码上写法有所区别; 一个表现在 API 层面的互斥锁 (lock(), unlock() 方法配合 try/finally 语句块来完成), 另一个表现为原生语法层面的互斥锁; 但 ReentrantLock 比 synchronized 增加了一些高级功能, 主要有: 等待可中断, 可实现公平锁, 锁可绑定多个条件  
- 等待可中断是指当持有锁的线程长期不释放锁的时候, 正在等待的线程可以选择放弃等待, 改为处理其他事情, 可中断特性对处理执行时间非常长的同步块很有帮助
- 公平锁是指多个线程在等待同一个锁时, 必须按照申请所的时间顺序来依次获取锁; 而非公平锁则不保证这一点, 在锁被释放时, 任何一个等待所的线程都有机会获得锁; synchronized 中的锁是非公平的, ReentrantLock 默认情况下也是非公平的, 但可以通过带布尔值的构造函数要求使用公平锁
- 锁绑定多个条件是指一个 ReentrantLock 对象可以同时绑定多个 Condition 对象, 而在 synchronized 中, 锁对象的 wait(), notify(), notifyAll() 方法可以实现一个隐含的条件, 如果要和多于一个的条件关联的时候, 就不得不额外添加一个锁, 而 ReentrantLock 只需多次调用 newConditions() 方法即可

Brian Goetz 对这两种锁在 JDK 1.5 与单核处理器, 以及 JDK 1.5 和双 Xoen 处理器环境下做了一组吞吐量对比实验; 结果表示: 多线程环境下 synchronized 的吞吐量下降的非常严重, 而 ReentrantLock 则能基本保持在同一个比较稳定的水平上; JDK 1.6 中加入了很多锁的优化措施, 所以在 JDK 1.6 后 synchronized 和 ReentrantLock 的性能基本完全持平; 虚拟机在未来的性能改进中肯定也会更加偏向与原生的 synchronized, 建议在 synchronized 能实现需求的情况下, 优先考虑使用 synchronized 来进行同步  

###### 非阻塞同步
互斥同步最主要的问题就是进行线程阻塞和唤醒带来的性能问题, 因此这种同步也称为阻塞同步 (Blocking Synchronization); 互斥同步属于一种悲观的并发策略, 认为只要不去做正确的同步措施, 就会出现问题, 无论共享数据是否真的会出现竞争, 它都要进行加锁 (这里讨论概念模型, 实际上虚拟机会优化掉很大一部分不必要的锁); 随着硬件指令集的发展, 有了基于冲突检测的乐观并发策略的选择, 即先进行操作, 如果没有其他线程争用共享数据, 那操作就成功完成, 如果共享数据争用产生了冲突, 那就在采用其他的补偿措施 (最常见的补偿措施是不断重试直到成功为止), 这种乐观的并发策略的许多实现都不需要把线程挂起, 因此这种同步操作被称为非阻塞同步 (Non-Blocking Synchronization)  
乐观并发策略需要硬件指令集的支持, 因为需要操作和冲突检测的这两个步骤具备原子性, 如果这里再使用同步互斥来保证就失去了意义, 所以只能靠硬件来保证一个从语义上看起来需要多次操作的行为只通过一条处理器指令就能完成; 这类指令常用的有:
- 测试并设置 (Test-And-Set)
- 获取并增加 (Fetch-And-Increment)
- 交换 (Swap)
- 比较并交换 (Compare-And-Swap, CAS)
- 加载链接/条件存储 (Load-Linked/Store-Conditional, LL/SC)

CAS 指令需要 3 个操作数, 分别是内存位置 (可简单理解为变量的内存地址, 用 V 表示), 旧的预期值 (用 A 表示), 新值 (用 B 表示); CAS 指令执行时, 当且仅当 V 符合旧预期值 A 时, 处理器用新值 B 更新 V 的值, 否则不执行更新, 但是无论是否更新了 V 的值, 都会返回 V 的旧值, 上述处理是一个原子操作  
在 JDK 1.5 之后, Java 程序中才可以使用 CAS 操作, 该操作由 sun.misc.Unsafe 类中的 compareAndSwapInt() 和 compareAndSwapLong() 等几个方法包装提供, 虚拟机在内部对这些方法做了特殊处理, 即时编译出来的结果就是一条平台相关的处理器 CAS 指令, 没有方法调用的过程, 或者可以认为是无条件内联进去了  
由于 Unsafe 类不是提供给用户使用的类, 因此如果不采用反射手段, 只能通过其他的 Java API 间接的使用, 如 java.util.concurrent 包中的原子类, 其中的 compareAndSet() 和 getAndIncrement() 等方法都是用 Unsafe 类中的 CAS 操作  
```
public static AtomicInteger race = new AtomicInteger(0);

public static void increase() {
    race.incrementAndGet();
}

private static final int THREADS_COUNT = 20;

public static void main(String[] args) {
    Thread[] threads = new Thread[THREADS_COUNT];
    for (int i = 0; i < THREADS_COUNT; i++) {
        threads[i] = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
            }
        });
        threads[i].start();
    }

    // 等待所有线程结束
    while (Thread.activeCount() > 1) {
        Thread.yield();
    }

    System.out.println(race);
}
```
第十二章中用来证明 volatile 修饰的变量不具备原子性的代码, 为其具备原子性, 使用原子类操作比 synchronized 同步要更有效率; 并且结果也会是正确, 这都要归功于 incrementAndGet 方法的原子性, 其代码如下
```
/**
 * Atomically increments by one the current value.
 *
 * @return the updated value
 */
public final int incrementAndGet() {
    for(;;) {
      int current = get();
      int next = current + 1;
      if (compareAndSet(current, next))
          return next;
    }
}
```
incrementAndGet() 方法在一个无限循环中, 不断尝试一个比当前值大 1 的新值赋给自己, 如果失败了, 那说明在执行 "获取-设置" 操作时已经有了修改, 于是再次循环进行下一次操作, 直到设置成功为止  
尽管 CAS 看起来很完美, 但是也有其无法涵盖互斥同步的使用场景, 并且 CAS 从语义上来将并不是完美的, 存在这样一个逻辑漏洞: 如果变量 V 初次读取的时候是 A 值, 并且在准备赋值的时候检查到仍然是 A 值, 但这这段期间它的值曾被修改为 B, 后又被改回 A, 那 CAS 操作就会误认为它从来没有被修改过; 这个漏洞被称为 CAS 操作的 "ABA" 问题; java.util.concurrent 包为了解决这个问题, 提供了一个带有标记的原子引用类 "AtomicStampedRefrence", 它可以通过控制变量的版本来保证 CAS 的正确性; 但大部分情况下 ABA 问题不会影响到程序并发的正确性, 如果需要解决 ABA 问题改用传统的互斥同步的问题会比原子类更高效

###### 无同步方案
要保证线程安全, 并不是一定就要进行同步, 两者没有因果关系; 同步只是保证共享数据争用时的正确性手段, 如果一个方法不涉及共享数据, 则无须任何同步措施去保证正确性, 因此会有一些代码天生就是线程安全的
- 可重入代码 (Reentrant Code)
这种代码可以在代码执行的任何时刻中断它, 转而去执行另外一段代码, 而在控制权返回后, 原来的程序不会出现任何错误; 相对线程安全来说, 可重入性是更基本的特性, 它可以保证线程安全, 即所有的可重入代码都是线程安全的, 但是并非所有线程安全的代码都是可重入的; 可重入代码有一些共同的特征, 例如不依赖存储在堆上的数据和公用的系统资源, 用到的状态量都由参数传入, 不调用非可重入的方法等; 如果一个方法它的返回结果是可预测的, 只要输入了相同的数据就都能返回相同的结果, 那就满足可重入性的要求, 也就是线程安全的
- 线程本地存储 (Thread Local Storage)
在 Java 语言中, 如果一个变量要被多线程访问, 可以使用 volatile 关键字声明它是 "易变的"; 如果一个变量要被某个线程独享, 可以通过 java.lang.ThreadLocal 类来实现线程本地存储的功能; 每一个线程的 Thread 对象都有一个 ThreadLocalMap 对象, 这个对象存储了一组以 ThreadLocal.threadLocalHashCode 为键, 以本地线程变量为值的 K-V 值对, ThreadLocal 对象就是当前线程的 ThreadLocalMap 的访问入口, 每一个 ThreadLocal 对象都包含了一个独一无二的 threadLocalHashCode 值, 使用这个只就可以在线程 K-V 值中找回对应的本地线程变量

#### 锁优化
