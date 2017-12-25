package org.shaw.core.task;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * @create: 2017-12-25
 * @description: 自定义线程
 */
public class CustomizableThread extends CustomizableThreadFactory {

    public CustomizableThread() {
        super();
    }

    public CustomizableThread(String threadNamePrefix) {
        super(threadNamePrefix);
    }
}
