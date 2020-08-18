package org.lucas.example.middleware.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class ProducerAsync {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        // 1 创建生产者实例,同一个集群实例的实例名称要一致。
        DefaultMQProducer producer = new DefaultMQProducer("jiaduo-producer-group");
        // 2 设置 nameserver 地址，多个可以使用";"分隔。
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(1000);
        producer.setRetryTimesWhenSendAsyncFailed(0);
        // 3 启动生产者,连接 ServerName 地址，获取 broker 地址，并与 broker 建立连接。
        producer.start();

        // 4 发送消息
        for (int i = 0; i < 20; i++) {
            try {
                // 4.1 创建消息体，topic为TopicTest，tag为TagA，第三个参数为消息类容，是个二进制数据。
                Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */, i + "",
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                // 4.2 异步发送消息,不需要阻塞，等消息投递后，底层IO线程会回调 Callback 进行通知。
                producer.send(msg, new SendCallback() {

                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("onSuccess:%s%n", sendResult);
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("onException:%s%n", e);
                    }
                });
            } catch (final MQClientException | RemotingException | InterruptedException e) {
                System.out.println("消息发送失败！。");
                e.printStackTrace();
            }catch (final Exception e){
                e.printStackTrace();
            }
        }
        // 5. 关闭
        Thread.sleep(100000);
        producer.shutdown();
    }

}
