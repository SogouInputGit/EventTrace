package com.sohu.inputmethod.foreign.base.util;

import android.os.SystemClock;

/**
 * @author dongjianye on 2020/7/21
 */
public class TimeUtils {

    public static int getElapsedTime(long oldTime) {
        return (int) ((SystemClock.uptimeMillis() - oldTime));
    }


}