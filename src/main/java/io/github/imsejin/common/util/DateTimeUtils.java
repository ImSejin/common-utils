package io.github.imsejin.common.util;

import io.github.imsejin.common.constant.DateType;

import javax.annotation.Nonnull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Datetime utilities
 *
 * @see DateType
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /**
     * Checks if it's leap year.
     *
     * <pre>{@code
     *     isLeapYear(2019); // false
     *     isLeapYear(2020); // true
     * }</pre>
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
     * <pre>{@code
     *     today(); // 20191231
     * }</pre>
     *
     * @return today's date
     */
    public static String today() {
        return LocalDate.now().format(ofPattern(DateType.DATE.getPattern()));
    }

    /**
     * Returns today's date formatted with pattern.
     *
     * <pre>{@code
     *     today();                 // 20191231
     *
     *     today(DateType.YEAR);    // 2019
     *     today(DateType.MONTH);   // 12
     *     today(DateType.DAY);     // 31
     *     today(DateType.HOUR);    // 17
     *     today(DateType.MINUTE);  // 59
     *     today(DateType.SECOND);  // 59
     *     today(DateType.F_ALL);   // 2019-12-31 17:59:59.723
     * }</pre>
     *
     * @param type type of the date
     * @return today's datetime formatted with pattern
     */
    public static String today(@Nonnull DateType type) {
        return LocalDateTime.now().format(type.getFormatter());
    }

    /**
     * Returns yesterday's date formatted as 'yyyyMMdd'.
     *
     * <pre>{@code
     *     today();     // 20191231
     *     yesterday(); // 20191230
     * }</pre>
     *
     * @return yesterday's date
     */
    public static String yesterday() {
        return LocalDate.now().minusDays(1).format(DateType.DATE.getFormatter());
    }

    /**
     * Returns the corresponding element of yesterday's date formatted as 'yyyyMMdd'.
     *
     * <pre>{@code
     *     yesterday();                 // 20191230
     *
     *     yesterday(DateType.YEAR);    // 2019
     *     yesterday(DateType.MONTH);   // 12
     *     yesterday(DateType.DAY);     // 30
     * }</pre>
     *
     * @param type type of the date
     * @return yesterday's datetime formatted with pattern
     */
    public static String yesterday(@Nonnull DateType type) {
        return LocalDateTime.now().minusDays(1).format(type.getFormatter());
    }

    /**
     * Returns the current datetime formatted as 'yyyyMMddHHmmss'.
     *
     * <pre>{@code
     *     now(); // 20191231175959
     * }</pre>
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
     * <pre>{@code
     *     validate("2019-02-28");  // true
     *     validate("20190229");    // false
     *     validate("20200229");    // true
     *     validate("2020-02-29");  // true
     * }</pre>
     *
     * @param date date
     * @return whether the date is valid
     */
    public static boolean validate(@Nonnull String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateType.DATE.getPattern(), Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date.replace("-", ""));
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * Check if the date and day of the week are actual.
     *
     * <p> Support date formats for "yyyy-MMdd", "yyyy-MM-dd"
     *
     * <pre>{@code
     *     validate("20190228", DayOfWeek.THURSDAY);    // true
     *     validate("2019-02-28", DayOfWeek.THURSDAY);  // true
     *     validate("20190229", DayOfWeek.FRIDAY);      // false
     *     validate("20200229", DayOfWeek.SATURDAY);    // true
     *     validate("2020-02-29", DayOfWeek.SATURDAY);  // true
     * }</pre>
     *
     * @param date      date
     * @param dayOfWeek day of week
     * @return whether the date is valid
     */
    public static boolean validate(@Nonnull String date, DayOfWeek dayOfWeek) {
        // 유효한 날짜인지 확인한다
        if (!validate(date)) return false;

        date = date.replace("-", "");
        LocalDate localDate = LocalDate.parse(date, DateType.DATE.getFormatter());

        // 유효한 요일인지 확인한다
        return localDate.getDayOfWeek().equals(dayOfWeek);
    }

    /**
     * Returns the last date of the year and month.
     *
     * <pre>{@code
     *     withMonthlyLastDate(2019, 2); // 20190228
     *     withMonthlyLastDate(2020, 2); // 20200229
     * }</pre>
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
     * <pre>{@code
     *     withMonthlyLastDate(2019, Month.FEBRUARY); // 20190228
     *     withMonthlyLastDate(2020, Month.FEBRUARY); // 20200229
     * }</pre>
     *
     * @param year  year
     * @param month month
     * @return last date of the year and month
     */
    public static String getLastDateOfMonth(int year, @Nonnull Month month) {
        LocalDate lastDate = YearMonth.of(year, month).atEndOfMonth();
        return lastDate.format(DateType.DATE.getFormatter());
    }

    /**
     * Returns the last date of the year and month.
     *
     * <pre>{@code
     *     withMonthlyLastDate("2019", "2"); // 20190228
     *     withMonthlyLastDate("2020", "2"); // 20200229
     * }</pre>
     *
     * @param year  year
     * @param month month
     * @return last date of the year and month
     */
    public static String getLastDateOfMonth(@Nonnull String year, @Nonnull String month) {
        LocalDate lastDate = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month)).atEndOfMonth();
        return lastDate.format(DateType.DATE.getFormatter());
    }

    /**
     * Returns the last date of the year and month.
     *
     * <pre>{@code
     *     withMonthlyLastDate("2019", Month.FEBRUARY); // 20190228
     *     withMonthlyLastDate("2020", Month.FEBRUARY); // 20200229
     * }</pre>
     *
     * @param year  year
     * @param month month
     * @return last date of the year and month
     */
    public static String getLastDateOfMonth(@Nonnull String year, @Nonnull Month month) {
        LocalDate lastDate = YearMonth.of(Integer.parseInt(year), month).atEndOfMonth();
        return lastDate.format(DateType.DATE.getFormatter());
    }

}