package org.shaw.nio.thread.executor.future;

import org.shaw.nio.thread.executor.future.impl.FutureClient;
import org.shaw.thread.executor.future.impl.Data;
import org.shaw.thread.executor.future.impl.FutureClient;

/**
 * @create: 2017-11-13
 * @description: future原理演示
 */
public class PrincipleDemo {
    public static void main(String[] args) {
        FutureClient fClient = new FutureClient();

        // 回调对象
        Data data = fClient.request("hello,world");
        System.out.println("请求发送成功...");

        System.out.println("干其他的事情...");
        String result = data.getRequest();

        System.out.println(result);
    }
}
