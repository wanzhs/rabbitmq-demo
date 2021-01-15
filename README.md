# 延时队列之TTL方式
RabbitMq 死信消息的过期时间的不同 会导致消费延时
当队列中已有消息，并设置消息的TTL，那么会追溯设置消息的有效期，当在特定情况下将会丢弃这些消息。只有当过期消息到达队列的头部时，它们才会被真实地丢弃(或死信路由)

存储消息在死信队列(delayqueue)   会阻塞
# 延时队列(通过rabbitmq_delayed_message_exchange-20171201-3.7.x.ez插件)

原理：  发送前进行延时操作,时间到了再发送到mq队列中,不受mq队列死信方式的影响
存储消息在延时交换机里(x-delayed-message exchange) 不会阻塞
1:生产者将消息(msg)和路由键(routekey)发送指定的延时交换机(exchange)上
2:延时交换机(exchange)存储消息等待消息到期后根据路由键(routekey)找到绑定自己的队列(queue)并把消息给它
3:队列(queue)再把消息发送给监听它的消费者(customer）
