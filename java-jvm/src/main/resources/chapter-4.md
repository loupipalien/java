### Java 内存区域与内存溢出异常
Java 与 C++ 之间有一堵有内存动态分配和垃圾收集技术所围成的 "高墙", 墙外面的人想进去, 墙里面的人却想出来

#### 概述
运行日志, 异常堆栈, GC 日志, 线程快照 (threaddump/javacore 文件), 堆转储快照 (heapdump/hprof 文件) 等

#### JDK 的命令行工具
JDK 的 bin 目录中提供了一些命令行工具, 这些命令行工具大多数是 jdk/lib/tools.jar 类库的一层包装, 主要功能都在 tools.jar 中实现; 以下是 Sun JDK 的一些监控和故障处理工具

|名称|主要作用|
|-|-|
|jps|JVM Process Status Tool, 显示指定系统内所有的 HotSpot 虚拟机进程|
|jstat|JVM statistics Monitoring Tool, 用于收集 HotSpot 虚拟机各方面的运行数据|
|jinfo|Configuration Info For Java, 显示虚拟机配置信息|
|jmap|Memory Map For Java, 生成虚拟机的内存转储快照 (heapdump 文件)|
|jhat|JVM Heap Dump Browser, 用于分析 heapdump 文件, 它会建立一个 HTTP/HTML 服务器, 让用户可以再浏览器上查看分析结果|
|jstack|Stack Trace For Java, 显示虚拟机的线程快照|

##### jps: 虚拟机进程状况工具
JDK 的很多小工具的名字都参考了 UNIX 命令的命名方式, jps (JVM Process Status Tool) 是其中典型; 除了名字像 UNIX 的 ps 命令之外, 它的功能也和 ps 命令类似: 可以列出正在运行的虚拟机进程, 并显示虚拟机执行主类 (main() 函数所在的类) 名称, 以及这些进程的本地虚拟机唯一 ID (Local Virtual Machine Identifier, LVMID), 对于本地虚拟机来说, 其中 LVMID 与操作系统的进程 ID (Process Identifier, PID) 是一致的; jps 命令格式如下:
```
jps [options] [hostid]
```
jps 可以通过 RMI 协议查询开启了 RMI 服务的远程虚拟机进程状态, hostid 为 RMI 注册表中注册的主机名; jps 的其他常用选项见下表

|选项|作用|
|-|-|
|-q|只输出 LVMID, 省略主类的名称|
|-m|输出虚拟机进程启动时传递给主类 main() 函数的参数|
|-l|输出主类的全名, 如果进程执行的是 jar 包, 输出 jar 路径|
|-v|输出虚拟机进程启动时 JVM 参数|

##### jstat: 虚拟机统计信息监视工具
jstat (JVM statistics Monitoring Tool) 是用于监视虚拟机各种运行状态信息的命令行工具; 可以显示本地或远程虚拟机进程中的类装载, 内存, 垃圾收集, JIT 编译等运行数据; jstat 的命令格式如下:
```
jstat [option vmid [interval [s|ms] [count]]]
```
如果是本地虚拟机进程, VMID 与 LVMID 是一致的, 如果是远程虚拟机进程, 那 VMID 的格式应当是:
```
[protocol:] [//] lvmid[@hostname[:port]/servername]
```
参数 interval 和 count 代表查询间隔和次数, 如果省略这两个参数, 说明只查询一次; 选项 option 代表这用户希望查询的虚拟机信息, 主要分 3 类: 类装载, 垃圾收集, 运行期编译状况, 参数具体描述如下

|选项|作用|
|-|-|
|-class|监视类装载, 卸载数量, 总空间以及类装载所耗费的时间|
|-gc|监视 Java 堆状况, 包括 Eden 区, 两个 Survivor 区, 老年代, 永久代等容量, 已用空间, GC 时间合计等信息|
|-gccapacity|监视内容与 -gc 相同, 但输出主要关注 Java 堆各个区域使用到的最大, 最小空间|
|-gcutil|监视内容与 -gc 相同, 但输出主要关注已使用空间占总空间的百分比|
|-gccease|与 -gcutil 功能一样, 但是会额外输出导致上一次 GC 产生的原因|
|-gcnew|监视新生代 GC 状况|
|-gcnewcapacity|监视内容与 -gcnew 基本相同, 输出主要关注使用到的最大, 最小空间|
|-gcold|监视老年代 GC 状况|
|-gcoldcapacity|监视内容与 -gcold 基本相同, 输出主要关注使用到的最大, 最小空间|
|-gcpermcapacity|输出永久代使用到的最大, 最小空间|
|-compiler|输出 JIT 编译器编译过的方法, 耗时等信息|
|-printcomplication|输出已经被 JIT 编译的方法|

执行样例

|S0|S1|E|O|P|YGC|YGCT|FGC|FGCT|GCT|
|-|-|-|-|-|-|-|-|-|-|
|0.00|0.00|6.20|41.42|47.20|16|0.105|3|0.472|0.557|

即表明这台服务器的新生代 Eden 区使用了 6.2% 的空间, 两个 Survivor 区里面都是空的, 老年代和永久代则分别使用了 41.42% 和 47.20% 的空间; 程序运行以来共发生 Minor GC 16 次, 总耗时 0.105 秒; 发生 Full GC 3 次, 总耗时 0.472 秒; 所有 GC 总耗时为 0.577 秒

##### jinfo: Java 配置信息工具  
jinfo (Configuration Info For Java) 的作用是实时地查看和调整虚拟机各项参数; 使用 jps 命令的 -v 参数可以查看虚拟机启动时显示指定的参数列表, 但如果想知道未被显示指定的参数的系统默认值, 除了找资料就只能是使用 jinfo 的 -flag 选项查询了 (JDK 1.6 或以上版本, 使用 java -XX:+PrintFlagsFinal 查看参数默认值也是一个很好的选择), jinfo 还可以使用 -sysprops 选项把虚拟机进程的 System.getProperties() 的内容打印出来; jinfo 还可以使用 -flag [-|+] name 或者 -flag name=value 修改一部分运行期可写的虚拟机参数值; jinfo 命令格式如下:
```
jinfo [option] pid
```

##### jmap: Java 内存映像工具
jmap (Memory Map For Java) 命令用于生成堆转储快照; 除了 jmap 命令外还有其他一些手段获取 Java 堆转储快照
- 使用 -XX:+HeapDumpOnOutOfMemoryError 参数, 可以让虚拟机在 OOM 异常出现之后自动生成 dump 文件
- 使用 -XX:+HeapDumpOnCtrlBreak 参数则可以使用 Ctrl + Break 键让虚拟机生成 dump 文件
- 在 Linux 系统中, 可以通过 Kill -3 命令发送进程退出信号 "吓唬" 一下虚拟机拿到 dump 文件

jmap 的作用并不仅仅是为了获取 dump 文件, 它还可以查询 finalize 执行队列, Java 堆和永久代的详细信息, 如空间使用率, 当前使用的是哪种收集器等; jmap 的命令格式如下:
```
jmap [option] vmid
```
option 的合法值见下表

|选项|作用|
|-|-|
|-dump|生成 Java 堆转储快照, 格式为: -dump:[live, ] format=b, file=<filename>, 其中 live 子参数说明是否只 dump 出存活的对象|
|finalizerinfo|显示在 F-Queue 中等待 Finalizer 线程执行 finalize 方法的对象, 只在 Linux/Solaris 平台下有效|
|-heap|显示 Java 堆详细信息, 如使用哪种回收器, 参数配置, 分代状况等; 只在 Linux/Solaris 平台下有效|
|-histo|显示堆中对象统计信息, 包括类, 实例数量, 合计容量|
|-permstat|以 ClassLoader 为统计口径显示永久代内存状态; 只在 Linux/Solaris 平台下有效|
|-F|当虚拟机进程堆 -dump 选项没有响应时, 可使用这个选项强制生成 dump 快照; 只在 Linux/Solaris 平台下有效|
