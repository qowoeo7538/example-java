package org.lucas.example.foundation.jvm.demo.permission.support;

import java.io.File;
import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @create: 2018-03-18
 * @description:
 */
public abstract class FileAccess {

    private final static String FOLDER_PATH = "C:\\Users\\john\\Desktop";

    public static void makeFile(String fileName) {
        try {
            File fs = new File(FOLDER_PATH + File.separator + fileName);
            fs.createNewFile();
        } catch (AccessControlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doPrivilegedAction(final String fileName) {
        // 用特权访问方式创建文件
        AccessController.doPrivileged((PrivilegedAction<String>) () -> {
            makeFile(fileName);
            return null;
        });
    }
}
