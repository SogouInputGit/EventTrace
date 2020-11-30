package com.sohu.inputmethod.foreign.base.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;

/**
 * @author jiany on 2020/4/10
 */
@RunWith(AndroidJUnit4.class)
public class ImeThreadTest {

    Handler mHandler = new Handler(Looper.getMainLooper());
    @Test
    public void useAppContext() {
        ImeThread.initialize();

        ImeThread.postTask(ImeThread.ID.FILE, new Runnable() {
            @Override
            public void run() {

                int a = 2;
//                Log.d("IMEThread", String.valueOf(1 / (a - 2)));
                Log.d("IMEThread", a + "");
                ImeThread.postTaskAndReply(ImeThread.ID.UI, new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        Log.d("IMEThread", "run on file 1");
                        return true;
                    }
                }, "testOnFile", new Callback<Boolean>() {
                    @Override
                    public void callback(Boolean result) throws Exception {
                        Log.d("IMEThread", "run on UI 1");
                    }
                });

                Log.d("IMEThread", "run on file end");

            }
        }, "test");

        try {
            Log.d("IMEThread", "begin run");
            Thread.sleep(50000 * 1000);
            Log.d("IMEThread", "end run");
        } catch (Throwable ignored) {

        }
    }

    @Test
    public void test() {
        ImeThread.initialize();

        for (int i =0; i < 100; ++i) {
            final int a = i;
            ImeThread.postTask(ImeThread.ID.FILE, new Runnable() {
                @Override
                public void run() {

                    Log.d("IMEThread", "" + a);

                }
            }, "test");
        }

        try {
            Log.d("IMEThread", "begin run");
            Thread.sleep(50000 * 1000);
            Log.d("IMEThread", "end run");
        } catch (Throwable ignored) {

        }
    }

    @Test
    public void testNanoTimeAndUpTime() {
        for (int i = 0; i < 100; i++) {
            long curTime = System.nanoTime();

            for (int j = 0; j < 100; ++j) {
                SystemClock.uptimeMillis();
            }

            Log.e("NanoTime uptimeMillis", "" + (System.nanoTime() - curTime) / 100);

            curTime = System.nanoTime();

            for (int j = 0; j < 100; ++j) {
                System.nanoTime();
            }

            Log.e("NanoTime nanoTime", "" + (System.nanoTime() - curTime) / 100);

            curTime = System.nanoTime();

            for (int j = 0; j < 100; ++j) {
                System.currentTimeMillis();
            }

            Log.e("NanoTime currentTimeMillis", "" + (System.nanoTime() - curTime) / 100);
        }
    }

    @Test
    public void testHandlerRemoveMessageTime() {
        boolean hasMessage = false;
        for (int i = 0; i < 100; i++) {
            long curTime = System.nanoTime();

            for (int j = 0; j < 100; ++j) {
                mHandler.removeMessages(1);
            }

            Log.e("direct removemessage", "" + (System.nanoTime() - curTime) / 100);

            curTime = System.nanoTime();

            for (int j = 0; j < 100; ++j) {
                if (hasMessage) {
                    mHandler.removeMessages(1);
                    hasMessage = false;
                }
            }

            Log.e("cache removeMessage", "" + (System.nanoTime() - curTime) / 100);

            curTime = System.nanoTime();

            for (int j = 0; j < 100; ++j) {

            }

            Log.e("empty removeMessage", "" + (System.nanoTime() - curTime) / 100);
        }
    }
}
