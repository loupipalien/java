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
