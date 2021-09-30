/*
 * Copyright 2020 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.util;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.*;
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

    @ExcludeFromGeneratedJacocoReport
    private StringUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Checks whether the string is null or empty.
     *
     * <pre><code>
     *     isNullOrEmpty(null);     // true
     *     isNullOrEmpty("");       // true
     *     isNullOrEmpty("abc");    // false
     * </code></pre>
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
     * <pre><code>
     *     ifNullOrEmpty(null, "(empty)");      // (empty)
     *     ifNullOrEmpty("", "(empty)");        // (empty)
     *     ifNullOrEmpty(" ", "(empty)");       // \u0020
     * </code></pre>
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
     * <pre><code>
     *     isNullOrBlank(null);     // true
     *     isNullOrBlank("");       // true
     *     isNullOrBlank(" ");      // true
     *     isNullOrBlank(" ABC");   // false
     * </code></pre>
     *
     * @param str string
     * @return whether the string is null or blank
     */
    public static boolean isNullOrBlank(String str) {
        if (isNullOrEmpty(str)) return true;

        for (char c : str.toCharArray()) {
            if (!Character.isWhitespace(c)) return false;
        }

        return true;
    }

    /**
     * If the string is null or blank, this returns default string.
     * <br>
     * If not, this returns original string.
     *
     * <pre><code>
     *     ifNullOrBlank(null, "(empty)");      // (empty)
     *     ifNullOrBlank("", "(empty)");        // (empty)
     *     ifNullOrBlank(" ", "(empty)");       // (empty)
     *     ifNullOrBlank(" ABC", "(empty)");    //  ABC
     * </code></pre>
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
     * <pre><code>
     *     anyNullOrBlank(null);              // true
     *     anyNullOrBlank([]);                // true
     *     anyNullOrBlank([null, " "]);       // true
     *     anyNullOrBlank([null, "ABC"]);     // true
     *     anyNullOrBlank(["ABC", ""]);       // true
     *     anyNullOrBlank([" ", "ABC"]);      // true
     *     anyNullOrBlank([" ABC", "ABC"]);   // false
     * </code></pre>
     *
     * @param strings strings
     * @return whether any strings are null or blank
     */
    public static boolean anyNullOrBlank(Collection<String> strings) {
        if (CollectionUtils.isNullOrEmpty(strings)) return true;
        return strings.stream().anyMatch(StringUtils::isNullOrBlank);
    }

    /**
     * 모두 공백 문자열인지 확인한다.
     *
     * <pre><code>
     *     allNullOrBlank(null);              // true
     *     allNullOrBlank([]);                // true
     *     allNullOrBlank([null, " "]);       // true
     *     allNullOrBlank([null, "ABC"]);     // false
     *     allNullOrBlank(["ABC", ""]);       // false
     *     allNullOrBlank([" ", "ABC"]);      // false
     *     allNullOrBlank([" ABC", "ABC"]);   // false
     * </code></pre>
     *
     * @param strings strings
     * @return whether all strings are null or blank
     */
    public static boolean allNullOrBlank(Collection<String> strings) {
        if (CollectionUtils.isNullOrEmpty(strings)) return true;
        return strings.stream().allMatch(StringUtils::isNullOrBlank);
    }

    /**
     * `기준 문자열`과 일치하는 문자열이 하나라도 있는지 확인한다.
     *
     * <pre><code>
     *     anyEquals(null, [null]);           // false
     *     anyEquals("", [null]);             // false
     *     anyEquals(null, [""]);             // false
     *     anyEquals("", [null, ""]);         // true
     *     anyEquals("ABC", ["abc"]);         // false
     *     anyEquals("ABC", ["abc", "ABC"]);  // true
     * </code></pre>
     *
     * @param criterion criterion string
     * @param strings   strings
     * @return whether any strings are equal to criterion string
     */
    public static boolean anyEquals(String criterion, Collection<String> strings) {
        if (criterion == null || CollectionUtils.isNullOrEmpty(strings)) return false;
        return strings.stream().anyMatch(criterion::equals);
    }

    /**
     * Checks whether the string is numeric.
     *
     * <pre><code>
     *     isNumeric(null);     // false
     *     isNumeric("");       // false
     *     isNumeric(" ");      // false
     *     isNumeric(" ABC");   // false
     *     isNumeric(" 01");    // false
     *     isNumeric("011");    // true
     * </code></pre>
     *
     * @param str string
     * @return whether the string is numeric
     */
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) return false;

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }

        return true;
    }

    /**
     * Fills the start of string with whitespace.
     *
     * <pre><code>
     *     padStart(8, "0304"); // "    0304"
     *     padStart(4, "0304"); // "0304"
     * </code></pre>
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
     * <pre><code>
     *     padStart(8, "0304", "0"); // "00000304"
     *     padStart(4, "0304", "0"); // "0304"
     * </code></pre>
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
     * <pre><code>
     *     padEnd(8, "0304"); // "0304    "
     *     padEnd(4, "0304"); // "0304"
     * </code></pre>
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
     * <pre><code>
     *     padEnd(8, "0304", "0"); // "03040000"
     *     padEnd(4, "0304", "0"); // "0304"
     * </code></pre>
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
     * <pre><code>
     *     replaceLast("ABC%DEF%GHI", "%", "-");    // ABC%DEF-GHI
     *     replaceLast("ABC%DEF%GHI", "%", "\\$");  // ABC%DEF$GHI
     * </code></pre>
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
     * <pre><code>
     *     formatComma(-100);   // -100
     *     formatComma(0);      // 0
     *     formatComma(100000); // 100,000
     * </code></pre>
     *
     * @param amount amount number
     * @return formatted number with comma
     */
    public static String formatComma(long amount) {
        return NumberFormat.getInstance(Locale.US).format(amount);
    }

    /**
     * 3자리 숫자마다 ,(comma)로 구분한 문자열을 반환한다.
     *
     * <pre><code>
     *     formatComma("-100");   // -100
     *     formatComma("0");      // 0
     *     formatComma("100000"); // 100,000
     * </code></pre>
     *
     * @param amount amount number
     * @return formatted number with comma
     */
    public static String formatComma(String amount) {
        return NumberFormat.getInstance(Locale.US).format(Long.parseLong(amount));
    }

    /**
     * Replicates a string as many times as you want.
     *
     * <pre><code>
     *     repeat(null, 2);     // nullnull
     *     repeat("", 5);       // \u0000
     *     repeat("abc", 3);    // abcabcabc
     * </code></pre>
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
     * <pre><code>
     *     repeat(' ', 3);    // "   "
     *     repeat('a', 3);    // aaa
     * </code></pre>
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

    /**
     * Finds the captured string with regular expression.
     *
     * <pre>{@code
     *    Pattern pattern = Pattern.compile("<.+>(.*)<\/(.+)>");
     *    find("<div>A</div>", pattern, 1); // div
     * }</pre>
     *
     * @param src     source string
     * @param pattern pattern of regular expression
     * @param group   group number you want to get value of
     * @return captured string
     */
    @Nullable
    public static String find(@Nonnull String src, @Nonnull Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(src);

        String result = null;
        while (matcher.find()) {
            result = matcher.group(group);
        }

        return result;
    }

    /**
     * Finds the captured string with regular expression.
     *
     * <pre>{@code
     *    find("<div>A</div>", "<.+>.*<\/(.+)>", 1); // div
     * }</pre>
     *
     * @param src   source string
     * @param regex regular expression
     * @param group group number you want to get value of
     * @return captured string
     */
    @Nullable
    public static String find(@Nonnull String src, @Nonnull String regex, int group) {
        Matcher matcher = Pattern.compile(regex).matcher(src);

        String result = null;
        while (matcher.find()) {
            result = matcher.group(group);
        }

        return result;
    }

    /**
     * Finds the captured strings with regular expression.
     *
     * <pre>{@code
     *     Pattern pattern = Pattern.compile("<.+>(.*)<\/(.+)>", Pattern.MULTILINE);
     *     find("<div>A</div>", pattern, 1, 2); // {1: "A", 2: "div"}
     * }</pre>
     *
     * @param src     source string
     * @param pattern pattern of regular expression
     * @param groups  group numbers you want to get value of
     * @return map whose key is group number and whose value is a captured string.
     */
    public static Map<Integer, String> find(@Nonnull String src, @Nonnull Pattern pattern, int... groups) {
        Matcher matcher = pattern.matcher(src);

        Map<Integer, String> result = new HashMap<>();
        while (matcher.find()) {
            for (int group : groups) {
                result.put(group, matcher.group(group));
            }
        }

        return result;
    }

    /**
     * Finds the captured strings with regular expression.
     *
     * <pre>{@code
     *     find("<div>A</div>", "<.+>(.*)<\/(.+)>", Pattern.MULTILINE, 1, 2); // {1: "A", 2: "div"}
     * }</pre>
     *
     * @param src    source string
     * @param regex  regular expression
     * @param flags  regular flags
     * @param groups group numbers you want to get value of
     * @return map whose key is group number and whose value is a captured string.
     */
    public static Map<Integer, String> find(@Nonnull String src, @Nonnull String regex, int flags, int... groups) {
        Matcher matcher = Pattern.compile(regex, flags).matcher(src);

        Map<Integer, String> result = new HashMap<>();
        while (matcher.find()) {
            for (int group : groups) {
                result.put(group, matcher.group(group));
            }
        }

        return result;
    }

    /**
     * Checks if criterial string contains other strings.
     *
     * @param container    criterial string
     * @param containments list of strings to compare
     * @return whether criterial string contains other strings
     */
    public static boolean anyContains(@Nonnull String container, @Nonnull Collection<String> containments) {
        return containments.stream().anyMatch(container::contains);
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

    public static String getLastString(@Nonnull String str) {
        return str.isEmpty() ? str : String.valueOf(str.charAt(str.length() - 1));
    }

}
