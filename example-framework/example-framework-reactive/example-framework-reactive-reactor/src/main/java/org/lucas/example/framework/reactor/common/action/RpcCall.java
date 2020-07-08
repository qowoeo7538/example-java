package org.lucas.example.framework.reactor.common.action;

public class RpcCall {

    public static String request(String ip, String param) {
        System.out.println(Thread.currentThread().getName() + " " + ip + " rpcCall:" + param);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return param;
    }
}
