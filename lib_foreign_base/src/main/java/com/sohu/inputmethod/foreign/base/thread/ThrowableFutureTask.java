package com.sohu.inputmethod.foreign.base.thread;

import java.util.concurrent.ExecutionException;

/**
 * A special FutureTask to throw RuntimeException when error.
 * @author jiany on 2020/4/10
 * @param <V> The type of parameter.
 */
public class ThrowableFutureTask<V> extends NamedFutureTask<V> {

    public ThrowableFutureTask(final IExecutorThread executorThread, Runnable r, String runnableName, String threadName, long waitTimeout, long execTimeout) {
        super(executorThread, r, runnableName, threadName, waitTimeout, execTimeout);
    }

    @Override
    protected void done() {
        try {
            if (!isCancelled()) {
                get();
            }
        } catch (InterruptedException | ExecutionException e) {
            // There is something wrong with the execution, just throw exception.
            throw new RuntimeException(e.getCause().getMessage(), e.getCause());
        }
    }
}
