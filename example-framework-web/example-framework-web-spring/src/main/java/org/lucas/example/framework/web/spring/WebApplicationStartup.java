package org.lucas.example.framework.web.spring;

import org.lucas.example.framework.web.spring.config.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * 应用启动引导类
 */
public class WebApplicationStartup {

    public static void main(String[] args) {
        // primarySource：配置类
        // args...：命令行参数
        ConfigurableApplicationContext ctx = SpringApplication.run(ServerConfig.class, args);
        System.out.println("----------------- spring beans ------------------------\n");
        System.out.println(iocInfo(ctx));
        System.out.println("------------------ end -----------------------");
    }

    private static String iocInfo(ConfigurableApplicationContext ctx) {
        StringBuilder allBeanStr = new StringBuilder(1024);
        String[] beanNames = ctx.getBeanDefinitionNames();
        allBeanStr.append("Bean 总数：" + beanNames.length + "\n");
        Arrays.sort(beanNames);
        for (String bn : beanNames) {
            allBeanStr.append(bn + "\n");
        }
        return allBeanStr.toString();
    }
}
