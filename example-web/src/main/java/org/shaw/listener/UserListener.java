package org.shaw.listener;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/**
 * Created by joy on 17-1-29.
 */
public class UserListener implements HttpSessionBindingListener ,HttpSessionActivationListener,Serializable {

    private String userName;

    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("User valueBound:"+httpSessionBindingEvent.getName());
    }

    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("User valueUnbound:"+httpSessionBindingEvent.getName());
    }

    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("sessionWillPassivate:"+httpSessionEvent.getSource());
    }

    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("sessionDidActivate:"+httpSessionEvent.getSource());
    }
}
