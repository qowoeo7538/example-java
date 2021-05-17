package org.lucas.example.foundation.thread.demo.lock;

import org.junit.Test;

/**
 * 对象锁 和 类锁
 * (多个对象多把锁、相互不影响)
 * (访问静态变量则需要多个对象使用一把锁、那么需要将锁升级为类锁)
 */
public class SynchronizedDemo {

    @Test
    public void testObjectLock(){

    }

}
