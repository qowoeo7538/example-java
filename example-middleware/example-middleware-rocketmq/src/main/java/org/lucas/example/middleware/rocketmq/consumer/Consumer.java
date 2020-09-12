package org.lucas.example.middleware.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Consumer {

    public static void main(String[] args) throws  MQClientException {
        // 1 创建消费实例，同一个消费集群的消费实例名称应该一样。
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group");
        // 2 配置 NameServer 地址
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // 3 消费属性配置
        // CONSUME_FROM_FIRST_OFFSET：消费实例从第一个偏移量开始消费。
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 4 订阅TopicTest topic下所有tag
        consumer.subscribe("TopicTest",  SubscriptionData.SUB_ALL);
        // 5 注册回调，当 "TopicTest" 主题下面有消息需要消费时的处理。
        consumer.registerMessageListener((List<MessageExt>  msgs, ConsumeConcurrentlyContext context) -> {
            for (MessageExt msg : msgs) {
                String body = "";
                try {
                    body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s Receive New Messages: %s %s %n", Thread.currentThread().getName(),
                        msg.getMsgId(), body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 6 启动消费实例，连接 NameServer 获取 Broker 地址，并与 Broker 连接。
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

}
