package org.shaw.windows.process;

import org.shaw.windows.util.ProcessUtils;

/**
 * @create: 2018-01-06
 * @description:
 */
public class KillPidDemo {
    public static void main(String[] args) {
        killProc(ProcessUtils.getPid("expApplication.exe"));
    }

    private static void killProc(String pid) {
        try {
            Runtime.getRuntime().exec("taskkill /F /PID " + pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
