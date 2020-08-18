package org.lucas.example.framework.akka.demo.local;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class Client {

	public static void main(String[] args) {
		// 1 创建Actor系统
		ActorSystem system = ActorSystem.create("AkkaClient", ConfigFactory.empty());

		// 2 创建Actor
		ActorRef client = system.actorOf(Props.create(ClientActor.class),"ClientActor");

		// 3 发送消息
		client.tell("DoCalcs", ActorRef.noSender());
		System.out.println("over");
	}
}
