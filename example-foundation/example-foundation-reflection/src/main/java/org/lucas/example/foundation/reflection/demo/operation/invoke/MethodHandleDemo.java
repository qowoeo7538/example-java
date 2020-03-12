package org.lucas.example.foundation.reflection.demo.operation.invoke;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;

/**
 * 即方法句柄,从性能角度上说，MethodHandle api要比反射快很多因为访问检查在创建的时候就已经完成了，而不是像反射一样等到运行时候才检查。
 */
public class MethodHandleDemo {

    @Test
    public void demoMethodHandle() {
        // 所有方法的
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // public 方法
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        /**
         * @Param 1.返回类型
         * @Param 2.参数类型
         */
        MethodType mt = MethodType.methodType(List.class, Object[].class);
    }

    @Test
    public void demoFindVirtual() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMH = lookup.findVirtual(String.class, "replace", mt);

        // invoke 会在调用的时候进行返回值和参数类型的转换工作
        String output = (String) replaceMH.invoke("jovo", Character.valueOf('o'), 'a');
        Assertions.assertEquals("java", output);
    }

}
