**一，apidoc参数格式**

参数说明

**{}为必填或枚举参数
[]为选填参数，但是我建议你按照我的格式都填了，因为我已经是按最小参数编写的该教程文档**

@api 					{请求类型[get|post]} [接口地址] [接口名称，描述等]
@apiParam				{参数类型[String,Number,Boolean,Object等}	[字段]	[字段名称]	[描述]

@apiGroup				[分组名称(可以用来生成归类接口类别，会生成导航，只能英文)]

@apiSuccessExample		{json}	[结果说明,一个json串（请先换行）]


参数真实案例
`
@api {post} /getUserLoginShortCutCode.html 获取快捷登录码  
@apiGroup login  
@apiParam {String} ticket 令牌  
@apiSuccessExample {json} Success-Response:
{ "Code": "令牌" }
`

**二，生成步骤**
1下载node

2进入你的工作目录（看你喜欢在哪个目录）

3在该目录下运行cmd或者PowerShell
执行：
npm init
初始化一个node项目

4执行
npm install apidoc
安装 apidoc

5创建一个apidoc的配置文件(apidoc.json)
输入如下内容
`
{
  "name": "至爱商城接口文档",
  "version": "0.1.0",
  "description": "至爱商城-至爱数据中心接口",
  "title": "至爱商城-至爱数据中心接口",
  "url" : "http://www.i72.com",					//正式环境地址
  "sampleUrl":"http://10.4.2.11:8000"			//测试环境地址		必填
}
`
[apidoc.json](/uploads/56554681d5cbba056710c3e862899245/apidoc.json)   可直接下载这个模板改

6使用apidoc生成文档

**apidoc -i [源码文件夹路径（会自动遍历子文件夹）] -o [生成文件夹路径(需先创建好)]**

使用iis或者 apache 直接部署生成路径即可

三，集成
与开发工具的集成（比如IDEA）
编辑IDEA的方法注释模板，将如上参数输入
File->settings->File and Code Templates

配置如下注释模板

`@apiGroup    分组名，必须英文
@api         {post} url  接口名称
@apiSuccessExample {json} result:
                        {"json":"你的json"}
@apiParam: {String|Number} 字段 字段名 描述
@return: $return$
@auther: $user$
@date: $date$ $time$` 


 
按你自己喜欢的方式来。反正符合apidoc的要求就行
触发快捷键也可以按你喜欢的来

编辑一个定时cmd
这样文件只要更新了，就会更新文档。全自动

**示例：**
`D:\node-v8.12.0-win-x64>apidoc -i D:\JavaCode\Dev\zaShop\kstore_v2 -o doc`
 
**一，apidoc参数格式**

参数说明

**{}为必填或枚举参数
[]为选填参数，但是我建议你按照我的格式都填了，因为我已经是按最小参数编写的该教程文档**

@api 					{请求类型[get|post]} [接口地址] [接口名称，描述等]
@apiParam				{参数类型[String,Number,Boolean,Object等}	[字段]	[字段名称]	[描述]

@apiGroup				[分组名称(可以用来生成归类接口类别，会生成导航，只能英文)]

@apiSuccessExample		{json}	[结果说明,一个json串（请先换行）]


参数真实案例
`
@api {post} /getUserLoginShortCutCode.html 获取快捷登录码  
@apiGroup login  
@apiParam {String} ticket 令牌  
@apiSuccessExample {json} Success-Response:
{ "Code": "令牌" }
`

**二，生成步骤**
1下载node

2进入你的工作目录（看你喜欢在哪个目录）

3在该目录下运行cmd或者PowerShell
执行：
npm init
初始化一个node项目

4执行
npm install apidoc
安装 apidoc

5创建一个apidoc的配置文件(apidoc.json)
输入如下内容
`
{
  "name": "至爱商城接口文档",
  "version": "0.1.0",
  "description": "至爱商城-至爱数据中心接口",
  "title": "至爱商城-至爱数据中心接口",
  "url" : "http://www.i72.com",					//正式环境地址
  "sampleUrl":"http://10.4.2.11:8000"			//测试环境地址		必填
}
`
[apidoc.json](/uploads/56554681d5cbba056710c3e862899245/apidoc.json)   可直接下载这个模板改

6使用apidoc生成文档

**apidoc -i [源码文件夹路径（会自动遍历子文件夹）] -o [生成文件夹路径(需先创建好)]**

使用iis或者 apache 直接部署生成路径即可

三，集成
与开发工具的集成（比如IDEA）
编辑IDEA的方法注释模板，将如上参数输入
File->settings->File and Code Templates

配置如下注释模板

`@apiGroup    分组名，必须英文
@api         {post} url  接口名称
@apiSuccessExample {json} result:
                        {"json":"你的json"}
@apiParam: {String|Number} 字段 字段名 描述
@return: $return$
@auther: $user$
@date: $date$ $time$` 


 
按你自己喜欢的方式来。反正符合apidoc的要求就行
触发快捷键也可以按你喜欢的来

编辑一个定时cmd
这样文件只要更新了，就会更新文档。全自动

**示例：**
`D:\node-v8.12.0-win-x64>apidoc -i D:\JavaCode\Dev\zaShop\kstore_v2 -o doc`
 
