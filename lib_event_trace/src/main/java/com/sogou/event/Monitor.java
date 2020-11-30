package com.sogou.event;

import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import androidx.annotation.VisibleForTesting;

/**
 * @author dongjianye on 2020/6/5
 *
 */
public class Monitor {
    static final String TAG = "EventRecord";

    final ReentrantLock mStorageLock = new ReentrantLock();

    volatile boolean mIsSending = false;

    final static int ERROR_NONE_USE_MEMORY_CACHE_ERROR = 0;
    final static int ERROR_DESERIALIZE_FROM_STORAGE_ERROR = 1;
    final static int ERROR_NEW_CLAZZ_INSTANCE = 2;
    final static int ERROR_FINAL_VALUE_NULL = 3;
    final static int ERROR_OPERATE = 4;
    final static int ERROR_SAVE_TO_STORAGE_1 = 5;
    final static int ERROR_SAVE_TO_STORAGE_2 = 6;
    final static int ERROR_CREATE_BACKUP_DIR = 7;
    final static int ERROR_CREATE_BACKUP_FILE = 8;
    final static int ERROR_WRITE_BACKUP_FILE = 9;
    final static int ERROR_SERIALIZE_TO_BACKUP = 10;
    final static int ERROR_SEND_TIMEOUT = 11;
    private final static int ERROR_COUNT = 12;

    // 主要是记录一些错误，不重要，不需要加锁
    private final int[] mErrorRecord = new int[ERROR_COUNT];

    private final String mId;

    Monitor(String id) {
        mId = id;
        if (BuildConfig.DEBUG) {
            initSuspendingLock();
        }
    }

    public String getId() {
        return mId;
    }

    void recordError(int index) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Error " + index);
        }
        if (index >= 0 && index < ERROR_COUNT) {
            mErrorRecord[index]++;
        }
    }

    String getError() {
        StringBuilder sb = new StringBuilder();
        for (int err : mErrorRecord) {
            sb.append(err).append('|');
        }
        return sb.toString();
    }

    void clearError() {
        Arrays.fill(mErrorRecord, 0);
    }

    // 用于测试，记录多少个正在处理，外面等待这个处理完毕
    private int mSuspendingCount = 0;
    private volatile ReentrantLock mSuspendingLock;
    private Condition mDone;
    private boolean mSuspendingInited;
    // 用于测试，记录多少个正在处理，外面等待这个处理完毕

    void initSuspendingLock() {
        if (!mSuspendingInited) {
            mSuspendingLock = new ReentrantLock();
            mDone = mSuspendingLock.newCondition();
            mSuspendingInited = true;
        }
    }

    void addSuspending() {
        if (BuildConfig.DEBUG && mSuspendingLock != null) {
            mSuspendingLock.lock();
            try {
                mSuspendingCount++;
                Log.d("EventRecord", "add suspending :" + mSuspendingCount);
            } finally {
                mSuspendingLock.unlock();
            }
        }
    }

    void removeSuspending() {
        if (BuildConfig.DEBUG && mSuspendingLock != null) {
            mSuspendingLock.lock();

            try {
                mSuspendingCount--;
                Log.d(TAG, "remove suspending :" + mSuspendingCount);
                if (mSuspendingCount == 0) {
                    mDone.signalAll();
                }
            } finally {
                mSuspendingLock.unlock();
            }
        }
    }

    // 只给自测代码使用，其他代码不能使用，也没必要用
    @VisibleForTesting
    public void waitDone() {
        if (BuildConfig.DEBUG && mSuspendingLock != null) {
            initSuspendingLock();
            mSuspendingLock.lock();

            try {
                while(mSuspendingCount != 0) {
                    mDone.await();
                }
                Log.d(TAG, "all suspending Done");
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            } finally {
                mSuspendingLock.unlock();
            }
        }
    }
}
