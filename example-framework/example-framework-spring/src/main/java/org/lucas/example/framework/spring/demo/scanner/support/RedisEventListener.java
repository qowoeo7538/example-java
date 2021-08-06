package org.lucas.example.framework.spring.demo.scanner.support;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Inherited
public @interface RedisEventListener {

    /**
     * 监听 key  与  监听事件类型 互斥
     */
    String[] keys() default {};

    /**
     * 监听事件类型 与 监听 key 互斥
     */
    EventType[] eventType() default {};

    /**
     * -1 代表所有库
     */
    int database() default -1;

    /**
     * 组
     */
    String group() default "defaultGroup";

}
