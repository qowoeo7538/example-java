package org.lucas.example.jvm.gc.impl;

public class Book {

    private boolean checkedOut;

    public Book(boolean checkOut) {
        this.checkedOut = checkOut;
    }

    public void checkIn() {
        checkedOut = false;
    }

    @Override
    protected void finalize() throws Throwable {

        try {
            if (checkedOut) {
                System.out.println("Error: checked out");
            }
        } finally {
            try {
                super.finalize();
            } catch (Throwable e) {
                // 如果finalize方法抛出未捕获的异常，该异常被忽略，并终止对象的回收。
                e.printStackTrace();
            }
        }
    }
}
