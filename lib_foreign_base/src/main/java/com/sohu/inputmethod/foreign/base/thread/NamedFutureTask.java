package com.sohu.inputmethod.foreign.base.thread;

import android.os.SystemClock;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author jiany on 2020/4/10
 * @param <V>
 */
public class NamedFutureTask<V> extends FutureTask<V> implements Named{

    private final String mRunnableName;
    private final String mThreadName;

    private volatile long mStartTime = -1L;
    private volatile long mEndTime = -1L;

    private final long mPostTime;
    private final long mWaitTimeout;
    private final long mExecTimeout;

    private final IExecutorThread mExecutor;

    public NamedFutureTask(IExecutorThread executorThread, Runnable r, String runnableName, String threadName, long waitTimeout, long execTimeout) {
        super(r, null);
        mRunnableName = runnableName;
        mThreadName = threadName;
        mWaitTimeout = waitTimeout;
        mExecTimeout = execTimeout;
        mPostTime = SystemClock.uptimeMillis();
        mExecutor = executorThread;
    }

    public NamedFutureTask(IExecutorThread executorThread, Callable callable, String callableName, String threadName, long waitTimeout, long execTimeout) {
        super(callable);
        mRunnableName = callableName;
        mThreadName = threadName;
        mWaitTimeout = waitTimeout;
        mExecTimeout = execTimeout;
        mPostTime = SystemClock.uptimeMillis();
        mExecutor = executorThread;
    }

    @Override
    public String getName() {
        return "Runnable:" + getRunnableName();
    }

    public String getRunnableName() {
        return mRunnableName;
    }

    public String getThreadName() {
        return mThreadName;
    }

    @Override
    public void run() {
        mExecutor.setCurRunnableName(getRunnableName());
        mStartTime = SystemClock.uptimeMillis();
        super.run();
        mEndTime = SystemClock.uptimeMillis();
    }

    public long getStartTime() {
        return mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public long getPostTime() {
        return mPostTime;
    }

    public boolean isTimeout() {
        return (mStartTime - mPostTime > mWaitTimeout) ||
                (mEndTime - mStartTime > mExecTimeout);
    }
}
