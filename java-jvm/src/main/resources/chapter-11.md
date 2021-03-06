### 晚期 (运行期) 优化
从计算机程序出现的第一天起, 对效率的追求就是程序天生的坚定信仰, 这个过程犹如一场没有终点, 永不停歇的 F1 方程式竞赛, 程序员是车手, 技术平台则是赛道上飞驰的赛车

#### 概述
在部分商用虚拟机中 (Sun HotSpot, IBM J9) 中, Java 程序最初是通过解释器进行解释执行的, 当虚拟机发现某个方法或代码块的运行特别频繁时, 就会把这些代码认定为 "热点代码" (Hot Spot Code); 为了提高热点代码的执行效率, 在运行时, 虚拟机将会把这些代码编译为与本地平台相关的机器码, 并进行各种层次的优化, 完成这个任务的编译器称为即时编译器 (Just In Time Compiler, JIT 编译器)  
即时编译器并不是虚拟机必需的部分, Java 虚拟机规范中并没有规定 Java 虚拟机内必须要有即时编译器存在, 更没有限定或指导即时编译器应该如何去实现; 但是, 即时编译器编译性能的好坏, 代码优化程度的高低却是衡量一款商用虚拟机优秀与否的最关键的指标之一, 也是虚拟机中最核心且最能体现虚拟机技术水平的部分  

#### HotSpot 虚拟机内的即时编译器
- 为何 HotSpot 虚拟机要使用解释器与编译器并存的架构
- 为何 HotSpot 虚拟机要实现两个不同的即时编译器
- 程序何时使用解释器执行, 何时使用编译器执行
- 哪些程序代码会被编译为本地代码, 如何编译为本地代码
- 如何从外部观察即时编译器的编译过程和编译结果

##### 解释器与编译器
解释器和编译器两者各有优势: 当程序需要快速启动和执行的时候, 省去编译的时间, 立即执行; 在程序运行之后, 随着时间的推移, 编译器逐渐发挥作用, 把越来越多的代码编译成本地代码之后, 可以获取更高的执行效率; 当程序运行环境中内存资源限制较大 (如部分嵌入式系统中), 可已使用解释执行节约内存, 反之可以使用编译执行来提升效率; 同时, 解释器还可以作为编译器激进优化时的一个 "逃生门", 让编译器依据概率选择一些大多数时候都能提升运行速度的优化手段, 当激进优化的假设不成立, 如加载了新类后类型继承结构出现变化, 出现 "罕见陷阱" 时可以通过逆优化退回到解释器状态继续执行 (部分没有解释器的虚拟机中也会采用不进行激进优化的 C1 编译器担任 "逃生门" 的角色); 因此, 在整个虚拟机架构中解释器与编译器经常配合工作
```
                                       |---------编译器---------|  
  |---- 解释器 ----| ---即时编译 --->   |  Client Compiler (C1)  |
  |  Interpreter  |                    |                        |
  |---------------| <--- 逆优化 ---    |  Server Compiler (C2)  |
                                       |------------------------|  
```
HotSpot 虚拟机中内置了两个即时编译器, 分别称为 Client Compiler (C1) 和 Server Compiler (C2) 编译器; 目前主流的 HotSpot 虚拟机中, 默认采用解释器与其中一个编译器直接配合的方式工作, 程序使用哪个编译器, 取决于虚拟机运行的模式, HotSpot 虚拟机会根据自身版本的与宿主机器的硬件性能自动选择运行模式, 用户也可以使用 "-client" 或 "-server" 参数去强制指定虚拟机在 Client 模式或 Server 模式  
无论是采用哪一个编译器, 解释器与编译器搭配使用的方式在虚拟机中称为 "混合模式" (Mixed Mode), 用户可以使用参数 "-Xint" 强制虚拟机运行与 "解释模式" (Interpreted Mode), 这时编译器完全不介入工作, 全部代码都使用解释方式执行; 另外也可以使用参数 "-Xcomp" 强制虚拟机运行于 "编译模式" (Compiled Mode), 这时将优先采用编译方式执行程序, 但是解释器仍要在编译无法进行的情况下介入执行过程  
由于即时编译本地代码需要占用程序运行时间, 要编译出优化程度更高的代码需要花费的时间可能更久; 而且要编译出优化程度更高的代码, 解释器可能还要替编译器收集性能监控信息, 这对解释执行的速度也有影响; 为了在程序启动响应速度与运行效率之间达到最佳平衡, HotSpot 虚拟机会逐渐启用分层编译 (Tiered Compilation) 的策略; 分层编译根据编译器编译, 优化的规模与耗时, 划分出不同的编译层次, 其中包括
- 第 0 层: 程序解释执行, 解释器不开启性能监控功能, 可触发第 1 层编译
- 第 1 层: 也称为 C1 编译, 将字节码编译为本地代码, 进行简单可靠的优化, 如有必要将加入性能监控的逻辑
- 第 2 层 (或 2 层以上): 也称 C2 编译, 也是将字节码编译为本地代码, 但是会启用一些编译耗时较长的优化, 甚至会根据性能监控信息进行一些不可靠的激进优化

实施分层编译后, Client Compiler 和 Server Compiler 将会同时工作, 许多代码都可能会被多次编译, 用 Client Compiler 获取更高的编译速度, 用 Server Compiler 来获取更好的编译质量, 在解释执行的时候也无须再承担收集性能监控信息的任务

##### 编译对象与触发条件
在运行过程中被即时编译器编译的 "热点代码" 有两类
- 被多次调用的方法
- 被多次执行的循环体

对于前者, 一个方法被调用的次数多了, 方法体内代码执行的次数自然就多, 成为热点代码是理所当然的; 而后者则是为了解决一个方法只被调用过一次或者少量的几次, 但是方法体内部存在循环次数较多的循环体的问题, 这样的循环体代码也被重复执行也认为是热点代码  
对于第一种情况, 由于是方法调用触发的编译, 因此编译器理所当然的会以整个方法作为编译对象, 这种编译也是虚拟机中标准的 JIT 编译方式; 而对后一种情况, 尽管编译动作是由循环体触发的, 但是编译器依然会以整个方法作为编译对象; 这种编译方式因为编译发生在方法执行过程中, 因此形象的称之为栈上替换 (On Stack Replacement, 简称 OSR 编译, 即方法帧还在栈上, 方法就被替换了)  
判断一段代码是不是热点代码, 是不是需要触发即时编译, 这样的行为称为热点探测 (Hot Spot Detection), 其实进行热点探测并不一定要知道方法具体被调用了多少次, 目前主要有以下两种方法
- 基于采样的热点探测 (Sample Based Hot Spot Detection): 采用这种方法的虚拟机会周期性的检查各个线程栈顶, 如果发现某个 (或某些) 方法经常出现在栈顶, 那这个方法就是热点方法; 基于采样的热点探测的好处是简单, 高效, 还可以很容易的获取方法调用关系 (将调用堆栈展开即可), 缺点是很难精确的确认一个方法的热度, 因为容易受到线程阻塞或别的外界因素的影响而扰乱热点探测
- 基于计数器的热点探测 (Counter Based Hot Spot Detection): 采用这种方法的虚拟机会为每个方法 (甚至代码块) 建立计数器, 统计方法的执行次数, 如果执行次数超过一定的阈值就认为它是热点方法; 这种统计方法实现起来麻烦一些, 需要为每个方法建立并维护计数器, 而且不能直接获取到方法的调用关系, 但是它的统计结果相对来说更加精确和严谨

在 HotSpot 虚拟机中使用的是第二种 --- 基于计数器的热点探测方法, 因此它为每个方法准备了两类计数器: 方法计数器 (Invocation Counter) 和回边计数器 (Back Edge Counter); 在虚拟机运行参数确定的前提下, 这两个计数器都有一个确定的阈值, 当计数器超过阈值溢出了, 就会触发 JIT 编译

###### 方法调用计数器  
方法调用计数器用于统计方法被调用的次数, 它的默认阈值在 Client 模式下是 1500 次, 在 Server 模式下是 10000 次, 这个阈值可以通过虚拟机参数 -XX: CompileThreshold 来人为设定; 当一个方法被调用时, 会检查是否存在被 JIT 编译过的版本, 如果存在则优先使用编译后的本地代码来执行, 如果不存在已被编译过的版本, 则将此方法的调用计数器值加 1, 然后判断方法调用计数器与回边计数器值之和是否超过方法调用计数器的阈值, 如果已超过阈值那么会向即时编译器提交一个该方法的代码编译请求  
如果不做任何设置, 执行引擎并不会同步等待编译请求完成, 而是继续进入解释器按照解释器方式执行字节码, 直到提交的请求被编译器编译完成; 当编译工作完成之后, 这个方法的调用入口地址就会被系统自动改写成新的, 下一次调用该方法时就会使用已编译的版本
如果不做任何设置, 方法调用计数器统计的并不是方法被调用的绝对次数, 而是一个相对的执行频率, 即一段时间之内方法被调用的次数; 当超过一定的时间限度, 如果方法的调用次数仍然不足以让它提交给即时编译器, 那这个方法的调用次数就会被减少一半, 这个过程称为方法调用计数器热度的衰减 (Counter Decay), 而这段时间内就称为此方法统计的半衰周期; 进行热度衰减的动作是在虚拟机进行垃圾回收时顺便进行的, 可以使用虚拟机参数 -XX: -UseCounterDecay 来关闭热度衰减, 让方法计数器统计调用的绝对次数, 这样只要运行时间足够长, 绝大部分方法都会被编译成本地代码; 另外可以使用 -XX: CounterHalfLifeTime 参数设置半衰周期的时间, 单位是秒  

###### 回边计数器
回边计数器的作用是统计一个方法中循环体代码执行的次数, 在字节码中遇到控制流向后跳转的指令称为回边, 建立回边计数器统计的目的就是为了触发 OSR 编译
HotSpot 虚拟机提供了一个类似方法调用计数器阈值 -XX: CompileThreshold 的参数 -XX: BackEdgeThreshold 供用户设置, 但是当前的虚拟机实际上并未使用此参数, 而是使用 -XX: OnStackReplacePercentage 来间接调整回边计数器的阈值, 其3公式如下
- Client 模式下
方法调用计数器阈值 (CompileThreshold) * ORS 比率 (OnStackReplacePercentage) / 100, 其中 OnStackReplacePercentage 的默认值为 933, 如果都取默认值则 Client 模式下的回边计数器阈值为 13995
- Server 模式下
方法调用计数器阈值 (CompileThreshold) * OSR 比率 (OnStackReplacePercentage) - 解释器监控比率 (InterpreterProfilePercentage) /100, 其中 OnStackReplacePercentage 默认值为 140, InterpreterProfilePercentage 默认值为 33, 如果都取默认值则 Server 模式下的回边计数器阈值为 10700

当解释器遇到一条回边指令时, 会先查找将要执行的代码片段是否有已经编译好的版本, 如果有则会优先执行已编译的代码, 否则就把回边计数器的值加 1, 然后判断方法调用计数器与回边计数器值之和是否超过回边计数器的阈值, 当超过阈值的时候, 将会提交一个 OSR 编译请求, 并且把回边计数器的值降低一些, 以便继续在解释器中执行循环, 等待编译器输出编译结果  
与方法计数器不同, 回边计数器没有计数热度衰减的过程, 因此这个计数器统计的就是该方法循环体执行的绝对次数, 当计数器溢出的时候, 它还会把方法计数器的值也调整到溢出状态, 这样下次再进入该方法时就会执行标准的编译过程

##### 编译过程
在默认设置下, 无论是方法调用产生的即时编译请求, 还是 OSR 编译请求, 虚拟机在代码编译器还未完成之前, 都仍将按照解释器方式继续执行, 而编译的动作可以在后台的编译线程中执行; 用于可以通过参数 -XX: -BackgroudComplication 来禁止后台编译, 在禁止后台编译后, 一旦达到 JIT 的编译条件, 执行线程向虚拟机提交编译请求后将会一直等待, 直到编译过程完成后再开始执行编译器输出的本地代码; 在后台执行编译的过程中, Client Compiler 和 Server Compiler 两个编译器的编译过程是不一样的  

###### Client Compiler
- 在第一阶段, 平台独立的前端将字节码构成一种高级中间代码表示 (High-Level Intermediate Representation, HIR); HIR 使用静态单分配 (Static Single Assignment, SSA) 的形式来代表代码值, 这可以使得一些在 HIR 的构造过程之中和之后进行的优化动作更容易实现; 在此之前编译器会在字节码上完成一些基础优化, 如方法内联, 常量传播等优化将会在字节码被构造成 HIR 之前完成  
- 在第二阶段, 平台相关的后端从 HIR 中产生低级中间代码表示 (Low-Level Intermediate Representation, LIR), 而在此之前会在 HIR 上完成另外一些优化, 如空置检查消除, 范围检查消除等, 以便让 HIR 达到更高效的代码表示形式
- 在最后阶段, 平台相关的后端使用线性扫面算法 (Linear Scan Register Allocation) 在 LIR 上分配寄存器, 并在 LIR 上做葵孔 (Peephole) 优化, 然后产生机器代码

###### Server Compiler
Server Compiler 编译时会执行所有经典的优化动作, 如无用代码消除 (Dead Code Elimination), 循环展开 (Loop Unrolling), 循环表达式外提 (Loop Expression Hoising), 消除公共子表达式 (Common Subexpression Elimination), 常量传播 (Constant Propagation), 基本块重排序 (Basic Block Recording) 等; 以及一些与 Java 语言特性相关的优化技术, 如范围检查消除 (Range Check Elimination), 空置检查消除 (Null Check Elimination) 等; 另外还可能根据解释器或 Client Compiler 提供的性能监控信息, 进行一些不稳定的激进优化, 如守护内联 (Guarded Inlining), 分支频率预测 (Branch Frequency Prediction) 等  
Server Compiler 的寄存器分配器是一个全局着色分配器, 它可以充分利用某些处理器架构 (如 RISC) 上的大寄存器集合; 以即时编译器的标准来看, Server Compiler 编译无疑是比较缓慢的, 但可以减少本地代码执行时间

##### 查看及分析即时编译结果
TODO...

#### 编译优化技术
