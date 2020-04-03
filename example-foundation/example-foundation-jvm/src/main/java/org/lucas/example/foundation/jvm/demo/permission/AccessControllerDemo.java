package org.lucas.example.foundation.jvm.demo.permission;

import org.lucas.example.foundation.jvm.demo.permission.impl.FileAccess;

/**
 * AccessController 作用:
 * 1) 根据当前有效的安全策略决定是否允许或拒绝对关键资源的访问 (AccessController.checkPermission(Permission))
 * 2) 将代码标记为 "privileged", 进行后续访问判断 (AccessController.doPrivileged)
 * 3) 获取当前调用上下文的 "snapshot", 对其他上下文的访问控制访问策略。(AccessController.getContext)
 * <p>
 * 判断是否授权的基本算法如下:
 * 1) 如果调用链中的某个调用程序没有所需的权限, 将抛出 AccessControlException;
 * 2) 调用程序访问另一个有该权限域里程序的方法, 并且此方法标记为有访问 "privileged" 即被授予权限;
 * 3) 调用程序所调用（直接或间接）的后续对象都有上述权限即被授予权限;
 */
public class AccessControllerDemo {

    public static void main(String[] args) {
        // 打开系统安全权限检查开关(默认拥有所有权限)
        System.setSecurityManager(new SecurityManager());
        // 根据安全策略创建 temp1.txt 文件
        FileAccess.doPrivilegedAction("temp1.txt");
    }

}
