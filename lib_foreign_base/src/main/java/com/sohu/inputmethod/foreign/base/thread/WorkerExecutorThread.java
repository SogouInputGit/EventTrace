package com.sohu.inputmethod.foreign.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiany on 2020/4/10
 * 单线程，顺序执行
 */
final class WorkerExecutorThread implements IExecutorThread {

    final ExecutorService mExecuter;
    final String mThreadName;
    private String mCurRunnableName;

    WorkerExecutorThread(String suffixName) {
        final ForeignThreadFactory threadFactory = new ForeignThreadFactory(suffixName);
        mThreadName = threadFactory.getThreadName();
        mExecuter = new ThreadPoolExecutor(0, 1,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory);
    }

    @Override
    public void execute(Runnable task) {
        mExecuter.execute(task);
    }

    @Override
    public void quit() {
        mExecuter.shutdown();
    }

    @Override
    public String getCurRunnableName() {
        return mCurRunnableName;
    }

    @Override
    public String getThreadName() {
        return mThreadName;
    }

    @Override
    public void setCurRunnableName(String runnableName) {
        mCurRunnableName = runnableName;
    }
}
