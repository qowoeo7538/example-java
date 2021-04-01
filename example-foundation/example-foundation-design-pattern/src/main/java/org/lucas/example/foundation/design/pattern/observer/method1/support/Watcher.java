package org.lucas.example.foundation.design.pattern.observer.method1.support;

import java.util.Observable;
import java.util.Observer;

/**
 * @create: 2017-11-07
 * @description:
 */
public class Watcher implements Observer {
    public Watcher(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("状态发生改变：" + ((Watched) o).getData());
    }
}
