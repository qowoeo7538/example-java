package org.lucas.example.foundation.jvm.demo.agent.impl;

import java.lang.instrument.Instrumentation;

public class AgentAttach {

    /**
     * 代理入口函数
     *
     * @param agentArgs 代理参数
     * @param inst      增强器
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        // 1 注册ClassFileTransformer实例,用于返回增强内容。
        // inst.addTransformer((a,b,c,d,e) -> 增强后的字节码, true);
        // 2 对目标类进行重定义
        // inst.retransformClasses(Class.forName(agentArgs));
    }

}
