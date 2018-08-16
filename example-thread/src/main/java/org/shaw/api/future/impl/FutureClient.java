package org.shaw.api.future.impl;

/**
 * @create: 2017-11-13
 * @description:
 */
public class FutureClient {
    public Data request(final String request) {
        final FutureData futureData = new FutureData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData(request);
                futureData.setRealData(realData);
            }
        }).start();

        return futureData;
    }
}
