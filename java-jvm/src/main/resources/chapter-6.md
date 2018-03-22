### 类文件结构
代码编译的结果从本地机器码转变为字节码, 是存储格式发展的一小步, 却是编程语言发展的一大步

#### 概述
计算机虽然只能识别 0 和 1, 当由于近十年来虚拟机以及大量建立在虚拟机上的程序语言如雨后春笋出现并蓬勃发展, 将编写的程序编译成二进制本地机器码 (Native Code) 已不再是唯一的选择, 越来越多的程序选择了与操作系统和机器指令集无关的, 平台中立的格式作为程序编译后的存储格式

#### 无关性基石  
- 虚拟机和字节码是构成平台无关性的基石
- 虚拟机和字节码也是实现语言无关性的基础

Java 虚拟机不和包括 Java 在内的任何语言绑定, 它只与 "Class 文件" 这种特定的二进制文件格式所关联, Class 文件中包含了 Java 虚拟机指令集和符号表以及若干其他辅助信息; 基于安全方面的考虑, Java 虚拟机规范要求在 Class 文件中使用许多强制性的语法和结构化约束, 但任何一门功能性语言都可以表示为一个能被 Java 虚拟机所接受的有效的 Class 文件; 作为一个通用的, 机器无关的执行平台, 任何其他语言的实现者都可以将 Java 虚拟机作为语言的产品交付媒介

#### Class 类文件的结构
Class 文件是一组以 8 位字节为基础单位的二进制流, 各个数据项目严格按照顺序紧凑的排列在 Class 文件中, 中间没有添加任何分隔符, 这使得整个 Class 文件中存储的内容几乎全部是程序运行的必要数据, 没有空隙存在; 当遇到需要占用 8 位字节以上空间的数据项时, 则会高位在前的方式分隔成若干个 8 位字节进行存储  
根据 Java 虚拟机规范的规定, Class　文件格式采用一种类似于 C 语言结构的伪结构来存储数据, 这种伪结构中只有两种数据类型: 无符号数和表, 后面的解析都要以这两种数据类型为基础
- 无符号数: 属于基本的数据类型, 以 u1, u2, u4, u8 来分别代表 1 个字节, 2 个字节, 4 个字节, 8 个字节的无符号数, 无符号数可以用来描述数字, 索引引用, 数量值或者按照 UTF-8 编码构成字符串值
- 表: 由多个无符号数或者其他表作为数据项构成的复合数据类型, 所有表都习惯性的以 "\_info" 结尾, 表用于描述有层次关系的复合结构的数据, 整个 Class 文件本质上就是一张表

Class 文件格式

|类型|名称|数量|  
|-|-|-|
|u4|magic|1|
|u2|minor_version|1|
|u2|major_version|1|
|u2|constant_pool_count|1|
|cp_info|constant_pool|constant_pool_count - 1|
|u2|access_flags|1|
|u2|this_class|1|
|u2|super_class|1|
|u2|interfaces_count|1|
|u2|interfaces|interfaces_count|
|u2|fields_count|1|
|field_info|fields|fields_count|
|u2|methods_count|1|
|method_info|methods|methods_count|
|u2|attributes_count|1|
|attribute_info|attributes|attributes_count|

无论是无符号数还是表, 当需要描述同一类但数量不定的多个数据时, 经常会使用一个前置的容量计数器加若干个连续的数据项的形式, 这时称这一系列连续的某一类型的数据为某以类型的集合

##### 魔数与 Class 文件的版本
每个 Class 文件的头 4 个字节称为魔数, 它的唯一作用是确定这个文件是否为一个能被虚拟机接受的 Class 文件, Class 文件的魔数值为: "0xCAFEBABE"  
紧接着魔数的 4 个字节存储的是 Class 文件的版本号: 第 5, 6 个字节是次版本号, 第 7, 8 个字节是主版本号; Java 版本号从 45 开始, JDK 1.1 之后每个大版本发布主版本号加 1, 高版本 JDK 兼容低版本的 Class 文件, 但不能运行以后版本的 Class 文件, 虚拟机也拒绝执行超过版本号的 Class 文件

##### 常量池
紧接着主次版本号之后的是常量池入口, 常量池可以理解为 Class 文件之中的资源仓库, 它是 Class 文件结构中与其他项目关联最多的数据类型,  也是占用 Class 文件空间最大的数据项目之一, 同时它还是在 Class 文件中第一个出现的表类型数据项目  
由于常量池中的常量数量不固定, 所以在常量池的入口需要放置一项 u2 类型的数据, 代表常量池容量计数值 (constant_pool_count); 这个容量计数不是从 1 开始而是从 0 开始, 在 Class 文件格式规范制定时, 设计者将 0 项常量空出来是有特殊考虑的, 这样做的目的在于满足后面某些指向常量池的索引值的数据在特定的情况下需要表达 "不引用任何一个常量池项目" 的含义, 这种情况就可以把索引值置为 0 来表示  
常量池中主要存放两大类常量: 字面量 (Literal) 和符号引用 (Symbolic References); 字面量比较接近 Java 语言层面的常量概念, 如文本字符串, 声明为 final 的常量值等; 而符号引用则属于编译原理方面的概念, 包括了下面三类常量:
- 类和接口的全限定名 (Fully Qualified Name)
- 字段的名称和描述符 (Descriptor)
- 方法的名称和描述符

Java 代码在进行 Javac 编译的时候, 并不像 C 和 C++ 那样有连接的步骤, 而是在虚拟机加载 Class 文件的时候进行动态连接; 也就是说 Class 文件中不会保存各个方法的, 字段的最终内存布局信息, 因此这些字段, 方法的符号引用不经过运行期转化的话就无法得到真正的内存入口地址, 也就无法直接被虚拟机使用; 当虚拟机运行时, 需要从常量池获得对应的符号引用, 再在类创建时或运行时解析, 翻译到具体的内存地址中  
常量池中的每一项常量都是一个表, 在 JDK 1.7 之前共有 11 种结构各不相同的表结构数据, 在 JDK 1.7 种为了更好的支持动态语言的调用, 又额外增加了 3 种 (CONSTANT_MethodHandle_info, CONSTANT_MethodType_info, CONSTANT_InvokeDynamic_info); 这 14 种表都有一个共同的特点, 就是表开始的第一位是一个 u1 类型的标志位 (tag), 代表当前这个常量属于哪种常量类型;常量池的项目类型如下:

|类型|标志|描述|
|-|-|-|
|CONSTANT_Utf8_info|1|UTF-8 编码的字段|
|CONSTANT_Integer_info|3|整型字面量|
|CONSTANT_Float_info|4|浮点型字面量|
|CONSTANT_Long_info|5|长整型字面量|
|CONSTANT_Double_info|6|双精度浮点型字面量|
|CONSTANT_Class_info|7|类或接口的符号引用|
|CONSTANT_String_ino|8|字符串类型字面量|
|CONSTANT_Fieldref_info|9|字段的符号引用|
|CONSTANT_Methodref_info|10|类中方法的符号引用|
|CONSTANT_InterfaceMethodref_info|11|接口中方法的符号引用|
|CONSTANT_NameAndType_info|12|字段或方法的部分符号引用|
|CONSTANT_MethodHandle_info|15|表示方法句柄|
|CONSTANT_MethodType_info|16|标识方法类型|
|CONSTANT_InvokeDynamic_info|18|表示动态方法调用点|

###### CONSTANT_Class_info 型常量的结构
|类型|名称|数量|
|-|-|-|
|u1|tag|1|
|u2|name_index|1|
其中 tag 是标志位, 用于区分常量类型; name_index 是一个索引值, 它指向常量池中一个 CONSTANT_Utf8_info 类型常量, 此常量代表了这类 (或者接口) 的全限定名

###### CONSTANT_Utf8_info 型常量的结构
|类型|名称|数量|
|-|-|-|
|u1|tag|1|
|u2|length|1|
|u1|bytes|length|
length 值说明了这个 UTF-8 编码的字符串是多少字节, 它后面紧跟着的长度为 length 字节的连续数据是一个使用 UTF-8 缩略编码表示的字符串; UTF-8 缩略编码与普通 UTF-8 编码的区别是: '\u0001' 到 '\u007f' 之间的字符串的缩略码使用一个字节表示, 从 '\u0080' 到 '\uo7ff' 之间的所有字符的缩略编码用两个字节表示, 从 '\0800' 到 '\ufff' 之间的所有字符的缩略编码就按照普通的 UTF-8 编码规则使用三个字节表示; 由于 Class 文件中方法, 字段等都需要引用 CONSTANT_Utf8_info 型常量来描述名称, 所以 CONSTANT_Utf8_info 型常量的最大长度也就是 Java 中方法, 字段名的最大长度, 即 u2 类型所能表示的最大值 65535 (64 KB)
