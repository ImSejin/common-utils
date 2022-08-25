package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.YearAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.time.InstantAssert;
import io.github.imsejin.common.assertion.time.YearMonthAssert;

import java.time.Instant;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class DateAssert<SELF extends DateAssert<SELF, ACTUAL>, ACTUAL extends Date>
        extends ObjectAssert<SELF, ACTUAL> {

    public DateAssert(ACTUAL actual) {
        super(actual);
    }

    protected DateAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    public SELF isBefore(ACTUAL expected) {
        if (actual.compareTo(expected) >= 0) {
            setDefaultDescription("It is expected to be before than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isBeforeOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) > 0) {
            setDefaultDescription("It is expected to be before than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isAfter(ACTUAL expected) {
        if (actual.compareTo(expected) <= 0) {
            setDefaultDescription("It is expected to be after than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) < 0) {
            setDefaultDescription("It is expected to be after than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLeapYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actual);
        int year = calendar.get(Calendar.YEAR);

        if (!Year.isLeap(year)) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_LEAP_YEAR, actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotLeapYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actual);
        int year = calendar.get(Calendar.YEAR);

        if (Year.isLeap(year)) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_NOT_LEAP_YEAR, actual);
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
