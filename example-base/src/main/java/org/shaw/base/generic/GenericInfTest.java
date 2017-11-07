package org.shaw.base.generic;

/**
 * Created by joy on 17-2-6.
 */
public class GenericInfTest {
    public static void main(String[] args) {
        GenericImpl<String> generic = new GenericImpl("hahahhh");
        System.out.println(generic.getInfo());
    }
}

interface GenericInf<T> {
    public void say();
}

class GenericImpl<T> implements GenericInf<T> {
    private String info;

    public GenericImpl(){}

    public GenericImpl(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void say() {

    }
}


