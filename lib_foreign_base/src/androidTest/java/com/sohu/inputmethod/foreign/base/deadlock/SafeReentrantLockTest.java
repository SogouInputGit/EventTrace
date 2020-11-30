package com.sohu.inputmethod.foreign.base.deadlock;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dongjianye on 2020/6/12
 */
@RunWith(AndroidJUnit4.class)
public class SafeReentrantLockTest {

    final SafeReentrantLock lock1 = new SafeReentrantLock();
    final SafeReentrantLock lock2 = new SafeReentrantLock();
    final SafeReentrantLock lock3 = new SafeReentrantLock();
    final SafeReentrantLock lock4 = new SafeReentrantLock();
    final SafeReentrantLock lock5 = new SafeReentrantLock();
    final SafeReentrantLock lock6 = new SafeReentrantLock();

    @Test
    public void testOne() {
        lock1.lock();
        lock2.lock();
        lock3.lock();
        lock4.lock();

        Thread another = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1 * 1000);
                } catch (Throwable ignored) {};

                lock4.lock();
                lock5.lock();
                lock6.lock();
                lock1.lock();

            }
        });
        another.start();
        lock4.unlock();
        lock3.unlock();
        lock2.unlock();
        lock1.unlock();

        try {
            another.join();
        } catch (Throwable ignored) {}

    }

    @Test
    public void testOneThread() {
        lock1.lock();
        lock2.lock();
        lock1.lock();
    }
}