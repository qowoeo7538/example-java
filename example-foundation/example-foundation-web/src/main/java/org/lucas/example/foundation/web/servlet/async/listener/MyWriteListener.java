package org.lucas.example.foundation.web.servlet.async.listener;

import javax.servlet.WriteListener;
import java.io.IOException;

public class MyWriteListener implements WriteListener {

    /**
     * 当可以写时通知容器线程调用该方法
     */
    @Override
    public void onWritePossible() throws IOException {

    }

    @Override
    public void onError(Throwable t) {

    }
}
