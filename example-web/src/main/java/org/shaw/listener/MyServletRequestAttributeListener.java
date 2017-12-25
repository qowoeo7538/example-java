package org.shaw.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by joy on 17-1-25.
 */
@WebListener
public class MyServletRequestAttributeListener implements ServletRequestAttributeListener{
    public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        System.out.println("ServletRequest attributeAdded:"+servletRequestAttributeEvent.getName());
    }

    public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        System.out.println("ServletRequest attributeRemoved:"+servletRequestAttributeEvent.getName());
    }

    public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        System.out.println("ServletRequest attributeReplaced:"+servletRequestAttributeEvent.getName());
    }
}
