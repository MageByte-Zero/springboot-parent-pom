# springboot-study
springboot 与现实开发中各种框架的优雅整合。最精简的方式，参考官方各个框架官方文档实现
* mybatis plus整合
* redis 哨兵模式
* dubbo yaml方式整合
* lagback日志
* rabbitmq各种模式使用、详情可以看对应的readme
## mybatis-plus
具体详见[mybatis-plus官方文档](https://mp.baomidou.com/guide/)
## dubbo
* api层，定义接口
* consumer 消费者 调用服务提供者
* provider 服务提供者 实现api定义的接口。主要业务逻辑在这里实现
## rabbitmq
* fanout exchange 最灵活的模糊匹配
* direct exchange 默认的根据routing key匹配投放队列
* topic exchange 发布订阅模式
## web core
* 全局异常处理、统一返回结构体、请求日志记录
