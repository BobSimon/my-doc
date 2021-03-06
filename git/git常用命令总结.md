1Git和SVN区别

Git是一个分布式版本控制器，SVN是集中式的。每一个使用Git管理的项目都是一个独立的版本库，
Git版本号在本地维持
安装git windows下载地址
https://git-for-windows.github.io/

笔记须知：

命令参数须知

<> 必选参数

[] 可选参数


版本号

git为每一个文件的提交都记录一个版本号。
即使你一次提交一个文件夹的东西，下面N个文件，也不会是一个版本号。而是每个文件一个版本号


GIT区域

git每一个分支都有三个区域
工作区（当前操作文件的区域）
暂存区（待提交区，即git add操作后的区域）
版本区（提交成功后的历史版本区，存放了所有文件的每个版本）

2添加目录仓库（该目录下的所有文件都会被版本跟踪）
打开git客户端，使用cd命令移动到指定目录，使用如下命令
git init
输出提示：Initialized empty Git repository in "你的路径"
该目录作为了仓库

3添加和提交文件
git add <文件名，包含后缀名> 
将该文件加入仓库。

git add -u
将所有变动过的文件加入到暂存区

git add -A
将本地删除的文件和新增的文件加入到暂存区


git commit -m <提交备注>
将git add命令指定的文件提交，必须写提交备注，注意必须写双引号，
提交后将会有提示，包含版本号，分支等信息

修改提交备注
git rebase -i <版本号> 
该命令执行后，会弹出一个编辑窗口，操作完之后关闭窗口
输出以下：Successfully rebased and updated refs/heads/master.
提示则成功，剩下的在push到远程，注释即修改成功
该命令用于修改已push到远程的提交注释

git commit --amend -m <新备注>
该命令用于已提交到暂存区，还未push到远端

4查看工作区被修改的文件
git status
执行之后会列出被改动的文件

5查看文件的修改
git diff [版本号|HEAD]  [文件名，包含后缀名]
比较文件和该文件指定版本号（没有指定版本号，则最后一个版本号）的差异

比较工作区和暂存区
git diff
比较的是工作区与暂存区的差异

比较工作区和版本区（把参数补上）
git diff [版本号|HEAD]  [文件名，包含后缀名]

比较暂存区和版本区
git diff --cached [版本号|HEAD]  [文件名，包含后缀名]



6查看提交日志
git log 
执行后输出提交日志（按时间从近到远排序，包含提交时输入的备注，版本号(commit后面跟着的乱码一样的字符串，和SVN不一样,SVN是数字自增)，日期等）
可选参数用来格式化输出，如果你不想看的很杂乱无章的话

7还原工作区和暂存区
还原工作区
git checkout --<文件名>			如果工作区改动了，但是没提交，可用这个还原。

还原暂存区(撤回已add操作)
git reset HEAD					该命令代表用版本库覆盖暂存区，所以暂存区也就没有“新”东西了。

8从版本区还原
git reset --hard <版本号|HEAD^>  HEAD表示当前操作的最后一个提交的版本 
^表示上一个版本
^^上2个，
以此类推，嫌长可以写HEAD~100,表示当前版本的前100个版本
执行之后输出回到之后的版本的信息
注意还原是基于版本号的，所以请对应要还原文件的版本号就可以了，除了使用HEAD^变量，还可以直接使用版本号

9命令历史（相当于操作记录）
git reflog
该命令执行后会列出所有你在git执行过的命令和参数。相当于一个操作记录，通过这个记录，也可以用来辅助还原，帮助你在
21世纪，19世纪，25世纪等等这些版本里面跳来跳去。

10撤销修改（工作区）
只要你修改了文件没提交，git都会将文件表示文已修改，但是突然你不想修改了。可以撤回修改。
git checkout -- [文件名，包含后缀名]
该命令相当于是用版本库里的版本替换工作区的版本，无论工作区是修改还是删除，都可以“一键还原”。

11撤回暂存区内容到工作区（退回git add操作，撤回的是暂存区的）
git reset HEAD <文件名，包含后缀名>

当前工作的目录是工作区，如果你修改了文件，你会使用
git add 命令加入到暂存区
git commit 命名提交到分支
如果已经加入了暂存区，但是不想提交了，要退回工作区，可以使用
git reset HEAD <文件名，包含后缀名>

12删除文件和文件夹
git rm <文件名，包含后缀名>
git commit -m <提交备注>

git rm -r <文件夹名>
git commit -m <备注>

该组合命令删除一个文件
不能同时提交删除和新增的内容。如果有删除内容即要马上提交

13远程仓库
不可能就真的把所有版本都放在本地的，那就是一个单机的版本控制软件。GIT可以从远程服务器获取文件，将本地的同步到远程服务器
这样别的用户也可以从远程服务器下载，然后开启自己本地的版本控制。这才是真正的分布式版本控制
要使用远程仓库必须创建密钥，密钥又分公钥和私钥。使用如下命令创建
ssh-keygen -t rsa -C <你的邮箱> 邮箱随便填，反正只是根据这个生成密钥而已
输入命令之后一路回车到完成，默认会创建在C盘下的某个地方，注意看输出的提示，本机创建在C:\Users\Administrator\.ssh文件夹下
该文件夹下有2个文件id_rsa和id_rsa.pub。分别是私钥和公钥的
公有密钥用来设置在远程仓库上做验证，然后也可以把公钥分发给其他项目成员，这样他们就能方便的从本地将文件同步至远端，多人协作就这样完成了

14从远程仓库克隆（也就是远程下载）
git clone <git地址>  远程仓库有https协议的地址也有git协议的 git更高效

15将本地分支同步到远程仓库的指定分支
git push <远程仓库地址,git协议的> [本地分支]:[远程分支]

16更新远程分支至本地
git pull <远程仓库地址,git协议的> [本地分支]:[远程分支]

17创建分支和切换分支
git checkout -b <分支名>
-b参数相当于创建并切换到该分支，是以下两条命令的集成
git branch <要创建的分支名>
切换分支
git checkout <要切换的分支名>
切换到了一个分支之后，就可以自由提交删除了。
在你切换分支的时候，文件会自动会到那个分支下的版本。

18查看分支
git branch -a
列出所有分支，并且会指明当前的分支（前面有一个*号）

git branch -l
查看当前本地分支

git branch -r
查看远程分支

19删除分支
git branch -D <分支名称>

20配置级别和全局设置
git 有3个配置级别
当前版本库
全局配置[--global]
应用级配置[--system]


git config [配置级别，--global | --system] <配置参数 user.name>		<用户名>
git config [配置级别，--global | --system] <配置参数 user.email>	<邮箱>
有了这些就会在log里面标识出是谁提交的了

21工作区暂存和恢复
暂存工作区
git stash

恢复工作区
git stash pop


22合并指定提交
git cherry-pick [提交id]

合并成功后，会直接在提交列表里，此时你只需要push就好了
需要注意的时，合并与被合并的所在分支，如果需要将B分支合并进A分支
那么请先切换到A分支，运行如上命令

23
将代码强制重置至特定版本

git reset --hard <远程仓库地址> <分支名> 
