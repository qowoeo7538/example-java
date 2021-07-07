package org.lucas.example.common.pojo.entity;

import org.lucas.example.common.pojo.ComparableEntity;

public class Teacher implements ComparableEntity<Teacher> {

    private Integer no;

    private String name;

    private Integer age;

    private String seminary;

    public Teacher() {
    }

    public Teacher(int no, String name, int age, String seminary) {
        this.no = no;
        this.name = name;
        this.age = age;
        this.seminary = seminary;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSeminary() {
        return seminary;
    }

    public void setSeminary(String seminary) {
        this.seminary = seminary;
    }


    public boolean equals(Teacher t) {
        if (this.no == t.no) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "编号为" + this.no + "、姓名为" + this.name + "、年龄为" + this.age + "的" + this.seminary + "学院的老师 ";
    }

    @Override
    public int compareTo(Teacher o) {
        Teacher t = o;
        if (no < t.no) {
            return -1;
        } else if (no > t.no) {
            return 1;
        }
        return 0;
    }

}
