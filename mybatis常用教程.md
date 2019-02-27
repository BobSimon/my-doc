1mybatis简介

简而言之就是mybatis轻量级，灵活可控。
他有如下jar包
```
MyBatis								核心jar包
MyBatis Spring						和spring 整合时用的
MyBatis Generator Core				DAO层生成工具
MyBatis Spring Boot Starter			和spring boot整合时用的
MyBatis Plus						mybatis中的linq to sql
```

2mybatis里的重要组成

```
SqlSessionFactoryBuilder：构造器，用来创建SqlSessionFactory
SqlSessionFactory：SqlSession工厂，被SqlSessionFactoryBuilder创建。建议SqlSessionFactory为singleton
SqlSession：命令器，用来执行SQL语句，被SqlSessionFactory创建，建议为property或request
SQL Mapper：映射器，定义了SQL语句的地方
```

创建顺序
SqlSessionFactoryBuilder->SqlSessionFactory->SqlSession->SQL Mapper->执行语句

3mybatis核心配置文件

mybatis配置文件分为核心配置文件和映射器(mapper)配置文件。本节只介绍核心配置文件，并且只介绍xml版的配置
mybatis需要定义一个配置文件，如定义mybatis-config.xml，内容节点如下
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <!-- 声明和dtd必须引入 此部分照抄就好了 -->
<configuration>
	<properties>
		<!-- 定义引入外部配置文件 或直接定义配置信息的key/value -->
	</properties>
	
	<settings>
		<!-- 一些基本配置 -->
	</settings>
	
	<typeAliases>
		<!-- 定义各种java类型的短名（别名） -->
	</typeAliases>
	
	<typeHandlers>
		<!-- 数据类型转换处理器，这里可以定义自己的转换规则，如怎么把一个java中的String，转换为数据库里的varchar还是varchar2 -->
	</typeHandlers>
	
	<objectFactory>
		<!-- mybatis将数据库记录转换为POJO处理器，可以定义自己的转换规则 -->
	</objectFactory>
	
	<plugins>
		<!-- 定义插件，可以拦截和切入mybatis运行时的一些行为，相当于一种事件 基本上不会用到 -->
	</plugins>
	
	<!--
		一般情况下，typeAliases，typeHandlers，objectFactory节点不需要配置。直接跳过就好
	-->
	
	<environments>
		<!-- 定义mybatis的运行环境，可多个 -->
		<environment>
			<transactionManager>
				<!-- 定义事物的规则 这个很重要 -->
			</transactionManager>
			
			<dataSource>
				<!-- 定义数据源 这个很重要 -->
			</dataSource>
		</environment>
		
		<!-- 可写多个environment标记，定义不同的环境 -->
	</environments>
	
	<databaseIdProvider>
		<!-- 定义数据库厂商等配置信息，用来切换不同数据库下执行不同的sql语句，基本上不会用到 -->
	</databaseIdProvider>
	
	<mapper>
		定义映射器
	</mapper>
	
	
	<!--
		这个配置文件中的节点请按顺序书写。不需要的可以跳过不写。
	-->
	
</configuration>
```

4<properties>节点配置

配置方法一
```
<properties>
	<property name="[key]" value="[value]"></property>
</properties>
```

配置方法二
```
<properties resource="你的properties文件路径">

</properties>
```

配置方法三
直接在代码中通过API设置，这个方式不做介绍，因为很少用

优先级
如果三个配置方法都写了，加载顺序为配置一>配置二>配置三
同名key会被覆盖，所以后加载的优先级高

配置的key/value怎么用
这些key/value，其实无非就是配置数据库连接信息的配置文件。所以他会被
<dataSource>节点用到。如
```
<properties>
	<property name="url" value="数据库地址"></property>
</properties>
<dataSource type="POOLED">
	<property name="url" value="${url}"></property>
	<!--
		注意这里使用了${}，这是spring表达式的语法。但是检查发现mybatis的包并没有依赖spring包
		可能是他内部也实现了这样的占位符替换法。
	-->
</dataSource>
```

5<settings>节点配置

该节点的配置就有点多了。只介绍几个用的到的。其他的请使用时查资料
```
<settings>
	<!-- 是否开启缓存 默认值true -->
	<setting name="cacheEnabled" value="true/false" />
	
	<!-- 决定mybatis按何种规则映射数据到POJO，NONE不自动映射，PARTIAL(默认值)只映射不嵌套的结果集，FULL映射任意结果集（任意嵌套也可以映射，性能低） -->
	<setting name="autoMappingBehavior" value="NONE/PARTIAL/FULL" />
	
	<!-- 默认的执行器，表示如何对待sql语句 将决定JDBC使用 Statement或PreparedStatement对象等 默认值为SIMPLE,建议为REUSE -->
	<setting name="defaultExecutorType" value="SIMPLE/REUSE/BATCH" />
	
	<!-- jdbc connection 超过时间 没有默认值 -->
	<setting name="defaultStatementTimeout" value="秒钟数">
	
	<!-- 是否开启经典严谨的数据库标准命名规则到javaBean命名规则的自动映射 如 数据库 A_COLUMN 到 java的 a_Column 默认值false 慎用 因为你的命名可能不规范 -->
	<setting name="mapUnderscoreToCamelCase" value="true/false">
		
</settings>
```

6<typeAliases>配置节点和别名

在mybatis很多地方会使用到一些参数需要指定类型。指定类型的时候可以写全限定的类名，也可以写别名
别名需要在<typeAliases>节点中定义。
mybatis里已经为我们预定义了很多别名。如别名int，对应的是java.lang.Integer。等等
我们可以自己定义别名
```
<typeAliases>
	<typeAlias alias="我是别名" type="我是一个类全限定名"></typeAlias>
</typeAliases>
```
在mybatis里，需要指定类型的地方，可以全限定类名和别名混用。很方便。

7<typeHandlers>配置节点

mybatis将参数设置为jdbc类型时，和将数据库类型转换为pojo对象中属性的类型时都会需要用到类型转换器
mybatis已经预定义了很多类型转换器。如果要自定义自己的类型转换器，可以实现TypeHandler<T>，这个接口是泛型的。
然后将自己的实现类注册到配置中
```
<typeHandlers>
	<typeHandler jdbcType="JDBC类型名" javaType="类型限定名或别名" handler="实现类全限定名" />
</typeHandlers>
```
你以为配置完他就自动生效了吗。其实不是的，他需要在mapper中使用的时候特别指定，这个指定方式后续会介绍

8<objectFactory>配置节点

mybatis在将ResuleSet映射为POJO时，需要创建POJO的实例。做这个工作的就是objectFactory工厂。mybatis默认的
实现是org.apache.ibatis.reflection.factory.DefaultObjectFactory。
如果要自定义自己的，可以实现ObjectFactory接口或直接集成DefaultObjectFactory类覆盖它的方法。一般为了方便
都是继承DefaultObjectFactory类覆盖它的方法去实现自己的业务。
```
<objectFactory type="自定义对象工厂全限定名">
	<property name="自定义对象工厂名称" value="自定义对象工厂类名">
</objectFactory>
```

9<environment>节点

该节点配置不同数据源。他下面有其他子节点，分别配置事务和数据源的信息。
```
<environment>
	<!--
			JDBC		这个配置就是直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域。
			MANAGED		这个配置几乎没做什么。它从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期
			
			比如一个没有使用spring容器的项目，他可能用的是Tomcat这个容器，此时可以就需要采用JNDI的一些方式去关闭事务了。
			
			如果你正在使用 Spring + MyBatis，则没有必要配置事务管理器， 因为 Spring 模块会使用自带的管理器来覆盖前面的配置。
			这时按spring的配置去配置就好了。
	-->
	<transactionManager type="JDBC/MANAGED">
		<!-- 为JDBC模式时的配置项,MANAGED 无须理会，因为基本上你不会用到 -->
		<property name="autoCommit" value="true/false">
	</transactionManager>
	
	<!--
			UNPOOLED	无连接池
			POOLED		有连接池，mybtais自己实现的
			JNDI		容器注入
	-->
	<dataSource type="UNPOOLED/POOLED/JNDI">
		<!--
			这里面全是定义<property>节点，用来配置key/value
			如果采用的是UNPOOLED/POOLED/JNDI，则需要按规定key名类配置值。
			如果使用其他连接池，例如阿里druid，则按自己喜好去命名配置key/value
			因为这些key/value会被你自己的实现类接管。这样你就可以自由处理这些配置文件，而不像mybatis预设的那些实现一样，需要用他规定的key去配置
			这里的配置信息，其实就是配置一个数据源用到的信息。
			无非就是数据库地址URL,驱动全类名，数据库账号密码等等。
		-->
	</dataSource>
	
<environment>
```

11<environment>配置druid，接入自定义数据源

实现mybatis的DataSourceFactory接口
```
public class MybatisDruidDataSourceFactory implements DataSourceFactory {

    private Properties props;

    @Override
    public void setProperties(Properties properties) {
		/*
			获得配置文件，其实就是
				<dataSource>
					<property />
				</dataSource>
			里配置好的
		*/
        this.props = properties;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource source = new DruidDataSource();
		
		//因为可以通过编程指定key,所以key怎么定完全看你需要
        source.setDriverClassName(this.props.getProperty("driver"));
        source.setUrl(this.props.getProperty("url"));
        source.setMaxActive(new Integer(this.props.getProperty("maxActive")));
        source.setUsername(this.props.getProperty("username"));
        source.setPassword(this.props.getProperty("password"));
        source.setInitialSize(new Integer(this.props.getProperty("initialSize")));
        try {
            source.init();
        }catch (Exception e){
            return null;
        }
        return source;
    }
}

<dataSource type="mybatisdemo.MybatisDruidDataSourceFactory">

	<!--
		这些配置的key 如果是采用mybatis本身实现的dataSource，则必须按他们实现的类去写
		如果是自定义数据源，比如通过接入阿里druid，这时，key随便传。反正到了实现类里面，
		你会根据key去获取，然后重新赋值。
	 -->
	<property name="url" value="jdbc:mysql://127.0.0.1:3306/wmshop?useSSL=true&amp;serverTimezone=GMT%2B8"></property>
	<property name="maxActive" value="10"></property>
	<property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
	<property name="username" value="root"></property>
	<property name="password" value="321123qwe"></property>
	<property name="initialSize" value="5"></property>
</dataSource>
```

12<databaseIdProvider>节点配置

基本上没什么用，就不废话了，总之他只有一个用处。就是你想在不同数据库(oracle,mysql,sql server)中动态切换SQL语句时
可能需要用到

13<mapper>配置

```
	<!-- 
		按xml文件路径配置，引入单个mapper，默认为按类路径，也可以物理文件则加file:// 
		建议按照此方式。
	-->
	<mapper resource="mapper/Goods.xml" />
	
	<!--按类型全限定名 引入单个mapper， -->
	<mapper class="mybatisdemo.dao.GoodsMapper" />
        
	<!-- 
	按包引入，此时xml文件必须和接口的同一个包内，并且需要生成到classes下，否则报错。
	因为按MAVEN项目的结构，只会把resources的放到classes下。所以这里需要特别设置下。
	一般不采用这种方式。

	-->
	<package name="mybatisdemo.dao"></package>
```


14映射器配置文件

最复杂的配置，其实是在映射器文件里。即<mapper>节点里配置的xml文件。这个xml文件
称呼为映射器配置文件，他有如下格式。
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="对应的接口全限定名">
	<select></select>					<!-- 查询语句 -->
	<insert></insert>					<!-- 插入语句 -->
	<update></update>					<!-- 更新语句 -->
	<delete></delete>					<!-- 删除语句 -->
	<parameterMap></parameterMap>		<!-- 参数映射 基本不会用到，也不推荐，可用parameterType属性代替 -->
	<resultMap></resultMap>				<!-- 结果集映射 -->
	<cache></cache>						<!-- 缓存配置 基本不会用到-->
	<cache-ref></cache-ref>				<!-- 缓存引用 基本不会用到-->
	<sql></sql>							<!-- SQL语句片段，可服用加入任何SQL语句中 -->
	
	
	<!--
		这几个节点使用不分前后。
	-->
</mapper>
```

15映射器配置文件中的常用属性

id:节点名称，必须和mapper接口里的方法名对应
parameterType:参数类型，可以是类全限定名，别名，用该属性，千万不要用<parameterMap>节点
timeout:超时时间，单位s

16参数书写格式

```
WHERE Id=#{name}
WHERE Id=#{name,javaType=[可以用别名，全限定名],jdbcType=[只能用JDBC类型，如NUMERIC]}
WHERE Id=#{name,javaType=[可以用别名，全限定名],jdbcType=[只能用JDBC类型，如NUMERIC],typeHandler=[<typeHandler>定义的类型转换器]}

//接口部分
Goods getGoods(Long goodsId);
Goods getGoods(@PARAM("goodsId")Long goodsId,@PARAM("goodsId")Long goodsId2);	//多个参数，其实被处理成了Map
Goods getGoods(GoodsParam param);												//以javabean传递
```


#{}语句,mybatis会为他创建预编译语句，所以不用害怕SQL注入

17<resultMap>节点

该节点用来映射ResultSet语句POJO的关系
```
<resultMap id="">
	<id property="javaBean属性名" column="SQL列名" />		<!-- 主键专属写法 -->
	<result property="javaBean属性名" column="SQL列名" />
</resultMap>
```

18<select>节点

写法一
```
<select id="getProductAll" parameterType="com.i72.Product" resultType="com.i72.Product" >
	<!-- 这里写SQL语句 -->
	
	SELECT * FROM product p WHERE p.Id=#{name}
	
</select>
```

这里需要注意的是resultType属性，如果返回的是列表，那么类型还是写列表元素的类型。而不是什么List等类型

写法二
```
<resultMap id="product">
	<id property="Id" column="id" />
	<result property="goodsName" column="goods_name" />
</resultMap>
<select id="getProductAll" parameterType="com.i72.Product" resultType="product" >
	<!-- 这里写SQL语句 -->
	
	SELECT id,goods_name FROM product p WHERE p.Id=#{name}
	
</select>
```


19<insert>节点

```
<insert id="insertProduct" parameterType="com.i72.Product">
	insert into product(name,num) value(#{productName},#{productNum})
</insert>
```

20<update>节点

同上，差不多

21<delete>节点

同上，差不多

22动态SQL和Mybatis语句

#{}会将参数编译，而${}直接将变量直接输出为SQL语句，所以这种方式可以动态注入语句，但是可能会有风险，最好不要用${}形式的

```
<if test="逻辑表达式">
	<!-- 这里可以写SQL语句 -->
</if>

<choose>

	<when>
	</when>
	
	<otherwise>
	</otherwise>
	
</choose>
这一类型语句相当于switch语句，不过不建议使用

<foreach>
	<!--  循环语句，不建议使用 -->
</foreach>

<trim>,<where>,<set>
```

这几类语句是mybatis中用来辅助生成sql语句时用的，不如说怕你没写 1=1 就进行条件拼接等。如果能注意到这些情况
就不需要使用这些语句。

<bind>
用来辅助SQL语句中的字符串连接，oracle 中字符串拼接是 '||' ,MYSQL 是'+'号,SQL SERVER 是+号
所以你书写语句的时候，可能因为这个差异会有不同写法。
mybatis的<bind>就是帮兼容写法的，他会自动转换为目标数据库的连接符。
推荐使用


23<sql>节点

该节点就是sql语句的片段，你可以用他定义字段的语句，也可以是SQL语句中的任何片段，他通过命令引入到其他节点里去
可以引入到<select>,<update>,<delete>,<insert>等，不过我们一般只把他引入到<select>内，其他节点的场景较少

```
<sql id="field">
	name,age
</sql>
<select>
	select <include refid="field" /> from student s 
</select>

<sql id="field">
	#{tableName}.name
</sql>
<select>
	select 
		<include refid="field">
			<property name="tableName" value="s">
		</include>
	from student s 
	
	<!--
		最后的效果是，select s.name from student s  
		参数注入，取表别名。
	-->
</select>
```

24级联

有隐患，不介绍了

25缓存

介绍下原理，但是不建议使用
mybatis设计了2层缓存，第一层缓存是在SqlSession级别。同一个SqlSession采用一个缓存池
第二层缓存在SqlSessionFactory级，他可以在多个SqlSession中共享。所以他的可用度更高，因为我们的SqlSession的生命周期是跟
请求生命周期走的。
mybatis的缓存策略就是SQL语句本身加参数，如果发现语句和参数都没变的话，就从缓存中去获取。
因为他的缓存都是存在于内存中（虽然有办法可以配到redis），而且失效机制也不好，所以最好不要用

26原理解析

第3节的mybatis核心配置文件，将会用来创建SqlSessionFactory对象
1将第3节的mybatis核心配置文件，通过XMLConfigBuilder来解析
2将解析所有的配置信息都会转为内部的Configuration对象来存储
3使用Configuration的对象来创建SqlSessionFactory
4mybatis提供了SqlSessionFactory的默认实现类，DefaultSqlSessionFactory，所以，实际上是它使用了Configuration对象。
5映射器通过动态代理被SqlSession创建
6映射器执行时，SqlSession先创建Executor对象。
7Executor对象创建StatementHandler
8StatementHanlder，解析语句，将参数传入ParameterHandler
9执行SQL语句，得到结果
10将结果通过ResultHandler，映射为最终的POJO对象

27mybatis-spring集成

前面的介绍不是和spring集成的配置方式，但是mybatis框架本身的配置方式是一致的，这里介绍和mybatis和spring集成的
配置方式，因为spring能力的介入，一些配置的方式可能不一样，但是基本是一样的。
mybatis和spring的集成需要引入一个jar包，这个包是mybatis-spring-x.x.x.jar，这个包是mybatis官方提供的

将数据源配置到SqlSessionFactory
```
<bean id="" class="org.mybatis.spring.SqlSessionFactoryBean">
	<property name="dataSource" ref="" /> <!-- 这里可以使用Druid的数据源，配置上比传统配置简单，传统配置如11节还需要重写特定的类实现 -->
	<property name="configLocation" value="mybatis核心配置文件路径" /> 
	<property name="mapperLocations" value="所有mapper XML文件的位置，可以通配符配置，也可以配置为列表。" />
</bean>
```

配置单个mapper
```
<bean id="" class="org.mybatis.spring.mapper.MapperFactoryBean">
	<property name="mapperInterface" value="" /> <!-- 你的mapper接口类 -->
	<property name="sqlSessionFactory" ref="" />
</bean>
```

没有人去一个一个配置mapper的bean的，太多了。所以有批量扫描配置的方式。

配置MapperScannerConfigurer
```
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
	<property name="basePackage" name="需要扫描的包，分布着mapper接口的地方" />
	
	<property name="sqlSessionFactoryBeanName" value="" />  
	<!-- 
		需要注入的sqlSessionFactory的 id名称，注意这里没用ref 是为了延迟启动 
		如果只配置一个SqlSessionFactory，也就是单数据源时，可以不用该配置
	-->
	
	<property name="annotationClass" value="org.springframework.stereotype.Repository" />	
	<!-- 有什么注解，就扫描注册他为bean，一般指定为@Repository -->
	
</bean>
```
