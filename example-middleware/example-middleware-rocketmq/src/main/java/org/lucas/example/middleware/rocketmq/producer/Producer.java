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
        // 3 启动生产者,然后实例将会连接NameServer并获取Broker的地址，然后生产者将会与Broker建立连接。
        producer.start();

        // 4 发送消息
        for (int i = 0; i < 10; i++) {
            try {

                // 4.1 创建消息体,topic为TopicTest，tag为TagA
                Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body 是个二进制数据*/
                );
                // 4.2 同步发送消息。
                // 首先创建一个 ResponseFuture 对象，并切换到IO线程，把请求发送到Broker，
                // 接着调用线程会调用 ResponseFuture 的 wait 方法阻塞调用线程，等IO线程将
                // 当前消息通过底层网络投递到TCP发送 buffer 后（这时消息还未投递到broker），
                // IO线程会设置 ResponseFuture 对象说请求已经完成，然后调用线程就会从 wait 方
                // 法返回。
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
