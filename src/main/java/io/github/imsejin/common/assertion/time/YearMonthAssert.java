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

package io.github.imsejin.common.assertion.time;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.YearAssertable;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public class YearMonthAssert<SELF extends YearMonthAssert<SELF>>
        extends AbstractTemporalAccessorAssert<SELF, YearMonth>
        implements YearAssertable<SELF, YearMonth> {

    public YearMonthAssert(YearMonth actual) {
        super(actual);
    }

    @Override
    public SELF isLeapYear() {
        if (!actual.isLeapYear()) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_LEAP_YEAR, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotLeapYear() {
        if (actual.isLeapYear()) {
            setDefaultDescription(YearAssertable.DEFAULT_DESCRIPTION_IS_NOT_LEAP_YEAR, actual);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public YearAssert<?> asYear() {
        YearAssert<?> assertion = Asserts.that(Year.of(actual.getYear()));
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public MonthAssert<?> asMonth() {
        MonthAssert<?> assertion = Asserts.that(Month.of(actual.getMonthValue()));
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
