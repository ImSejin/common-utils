/*
 * Copyright 2021 Sejin Im
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

package io.github.imsejin.common.assertion.time.chrono;

import java.time.MonthDay;
import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.util.AbstractMap.SimpleEntry;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.YearAssertable;
import io.github.imsejin.common.assertion.time.MonthDayAssert;
import io.github.imsejin.common.assertion.time.YearMonthAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

public class ChronoLocalDateAssert<SELF extends ChronoLocalDateAssert<SELF>>
        extends AbstractTemporalAccessorAssert<SELF, ChronoLocalDate>
        implements YearAssertable<SELF, ChronoLocalDate> {

    public ChronoLocalDateAssert(ChronoLocalDate actual) {
        super(actual);
    }

    protected ChronoLocalDateAssert(Descriptor<?> descriptor, ChronoLocalDate actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF isLeapYear() {
        if (!actual.isLeapYear()) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_LEAP_YEAR);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotLeapYear() {
        if (actual.isLeapYear()) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_NOT_LEAP_YEAR);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public YearMonthAssert<?> asYearMonth() {
        class YearMonthAssertImpl extends YearMonthAssert<YearMonthAssertImpl> {
            YearMonthAssertImpl(Descriptor<?> descriptor, YearMonth actual) {
                super(descriptor, actual);
            }
        }

        YearMonth yearMonth = YearMonth.from(actual);
        return new YearMonthAssertImpl(this, yearMonth);
    }

    public MonthDayAssert<?> asMonthDay() {
        class MonthDayAssertImpl extends MonthDayAssert<MonthDayAssertImpl> {
            MonthDayAssertImpl(Descriptor<?> descriptor, MonthDay actual) {
                super(descriptor, actual);
            }
        }

        MonthDay monthDay = MonthDay.from(actual);
        return new MonthDayAssertImpl(this, monthDay);
    }

}
