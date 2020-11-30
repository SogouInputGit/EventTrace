package com.sohu.inputmethod.foreign.base.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiany on 2020/4/10
 */
public class ForeignThreadFactory implements ThreadFactory {
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String threadName;

    public ForeignThreadFactory(final String suffixName) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        threadName = "sogou-foreign-thread-" + suffixName + "-" + POOL_NUMBER.getAndIncrement() + "-thread-" + THREAD_NUMBER.getAndIncrement();
    }

    String getThreadName() {
        return threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, threadName,
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
