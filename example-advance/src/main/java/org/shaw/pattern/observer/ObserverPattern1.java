package org.shaw.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 17-2-22.
 */
public class ObserverPattern1 {
    public static void main(String[] args) {
        ConcreteSubject1 concreteSubject1 = new ConcreteSubject1();
        Observer1 observer1 = new ConcreteObserver1();

        concreteSubject1.attach(observer1);
        concreteSubject1.change("new state");
        System.out.println("具体主题角色类:"+concreteSubject1.getState());
    }
}

/**********************   推模型   *****************************************/

/**
 * 抽象主题角色:把所有对观察者对象的引用保存在一个聚集（比如ArrayList对象）里，每个主题都可以有任何数量的观察者。
 * 抽象主题提供一个接口，可以增加和删除观察者对象，抽象主题角色又叫做抽象被观察者(Observable)角色。
 */
abstract class Subject1 {

    // 用来保存注册的观察者对象
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

/**
 * 具体主题角色类：将有关状态存入具体观察者对象;在具体主题的内部状态改变时，给所有登记过的观察者发出通知。
 * 具体主题角色又叫做具体被观察者角色。
 */
class ConcreteSubject1 extends Subject1 {
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

/**
 * 抽象观察者角色类:为所有的具体观察者定义一个接口，在得到主题的通知时更新自己，这个接口叫做更新接口。
 */
interface Observer1 {
    void update(String state);
}

/**
 * 具体观察者角色类:存储与主题的状态自恰的状态。
 * 具体观察者角色实现抽象观察者角色所要求的更新接口，以便使本身的状态与主题的状态 像协调。
 * 如果需要，具体观察者角色可以保持一个指向具体主题对象的引用。
 */
class ConcreteObserver1 implements Observer1 {

    private String observerState;

    @Override
    public void update(String state) {
        this.observerState = state;
        System.out.println("更改状态为：" + observerState);
    }
}


/**********************   拉模型   *****************************************/

abstract class Subject{
    List<Observer> list = new ArrayList<Observer>();

    public void attach(Observer observer){
        list.add(observer);
        System.out.println("添加一个观者对象");
    }

    public void detach(Observer observer){
        list.remove(observer);
        System.out.println("删除一个观者对象");
    }

    public void notifyObserver(){
        for (int i = 0; i < list.size(); i++) {
            list.get(i).update(this);
        }
    }
}

class ConcreteSubject extends Subject{
    private String state;

    public void change(String newState){
        this.state = newState;
        System.out.println("主题状态发生改变："+state);
        this.notifyObserver();
    }

    public String getState(){
        return state;
    }
}

interface Observer{
    void update(Subject subject);
}

class ConcreteObserver implements Observer{

    private String observerState;

    @Override
    public void update(Subject subject) {
        String observerState =((ConcreteSubject)subject).getState();
        System.out.println("观察者状态为："+observerState);
    }
}