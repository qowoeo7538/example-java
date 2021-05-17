package org.lucas.example.framework.web.spring.context.lifecycle;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class SmartLifecycleDemo implements SmartLifecycle {

    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * 1）主要在该方法中启动任务或者其他异步服务，比如开启MQ接收消息,Web服务器。
     * 2）在应用程序上下文刷新的finishRefresh阶段（所有对象已被实例化和初始化之后），
     * 将调用该方法，默认生命周期处理器将检查每个 SmartLifecycle 对象的 isAutoStartup()
     * 方法返回的布尔值。如果为 "true"，则该方法会被调用，而不是等待显式调用自己的start()方法。
     */
    @Override
    public void start() {
        // 修改 isRunning = true
        if (this.running.compareAndSet(false, true)) {
            System.out.println("start");
        }
    }

    /**
     * 如果工程中有多个实现接口 SmartLifecycle 的类，则这些类的 start 的执行顺序
     * 按 getPhase 方法返回值从小到大执行。
     * <p>
     * 例如：1比2先执行，-1 比 0 先执行。
     * <p>
     * stop方法的执行顺序则相反，getPhase 返回值较大类的stop方法先被调用，小的后被调用。
     */
    @Override
    public int getPhase() {
        return 0;
    }

    /**
     * 根据该方法的返回值决定是否执行start方法。
     * 返回 true 时 start 方法会被自动执行，返回 false 则不会。
     */
    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop() {
        if (this.running.compareAndSet(true, false)) {
            System.out.println("stop()");
        }
    }

    /**
     * SmartLifecycle子类的才有的方法，当isRunning方法返回true时，该方法才会被调用。
     */
    @Override
    public void stop(Runnable callback) {
        System.out.println("stop(Runnable)");

        // 如果你让 isRunning 返回 true，需要执行stop这个方法，那么就不要忘记调用callback.run()。
        // 否则在你程序退出时，Spring 的 DefaultLifecycleProcessor会认为你这个TestSmartLifecycle没有stop完成，程序会一直卡着结束不了，等待一定时间（默认超时时间30秒）后才会自动结束。
        // PS：如果你想修改这个默认超时时间，可以按下面思路做，当然下面代码是springmvc配置文件形式的参考，在SpringBoot中自然不是配置xml来完成，这里只是提供一种思路。
        // <bean id="lifecycleProcessor" class="org.springframework.context.support.DefaultLifecycleProcessor">
        //      <!-- timeout value in milliseconds -->
        //      <property name="timeoutPerShutdownPhase" value="10000"/>
        // </bean>
        stop();
        callback.run();
    }

    /**
     * 1. 只有该方法返回 false 时，start方法才会被执行。
     * 2. 只有该方法返回 true 时，stop(Runnable callback) 或 stop() 方法才会被执行。
     */
    @Override
    public boolean isRunning() {
        return this.running.get();
    }
}
