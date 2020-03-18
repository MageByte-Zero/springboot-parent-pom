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