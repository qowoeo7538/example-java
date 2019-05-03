package org.lucas.demo.future;

import org.lucas.demo.future.impl.FutureClient;
import org.lucas.demo.future.impl.Data;

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
