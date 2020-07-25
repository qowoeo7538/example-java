package org.lucas.example.foundation.web.servlet.async.listener;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;

public class MyAsyncListener implements AsyncListener {

    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        System.out.println("onComplete");
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        System.out.println("onTimeout");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        System.out.println("onError");
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        System.out.println("onStartAsync");
    }
}
