package org.lucas.example.foundation.thread.kata.futrue;

import org.lucas.example.foundation.thread.kata.futrue.support.FutureController;
import org.lucas.example.foundation.thread.kata.futrue.support.FutureData;

public class FutureKata {

    public static void main(String[] args) {
        //发送请求
        FutureController fc = new FutureController();
        //2.Controller.handler方法被调用,并传入了参数
        //3.此处返回的Data对象为FutureData对象,返回的是Data的代理类
        FutureData data = fc.handler("UserId");
        System.out.println("请求发送成功...");
        System.out.println("继续其他的处理...");
        //3.获取真实数据
        System.out.println("Main开始获取真实结果");
        String result = data.getRealData();
        System.out.println(result);
    }

}
