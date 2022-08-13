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

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * Generator of randomized {@link String} by the specific language.
 *
 * @see <a href="https://www.oracle.com/java/technologies/javase/jdk8-jre8-suported-locales.html">
 * Java Supported Locales</a>
 */
public class RandomString {

    protected static final Map<String, Set<Entry<Character, Character>>> LANGUAGE_UNICODE_MAP;

    static {
        Locale israelLocale = new Locale("iw", "IL");
        Locale indiaLocale = new Locale("hi", "IN");

        Map<String, Set<Entry<Character, Character>>> map = new HashMap<>();
        map.put(Locale.ENGLISH.getLanguage(), new HashSet<>(Arrays.asList(
                new SimpleEntry<>('A', 'Z'),
                new SimpleEntry<>('a', 'z')
        )));
        map.put(Locale.CHINESE.getLanguage(), new HashSet<>(Arrays.asList(
                new SimpleEntry<>('\u2E80', '\u2FD5'),
                new SimpleEntry<>('\u3190', '\u319f'),
                new SimpleEntry<>('\u3400', '\u4DBF'),
                new SimpleEntry<>('\u4E00', '\u9FCC'),
                new SimpleEntry<>('\uF900', '\uFAAD')
        )));
        map.put(Locale.JAPANESE.getLanguage(), new HashSet<>(Arrays.asList(
                new SimpleEntry<>('\u3041', '\u3096'),
                new SimpleEntry<>('\u30A0', '\u30FF'),
                new SimpleEntry<>('\u3400', '\u4DB5'),
                new SimpleEntry<>('\u4E00', '\u9FCB'),
                new SimpleEntry<>('\uF900', '\uFA6A')
        )));
        map.put(Locale.KOREAN.getLanguage(), Collections.singleton(new SimpleEntry<>('\uAC00', '\uD7A3')));
        map.put(indiaLocale.getLanguage(), new HashSet<>(Arrays.asList(
                new SimpleEntry<>('\u0900', '\u097F'),
                new SimpleEntry<>('\u0980', '\u09FF'),
                new SimpleEntry<>('\u0A00', '\u0A7F'),
                new SimpleEntry<>('\u0A80', '\u0AFF'),
                new SimpleEntry<>('\u0B00', '\u0B7F'),
                new SimpleEntry<>('\u0B80', '\u0BFF'),
                new SimpleEntry<>('\u0C00', '\u0C7F'),
                new SimpleEntry<>('\u0C80', '\u0CFF'),
                new SimpleEntry<>('\u0D00', '\u0D7F')
        )));

        LANGUAGE_UNICODE_MAP = Collections.unmodifiableMap(map);
    }

    private final Random random;

    private final char[] symbols;

    public RandomString() {
        this(new Random());
    }

    public RandomString(Random random) {
        this(random, Locale.ENGLISH);
    }

    /**
     * Creates with custom {@link Random} and characters of the specific language.
     *
     * <h2>Chinese</h2>
     *
     * <dl>
     *     <dt><b>U+2E80 ~ U+2FD5</b></dt>
     *     <dd>CJK Radicals Supplement is a Unicode block containing alternative, often positional,
     *     forms of the Kangxi radicals. They are used headers in dictionary indices and
     *     other CJK ideograph collections organized by radical-stroke.</dd>
     *
     *     <dt><b>U+3190 ~ U+319F</b></dt>
     *     <dd>Kanbun is a Unicode block containing annotation characters used in Japanese copies
     *     of classical Chinese texts, to indicate reading order.</dd>
     *
     *     <dt><b>U+3400 ~ U+4DBF</b></dt>
     *     <dd>CJK Unified Ideographs Extension-A is a Unicode block containing rare Han ideographs.</dd>
     *
     *     <dt><b>U+4E00 ~ U+9FCC</b></dt>
     *     <dd>CJK Unified Ideographs is a Unicode block containing the most common CJK ideographs
     *     used in modern Chinese and Japanese.</dd>
     *
     *     <dt><b>U+F900 ~ U+FAAD</b></dt>
     *     <dd>CJK Compatibility Ideographs is a Unicode block created to contain Han characters
     *     that were encoded in multiple locations in other established character encodings,
     *     in addition to their CJK Unified Ideographs assignments, in order to retain round-trip
     *     compatibility between Unicode and those encodings.</dd>
     * </dl>
     *
     * <hr>
     *
     * <h2>Hindi</h2>
     *
     * <dl>
     *     <dt><b>U+0900 ~ U+097F</b></dt>
     *     <dd>Devanagari</dd>
     *
     *     <dt><b>U+0980 ~ U+09FF</b></dt>
     *     <dd>Bengali</dd>
     *
     *     <dt><b>U+0A00 ~ U+0A7F</b></dt>
     *     <dd>Gurmukhi</dd>
     *
     *     <dt><b>U+0A80 ~ U+0AFF</b></dt>
     *     <dd>Gujarati</dd>
     *
     *     <dt><b>U+0B00 ~ U+0B7F</b></dt>
     *     <dd>Odia</dd>
     *
     *     <dt><b>U+0B80 ~ U+0BFF</b></dt>
     *     <dd>Tamil</dd>
     *
     *     <dt><b>U+0C00 ~ U+0C7F</b></dt>
     *     <dd>Telugu</dd>
     *
     *     <dt><b>U+0C80 ~ U+0CFF</b></dt>
     *     <dd>Kannada</dd>
     *
     *     <dt><b>U+0D00 ~ U+0D7F</b></dt>
     *     <dd>Malayalam</dd>
     * </dl>
     *
     * <hr>
     *
     * <h2>Japanese</h2>
     *
     * <dl>
     *     <dt><b>U+3041 ~ U+3096</b></dt>
     *     <dd>Hiragana</dd>
     *
     *     <dt><b>U+30A0 ~ U+30FF</b></dt>
     *     <dd>Katakana</dd>
     *
     *     <dt><b>U+3400 ~ U+4DB5</b></dt>
     *     <dt><b>U+4E00 ~ U+9FCB</b></dt>
     *     <dt><b>U+F900 ~ U+FA6A</b></dt>
     *     <dd>Kanij, including those used in Chinese.</dd>
     * </dl>
     *
     * @param random instance of custom random
     * @param locale language to use in
     */
    public RandomString(Random random, Locale locale) {
        this.random = random;

        // Resolves symbols by locale.
        StringBuilder sb = new StringBuilder();
        Set<Entry<Character, Character>> entries = LANGUAGE_UNICODE_MAP.getOrDefault(
                locale.getLanguage(), LANGUAGE_UNICODE_MAP.get(Locale.ENGLISH.getLanguage()));

        for (Entry<Character, Character> entry : entries) {
            for (char c = entry.getKey(); c <= entry.getValue(); c++) {
                sb.append(c);
            }
        }

        this.symbols = sb.toString().toCharArray();
    }

    // -------------------------------------------------------------------------------------------------

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

}
