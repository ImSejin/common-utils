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

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

/**
 * String utilities
 */
public final class StringUtils {

    /**
     * Whitespace character.
     */
    private static final char WHITE_SPACE = '\u0020';

    /**
     * Number formatter.
     */
    private static final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

    @ExcludeFromGeneratedJacocoReport
    private StringUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Checks whether the string is null or empty.
     *
     * <pre><code>
     *     isNullOrEmpty(null);  // true
     *     isNullOrEmpty("");    // true
     *     isNullOrEmpty("abc"); // false
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
     *     ifNullOrEmpty(null, "(empty)"); // "(empty)"
     *     ifNullOrEmpty("", "(empty)");   // "(empty)"
     *     ifNullOrEmpty(" ", "(empty)");  // "\u0020"
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
     *     ifNullOrEmpty(null, () -> "(empty)"); // "(empty)"
     *     ifNullOrEmpty("", () -> "(empty)");   // "(empty)"
     *     ifNullOrEmpty(" ", () -> "(empty)");  // "\u0020"
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
     * <pre><code>
     *     isNullOrBlank(null);   // true
     *     isNullOrBlank("");     // true
     *     isNullOrBlank(" ");    // true
     *     isNullOrBlank(" ABC"); // false
     * </code></pre>
     *
     * @param str string
     * @return whether the string is null or blank
     */
    public static boolean isNullOrBlank(String str) {
        if (isNullOrEmpty(str)) {
            return true;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * If the string is null or blank, this returns default string.
     * <br>
     * If not, this returns original string.
     *
     * <pre><code>
     *     ifNullOrBlank(null, "(empty)");   // "(empty)"
     *     ifNullOrBlank("", "(empty)");     // "(empty)"
     *     ifNullOrBlank(" ", "(empty)");    // "(empty)"
     *     ifNullOrBlank(" ABC", "(empty)"); // " ABC"
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
     *     ifNullOrBlank(null, () -> "(empty)");   // "(empty)"
     *     ifNullOrBlank("", () -> "(empty)");     // "(empty)"
     *     ifNullOrBlank(" ", () -> "(empty)");    // "(empty)"
     *     ifNullOrBlank(" ABC", () -> "(empty)"); // " ABC"
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
     * Checks if criterial string is equal to other strings.
     *
     * <pre><code>
     *     anyEquals(null, [null]);          // true
     *     anyEquals("", [null, ""]);        // true
     *     anyEquals("ABC", ["abc", "ABC"]); // true
     *     anyEquals(null, null);            // false
     *     anyEquals(null, []);              // false
     *     anyEquals(null, [""]);            // false
     *     anyEquals("", [null]);            // false
     *     anyEquals("ABC", ["abc"]);        // false
     * </code></pre>
     *
     * @param criterion criterial string
     * @param strings   strings to be compared
     * @return whether any strings are equal to criterion string
     */
    public static boolean anyEquals(@Nullable String criterion, @Nullable Collection<String> strings) {
        if (CollectionUtils.isNullOrEmpty(strings)) {
            return false;
        }

        for (String string : strings) {
            if (Objects.deepEquals(criterion, string)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if criterial string contains other strings.
     *
     * <pre><code>
     *    anyContains("", [null, ""]);            // true
     *    anyContains(" c", ["a", "B", "c"]);     // true
     *    anyContains("Abs", ['ab', "aB", "Ab"]); // true
     *    anyContains(null, null);                // false
     *    anyContains(null, []);                  // false
     *    anyContains(null, [null]);              // false
     *    anyContains(null, ["null"]);            // false
     *    anyContains("", []);                    // false
     * </code></pre>
     *
     * @param criterion criterial string
     * @param strings   strings to be compared
     * @return whether criterial string contains other strings
     */
    public static boolean anyContains(@Nullable String criterion, @Nullable Iterable<String> strings) {
        if (criterion == null || strings == null || strings.spliterator().estimateSize() == 0) {
            return false;
        }

        for (String string : strings) {
            if (string != null && criterion.contains(string)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the string is numeric.
     *
     * <pre><code>
     *     isNumeric("011");  // true
     *     isNumeric(null);   // false
     *     isNumeric("");     // false
     *     isNumeric(" ");    // false
     *     isNumeric(" ABC"); // false
     *     isNumeric(" 01");  // false
     *     isNumeric("-86");  // false
     * </code></pre>
     *
     * @param str string
     * @return whether the string is numeric
     */
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
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
    public static String padStart(int len, String origin) {
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
    public static String padStart(int len, String origin, String appendix) {
        int originLen = origin.length();

        if (originLen >= len) {
            return origin;
        }
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
    public static String padEnd(int len, String origin) {
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
    public static String padEnd(int len, String origin, String appendix) {
        int originLen = origin.length();

        if (originLen >= len) {
            return origin;
        }
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
    public static int countOf(String origin, String keyword) {
        // If don't, will go into infinite loop.
        if (keyword.isEmpty()) {
            return origin.length();
        }

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
     * Replaces the last substring of string that matches the given regular expression with the given replacement.
     *
     * <pre><code>
     *     replaceLast("ABC%DEF%GHI", "%", "-");   // "ABC%DEF-GHI"
     *     replaceLast("ABC%DEF%GHI", "%", "\\$"); // "ABC%DEF$GHI"
     * </code></pre>
     *
     * @param text        text
     * @param regex       regular expression
     * @param replacement replacement string
     * @return string replaced with replacement by last replacer
     */
    public static String replaceLast(String text, @Language("RegExp") String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    /**
     * Returns a string formatted by each three-digits with comma.
     *
     * <pre><code>
     *     formatComma(-100);   // "-100"
     *     formatComma(0);      // "0"
     *     formatComma(84.0);   // "84"
     *     formatComma(1024.5); // "1,024.5"
     * </code></pre>
     *
     * @param amount number
     * @return formatted numeric string with comma
     */
    public static String formatComma(double amount) {
        return formatter.format(amount);
    }

    /**
     * Returns a string formatted by each three-digits with comma.
     *
     * <pre><code>
     *     formatComma("-100");   // "-100"
     *     formatComma("0");      // "0"
     *     formatComma("84.0");   // "84"
     *     formatComma("1024.5"); // "1,024.5"
     * </code></pre>
     *
     * @param amount numeric string
     * @return formatted numeric string with comma
     */
    public static String formatComma(String amount) {
        return formatter.format(Double.parseDouble(amount));
    }

    /**
     * Replicates a string as many times as you want.
     *
     * <pre><code>
     *     repeat(null, 2);  // "nullnull"
     *     repeat("", 5);    // "\u0000"
     *     repeat("abc", 3); // "abcabcabc"
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
     *     repeat(' ', 3); // "   "
     *     repeat('a', 3); // "aaa"
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
        return repeat(String.valueOf(c), cnt);
    }

    /**
     * Finds the captured string with regular expression.
     *
     * <pre>{@code
     *    find("<div>A</div>", "<.+>.*<\/(.+)>", 1); // "div"
     * }</pre>
     *
     * @param src   source string
     * @param regex regular expression
     * @param group group number you want to get value of
     * @return captured string
     */
    @Nullable
    public static String find(String src, @Language("RegExp") String regex, int group) {
        return find(src, Pattern.compile(regex), group);
    }

    /**
     * Finds the captured string with regular expression.
     *
     * <pre>{@code
     *    Pattern pattern = Pattern.compile("<.+>(.*)<\/(.+)>");
     *    find("<div>A</div>", pattern, 1); // "div"
     * }</pre>
     *
     * @param src     source string
     * @param pattern pattern of regular expression
     * @param group   group number you want to get value of
     * @return captured string
     */
    @Nullable
    public static String find(String src, Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(src);

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
     *     find("<div>A</div>", "<.+>(.*)<\/(.+)>", Pattern.MULTILINE, 1, 2); // {1: "A", 2: "div"}
     * }</pre>
     *
     * @param src    source string
     * @param regex  regular expression
     * @param flags  regular flags
     * @param groups group numbers you want to get value of
     * @return map whose key is group number and whose value is a captured string
     */
    public static Map<Integer, String> find(String src, @Language("RegExp") String regex, int flags, int... groups) {
        return find(src, Pattern.compile(regex, flags), groups);
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
     * @return map whose key is group number and whose value is a captured string
     */
    public static Map<Integer, String> find(String src, Pattern pattern, int... groups) {
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
     * Removes the last characters in the string.
     *
     * @param str string
     * @return chopped string
     */
    public static String chop(String str) {
        return str.isEmpty() ? str : str.substring(0, str.length() - 1);
    }

    /**
     * Returns the last character from string
     *
     * @param str string
     * @return the last character
     */
    public static String getLastString(String str) {
        return str.isEmpty() ? str : String.valueOf(str.charAt(str.length() - 1));
    }

    /**
     * Returns the ordinal index of the string.
     *
     * <pre>{@code
     *     // Positive ordinal
     *     ordinalIndexOf("1,2,3,4", ',', 1);           // 1
     *     ordinalIndexOf("alpha|beta|gamma", '|', 2);  // 10
     *     ordinalIndexOf("A/B//D///", '/', 5);         // 7
     *     ordinalIndexOf("15.768", '.', 1);            // 2
     *     ordinalIndexOf("15.768", '.', 3);            // -1
     *     ordinalIndexOf("a,b,c", ';', 2);             // -1
     *
     *     // Zero ordinal
     *     ordinalIndexOf("x-y-z", '-', 0);             // -1
     *
     *     // Negative ordinal
     *     ordinalIndexOf("1,2,3,4", ',', -1);          // 5
     *     ordinalIndexOf("alpha|beta|gamma", '|', -2); // 5
     *     ordinalIndexOf("A/B//D///", '/', -5);        // 3
     *     ordinalIndexOf("15.768", '.', -1);           // 2
     *     ordinalIndexOf("15.768", '.', -3);           // -1
     *     ordinalIndexOf("a,b,c", ';', -2);            // -1
     * }</pre>
     *
     * @param str     string
     * @param ch      a character
     * @param ordinal n-th character to find
     * @return the n-th index of the string, or -1 if no match
     */
    public static int ordinalIndexOf(String str, char ch, int ordinal) {
        // When ordinal is zero, regard as not finding.
        if (ordinal == 0) {
            return -1;
        }

        int index;

        if (ordinal > 0) {
            // When ordinal is positive, find the character from the beginning.
            index = str.indexOf(ch);
            if (index == -1) {
                return -1;
            }

            for (int i = 1; i < ordinal && index != -1; i++) {
                index = str.indexOf(ch, index + 1);
            }

        } else {
            // When ordinal is negative, find the character from the end.
            index = str.lastIndexOf(ch);
            if (index == -1) {
                return -1;
            }

            for (int i = -1; i > ordinal && index != -1; i--) {
                index = str.lastIndexOf(ch, index - 1);
            }
        }

        return index;
    }

    /**
     * Returns index of character as current closing bracket.
     *
     * <pre>
     *     String s0 = "{name: 'jeremy', age: 16}";
     *     indexOfCurrentClosingBracket(s0, 0, '{', '}');  // 24
     *
     *     String s1 = "[0, [1, 2], 3]";
     *     indexOfCurrentClosingBracket(s1, 0, '[', ']');  // 13
     *     indexOfCurrentClosingBracket(s1, 12, '[', ']'); // 13
     *     indexOfCurrentClosingBracket(s1, 4, '[', ']');  // 9
     *     indexOfCurrentClosingBracket(s1, 7, '[', ']');  // 9
     * </pre>
     *
     * @param str    string
     * @param pos    index of character within the current bracket
     * @param opener character of opening bracket
     * @param closer character of closing bracket
     * @return index of the current closing bracket
     */
    public static int indexOfCurrentClosingBracket(@Nullable String str, int pos, char opener, char closer) {
        if (isNullOrEmpty(str)) {
            return -1;
        }

        // Finds the opening bracket in the current context.
        char ch = str.charAt(pos);
        if (ch != opener) {
            // Prevents this variable from increasing when start character is closer.
            int depth = ch == closer ? 0 : 1;

            for (int i = pos; i >= 0; i--) {
                char c = str.charAt(i);

                if (c == closer) {
                    depth++;
                }
                if (c == opener) {
                    depth--;
                    if (depth == 0) {
                        pos = i;
                        ch = str.charAt(pos);
                        break;
                    }
                }
            }

            // When not found opening bracket in whole characters.
            if (ch != opener) {
                return -1;
            }
        }

        // Since the opener is found, this variable will increase by 1 immediately.
        int depth = 0;

        for (int i = pos; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == opener) {
                depth++;
            }
            if (c == closer) {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }

        // When not found the current closing bracket.
        return -1;
    }

}
