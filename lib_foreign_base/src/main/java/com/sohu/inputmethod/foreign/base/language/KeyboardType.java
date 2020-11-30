package com.sohu.inputmethod.foreign.base.language;

/**
 * @author dongjianye on 2020-01-16
 * <p>
 * 键盘类型：字母，符号，数字
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({KeyboardType.KEYBOARD_TYPE_ALPHABET,
        KeyboardType.KEYBOARD_TYPE_SYMBOLS,
        KeyboardType.KEYBOARD_TYPE_DIGIT})
@Retention(RetentionPolicy.SOURCE)
public @interface KeyboardType {
    public final static int INVALID_KEYBOARD_TYPE = -1;

    public final static int KEYBOARD_TYPE_ALPHABET = 0;
    public final static int KEYBOARD_TYPE_SYMBOLS = 1;
    public final static int KEYBOARD_TYPE_DIGIT = 2;

    public final static int SWITCH_STATE_COUNT = 3;
}