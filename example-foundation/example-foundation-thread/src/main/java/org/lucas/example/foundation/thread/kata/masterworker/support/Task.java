package org.lucas.example.foundation.thread.kata.masterworker.support;

/**
 * 模拟任务实体
 */
public class Task {

    private int id;

    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
