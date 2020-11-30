package com.sohu.inputmethod.foreign.base.thread;

import android.os.Handler;
import android.os.Looper;

/**
 * @author jiany on 2020/4/10
 */
final class UiExecutorThread implements IExecutorThread {
    final Handler mHandler;

    // 都是在同一个线程里面，而且是顺序执行，不用考虑同步
    private String mCurrentRunnableName;

    UiExecutorThread() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(Runnable task) {
        if (task != null) {
            mHandler.post(task);
        }
    }

    @Override
    public void quit() {
        // 主线程不能quit，只能删除所有的任务
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public String getCurRunnableName() {
        return mCurrentRunnableName;
    }

    @Override
    public void setCurRunnableName(String runnableName) {
        mCurrentRunnableName = runnableName;
    }

    @Override
    public String getThreadName() {
        return mHandler.getLooper().getThread().getName();
    }
}
