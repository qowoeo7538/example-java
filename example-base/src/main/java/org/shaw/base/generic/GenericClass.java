package org.shaw.base.generic;

/**
 * 集合的泛型，编译之后泛型将不存在，可以通过反射绕过编译。
 * Created by joy on 17-2-5.
 */
class Pinter <K,T> { //泛型对象
    private K x;
    private T y;

    public Pinter() {
    }

    public Pinter(K x, T y) {
        this.x = x;
        this.y = y;
    }

    public K getX() {
        return x;
    }

    public void setX(K x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x="+getX()+",y="+getY();
    }
}

public class GenericClass {
    public static void main(String[] args) {
        Pinter<String,Integer> pinter = new Pinter<String,Integer>();
        pinter.setX("100");
        pinter.setY(50);
        PinterPrintln(pinter);

        String[] strings = {"dsfsd","sdfsdf"};
        genericMethod(strings);
    }

    public static void PinterPrintln (Pinter<?,?> pinter){ //通配符
        System.out.println(pinter);
    }

    public static <T> T[] genericMethod(T[] t){ //泛型方法与泛型数组
        System.out.println(t.toString());
        return t;
    }
}
