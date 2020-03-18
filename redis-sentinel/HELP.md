# Getting Started
spring boot redis 哨兵模式使用。
详情请参考 [spring boot官方文档](https://docs.spring.io/spring-data/redis/docs/2.1.6.RELEASE/reference/html/#redis:write-to-master-read-from-replica)

### Reference Documentation
For further reference, please consider the following sections:
* 使用yaml方式配置redis，并重新定义序列化方式。
```yaml
spring:
  application:
    name: redis-sentinel
  redis:
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
    sentinel:
      master: mymaster
      nodes:
      # 要连接的sentinel节点
        - 127.0.0.1:26379
        - 127.0.0.1:26380
        - 127.0.0.1:26381
```

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)

