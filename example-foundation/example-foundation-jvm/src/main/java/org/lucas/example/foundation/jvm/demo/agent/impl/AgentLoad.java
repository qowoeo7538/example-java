package org.lucas.example.foundation.jvm.demo.agent.impl;

import java.lang.instrument.Instrumentation;

public class AgentLoad {

    /**
     * 当Agent启动时，首先会触发对premain()函数的调用
     *
     * @param args 参数
     * @param inst 增强器
     * @throws Throwable
     */
    public static void premain(String args, Instrumentation inst) throws Throwable {
        // 1 注册ClassFileTransformer实例,用于返回增强内容。
        // inst.addTransformer((a, b, c, d, e) -> 增强后的字节码, true);
    }

}
