package com.sohu.inputmethod.foreign.base.language;


import androidx.annotation.IntDef;

/**
 * @author dongjianye on 2020-03-05
 * 外文对LanImeType其实用处不大
 */
@IntDef({
        LanImeType.INVALID_IME_TYPE,
        LanImeType.FOREIGN_IME_TYPE_DIGIT,
        LanImeType.FOREIGN_IME_TYPE_EDIT,
        LanImeType.FOREIGN_IME_TYPE_ALPHABET,
        LanImeType.ENGLISH_IME_TYPE_RAW,
        LanImeType.ENGLISH_IME_TYPE_ENGLISH})
public @interface LanImeType {
    int INVALID_IME_TYPE = Integer.MIN_VALUE;

    int FOREIGN_IME_TYPE_START = 512; // 为了跟中文做区别
    int FOREIGN_IME_TYPE_DIGIT = FOREIGN_IME_TYPE_START;    // 数字
    int FOREIGN_IME_TYPE_EDIT = FOREIGN_IME_TYPE_START + 1; // 编辑面板
    int FOREIGN_IME_TYPE_ALPHABET = FOREIGN_IME_TYPE_START + 2; // 字母键盘
    int FOREIGN_IME_TYPE_SYMBOL = FOREIGN_IME_TYPE_START + 3; // 符号键盘

    int ENGLISH_IME_TYPE_RAW = FOREIGN_IME_TYPE_START + 4;  // 英语：没有预测
    int ENGLISH_IME_TYPE_ENGLISH = FOREIGN_IME_TYPE_START + 5; // 英语：有预测

}
