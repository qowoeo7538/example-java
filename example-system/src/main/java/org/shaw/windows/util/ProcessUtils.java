package org.shaw.windows.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @create: 2018-01-06
 * @description:
 */
public class ProcessUtils {

    /**
     * 获取进程PID
     *
     * @param processName 进程名
     * @return
     */
    public static String getPid(String processName) {
        String processId = "";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Runtime.getRuntime()
                        .exec("tasklist /nh /FI \"IMAGENAME eq " + processName + "\"")
                        .getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processName)) {
                    String process = line;
                    int lena = process.lastIndexOf("Console");
                    processId = process.substring(processName.length(),
                            lena);
                    if (!"".equals(processId) && processId != null) {
                        processId = processId.trim();
                        return processId;
                    }
                }
            }
        } catch (IOException e) {
            return "";
        }
        return processId;
    }

    /**
     * 根据PID杀死进程
     *
     * @param pid
     */
    private static void killProc(String pid) {
        try {
            Runtime.getRuntime().exec("taskkill /F /PID " + pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
