package com.sogou.event;

import android.support.v4.util.ArraySet;

import com.sogou.event.internal.Preconditions;

/**
 * @author dongjianye on 2020/7/7
 */
public class IdManager {

    private final static ArraySet<String> sIds = new ArraySet<>();

    private IdManager() {

    }

    public static void registerId(String id) {
        if (BuildConfig.DEBUG) {
            synchronized (sIds) {
                if (!sIds.add(id)) {
                    Preconditions.throwCondition("别的模块已经注册过了");
                }
            }
        }
    }

    public static void checkId(String id) {
        if (BuildConfig.DEBUG) {
            synchronized (sIds) {
                if (!sIds.contains(id)) {
                    Preconditions.throwCondition("没有注册");
                }
            }
        }
    }
}