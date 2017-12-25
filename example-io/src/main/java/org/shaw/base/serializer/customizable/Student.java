package org.shaw.base.serializer.customizable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by joy on 17-2-8.
 */
public class Student implements Serializable {
    private String stuno;
    private String stuname;
    /**
     * 该元素不会进行jvm默认的序列化,也可以自己完成这个元素的序列化
     */
    private transient int stuage;

    public Student(String stuno, String stuname, int stuage) {
        super();
        this.stuno = stuno;
        this.stuname = stuname;
        this.stuage = stuage;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStunae(String stuname) {
        this.stuname = stuname;
    }

    public int getStuage() {
        return stuage;
    }

    public void setStuage(int stuage) {
        this.stuage = stuage;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuno='" + stuno + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stuage=" + stuage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return stuage == student.stuage &&
                Objects.equals(stuno, student.stuno) &&
                Objects.equals(stuname, student.stuname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stuno, stuname, stuage);
    }

    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        //把jvm能默认序列化的元素进行序列化操作
        s.defaultWriteObject();
        //自己完成stuage的序列化
        s.writeInt(stuage);
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        //把jvm能默认反序列化的元素进行反序列化操作
        s.defaultReadObject();
        //自己完成stuage的反序列化操作
        this.stuage = s.readInt();
    }
}
