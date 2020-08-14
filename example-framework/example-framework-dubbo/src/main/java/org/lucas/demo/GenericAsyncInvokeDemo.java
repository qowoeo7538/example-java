package org.lucas.demo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.lucas.common.pojo.InvokeTarget;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GenericAsyncInvokeDemo {

    /**
     * 消费组
     */
    private static final String GROUP = "sibu.mall.toolkit.dev";

    /**
     * 版本
     */
    private static final String VERSION = "dev-1.0.0";

    /**
     * 注册中心地址
     */
    private static final String REGISTRY_ADDRESS = "zookeeper://zk-zookeeper-0.zk-zookeeper-headless.dev-base.svc.cluster.dev:2181?backup=zk-zookeeper-1.zk-zookeeper-headless.dev-base.svc.cluster.dev:2181,zk-zookeeper-2.zk-zookeeper-headless.dev-base.svc.cluster.dev:2181";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 0 构建请求目标
        InvokeTarget target = InvokeTarget.builder()
                .interfaceName("com.sibu.mall.toolkit.service.AppClientService")
                .methodName("getShareTypeByApp")
                .methodParamType("com.sibu.mall.toolkit.request.AppInfoRequest")
                .methodParamToJson("{\"appId\":\"20005f0ece2d75704f00013b5e4a\"}", Object.class)
                .build();
        // 1 创建服务引用对象实例
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        // 2 设置应用程序信息
        referenceConfig.setApplication(new ApplicationConfig("dubbo-consumer"));
        // 3 设置服务注册中心
        referenceConfig.setRegistry(new RegistryConfig(REGISTRY_ADDRESS));
        // 4 设置服务接口
        referenceConfig.setInterface(target.getInterfaceName());
        // 5 设置超时时间
        referenceConfig.setTimeout(5000);
        // 6 设置服务分组与版本
        referenceConfig.setGroup(GROUP);
        referenceConfig.setVersion(VERSION);
        // 7 设置为异步
        referenceConfig.setAsync(true);
        // 8 设置泛化调用
        referenceConfig.setGeneric(CommonConstants.GENERIC_SERIALIZATION_DEFAULT);
        // 9 引用服务
        GenericService genericService = referenceConfig.get();

        // 10 异步执行,并设置回调
        genericService.$invoke(target.getMethodName(), target.getMethodParamTypes(), target.getMethodParams());
        CompletableFuture<Object> future1 = RpcContext.getContext().getCompletableFuture();
        future1.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(Thread.currentThread().getName() + " " + v);
            }
        });

        CompletableFuture<Object> future2 = RpcContext.getContext().getCompletableFuture();
        future2.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(Thread.currentThread().getName() + " " + v);
            }
        });

        // 11 挂起线程
        Thread.currentThread().join();
    }

}
