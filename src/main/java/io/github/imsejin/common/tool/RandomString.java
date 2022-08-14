/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.tool;

import io.github.imsejin.common.assertion.Asserts;
import org.jetbrains.annotations.VisibleForTesting;

import java.lang.Character.UnicodeScript;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * Generator of randomized {@link String} by the specific language.
 *
 * @see <a href="https://www.oracle.com/java/technologies/javase/jdk8-jre8-suported-locales.html">
 * Java Supported Locales</a>
 */
public class RandomString {

    private static final Map<String, List<String>> LANGUAGE_UNICODE_POINT_MAP;

    static {
        Map<UnicodeScript, List<Integer>> unicodeMap = IntStream.rangeClosed(Character.MIN_VALUE, Character.MAX_VALUE)
                .boxed().collect(groupingBy(UnicodeScript::of));
        Map<String, List<String>> languageUnicodeRangeMap = new HashMap<>();

        // Arabic: U+0600..U+06FF
        languageUnicodeRangeMap.put("ar", Collections.singletonList("1536-1791"));

        // Chinese: U+4E00..U+9FCC (CJK Unified Ideographs)
        languageUnicodeRangeMap.put(Locale.CHINESE.getLanguage(), Collections.singletonList("19968-40908"));

        // English: U+0041..U+005A, U+0061..U+007A
        languageUnicodeRangeMap.put(Locale.ENGLISH.getLanguage(), Arrays.asList("65-90", "97-122"));

        // Hebrew: U+0590..U+05FF
        languageUnicodeRangeMap.put("iw", Collections.singletonList("1424-1535"));

        // Hindi: U+0900..U+0D7F
        languageUnicodeRangeMap.put("hi", Collections.singletonList("2304-3455"));

        // Japanese: U+3041..U+3096, U+30A1..U+30FA
        languageUnicodeRangeMap.put(Locale.JAPANESE.getLanguage(), Arrays.asList("12353-12438", "12449-12538"));

        // Korean: U+AC00..U+D7A3
        languageUnicodeRangeMap.put(Locale.KOREAN.getLanguage(), Collections.singletonList("44032-55203"));

        LANGUAGE_UNICODE_POINT_MAP = languageUnicodeRangeMap.entrySet().stream()
                .map(e -> new SimpleEntry<>(e.getKey(), Collections.unmodifiableList(e.getValue())))
                .collect(collectingAndThen(toMap(Entry::getKey, Entry::getValue), Collections::unmodifiableMap));
    }

    private final Random random;

    private final char[] symbols;

    /**
     * Creates with default {@link Random} and characters of {@link Locale#ENGLISH}.
     */
    public RandomString() {
        this(new Random());
    }

    /**
     * Creates with custom {@link Random} and characters of {@link Locale#ENGLISH}.
     *
     * @param random instance of custom random
     */
    public RandomString(Random random) {
        this(random, Locale.ENGLISH);
    }

    /**
     * Creates with custom {@link Random} and characters of the specific language.
     *
     * <h2>Chinese</h2>
     *
     * <dl>
     *     <dt><b>U+4E00..U+9FCC</b></dt>
     *     <dd>CJK Unified Ideographs is a Unicode block containing the most common CJK ideographs
     *     used in modern Chinese and Japanese.</dd>
     * </dl>
     *
     * <hr>
     *
     * <h2>Hindi</h2>
     *
     * <dl>
     *     <dt><b>U+0900..U+097F</b></dt>
     *     <dd>Devanagari</dd>
     *
     *     <dt><b>U+0980..U+09FF</b></dt>
     *     <dd>Bengali</dd>
     *
     *     <dt><b>U+0A00..U+0A7F</b></dt>
     *     <dd>Gurmukhi</dd>
     *
     *     <dt><b>U+0A80..U+0AFF</b></dt>
     *     <dd>Gujarati</dd>
     *
     *     <dt><b>U+0B00..U+0B7F</b></dt>
     *     <dd>Odia</dd>
     *
     *     <dt><b>U+0B80..U+0BFF</b></dt>
     *     <dd>Tamil</dd>
     *
     *     <dt><b>U+0C00..U+0C7F</b></dt>
     *     <dd>Telugu</dd>
     *
     *     <dt><b>U+0C80..U+0CFF</b></dt>
     *     <dd>Kannada</dd>
     *
     *     <dt><b>U+0D00..U+0D7F</b></dt>
     *     <dd>Malayalam</dd>
     * </dl>
     *
     * <hr>
     *
     * <h2>Japanese</h2>
     *
     * <dl>
     *     <dt><b>U+3041..U+3096</b></dt>
     *     <dd>Hiragana</dd>
     *
     *     <dt><b>U+30A1..U+30FA</b></dt>
     *     <dd>Katakana</dd>
     * </dl>
     *
     * @param random instance of custom random
     * @param locale language to use in
     */
    public RandomString(Random random, Locale locale) {
        this.random = random;

        // Resolves symbols by locale.
        StringBuilder sb = new StringBuilder();

        List<String> unicodePointRanges = LANGUAGE_UNICODE_POINT_MAP.getOrDefault(locale.getLanguage(),
                LANGUAGE_UNICODE_POINT_MAP.get(Locale.ENGLISH.getLanguage()));
        for (String range : unicodePointRanges) {
            String[] codePoints = range.split("-");

            if (codePoints.length == 1) {
                int codePoint = Integer.parseInt(codePoints[0]);
                sb.append((char) codePoint);
            } else {
                int start = Integer.parseInt(codePoints[0]);
                int end = Integer.parseInt(codePoints[1]);

                for (int codePoint = start; codePoint <= end; codePoint++) {
                    sb.append((char) codePoint);
                }
            }
        }

        this.symbols = sb.toString().toCharArray();
    }

    /**
     * Returns a random string of n characters.
     *
     * <p> You can get a randomized string with the specific language and
     * its length depends on parameter.
     *
     * @param length length of randomized string
     * @return random string
     */
    public String nextString(int length) {
        Asserts.that(length)
                .as("The length of random string must be positive, but it isn't: {0}", length)
                .isPositive();

        char[] chars = new char[length];
        for (int i = 0; i < chars.length; i++) {
            int index = this.random.nextInt(this.symbols.length);
            chars[i] = this.symbols[index];
        }

        return new String(chars);
    }

    // -------------------------------------------------------------------------------------------------

    @VisibleForTesting
    static List<String> convertAsRanges(List<Integer> integers) {
        List<String> ranges = new ArrayList<>();

        List<Integer> prevList = new ArrayList<>();
        for (Integer curr : integers) {
            if (prevList.isEmpty()) {
                prevList.add(curr);
                continue;
            }

            Integer prev = prevList.get(prevList.size() - 1);
            if (prev + 1 == curr) {
                prevList.add(curr);
                continue;
            } else {
                String range;
                if (prevList.size() == 1) {
                    range = String.valueOf(prevList.get(0));
                } else {
                    range = prevList.get(0) + "-" + prevList.get(prevList.size() - 1);
                }

                ranges.add(range);
                prevList = new ArrayList<>();
            }

            prevList.add(curr);
        }

        if (!prevList.isEmpty()) {
            String range;
            if (prevList.size() == 1) {
                range = String.valueOf(prevList.get(0));
            } else {
                range = prevList.get(0) + "-" + prevList.get(prevList.size() - 1);
            }

            ranges.add(range);
        }

        return Collections.unmodifiableList(ranges);
    }

}
