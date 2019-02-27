利用Nginx搭建集群环境，通过部署多实例站点，快速切换nginx代理的站点来实现。

**1.Nginx集群环境示例图：**
![nginx集群图片1](/nginx集群图片1.png)



**2.针对至爱商城项目，研究分析。**

2.1，单台服务器站点端口通过Nginx配置代理到其他实例站点的端口，具体单台服务运行Tomcat多实例参考：https://github.com/BobSimon/my-doc/blob/master/tomcat%E9%85%8D%E7%BD%AE%E5%A4%9A%E5%AE%9E%E4%BE%8B.md

2.2，当需要更新时，先将集群中的一个Tomcat实例隔离出来。用这个实例来更新需要发布的版本，更新完成后，再将该站点加入集群，同时将其他站点隔离出来。隔离出来的集群同步更新后，再加入到集群中。


**3.部署Nginx服务**

3.1， 在Nginx官网（http://nginx.org/en/download.html）   下载最新的稳定版本。修改配置文件，
3.5配置：
![3.5服务器Nginx配置](/3.5服务器Nginx配置.png)

3.2， 配置说明

     A,配置中的proxy_pass  http://zaLocalServer; 代表需要访问的集群服务。

     B,zaLocalServer 配置了2个端口，代表需要2个Tomcat实例。
    upstream zaLocalServer {	
		server localhost:8002  weight=1;
		server localhost:8003  weight=1;
    }



**4.相关命令**

Nginx命令：

启动：/opt/nginx/sbin/nginx -c /opt/nginx/conf/nginx.conf

重启：/opt/nginx/sbin/nginx -s reload

停止：/opt/nginx/sbin/nginx -s stop 
