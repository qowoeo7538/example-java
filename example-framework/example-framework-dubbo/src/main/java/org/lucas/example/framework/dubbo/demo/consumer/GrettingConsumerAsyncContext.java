package org.lucas.example.framework.dubbo.demo.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.lucas.example.framework.dubbo.demo.common.service.GrettingServiceRpcContext;


public class GrettingConsumerAsyncContext {

    public static void main(String[] args) {
        ReferenceConfig<GrettingServiceRpcContext> referenceConfig = new ReferenceConfig<GrettingServiceRpcContext>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface(GrettingServiceRpcContext.class);
        referenceConfig.setTimeout(5000);

        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        GrettingServiceRpcContext greetingService = referenceConfig.get();

        //设置隐式参数
        RpcContext.getContext().setAttachment("company", "alibaba");
        String result = greetingService.sayHello("world");
        System.out.println(result);
    }
}     