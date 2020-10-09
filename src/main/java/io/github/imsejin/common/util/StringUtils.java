package io.github.imsejin.common.util;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String ifNullOrEmpty(String str, @Nonnull Supplier<String> supplier) {
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
    public static String ifNullOrBlank(String str, @Nonnull Supplier<String> supplier) {
        return isNullOrBlank(str) ? supplier.get() : str;
    }

    /**
     * 공백 문자열이 하나라도 있는지 확인한다.
     *
     * <pre>{@code
     *     anyNullOrBlank([null, " "]);       // true
     *     anyNullOrBlank([null, "ABC"]);     // true
     *     anyNullOrBlank(["ABC", ""]);       // true
     *     anyNullOrBlank([" ", "ABC"]);      // true
     *     anyNullOrBlank([" ABC", "ABC"]);   // false
     * }</pre>
     *
     * @param strs strings
     * @return whether any strings are null or blank
     */
    public static boolean anyNullOrBlank(Collection<String> strs) {
        if (CollectionUtils.isNullOrEmpty(strs)) return true;
        return strs.stream().anyMatch(StringUtils::isNullOrBlank);
    }

    /**
     * 모두 공백 문자열인지 확인한다.
     *
     * <pre>{@code
     *     allNullOrBlank([null, " "]);       // true
     *     allNullOrBlank([null, "ABC"]);     // false
     *     allNullOrBlank(["ABC", ""]);       // false
     *     allNullOrBlank([" ", "ABC"]);      // false
     *     allNullOrBlank([" ABC", "ABC"]);   // false
     * }</pre>
     *
     * @param strs strings
     * @return whether all strings are null or blank
     */
    public static boolean allNullOrBlank(Collection<String> strs) {
        if (CollectionUtils.isNullOrEmpty(strs)) return true;
        return strs.stream().allMatch(StringUtils::isNullOrBlank);
    }

    /**
     * `기준 문자열`과 일치하는 문자열이 하나라도 있는지 확인한다.
     *
     * <pre>{@code
     *     anyEquals(null, [null]);           // false
     *     anyEquals("", [null]);             // false
     *     anyEquals(null, [""]);             // false
     *     anyEquals("", [null, ""]);         // true
     *     anyEquals("ABC", ["abc"]);         // false
     *     anyEquals("ABC", ["abc", "ABC"]);  // true
     * }</pre>
     *
     * @param criterion criterion string
     * @param strs      strings
     * @return whether any strings are equal to criterion string
     */
    public static boolean anyEquals(String criterion, Collection<String> strs) {
        if (criterion == null || CollectionUtils.isNullOrEmpty(strs)) return false;
        return strs.stream().anyMatch(criterion::equals);
    }

    /**
     * Fills the start of string with whitespace.
     *
     * <pre>{@code
     *     padStart(8, "0304"); // "    0304"
     *     padStart(4, "0304"); // "0304"
     * }</pre>
     *
     * @param len    targeted string length;
     *               If it is less than the length of origin string,
     *               this returns it as it is.
     * @param origin string that needs to be padded
     * @return padded string
     * @see #repeat(String, int)
     */
    public static String padStart(int len, @Nonnull String origin) {
        return padStart(len, origin, String.valueOf(WHITE_SPACE));
    }

    /**
     * Fills the start of string with another string.
     *
     * <pre>{@code
     *     padStart(8, "0304", "0"); // "00000304"
     *     padStart(4, "0304", "0"); // "0304"
     * }</pre>
     *
     * @param len      targeted string length;
     *                 If it is less than the length of origin string,
     *                 this returns it as it is.
     * @param origin   string that needs to be padded
     * @param appendix string to be appended to origin string
     * @return padded string
     * @see #repeat(String, int)
     */
    public static String padStart(int len, @Nonnull String origin, String appendix) {
        int originLen = origin.length();

        if (originLen >= len) return origin;
        return repeat(appendix, len - originLen) + origin;
    }

    /**
     * Fills the end of string with whitespace.
     *
     * <pre>{@code
     *     padEnd(8, "0304"); // "0304    "
     *     padEnd(4, "0304"); // "0304"
     * }</pre>
     *
     * @param len    targeted string length;
     *               If it is less than the length of origin string,
     *               this returns it as it is.
     * @param origin string that needs to be padded
     * @return padded string
     * @see #repeat(String, int)
     */
    public static String padEnd(int len, @Nonnull String origin) {
        return padEnd(len, origin, String.valueOf(WHITE_SPACE));
    }

    /**
     * Fills the end of string with another string.
     *
     * <pre>{@code
     *     padEnd(8, "0304", "0"); // "03040000"
     *     padEnd(4, "0304", "0"); // "0304"
     * }</pre>
     *
     * @param len      targeted string length;
     *                 If it is less than the length of origin string,
     *                 this returns it as it is.
     * @param origin   string that needs to be padded
     * @param appendix string to be appended to origin string
     * @return padded string
     * @see #repeat(String, int)
     */
    public static String padEnd(int len, @Nonnull String origin, String appendix) {
        int originLen = origin.length();

        if (originLen >= len) return origin;
        return origin + repeat(appendix, len - originLen);
    }

    /**
     * Returns the number of strings to be found at origin string.
     *
     * <p> If you input empty string as keyword,
     * this returns length of the origin string.
     *
     * @param origin  origin string
     * @param keyword string to be found
     * @return count of inclusions
     */
    public static int countOf(@Nonnull String origin, @Nonnull String keyword) {
        // If don't, will go into infinite loop.
        if (keyword.isEmpty()) return origin.length();

        int keywordLen = keyword.length();
        int count = 0;

        for (int i = origin.indexOf(keyword); i >= 0; i = origin.indexOf(keyword, i + keywordLen)) {
            count++;
        }

        return count;
    }

    /**
     * Reverses all the characters in a string.
     *
     * @param str string to be reversed
     * @return reversed string
     * @see StringBuilder#reverse
     */
    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
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
    public static String replaceLast(@Nonnull String text, String regex, String replacement) {
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
     * Replicates a string as many times as you want.
     *
     * <pre>{@code
     *     repeat(null, 2);     // nullnull
     *     repeat("", 5);       // \u0000
     *     repeat("abc", 3);    // abcabcabc
     * }</pre>
     *
     * <table>
     *     <caption>{@code <String concatenation comparison>}</caption>
     *     <tr>
     *         <td></td>
     *         <td>{@link String#join(CharSequence, Iterable)} + {@link Collections#nCopies(int, Object)}</td>
     *         <td>{@link StringBuilder#append(String)} + {@link StringBuilder#toString()}</td>
     *         <td>{@link StringBuffer#append(String)} + {@link StringBuffer#toString()}</td>
     *     </tr>
     *     <tr>
     *         <td>1 million</td>
     *         <td>1359.5863 ms</td>
     *         <td>775.0314 ms</td>
     *         <td>888.8059 ms</td>
     *     </tr>
     *     <tr>
     *         <td>10 million</td>
     *         <td>4232.0463 ms</td>
     *         <td>5798.3983 ms</td>
     *         <td>6404.1909 ms</td>
     *     </tr>
     *     <tr>
     *         <td>1 billion</td>
     *         <td>31917.2627 ms</td>
     *         <td>60978.4536 ms</td>
     *         <td>68058.4082 ms</td>
     *     </tr>
     * </table>
     *
     * @param str string to be repeated
     * @param cnt repetition count
     * @return repeated string
     */
    public static String repeat(String str, int cnt) {
        return String.join("", Collections.nCopies(cnt, str));
    }

    /**
     * Replicates a character as many times as you want.
     *
     * <pre>{@code
     *     repeat(' ', 3);    // "   "
     *     repeat('a', 3);    // aaa
     * }</pre>
     *
     * <table>
     *     <caption>{@code <String concatenation comparison>}</caption>
     *     <tr>
     *         <td></td>
     *         <td>{@link String#join(CharSequence, Iterable)} + {@link Collections#nCopies(int, Object)}</td>
     *         <td>{@link StringBuilder#append(char)} + {@link StringBuilder#toString()}</td>
     *         <td>{@link StringBuffer#append(char)} + {@link StringBuffer#toString()}</td>
     *     </tr>
     *     <tr>
     *         <td>1 million</td>
     *         <td>1359.5863 ms</td>
     *         <td>775.0314 ms</td>
     *         <td>888.8059 ms</td>
     *     </tr>
     *     <tr>
     *         <td>10 million</td>
     *         <td>4232.0463 ms</td>
     *         <td>5798.3983 ms</td>
     *         <td>6404.1909 ms</td>
     *     </tr>
     *     <tr>
     *         <td>1 billion</td>
     *         <td>31917.2627 ms</td>
     *         <td>60978.4536 ms</td>
     *         <td>68058.4082 ms</td>
     *     </tr>
     * </table>
     *
     * @param c   character to be repeated
     * @param cnt repetition count
     * @return repeated string
     */
    public static String repeat(char c, int cnt) {
        return String.join("", Collections.nCopies(cnt, String.valueOf(c)));
    }

    public static String match(@Nonnull String regex, @Nonnull String src) {
        return match(regex, src, 0);
    }

    public static String match(@Nonnull String regex, @Nonnull String src, int groupNo) {
        Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(src);

        String matched = null;
        while (matcher.find()) {
            matched = matcher.group(groupNo);
        }

        return matched;
    }

    /**
     * Checks if criterial string contains other strings.
     *
     * @param container  criterial string
     * @param containees list of strings to compare
     * @return whether criterial string contains other strings
     */
    public static boolean anyContains(@Nonnull String container, @Nonnull Collection<String> containees) {
        return containees.stream().anyMatch(container::contains);
    }

    /**
     * Removes last characters in the string.
     *
     * @param str string
     * @return chopped string
     */
    public static String chop(@Nonnull String str) {
        return str.isEmpty() ? str : str.substring(0, str.length() - 1);
    }

}
