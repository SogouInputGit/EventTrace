package com.sohu.inputmethod.foreign.base.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author dongjianye on 2020-01-15
 */
public abstract class AbstractSafeHandler<T> extends Handler {
    private final WeakReference<T> mOwner;

    public AbstractSafeHandler(T owner) {
        super(Looper.getMainLooper());
        mOwner = new WeakReference<>(owner);
    }

    @Override
    public final void handleMessage(Message msg) {
        T owner = mOwner.get();
        if (owner != null) {
            doHandleMessage(owner, msg);
        }
    }

    public abstract void doHandleMessage(T owner, Message msg);
}