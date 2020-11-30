package com.sogou.event.internal;

import com.sogou.event.BuildConfig;

/**
 * @author dongjianye on 2020/6/5
 */
public class Preconditions {

    public static final String TAG = "lib_event_trace";

    public static void throwCondition(String condition) {
        if (BuildConfig.DEBUG) {
            throw new RuntimeException(condition);
        }
    }
}
