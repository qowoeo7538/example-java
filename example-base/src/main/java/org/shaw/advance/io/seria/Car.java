package org.shaw.advance.io.seria;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 单例模式序列化
 */
public class Car implements Serializable {

    private String wheel;

    private String tyre;

    private Car() {
    }

    public Car(String wheel, String tyre) {
        this.wheel = wheel;
        this.tyre = tyre;
    }

    private static class InstanceCar{
        private static final Car instatnce = new Car("方向盘","轮胎");
    }

    public static Car getCar(){
        return InstanceCar.instatnce;
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheel='" + wheel + '\'' +
                ", tyre='" + tyre + '\'' +
                '}';
    }

    // 直接返回对象替代序列化对象
    private Object readResolve() throws ObjectStreamException {
        return InstanceCar.instatnce;
    }
}
