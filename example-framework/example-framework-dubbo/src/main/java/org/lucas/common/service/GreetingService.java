package org.lucas.common.service;

import org.lucas.common.pojo.PoJo;
import org.lucas.common.pojo.Result;

public interface GreetingService {

	String sayHello(String name);
	
	Result<String> testGeneric(PoJo poJo);
}
