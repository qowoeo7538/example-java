package org.shaw.nio.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by joy on 17-1-25.
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("Session start!");
        HttpSession session = httpSessionEvent.getSession();
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("Session shutdown!");
    }
}

