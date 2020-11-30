package com.sohu.inputmethod.foreign.base.thread;

import android.os.Process;
import android.os.SystemClock;

/**
 * @author dongjianye
 */
public class ImeWatchDog extends Thread {

    private final static long CHECK_INTERVAL = 5 * 60 * 1000; // 5分钟，同样的超时，跟CheckTimeout对比

    private final IExecutorThread mExecutor;

    public ImeWatchDog(IExecutorThread executor) {
        super("ImeWatchDogThread");
        mExecutor = executor;
    }

    private class TimeoutRunnable implements Runnable {

        private volatile long mStartTime;
        private volatile long mExecuteTime;
        private volatile boolean mCompleted;

        @Override
        public void run() {
            // 保证更改的值的可见性
            synchronized (this) {
                mCompleted = true;
                mExecuteTime = SystemClock.uptimeMillis();
            }
        }

        void schedule() {
            mCompleted = false;
            mStartTime = SystemClock.uptimeMillis();
            mExecutor.execute(this);
        }

        synchronized boolean isBlocked() {
            return !mCompleted || (mExecuteTime - mStartTime >= CHECK_INTERVAL);
        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            synchronized (this) {
                final TimeoutRunnable checker = new TimeoutRunnable();
                checker.schedule();

                long timeout = CHECK_INTERVAL;

                // NOTE: We use uptimeMillis() here because we do not want to increment the time we
                // wait while asleep. If the device is asleep then the thing that we are waiting
                // to timeout on is asleep as well and won't have a chance to run, causing a false
                // positive on when to kill things.
                long start = SystemClock.uptimeMillis();

                while (timeout > 0) {
                    try {
                        wait(timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timeout = CHECK_INTERVAL - (SystemClock.uptimeMillis() - start);
                }

                if (!checker.isBlocked()) {
                    continue;
                }
            }
        }
    }
}
