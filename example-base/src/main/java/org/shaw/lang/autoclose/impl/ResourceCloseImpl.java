package org.shaw.lang.autoclose.impl;

import sun.net.www.protocol.http.Handler;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.URL;

/**
 * @create: 2018-06-11
 * @description:
 */
public class ResourceCloseImpl extends HttpURLConnection implements AutoCloseable {

    private final URL url;

    private final HttpURLConnection httpURLConnection;

    public ResourceCloseImpl(URL url) {
        super(url, null, new Handler());
        this.url = url;
        try {
            this.httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        httpURLConnection.disconnect();
        System.out.println("资源关闭完成!");
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }
}
