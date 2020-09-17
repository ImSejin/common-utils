package io.github.imsejin.util;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * String utilities
 */
public final class StringUtils {

    private StringUtils() {}

    /**
     * Whitespace character.
     */
    private static final char WHITE_SPACE = '\u0020';

    /**
     * Checks whether the string is null or empty.
     *
     * <pre>{@code
     *     isNullOrEmpty(''); // true
     *     isNullOrEmpty(null); // true
     *     isNullOrEmpty('abc'); // false
     * }</pre>
     *
     * @param str original string
     * @return whether the string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * If the string is null or empty, this returns default string.
     * <br/>
     * If not, this returns original string.
     *
     * @param str original string
     * @param defaultValue default string
     * @return the string
     */
    public static String ifNullOrEmpty(String str, String defaultValue) {
        return isNullOrEmpty(str) ? defaultValue : str;
    }

    /**
     * 공백 문자열인지 확인한다.
     *
     * <pre>{@code
     *     isNullOrBlank(null); // true
     *     isNullOrBlank(""); // true
     *     isNullOrBlank(" "); // true
     *     isNullOrBlank(" ABC"); // false
     * }</pre>
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * If the string is null or blank, this returns default string.
     * <br/>
     * If not, this returns original string.
     *
     * @param str original string
     * @param defaultValue default string
     * @return the string
     */
    public static String ifNullOrBlank(String str, String defaultValue) {
        return isNullOrBlank(str) ? defaultValue : str;
    }

    /**
     * 공백 문자열이 하나라도 있는지 확인한다.
     *
     * <pre>
     * StringUtils.anyNullOrBlank(null, " "): true
     * StringUtils.anyNullOrBlank(null, "ABC"): true
     * StringUtils.anyNullOrBlank("ABC", ""): true
     * StringUtils.anyNullOrBlank(" ", "ABC"): true
     * StringUtils.anyNullOrBlank(" ABC", "ABC"): false
     * </pre>
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
     * <pre>
     * StringUtils.allNullOrBlank(null, " "): true
     * StringUtils.allNullOrBlank(null, "ABC"): false
     * StringUtils.allNullOrBlank("ABC", ""): false
     * StringUtils.allNullOrBlank(" ", "ABC"): false
     * StringUtils.allNullOrBlank(" ABC", "ABC"): false
     * </pre>
     */
    public static boolean allNullOrBlank(String... strs) {
        if (strs == null || strs.length == 0) return true;

        return Stream.of(strs).allMatch(StringUtils::isNullOrBlank);
    }

    /**
     * `기준 문자열`과 일치하는 문자열이 하나라도 있는지 확인한다.
     *
     * <pre>
     * StringUtils.anyEquals(null, null): false
     * StringUtils.anyEquals("", null): false
     * StringUtils.anyEquals(null, ""): false
     * StringUtils.anyEquals("", null, ""): true
     * StringUtils.anyEquals("ABC", "abc"): false
     * StringUtils.anyEquals("ABC", "abc", "ABC"): true
     * </pre>
     */
    public static boolean anyEquals(String s1, String... strs) {
        if (s1 == null || strs == null || strs.length == 0) return false;

        for (String str : strs) {
            if (s1.equals(str)) return true;
        }

        return false;
    }

    public static String padStart(int len, String origin) {
        if (origin.length() >= len) return origin;

        return new StringBuilder().append(repeat(WHITE_SPACE, len))
                .append(origin)
                .toString();
    }

    public static String padStart(int len, String origin, String appendix) {
        if (origin.length() >= len) return origin;

        return new StringBuilder().append(repeat(appendix, len))
                .append(origin)
                .toString();
    }

    public static String padEnd(int len, String origin) {
        if (origin.length() >= len) return origin;

        return new StringBuilder().append(origin)
                .append(repeat(WHITE_SPACE, len))
                .toString();
    }

    public static String padEnd(int len, String origin, String appendix) {
        if (origin.length() >= len) return origin;

        return new StringBuilder().append(origin)
                .append(repeat(appendix, len))
                .toString();
    }

    /**
     * Count of.
     *
     * @param str the str
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
     * <p>
     * Reverses a String as per.
     *
     * @param str the String to reverse, may be null
     * @return the reversed String, <code>null</code> if null String input
     *         {@link StringBuffer#reverse()}.
     *         </p>
     *         <p>
     *         <A code>null</code> String returns <code>null</code>.
     *         </p>
     *
     *         <pre>
     * StringUtils.reverse(null)  		   = null
     * StringUtils.reverse(&quot;&quot;)    = &quot;&quot;
     * StringUtils.reverse(&quot;bat&quot;) = &quot;tab&quot;
     *         </pre>
     */
    public static String reverse(String str) {
        if (str == null) return null;
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 가장 마지막에 일치하는 문구를 원하는 문구로 대체한다.
     *
     * <pre>
     * StringUtils.replaceLast("ABC%DEF%GHI", "%", "-"): "ABC%DEF-GHI"
     * StringUtils.replaceLast("ABC%DEF%GHI", "%", "\\$"): "ABC%DEF$GHI"
     * </pre>
     */
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    /**
     * 3자리 숫자마다 ,(comma)로 구분한 문자열을 반환한다.
     *
     * <pre>
     * StringUtils.formatComma(""): "0"
     * StringUtils.formatComma("-100"): "-100"
     * StringUtils.formatComma("100000"): "100,000"
     * </pre>
     */
    public static String formatComma(String amount) {
        return new DecimalFormat("###,###,###,###,###,###,###").format(amount);
    }

    /**
     * 3자리 숫자마다 ,(comma)로 구분한 문자열을 반환한다.
     *
     * <pre>
     * StringUtils.formatComma(0): "0"
     * StringUtils.formatComma(-100): "-100"
     * StringUtils.formatComma(100000): "100,000"
     * </pre>
     */
    public static String formatComma(long amount) {
        return new DecimalFormat("###,###,###,###,###,###,###").format(amount);
    }

    /**
     * `해당 문자열`을 원하는 만큼 반복하여 복제한다.
     *
     * <pre>
     * StringUtils.repeat(null, 2): "nullnull"
     * StringUtils.repeat("", 5): ""
     * StringUtils.repeat("abc", 3): "abcabcabc"
     * </pre>
     */
    public static String repeat(String str, int cnt) {
        return String.join("", Collections.nCopies(cnt, str));
    }

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
