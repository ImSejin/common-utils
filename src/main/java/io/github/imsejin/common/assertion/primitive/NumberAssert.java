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

package io.github.imsejin.common.assertion.primitive;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

import java.math.BigDecimal;
import java.util.Objects;

public class NumberAssert<
        SELF extends NumberAssert<SELF, NUMBER>,
        NUMBER extends Number & Comparable<NUMBER>>
        extends AbstractObjectAssert<SELF, NUMBER> {

    private final NUMBER zero;

    @SuppressWarnings("unchecked")
    public NumberAssert(NUMBER actual) {
        super(actual);
        this.zero = toNumber((NUMBER) Integer.valueOf(0), (Class<NUMBER>) actual.getClass());
    }

    /**
     * Converts a number to the given type.
     *
     * @param number     number to be converted
     * @param numberType type of number
     * @return converted number
     */
    @SuppressWarnings("unchecked")
    private static <N extends Number & Comparable<? extends Number>> N toNumber(N number, Class<N> numberType) {
        if (numberType == Byte.class) return (N) Byte.valueOf(number.byteValue());
        if (numberType == Short.class) return (N) Short.valueOf(number.shortValue());
        if (numberType == Integer.class) return (N) Integer.valueOf(number.intValue());
        if (numberType == Long.class) return (N) Long.valueOf(number.longValue());
        if (numberType == Float.class) return (N) Float.valueOf(number.floatValue());
        if (numberType == Double.class) return (N) Double.valueOf(number.doubleValue());

        return null;
    }

    public SELF isGreaterThan(NUMBER expected) {
        if (actual.compareTo(expected) <= 0) throw getException();
        return self;
    }

    public SELF isGreaterThanOrEqualTo(NUMBER expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
    }

    public SELF isLessThan(NUMBER expected) {
        if (actual.compareTo(expected) >= 0) throw getException();
        return self;
    }

    public SELF isLessThanOrEqualTo(NUMBER expected) {
        if (actual.compareTo(expected) > 0) throw getException();
        return self;
    }

    public SELF isPositive() {
        if (actual.compareTo(this.zero) <= 0) throw getException();
        return self;
    }

    public SELF isZeroOrPositive() {
        if (actual.compareTo(this.zero) < 0) throw getException();
        return self;
    }

    public SELF isNegative() {
        if (actual.compareTo(this.zero) >= 0) throw getException();
        return self;
    }

    public SELF isZeroOrNegative() {
        if (actual.compareTo(this.zero) > 0) throw getException();
        return self;
    }

    public SELF isBetween(NUMBER startInclusive, NUMBER endInclusive) {
        return isGreaterThanOrEqualTo(startInclusive).isLessThanOrEqualTo(endInclusive);
    }

    public SELF isStrictlyBetween(NUMBER startExclusive, NUMBER endExclusive) {
        return isGreaterThan(startExclusive).isLessThan(endExclusive);
    }

    /**
     * Asserts that actual value is close to other.
     *
     * @param expected   approximation
     * @param percentage acceptable error rate
     * @return assertion instance
     */
    public SELF isCloseTo(NUMBER expected, double percentage) {
        Asserts.that(percentage)
                .as("Error percentage must be zero or positive and less than 100, but it isn't: {0}", percentage)
                .isZeroOrPositive().isLessThan(100.0);

        // When acceptable error rate is 0%, they must be equal.
        if (percentage == 0.0) return isEqualTo(expected);

        // Prevents calculating actual value with expected value from resulting in NaN.
        if (Objects.deepEquals(actual, expected)) return self;

        if (expected == null) {
            setDefaultDescription("It is expected to close to other, but it isn't. (expected: 'null', actual: '{0}')", actual);
            throw getException();
        }

        // To calculate with arguments that are type of abstract Number, converts them to double.
        double $actual = actual.doubleValue();
        double $expected = expected.doubleValue();

        if (Double.isNaN($actual) || Double.isInfinite($actual) || Double.isNaN($expected) || Double.isInfinite($expected)) {
            setDefaultDescription("It is expected to close to other, but it isn't. (expected: '{0}', actual: '{1}')",
                    String.valueOf(expected), String.valueOf(actual));
            throw getException();
        }

        double diff = Math.abs($actual - $expected);
        double errorRate = diff / $actual * 100.0;

        // When actual is 0, errorRate is NaN or infinite.
        boolean invalid = Double.isNaN(errorRate) || Double.isInfinite(errorRate);
        if (invalid || Math.abs(errorRate) > percentage) {
            setDefaultDescription("It is expected to close to other by less than {0}%, but difference was {1}%. (expected: '{2}', actual: '{3}')",
                    BigDecimal.valueOf(percentage).stripTrailingZeros().toPlainString(),
                    invalid ? errorRate : BigDecimal.valueOf(errorRate).stripTrailingZeros().toPlainString(),
                    BigDecimal.valueOf($expected).stripTrailingZeros().toPlainString(),
                    BigDecimal.valueOf($actual).stripTrailingZeros().toPlainString());
            throw getException();
        }

        return self;
    }

}
