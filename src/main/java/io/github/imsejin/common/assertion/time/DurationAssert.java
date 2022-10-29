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

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.AmountAssertable;
import io.github.imsejin.common.assertion.composition.AmountComparisonAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.math.BigDecimalAssert;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.AbstractMap.SimpleEntry;

public class DurationAssert<
        SELF extends DurationAssert<SELF>>
        extends ObjectAssert<SELF, Duration>
        implements AmountAssertable<SELF, Duration>,
        AmountComparisonAssertable<SELF, Duration> {

    public DurationAssert(Duration actual) {
        super(actual);
    }

    protected DurationAssert(Descriptor<?> descriptor, Duration actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF isGreaterThan(Duration expected) {
        if (!AmountComparisonAssertable.IS_GREATER_THAN.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(Duration expected) {
        if (!AmountComparisonAssertable.IS_GREATER_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThan(Duration expected) {
        if (!AmountComparisonAssertable.IS_LESS_THAN.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThanOrEqualTo(Duration expected) {
        if (!AmountComparisonAssertable.IS_LESS_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isPositive() {
        if (actual.isZero() || actual.isNegative()) {
            setDefaultDescription("It is expected to be positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isZeroOrPositive() {
        if (actual.isNegative()) {
            setDefaultDescription("It is expected to be zero or positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNegative() {
        if (!actual.isNegative()) {
            setDefaultDescription("It is expected to be negative, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isZeroOrNegative() {
        if (!actual.isZero() && !actual.isNegative()) {
            setDefaultDescription("It is expected to be zero or negative, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public BigDecimalAssert<?> asTotalSeconds() {
        class BigDecimalAssertImpl extends BigDecimalAssert<BigDecimalAssertImpl> {
            BigDecimalAssertImpl(Descriptor<?> descriptor, BigDecimal actual) {
                super(descriptor, actual);
            }
        }

        BigDecimal totalSeconds = new BigDecimal(actual.getSeconds() + "." + actual.getNano());
        return new BigDecimalAssertImpl(this, totalSeconds);
    }

}
