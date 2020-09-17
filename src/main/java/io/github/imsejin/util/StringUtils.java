package io.github.imsejin.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * String utilities
 */
public final class StringUtils {

    /**
     * Whitespace character.
     */
    private static final char WHITE_SPACE = '\u0020';

    private StringUtils() {
    }

    /**
     * Checks whether the string is null or empty.
     *
     * <pre>{@code
     *     isNullOrEmpty(null);     // true
     *     isNullOrEmpty("");       // true
     *     isNullOrEmpty("abc");    // false
     * }</pre>
     *
     * @param str string
     * @return whether the string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * If the string is null or empty, this returns default string.
     * <br>
     * If not, this returns original string.
     *
     * <pre>{@code
     *     ifNullOrEmpty(null, "(empty)");      // (empty)
     *     ifNullOrEmpty("", "(empty)");        // (empty)
     *     ifNullOrEmpty(" ", "(empty)");       // \u0020
     * }</pre>
     *
     * @param str          original string
     * @param defaultValue default string
     * @return original string or default string
     */
    public static String ifNullOrEmpty(String str, String defaultValue) {
        return isNullOrEmpty(str) ? defaultValue : str;
    }

    /**
     * If the string is null or empty, this returns default string.
     * <br>
     * If not, this returns original string.
     *
     * <pre>{@code
     *     ifNullOrEmpty(null, () -> "(empty)");    // (empty)
     *     ifNullOrEmpty("", () -> "(empty)");      // (empty)
     *     ifNullOrEmpty(" ", () -> "(empty)");     // \u0020
     * }</pre>
     *
     * @param str      original string
     * @param supplier supplier that returns default string
     * @return original string or default string
     */
    public static String ifNullOrEmpty(String str, Supplier<String> supplier) {
        return isNullOrEmpty(str) ? supplier.get() : str;
    }

    /**
     * Checks whether the string is null or blank.
     *
     * <pre>{@code
     *     isNullOrBlank(null);     // true
     *     isNullOrBlank("");       // true
     *     isNullOrBlank(" ");      // true
     *     isNullOrBlank(" ABC");   // false
     * }</pre>
     *
     * @param str string
     * @return whether the string is null or blank
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * If the string is null or blank, this returns default string.
     * <br>
     * If not, this returns original string.
     *
     * <pre>{@code
     *     ifNullOrBlank(null, "(empty)");      // (empty)
     *     ifNullOrBlank("", "(empty)");        // (empty)
     *     ifNullOrBlank(" ", "(empty)");       // (empty)
     *     ifNullOrBlank(" ABC", "(empty)");    //  ABC
     * }</pre>
     *
     * @param str          original string
     * @param defaultValue default string
     * @return original string or default string
     */
    public static String ifNullOrBlank(String str, String defaultValue) {
        return isNullOrBlank(str) ? defaultValue : str;
    }

    /**
     * If the string is null or blank, this returns default string.
     * <br>
     * If not, this returns original string.
     *
     * <pre>{@code
     *     ifNullOrBlank(null, () -> "(empty)");    // (empty)
     *     ifNullOrBlank("", () -> "(empty)");      // (empty)
     *     ifNullOrBlank(" ", () -> "(empty)");     // (empty)
     *     ifNullOrBlank(" ABC", () -> "(empty)");  //  ABC
     * }</pre>
     *
     * @param str      original string
     * @param supplier supplier that returns default string
     * @return original string or default string
     */
    public static String ifNullOrBlank(String str, Supplier<String> supplier) {
        return isNullOrBlank(str) ? supplier.get() : str;
    }

    /**
     * 공백 문자열이 하나라도 있는지 확인한다.
     *
     * <pre>{@code
     *     anyNullOrBlank(null, " ");       // true
     *     anyNullOrBlank(null, "ABC");     // true
     *     anyNullOrBlank("ABC", "");       // true
     *     anyNullOrBlank(" ", "ABC");      // true
     *     anyNullOrBlank(" ABC", "ABC");   // false
     * }</pre>
     *
     * @param strs strings
     * @return whether any strings are null or blank
     */
    public static boolean anyNullOrBlank(String... strs) {
        if (strs == null || strs.length == 0) return true;

        for (String str : strs) {
            if (isNullOrBlank(str)) return true;
        }

        return false;
    }

    /**
     * 모두 공백 문자열인지 확인한다.
     *
     * <pre>{@code
     *     allNullOrBlank(null, " ");       // true
     *     allNullOrBlank(null, "ABC");     // false
     *     allNullOrBlank("ABC", "");       // false
     *     allNullOrBlank(" ", "ABC");      // false
     *     allNullOrBlank(" ABC", "ABC");   // false
     * }</pre>
     *
     * @param strs strings
     * @return whether all strings are null or blank
     */
    public static boolean allNullOrBlank(String... strs) {
        if (strs == null || strs.length == 0) return true;

        return Arrays.stream(strs).allMatch(StringUtils::isNullOrBlank);
    }

    /**
     * `기준 문자열`과 일치하는 문자열이 하나라도 있는지 확인한다.
     *
     * <pre>{@code
     *     anyEquals (null, null);          // false
     *     anyEquals("", null);             // false
     *     anyEquals(null, "");             // false
     *     anyEquals("", null, "");         // true
     *     anyEquals("ABC", "abc");         // false
     *     anyEquals("ABC", "abc", "ABC");  // true
     * }</pre>
     *
     * @param criterion criterion string
     * @param strs      strings
     * @return whether any strings are equal to criterion string
     */
    public static boolean anyEquals(String criterion, String... strs) {
        if (criterion == null || strs == null || strs.length == 0) return false;

        for (String str : strs) {
            if (criterion.equals(str)) return true;
        }

        return false;
    }

    public static String padStart(int len, String origin) {
        if (origin.length() >= len) return origin;
        return repeat(WHITE_SPACE, len) + origin;
    }

    public static String padStart(int len, String origin, String appendix) {
        if (origin.length() >= len) return origin;
        return repeat(appendix, len) + origin;
    }

    public static String padEnd(int len, String origin) {
        if (origin.length() >= len) return origin;
        return origin + repeat(WHITE_SPACE, len);
    }

    public static String padEnd(int len, String origin, String appendix) {
        if (origin.length() >= len) return origin;
        return origin + repeat(appendix, len);
    }

    /**
     * Count of.
     *
     * @param str        the str
     * @param charToFind the char to find
     * @return the int
     */
    public static int countOf(String str, String charToFind) {
        int findLength = charToFind.length();
        int count = 0;

        for (int idx = str.indexOf(charToFind); idx >= 0; idx = str.indexOf(charToFind, idx + findLength)) {
            count++;
        }

        return count;
    }

    /**
     * Reverses all the characters in a string.
     *
     * @param str string to be reversed
     * @return reversed string
     * @see StringBuffer#reverse
     */
    public static String reverse(String str) {
        if (str == null) return null;
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 가장 마지막에 일치하는 문구를 원하는 문구로 대체한다.
     *
     * <pre>{@code
     *     replaceLast("ABC%DEF%GHI", "%", "-");    // ABC%DEF-GHI
     *     replaceLast("ABC%DEF%GHI", "%", "\\$");  // ABC%DEF$GHI
     * }</pre>
     *
     * @param text        text
     * @param regex       regular expression
     * @param replacement replacement string
     * @return string replaced with replacement by last replacer
     */
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    /**
     * 3자리 숫자마다 ,(comma)로 구분한 문자열을 반환한다.
     *
     * <pre>{@code
     *     formatComma("");         // 0
     *     formatComma("-100");     // -100
     *     formatComma("100000");   // 100,000
     * }</pre>
     *
     * @param amount amount number
     * @return formatted number with comma
     */
    public static String formatComma(String amount) {
        return new DecimalFormat("###,###,###,###,###,###,###").format(amount);
    }

    /**
     * 3자리 숫자마다 ,(comma)로 구분한 문자열을 반환한다.
     *
     * <pre>{@code
     *     formatComma(0);      // 0
     *     formatComma(-100);   // -100
     *     formatComma(100000); // 100,000
     * }</pre>
     *
     * @param amount amount number
     * @return formatted number with comma
     */
    public static String formatComma(long amount) {
        return new DecimalFormat("###,###,###,###,###,###,###").format(amount);
    }

    /**
     * `해당 문자열`을 원하는 만큼 반복하여 복제한다.
     *
     * <pre>{@code
     *     repeat(null, 2);     // nullnull
     *     repeat("", 5);       // \u0000
     *     repeat("abc", 3);    // abcabcabc
     * }</pre>
     *
     * @param str string to be repeated
     * @param cnt repetition count
     * @return repeated string
     */
    public static String repeat(String str, int cnt) {
        return String.join("", Collections.nCopies(cnt, str));
    }

    /**
     * `해당 문자열`을 원하는 만큼 반복하여 복제한다.
     *
     * <pre>{@code
     *     repeat(' ', 3);    // \u0020
     *     repeat('a', 3);    // aaa
     * }</pre>
     *
     * @param c   character to be repeated
     * @param cnt repetition count
     * @return repeated string
     */
    public static String repeat(char c, int cnt) {
        return String.join("", Collections.nCopies(cnt, String.valueOf(c)));
    }

    public static String match(String regex, String src) {
        return match(regex, src, 0);
    }

    public static String match(String regex, String src, int groupNo) {
        Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(src);

        String matched = null;
        while (matcher.find()) {
            matched = matcher.group(groupNo);
        }

        return matched;
    }

}
