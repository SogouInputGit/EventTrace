package com.sohu.inputmethod.foreign.base.language;

/**
 * 用于定义一种语言的输入模式
 * 包含一个int型值：mSubTypeValue，构成如下：
 * 前16位用于标识一种语言，语言id取值范围：0 - 65535
 * 中间8位用于标识该语言的输入模式，比如汉语中的拼音、手写、五笔等，取值范围：0 - 255
 * 后8位用于标识键盘模式，比如9键、26键等，取值范围：0 - 255
 * Language Type | Ime Type | Keyboard Type
 *    16            8             8
 * @author fanfukang on 17-11-9.
 */

public class SubType {
    public static final int INVALID_KEYBOARD_ID = Integer.MIN_VALUE;

    public static final int LANGUAGE_TYPE_THAI = 183;

    public static final int LANGUAGE_TYPE_OFFSET = 16;
    public static final int IME_TYPE_OFFSET = 8;
    public static final int KEYBOARD_TYPE_OFFSET = 0;

    public static final int BIT_16_MASK = 0xFFFF;
    public static final int BIT_8_MASK = 0xFF;

    public static final int LANGUAGE_TYPE_MASK = BIT_16_MASK << LANGUAGE_TYPE_OFFSET;
    public static final int IME_TYPE_MASK = BIT_8_MASK << IME_TYPE_OFFSET;
    public static final int KEYBOARD_TYPE_MASK = BIT_8_MASK << KEYBOARD_TYPE_OFFSET;

    public static final int KEYBORAD_TYPE_DIGIT = 0xFF << KEYBOARD_TYPE_OFFSET;


    public static final int CN_IME_TYPE_DEFAULT = 0;
    public static final int CN_IME_TYPE_BIHUA = 3;
    public static final int CN_IME_TYPE_HANDWRITE = 4;
    public static final int CN_IME_TYPE_HANDWRITE_FULLSCREEN = 5;
    public static final int CN_IME_TYPE_WUBI = 7;

    public static final int CN_KEYBOARD_TYPE_NINE_KEYS = 0;
    public static final int CN_KEYBOARD_TYPE_QWERTY = 1;
    public static final int CN_KEYBOARD_TYPE_BIG_NINE_KEYS = 2;
    public static final int CN_KEYBOARD_TYPE_SP_NINE_KEYS = 3;
    public static final int CN_KEYBOARD_TYPE_SP_QWERTY = 4;

    // 跟ImeInterface的值相同，以后不再用ImeInterface中关于英语的值了
    public static final int EN_LEGACY_KEYBOARD_TYPE_PHONE = 1; // 英文九键
    public static final int EN_LEGACY_KEYBOARD_TYPE_QWERTY = 2; // 英文全键
    public static final int EN_LEGACY_KEYBOARD_TYPE_SYMBOL_26 = 6; //符号键盘
    public static final int EN_LEGACY_KEYBOARD_TYPE_DIGIT_9 = 7;   //数字键盘
    public static final String LANGUAGE_CODE_CANGJIE = "zhHK";

    public static final String LANGUAGE_LAYOUT_CANGJIE = "Cangjie";



    private int mSubTypeValue = 0;

    public SubType(int subTypeValue) {
        mSubTypeValue = subTypeValue;
    }

    public SubType(int languageType, int imeType, int keyboardType) {
        mSubTypeValue= assembleSubTypeValue(languageType, imeType, keyboardType);
    }

    public int getLanguageType() {
        return getLanguageType(mSubTypeValue);
    }

    public int getIMEType() {
        return getImeType(mSubTypeValue);
    }

    public int getKeyboardType() {
        return getKeyboardType(mSubTypeValue);
    }

    public int getSubTypeValue() {
        return mSubTypeValue;
    }

    public void changeKeyboardType(int newKeyboardType) {
        mSubTypeValue = (mSubTypeValue & ~KEYBOARD_TYPE_MASK) | ((newKeyboardType & BIT_8_MASK) << KEYBOARD_TYPE_OFFSET);
    }

    public void changeImeType(int newImeType) {
        mSubTypeValue = (mSubTypeValue & ~IME_TYPE_MASK) | ((newImeType & BIT_8_MASK) << IME_TYPE_OFFSET);
    }

    public static int assembleSubTypeValue(int lanId, int imeType, int keyboardType) {
        int subTypeValue = ((lanId & BIT_16_MASK) << LANGUAGE_TYPE_OFFSET) |
                ((imeType & BIT_8_MASK)  << IME_TYPE_OFFSET) |
                ((keyboardType & BIT_8_MASK)  << KEYBOARD_TYPE_OFFSET);
        return subTypeValue;
    }

    public static int getLanguageType(int subTypeValue) {
        return (subTypeValue & LANGUAGE_TYPE_MASK) >> LANGUAGE_TYPE_OFFSET;
    }

    public static int getImeType(int subTypeValue) {
        return (subTypeValue & IME_TYPE_MASK) >> IME_TYPE_OFFSET;
    }

    public static int getKeyboardType(int subTypeValue) {
        return (subTypeValue & KEYBOARD_TYPE_MASK) >> KEYBOARD_TYPE_OFFSET;
    }

    public void copyValueFrom(SubType srcSubType) {
        if (srcSubType == null) {
            return;
        }
        this.mSubTypeValue = srcSubType.mSubTypeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubType subType = (SubType) o;

        return mSubTypeValue == subType.mSubTypeValue;

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SubType{");
        sb.append("mSubTypeValue=").append(mSubTypeValue);
        sb.append(", languageType=").append(getLanguageType());
        sb.append(", imeType=").append(getIMEType());
        sb.append(", keyboardType=").append(getKeyboardType());
        sb.append('}');
        return sb.toString();
    }
}
