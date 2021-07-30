package org.lucas.example.foundation.thread.kata.futrue.support;

/**
 * 真实任务（用户真实想得到的数据从这里获取)
 */
public class Task implements Data {

    private String result;

    /**
     * 具体任务
     *
     * @param request
     */
    public Task(String request) {
        System.out.println("根据" + request + "进行查询:" + "SELECT " + request + " FROM table");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("操作完毕,获取结果");
        result = "查询结果:" + request + " 结果集";
    }

    @Override
    public String getRealData() {
        return result;
    }
}
