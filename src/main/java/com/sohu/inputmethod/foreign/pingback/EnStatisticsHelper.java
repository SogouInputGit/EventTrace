package com.sohu.inputmethod.foreign.pingback;

import com.sogou.event.EventRecord;
import com.sogou.event.internal.EventParamProto;
import com.sogou.lib.common.utils.ContextHolder;
import com.sohu.inputmethod.foreign.base.language.EnglishMode;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.EaIndex;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.EbIndex;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.SwitchArrayOperation;

/**
 * @author dongjianye
 */
public class EnStatisticsHelper {

    private static void addEnglishSwitch(String key, boolean enable) {
        EventParamProto paramProto = EventParamProto.obtain();
        paramProto.setArgInt0(enable ? 1 : 0);
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(key, SwitchArrayOperation.SWITCH_ARRAY_PUT, paramProto);
    }

    public static void onEnglishEnablePrediction(boolean enable) {
        MultiLanguageStaticsHelper.increaseEbNumber(enable ? EbIndex.EB_ENGLISH_ENABLE_PREDICTION : EbIndex.EB_ENGLISH_DISABLE_PREDICTION);
        addEnglishSwitch(EaIndex.ENGLISH_PREDICTION, enable);
    }

    public static void onEnglishClickTimes(boolean prediction) {
        MultiLanguageStaticsHelper.increaseEbNumber(prediction ? EbIndex.EB_ENGLISH_CLICK_TIMES_WITH_PREDICTION : EbIndex.EB_ENGLISH_CLICK_TIMES_WITHOUT_PREDICTION);
    }

    public static void onEnglishInputDigitBySlide() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_BY_SLIDE);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_TOTAL_TIMES);
    }

    public static void onEnglishInputDigitByLongPress() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_BY_LONG_PRESS);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_TOTAL_TIMES);
    }

    public static void onEnglishInputDigitByNumberLine() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_BY_NUMBER_LINE);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_TOTAL_TIMES);
    }

    public static void onEnglishInputDigitBySymbol(boolean isAlphabetQwerty) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_BY_SYMOBLE);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_TOTAL_TIMES);

        if (isAlphabetQwerty) {
            EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_ENGLISH_INPUT_DIGIT_IN_SYMBOL_FROM_QWERTY_KEYBOARD);
        }
    }

    public static void onEnglishInputDigitBySoduku(boolean isAlphabetQwerty) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_BY_SODUKU);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_DIGIT_INPUT_TOTAL_TIMES);

        if (isAlphabetQwerty) {
            EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_ENGLISH_INPUT_DIGIT_IN_DIGIT_SUDOKU_FROM_QWERTY_KEYBOARD);
        }
    }

    public static void onEnglishInputSymbolBySlide() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_BY_SLIDE);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_TOTAL_TIMES);
    }

    public static void onEnglishInputSymbolByLongPress() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_BY_LONG_PRESS);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_TOTAL_TIMES);
    }

    public static void onEnglishInputSymbolBySoduku() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_BY_SODUKU);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_TOTAL_TIMES);
    }

    public static void onEnglishInputSymbolBySymbol() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_BY_SYMOBLE);
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_INPUT_TOTAL_TIMES);
    }

    public static void onEnglishInputSymbolCommaAndPeriod() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SYMBOL_COMMA_AND_PERIOD);
    }

    public static void onEnglishShiftClick(boolean doubleClick) {
        MultiLanguageStaticsHelper.increaseEbNumber(doubleClick ?
                EbIndex.EB_ENGLISH_SHIFT_DOUBLE_CLICK :
                EbIndex.EB_ENGLISH_SHIFT_CLICK);
    }

    public static void onEnglishInputLetterWhenShiftLocked() {
        MultiLanguageStaticsHelper.increaseEbNumber(
                EbIndex.EB_ENGLISH_INPUT_LETTER_WHEN_SHIFT_LOCKED);
    }

    public static void onInputLetterAfterShiftToNormalFromPressed() {
        MultiLanguageStaticsHelper.increaseEbNumber(
                EbIndex.EB_ENGLISH_INPUT_LETTER_AFTER_SHIFT_TO_NORMAL_FROM_PRESSED);
    }

    public static void onAppSettingPrediction(boolean checked) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_APP_SETTING_CLICK_PREDICTION);
        addEnglishSwitch(EaIndex.ENGLISH_PREDICTION, checked);
    }

    public static void onAppSettingAssociation(boolean checked) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_APP_SETTING_CLICK_ASSOCIATION);
        addEnglishSwitch(EaIndex.ENGLISH_ASSOCIATION, checked);
    }

    public static void onAppSettingAutoSpace(boolean checked) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_APP_SETTING_CLICK_AUTO_ADD_SPACE);
        addEnglishSwitch(EaIndex.ENGLISH_AUTO_SPACE, checked);
    }

    public static void onAppSettingAutoCapital(boolean checked) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_APP_SETTING_CLICK_AUTO_SENTENCE_CAPITAL);
        addEnglishSwitch(EaIndex.ENGLISH_AUTO_FIRST_CAP, checked);
    }

    public static void onAppSettingAutoLockCapital(boolean checked) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_APP_SETTING_CLICK_LOCK_CAPITAL);
        addEnglishSwitch(EaIndex.ENGLISH_AUTO_LOCK_CAPITAL, checked);
    }

    public static void onAppSettingEnglishCorrect(boolean checked) {
        addEnglishSwitch(EaIndex.ENGLISH_CORRECT, checked);
    }

    public static void onAppSettingNine(boolean checked) {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_APP_SETTING_CLICK_NINE_KEYBOARD);
        addEnglishSwitch(EaIndex.ENGLISH_USE_NINE_MODE, checked);
    }

    public static void onAppSettingKeyboardWithDigit(boolean checked) {
        addEnglishSwitch(EaIndex.ENGLISH_QWERTY_DIGIT_MODE_USER, checked);
    }

    public static void onEnglishSelectCandidateAfterSwipe() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SELECT_CANDIDATE_AFTER_SWIPE);
    }

    public static void onEnglishClickMoreCandidatesButton() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_CLICK_MORE_CANDIDATES);
    }

    public static void onEnglishSelectCandidateOnMorePage() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SELECT_CANDIDATE_ON_MORE_PAGE);
    }

    public static void onEnglishSelectCandidateBySpace() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SELECT_CANDIDATE_BY_SPACE);
    }

    public static void onEnglishSelectCandidateByClick() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_SELECT_CANDIDATE_BY_CLICK);
    }

    public static void onEnglishSelectShellCandidate(int index) {
        final String indexPb;
        switch (index) {
            case 0:
                indexPb = EbIndex.EB_ENGLISH_SELECT_SHELL_FIRST_CANDIDATE;
                break;
            case 1:
                indexPb = EbIndex.EB_ENGLISH_SELECT_SHELL_SECOND_CANDIDATE;
                break;
            case 2:
                indexPb = EbIndex.EB_ENGLISH_SELECT_SHELL_THIRD_CANDIDATE;
                break;
            default:
                indexPb = EbIndex.EB_ENGLISH_SELECT_SHELL_FOURTH_AND_MORE_CANDIDATE;
                break;
        }
        MultiLanguageStaticsHelper.increaseEbNumber(indexPb);
    }

    public static void onEnableNine(boolean enable) {
        addEnglishSwitch(EaIndex.ENGLISH_HAS_NINE_MODE, enable);
    }

    public static void onSelectQwerty(boolean qwerty) {
        MultiLanguageStaticsHelper.increaseEbNumber(qwerty ? EbIndex.EB_ENGLISH_SELECT_QWERT : EbIndex.EB_ENGLISH_SELECT_PHONE);
    }

    public static void onContinue3TimesCommit() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_CONTINUE_3_TIMES_COMMIT);
    }

    public static void onContinue5TimesCommit() {
        MultiLanguageStaticsHelper.increaseEbNumber(EbIndex.EB_ENGLISH_CONTINUE_5_TIMES_COMMIT);
    }

    public static void onEnableSynonym(boolean enable) {
        addEnglishSwitch(EaIndex.ENGLISH_SYNONYM, enable);
    }

    public static void onSwitchToSymbolKeyboard(boolean isQwertyKeyboard, final int keyboardMode) {
        if (isQwertyKeyboard) {
            EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_ENGLISH_SWITCH_TO_SYMBOL_IN_QWERTY_KEYBOARD);
            if (keyboardMode == EnglishMode.Pro){
                EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_CLICK_SYMBOL_KEY_IN_ENGLISH_PRO);
            }
        }
    }

    public static void onSwitchToDigitKeyboard(boolean isQwertyKeyboard, final int keyboardMode) {
        if (isQwertyKeyboard) {
            EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_ENGLISH_SWITCH_TO_DIGIT_SUDOKU_IN_QWERTY_KEYBOARD);
            if (keyboardMode == EnglishMode.Pro){
                EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_CLICK_DIGIT_KEY_IN_ENGLISH_PRO);
            }
        }
    }

    public static void onClickCommaInAlphabetKeyboard() {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_ENGLISH_CLICK_COMMA_COUNT);
    }

    public static void onClickPeriodInAlphabetKeyboard() {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_ENGLISH_CLICK_PERIOD_COUNT);
    }

    public static void onInputCommaInPro() {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_COMMIT_COMMA_IN_ENGLISH_PRO);
    }

    public static void onInputPeriodInPro() {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(EbIndex.EB_COMMIT_PERIOD_IN_ENGLISH_PRO);
    }
}
