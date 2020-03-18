# rabbitmq

## 交换机(Exchange)
交换机的功能主要是接收消息并且转发到绑定的队列，交换机不存储消息，在启用ack模式后，交换机找不到队列会返回错误。交换机有四种类型：Direct, topic, Headers and Fanout

* Direct：direct 类型的行为是"先匹配, 再投送". 即在绑定时设定一个 routing_key, 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去.默认模式
* Topic：按规则转发消息（最灵活）
* Headers：设置 header attribute 参数类型的交换机
* Fanout：转发消息到所有绑定队列

## 消息确认
消息消费者如何通知 Rabbit 消息消费成功？

消息通过 ACK 确认是否被正确接收，每个 Message 都要被确认（acknowledged），可以手动去 ACK 或自动 ACK
自动确认会在消息发送给消费者后立即确认，但存在丢失消息的可能，如果消费端消费逻辑抛出异常，也就是消费端没有处理成功这条消息，那么就相当于丢失了消息
如果消息已经被处理，但后续代码抛出异常，使用 Spring 进行管理的话消费端业务逻辑会进行回滚，这也同样造成了实际意义的消息丢失
如果手动确认则当消费者调用 ack、nack、reject 几种方法进行确认，手动确认可以在业务失败后进行一些操作，如果消息未被 ACK 则会发送到下一个消费者
如果某个服务忘记 ACK 了，则 RabbitMQ 不会再发送数据给它，因为 RabbitMQ 认为该服务的处理能力有限
ACK 机制还可以起到限流作用，比如在接收到某条消息时休眠几秒钟
消息确认模式有：

AcknowledgeMode.NONE：自动确认
AcknowledgeMode.AUTO：根据情况确认
AcknowledgeMode.MANUAL：手动确认

## topic
星号（*） ：只能匹配一个单词
井号（#）：可以匹配0个或多个单词

如果一个队列绑定的键为"#"时，将会接收所有的队列，类似于fanout转发器。
如果绑定的队列不包含"#"和"*"时，这时候类似于Direct模式，直接匹配。

## Fanout Exchange
Fanout 就是我们熟悉的广播模式或者订阅模式，给 Fanout 交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。

