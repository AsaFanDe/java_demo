# Java线程精讲

### 一、知识点罗列

* Java线程简介
* Java创建和销毁线程开销问题
* 为什么要使用线程池 [http://blog.csdn.net/wolf909867753/article/details/77500625](http://blog.csdn.net/wolf909867753/article/details/77500625)  [http://blog.csdn.net/a497393102/article/details/8597819](http://blog.csdn.net/a497393102/article/details/8597819)
* 使用线程池要面临哪些问题？
* Java多线程详解和应用场景
* Java多线程框架
* 同步、异步、阻塞和非阻塞
* 多线程和高并发的关系
* Java线程的运行机制

### 二、介绍

1、操作系统中线程和进程的概念    现在的操作系统是多任务操作系统。多线程是实现多任务的一种方式。    进程是指一个内存中运行的应用程序，每个进程都有自己独立的一块内存空间，一个进程中可以启动多个线程。比如在Windows系统中，一个运exe就是一个进程。    线程是指进程中的一个执行流程，一个进程中可以运行多个线程。比如java.exe进程中可以运行很多线程。线程总是属于某个进程，进程中的多个线程共享进程的内存。  “同时”执行是人的感觉，在线程之间实际上轮换执行。

  
2、Java中的线程    在Java中，“线程”指两件不同的事情：

1、java.lang.Thread类的一个实例；

2、线程的执行。  
    使用java.lang.Thread类或者java.lang.Runnable接口编写代码来定义、实例化和启动新线程。  一个Thread类实例只是一个对象，像Java中的任何其他对象一样，具有变量和方法，生死于堆上。 Java中，每个线程都有一个调用栈，即使不在程序中创建任何新的线程，线程也在后台运行着。 一个Java应用总是从main\(\)方法开始运行，mian\(\)方法运行在一个线程内，它被称为主线程。 一旦创建一个新的线程，就产生一个新的调用栈。     线程总体分两类：用户线程和守护线程。 （[http://blog.csdn.net/lvxiangan/article/details/54145846](http://blog.csdn.net/lvxiangan/article/details/54145846)）   

 当所有用户线程执行完毕的时候，JVM自动关闭。但是守候线程却不独立于JVM，守护线程一般是由操作系统或者用户自己创建的。

  
3、Java创建和销毁线程有哪些开销？各个开销耗时多么？哪个开销最耗时？关于时间，创建线程使用是直接向系统申请资源的，这里调用系统函数进行分配资源的话耗时不好说。关于资源，Java线程的线程栈所占用的内存是在Java堆外的，所以是不受java程序控制的，只受系统资源限制，默认一个线程的线程栈大小是1M（当让这个可以通过设置-Xss属性设置，但是要注意栈溢出问题），但是，如果每个用户请求都新建线程的话，1024个用户光线程就占用了1个G的内存，如果系统比较大的话，一下子系统资源就不够用了，最后程序就崩溃了。PS：同样的道理在java程序中也不要随意开启新的线程，特别是高频业务尽量使用线程池，不然很容易导致内存不足，程序崩溃的问题。对操作系统来说,创建一个线程的代价是十分昂贵的, 需要给它分配内存、列入调度,同时在线程切换的时候还要执行内存换页,CPU 的缓存被清空,切换回来的时候还要重新从内存中读取信息,破坏了数据的局部性。  
  


![](../.gitbook/assets/image%20%282%29.png)

### 三、网络资源

| 编号 | 题目 | 网址 | 备注（关键词）必看&gt;重点&gt;理解&gt;了解   ??（未理解） |
| :--- | :--- | :--- | :--- |
|   1 | 深入JVM剖析Java线程堆栈 | [http://www.jb51.net/article/69258.htm](http://www.jb51.net/article/69258.htm) | 了解 |
|   2 | Executor框架详解 | [https://www.cnblogs.com/study-everyday/archive/2017/04/20/6737428.html](https://www.cnblogs.com/study-everyday/archive/2017/04/20/6737428.html) | 了解 |
|   3 | 如何提高Web服务端并发效率的异步编程技术 | [http://blog.jobbole.com/80404/](http://blog.jobbole.com/80404/) | 理解 |
|   4 | SpringBoot异步编程框架Akka | [https://baijiahao.baidu.com/s?id=1587478307893788185&wfr=spider&for=pc](https://baijiahao.baidu.com/s?id=1587478307893788185&wfr=spider&for=pc) |   |
|   5 | 深入浅出多线程（视频） | [https://www.imooc.com/video/4618/0](https://www.imooc.com/video/4618/0) |   |
|   6 |   |   |  |



