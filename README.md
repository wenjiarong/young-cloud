#### 项目介绍
微服务项目架构 实现支持高并发、高可用的微服务项目(后端)

#### 项目规划
目前已实现的功能为下列列举项,该项目正在一步步开发完善中...计划年后实现xxl-job分布式时间调度

#### 项目技术方案

1.  Sentinel：把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。(持久化到nacos以及与网关API进行了整合),服务调用负载均衡：Ribbon、OpenFeign;服务熔断，隔离，与降级。采用配置持久化到nacs实现系统流控
2.  Nacos：（支持集群部署）一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。注册中心和配置中心(系统采用多环境配置)
3.  canal: 实现数据库与mysql同步框架
4.  Seata：高性能微服务分布式事务解决 （项目采用AT模式支持集群部署以及注册进nacos和事务协调者服务信息持久化到mysql）
5.  RocketMQ：基于高可用分布式集群技术，提供低延时的、高可靠的消息发布与订阅服务
6.  Gateway：服务网关 （支持集群部署） ，已实现服务跨域，熔断，自定义注解fegin权限校验
7.  Swagger: 单服务单独配置，网关服务对swagger进行了根据Gateway的断言规则进行了自动聚合
8.  Oauth2.0+Jwt+Security: 实现鉴权服务，扩展token属性。
9.  logback：实现日志打印，使用logback来记录日志,可整合elk日志收集
10. jpush： 整合极光推送
11. Spring Cloud Sleuth：来追踪这个过程，并借助Zipkin以图形化界面的方式展示
12. redis: nosql缓存，集成Lettuce


#### 项目模块介绍

db  ---数据库   young_cloud.sql
doc --- ftp     user.cfg用户配置
    --- nacos 配置 
            young.yaml
            young-dev.yaml
            young-gateway.yaml
    --- Environment.txt    环境搭建（ELK环境搭建,）
    --- zipkin.jar         第三方zipkin
    
1.  young-auth    鉴权服务
2.  young-canal   整合canal缓存，同步数据库
3.  young-common  公共模块
4.  young-core    核心代码模块
5.  young-gateway 网关模块
6.  young-ops     功能模块
7.   |   -young-admin              分布式任务调度管理模块
8.   |   -young-canal-client       canal客户端模块
9.   |   -young-file-manage        ftp和tftp服务器模块
10.  |   -young-jpush              极光推送模块
11.  young-ops-api 功能模块api、实体类
12.  young-service 业务模块
13.  |   young-service-system       系统权限模块
14.  |   young-service-demo         RocketMQ分布式事务测试模块
15.  |   young-service-test         测试模块、fegin测试、熔断测试
16.  young-service-api 业务模块api、实体类



#### 项目备注
1. 纯个人从0设计搭建的微服务高并发项目,致力于研究学习.如有不同想法,欢迎共同探讨交流进步！
2. 微信联系方式:wenjiarongs
