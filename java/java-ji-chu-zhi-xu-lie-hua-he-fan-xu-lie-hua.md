# Java基础之序列化和反序列化

## 一、知识点罗列

* 介绍
* 应用场景
* 如何实现序列化和反序列化
* **二、介绍**

序列化分为两大部分：序列化和反序列化。序列化是这个过程的第一部分，将数据分解成字节流，以便存储在文件中或在网络上传输。反序列化就是打开字节流并重构对象。 对象序列化不仅要将基本数据类型转换成字节表示，有时还要恢复数据。恢复数据要求有恢复数据的对象实例。ObjectOutputStream中的序列化过程与字节流连接，包括对象类型和版本信息。反序列化时，JVM用头信息生成对象实例，然后将对象字节流中的数据复制到对象数据成员中。
