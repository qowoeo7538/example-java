package org.shaw.thread.concurrent.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @create: 2017-11-06
 * @description:
 */
public class ApplicationStartupUtil {

    private static CountDownLatch _latch;

    private static List<BaseHealthChecker> _service;

    public static boolean checkExternalServices() throws Exception {
        _latch = new CountDownLatch(3);
        _service = new ArrayList(3);
        _service.add(new NetworkHealthChecker("一号线程", _latch));
        _service.add(new NetworkHealthChecker("二号线程", _latch));
        _service.add(new NetworkHealthChecker("三号线程", _latch));

        ExecutorService executor = Executors.newFixedThreadPool(_service.size());
        for (final BaseHealthChecker v : _service) {
            executor.execute(v);
        }
        _latch.await();
        executor.shutdown();
        for (final BaseHealthChecker v : _service) {
            if (!v.isServiceUp()) {
                return false;
            }
        }
        return true;
    }
}
