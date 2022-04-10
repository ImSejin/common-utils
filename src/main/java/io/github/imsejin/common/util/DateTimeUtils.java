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
import io.github.imsejin.common.constant.DateType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.zone.ZoneRules;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Datetime utilities
 *
 * @see DateType
 */
public final class DateTimeUtils {

    private static final Randoms randoms = new Randoms(ThreadLocalRandom.current());

    @ExcludeFromGeneratedJacocoReport
    private DateTimeUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Checks if it's leap year.
     *
     * <pre><code>
     *     isLeapYear(2019); // false
     *     isLeapYear(2020); // true
     * </code></pre>
     *
     * @param year year you want to check
     * @return the year is leap year or not
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * Returns today's date formatted as 'yyyyMMdd'.
     *
     * <pre><code>
     *     today(); // 20191231
     * </code></pre>
     *
     * @return today's date
     */
    public static String today() {
        return LocalDate.now().format(DateType.DATE.getFormatter());
    }

    /**
     * Returns today's date formatted with pattern.
     *
     * <pre><code>
     *     today();                 // 20191231
     *
     *     today(DateType.YEAR);    // 2019
     *     today(DateType.MONTH);   // 12
     *     today(DateType.DAY);     // 31
     *     today(DateType.HOUR);    // 17
     *     today(DateType.MINUTE);  // 59
     *     today(DateType.SECOND);  // 59
     *     today(DateType.F_ALL);   // 2019-12-31 17:59:59.723
     * </code></pre>
     *
     * @param type type of the date
     * @return today's datetime formatted with pattern
     */
    public static String today(DateType type) {
        return LocalDateTime.now().format(type.getFormatter());
    }

    /**
     * Returns yesterday's date formatted as 'yyyyMMdd'.
     *
     * <pre><code>
     *     today();     // 20191231
     *     yesterday(); // 20191230
     * </code></pre>
     *
     * @return yesterday's date
     */
    public static String yesterday() {
        return LocalDate.now().minusDays(1).format(DateType.DATE.getFormatter());
    }

    /**
     * Returns the corresponding element of yesterday's date formatted as 'yyyyMMdd'.
     *
     * <pre><code>
     *     yesterday();                 // 20191230
     *
     *     yesterday(DateType.YEAR);    // 2019
     *     yesterday(DateType.MONTH);   // 12
     *     yesterday(DateType.DAY);     // 30
     * </code></pre>
     *
     * @param type type of the date
     * @return yesterday's datetime formatted with pattern
     */
    public static String yesterday(DateType type) {
        return LocalDateTime.now().minusDays(1).format(type.getFormatter());
    }

    /**
     * Returns the current datetime formatted as 'yyyyMMddHHmmss'.
     *
     * <pre><code>
     *     now(); // 20191231175959
     * </code></pre>
     *
     * @return corresponding element of yesterday's datetime
     */
    public static String now() {
        return LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
    }

    /**
     * Check if the date is actual.
     *
     * <p> Support date formats for "yyyy-MMdd", "yyyy-MM-dd"
     *
     * <pre><code>
     *     validate("2019-02-28");  // true
     *     validate("20190229");    // false
     *     validate("20200229");    // true
     *     validate("2020-02-29");  // true
     * </code></pre>
     *
     * @param date date
     * @return whether the date is valid
     */
    public static boolean validate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateType.DATE.getPattern(), Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date.replace("-", ""));
            return true;
        } catch (ParseException ignored) {
            return false;
        }
    }

    /**
     * Check if the date and day of the week are actual.
     *
     * <p> Support date formats for "yyyy-MMdd", "yyyy-MM-dd"
     *
     * <pre><code>
     *     validate("20190228", DayOfWeek.THURSDAY);    // true
     *     validate("2019-02-28", DayOfWeek.THURSDAY);  // true
     *     validate("20190229", DayOfWeek.FRIDAY);      // false
     *     validate("20200229", DayOfWeek.SATURDAY);    // true
     *     validate("2020-02-29", DayOfWeek.SATURDAY);  // true
     * </code></pre>
     *
     * @param date      date
     * @param dayOfWeek day of week
     * @return whether the date is valid
     */
    public static boolean validate(String date, DayOfWeek dayOfWeek) {
        // Checks if date is valid.
        if (!validate(date)) return false;

        date = date.replace("-", "");
        LocalDate localDate = LocalDate.parse(date, DateType.DATE.getFormatter());

        // Checks if day of week is valid.
        return localDate.getDayOfWeek().equals(dayOfWeek);
    }

    /**
     * Returns the last date of the year and month.
     *
     * <pre><code>
     *     getLastDateOfMonth(2019, 2); // 20190228
     *     getLastDateOfMonth(2020, 2); // 20200229
     * </code></pre>
     *
     * @param year  year
     * @param month month
     * @return last date of the year and month
     */
    public static String getLastDateOfMonth(int year, int month) {
        LocalDate lastDate = YearMonth.of(year, month).atEndOfMonth();
        return lastDate.format(DateType.DATE.getFormatter());
    }

    /**
     * Returns the last date of the year and month.
     *
     * <pre><code>
     *     getLastDateOfMonth(2019, Month.FEBRUARY); // 20190228
     *     getLastDateOfMonth(2020, Month.FEBRUARY); // 20200229
     * </code></pre>
     *
     * @param year  year
     * @param month month
     * @return last date of the year and month
     */
    public static String getLastDateOfMonth(int year, Month month) {
        LocalDate lastDate = YearMonth.of(year, month).atEndOfMonth();
        return lastDate.format(DateType.DATE.getFormatter());
    }

    /**
     * Returns the last date of the year and month.
     *
     * <pre><code>
     *     getLastDateOfMonth("2019", "2"); // 20190228
     *     getLastDateOfMonth("2020", "2"); // 20200229
     * </code></pre>
     *
     * @param year  year
     * @param month month
     * @return last date of the year and month
     */
    public static String getLastDateOfMonth(String year, String month) {
        LocalDate lastDate = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month)).atEndOfMonth();
        return lastDate.format(DateType.DATE.getFormatter());
    }

    /**
     * Returns the last date of the year and month.
     *
     * <pre><code>
     *     getLastDateOfMonth("2019", Month.FEBRUARY); // 20190228
     *     getLastDateOfMonth("2020", Month.FEBRUARY); // 20200229
     * </code></pre>
     *
     * @param year  year
     * @param month month
     * @return last date of the year and month
     */
    public static String getLastDateOfMonth(String year, Month month) {
        LocalDate lastDate = YearMonth.of(Integer.parseInt(year), month).atEndOfMonth();
        return lastDate.format(DateType.DATE.getFormatter());
    }

    /**
     * Returns {@link ZoneOffset} of system default.
     *
     * @return offset of zone
     */
    public static ZoneOffset getSystemDefaultZoneOffset() {
        ZoneId zone = ZoneId.systemDefault();
        ZoneRules rules = zone.getRules();
        return rules.getOffset(LocalDateTime.now(zone));
    }

    /**
     * Returns randomly generated {@link LocalDateTime}.
     *
     * @param start start date time
     * @param end   end date time
     * @return random date time
     */
    public static LocalDateTime random(ChronoLocalDateTime<? extends ChronoLocalDate> start,
                                       ChronoLocalDateTime<? extends ChronoLocalDate> end) {
        return randoms.nextDateTime(start, end);
    }

    /**
     * Returns randomly generated {@link LocalDateTime}.
     *
     * @param start  start date time
     * @param end    end date time
     * @param offset zone offset
     * @return random date time
     */
    public static LocalDateTime random(ChronoLocalDateTime<? extends ChronoLocalDate> start,
                                       ChronoLocalDateTime<? extends ChronoLocalDate> end,
                                       ZoneOffset offset) {
        return randoms.nextDateTime(start, end, offset);
    }

    private static class Randoms {
        private final ThreadLocalRandom random;

        private Randoms(ThreadLocalRandom random) {
            this.random = random;
        }

        public LocalDateTime nextDateTime(ChronoLocalDateTime<? extends ChronoLocalDate> start,
                                          ChronoLocalDateTime<? extends ChronoLocalDate> end) {
            return nextDateTime(start, end, getSystemDefaultZoneOffset());
        }

        public LocalDateTime nextDateTime(ChronoLocalDateTime<? extends ChronoLocalDate> start,
                                          ChronoLocalDateTime<? extends ChronoLocalDate> end,
                                          ZoneOffset offset) {
            long randomSeconds = this.random.nextLong(start.toEpochSecond(offset), end.toEpochSecond(offset));
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(randomSeconds), ZoneId.systemDefault());
        }
    }

}
