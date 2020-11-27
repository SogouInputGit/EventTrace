package com.sohu.inputmethod.foreign.pingback;

import com.sogou.event.EventRecord;
import com.sogou.event.internal.EventParamProto;
import com.sogou.lib.common.utils.ContextHolder;
import com.sohu.inputmethod.foreign.base.language.EnglishMode;
import com.sohu.inputmethod.foreign.base.language.LanId;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.EbIndex;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.EcIndex;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.LanguageDownAndActiveOperation;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.LanguageEventOperation;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.LanguageLayoutDetailOperation;

import java.util.List;

/**
 * @author dongjianye
 * pingback模块不能引用业务层的代码
 */

public class MultiLanguageStaticsHelper {

    public static final int WAY_OF_CHANGE_LANGUAGE_KEYBOARD_SWITCH = 1;
    public static final int WAY_OF_CHANGE_LANGUAGE_LONG_PRESS_EARTH_CLICK = 2;
    public static final int WAY_OF_CHANGE_LANGUAGE_LONG_PRESS_EARTH_MOVE = 3;
    public static final int WAY_OF_CHANGE_LANGUAGE_PRESS_EARTH = 4;

    public static void ec38Increase(int lanId) {
        EventParamProto eventParam = EventParamProto.obtain();
        eventParam.setArgInt0(lanId);
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_LANGUAGE_LAYOUT_SET_LOG,
                LanguageLayoutDetailOperation.ACTION_TYPE_INCREASE, eventParam);

    }

    public static void ec38Put(int lanId, int from, int to) {
        EventParamProto eventParam = EventParamProto.obtain();
        eventParam.setArgInt0(lanId);
        eventParam.setArgInt1(from);
        eventParam.setArgInt2(to);

        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_LANGUAGE_LAYOUT_SET_LOG,
                LanguageLayoutDetailOperation.ACTION_TYPE_PUT, eventParam);
    }

    public static void ec36Add(int source, List<Integer> lans) {
        if (lans != null && lans.size() > 0) {
            Integer[] lanArray = new Integer[lans.size()];
            lanArray = lans.toArray(lanArray);

            EventParamProto paramProto = EventParamProto.obtain();
            paramProto.setArgString0(String.valueOf(source).intern());
            paramProto.setArgIntArray(lanArray);

            EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_MULTI_LANGUAGE_SETTING_DLD_AND_SELECT,
                    LanguageDownAndActiveOperation.ACTION_ADD_ITEM, paramProto);
        }
    }

    public static void increaseEbOneMap(final String key, int value) {
        // 这个value也是有限的字符串常量
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseOneMap(key, String.valueOf(value).intern());
    }

    public static void increaseEbOneMap(final String key, String value) {
        // 这个value也是有限的字符串常量
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseOneMap(key, value);
    }

    public static void increaseEbNumber(final String key) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(key);
    }

    public static void increaseEbNumberStep(final String key,final int step) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(key, step);
    }

    public static void increaseEcByItem(final String key, final String value) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseOneMap(key, value);
    }

    public static void increaseEc(final String key, final int lanId, final int alphabetId) {
        increaseEc(key, lanId, alphabetId, 1);
    }

    public static void increaseEc(final String key, final int lanId, final int alphabetId, int value) {
        // 语言id和alphabet只有有限的几个，因此直接放到常量池里面
        EventParamProto paramProto = EventParamProto.obtain();
        paramProto.setArgInt0(value);
        paramProto.setArgInt1(lanId);
        paramProto.setArgInt2(alphabetId);
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(key, LanguageEventOperation.ACTION_INCREASE, paramProto);
    }

    public static void increaseEcTwoMap(final String key, final int first, final int second, final int step) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseMultiMap(key, new String[]{String.valueOf(first).intern(), String.valueOf(second).intern()}, step);
    }

    public static void increaseEcTwoMap(final String key, final int first, final String second, final int step) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseMultiMap(key, new String[]{String.valueOf(first).intern(), second}, step);
    }

    private static void onCommitTextInternal(int lanId, int alphabetId, int length, boolean commitTimes, boolean commitLength) {
        if (commitTimes) {
            MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_COMMIT_TIMES, lanId, alphabetId);
        }
        if (commitLength) {
            MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_COMMIT_CHARQUENCE_LENGTH, lanId, alphabetId, length);
        }
    }

    public static void onCommitTextByShell(int lanId, int alphabetId, int keyboardMode, int length) {
        if (length <= 0) {
            return;
        }
        if (lanId == LanId.LANGUAGE_TYPE_EN && keyboardMode == EnglishMode.Pro){
            MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_COMMIT_TIMES_IN_ENGLISH_PRO);
            MultiLanguageStaticsHelper.increaseEbNumberStep(EbIndex.EB_WORD_COMMIT_IN_ENGLISH_PRO, length);
        }

        onCommitTextInternal(lanId, alphabetId, length, true, true);
    }

    private static void onCommitCandidateInternal(int lanId, int alphabetId, int index) {
        switch (index){
            case 0:
                MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_COMMIT_FIRST_CANDIDATE_TIMES, lanId, alphabetId);
                break;
            case 1:
                MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_COMMIT_SECOND_CANDIDATE_TIMES, lanId, alphabetId);
                break;
            case 2:
                MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_COMMIT_THIRD_CANDIDATE_TIMES, lanId, alphabetId);
                break;
            default:
                break;
        }
    }

    public static void onCommitCandidateByShell(int lanId, int alphabetId, int index) {
        onCommitCandidateInternal(lanId, alphabetId, index);
    }

    private static void onCommitBySpaceInternal(int lanId, int alphabetId) {
        MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_COMMIT_FROM_SPACE_TIMES, lanId, alphabetId);
    }

    public static void onCommitBySpaceByShell(int lanId, int alphabetId) {
        onCommitBySpaceInternal(lanId, alphabetId);
    }

    public static void countForeignShiftOpenPingback() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.OPEN_BOTTOMWORDS_IN_BO_TIMES);
    }

    public static void countForeignShiftClosePingback() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.CLOSE_BOTTOMWORDS_IN_BO_TIMES);
    }

    public static void onAssociationCalled(int lanId, int alphabetId) {
        MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_ASSOC_CALLED_TIMES, lanId, alphabetId);
    }

    public static void onAssociationShow(int lanId, int alphabetId) {
        MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_SHOW_ASSOCIATION, lanId, alphabetId);
    }

    public static void onPressAnyKey(int lanId, int alphabetId, int keyboardMode) {
        if (lanId == LanId.LANGUAGE_TYPE_EN && keyboardMode == EnglishMode.Pro){
            MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ALL_KEYS_CLICK_IN_ENGLISH_PRO);
        }
        MultiLanguageStaticsHelper.increaseEc(EcIndex.CLICK_ANYKEY_TIME, lanId, alphabetId);
    }

    public static void onClickAnyCharacter(int lanId, int alphabetId) {
        MultiLanguageStaticsHelper.increaseEc(EcIndex.EC_CLICK_CHARACTER_NUM_SYMBOL_TIMES, lanId, alphabetId);
    }

    public static void ec48Increase(int actionType) {
        EventParamProto eventParam = EventParamProto.obtain();
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_IC_EVALUATION_PINGBACK,
                actionType, eventParam);
    }

    public static void ec48DiffInfo(int actionType, CharSequence expected, CharSequence actual, String cursor, String pkgName) {
        EventParamProto eventParam = EventParamProto.obtain();
        eventParam.setArgString0(expected == null ? "" : expected.toString());
        eventParam.setArgString1(actual == null ? "" : actual.toString());
        eventParam.setArgString2(cursor);
        eventParam.setArgString3(pkgName);
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_IC_EVALUATION_PINGBACK,
                actionType, eventParam);
    }
}
