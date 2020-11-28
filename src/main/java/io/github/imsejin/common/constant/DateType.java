package io.github.imsejin.common.constant;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Datetime patterns for {@link java.time.format.DateTimeFormatter#ofPattern(String)}.
 */
public enum DateType {

    /**
     * Year
     *
     * <pre>{@code
     *     yyyy
     * }</pre>
     */
    YEAR("yyyy", ofPattern("yyyy")),

    /**
     * Month
     *
     * <pre>{@code
     *     MM
     * }</pre>
     */
    MONTH("MM", ofPattern("MM")),

    /**
     * Day
     *
     * <pre>{@code
     *     dd
     * }</pre>
     */
    DAY("dd", ofPattern("dd")),

    /**
     * Hour
     *
     * <pre>{@code
     *     HH
     * }</pre>
     */
    HOUR("HH", ofPattern("HH")),

    /**
     * Minute
     *
     * <pre>{@code
     *     mm
     * }</pre>
     */
    MINUTE("mm", ofPattern("mm")),

    /**
     * Second
     *
     * <pre>{@code
     *     ss
     * }</pre>
     */
    SECOND("ss", ofPattern("ss")),

    /**
     * Millisecond
     *
     * <pre>{@code
     *     SSS
     * }</pre>
     */
    MILLISECOND("SSS", ofPattern("SSS")),

    /**
     * Year and month
     *
     * <pre>{@code
     *     yyyyMM
     * }</pre>
     */
    YEAR_MONTH(YEAR.pattern + MONTH.pattern,
            ofPattern(YEAR.pattern + MONTH.pattern)),

    /**
     * Year, month and day
     *
     * <pre>{@code
     *     yyyyMMdd
     * }</pre>
     */
    DATE(YEAR.pattern + MONTH.pattern + DAY.pattern,
            ofPattern(YEAR.pattern + MONTH.pattern + DAY.pattern)),

    /**
     * Hour, minute and second
     *
     * <pre>{@code
     *     HHmmss
     * }</pre>
     */
    TIME(HOUR.pattern + MINUTE.pattern + SECOND.pattern,
            ofPattern(HOUR.pattern + MINUTE.pattern + SECOND.pattern)),

    /**
     * Hour, minute, second and millisecond
     *
     * <pre>{@code
     *     HHmmssSSS
     * }</pre>
     */
    HOUR_2_MILSEC(TIME.pattern + MILLISECOND.pattern,
            ofPattern(TIME.pattern + MILLISECOND.pattern)),

    /**
     * Year, month, day, hour, minute and second
     *
     * <pre>{@code
     *     yyyyMMddHHmmss
     * }</pre>
     */
    DATE_TIME(DATE.pattern + TIME.pattern,
            ofPattern(DATE.pattern + TIME.pattern)),

    /**
     * Year, month, day, hour, minute, second and millisecond
     *
     * <pre>{@code
     *     yyyyMMddHHmmssSSS
     * }</pre>
     */
    ALL(DATE.pattern + HOUR_2_MILSEC.pattern,
            ofPattern(DATE.pattern + HOUR_2_MILSEC.pattern)),

    /**
     * Formatted year, month and day
     *
     * <pre>{@code
     *     yyyy-MM-dd
     * }</pre>
     */
    F_DATE(YEAR.pattern + "-" + MONTH.pattern + "-" + DAY.pattern,
            ofPattern(YEAR.pattern + "-" + MONTH.pattern + "-" + DAY.pattern)),

    /**
     * Formatted hour, minute and second
     *
     * <pre>{@code
     *     HH:mm:ss
     * }</pre>
     */
    F_TIME(HOUR.pattern + ":" + MINUTE.pattern + ":" + SECOND.pattern,
            ofPattern(HOUR.pattern + ":" + MINUTE.pattern + ":" + SECOND.pattern)),

    /**
     * Formatted hour, minute, second and millisecond
     *
     * <pre>{@code
     *     HH:mm:ss.SSS
     * }</pre>
     */
    F_HOUR_2_MILSEC(F_TIME.pattern + "." + MILLISECOND.pattern,
            ofPattern(F_TIME.pattern + "." + MILLISECOND.pattern)),

    /**
     * Formatted year, month, day, hour, minute and second
     *
     * <pre>{@code
     *     yyyy-MM-dd HH:mm:ss
     * }</pre>
     */
    F_DATE_TIME(F_DATE.pattern + " " + F_TIME.pattern,
            ofPattern(F_DATE.pattern + " " + F_TIME.pattern)),

    /**
     * Formatted year, month, day, hour, minute, second and millisecond
     *
     * <pre>{@code
     *     yyyy-MM-dd HH:mm:ss.SSS
     * }</pre>
     */
    F_ALL(F_DATE.pattern + " " + F_HOUR_2_MILSEC.pattern,
            ofPattern(F_DATE.pattern + " " + F_HOUR_2_MILSEC.pattern));

    /**
     * Pattern to format.
     */
    private final String pattern;

    /**
     * Formatter.
     */
    private final DateTimeFormatter formatter;

    DateType(String pattern, DateTimeFormatter formatter) {
        this.pattern = pattern;
        this.formatter = formatter;
    }

    public static boolean contains(String pattern) {
        return Arrays.stream(values())
                .anyMatch(dateType -> dateType.pattern.equals(pattern));
    }

    public static Optional<DateType> of(String pattern) {
        return Arrays.stream(values())
                .filter(type -> type.pattern.equals(pattern))
                .findFirst();
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
