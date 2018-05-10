&lt;pre&gt;
springboot + dubbo做的小框架

domaim : 实体类
api : 接口
provider : 服务提供者(xml和注解)
consumer : 服务消费者(xml和注解)

下载ZooKeeper,在conf中创建输入zoo.cfg:

tickTime=2000
#这个地址自定义
dataDir=/javaee/zookeeper/data  
#这个地址自定义
dataLogDir=/javaee/zookeeper/log
clientPort=2181
然后在bin目录中./zkServer.sh start启动

详细安装自行查找...

先说说注解方式(provider):
pom文件依赖:
&lt;dependency&gt;
	&lt;groupId&gt;io.dubbo.springboot&lt;/groupId&gt;
	&lt;artifactId&gt;spring-boot-starter-dubbo&lt;/artifactId&gt;
	&lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;

配置文件:
server.port=8002

spring.dubbo.registry.address=zookeeper://localhost:2181
spring.dubbo.application.name=provider
spring.dubbo.protocol.name=dubbo
spring.dubbo.protocol.port=20884    
spring.dubbo.scan=com.xiaoywqs.provider.service

然后就是service类加入注解(dubbo注解,要指定版本):
@Service(version = "1.0.1")
然后正常启动即可!

(consumer):
pom依赖一样,
配置文件(这个端口是外部调用的,可设置为80):
server.port=8001

spring.dubbo.application.name=consumer
spring.dubbo.registry.address=zookeeper://localhost:2181
spring.dubbo.scan=com.xiaoywqs.consumer.controller

在服务接口上注解(版本要和service一样): @Reference(version = "1.0.1")
正常启动即可!

下面说明一下xml方式(provider):
pom文件:
&lt;!-- dubbo 和 zookeeper 依赖--&gt;
&lt;dependency&gt;
	&lt;groupId&gt;com.alibaba&lt;/groupId&gt;
	&lt;artifactId&gt;dubbo&lt;/artifactId&gt;
	&lt;version&gt;2.6.1&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
	&lt;groupId&gt;org.apache.zookeeper&lt;/groupId&gt;
	&lt;artifactId&gt;zookeeper&lt;/artifactId&gt;
	&lt;version&gt;3.4.10&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
	&lt;groupId&gt;com.101tec&lt;/groupId&gt;
	&lt;artifactId&gt;zkclient&lt;/artifactId&gt;
	&lt;version&gt;0.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
	&lt;groupId&gt;org.apache.curator&lt;/groupId&gt;
	&lt;artifactId&gt;curator-framework&lt;/artifactId&gt;
	&lt;version&gt;4.0.0&lt;/version&gt;
&lt;/dependency&gt;

此时,需要要springboot启动类中加入:
@ImportResource(locations = {"classpath:/dubbo/provider.xml"})

而这个provider.xml配置如下:
	&lt;!-- 提供方应用信息，用于计算依赖关系 --&gt;
    &lt;dubbo:application name="demo-provider-xml"/&gt;

    &lt;!-- 使用multicast广播注册中心暴露服务地址 --&gt;
    &lt;dubbo:registry address="zookeeper://localhost:2181"/&gt;

    &lt;!-- 用dubbo协议在20880端口暴露服务 --&gt;
    &lt;dubbo:protocol name="dubbo" port="20885"/&gt;
    
    &lt;!-- 要扫描服务的包路径，使用注解方式暴露接口 这种扫描方式无法跟 @Transactional 注解一起使用 --&gt;
	&lt;!--     &lt;dubbo:annotation package="com.fast" /&gt; --&gt;
	
    &lt;!-- user service 定义接口及实现类bean,注意:实现类中的@service注解是spring的 --&gt;
	&lt;bean id="userService" class="com.xiaoywqs.provider.service.impl.UserServiceImpl" /&gt;
    &lt;dubbo:service version="1.0.0" interface="org.dubbo.demo.api.UserService" ref="userService"/&gt;

因为在以上xml文件中有定义一个bean,是给dubbo使用的,所以我们在service类中,还是要使用正常的spring注解:@Service

下面说明一下consumer配置:
pom中依赖和provider是一样的;

同样的,启动类中加入: @ImportResource(value = "classpath:dubbo/consumer.xml")

这个consumer配置简单多了:
	&lt;!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 --&gt;
    &lt;dubbo:application name="demo-consumer-xml"/&gt;

    &lt;!-- 使用multicast广播注册中心暴露发现服务地址 --&gt;
    &lt;dubbo:registry address="zookeeper://localhost:2181"/&gt;
    
    &lt;!-- 要扫描的包路径　使用注解方式创建服务 --&gt;
    &lt;dubbo:annotation package="com.xiaoywqs.consumer.controller" /&gt;

	&lt;dubbo:reference interface="org.dubbo.demo.api.UserService" id="userService"/&gt;
	
在controller中,我们这样使用(版本要一致):
	@Reference(version = "1.0.0")
	private UserService userService;

至此,xml配置完成!
&lt;/pre&gt;