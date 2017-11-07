package org.shaw.pattern.observer;

import java.util.Observable;

/**
 * Created by joy on 17-2-23.
 *
 * 推模型是假定主题对象知道观察者需要的数据；
 * 而拉模型是主题对象不知道观察者具体需要什么数据，没有办法的情况下，干脆把自身传递给观察者，让观察者自己去按需要取值。
 */
public class ObserverPattern {
    public static void main(String[] args) {
        Watched watched = new Watched();
        Watcher watcher = new Watcher(watched);

        watched.setData("start");
        watched.setData("run");
        watched.setData("stop");
    }
}

class Watched extends Observable{
    private String data = "";

    public String getData(){
        return data;
    }

    public void setData(String data){
        if(!this.data.equals(data)){
            this.data = data;
            setChanged();
        }
        notifyObservers();
    }
}

class Watcher implements java.util.Observer{
    public Watcher(Observable o){
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("状态发生改变：" + ((Watched)o).getData());
    }
}

