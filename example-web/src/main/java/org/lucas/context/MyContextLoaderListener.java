package org.lucas.context;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Created by joy on 17-4-22.
 */

public class MyContextLoaderListener extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.setInitParameter("contextConfigLocation", "/WEB-INF/configs/spring/applicationContext.xml");
        this.initWebApplicationContext(context);
    }
}
