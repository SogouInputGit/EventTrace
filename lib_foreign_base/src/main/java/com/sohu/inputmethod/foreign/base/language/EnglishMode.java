package com.sohu.inputmethod.foreign.base.language;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 英文键盘Mode
 * @author lvchong
 */
@IntDef({
        EnglishMode.None, EnglishMode.Qwerty, EnglishMode.Nine, EnglishMode.Pro
})
@Retention(RetentionPolicy.SOURCE)
public @interface EnglishMode {
    int None = -1;
    /**英语全键*/
    int Qwerty = 0;
    /*英语九键*/
    int Nine = 1;
    /*英语全键Pro*/
    int Pro = 3;
}