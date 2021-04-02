package org.lucas.example.foundation.io.kata.longpolling.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PullRequestHoldService extends ServiceThread {

    private final ResponseController responseController;

    private static final String SEPARATOR = "@";

    private ConcurrentMap<String/*  group@name */, ManyPullRequest> pullRequestTable = new ConcurrentHashMap<>(1024);

    public PullRequestHoldService(final ResponseController responseController) {
        this.responseController = responseController;
    }

    /**
     * 添加相同请求
     *
     * @param group   组
     * @param name    名
     * @param request 请求
     */
    public void suspendPullRequest(final String group, final String name, final PullRequest request) {
        String key = buildKey(group, name);
        ManyPullRequest mpr = this.pullRequestTable.get(key);
        if (mpr == null) {
            mpr = new ManyPullRequest();
            ManyPullRequest prev = this.pullRequestTable.putIfAbsent(key, mpr);
            if (prev != null) {
                mpr = prev;
            }
        }
        mpr.addPullRequest(request);
    }

    @Override
    public void run() {
        this.waitForRunning(5 * 1000);
        long beginLockTimestamp = System.currentTimeMillis();
        this.checkHoldRequest();
    }

    /**
     * 请求参数校验
     */
    private void checkHoldRequest() {
        for (String key : this.pullRequestTable.keySet()) {
            String[] kArray = key.split(SEPARATOR);
            if (2 == kArray.length) {
                String group = kArray[0];
                String name = kArray[1];
                final long maxVersion = this.responseController.getMessageStore().getMaxVersion(group, name);
                try {
                    this.notifyMessageArriving(group, name, maxVersion);
                } catch (Throwable e) {
                    throw new RuntimeException("请求参数校验失败");
                }
            }
        }
    }

    public void notifyMessageArriving(String group, String name, Long maxVersion) {
        String key = this.buildKey(group, name);
        ManyPullRequest mpr = this.pullRequestTable.get(key);
        if (Objects.nonNull(mpr)) {
            List<PullRequest> requestList = mpr.cloneListAndClear();
            if (Objects.nonNull(requestList)) {
                List<PullRequest> replayList = new ArrayList<>();
                for (PullRequest request : requestList) {
                    long newVersion = maxVersion;
                    if (newVersion <= request.getVersion()) {
                        maxVersion = this.responseController.getMessageStore().getMaxVersion(group, name);
                    }
                    if (maxVersion > request.getVersion()) {

                    }
                }
            }
        }
    }

    private String buildKey(final String topic, final String queueId) {
        StringBuilder sb = new StringBuilder();
        sb.append(topic);
        sb.append(SEPARATOR);
        sb.append(queueId);
        return sb.toString();
    }
}
