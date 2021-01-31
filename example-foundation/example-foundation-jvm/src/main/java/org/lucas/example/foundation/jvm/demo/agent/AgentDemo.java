package org.lucas.example.foundation.jvm.demo.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * 基于JVMTI(Java Virtual Machine Tool Interface，Java虚拟机工具接口)规范的Instrumentation-API，使
 * 能够使用Instrumentation来构建一个独立于应用的Agent程序，以便于监测和协助运行在JVM上的程序。使用Instrumentation可以
 * 在运行期对类定义进行修改和替换。
 */
public class AgentDemo {

    /**
     * 命令行参数“-javaagent”来指定目标jar文件
     * <p>
     * 执行命令“-XX:+TraceClassLoading”发现，当目标类被加载进方法区之前，会
     * 由Instrumentation的实现负责回调transform()函数执行增强（已加载的类则
     * 需要手动触发Instrumentation.retransformClasses()函数显式重定义）。
     */
    @Test
    public void demoAgentOnLoad() {

    }

    /**
     * JVM启动后，且所有类型均已全部完成加载之后再对目标类进行重定义。
     */
    @Test
    public void demoAgentOnAttach() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        var vm = VirtualMachine.attach("pid");
        if (Objects.nonNull(vm)) {
            try {
                vm.loadAgent("path", "name");
            } finally {
                vm.detach();
            }
        }
    }

}
