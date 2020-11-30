package com.sohu.inputmethod.foreign.base.language;

import static com.sohu.inputmethod.foreign.base.language.LanId.LANGUAGE_TYPE_CN;
import static com.sohu.inputmethod.foreign.base.language.LanId.LANGUAGE_TYPE_EN;
import static com.sohu.inputmethod.foreign.base.language.LanId.LANGUAGE_TYPE_KOREAN;

/**
 * @author dongjianye on 2020-03-05
 */
public class LanUtil {

    public static boolean isEnglish(@LanId int lanId) {
        return lanId == LANGUAGE_TYPE_EN;
    }

    public static boolean isChinese(@LanId int lanId) {
        return lanId == LANGUAGE_TYPE_CN;
    }

    public static boolean isKorean(@LanId int lanId) {
        return lanId == LANGUAGE_TYPE_KOREAN;
    }

    public static boolean isForeign(@LanId int lanId) {
        return isValidLanguageId(lanId) && lanId != LANGUAGE_TYPE_CN;
    }

    public static boolean isJapanese(@LanId int lanId) {
        return lanId == LanId.LANGUAGE_TYPE_JAPANESE;
    }

    public static boolean isCangjie(@LanId int lanId) {
        return lanId == LanId.LANGUAGE_TYPE_CANGJIE;
    }

    public static boolean isZhuyin(@LanId int lanId) {
        return lanId == LanId.LANGUAGE_TYPE_ZHUYIN;
    }

    public static boolean isValidLanguageId(@LanId int lanId) {
        return lanId != LanId.INVALID_LANGUAGE_ID;
    }

    public static boolean isValidImeType(@LanImeType int lanImeType) {
        return lanImeType != LanImeType.INVALID_IME_TYPE;
    }

    public static boolean isEnglishImeType(@LanImeType int imeType) {
        return imeType == LanImeType.ENGLISH_IME_TYPE_ENGLISH ||
                imeType == LanImeType.ENGLISH_IME_TYPE_RAW;
    }

    public static int getEnglishImeType(boolean prediction) {
        return prediction ? LanImeType.ENGLISH_IME_TYPE_ENGLISH :
                LanImeType.ENGLISH_IME_TYPE_RAW;
    }

    public static boolean isForeignImeType(@LanImeType int imeType) {
        return imeType >= LanImeType.FOREIGN_IME_TYPE_START;
    }

    public static boolean isValidKeyboardType(int keyboardType) {
        return keyboardType >= 0 && keyboardType < KeyboardType.SWITCH_STATE_COUNT;
    }

    public static boolean isChineseDefaultLanguage(int lanId){
        return lanId == LanId.LANGUAGE_TYPE_ZHUYIN || lanId == LanId.LANGUAGE_TYPE_CANGJIE
                || lanId == LanId.LANGUAGE_TYPE_CANTON;
    }

    // TODO 目前只有中英文可以使用第三方字体，暂时这么写死，等以后做成在键盘内部可配置，现在的KeyPool那个代码还是太乱
    public static boolean supportCustomFont(int lanId) {
        return lanId == LANGUAGE_TYPE_CN || lanId == LANGUAGE_TYPE_EN;
    }

    public static int assembleFlxInputType(int lanId, int keyboardId) {
        return (0x80000000 | (lanId << 15) | keyboardId);
    }
}
