package org.lucas.example.framework.netty.kata.rpc;

import io.reactivex.Flowable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncRpcStartup {

    private static final RpcClient rpcClient = new RpcClient();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        startup();
        // completeStartup();
        // reactiveStartup();
    }

    private static void startup() throws InterruptedException, ExecutionException {
        // 1 同步调用
        System.out.println(rpcClient.rpcSyncCall("who are you"));

        // 2 发起远程调用异步，并注册回调，马上返回
        CompletableFuture<String> future = rpcClient.rpcAsyncCall("who are you");

        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(v);
            }
        });
        System.out.println("---async rpc call over");
    }

    private static void completeStartup() throws InterruptedException, ExecutionException {
        // 1 发起远程调用异步，并注册回调，马上返回
        CompletableFuture<String> future1 = rpcClient.rpcAsyncCall("who are you");

        // 2 发起远程调用异步，并注册回调，马上返回
        CompletableFuture<String> future2 = rpcClient.rpcAsyncCall("who are you");

        // 3 等两个请求都返回结果时候，使用结果做些事情
        CompletableFuture<String> future = future1.thenCombine(future2, (u, v) -> u + v);

        // 4.等待最终结果
        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(v);
            }

        });
        System.out.println("---async rpc call over---");
        // rpcClient.close();
    }

    private static void reactiveStartup() throws InterruptedException, ExecutionException {
        // 1 发起远程调用异步，并注册回调，马上返回,这时候并没有发起请求。
        Flowable<String> result = rpcClient.rpcAsyncCallFlowable4("who are you");

        // 2 订阅流对象，这时才真正发起请求。
        result.subscribe(/* onNext */r -> {
            System.out.println(Thread.currentThread().getName() + ":" + r);
        }, /* onError */error -> {
            System.out.println(Thread.currentThread().getName() + "error:" + error.getLocalizedMessage());
        });

        System.out.println("---async rpc call over");
    }

}
