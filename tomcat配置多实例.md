**起因：单台服务器站点更新时，站点服务不可用。**

tomcat站点更新时，会需要重启,而有的项目重启过程可能需要几分钟，这样会导致这段时间里当前服务器的站点服务不可用。
安装必要步骤：

**1.下载安装jdk，tomcat**

官网下载jdk包https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html;下载Tomcat：https://tomcat.apache.org/download-80.cgi


**2.下载解压到目录,jdk放到java目录，tomcat放到tomcat8-01,复制一份到tocat8-02**

![1下载解压jdk](/uploads/1c92279d7537bf323a915f6ab6a98141/1下载解压jdk.png)


**3.修改系统环境变量：vi /etc/profile,下最后追加配置**


`
export JAVA_HOME=/usr/local/src/java/
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

export CATALINA_BASE=/usr/local/src/tomcat8-01
export CATALINA_2_BASE=/usr/local/src/tomcat8-02

export CATALINA_HOME=/usr/local/src/tomcat8-01
export CATALINA_2_HOME=/usr/local/src/tomcat8-02

export TOMCAT_HOME=/usr/local/src/tomcat8-01
export TOMCAT_2_HOME=/usr/local/src/tomcat8-02
`
![修改profile](/uploads/c9efc4d3de94ed74d3c2637fd538e5bb/修改profile.png)


**4.修改Tomcat实例的端口：**

tomcat8-01可以不改，默认8080；tomcat8-02的修改如下：

修改server.xml配置和第一个不同的启动、关闭监听端口。
修改后示例如下：
　 <Server port="9005" shutdown="SHUTDOWN">　               端口：8005->9005
<!-- Define a non-SSL HTTP/1.1 Connector on port 8080 -->
    <Connector port="9080" maxHttpHeaderSize="8192"　       端口：8080->9080
maxThreads="150" minSpareThreads="25" maxSpareThreads="75"
               enableLookups="false" redirectPort="8443" acceptCount="100"
               connectionTimeout="20000" disableUploadTimeout="true" />
<!-- Define an AJP 1.3 Connector on port 8009 -->
    <Connector port="9009"                                  端口：8009->9009
               enableLookups="false" redirectPort="8443" protocol="AJP/1.3" />


**5.分别执行tomcat8-01和tomcat8-02**

/usr/local/src/tomcat8-01/bin/startup.sh 

/usr/local/src/tomcat8-02/bin/startup.sh 
