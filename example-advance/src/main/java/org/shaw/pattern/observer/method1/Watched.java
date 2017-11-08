package org.shaw.pattern.observer.method1;

import java.util.Observable;

/**
 * @create: 2017-11-07
 * @description:
 */
public class Watched extends Observable {
    private String data = "";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        if (!this.data.equals(data)) {
            this.data = data;
            setChanged();
        }
        notifyObservers();
    }
}