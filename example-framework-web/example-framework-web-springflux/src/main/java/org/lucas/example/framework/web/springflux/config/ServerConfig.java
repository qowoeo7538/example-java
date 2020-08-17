package org.lucas.example.framework.web.springflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

import javax.servlet.Servlet;
import java.io.File;


/**
 * SpringBootApplication 三个注解的组合:
 * 1.@Configuration 标明该类使用Spring基于Java的配置.
 * 2.@ComponentScan 组件扫描,扫描组件并注册为Spring应用程序上下文里的Bean。
 * 3.@EnableAutoConfiguration 开启了Spring Boot自动配置.
 */
@SpringBootApplication(scanBasePackages = "org.lucas.example.framework.web.springflux")
@EnableConfigurationProperties(AppConfig.class)
public class ServerConfig {


}
