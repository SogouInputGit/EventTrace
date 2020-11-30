package com.sohu.inputmethod.foreign.base.util;

import android.text.TextUtils;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author dongjianye
 */
public final class StringTools {

    public static final int[] EMPTY_CODE_POINT_ARRAY = {};
    public static final List<Integer> EMPTY_CODE_POINT_LIST = new ArrayList<>(0);
    public static final int INVALID_CODE_POINT = -1;

    public static final int CM_UNKNOWN = 0;
    public static final int CM_LOWERCASE = 1;
    public static final int CM_TITLECASE = 2;
    public static final int CM_UPPERCASE = 3;

    private StringTools() {
    }

    public static String getPrecedingLine(final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getLineInstance(locale);
        bi.setText(text);

        int end = bi.last();
        int start = bi.previous();
        if (start != BreakIterator.DONE && end > start) {
            return text.substring(start, end);
        }
        return text;
    }

    public static String getFollowingLine(final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getLineInstance(locale);
        bi.setText(text);

        int start = bi.first();
        int end = bi.next();
        if (start != BreakIterator.DONE && end > start) {
            return text.substring(start, end);
        }
        return text;
    }

    public static String getPrecedingSentence(final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getSentenceInstance(locale);
        bi.setText(text);

        int end = bi.last();
        int start = bi.previous();
        if (start != BreakIterator.DONE && end > start) {
            if (end == text.length()) {
                final int lastCodePoint = StringTools.lastCodePoint(text);
                if (UnicodeConstants.isParagraphSeparator(lastCodePoint)) {
                    return "";
                }
            }
            return text.substring(start, end);
        }
        return text;
    }

    public static String getFollowingSentence(final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getSentenceInstance(locale);
        bi.setText(text);

        int start = bi.first();
        int end = bi.next();
        if (start != BreakIterator.DONE && end > start) {
            return text.substring(start, end);
        }
        return text;
    }


    public static boolean isOnlySpaceCharString(final String text) {
        for (int index = 0; index < text.length(); index = Character.offsetByCodePoints(text, index, 1)) {
            final int codePoint = Character.codePointAt(text, index);
            if (!Character.isSpaceChar(codePoint)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigitNumberString(final String text) {
        for (int index = 0; index < text.length(); index = Character.offsetByCodePoints(text, index, 1)) {
            final int codePoint = Character.codePointAt(text, index);
            if (!Character.isDigit(codePoint)) {
                return false;
            }
        }
        return true;
    }

    public static int countCodePoint(final CharSequence text) {
        if (text == null) {
            throw new NullPointerException("text == null");
        }
        if (text.length() > 0) {
            return Character.codePointCount(text, 0, text.length());
        }
        return 0;
    }

    public static int codePointAt(final CharSequence text, int index) {
        if (text == null) {
            throw new NullPointerException("text == null");
        }
        int len = countCodePoint(text);
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException();
        }

        for (int off = 0, idx = 0; off < text.length(); off = Character.offsetByCodePoints(text, off, 1), idx++) {
            if (idx == index) {
                return Character.codePointAt(text, off);
            }
        }

//        if (BuildConfig.THROW_DEBUG_EXCEPTION) {
//            throw new NotFoundException("Not found code point. index = " + index);
//        }
        return -1;
    }

    public static int codePointByIndex(final CharSequence text, int index) {
        if (text == null) {
            throw new NullPointerException("text == null");
        }

        int len = countCodePoint(text);
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException();
        }

        for (int off = 0, idx = 0; off < text.length(); off = Character.offsetByCodePoints(text, off, 1), idx++) {
            if (idx == index) {
                return Character.codePointAt(text, off);
            }
        }
//        if (BuildConfig.THROW_DEBUG_EXCEPTION) {
//            throw new NotFoundException("Not found code point. index = " + index);
//        }
        return -1;
    }

    public static int codePointByOffset(final CharSequence text, int offset) {
        if (text == null) {
            throw new NullPointerException("text == null");
        }

        int len = countCodePoint(text);
        if (Math.abs(offset) >= len) {
            throw new IndexOutOfBoundsException();
        }

        int index = (offset < 0) ? (len + offset) : offset;
        for (int off = 0, idx = 0; off < text.length(); off = Character.offsetByCodePoints(text, off, 1), idx++) {
            if (idx == index) {
                return Character.codePointAt(text, off);
            }
        }
//        if (BuildConfig.THROW_DEBUG_EXCEPTION) {
//            throw new NotFoundException("Not found code point. offset = " + offset);
//        }
        return -1;
    }

    public static int firstCodePoint(final CharSequence text) {
        if (text == null || text.length() == 0) {
            return UnicodeConstants.INVALID_CODE_POINT;
        }
        return Character.codePointAt(text, 0);
    }

    public static int lastCodePoint(final CharSequence text) {
        if (text == null || text.length() == 0) {
            return UnicodeConstants.INVALID_CODE_POINT;
        }
        return Character.codePointBefore(text, text.length());
    }

    public static String makeUppercase(final String raw, final Locale locale) {
        if (raw == null || raw.length() == 0) {
            return raw;
        }
        // special for German
        if (locale.getLanguage().startsWith("de") && raw.contains("ß")) {
            return raw.replace("ß", "ẞ").toUpperCase(locale);
        }

        if (locale.getLanguage().startsWith("el")) {
            StringBuilder sb = new StringBuilder();

            Iterator<Integer> it = StringTools.iterator(raw);
            while (it.hasNext()) {
                final int cp = it.next();
                sb.appendCodePoint(Character.toUpperCase(cp));
            }
            return sb.toString();
        }
        return raw.toUpperCase(locale);
    }

    public static String makeLowercase(final String raw, final Locale locale) {
        if (raw == null || raw.length() == 0) {
            return raw;
        }
        // special for German
        if (locale.getLanguage().startsWith("de") && raw.contains("ẞ")) {
            return raw.replace("ẞ", "ß").toLowerCase(locale);
        }


        if (locale.getLanguage().startsWith("el")) {
            StringBuilder sb = new StringBuilder();

            Iterator<Integer> it = StringTools.iterator(raw);
            while (it.hasNext()) {
                final int cp = it.next();
                sb.appendCodePoint(Character.toLowerCase(cp));
            }
            return sb.toString();
        }
        return raw.toLowerCase(locale);
    }

    public static String makeTitlecase(final String raw, final Locale locale) {
        if (raw == null || raw.length() == 0) {
            return raw;
        }

        if (locale.getLanguage().startsWith("el")) {
            StringBuilder sb = new StringBuilder();

            Iterator<Integer> it = StringTools.iterator(raw);
            while (it.hasNext()) {
                final int cp = it.next();
                if (sb.length() == 0) {
                    sb.appendCodePoint(Character.toUpperCase(cp));
                } else {
                    sb.appendCodePoint(Character.toLowerCase(cp));
                }
            }
            return sb.toString();
        }
        int off = raw.offsetByCodePoints(0, 1);

        if (locale.getLanguage().startsWith("de")) {
            if (raw.startsWith("ß")) {
                return "ẞ" + makeLowercase(raw.substring(off), locale);
            } else if (raw.startsWith("ẞ")) {
                return raw.substring(0, off) + makeLowercase(raw.substring(off), locale);
            } else {
                return raw.substring(0, off).toUpperCase(locale) + makeLowercase(raw.substring(off), locale);
            }
        } else {
            return raw.substring(0, off).toUpperCase(locale) + makeLowercase(raw.substring(off), locale);
        }
    }

    public static boolean startsWith(final CharSequence target, final CharSequence prefix) {
        return regionMatches(target, 0, prefix, 0, prefix.length());
    }


    public static boolean endsWith(final CharSequence target, final CharSequence suffix) {
        return regionMatches(target, target.length() - suffix.length(), suffix, 0, suffix.length());
    }

    public static boolean regionMatches(final CharSequence target, int thisStart, final CharSequence find, int start, int length) {
        if (target == null) {
            throw new NullPointerException("target == null");
        }
        if (start < 0 || find.length() - start < length) {
            return false;
        }
        if (thisStart < 0 || target.length() - thisStart < length) {
            return false;
        }
        if (length <= 0) {
            return true;
        }
        for (int i = 0; i < length; ++i) {
            if (target.charAt(thisStart + i) != find.charAt(start + i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean compare(final CharSequence target, int thisStart, int thisEnd, final CharSequence find, int start, int length) {
        if (target == null) {
            throw new NullPointerException("target == null");
        }
        if (start < 0 || find.length() - start < length) {
            return false;
        }
        if (thisStart < 0 || thisEnd < thisStart || thisEnd - thisStart < target.length() || thisEnd - thisStart != length) {
            return false;
        }
        if (length <= 0) {
            return true;
        }
        for (int i = 0; i < length; ++i) {
            if (target.charAt(thisStart + i) != find.charAt(start + i)) {
                return false;
            }
        }
        return true;
    }


    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
        }

        public NotFoundException(String name) {
            super(name);
        }
    }

    public static Iterator<Integer> reverseIterator(final CharSequence text) {
        return new Iterator<Integer>() {
            int nextIndex = text.length();

            public boolean hasNext() {
                return nextIndex > 0;
            }

            public Integer next() {
                int result = Character.codePointBefore(text, nextIndex);
                nextIndex -= Character.charCount(result);
                return result;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static Iterator<Integer> iterator(final CharSequence text) {
        return new Iterator<Integer>() {
            int nextIndex = 0;

            public boolean hasNext() {
                return nextIndex < text.length();
            }

            public Integer next() {
                int result = Character.codePointAt(text, nextIndex);
                nextIndex += Character.charCount(result);
                return result;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    public static List<Integer> toCodePointList(final CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return EMPTY_CODE_POINT_LIST;
        } else {
            List<Integer> ret = new ArrayList<>();
            Iterator<Integer> it = iterator(text);
            while (it.hasNext()) {
                ret.add(it.next());
            }

            return ret;
        }
    }

    public static int[] toCodePointArray(final CharSequence text) {
        return toCodePointArray(text, 0, text.length());
    }

    public static int[] toCodePointArray(final CharSequence text, final int startIdx, final int endIdx) {
        final int length = text.length();
        if (length <= 0) {
            return EMPTY_CODE_POINT_ARRAY;
        }
        final int[] codePoints = new int[Character.codePointCount(text, startIdx, endIdx)];
        toCodePointArray(codePoints, text, startIdx, endIdx);
        return codePoints;
    }

    public static int toCodePointArray(final int[] dest, final CharSequence text,
                                       final int startIdx, final int endIdx) {
        int destIndex = 0;
        for (int index = startIdx; index < endIdx; index = Character.offsetByCodePoints(text, index, 1)) {
            final int codePoint = Character.codePointAt(text, index);
            dest[destIndex] = codePoint;
            destIndex++;
        }
        return destIndex;
    }

    public static List<String> breakdownSentenceForward(final String text, final Locale locale) {
        List<String> ret = new ArrayList<String>(32);
        breakdownSentenceForward(ret, text, locale);
        return ret;
    }

    public static void breakdownSentenceForward(List<String> ret, final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getWordInstance(locale);
        bi.setText(text);

        int start = bi.first();
        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            ret.add(text.substring(start, end));
        }
    }


    public static List<String> breakdownSentenceBackward(final String text, final Locale locale) {
        List<String> ret = new ArrayList<String>(32);
        breakdownSentenceBackward(ret, text, locale);
        return ret;
    }

    public static void breakdownSentenceBackward(List<String> ret, final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getWordInstance(locale);
        bi.setText(text);

        int end = bi.last();
        for (int start = bi.previous(); start != BreakIterator.DONE; end = start, start = bi.previous()) {
            ret.add(0, text.substring(start, end));
        }
    }

    public static List<String> breakdownByCharacterForward(final String text, final Locale locale) {
        List<String> ret = new ArrayList<String>(32);
        breakdownByCharacterForward(ret, text, locale);
        return ret;
    }

    public static void breakdownByCharacterForward(List<String> ret, final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getCharacterInstance(locale);
        bi.setText(text);

        int start = bi.first();
        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            ret.add(text.substring(start, end));
        }
    }

    public static List<String> breakdownByCharacterBackward(final String text, final Locale locale) {
        List<String> ret = new ArrayList<String>(text.length());
        breakdownByCharacterBackward(ret, text, locale);
        return ret;
    }

    public static void breakdownByCharacterBackward(List<String> ret, final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getCharacterInstance(locale);
        bi.setText(text);

        int end = bi.last();
        for (int start = bi.previous(); start != BreakIterator.DONE; end = start, start = bi.previous()) {
            ret.add(0, text.substring(start, end));
        }
    }

    public static boolean containLetterAsSymbol(final CharSequence text, final Locale locale) {
        Iterator<Integer> it = StringTools.iterator(text);
        while (it.hasNext()) {
            final int code = it.next();
            if (UnicodeConstants.isLetterAsSymbol(code, locale)) {
                return true;
            }
        }
        return false;
    }

    public static int fixCodePointAlignmentAtHead(final CharSequence text) {
        return fixCodePointAlignmentAtHead(text, false);
    }

    public static int fixCodePointAlignmentAtHead(final CharSequence text, boolean expand) {
        int offset = 0;
        if (text.length() > 0) {
            char first = text.charAt(0);

            if (Character.isLowSurrogate(first)) {
                offset = expand ? -1 : 1;
            }
        }
        return offset;
    }

    public static int fixCodePointAlignmentAtTail(final CharSequence text) {
        return fixCodePointAlignmentAtTail(text, false);
    }

    public static int fixCodePointAlignmentAtTail(final CharSequence text, boolean expand) {
        int offset = 0;
        if (text.length() > 0) {
            char last = text.charAt(text.length() - 1);

            if (Character.isHighSurrogate(last)) {
                offset = expand ? 1 : -1;
            }
        }
        return offset;
    }

    public static String getLastElement(final String text, final Locale locale) {
        Locale current = locale == null ? Locale.getDefault() : locale;

        final BreakIterator bi = BreakIterator.getWordInstance(current);

        return getLastElementInternal(text, bi);
    }

    private static String getLastElementInternal(final String text, final BreakIterator bi) {
        String ret = "";

        bi.setText(text);

        int end = bi.last();
        for (int start = bi.previous(); start != BreakIterator.DONE; end = start, start = bi.previous()) {
            String ready = text.substring(start, end);
            if (countCodePoint(ready) == 1) {
                final int codePoint = Character.codePointAt(ready, 0);

                if (codePoint == 0x002D && end == text.length()) {
                    ret = ready;
                } else {
                    ret = ready + ret;
                    break;
                }
            } else {
                ret += ready;
                break;
            }
        }

/*        if (ret.length() > 0) {
            Iterator<Integer> rit = reverseIterator(ret);
            int newStart = ret.length();
            while (rit.hasNext()) {
                final int code = rit.next();
                final int count = Character.charCount(code);
                final int type = Character.getType(code);
                if (!UnicodeConstants.isLetterType(type)
                        && (code != 0x002D || newStart == count)) {
                    break;
                }
                newStart -= count;
            }

            return ret.substring(newStart, ret.length());
        }*/
        return ret;
    }

    public static String getLastElementLongestMatching(final String text, final Locale locale) {
        Locale current = locale == null ? Locale.getDefault() : locale;

        final BreakIterator bi = BreakIterator.getWordInstance(current);

        String firstMatch = getLastElementInternal(text, bi);
        List<String> remaining = breakdownByCharacterBackward(text.substring(0, text.length() - firstMatch.length()), locale);
        String test = firstMatch;

        int start = 0;
        int end = 0;

        for (int i = remaining.size(); i > 0; i--) {
            test = remaining.get(i - 1) + test;

            bi.setText(test);
            end = bi.last();
            start = bi.previous();
            if (start == 0 && end == test.length()) {
                return test;
            }
        }

        return firstMatch;
    }

    public static String getLastElement(final String text, final int startIndex, final int endIndex, final Locale locale) {
        return getLastElement(text.substring(startIndex, endIndex), locale);
    }

    private static String getLastSpaceCharDividedStringInternal(final String text, final BreakIterator bi) {
        StringBuilder ret = new StringBuilder();

        bi.setText(text);

        int end = bi.last();
        for (int start = bi.previous(); start != BreakIterator.DONE; end = start, start = bi.previous()) {
            String ready = text.substring(start, end);
            if (countCodePoint(ready) == 1) {
                final int codePoint = Character.codePointAt(ready, 0);

                if (Character.isSpaceChar(codePoint)) {
                    break;
                } else {
                    ret.insert(0, ready);
                }
            } else {
                ret.insert(0, ready);
            }
        }

        return ret.toString();
    }

    public static String getLastSpaceCharDividedString(final String text, final Locale locale) {
        Locale current = locale == null ? Locale.getDefault() : locale;

        final BreakIterator bi = BreakIterator.getWordInstance(current);

        return getLastSpaceCharDividedStringInternal(text, bi);
    }

    public static String getLastSpaceCharDividedString(final String text, final int startIndex, final int endIndex, final Locale locale) {
        return getLastSpaceCharDividedString(text.substring(startIndex, endIndex), locale);
    }

    private static String getFirstSpaceCharDividedStringInternal(final String text, final BreakIterator bi) {
        StringBuilder ret = new StringBuilder();

        bi.setText(text);

        int start = bi.first();
        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            String ready = text.substring(start, end);
            if (countCodePoint(ready) == 1) {
                final int codePoint = Character.codePointAt(ready, 0);

                if (Character.isSpaceChar(codePoint)) {
                    break;
                } else {
                    ret.append(ready);
                }
            } else {
                ret.append(ready);
            }
        }

        return ret.toString();
    }

    public static String getFirstSpaceCharDividedString(final String text, final Locale locale) {
        Locale current = locale == null ? Locale.getDefault() : locale;

        final BreakIterator bi = BreakIterator.getWordInstance(current);

        return getFirstSpaceCharDividedStringInternal(text, bi);
    }

    public static String getFirstSpaceCharDividedString(final String text, final int startIndex, final int endIndex, final Locale locale) {
        return getFirstSpaceCharDividedString(text.substring(startIndex, endIndex), locale);
    }

    public static String getFirstElementLongestMatching(final String text, final Locale locale) {
        Locale current = locale == null ? Locale.getDefault() : locale;

        final BreakIterator bi = BreakIterator.getWordInstance(current);

        String firstMatch = getFirstElementInternal(text, bi);
        List<String> remaining = breakdownByCharacterForward(text.substring(firstMatch.length()), locale);
        String test = firstMatch;

        int start = 0;
        int end = 0;

        for (int i = remaining.size(); i > 0; i--) {
            test = test + remaining.get(i - 1);

            bi.setText(test);
            start = bi.first();
            end = bi.next();

            if (start == 0 && end == test.length()) {
                return test;
            }
        }

        return firstMatch;
    }


    public static String getFirstElement(final String text, final Locale locale) {
        Locale current = locale == null ? Locale.getDefault() : locale;

        final BreakIterator bi = BreakIterator.getWordInstance(current);

        return getFirstElementInternal(text, bi);
    }

    private static String getFirstElementInternal(final String text, final BreakIterator bi) {
        String ret = "";
        bi.setText(text);

        int start = bi.first();
        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            String ready = text.substring(start, end);
            if (countCodePoint(ready) == 1) {
                final int codePoint = Character.codePointAt(ready, 0);

                if (codePoint == 0x002D && start == 0) {
                    ret = ready;
                } else {
                    ret += ready;
                    break;
                }
            } else {
                ret += ready;
                break;
            }
        }
/*        if (ret.length() > 0) {
            Iterator<Integer> it = iterator(ret);
            int newEnd = 0;
            int oldEnd = ret.length();
            while (it.hasNext()) {
                final int code = it.next();
                final int count = Character.charCount(code);
                final int type = Character.getType(code);
                if (!UnicodeConstants.isLetterType(type)
                        && (code != 0x002D || oldEnd - newEnd == count)) {
                    break;
                }
                newEnd += count;
            }

            return ret.substring(0, newEnd);
        }*/

        return ret;
    }

    public static String getFirstElement(final String text, final int startIndex, final int endIndex, final Locale locale) {
        return getFirstElement(text.substring(startIndex, endIndex), locale);
    }


    public static String getLastParagraph(String text) {
        if (!TextUtils.isEmpty(text)) {
            int index = text.lastIndexOf("\n");
            if (index == -1) {
                return text;
            } else {
                return text.substring(index + 1);
            }
        }
        return "";
    }

    public static String getFirstParagraph(String text) {
        if (!TextUtils.isEmpty(text)) {
            int index = text.indexOf("\n");
            if (index == -1) {
                return text;
            } else {
                return text.substring(0, index);
            }
        }
        return "";
    }

    public static String getLastSentence(final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getSentenceInstance(locale);
        bi.setText(text);

        int end = bi.last();
        int start = bi.previous();
        if (start != BreakIterator.DONE && end > start) {
            if (end == text.length()) {
                final int lastCodePoint = StringTools.lastCodePoint(text);
                if (UnicodeConstants.isParagraphSeparator(lastCodePoint)) {
                    return "";
                }
            }
            return text.substring(start, end);
        }

        return text;
    }

    public static String getFirstSentence(final String text, final Locale locale) {
        final BreakIterator bi = BreakIterator.getSentenceInstance(locale);
        bi.setText(text);

        int start = bi.first();
        int end = bi.next();
        if (start != BreakIterator.DONE && end > start) {
            return text.substring(start, end);
        }

        return text;
    }

    public static boolean isCalculableDigitNumber(String text) {
        if (!TextUtils.isEmpty(text)) {
            boolean hasDecimalPoint = false;
            for (int index = 0; index < text.length(); index = Character.offsetByCodePoints(text, index, 1)) {
                final int codePoint = Character.codePointAt(text, index);
                if (!Character.isDigit(codePoint)) {
                    if (!hasDecimalPoint && codePoint == 0x2E) {
                        hasDecimalPoint = true;
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isIpAddress(String text) {
        if (!TextUtils.isEmpty(text)) {
            String[] parts = text.split("\\.");
            if (parts.length > 0 && parts.length < 4) {
                for (String p : parts) {
                    try {
                        int v = Integer.parseInt(p);
                        if (v < 0 || v > 255) {
                            return false;
                        }
                    } catch (Exception ex) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isCalSignal(int codePoint) {
        return (codePoint == 0x2B || codePoint == 0x2D || codePoint == 0x2A || codePoint == 0x2F/* || codePoint == 0x2E*/   // + - * / .
                || codePoint == 0x28 || codePoint == 0x29 || codePoint == 0xD7);  //  ( ) *
        //codePoint == 0x25 %
    }

    public static String pickOffLastDigitNumber(String text) {
        int codePoint = -1;
        int off = text.length();
        for (; off > 0; ) {
            codePoint = Character.codePointBefore(text, off);
            if (!Character.isDigit(codePoint) && codePoint != 0x2E) {
                if (!isCalSignal(codePoint)) {
                    return "";
                } else {
                    break;
                }
            }
            off -= Character.charCount(codePoint);
        }

        String test = text.substring(off);

        if (isOnlyContainPeriodAndDigitNumber(test)) {
            return test;
        }

        return "";
    }

    public static boolean isOnlyContainPeriodAndDigitNumber(String text) {
        if (!TextUtils.isEmpty(text)) {
            for (int index = 0; index < text.length(); index = Character.offsetByCodePoints(text, index, 1)) {
                final int codePoint = Character.codePointAt(text, index);
                if (!Character.isDigit(codePoint) && codePoint != 0x2E) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static String getLastCountCodePoint(String raw, int codePointCount) {
        StringBuilder buf = new StringBuilder(codePointCount);
        int off = raw.length();
        for (int count = 0; off > 0; ) {
            final int code = Character.codePointBefore(raw, off);
            if (count <= codePointCount) {
                buf.insert(0, String.valueOf(Character.toChars(code)));
                count++;
                off -= Character.charCount(code);
            } else {
                break;
            }
        }

        return buf.toString();
    }
}
