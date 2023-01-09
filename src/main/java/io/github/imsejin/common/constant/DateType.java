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

package io.github.imsejin.common.constant;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import io.github.imsejin.common.assertion.Asserts;

import static java.util.stream.Collectors.*;

/**
 * Datetime patterns for {@link DateTimeFormatter#ofPattern(String)}.
 */
public enum DateType {

    /**
     * Year
     *
     * <pre><code>
     *     yyyy
     * </code></pre>
     */
    YEAR("yyyy"),

    /**
     * Month
     *
     * <pre><code>
     *     MM
     * </code></pre>
     */
    MONTH("MM"),

    /**
     * Day
     *
     * <pre><code>
     *     dd
     * </code></pre>
     */
    DAY("dd"),

    /**
     * Hour
     *
     * <pre><code>
     *     HH
     * </code></pre>
     */
    HOUR("HH"),

    /**
     * Minute
     *
     * <pre><code>
     *     mm
     * </code></pre>
     */
    MINUTE("mm"),

    /**
     * Second
     *
     * <pre><code>
     *     ss
     * </code></pre>
     */
    SECOND("ss"),

    /**
     * Millisecond
     *
     * <pre><code>
     *     SSS
     * </code></pre>
     */
    MILLISECOND("SSS"),

    /**
     * Year and month
     *
     * <pre><code>
     *     yyyyMM
     * </code></pre>
     */
    YEAR_MONTH(YEAR.pattern + MONTH.pattern),

    /**
     * Year, month and day
     *
     * <pre><code>
     *     yyyyMMdd
     * </code></pre>
     */
    DATE(YEAR.pattern + MONTH.pattern + DAY.pattern),

    /**
     * Hour, minute and second
     *
     * <pre><code>
     *     HHmmss
     * </code></pre>
     */
    TIME(HOUR.pattern + MINUTE.pattern + SECOND.pattern),

    /**
     * Hour, minute, second and millisecond
     *
     * <pre><code>
     *     HHmmssSSS
     * </code></pre>
     */
    HOUR_2_MILSEC(TIME.pattern + MILLISECOND.pattern),

    /**
     * Year, month, day, hour, minute and second
     *
     * <pre><code>
     *     yyyyMMddHHmmss
     * </code></pre>
     */
    DATE_TIME(DATE.pattern + TIME.pattern),

    /**
     * Year, month, day, hour, minute, second and millisecond
     *
     * <pre><code>
     *     yyyyMMddHHmmssSSS
     * </code></pre>
     */
    ALL(DATE.pattern + HOUR_2_MILSEC.pattern),

    /**
     * Formatted year, month and day
     *
     * <pre><code>
     *     yyyy-MM-dd
     * </code></pre>
     */
    F_DATE(YEAR.pattern + '-' + MONTH.pattern + '-' + DAY.pattern),

    /**
     * Formatted hour, minute and second
     *
     * <pre><code>
     *     HH:mm:ss
     * </code></pre>
     */
    F_TIME(HOUR.pattern + ':' + MINUTE.pattern + ':' + SECOND.pattern),

    /**
     * Formatted hour, minute, second and millisecond
     *
     * <pre><code>
     *     HH:mm:ss.SSS
     * </code></pre>
     */
    F_HOUR_2_MILSEC(F_TIME.pattern + '.' + MILLISECOND.pattern),

    /**
     * Formatted year, month, day, hour, minute and second
     *
     * <pre><code>
     *     yyyy-MM-dd HH:mm:ss
     * </code></pre>
     */
    F_DATE_TIME(F_DATE.pattern + ' ' + F_TIME.pattern),

    /**
     * Formatted year, month, day, hour, minute, second and millisecond
     *
     * <pre><code>
     *     yyyy-MM-dd HH:mm:ss.SSS
     * </code></pre>
     */
    F_ALL(F_DATE.pattern + ' ' + F_HOUR_2_MILSEC.pattern);

    /**
     * Pattern to format.
     */
    private final String pattern;

    /**
     * Formatter.
     */
    private final DateTimeFormatter formatter;

    private static final Map<String, DateType> $CODE_LOOKUP = Arrays.stream(values())
            .collect(collectingAndThen(toMap(it -> it.pattern, Function.identity()), Collections::unmodifiableMap));

    DateType(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public static boolean contains(String pattern) {
        return $CODE_LOOKUP.containsKey(pattern);
    }

    /**
     * Returns a constant of {@link DateType} matched given pattern.
     *
     * @param pattern pattern of date type
     * @return constant of {@link DateType} matched given pattern
     */
    public static DateType from(String pattern) {
        Asserts.that($CODE_LOOKUP)
                .describedAs("Enumeration 'DateType' has no value: '{0}'", pattern)
                .containsKey(pattern);

        return $CODE_LOOKUP.get(pattern);
    }

    /**
     * Returns a pattern.
     *
     * @return pattern
     */
    public String getPattern() {
        return this.pattern;
    }

    /**
     * Returns a formatter.
     *
     * @return formatter
     */
    public DateTimeFormatter getFormatter() {
        return this.formatter;
    }

}
