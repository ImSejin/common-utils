package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.PositionComparisonAssertable;
import io.github.imsejin.common.assertion.composition.YearAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.time.InstantAssert;
import io.github.imsejin.common.assertion.time.YearMonthAssert;

import java.time.Instant;
import java.time.Year;
import java.time.YearMonth;
import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;
import java.util.Date;

/**
 * Assertion for {@link Date}
 *
 * @param <SELF>   this class
 * @param <ACTUAL> type of date
 */
public class DateAssert<
        SELF extends DateAssert<SELF, ACTUAL>,
        ACTUAL extends Date>
        extends ObjectAssert<SELF, ACTUAL>
        implements PositionComparisonAssertable<SELF, ACTUAL>,
        YearAssertable<SELF, ACTUAL> {

    public DateAssert(ACTUAL actual) {
        super(actual);
    }

    protected DateAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF isEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_NOT_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isBefore(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_BEFORE.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_BEFORE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isBeforeOrEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_BEFORE_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_BEFORE_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isAfter(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_AFTER_THAN.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_AFTER_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_AFTER_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_AFTER_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLeapYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actual);
        int year = calendar.get(Calendar.YEAR);

        if (!Year.isLeap(year)) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_LEAP_YEAR);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotLeapYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actual);
        int year = calendar.get(Calendar.YEAR);

        if (Year.isLeap(year)) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_NOT_LEAP_YEAR);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public InstantAssert<?> asInstant() {
        class InstantAssertImpl extends InstantAssert<InstantAssertImpl> {
            InstantAssertImpl(Descriptor<?> descriptor, Instant actual) {
                super(descriptor, actual);
            }
        }

        Instant instant = actual.toInstant();
        return new InstantAssertImpl(this, instant);
    }

    public YearMonthAssert<?> asYearMonth() {
        class YearMonthAssertImpl extends YearMonthAssert<YearMonthAssertImpl> {
            YearMonthAssertImpl(Descriptor<?> descriptor, YearMonth actual) {
                super(descriptor, actual);
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actual);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        YearMonth yearMonth = YearMonth.of(year, month);
        return new YearMonthAssertImpl(this, yearMonth);
    }

}
