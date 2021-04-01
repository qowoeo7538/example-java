package org.lucas.example.foundation.design.pattern.observer.method2.support;

/**
 * @create: 2017-11-07
 * @description:
 */
public class ConcreteObserver implements Observer {
    private String observerState;

    @Override
    public void update(Subject subject) {
        String observerState = ((ConcreteSubject) subject).getState();
        System.out.println("观察者状态为：" + observerState);
    }
}
