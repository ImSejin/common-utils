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
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.math.BigDecimalAssert;

import java.math.BigDecimal;
import java.time.Duration;

public class DurationAssert<SELF extends DurationAssert<SELF>> extends ObjectAssert<SELF, Duration> {

    public DurationAssert(Duration actual) {
        super(actual);
    }

    protected DurationAssert(Descriptor<?> descriptor, Duration actual) {
        super(descriptor, actual);
    }

    public SELF isGreaterThan(Duration expected) {
        if (actual.compareTo(expected) <= 0) {
            setDefaultDescription("It is expected to be greater than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isGreaterThanOrEqualTo(Duration expected) {
        if (actual.compareTo(expected) < 0) {
            setDefaultDescription("It is expected to be greater than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLessThan(Duration expected) {
        if (actual.compareTo(expected) >= 0) {
            setDefaultDescription("It is expected to be less than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLessThanOrEqualTo(Duration expected) {
        if (actual.compareTo(expected) > 0) {
            setDefaultDescription("It is expected to be less than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isPositive() {
        if (actual.isZero() || actual.isNegative()) {
            setDefaultDescription("It is expected to be positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isZeroOrPositive() {
        if (actual.isNegative()) {
            setDefaultDescription("It is expected to be zero or positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isNegative() {
        if (!actual.isNegative()) {
            setDefaultDescription("It is expected to be negative, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

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
