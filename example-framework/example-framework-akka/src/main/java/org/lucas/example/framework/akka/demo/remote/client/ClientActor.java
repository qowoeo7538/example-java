package org.lucas.example.framework.akka.demo.remote.client;

import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.lucas.example.framework.akka.common.domain.Messages;

public class ClientActor extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * 创建远程actor系统服务
     */
    private ActorSelection actorRef = getContext()
            .actorSelection("akka://AkkaRemoteServer@127.0.0.1:2552/user/CalculatorActor");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message.equals("DoCalcs")) {
            log.info("Got a calc job, send it to the remote calculator");
            System.out.println(actorRef);
            actorRef.tell(new Messages.Sum(1, 2), getSelf());
        } else if (message instanceof Messages.Result) {
            Messages.Result result = (Messages.Result) message;
            log.info("Got result back from calculator: {}", result.getResult());
        }
    }
}
