package org.shaw.base.seria.single;

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

    private static class InstanceCar {
        private static final Car INSTATNCE = new Car("方向盘", "轮胎");
    }

    public static Car getCar() {
        return InstanceCar.INSTATNCE;
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheel='" + wheel + '\'' +
                ", tyre='" + tyre + '\'' +
                '}';
    }

    /**
     * 直接返回对象替代序列化对象
     *
     * @return Object
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException {
        return InstanceCar.INSTATNCE;
    }
}
