### 虚拟机字节码执行引擎
代码编译的结果从本地机器码转变为字节码, 是存储格式发展的一小步, 却是编程语言发展的一大步

#### 概述
在 Class 文件格式与执行引擎这部分中, 用户程序能直接影响的内容并不太多, Class 文件以何种格式存储, 类型何时加载, 如何连接, 以及虚拟机如何执行字节码指令等都是由虚拟机直接控制的行为, 用户程序无法对其进行改变; 能通过程序进行操作的, 主要是字节码生成和类加载器这两部分的功能

#### 案例分析
以下四个案例中, 类加载器和字节码的案例各有两个

##### Tomcat: 正统的类加载器架构
主流的 Java Web 服务器, 如 Tomcat , Jetty, WebLogic, WebSphere 等, 都实现了自己定义的类加载器 (一般不止一个); 因为一个功能健全的 Web 服务器要解决以下几个问题
- 部署在同一个服务器上的两个 Web 应用程序所使用的 Java 类库可以实现相互隔离; 两个不同的应用程序可能会依赖同一个第三方类库的不同版本, 不能要求一个类库在一个服务器中只有一份, 服务器应当保证两个应用程序的类库可以相互独立使用
- 部署在同一个服务器上的两个 Web 应用程序所使用的 Java 类库可以互相共享; 如果有多个应用部署时, 如果类库不能共享, 虚拟机的方法区就会很容易出现过度膨胀的风险
- 服务器需要尽可能的保证自身的安全不受部署的 Web 应用程序影响; 目前有许多主流服务器也是由 Java 语言来实现的; 因此服务器本身也有类库依赖的问题, 基于安全考虑, 服务器所使用的类库应该与应用程序的类库互相独立
- 支持 JSP 应用的 Web 服务器, 大多数都需要支持 HotSwap 功能; 但 JSP 文件最终要编译成 Class 文件才可以由虚拟机执行, 但 JSP 文件由于其纯文本的特性, 运行时修改的概率远远大于第三方类库或者程序本身

由于上述问题, 在部署 Web 应用时, 单独的一个 ClassPath 就无法满足需求了, 所以各种 Web 服务器都不约而同的提供了好几个 ClassPath 路径供用户存放第三方类库, 这些路径一般都以 `lib` 或 `classes` 命名; 被放置到不同的路径中的类库, 具备不同的访问范围和服务对象, 通常每一个目录都会有相应的自定义类加载器去加载放置在里面的 Java 类库  
在 Tomcat (5.x) 目录结构中有 3 组目录 ("/common", "/server", "/shard") 可以存放 Java 类库, 另外还可以加上 Web 应用程序自身的目录 "/WEB-INF", 一共 4 组; 把 Java 类库放置在这些目录中的含义分别如下
- 放置在 /common 目录中, 类库可被 Tomcat 和所有的 Web 应用程序共同使用
- 放置在 /server 目录中, 类库可被 Tomcat 使用, 对所有的 Web 应用程序都不可见
- 放置在 /shared 目录中, 类库可被所有的 Web 应用程序使用, 但对 Tomcat 自己不可见
- 放置在 /WebApp/WEB-INF 目录中, 类库可被所有的 Web 应用程序共同使用, 但对 Tomcat 自己不可见

为了支持这套目录结构, 并对目录里面的类库进行加载和隔离, Tomcat 自定义实现了多个类加载器, 这些类加载器按照经典的双亲委派模型来实现
```
                                                                Catalina 类加载器
                                                               /
启动类加载器 <- 扩展类加载器 <- 应用程序类加载器 <- Common 类加载器
                                                               \
                                                                Shared 类加载器 <- WebApp 类加载器 <- Jsp 类加载器
```
CommmonClassLoader, CatalinaClassLoader, SharedClassLoader 和 WebAppClassLoader 是 Tomcat 自定义的类加载器, 它们分别加载 /common, /server, /shared 和 /WebApp/WEB-INF 中的 Java 类库; 其中 WebApp 类加载和 Jsp 类加载器通常会存在多个实例, 每一个 Web 应用程序对应一个 Jsp 类加载器; CommmonClassLoader 能加载的类都可以被 CatalinaClassLoader 和 SharedClassLoader 使用, 而 CatalinaClassLoader 和 SharedClassLoader 自己能加载的类与对方相互隔离; WebAppClassLoader 可以使用 SharedClassLoader 加载到的类, 但各个 WebAppClassLoader 实例之间相互隔离; 而 JspClassLoader 的加载范围仅仅是这个 JSP 文件编译出的 Class, 它出现的目的就是为了被丢弃: 当服务器检测到 JSP 文件被修改时, 会替换掉目前的 JspClassLoader 的实例, 并通过再建立一个新的 Jsp 类加载器来实现 JSP 文件的 HotSwap 功能

##### OSGi: 灵活的类加载架构
OSGi
