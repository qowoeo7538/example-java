package org.lucas.example.middleware.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        // 1 创建生产者实例,同一个集群实例的实例名称要一致。
        DefaultMQProducer producer = new DefaultMQProducer("jiaduo-producer-group");
        // 2 设置nameserver地址，多个可以使用;分隔
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(1000);
        // 3 启动生产者
        producer.start();

        // 4 发送消息
        for (int i = 0; i < 10; i++) {
            try {

                // 4.1 创建消息体,topic为TopicTest，tag为TagA
                Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                // 4.2 同步发送消息，当前消息通过底层网络投递到TCP发送 buffer 后（这时消息还未投递到broker）才会返回，整个过程会导致阻塞。
                SendResult sendResult = producer.send(msg);
                // 4.3
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 5 关闭生产者实例
        producer.shutdown();
    }

}
