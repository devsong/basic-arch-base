package io.github.devsong.base.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author guanzhisong
 *
 */
public class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String threadPoolName;

    public NamedThreadFactory() {
        this.threadPoolName = "basic-arch";
    }

    public NamedThreadFactory(String name) {
        this.threadPoolName = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadPoolName + '-' + threadNumber.getAndIncrement());
        return thread;
    }
}
