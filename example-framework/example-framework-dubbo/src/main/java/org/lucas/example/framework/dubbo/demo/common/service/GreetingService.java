package org.lucas.example.framework.dubbo.demo.common.service;

import org.lucas.example.framework.dubbo.demo.common.pojo.PoJo;
import org.lucas.example.framework.dubbo.demo.common.pojo.Result;

public interface GreetingService {

	String sayHello(String name);
	
	Result<String> testGeneric(PoJo poJo);
}
