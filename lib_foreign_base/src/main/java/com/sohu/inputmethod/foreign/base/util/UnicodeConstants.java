package com.sohu.inputmethod.foreign.base.util;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author dongjianye
 */
public final class UnicodeConstants {
    private UnicodeConstants() {
    }

    public static final int INVALID_CODE_POINT = -1;

    public static final int PARAGRAPH_SEPARATOR = 0x2029;
    public static final int LINE_SEPARATOR = 0x2028;
    public static final int NEW_LINE = 0x000A;
    public static final int[] PARAGRAPH_SEPARATORS = { 0x000A, 0x000D, 0x2028, 0x2029 };
    public static final int INVERTED_QUESTION_MARK = 0x00BF;
    public static final int INVERTED_EXCLAMATION_MARK = 0x00A1;

    public static final int LATIN_SMALL_LETTER_S = 0x73;
    public static final int LATIN_SMALL_LETTER_SHARP_S = 0x00DF;

    public static final int GREEK_SMALL_LETTER_UPSILON = 0x03C5;
    public static final int GREEK_SMALL_LETTER_UPSILON_WITH_DIALYTIKA_AND_TONOS = 0x03B0;

    public static final int GREEK_SMALL_LETTER_IOTA = 0x03B9;
    public static final int GREEK_SMALL_LETTER_IOTA_WITH_DIALYTIKA_AND_TONOS = 0x0390;
    public static final int ZERO_WIDTH_JOINER = 0x200D;


    public static final int VARIATION_SELECTOR$1 = 0xFE00;
    public static final int VARIATION_SELECTOR$15 = 0xFE0E;
    public static final int VARIATION_SELECTOR$16 = 0xFE0F;

    public static boolean isVariationSelector(int codePoint) {
        return codePoint == VARIATION_SELECTOR$1 || codePoint == VARIATION_SELECTOR$15 || codePoint == VARIATION_SELECTOR$16;
    }


//    public static final int EMOJI_REGION_START = 0x1F300;
//    public static final int EMOJI_REGION_END = 0x1F5FF;


    public static boolean isPunctuation(int codePoint) {
        final int type = Character.getType(codePoint);
        return type == Character.DASH_PUNCTUATION
                || type == Character.START_PUNCTUATION
                || type == Character.END_PUNCTUATION
                || type == Character.CONNECTOR_PUNCTUATION
                || type == Character.OTHER_PUNCTUATION
                || type == Character.INITIAL_QUOTE_PUNCTUATION
                || type == Character.FINAL_QUOTE_PUNCTUATION;
    }

    private static boolean inSortedArray(final int[] array, final int code) {
        return Arrays.binarySearch(array, code) >= 0;
    }

    public static boolean isParagraphSeparator(final int codePoint) {
        return inSortedArray(PARAGRAPH_SEPARATORS, codePoint);
    }

//    public static boolean isEmoji(int codePoint) {
//        return (codePoint >= EMOJI_REGION_START && codePoint <= EMOJI_REGION_END);
//    }

    public static boolean isLetterAsSymbol(int codePoint, Locale locale) {
        if (locale.getLanguage().startsWith("el")) {
            return false;
        }
        switch (codePoint) {
            case 0x03b1:
            case 0x03b2:
            case 0x03b8:
            case 0x03c0:
            case 0x0394:
            case 0x03a3:
            case 0x03a9:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSeparateWordBySpace(String token) {
        if (token.equalsIgnoreCase("th")
                || token.equalsIgnoreCase("my")
                || token.equalsIgnoreCase("myMM")
                || token.equalsIgnoreCase("km")
                || token.equalsIgnoreCase("lo")
                || token.equalsIgnoreCase("ja")
                || token.equalsIgnoreCase("zh")
                || token.equalsIgnoreCase("zhtw")
                || token.equalsIgnoreCase("zhhk")) {
            return false;
        }
        return true;
    }

    public static boolean isImplementBySecondaryConvert(String token) {
        if (!TextUtils.isEmpty(token)) {
            if (token.toLowerCase().startsWith("zh")) {
                return true;
            }
            if (token.toLowerCase().startsWith("zhtw")) {
                return true;
            }
            if (token.toLowerCase().startsWith("zhhk")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isProbableCjk(int codePoint) {
        try {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(codePoint);
            if (ub != null) {
                final String ubName = ub.toString();
                if (ubName != null && ubName.startsWith("CJK_")) {
                    return true;
                }
                /*if (ubName != null && ubName.startsWith("HANGUL_")) {
                    return true;
                }*/
                if (ub == Character.UnicodeBlock.YIJING_HEXAGRAM_SYMBOLS
                        || ub == Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS
                        || ub == Character.UnicodeBlock.KATAKANA
                        || ub == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS
                        || ub == Character.UnicodeBlock.HIRAGANA
                        || ub == Character.UnicodeBlock.IDEOGRAPHIC_DESCRIPTION_CHARACTERS) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
//            EngineDebug.w("Werid", "exception code point = 0x" + Integer.toHexString(codePoint));
            return false;
        }
    }

    public static boolean isProbableNonSpaceSeparatedAbugida(int codePoint) {
        try {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(codePoint);
            if (ub != null) {
                final String ubName = ub.toString();
                if (ub == Character.UnicodeBlock.THAI) {
                    return true;
                }

                if (ubName != null && ubName.startsWith("MYANMAR_")) {
                    return true;
                }

                if (ub == Character.UnicodeBlock.LAO) {
                    return true;
                }

                if (ub == Character.UnicodeBlock.KHMER) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
//            EngineDebug.w("Werid", "exception code point = 0x" + Integer.toHexString(codePoint));
            return false;
        }
    }


    public static boolean isCodePointNotSupportAutoPick(int codePoint) {
        return isProbableNonSpaceSeparatedAbugida(codePoint) || isProbableCjk(codePoint);
    }

    public static boolean isLetterType(int type) {
        return isCasedLetterType(type)
                || type == Character.MODIFIER_LETTER
                || type == Character.OTHER_LETTER;
    }

    public static boolean isCasedLetterType(int type) {
        return type == Character.UPPERCASE_LETTER
                || type == Character.LOWERCASE_LETTER
                || type == Character.TITLECASE_LETTER;
    }

    public static boolean isPunctuationType(int type) {
        return type == Character.CONNECTOR_PUNCTUATION
                || type == Character.DASH_PUNCTUATION
                || type == Character.START_PUNCTUATION
                || type == Character.END_PUNCTUATION
                || type == Character.INITIAL_QUOTE_PUNCTUATION
                || type == Character.FINAL_QUOTE_PUNCTUATION
                || type == Character.OTHER_PUNCTUATION;
    }

    public static boolean isSymbolType(int type) {
        return type == Character.MATH_SYMBOL
                || type == Character.CURRENCY_SYMBOL
                || type == Character.MODIFIER_SYMBOL
                || type == Character.OTHER_SYMBOL;
    }

    public static boolean isNumberType(int type) {
        return type == Character.DECIMAL_DIGIT_NUMBER
                || type == Character.LETTER_NUMBER
                || type == Character.OTHER_NUMBER;
    }

    public static boolean isMarkType(int type) {
        return type == Character.NON_SPACING_MARK
                || type == Character.ENCLOSING_MARK
                || type == Character.COMBINING_SPACING_MARK;
    }

    public static boolean isSeparatorType(int type) {
        return type == Character.SPACE_SEPARATOR
                || type == Character.LINE_SEPARATOR
                || type == Character.PARAGRAPH_SEPARATOR;
    }

    public static boolean isControlType(int type) {
        return type == Character.CONTROL
                || type == Character.FORMAT
                || type == Character.SURROGATE
                || type == Character.PRIVATE_USE
                || type == Character.UNASSIGNED;
    }
}
