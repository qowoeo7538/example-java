package org.lucas.example.framework.akka.demo.local;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.lucas.example.framework.akka.common.domain.Messages;

public class ClientActor extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * 创建本地子actor:CalculatorActor
     */
    private ActorRef actorRef =
            getContext().actorOf(Props.create(CalculatorActor.class), "calculatorActor");

    /**
     * 处理逻辑
     */
    @Override
    public void onReceive(Object message) throws Exception {
        // 1 如果消息为 "DoCalcs" 则执行计算。
        if (message.equals("DoCalcs")) {
            log.info("Got a calc job, local calculator");
            System.out.println(actorRef);
            // 1.1 调用子actor进行消息处理
            actorRef.tell(new Messages.Sum(1, 2), getSelf());
        } else if (message instanceof Messages.Result) {
            // 2 如果消息为 Messages.Result 类型则打印结果。
            Messages.Result result = (Messages.Result) message;
            log.info("Got result back from calculator: {}", result.getResult());
        }

    }
}
