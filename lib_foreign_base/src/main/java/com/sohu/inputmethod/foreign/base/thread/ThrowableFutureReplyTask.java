package com.sohu.inputmethod.foreign.base.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


/**
 * A special FutureTask to post callback back to calling thread.
 * @author jiany on 2020/4/10
 * @param <V> The type of parameter.
 */
public class ThrowableFutureReplyTask<V> extends NamedFutureTask<V> {

    /** The underlying callable; nulled out after running */
    private final Callback<V> mCallback;
    private final Executor mExecutor;
    private final ThrowableWithParamTask<V> mTask;

    public ThrowableFutureReplyTask(final IExecutorThread executorThread,
                                    Callable<V> callable,
                                    Callback<V> callback, Executor replyExecutor,
                                    String runnableName, String threadName,
                                    long waitTimeout, long execTimeout) {
        super(executorThread, callable, runnableName, threadName, waitTimeout, execTimeout);
        if (callback == null || replyExecutor == null) {
            throw new NullPointerException();
        }
        this.mCallback = callback;
        this.mExecutor = replyExecutor;

        mTask = new ThrowableWithParamTask<V>(executorThread, new Runnable() {
            @Override
            public void run() {
                final Callback<V> c = mCallback;
                try {
                    c.callback(mTask.getParam());
                } catch (RuntimeException runtimeEx) {
                    throw runtimeEx;
                } catch (Exception ex) {
                    throw new RuntimeException(ex.getMessage(), ex);
                }
            }
        }, runnableName, "reply", waitTimeout, execTimeout);
    }

    @Override
    protected void done() {
        try {
            if (isCancelled()) {
                return;
            }
            final V result = get();
            mTask.setParam(result);
            mExecutor.execute(mTask);
        } catch (InterruptedException | ExecutionException e) {
            // There is something wrong with the execution, just throw exception.
            throw new RuntimeException(e.getCause().getMessage(), e.getCause());
        }
    }

    private static class ThrowableWithParamTask<V> extends ThrowableFutureTask<V> {
        private volatile V mParam;

        public void setParam(V param) {
            mParam = param;
        }

        public V getParam() {
            return mParam;
        }

        public ThrowableWithParamTask(final IExecutorThread executorThread, Runnable r, String runnableName, String threadName,
                                      long waitTimeout, long execTimeout) {
            super(executorThread, r, runnableName, threadName, waitTimeout, execTimeout);
        }
    }
}
