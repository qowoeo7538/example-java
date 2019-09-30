package org.lucas.example.foundation.design.pattern.observer.method2.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * @create: 2017-11-07
 * @description: 拉模型
 */
public abstract class Subject {

    List<Observer> list = new ArrayList();

    public void attach(Observer observer) {
        list.add(observer);
        System.out.println("添加一个观者对象");
    }

    public void detach(Observer observer) {
        list.remove(observer);
        System.out.println("删除一个观者对象");
    }

    public void notifyObserver() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).update(this);
        }
    }
}
