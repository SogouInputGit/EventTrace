package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import com.sogou.event.operation.BaseEventDefine;
import com.sogou.event.operation.IntOperation;
import com.sogou.event.operation.OneMapOperation;

/**
 *The record's mapping info for EB.
 *
 * @author lvchong
 * @date 19-12-10
 */
public class EbIndex extends BaseEventDefine {

    public static final String PREFIX = "eb";

    public static final String CLICK_EARTH_TIMES = PREFIX + "1";
    public static final String LONG_PRESS_EARTH_TIMES = PREFIX + "2";
    public static final String LONGPRESS_EARTH_SELECT_LANGUAGE_TIMES = PREFIX + "3";
    public static final String ADD_LANGUAGE_BY_EARTH_TIMES = PREFIX + "4";
    public static final String OPEN_BOTTOMWORDS_IN_BO_TIMES = PREFIX + "5";
    public static final String CLOSE_BOTTOMWORDS_IN_BO_TIMES = PREFIX + "6";
    public static final String COMMIT_APLHA_WHEN_BOTTONWORD_OPEN_IN_BO_TIMES = PREFIX + "7";

    public static final String clickExpressionTimesInToolbar = PREFIX + "8";
    public static final String baseExpressionCommitCounts = PREFIX + "9";
    public static final String clickSwitchKeyboardTimesInToolbar = PREFIX + "10";
    public static final String totalClickTimesInKeyboardSwitchView = PREFIX + "11";
    public static final String click9PinyinTimesInKeyboardSwitchView = PREFIX + "12";
    public static final String click26PinyinTimesInKeyboardSwitchView = PREFIX + "13";
    public static final String ClickHWTimesInKeyboardSwitchView = PREFIX + "14";
    public static final String ClickAddTimesInKeyboardSwitchView = PREFIX + "15";

    public static final String EB_UpdateForeignLanguageButtonClickCount = PREFIX + "16";
    public static final String EB_UpdateForeignLanguagePopupShowCount = PREFIX + "17";
    public static final String EB_UpdateForeignLanguagePopupUpdateClickCount = PREFIX + "18";

    public static final String multi_language_setting_enter_times = PREFIX + "19";
    public static final String multi_language_setting_enter_source_keyboard_switch = PREFIX + "20";
    public static final String multi_language_setting_enter_source_setting_language_download = PREFIX + "21";
    public static final String multi_language_setting_enter_source_setting_language_layout = PREFIX + "22";

    public static final String EB_language_keyboard_show_time = PREFIX + "23";
    public static final String EB_quick_switch_language_set_time = PREFIX + "24";

    public static final String EB_change_language_way_when_in_quick_switch = PREFIX + "27";
    public static final String EB_change_language_way_when_not_in_quick_switch = PREFIX + "28";
    public static final String EB_change_language_in_keyboard_switch_view = PREFIX + "29";

    public static final String EB_ENGLISH_ENABLE_PREDICTION = PREFIX + "30";
    public static final String EB_ENGLISH_DISABLE_PREDICTION = PREFIX + "31";
    public static final String EB_ENGLISH_CLICK_TIMES_WITH_PREDICTION = PREFIX + "32";
    public static final String EB_ENGLISH_CLICK_TIMES_WITHOUT_PREDICTION = PREFIX + "33";

    public static final String EB_ENGLISH_DIGIT_INPUT_BY_SLIDE = PREFIX + "34";
    public static final String EB_ENGLISH_DIGIT_INPUT_BY_LONG_PRESS = PREFIX + "35";
    public static final String EB_ENGLISH_DIGIT_INPUT_BY_SODUKU = PREFIX + "36";
    public static final String EB_ENGLISH_DIGIT_INPUT_BY_SYMOBLE = PREFIX + "37";
    public static final String EB_ENGLISH_DIGIT_INPUT_BY_NUMBER_LINE = PREFIX + "38";
    public static final String EB_ENGLISH_DIGIT_INPUT_TOTAL_TIMES = PREFIX + "39";

    public static final String EB_ENGLISH_SYMBOL_INPUT_BY_SLIDE = PREFIX + "40";
    public static final String EB_ENGLISH_SYMBOL_INPUT_BY_LONG_PRESS = PREFIX + "41";
    public static final String EB_ENGLISH_SYMBOL_INPUT_BY_SODUKU = PREFIX + "42";
    public static final String EB_ENGLISH_SYMBOL_INPUT_BY_SYMOBLE = PREFIX + "43";
    public static final String EB_ENGLISH_SYMBOL_INPUT_TOTAL_TIMES = PREFIX + "44";
    public static final String EB_ENGLISH_SYMBOL_COMMA_AND_PERIOD = PREFIX + "45";

    public static final String EB_ENGLISH_SHIFT_CLICK = PREFIX + "46";
    public static final String EB_ENGLISH_SHIFT_DOUBLE_CLICK = PREFIX + "47";
    public static final String EB_ENGLISH_INPUT_LETTER_WHEN_SHIFT_LOCKED = PREFIX + "48";
    public static final String EB_ENGLISH_INPUT_LETTER_AFTER_SHIFT_TO_NORMAL_FROM_PRESSED = PREFIX + "49";

    public static final String EB_ENGLISH_APP_SETTING_CLICK_PREDICTION = PREFIX + "50";
    public static final String EB_ENGLISH_APP_SETTING_CLICK_ASSOCIATION = PREFIX + "51";
    public static final String EB_ENGLISH_APP_SETTING_CLICK_AUTO_ADD_SPACE = PREFIX + "52";
    public static final String EB_ENGLISH_APP_SETTING_CLICK_AUTO_SENTENCE_CAPITAL = PREFIX + "53";
    public static final String EB_ENGLISH_APP_SETTING_CLICK_LOCK_CAPITAL = PREFIX + "54";
    public static final String EB_ENGLISH_APP_SETTING_CLICK_NINE_KEYBOARD = PREFIX + "55";

    public static final String EB_ENGLISH_SELECT_CANDIDATE_AFTER_SWIPE = PREFIX + "56";
    public static final String EB_ENGLISH_CLICK_MORE_CANDIDATES = PREFIX + "57";
    public static final String EB_ENGLISH_SELECT_CANDIDATE_ON_MORE_PAGE = PREFIX + "58";

    public static final String EB_ENGLISH_SELECT_CANDIDATE_BY_SPACE = PREFIX + "59";
    public static final String EB_ENGLISH_SELECT_CANDIDATE_BY_CLICK = PREFIX + "60";
    public static final String EB_ENGLISH_SELECT_SHELL_FIRST_CANDIDATE = PREFIX + "61";
    public static final String EB_ENGLISH_SELECT_SHELL_SECOND_CANDIDATE = PREFIX + "62";
    public static final String EB_ENGLISH_SELECT_SHELL_THIRD_CANDIDATE = PREFIX + "63";
    public static final String EB_ENGLISH_SELECT_SHELL_FOURTH_AND_MORE_CANDIDATE = PREFIX + "64";
    public static final String EB_ENGLISH_SELECT_QWERT = PREFIX + "65";
    public static final String EB_ENGLISH_SELECT_PHONE = PREFIX + "66";
    public static final String EB_ENGLISH_CONTINUE_3_TIMES_COMMIT = PREFIX + "67";
    public static final String EB_ENGLISH_CONTINUE_5_TIMES_COMMIT = PREFIX + "68";

    public static final String EB_JAPANESE_SWITCH_CANDIDATE_BY_SPACE = PREFIX + "69";
    public static final String EB_JAPANESE_CONFIRM_INLINE_BY_ENTER = PREFIX + "70";

    public static final String EB_MORE_SYMBOL_CLICK_COUNT = PREFIX + "71";

    public static final String EB_ENGLISH_CLICK_COMMA_COUNT = PREFIX + "72";
    public static final String EB_ENGLISH_CLICK_PERIOD_COUNT = PREFIX + "73";

    public static final String EB_JAPANESE_ENABLE_KBHW = PREFIX + "74";
    public static final String EB_JAPANESE_RECEIVE_CANDIATE_IN_KBHW = PREFIX + "75";
    public static final String EB_JAPANESE_COMMIT_CANDIATE_IN_KBHW = PREFIX + "76";
    public static final String EB_JAPANESE_DELETE_KEY_IN_KBHW = PREFIX + "77";

    public static final String EB_ENGLISH_SYNONYM_COMMITED_WORD_HIT = PREFIX + "78";
    public static final String EB_ENGLISH_SYNONYM_GUIDE_SHOW_TIMES = PREFIX + "79";
    public static final String EB_ENGLISH_SYNONYM_GUIDE_CLICK_CLOSE_BTN_TIMES = PREFIX + "80";
    public static final String EB_ENGLISH_SYNONYM_CANDIDATE_SHOW_TIMES = PREFIX + "81";
    public static final String EB_ENGLISH_SYNONYM_CANDIDATE_CLICK_TIMES = PREFIX + "82";
    public static final String EB_ENGLISH_CLICK_SPACE_WHEN_SYNONYM_CANDIDATE = PREFIX + "83";
    public static final String EB_ENGLISH_SYNONYM_CANDIDATE_COMMIT_TIMES = PREFIX + "84";
    public static final String EB_ENGLISH_SYNONYM_GUIDE_CLICK_CLOSE_OUTSIDE_TIMES = PREFIX + "87";
    public static final String EB_FOREIGN_SHARE_OPEN = PREFIX + "89";
    public static final String EB_FOREIGN_SHARE_CLOSE = PREFIX + "90";
    public static final String EB_FOREIGN_CHINESE_DEFAULT_KEYBOARD_POPUP = PREFIX + "91";

    public static final String EB_ENGLISH_SWITCH_TO_SYMBOL_IN_QWERTY_KEYBOARD = PREFIX + "92";
    public static final String EB_ENGLISH_SWITCH_TO_DIGIT_SUDOKU_IN_QWERTY_KEYBOARD = PREFIX + "93";
    public static final String EB_ENGLISH_INPUT_DIGIT_IN_SYMBOL_FROM_QWERTY_KEYBOARD = PREFIX + "94";
    public static final String EB_ENGLISH_INPUT_DIGIT_IN_DIGIT_SUDOKU_FROM_QWERTY_KEYBOARD = PREFIX + "95";

    public static final String EB_CANTONESE_PINYIN_DISPLAY_SWITCH_CLICKED_COUNT_IN_MORE_CANDS = PREFIX + "96";
    public static final String EB_CANTONESE_PINYIN_DISPLAY_SWITCH_CLICKED_COUNT_IN_SETTINGS = PREFIX + "97";

    /** 在英文全键pro下，按键的次数*/
    public static final String EB_ALL_KEYS_CLICK_IN_ENGLISH_PRO = PREFIX + "98";
    /**在英文全键pro下，首选上屏次数*/
    public static final String EB_COMMIT_HIGHLIGHTED_CANDIDATE_IN_ENGLISH_PRO = PREFIX + "99";
    /** 在英文全键pro下，前三上屏次数*/
    public static final String EB_COMMIT_CANDIDATE_BEFORE_THIRD_IN_ENGLISH_PRO = PREFIX + "100";
    /**在英文全键pro下，上屏总次数*/
    public static final String EB_COMMIT_TIMES_IN_ENGLISH_PRO = PREFIX + "101";
    /**在英文全键pro下，上屏的总字数*/
    public static final String EB_WORD_COMMIT_IN_ENGLISH_PRO = PREFIX + "102";
    /**在英文全键pro下，点击符号!?#键的次数*/
    public static final String EB_CLICK_SYMBOL_KEY_IN_ENGLISH_PRO = PREFIX + "103";
    /**在英文全键pro下，点击数字123键的次数*/
    public static final String EB_CLICK_DIGIT_KEY_IN_ENGLISH_PRO = PREFIX + "104";
    /**在英文全键pro下，首页输入句号的次数*/
    public static final String EB_COMMIT_PERIOD_IN_ENGLISH_PRO = PREFIX + "105";
    /**在英文全键pro下，首页上滑或长按输入逗号的次数*/
    public static final String EB_COMMIT_COMMA_IN_ENGLISH_PRO = PREFIX + "106";


    @Override
    protected void initDefines() {
        putEventDefine(CLICK_EARTH_TIMES, IntOperation.class);
        putEventDefine(LONG_PRESS_EARTH_TIMES, IntOperation.class);
        putEventDefine(LONGPRESS_EARTH_SELECT_LANGUAGE_TIMES, IntOperation.class);
        putEventDefine(ADD_LANGUAGE_BY_EARTH_TIMES, IntOperation.class);
        putEventDefine(OPEN_BOTTOMWORDS_IN_BO_TIMES, IntOperation.class);
        putEventDefine(CLOSE_BOTTOMWORDS_IN_BO_TIMES, IntOperation.class);
        putEventDefine(COMMIT_APLHA_WHEN_BOTTONWORD_OPEN_IN_BO_TIMES, IntOperation.class);
        putEventDefine(clickExpressionTimesInToolbar, IntOperation.class);
        putEventDefine(baseExpressionCommitCounts, IntOperation.class);
        putEventDefine(clickSwitchKeyboardTimesInToolbar, IntOperation.class);
        putEventDefine(totalClickTimesInKeyboardSwitchView, IntOperation.class);
        putEventDefine(click9PinyinTimesInKeyboardSwitchView, IntOperation.class);
        putEventDefine(click26PinyinTimesInKeyboardSwitchView, IntOperation.class);
        putEventDefine(ClickHWTimesInKeyboardSwitchView, IntOperation.class);
        putEventDefine(ClickAddTimesInKeyboardSwitchView, IntOperation.class);

        putEventDefine(EB_UpdateForeignLanguageButtonClickCount, OneMapOperation.class);
        putEventDefine(EB_UpdateForeignLanguagePopupShowCount, OneMapOperation.class);
        putEventDefine(EB_UpdateForeignLanguagePopupUpdateClickCount, OneMapOperation.class);
        
        putEventDefine(multi_language_setting_enter_times, IntOperation.class);
        putEventDefine(multi_language_setting_enter_source_keyboard_switch, IntOperation.class);
        putEventDefine(multi_language_setting_enter_source_setting_language_download, IntOperation.class);
        putEventDefine(multi_language_setting_enter_source_setting_language_layout, IntOperation.class);
        
        putEventDefine(EB_language_keyboard_show_time, OneMapOperation.class);
        putEventDefine(EB_quick_switch_language_set_time, OneMapOperation.class);

        putEventDefine(EB_change_language_way_when_in_quick_switch, OneMapOperation.class);
        putEventDefine(EB_change_language_way_when_not_in_quick_switch, OneMapOperation.class);
        putEventDefine(EB_change_language_in_keyboard_switch_view, OneMapOperation.class);
        
        putEventDefine(EB_ENGLISH_ENABLE_PREDICTION, IntOperation.class);
        putEventDefine(EB_ENGLISH_DISABLE_PREDICTION, IntOperation.class);
        putEventDefine(EB_ENGLISH_CLICK_TIMES_WITH_PREDICTION, IntOperation.class);
        putEventDefine(EB_ENGLISH_CLICK_TIMES_WITHOUT_PREDICTION, IntOperation.class);
        putEventDefine(EB_ENGLISH_DIGIT_INPUT_BY_SLIDE, IntOperation.class);
        putEventDefine(EB_ENGLISH_DIGIT_INPUT_BY_LONG_PRESS, IntOperation.class);
        putEventDefine(EB_ENGLISH_DIGIT_INPUT_BY_SODUKU, IntOperation.class);
        putEventDefine(EB_ENGLISH_DIGIT_INPUT_BY_SYMOBLE, IntOperation.class);
        putEventDefine(EB_ENGLISH_DIGIT_INPUT_BY_NUMBER_LINE, IntOperation.class);
        putEventDefine(EB_ENGLISH_DIGIT_INPUT_TOTAL_TIMES, IntOperation.class);
        
        putEventDefine(EB_ENGLISH_SYMBOL_INPUT_BY_SLIDE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYMBOL_INPUT_BY_LONG_PRESS, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYMBOL_INPUT_BY_SODUKU, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYMBOL_INPUT_BY_SYMOBLE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYMBOL_INPUT_TOTAL_TIMES, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYMBOL_COMMA_AND_PERIOD, IntOperation.class);
        putEventDefine(EB_ENGLISH_SHIFT_CLICK, IntOperation.class);
        putEventDefine(EB_ENGLISH_SHIFT_DOUBLE_CLICK, IntOperation.class);
        putEventDefine(EB_ENGLISH_INPUT_LETTER_WHEN_SHIFT_LOCKED, IntOperation.class);
        putEventDefine(EB_ENGLISH_INPUT_LETTER_AFTER_SHIFT_TO_NORMAL_FROM_PRESSED, IntOperation.class);
        
        putEventDefine(EB_ENGLISH_APP_SETTING_CLICK_PREDICTION, IntOperation.class);
        putEventDefine(EB_ENGLISH_APP_SETTING_CLICK_ASSOCIATION, IntOperation.class);
        putEventDefine(EB_ENGLISH_APP_SETTING_CLICK_AUTO_ADD_SPACE, IntOperation.class);
        putEventDefine(EB_ENGLISH_APP_SETTING_CLICK_AUTO_SENTENCE_CAPITAL, IntOperation.class);
        putEventDefine(EB_ENGLISH_APP_SETTING_CLICK_LOCK_CAPITAL, IntOperation.class);
        putEventDefine(EB_ENGLISH_APP_SETTING_CLICK_NINE_KEYBOARD, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_CANDIDATE_AFTER_SWIPE, IntOperation.class);
        putEventDefine(EB_ENGLISH_CLICK_MORE_CANDIDATES, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_CANDIDATE_ON_MORE_PAGE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_CANDIDATE_BY_SPACE, IntOperation.class);

        putEventDefine(EB_ENGLISH_SELECT_CANDIDATE_BY_CLICK, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_SHELL_FIRST_CANDIDATE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_SHELL_SECOND_CANDIDATE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_SHELL_THIRD_CANDIDATE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_SHELL_FOURTH_AND_MORE_CANDIDATE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_QWERT, IntOperation.class);
        putEventDefine(EB_ENGLISH_SELECT_PHONE, IntOperation.class);
        putEventDefine(EB_ENGLISH_CONTINUE_3_TIMES_COMMIT, IntOperation.class);
        putEventDefine(EB_ENGLISH_CONTINUE_5_TIMES_COMMIT, IntOperation.class);
        putEventDefine(EB_JAPANESE_SWITCH_CANDIDATE_BY_SPACE, IntOperation.class);
        putEventDefine(EB_JAPANESE_CONFIRM_INLINE_BY_ENTER, IntOperation.class);
        putEventDefine(EB_MORE_SYMBOL_CLICK_COUNT, IntOperation.class);
        putEventDefine(EB_ENGLISH_CLICK_COMMA_COUNT, IntOperation.class);
        putEventDefine(EB_ENGLISH_CLICK_PERIOD_COUNT, IntOperation.class);
        putEventDefine(EB_JAPANESE_ENABLE_KBHW, IntOperation.class);
        putEventDefine(EB_JAPANESE_RECEIVE_CANDIATE_IN_KBHW, IntOperation.class);
        putEventDefine(EB_JAPANESE_COMMIT_CANDIATE_IN_KBHW, IntOperation.class);
        putEventDefine(EB_JAPANESE_DELETE_KEY_IN_KBHW, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYNONYM_COMMITED_WORD_HIT, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYNONYM_GUIDE_SHOW_TIMES, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYNONYM_GUIDE_CLICK_CLOSE_BTN_TIMES, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYNONYM_CANDIDATE_SHOW_TIMES, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYNONYM_CANDIDATE_CLICK_TIMES, IntOperation.class);
        putEventDefine(EB_ENGLISH_CLICK_SPACE_WHEN_SYNONYM_CANDIDATE, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYNONYM_CANDIDATE_COMMIT_TIMES, IntOperation.class);
        putEventDefine(EB_ENGLISH_SYNONYM_GUIDE_CLICK_CLOSE_OUTSIDE_TIMES, IntOperation.class);
        putEventDefine(EB_FOREIGN_SHARE_OPEN, IntOperation.class);
        putEventDefine(EB_FOREIGN_SHARE_CLOSE, IntOperation.class);
        putEventDefine(EB_FOREIGN_CHINESE_DEFAULT_KEYBOARD_POPUP, IntOperation.class);
        putEventDefine(EB_ENGLISH_SWITCH_TO_SYMBOL_IN_QWERTY_KEYBOARD, IntOperation.class);
        putEventDefine(EB_ENGLISH_SWITCH_TO_DIGIT_SUDOKU_IN_QWERTY_KEYBOARD, IntOperation.class);
        putEventDefine(EB_ENGLISH_INPUT_DIGIT_IN_SYMBOL_FROM_QWERTY_KEYBOARD, IntOperation.class);
        putEventDefine(EB_ENGLISH_INPUT_DIGIT_IN_DIGIT_SUDOKU_FROM_QWERTY_KEYBOARD, IntOperation.class);
        putEventDefine(EB_CANTONESE_PINYIN_DISPLAY_SWITCH_CLICKED_COUNT_IN_MORE_CANDS, IntOperation.class);
        putEventDefine(EB_CANTONESE_PINYIN_DISPLAY_SWITCH_CLICKED_COUNT_IN_SETTINGS, IntOperation.class);

        putEventDefine(EB_ALL_KEYS_CLICK_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_COMMIT_HIGHLIGHTED_CANDIDATE_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_COMMIT_CANDIDATE_BEFORE_THIRD_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_COMMIT_TIMES_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_WORD_COMMIT_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_CLICK_SYMBOL_KEY_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_CLICK_DIGIT_KEY_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_COMMIT_PERIOD_IN_ENGLISH_PRO, IntOperation.class);
        putEventDefine(EB_COMMIT_COMMA_IN_ENGLISH_PRO, IntOperation.class);

    }
}
