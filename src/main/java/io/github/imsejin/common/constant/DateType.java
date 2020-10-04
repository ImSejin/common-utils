package io.github.imsejin.common.constant;

import io.github.imsejin.common.constant.interfaces.KeyValue;

import java.util.Arrays;
import java.util.Optional;

/**
 * Datetime patterns for {@link java.time.format.DateTimeFormatter#ofPattern(String)}.
 */
public enum DateType implements KeyValue {

    /**
     * Year
     *
     * <pre>{@code
     *     yyyy
     * }</pre>
     */
    YEAR("yyyy"),

    /**
     * Month
     *
     * <pre>{@code
     *     MM
     * }</pre>
     */
    MONTH("MM"),

    /**
     * Day
     *
     * <pre>{@code
     *     dd
     * }</pre>
     */
    DAY("dd"),

    /**
     * Hour
     *
     * <pre>{@code
     *     HH
     * }</pre>
     */
    HOUR("HH"),

    /**
     * Minute
     *
     * <pre>{@code
     *     mm
     * }</pre>
     */
    MINUTE("mm"),

    /**
     * Second
     *
     * <pre>{@code
     *     ss
     * }</pre>
     */
    SECOND("ss"),

    /**
     * Millisecond
     *
     * <pre>{@code
     *     SSS
     * }</pre>
     */
    MILLISECOND("SSS"),

    /**
     * Year and month
     *
     * <pre>{@code
     *     yyyyMM
     * }</pre>
     */
    YEAR_MONTH(YEAR.value() + MONTH.value()),

    /**
     * Year, month and day
     *
     * <pre>{@code
     *     yyyyMMdd
     * }</pre>
     */
    DATE(YEAR.value() + MONTH.value() + DAY.value()),

    /**
     * Hour, minute and second
     *
     * <pre>{@code
     *     HHmmss
     * }</pre>
     */
    TIME(HOUR.value() + MINUTE.value() + SECOND.value()),

    /**
     * Hour, minute, second and millisecond
     *
     * <pre>{@code
     *     HHmmssSSS
     * }</pre>
     */
    HOUR_2_MILSEC(TIME.value() + MILLISECOND.value()),

    /**
     * Year, month, day, hour, minute and second
     *
     * <pre>{@code
     *     yyyyMMddHHmmss
     * }</pre>
     */
    DATE_TIME(DATE.value() + TIME.value()),

    /**
     * Year, month, day, hour, minute, second and millisecond
     *
     * <pre>{@code
     *     yyyyMMddHHmmssSSS
     * }</pre>
     */
    ALL(DATE.value() + HOUR_2_MILSEC.value()),

    /**
     * Formatted year, month and day
     *
     * <pre>{@code
     *     yyyy-MM-dd
     * }</pre>
     */
    F_DATE(YEAR.value() + "-" + MONTH.value() + "-" + DAY.value()),

    /**
     * Formatted hour, minute and second
     *
     * <pre>{@code
     *     HH:mm:ss
     * }</pre>
     */
    F_TIME(HOUR.value() + ":" + MINUTE.value() + ":" + SECOND.value()),

    /**
     * Formatted hour, minute, second and millisecond
     *
     * <pre>{@code
     *     HH:mm:ss.SSS
     * }</pre>
     */
    F_HOUR_2_MILSEC(F_TIME.value() + "." + MILLISECOND.value()),

    /**
     * Formatted year, month, day, hour, minute and second
     *
     * <pre>{@code
     *     yyyy-MM-dd HH:mm:ss
     * }</pre>
     */
    F_DATE_TIME(F_DATE.value() + " " + F_TIME.value()),

    /**
     * Formatted year, month, day, hour, minute, second and millisecond
     *
     * <pre>{@code
     *     yyyy-MM-dd HH:mm:ss.SSS
     * }</pre>
     */
    F_ALL(F_DATE.value() + " " + F_HOUR_2_MILSEC.value());

    /**
     * Pattern to format.
     */
    private final String pattern;

    DateType(String pattern) {
        this.pattern = pattern;
    }

    public static boolean contains(String pattern) {
        for (DateType dateType : DateType.values()) {
            if (dateType.pattern.equals(pattern)) return true;
        }

        return false;
    }

    public static Optional<DateType> of(String pattern) {
        return Arrays.stream(DateType.values())
                .filter(type -> type.pattern.equals(pattern))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String key() {
        return this.name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value() {
        return this.pattern;
    }

}
