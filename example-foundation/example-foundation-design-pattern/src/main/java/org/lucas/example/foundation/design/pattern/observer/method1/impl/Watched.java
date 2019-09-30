package org.lucas.example.foundation.design.pattern.observer.method1.impl;

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
