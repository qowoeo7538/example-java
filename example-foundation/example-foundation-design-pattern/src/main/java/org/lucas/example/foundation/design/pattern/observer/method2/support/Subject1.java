package org.lucas.example.foundation.design.pattern.observer.method2.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 推模型
 * <p>
 * 抽象主题角色:把所有对观察者对象的引用保存在一个聚集（比如ArrayList对象）里，每个主题都可以有任何数量的观察者。
 * 抽象主题提供一个接口，可以增加和删除观察者对象，抽象主题角色又叫做抽象被观察者(Observable)角色。
 */
public abstract class Subject1 {
    /**
     * 用来保存注册的观察者对象
     */
    private List<Observer1> observers = new ArrayList<Observer1>();

    /**
     * 注册观察者对象
     *
     * @param observer 观察者对象
     */
    public void attach(Observer1 observer) {
        observers.add(observer);
        System.out.println("添加一个观察者对象");
    }

    /**
     * 删除观察者对象
     *
     * @param observer 观察者对象
     */
    public void detech(Observer1 observer) {
        observers.remove(observer);
        System.out.println("删除一个观察者对象");
    }

    /**
     * 通知所有注册的观察者对象
     */
    public void nodifyObservers(String newState) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(newState);
        }
    }
}
