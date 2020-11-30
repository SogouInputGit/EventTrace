package com.sohu.inputmethod.foreign.base.code;

/**
 * @author dongjianye on 2020-01-15
 */
public class ForeignKeyCode {

    public static final int INVALIDE_CODE = Integer.MIN_VALUE / 2;

    /*主线定义的code*/
    public static final int KEYCODE_CLEARINPUT = -105;
    public static final int KEYCODE_SYMBOLTABLE = -25;
    public static final int KEYCODE_IME_DIGITS = -23;
    public static final int KEYCODE_RETURN = -36;
    public static final int KEYCODE_SHIFT  = -1;
    public static final int KEYCODE_IME_PREDICTION = -24;
    public static final int KEYCODE_IME_CHANGE = -20;
    public static final int KEYCODE_SPACE = 32;
    public static final int KEYCODE_BACKSPACE  = -5;
    public static final int KEYCODE_VOICE = -106;
    public static final int KEYCODE_LEFT   = 0xf170;
    public static final int KEYCODE_RIGHT  = 0xf171;
    public static final int KEYCODE_UP     = 0xf172;
    public static final int KEYCODE_DOWN   = 0xf173;
    public static final int KEYCODE_ENTER      = 10;

    public static final int KEYCODE_COPY = -30;
    public static final int KEYCODE_PASTE = -31;
    public static final int KEYCODE_CUT = -32;
    public static final int KEYCODE_COPYALL = -33;
    public static final int KEYCODE_CLEAR = -34;
    public static final int KEYCODE_CUTALL = -35;
    public static final int KEYCODE_TEXTSELECT_ALL = -43;
    /*主线定义的code*/

    /*多语言定义的code*/
    public static final int KEYCODE_FOREIGN_FUNC_KEY_START  = -20000;
    public static final int KEYCODE_FOREIGN_CHANGE_PAGE     = KEYCODE_FOREIGN_FUNC_KEY_START - 1;
    public static final int KEYCODE_FOREIGN_SHIFT_CODE      = KEYCODE_FOREIGN_FUNC_KEY_START - 2;
    public static final int KEYCODE_FOREIGN_SHIFT_LABEL     = KEYCODE_FOREIGN_FUNC_KEY_START - 3;
    public static final int KEYCODE_FOREIGN_SYMBOL          = KEYCODE_FOREIGN_FUNC_KEY_START - 4;
    public static final int KEYCODE_FOREIGN_LANGUAGE_CHANGE = KEYCODE_FOREIGN_FUNC_KEY_START - 5;
    public static final int KEYCODE_FOREIGN_LANGUAGE_RETURN = KEYCODE_FOREIGN_FUNC_KEY_START - 6;
    public static final int KEYCODE_FOREIGN_SHIFT_UPPERCASE = KEYCODE_FOREIGN_FUNC_KEY_START - 7;
    public static final int KEYCODE_FOREIGN_SUDOKU          = KEYCODE_FOREIGN_FUNC_KEY_START - 8;
    public static final int KEYCODE_FOREIGN_ENGLISH_SWITCH_PREDICTION = KEYCODE_FOREIGN_FUNC_KEY_START - 9;
    public static final int KEYCODE_FOREIGN_SHIFT_AUTO_UPPERCASE = KEYCODE_FOREIGN_FUNC_KEY_START - 10;

    /** 日语九键 功能键 占坑避免重复*/
    public static final int KEYCODE_JAPANESE_SUDOKU_REVERSE = KEYCODE_FOREIGN_FUNC_KEY_START - 11;
    public static final int KEYCODE_JAPANESE_SUDOKU_LEFT = KEYCODE_FOREIGN_FUNC_KEY_START - 12;
    public static final int KEYCODE_JAPANESE_SUDOKU_RIGHT = KEYCODE_FOREIGN_FUNC_KEY_START - 13;
    public static final int KEYCODE_JAPANESE_SUDOKU_LOWER_KEY = KEYCODE_FOREIGN_FUNC_KEY_START - 14;


}
