# OMS
[![Build Status](https://travis-ci.org/apache/rocketmq-externals.svg?branch=master)](https://travis-ci.org/apache/rocketmq-externals) 
[![Coverage Status](https://coveralls.io/repos/github/rocketmq/rocketmq-console-ng/badge.svg?branch=master)](https://coveralls.io/github/rocketmq/rocketmq-console-ng?branch=master)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

<p>
  <a href="https://github.com/lgykuuga/OMS-fast"><img src="https://github.com/lgykuuga/OMS-fast/blob/master/img/orange.svg" alt="项目地址"></a>
</p>

## 前言

`OMS`项目基于RuoYi 4.0(2019-8-15)版本进行修改，新增集成现阶段流行技术(rabbitMQ、redis、mongoDB、mybatis plus、xxl-job、disruptor并发框架...),仅凭个人兴趣及行业累积经验所输出的业务项目，带着思考去学习技术并落地到项目中。

## 项目文档

- 文档地址：[https://github.com/lgykuuga/OMS-fast/blob/master/README.md](https://github.com/lgykuuga/OMS-fast/blob/master/README.md)
- 开发手册：[http://doc.ruoyi.vip/ruoyi/](http://doc.ruoyi.vip/ruoyi/)

## 项目介绍

`OMS`项目是一套订单管理系统，基于SpringBoot+MyBatis Plus实现。系统功能包含基础资料（商品管理、货主管理、仓库管理、物流商管理...）、订单管理（下载订单、订单审核、订单配货、订单推送）、库存管理设置等模块。  
可根据系统订单量级,配置项目集群或者单点应用。一键开关配置中间件.做到开箱即用效果的同时也兼顾到技术。

### 项目演示


[^_^]:项目演示地址：[http://115.159.201.193:8080/login](http://115.159.201.193:8080/login)（建议使用chrome浏览器访问，不定期更新）


### 组织结构

``` lua
OMS
├── jeefast-admin     -- 前端代码及对后台管理系统接口
├── jeefast-base      -- OMS基础资料模块
├── jeefast-common    -- 系统公用类、工具类等基础封装
├── jeefast-framework -- 基于Ruoyi项目工程基础框架封装
├── jeefast-generator -- MyBatisGenerator生成的数据库操作代码
├── jeefast-oms       -- OMS系统核心业务代码
├── jeefast-quartz    -- 定时任务
├── jeefast-system    -- 基于Ruoyi项目工程基础模块
├── jeefast-es        -- 基于Elasticsearch的订单搜索系统
├── jeefast-sync      -- 数据传输服务模块
```

### 技术选型

#### 后端技术

| 技术                 | 说明                | 官网                                                 |
| -------------------- | ------------------- | ---------------------------------------------------- |
| SpringBoot           | 容器+MVC框架        | https://spring.io/projects/spring-boot               |
| MyBatis Plus         | ORM框架             | http://www.mybatis.org/mybatis-3/zh/index.html       |
| MyBatisGenerator     | 数据层代码生成      | http://www.mybatis.org/generator/index.html          |
| PageHelper           | MyBatis物理分页插件 | http://git.oschina.net/free/Mybatis_PageHelper       |
| Swagger-UI           | 文档生产工具        | https://github.com/swagger-api/swagger-ui            |
| Elasticsearch        | 搜索引擎            | https://github.com/elastic/elasticsearch             |
| Canal                | MySQL binlog 增量订阅&消费组件  | https://github.com/alibaba/canal             |
| RabbitMq             | 消息队列            | https://www.rabbitmq.com/                            |
| Redis                | 分布式缓存          | https://redis.io/                                    |
| MongoDb              | NoSql数据库         | https://www.mongodb.com                              |
| Docker               | 应用容器引擎        | https://www.docker.com                               |
| Druid                | 数据库连接池        | https://github.com/alibaba/druid                     |
| Shiro                | 认证、授权、会话管理  | http://shiro.apache.org/                            |
| Disruptor            | Java并发框架        | https://github.com/LMAX-Exchange/disruptor          |
| XXL-JOB              | 任务调度中心        | https://github.com/xuxueli/xxl-job               |
| Lombok               | 简化对象封装工具    | https://github.com/rzwitserloot/lombok               |
| MapStruct            | Bean映射的简化框架  | https://mapstruct.org/               |

#### 前端技术

| 技术       | 说明                  | 官网                                   |
| ---------- | --------------------- | -------------------------------------- |
| thymeleaf  | 前端模板引擎框架        | https://www.thymeleaf.org/                     |
| Bootstrap  | 前端UI框架            | https://getbootstrap.com/               |

#### 架构图

##### 系统架构图

![系统架构图](https://github.com/lgykuuga/OMS-fast/blob/master/img/%E7%BD%91%E7%BB%9C%E6%8B%93%E6%89%91%E5%9B%BE.png)

##### 业务架构图


![业务架构图](https://github.com/lgykuuga/OMS-fast/blob/master/img/%E4%B8%9A%E5%8A%A1%E6%9E%B6%E6%9E%84.png)


#### 模块介绍

#### 开发进度

（未画）

## 环境搭建

### 开发工具

| 工具          | 说明                | 官网                                            |
| ------------- | ------------------- | ----------------------------------------------- |
| IDEA          | 开发IDE             | https://www.jetbrains.com/idea/download         |
| RedisDesktop  | redis客户端连接工具 | https://redisdesktop.com/download               |
| Robomongo     | mongo客户端连接工具 | https://robomongo.org/download                  |
| X-shell       | Linux远程连接工具   | http://www.netsarang.com/download/software.html |
| Navicat       | 数据库连接工具      | http://www.formysql.com/xiazai.html             |
| Axure         | 原型设计工具        | https://www.axure.com/                          |
| MindMaster    | 思维导图设计工具    | http://www.edrawsoft.cn/mindmaster              |
| ScreenToGif   | gif录制工具         | https://www.screentogif.com/                    |
| ProcessOn     | 流程图绘制工具      | https://www.processon.com/                      |
| PicPick       | 图片处理工具        | https://picpick.app/zh/                         |
| Postman       | API接口调试工具      | https://www.postman.com/                        |
| Typora        | Markdown编辑器      | https://typora.io/                              |
| ElasticSearch Head Chrome插件 | es数据可视化工具   |                                    |
| Kibana        | es数据可视化工具    | https://www.elastic.co/products/kibana           |

### 开发环境

| 工具           | 版本号 | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql         | 5.7    | https://www.mysql.com/                                       |
| Redis         | 3.4    | https://redis.io/download                                    |
| Elasticsearch | 6.2.4  | https://www.elastic.co/downloads                             |
| Canal         | 1.1.4  | https://github.com/alibaba/canal                             |
| MongoDb       | 3.2    | https://www.mongodb.com/download-center                      |
| RabbitMq      | 3.7.7  | http://www.rabbitmq.com/download.html                        |
| XXL-JOB       | (待开发)  | https://github.com/xuxueli/xxl-job                        |
| Nginx         | 1.10   | http://nginx.org/en/download.html                            |

## Ruoyi内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql)支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

Copyright (c) 2019-2020 LGy
