package org.shaw.lang.autoclose;

import org.shaw.lang.autoclose.impl.ResourceCloseImpl;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 利用 try with resource 实现资源自动关闭
 */
public class ResourceCloseDemo {

    private static final String HOST = "http://10.1.5.65/sort/pac_message_receiver.do?";

    public static void main(String[] args) {
        try (ResourceCloseImpl resourceClose = new ResourceCloseImpl(new URL(HOST))) {
            HttpURLConnection httpConnection = resourceClose.getHttpURLConnection();
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (StringUtils.isEmpty(response)) {
                System.out.println("返回空!!");
            }
            System.out.println(response.toString());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
