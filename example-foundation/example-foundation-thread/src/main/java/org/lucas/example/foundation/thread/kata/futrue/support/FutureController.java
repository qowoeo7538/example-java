package org.lucas.example.foundation.thread.kata.futrue.support;


/**
 * 接收请求的控制器
 */
public class FutureController {

    public FutureData handler(final String param) {
        //由于真实的数据还没有准备好,所以返回一个真实Data数据的代理类
        final FutureData futureData = new FutureData();

        //2 启动一个新的线程，去加载真实的数据，传递给这个代理对象
        new Thread(() -> futureData.initRealData(param)).start();
        return futureData;
    }
}
