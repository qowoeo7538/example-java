package org.lucas.example.framework.akka.demo.remote.server;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.lucas.example.framework.akka.common.domain.Messages;

public class CalculatorActor extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("onReceive({})", message);

        // 1 如果消息为 Messages.Sum 类型则处理
        if (message instanceof Messages.Sum) {
            log.info("got a Sum message");
            Messages.Sum sum = (Messages.Sum) message;
            // 1.1 计算加法并返回结果
            int result = sum.getFirst() + sum.getSecond();
            // 1.2 将结果返回给消息发送者
            getSender().tell(new Messages.Result(result), getSelf());
            System.out.println("sub over");
        } else {
            // 2 忽略该请求
            unhandled(message);
        }
    }
}
