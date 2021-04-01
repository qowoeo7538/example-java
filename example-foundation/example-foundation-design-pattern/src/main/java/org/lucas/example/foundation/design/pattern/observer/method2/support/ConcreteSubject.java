package org.lucas.example.foundation.design.pattern.observer.method2.support;

/**
 * @create: 2017-11-07
 * @description:
 */
public class ConcreteSubject extends Subject {
    private String state;

    public void change(String newState) {
        this.state = newState;
        System.out.println("主题状态发生改变：" + state);
        this.notifyObserver();
    }

    public String getState() {
        return state;
    }
}
