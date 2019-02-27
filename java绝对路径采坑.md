1JAVA JVM是一个跨平台运行的虚拟机环境，JAVA字节码运行在这个容器里。

2WINDOWS和mac和linux的路径连接符不一样，windows是\, mac和linux是/

3请用File.separator常量来代替直接字面量书写"\"或"/"，他在不同的系统之下就是那个系统的路径连接符。
所以能解决跨平台的问题。这个问题不单是JAVA有，PHP，NODE等都有，只要是要在WINDOWS，又要在LINUX运行的
技术实现，都会提供这样的常量供开发者使用。

4关于url路径，它是国际标准，所以他在任何一个web程序中体现的意义基本上是一致的。不管实现这个web本身的技术体系如何。

5Servlet中获取rootPath的坑

`String rootPath = request.getServletContext().getRealPath(File.separator);`

**WINDOWS下结尾会有\(路径连接符)
而mac,linux不会有，开发者请注意**

6最后
路径连接符请使用File.separator常量。如
string path=root+File.separator+"temp";

**不要使用**

string path=root+"\\"+"temp";**(LINUX下报错，因为他的是/)**
