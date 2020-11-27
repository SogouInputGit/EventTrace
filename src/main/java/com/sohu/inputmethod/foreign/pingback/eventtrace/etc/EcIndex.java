package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import com.sogou.event.operation.BaseEventDefine;
import com.sogou.event.operation.OneMapOperation;
import com.sogou.event.operation.TwoMapOperation;
import com.sohu.inputmethod.foreign.pingback.eventtrace.evaluation.IcEvaluationOperation;

/**
 *The record's mapping info for EC.
 *
 * @author lvchong
 * @date 19-12-10
 */
public class EcIndex extends BaseEventDefine {

    public static final String PREFIX = "ec";

    public static final String CLICK_SHIFT_FROM_FIRST_TO_SEC = PREFIX + "1";
    public static final String CLICK_SHIFT_FROM_SEC_TO_FIRST = PREFIX + "2";
    public static final String CLICK_ANYKEY_TIME = PREFIX + "3";
    public static final String SHOW_SYMBO_KEYBOARD_TIME = PREFIX + "4";
    public static final String CLICK_NUMBER_IN_SYMBO_KEYBOARD = PREFIX + "5";
    public static final String CLICK_SYMBO_IN_SYMBO_KEYBOARD = PREFIX + "6";
    public static final String CLICK_BACK_IN_SYMBO_KEYBOARD = PREFIX + "7";
    public static final String LONGPRESS_COMMIT_IN_SYMBO_KEYBOARD = PREFIX + "8";
    public static final String SLIDE_UP_NUM_IN_ALPHA_KEYBOARD = PREFIX + "9";
    public static final String SLIDE_UP_SYMBO_IN_ALPHA_KEYBOARD = PREFIX + "10";
    public static final String SLIDE_UP_ALPHA_IN_ALPHA_KEYBOARD = PREFIX + "11";
    public static final String LONG_PRESS_NUM_IN_ALPHA_KEYBOARD = PREFIX + "12";
    public static final String LONG_PRESS_SYMBO_IN_ALPHA_KEYBOARD = PREFIX + "13";
    public static final String LONG_PRESS_ALPHA_IN_ALPHA_KEYBOARD = PREFIX + "14";

    /**
     * EC15 上屏总字数
     **/
    public static final String EC_COMMIT_CHARQUENCE_LENGTH = PREFIX + "15";
    /**
     * EC16 上屏总次数
     **/
    public static final String EC_COMMIT_TIMES = PREFIX + "16";
    /**
     * EC17 候选首选上屏总次数
     **/
    public static final String EC_COMMIT_FIRST_CANDIDATE_TIMES = PREFIX + "17";
    /**
     * EC18 候选二选上屏总次数
     **/
    public static final String EC_COMMIT_SECOND_CANDIDATE_TIMES = PREFIX + "18";
    /**
     * EC19 候选三选上屏总次数
     **/
    public static final String EC_COMMIT_THIRD_CANDIDATE_TIMES = PREFIX + "19";
    /**
     * EC20 空格上屏候选总次数
     **/
    public static final String EC_COMMIT_FROM_SPACE_TIMES = PREFIX + "20";
    /**
     * EC21 inline状态时点击退格次数
     **/
    public static final String EC_PRESS_BACK_TIMES_INLINE = PREFIX + "21";
    /**
     * EC22 非inline状态时点击退格次数
     **/
    public static final String EC_PRESS_BACK_TIMES_UNINLINE = PREFIX + "22";

    /**
     * EC24 多候选时点击更多按钮次数
     **/
    public static final String EC_MORE_CANDIDATE_MORE_BUTTON_CLICK_TIMES = PREFIX + "24";
    /**
     * EC25 更多候选界面点击候选上屏的次数
     **/
    public static final String EC_MORE_CANDIDATE_WORDS_COMMIT_TIMES = PREFIX + "25";
    /**
     * EC26 更多候选界面点击[返回]按钮的次数
     **/
    public static final String EC_MORE_CANDIDATE_BACK_BUTTON_CLICK_TIMES = PREFIX + "26";
    /**
     * EC27 更多候选界面点击[删除]按钮的次数
     **/
    public static final String EC_MORE_CANDIDATE_BACKSPACE_BUTTON_CLICK_TIMES = PREFIX + "27";
    /**
     * EC28 更多候选界面点击[↑]按钮的次数
     **/
    public static final String EC_MORE_CANDIDATE_CLEAR_INPUT_BUTTON_CLICK_TIMES = PREFIX + "28";
    /**
     * EC29 更多候选界面点击[↓]按钮的次数
     **/
    public static final String EC_MORE_CANDIDATE_ENTER_BUTTON_CLICK_TIMES = PREFIX + "29";
    /**
     * EC30 展示联想
     **/
    public static final String EC_SHOW_ASSOCIATION = PREFIX + "30";
    /**
     * EC31 联想上屏次数
     **/
    public static final String EC_COMMIT_FROM_ASSOCIATION = PREFIX + "31";
    /**
     * EC32 点击关闭按钮关闭联想次数（不区分三候选或多候选）
     **/
    public static final String EC_CLOSE_ASSOCIATION_BUTTON_CLICK_TIMES = PREFIX + "32";
    /**
     * EC33 点击按键关闭联想次数
     **/
    public static final String EC_CLOSE_ASSOCIATION_FROM_OTHERS = PREFIX + "33";
    /**
     * EC34 lock the shift
     */
    public static final String EC_LOCK_SHIFT_TIMES = PREFIX + "34";
    /**
     * EC35 slip clear candidate
     */
    public static final String EC_LEFT_SLIP_CLEAR_CANDIDATES_TIMES = PREFIX + "35";

    /*Ec36 比较特殊，特殊处理,从不同入口进入语言管理页面后，完成多语言启用的漏斗情况*/
    public static final String EC_MULTI_LANGUAGE_SETTING_DLD_AND_SELECT = PREFIX + "36";

    // 联想调用次数
    public static final String EC_ASSOC_CALLED_TIMES = PREFIX + "37";
    /*Ec38 比较特殊，特殊处理,从不同入口进入语言管理页面后，完成多语言启用的漏斗情况*/
    public static final String EC_LANGUAGE_LAYOUT_SET_LOG = PREFIX + "38";
    public static final String EC_CLICK_CHARACTER_NUM_SYMBOL_TIMES = PREFIX + "39";
    public static final String EC_MORE_SYMBOL_CLICK_SYMBOL_COUNT = PREFIX + "40";
    public static final String EC_LANGUAGE_UPDATE = PREFIX + "41";

    public static final String EC_LANGUAGE_SHARE_LANGUAGE_CLICK = PREFIX + "43";
    public static final String EC_LANGUAGE_SHARE_CHANNEL_CLICK = PREFIX + "44";
    public static final String EC_GUID_IN_SYMBOL = PREFIX + "45";
    public static final String EC_CHINESE_KEYBOARD_POPUP = PREFIX + "46";
    public static final String EC_SWITCH_KEYBOARD_ON_SHOW = PREFIX + "47";
    public static final String EC_IC_EVALUATION_PINGBACK = PREFIX + "48";

    @Override
    protected void initDefines() {
        putEventDefine(CLICK_SHIFT_FROM_FIRST_TO_SEC, LanguageEventOperation.class);
        putEventDefine(CLICK_SHIFT_FROM_SEC_TO_FIRST, LanguageEventOperation.class);
        putEventDefine(CLICK_ANYKEY_TIME, LanguageEventOperation.class);
        putEventDefine(SHOW_SYMBO_KEYBOARD_TIME, LanguageEventOperation.class);

        putEventDefine(CLICK_NUMBER_IN_SYMBO_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(CLICK_SYMBO_IN_SYMBO_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(CLICK_BACK_IN_SYMBO_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(LONGPRESS_COMMIT_IN_SYMBO_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(SLIDE_UP_NUM_IN_ALPHA_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(SLIDE_UP_SYMBO_IN_ALPHA_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(SLIDE_UP_ALPHA_IN_ALPHA_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(LONG_PRESS_NUM_IN_ALPHA_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(LONG_PRESS_SYMBO_IN_ALPHA_KEYBOARD, LanguageEventOperation.class);
        putEventDefine(LONG_PRESS_ALPHA_IN_ALPHA_KEYBOARD, LanguageEventOperation.class);

        putEventDefine(EC_COMMIT_CHARQUENCE_LENGTH, LanguageEventOperation.class);
        putEventDefine(EC_COMMIT_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_COMMIT_FIRST_CANDIDATE_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_COMMIT_SECOND_CANDIDATE_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_COMMIT_THIRD_CANDIDATE_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_COMMIT_FROM_SPACE_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_PRESS_BACK_TIMES_INLINE, LanguageEventOperation.class);
        putEventDefine(EC_PRESS_BACK_TIMES_UNINLINE, LanguageEventOperation.class);
        putEventDefine(EC_MORE_CANDIDATE_MORE_BUTTON_CLICK_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_MORE_CANDIDATE_WORDS_COMMIT_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_MORE_CANDIDATE_BACK_BUTTON_CLICK_TIMES, LanguageEventOperation.class);

        putEventDefine(EC_MORE_CANDIDATE_BACKSPACE_BUTTON_CLICK_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_MORE_CANDIDATE_CLEAR_INPUT_BUTTON_CLICK_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_MORE_CANDIDATE_ENTER_BUTTON_CLICK_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_SHOW_ASSOCIATION, LanguageEventOperation.class);
        putEventDefine(EC_COMMIT_FROM_ASSOCIATION, LanguageEventOperation.class);
        putEventDefine(EC_CLOSE_ASSOCIATION_BUTTON_CLICK_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_CLOSE_ASSOCIATION_FROM_OTHERS, LanguageEventOperation.class);
        putEventDefine(EC_LOCK_SHIFT_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_LEFT_SLIP_CLEAR_CANDIDATES_TIMES, LanguageEventOperation.class);

        putEventDefine(EC_MULTI_LANGUAGE_SETTING_DLD_AND_SELECT, LanguageDownAndActiveOperation.class);

        putEventDefine(EC_LANGUAGE_LAYOUT_SET_LOG, LanguageLayoutDetailOperation.class);

        putEventDefine(EC_ASSOC_CALLED_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_CLICK_CHARACTER_NUM_SYMBOL_TIMES, LanguageEventOperation.class);
        putEventDefine(EC_MORE_SYMBOL_CLICK_SYMBOL_COUNT, OneMapOperation.class);

        putEventDefine(EC_LANGUAGE_UPDATE, TwoMapOperation.class);
        putEventDefine(EC_LANGUAGE_SHARE_LANGUAGE_CLICK, TwoMapOperation.class);
        putEventDefine(EC_LANGUAGE_SHARE_CHANNEL_CLICK, TwoMapOperation.class);

        putEventDefine(EC_GUID_IN_SYMBOL, LanguageEventOperation.class);
        putEventDefine(EC_CHINESE_KEYBOARD_POPUP, LanguageEventOperation.class);
        putEventDefine(EC_SWITCH_KEYBOARD_ON_SHOW, LanguageEventOperation.class);
        putEventDefine(EC_IC_EVALUATION_PINGBACK, IcEvaluationOperation.class);
    }
}
