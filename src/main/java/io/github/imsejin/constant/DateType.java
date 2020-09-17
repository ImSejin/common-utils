package io.github.imsejin.constant;

import io.github.imsejin.constant.interfaces.KeyValue;

import java.util.Arrays;
import java.util.Optional;

public enum DateType implements KeyValue {

    /**
     * Year (yyyy)
     */
    YEAR("yyyy"),

    /**
     * Month (MM)
     */
    MONTH("MM"),

    /**
     * Day (dd)
     */
    DAY("dd"),

    /**
     * Hour (HH)
     */
    HOUR("HH"),

    /**
     * Minute (mm)
     */
    MINUTE("mm"),

    /**
     * Second (ss)
     */
    SECOND("ss"),

    /**
     * Millisecond (SSS)
     */
    MILLISECOND("SSS"),

    /**
     * Year and month (yyyyMM)
     */
    YEAR_MONTH(YEAR.value() + MONTH.value()),

    /**
     * Year, month and day (yyyyMMdd)
     */
    DATE(YEAR.value() + MONTH.value() + DAY.value()),

    /**
     * Hour, minute and second (HHmmss)
     */
    TIME(HOUR.value() + MINUTE.value() + SECOND.value()),

    /**
     * Hour, minute, second and millisecond (HHmmssSSS)
     */
    HOUR_2_MILSEC(TIME.value() + MILLISECOND.value()),

    /**
     * Year, month, day, hour, minute and second (yyyyMMddHHmmss)
     */
    DATE_TIME(DATE.value() + TIME.value()),

    /**
     * Year, month, day, hour, minute, second and millisecond (yyyyMMddHHmmssSSS)
     */
    ALL(DATE.value() + HOUR_2_MILSEC.value()),

    // yyyy-MM-dd
    F_DATE(YEAR.value() + "-" + MONTH.value() + "-" + DAY.value()),

    // HH:mm:ss
    F_TIME(HOUR.value() + ":" + MINUTE.value() + ":" + SECOND.value()),

    // HH:mm:ss.SSS
    F_HOUR_2_MILSEC(F_TIME.value() + "." + MILLISECOND.value()),

    // yyyy-MM-dd HH:mm:ss
    F_DATE_TIME(F_DATE.value() + " " + F_TIME.value()),

    // yyyy-MM-dd HH:mm:ss.SSS
    F_ALL(F_DATE.value() + F_HOUR_2_MILSEC.value());

    private final String pattern;

    DateType(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @param value
     * @return
     */
    public static boolean contains(String value) {
        for (DateType dateType : DateType.values()) {
            if (dateType.pattern.equals(value)) return true;
        }

        return false;
    }

    public static Optional<DateType> of(String pattern) {
        return Arrays.stream(DateType.values())
                .filter(type -> type.pattern.equals(pattern))
                .findFirst();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String key() {
        return this.name();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String value() {
        return this.pattern;
    }

}