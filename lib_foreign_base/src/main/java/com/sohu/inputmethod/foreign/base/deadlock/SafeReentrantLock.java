package com.sohu.inputmethod.foreign.base.deadlock;


import com.sohu.inputmethod.foreign.base.BuildConfig;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dongjianye on 2020/6/11
 *
 * 在运行的时候会检测潜在的死锁，如果检测到了，直接崩溃
 */
public class SafeReentrantLock extends ReentrantLock {

    // 只能在debug版本才能使用;性能评测的时候自己打log，应该把这个关了
    private static final boolean CHECK_DEAD_LOCK = BuildConfig.DEBUG;

    @Override
    public void lock() {
        super.lock();

        if (CHECK_DEAD_LOCK) {
            try {
                DeadLockChecker.afterLock(this, LockLocationManager.getInstance().getLocationId(new Throwable().getStackTrace()));
            } catch (DeadLockException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public boolean tryLock() {
        final boolean locked = super.tryLock();

        if (CHECK_DEAD_LOCK && locked) {
            try {
                DeadLockChecker.afterLock(this, LockLocationManager.getInstance().getLocationId(new Throwable().getStackTrace()));
            } catch (DeadLockException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return locked;
    }

    @Override
    public void unlock() {
        if (CHECK_DEAD_LOCK) {
            try {
                DeadLockChecker.afterUnlock(this);
            } catch (DeadLockException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        super.unlock();


    }
}