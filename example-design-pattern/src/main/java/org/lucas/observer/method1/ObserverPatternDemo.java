package org.lucas.observer.method1;


import org.junit.Test;
import org.lucas.observer.method1.impl.Watched;
import org.lucas.observer.method1.impl.Watcher;

/**
 * <p>
 * 推模型是假定主题对象知道观察者需要的数据；
 * <p>
 * 而拉模型是主题对象不知道观察者具体需要什么数据，
 * 没有办法的情况下，干脆把自身传递给观察者，让观察者自己去按需要取值。
 */
public class ObserverPatternDemo {

    /**
     * @since 9
     */
    @Test
    public void observer() {
        final Watched watched = new Watched();
        final Watcher watcher = new Watcher(watched);

        watched.setData("start");
        watched.setData("run");
        watched.setData("stop");
    }

    /**
     * @since 10
     */
    @Test
    public void observer1() {
        final Watched watched = new Watched();
        final Watcher watcher = new Watcher(watched);

        watched.setData("start");
        watched.setData("run");
        watched.setData("stop");
    }

}
