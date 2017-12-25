package org.shaw.nio.pattern.observer.method2.impl;

/**
 * 具体主题角色类：将有关状态存入具体观察者对象;在具体主题的内部状态改变时，给所有登记过的观察者发出通知。
 * 具体主题角色又叫做具体被观察者角色。
 */
public class ConcreteSubject1 extends Subject1 {
    private String state;

    public String getState() {
        return state;
    }

    public void change(String newState) {
        this.state = newState;
        System.out.println("主题状态为:" + state);
        this.nodifyObservers(state);
    }
}
