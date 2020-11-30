package com.sohu.inputmethod.foreign.base.thread;

import android.os.Looper;
import android.text.TextUtils;

import com.sohu.inputmethod.foreign.base.BuildConfig;
import com.sohu.inputmethod.foreign.base.deadlock.SafeReentrantLock;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

/**
 * @author jiany on 2020/4/10
 */
public class ImeThread {

    private static final String TAG = "ImeThread";
    private static final SafeReentrantLock INSTANCE_LOCK = new SafeReentrantLock();

    public static final long DEFAULT_WAIT_TIMEOUT = 19 * 1000; // 19秒
    public static final long DEFAULT_EXEC_TIMEOUT = 5 * 1000; // 5秒

    public enum ID {
        // The main thread in the ime.
        UI("UI", true, false),

        // This is the thread that interacts with the file system.
        FILE("FILE", false, false),

        // This is the thread that processes non-blocking or blocking IO, i.e. IPC and network.
        IO("IO", false, false),

        // This identifier does not represent a thread.  Instead it counts the
        // number of well-known threads.  Insert new well-known threads before this
        // identifier.
        ID_COUNT("NULL", false, false);

        private final String mName;
        private final boolean mUIThread;
        private final boolean mCheckTimeOut;

        ID(String name, boolean uiThread, boolean checkTimeOut) {
            mName = name;
            mUIThread = uiThread;
            mCheckTimeOut = checkTimeOut;
        }

        public String getName() {
            return mName;
        }

        public boolean isUiThread() {
            return mUIThread;
        }

        public boolean isCheckTimeOut() {
            return mCheckTimeOut;
        }
    }

    public final static IExecutorThread[] EXECUTOR_THREADS = new IExecutorThread[ID.ID_COUNT.ordinal()];

    private static volatile boolean smInitialized = false;

    /**
     * Initialize the message loop.
     *
     * @throws RuntimeException
     */
    public static void initialize() throws RuntimeException {
        if (smInitialized) {
            return;
        }

        INSTANCE_LOCK.lock();
        try {
            if (smInitialized) {
                return;
            }
            if (Looper.getMainLooper() == null) {
                throw new RuntimeException("IMEThreadHandler should init on thread with looper!");
            }

            for (int i = 0; i < EXECUTOR_THREADS.length; ++i) {
                final ID id = ID.values()[i];
                final IExecutorThread executorThread = id.isUiThread() ? new UiExecutorThread() :
                        new WorkerExecutorThread(id.getName());
                EXECUTOR_THREADS[i] = executorThread;
                if (id.isCheckTimeOut()) {
                    new ImeWatchDog(executorThread).start();
                }
            }

            smInitialized = true;
        } finally {
            INSTANCE_LOCK.unlock();
        }
    }

    private static IExecutorThread getExecutorThreadById(ID identifier) {
        final int idx = identifier.ordinal();
        if (idx < 0 || idx >= EXECUTOR_THREADS.length) {
            throw new RuntimeException("Invalide ID params");
        }

        return EXECUTOR_THREADS[idx];
    }

    public static Future<?> postTask(ID identifier, Runnable runnable, String runnableName) throws RuntimeException {
        return postTask(identifier, runnable, runnableName, DEFAULT_WAIT_TIMEOUT, DEFAULT_EXEC_TIMEOUT);
    }

    /**
     * Post task to other thread.
     *
     * @param identifier Thread identifier, currently allow UI, FILE, IO.
     * @param runnable   Task that we need to call on target thread.
     * @param waitTimeout Max wait time before execute
     * @param execTimeout Max exec time
     * @return WeakReference<CancellableRunnable> for the posted task.
     */
    public static Future<?> postTask(ID identifier, Runnable runnable, String runnableName, long waitTimeout, long execTimeout) throws RuntimeException {
        if (!smInitialized) {
            initialize();
        }

        final IExecutorThread executorThread = getExecutorThreadById(identifier);
        if (executorThread == null) {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("ImeThread.postTask must depend on valid ID!");
            } else {
                return null;
            }
        }
        RunnableFuture<Void> ftask = new ThrowableFutureTask<>(executorThread, runnable, runnableName, identifier.getName(), waitTimeout, execTimeout);

        executorThread.execute(ftask);

        return ftask;
    }

    public static <T> Future<T> postTaskAndReply(ID identifier, Callable<T> callable, String callableName, Callback<T> reply) {
        return postTaskAndReply(identifier, callable, callableName, reply, DEFAULT_WAIT_TIMEOUT, DEFAULT_EXEC_TIMEOUT);
    }

    public static <T> Future<T> postTaskAndReply(ID identifier, Callable<T> callable, String callableName, ID replyIdentifier, Callback<T> reply) {
        return postTaskAndReply(identifier, callable, callableName, replyIdentifier, reply, DEFAULT_WAIT_TIMEOUT, DEFAULT_EXEC_TIMEOUT);
    }

    /**
     * Post task to other thread.
     *
     * @param identifier Thread identifier, currently allow UI, FILE, IO.
     * @param callable   Task that we need to call on target thread.
     * @param waitTimeout Max wait time before execute
     * @param execTimeout Max exec time
     * @return WeakReference<CancellableRunnable> for the posted task.
     */
    public static <T> Future<T> postTaskAndReply(ID identifier, Callable<T> callable, String callableName, ID replyIdentifier, Callback<T> reply, long waitTimeout, long execTimeout)
            throws RuntimeException {
        if (!smInitialized) {
            initialize();
        }

        Executor currentMessageLoop = getExecutorThreadById(replyIdentifier);

        if (currentMessageLoop == null) {
            throw new RuntimeException("ImeThread.postTaskAndReply must called on named thread!");
        }

        final IExecutorThread executorThread = getExecutorThreadById(identifier);
        if (executorThread == null) {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("ImeThread.postTaskAndReply must depend on valid ID!");
            } else {
                return null;
            }
        }

        RunnableFuture<T> ftask = new ThrowableFutureReplyTask<>(executorThread, callable, reply, currentMessageLoop, callableName, identifier.getName(), waitTimeout, execTimeout);

        executorThread.execute(ftask);

        return ftask;
    }


    /**
     * Post task to other thread.
     *
     * @param identifier Thread identifier, currently allow UI, FILE, IO.
     * @param callable   Task that we need to call on target thread.
     * @param waitTimeout Max wait time before execute
     * @param execTimeout Max exec time
     * @return WeakReference<CancellableRunnable> for the posted task.
     */
    public static <T> Future<T> postTaskAndReply(ID identifier, Callable<T> callable, String callableName, Callback<T> reply, long waitTimeout, long execTimeout)
            throws RuntimeException {
        if (!smInitialized) {
            initialize();
        }

        Executor currentMessageLoop = null;
        final long currThreadId = Thread.currentThread().getId();
        final String curThreadName = Thread.currentThread().getName();
        final boolean isCurMainThread = currThreadId == Looper.getMainLooper().getThread().getId();

        // 获取当前线程，reply的要在当前thread里面去执行
        if (isCurMainThread) {
            currentMessageLoop = getExecutorThreadById(ID.UI);
        } else {
            for (IExecutorThread executorThread : EXECUTOR_THREADS) {
                if (TextUtils.equals(curThreadName, executorThread.getThreadName())) {
                    currentMessageLoop = executorThread;
                    break;
                }
            }
        }

        if (currentMessageLoop == null) {
            throw new RuntimeException("ImeThread.postTaskAndReply must called on named thread!");
        }

        final IExecutorThread executorThread = getExecutorThreadById(identifier);
        if (executorThread == null) {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("ImeThread.postTaskAndReply must depend on valid ID!");
            } else {
                return null;
            }
        }

        RunnableFuture<T> ftask = new ThrowableFutureReplyTask<>(executorThread, callable, reply, currentMessageLoop, callableName, identifier.getName(), waitTimeout, execTimeout);

        executorThread.execute(ftask);

        return ftask;
    }

    /**
     * Uninitialize the message loop.
     *
     * @throws RuntimeException
     */
    public static void uninitialize() throws RuntimeException {
        if (!smInitialized) {
            return;
        }

        INSTANCE_LOCK.lock();
        try {
            for (int i = 0; i < EXECUTOR_THREADS.length; ++i) {
                final IExecutorThread old = EXECUTOR_THREADS[i];
                EXECUTOR_THREADS[i] = null;
                old.quit();
            }

            smInitialized = false;
        } finally {
            INSTANCE_LOCK.unlock();
        }
    }
}
