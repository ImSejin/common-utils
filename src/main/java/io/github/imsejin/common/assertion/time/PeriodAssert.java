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
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.time.Period;
import java.util.Comparator;

public class PeriodAssert<SELF extends PeriodAssert<SELF>> extends ObjectAssert<SELF, Period> {

    private static final Comparator<Period> COMPARATOR = (o1, o2) -> {
        int total1 = (((o1.getYears() * 12) + o1.getMonths()) * 30) + o1.getDays();
        int total2 = (((o2.getYears() * 12) + o2.getMonths()) * 30) + o2.getDays();

        return total1 - total2;
    };

    public PeriodAssert(Period actual) {
        super(actual);
    }

    protected PeriodAssert(Descriptor<?> descriptor, Period actual) {
        super(descriptor, actual);
    }

    public SELF isGreaterThan(Period expected) {
        if (COMPARATOR.compare(actual, expected) <= 0) {
            setDefaultDescription("It is expected to be greater than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isGreaterThanOrEqualTo(Period expected) {
        if (COMPARATOR.compare(actual, expected) < 0) {
            setDefaultDescription("It is expected to be greater than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLessThan(Period expected) {
        if (COMPARATOR.compare(actual, expected) >= 0) {
            setDefaultDescription("It is expected to be less than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLessThanOrEqualTo(Period expected) {
        if (COMPARATOR.compare(actual, expected) > 0) {
            setDefaultDescription("It is expected to be less than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isPositive() {
        if (COMPARATOR.compare(actual, Period.ZERO) <= 0) {
            setDefaultDescription("It is expected to be positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isZeroOrPositive() {
        if (COMPARATOR.compare(actual, Period.ZERO) < 0) {
            setDefaultDescription("It is expected to be zero or positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isNegative() {
        if (COMPARATOR.compare(actual, Period.ZERO) >= 0) {
            setDefaultDescription("It is expected to be negative, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isZeroOrNegative() {
        if (COMPARATOR.compare(actual, Period.ZERO) > 0) {
            setDefaultDescription("It is expected to be zero or negative, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    @SuppressWarnings({"unchecked", "rawtypes"})
    public NumberAssert<?, Integer> asTotalDays() {
        int totalDays = (((actual.getYears() * 12) + actual.getMonths()) * 30) + actual.getDays();
        return new NumberAssert(this, totalDays) {
        };
    }

}
