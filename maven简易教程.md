1Maven概述

解压到自定义目录，然后环境变量Path追加路径为Maven安装目录的bin目录下，即可。
cmd命令中运行 mvn -v 
出现版本等信息则表示安装成功
需要注意的是Maven是一个java程序，如果想他被运行，那么需要有java的运行环境，所以也就是必须安装JDK等。

3pom.xml(Project Object Model)文件

在用Maven创建java项目模版时，会使用pom.xml文件来描述这个工程的信息，包含该工程的一些信息以及依赖的一些jar包的
信息
pom.xml像.NET中的web.config一样，会多层继承。并且pom.xml有一个父的xml文件和他合并执行。我们把这个父的pom.xml叫做
Super POM。该文件定义了一些公共信息。通过命令可以下载这个Super POM。该文件定义了一些公共信息。通过命令可以查看这个Super POM
mvn help:effective-pom
命令执行完会在控制台会输出Super POM和你本地的pom.xml合并后的xml，这个合并完内容后的xml就是一个完整的pom.xml
pom.xml文件中的一些配置节点就不赘述了。这个网上有大把。



4Maven构建机制

Maven构建机制的就是利用Maven来将程序编译，打包，发布的过程。Maven管理了项目依赖，所以他在构建过程中会自动包含那些依赖的
项目。
Maven的构建周期里面，由4个阶段组成（资源拷贝，编译，打包，安装）
Maven提供了3个标准的构建生命周期，可以直接使用，这3个标准的构建是（clean,default(or build),site），他们每个构建周期内都有那4个
标准构建阶段。三个标准构建的不同阶段分别如下：
```
clean（用来删除构建好的文件，一般重新打包前会使用）
pre-clean			清除前
clean				正在清除
post-clean		清除之后

default(默认构建，用来打包)
validate																	检查工程配置是否正确，完成构建过程的所有必要信息是否能够获取到。
initialize																	初始化构建状态，例如设置属性。
generate-sources														生成编译阶段需要包含的任何源码文件。
process-sources														处理源代码，例如，过滤任何值（filter any value）。
generate-resources													生成工程包中需要包含的资源文件。
process-resources													拷贝和处理资源文件到目的目录中，为打包阶段做准备。
compile																	编译工程源码。
process-classes														处理编译生成的文件，例如 Java Class 字节码的加强和优化。
generate-test-sources												生成编译阶段需要包含的任何测试源代码。
process-test-sources												处理测试源代码，例如，过滤任何值（filter any values)。
test-compile																编译测试源代码到测试目的目录。
process-test-classes													处理测试代码文件编译后生成的文件。
test																			使用适当的单元测试框架（例如JUnit）运行测试。
prepare-package														在真正打包之前，为准备打包执行任何必要的操作。
package																	获取编译后的代码，并按照可发布的格式进行打包，例如 JAR、WAR 或者 EAR 文件。
pre-integration-test													在集成测试执行之前，执行所需的操作。例如，设置所需的环境变量。
integration-test														处理和部署必须的工程包到集成测试能够运行的环境中。
post-integration-test												在集成测试被执行后执行必要的操作。例如，清理环境。
verify																		运行检查操作来验证工程包是有效的，并满足质量要求。
install																		安装工程包到本地仓库中，该仓库可以作为本地其他工程的依赖。
deploy																		拷贝最终的工程包到远程仓库中，以共享给其他开发人员和工程。

site(用来创建新的报告文档、部署站点)
pre-site
site
post-site
site-deploy	生成站点说明阶段
```

可以使用mvn命令直接驱动标准构建周期，如
mvn clean
mvn default
mvn site
这样就会开始构建整个生命周期里的过程构建。当然也可以从一个周期内的一个阶段开始构建，如
mvn compile
当一个阶段是通过 Maven命令调用，例如：mvn compile，即代表该阶段之前的阶段至该阶段为止按顺序执行。
注意：构建命令只能在pom.xml文件所在文件夹执行

5中央仓库

打开maven安装目录下的conf文件夹，该文件夹下有一个settings.xml文件，该文件的maven的基本配置文件，里面可以设置中央仓库的代理信息
（因为国内访问外国网站可能有墙，所以可能要使用代理）设置代理的内容定义在<proxies>节点内，也可以直接把中央仓库替换成如阿里云的
镜像地址：http://maven.aliyun.com/nexus/content/groups/public/，
通过<mirrors>节点下的
<mirror>
	<id>mirrorId</id>
	<mirrorOf>repositoryId</mirrorOf>
	<name>Human Readable Name for this Mirror.</name>
	<url>http://my.repository.com/repo/path</url>
</mirror>
节点重新定义中央仓库的地址，此时可以换成阿里云的
这样获取项目的时候就从镜像地址获取了，中央仓库是maven官方维护的一个工程仓库（他的镜像由阿里云同步过来），如果没有你要的工程，则可以使用远程仓库。
上述方法是全局的，也可以只在一个pom.xml里配置如下
<repository>  
      <snapshots>  
      <enabled>false</enabled>  
      </snapshots>  
      <id>central</id>  
      <name>Maven Repository Switchboard</name>  
      <url>http://repo1.maven.org/maven2</url>  镜像地址
 </repository>  

本地仓库默认在你本地的用

6本地仓库

settings.xml文件的<localRepository>节点定义了本地的项目包(jar包)存储的路径，jar包被下载的本地之后，以后有用到同样的项目包将优先从本地
仓库获取。只有声明的项目包不存在于本地仓库时，才会从远程仓库获取，并且获取好后会加入到本地仓库的路径，以便下次使用。

7远程仓库

在中央没有仓库的工程，如果你有额外的托管仓库（称为远程仓库），则可以配置远程仓库的信息。在pom.xml文件中加入如下配置：
```
<repositories>
    <repository>
      <id>java.net</id>
      <url>https://maven.java.net/content/repositories/public/</url>
    </repository>
 </repositories>
```
此时，项目工程也可以从远程仓库获取，一个工程依赖的获取顺序是：
本地仓库
中央仓库（中央仓库获取后则放到本地）
远程仓库

8将一个自定义工程加入到本地仓库

使用如下命令，将一个工程加入本地仓库
```
mvn install:install-file 
-Dfile=<文件绝对完整路径> 
-DgroupId=<组织id,在引用该工程时会用到> 
-DartifactId=<项目id,在引用该工程时会用到>
-Dversion=<版本号,如2.3> 
-Dpackaging=jar
实例：
mvn install:install-file 
-Dfile=C:\Users\Administrator\Desktop\maven\dom4j-1.6.1.jar 
-DgroupId=com.dom4j 
-DartifactId=dom4j 
-Dversion=1.6.1 
-Dpackaging=jar
```
(为阅读，格式化了命令，实际情况是参数之间有空格间隔）
安装成功之后，会显示加入到本地库的工程路径

9使用Maven创建工程

使用如下命令创建一个Java工程,Maven本身提供了很多种java工程的模版
```
mvn archetype:generate
-DgroupId=<组织id,一般为一个网站倒过来写，如com.3vjia.gz>
-DartifactId=<项目id> 
-DarchetypeArtifactId=<模版类型，有多种给定值，如maven-archetype-webapp代表web工程,maven-archetype-quickstart代表普通java工程>
-DinteractiveMode=false
-DarchetypeCatalog=internal
实例：
mvn archetype:generate -DgroupId=com.jj  -DartifactId=jjMaven  -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false -DarchetypeCatalog=internal
```
该命令执行跟网速有关，因为要从远程获取模板，可以将-DarchetypeCatalog参数设置为local，然后去http://repo1.maven.org/maven2/archetype-catalog.xml
下载模版文件放到.m2\repository\org\apache\maven\archetype\archetype-catalog\<版本号>\即可。
注意：创建Maven工程的目录不要预先放pom.xml文件，会报错。因为创建项目模版的时候会默认创建pom.xml，并引入一些基本依赖
注意：使用Maven模板创建的一些工程可能引用的东西比较旧，这么时候就需要手动调整，例如创建的web工程中的web.xml中对Servlet的版本可能过低，这时就需要手动改


10Maven工程文件结构

大致结构如：
```
maven-archetype-quickstart
|-src
   |---main
   |-----java 放源代码
   |-------com
   |---------yiibai   
   |-----------App.java
   |---test|-----java 放测试工程源代码
   |-------com
   |---------yiibai
   |-----------AppTest.java
   |-pom.xml

maven-archetype-webapp
src
	main
		java			源代码
		resource	.class文件，会自动复制到classes文件夹
		filters		资源过滤文件
		webapp	站点根目录
		assembly	组件的描述配置（如何打包）
		config		配置文件
```


11Maven编译和打包

在pom文件中可定义一些Maven插件的参数，例如Maven的一个编译插件可以指定JDK的版本，在pom.xml中添加如下配置
```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-compiler-plugin</artifactId>
	<version>2.3.2</version>
	<configuration>
		<source>1.7</source>
		<target>1.7</target>
		<!--
			其他不用改，就改这个版本号就可以了，默认是JDK1.4
			当然Maven还可以配置他的其他插件，比如Tomcat的等
		-->
	</configuration>
</plugin>
```

Maven还有很多其他插件，都可用配置在<plugins></plugins>内。

使用命令
mvn package
根据第4点的介绍可知其实这个命令是一个执行一个构建阶段。
打包当前的工程，至于打成什么样的包根据pom.xml文件中的定义得出，如每个pom.xml文件都会有描述本工程信息的节点，一般在文件的顶部，如
```
<project>
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.yiibai</groupId>
	<artifactId>NumberGenerator</artifactId>	
	<packaging>jar</packaging>	
	<version>1.0-SNAPSHOT</version>
</project>
```

打包之后的文件在pom.xml文件平级的target文件夹中
注意：构建命令必须在pom.xml文件目录执行


