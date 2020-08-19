package org.lucas.example.framework.akka.demo.remote.server;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class Server {

    public static void main(String[] args) {
        // 1 创建 actor 系统，并加载 application-server.conf
        ActorSystem system = ActorSystem.create("AkkaRemoteServer", ConfigFactory.load("application-server.conf"));
        // 2 创建 CalculatorActor.
        system.actorOf(Props.create(CalculatorActor.class), "CalculatorActor");
    }

}
