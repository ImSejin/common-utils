/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.SizeComparisonAssertable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Assertion for {@link Number}
 *
 * @param <SELF>   this class
 * @param <NUMBER> comparable numeric type
 */
public class NumberAssert<
        SELF extends NumberAssert<SELF, NUMBER>,
        NUMBER extends Number & Comparable<NUMBER>>
        extends ObjectAssert<SELF, NUMBER>
        implements SizeComparisonAssertable<SELF, NUMBER> {

    private final NUMBER zero;

    @SuppressWarnings("unchecked")
    public NumberAssert(NUMBER actual) {
        super(actual);
        this.zero = toNumber((NUMBER) BigInteger.ZERO, (Class<NUMBER>) actual.getClass());
    }

    @SuppressWarnings("unchecked")
    protected NumberAssert(Descriptor<?> descriptor, NUMBER actual) {
        super(descriptor, actual);
        this.zero = toNumber((NUMBER) BigInteger.ZERO, (Class<NUMBER>) actual.getClass());
    }

    /**
     * Converts as instance whose type is subclass of {@link Number} to the given type.
     *
     * @param number     number to be converted
     * @param numberType type of number
     * @return converted number
     * @throws UnsupportedOperationException if {@code numberType} is not supported
     */
    @SuppressWarnings("unchecked")
    private static <N extends Number & Comparable<? extends Number>> N toNumber(N number, Class<N> numberType) {
        if (numberType == Byte.class) return (N) Byte.valueOf(number.byteValue());
        if (numberType == Short.class) return (N) Short.valueOf(number.shortValue());
        if (numberType == Integer.class) return (N) Integer.valueOf(number.intValue());
        if (numberType == Long.class) return (N) Long.valueOf(number.longValue());
        if (numberType == Float.class) return (N) Float.valueOf(number.floatValue());
        if (numberType == Double.class) return (N) Double.valueOf(number.doubleValue());
        if (numberType == BigInteger.class) return (N) BigInteger.valueOf(number.longValue());
        if (numberType == BigDecimal.class) return (N) BigDecimal.valueOf(number.doubleValue());

        throw new UnsupportedOperationException("NumberAssert doesn't support the type: " + numberType);
    }

    /**
     * Asserts that actual value is equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isEqualTo(2);
     *     Asserts.that(3.14).isEqualTo(3.14);
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isEqualTo(1);
     *     Asserts.that(3.14).isEqualTo(3.15);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isEqualTo(NUMBER expected) {
        if (!SizeComparisonAssertable.IS_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_EQUAL_TO, expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is not equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isNotEqualTo(1);
     *     Asserts.that(3.14).isNotEqualTo(3.15);
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isNotEqualTo(2);
     *     Asserts.that(3.14).isNotEqualTo(3.14);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isNotEqualTo(NUMBER expected) {
        if (!SizeComparisonAssertable.IS_NOT_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO, expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is greater than expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isGreaterThan(1);
     *     Asserts.that(3.15).isGreaterThan(3.14);
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isGreaterThan(2);
     *     Asserts.that(3.14).isGreaterThan(3.15);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isGreaterThan(NUMBER expected) {
        if (!SizeComparisonAssertable.IS_GREATER_THAN.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN, expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is greater than or equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isGreaterThanOrEqualTo(2);
     *     Asserts.that(3.15).isGreaterThanOrEqualTo(3.14);
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isGreaterThanOrEqualTo(3);
     *     Asserts.that(3.14).isGreaterThanOrEqualTo(3.15);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isGreaterThanOrEqualTo(NUMBER expected) {
        if (!SizeComparisonAssertable.IS_GREATER_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO, expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is less than expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isLessThan(3);
     *     Asserts.that(3.14).isLessThan(3.15);
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isLessThan(2);
     *     Asserts.that(3.15).isLessThan(3.14);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isLessThan(NUMBER expected) {
        if (!SizeComparisonAssertable.IS_LESS_THAN.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN, expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is less than or equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isLessThanOrEqualTo(2);
     *     Asserts.that(3.14).isLessThanOrEqualTo(3.15);
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isLessThanOrEqualTo(1);
     *     Asserts.that(3.15).isLessThanOrEqualTo(3.14);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isLessThanOrEqualTo(NUMBER expected) {
        if (!SizeComparisonAssertable.IS_LESS_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO, expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is positive.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isPositive();
     *     Asserts.that(3.14).isPositive();
     *
     *     // Assertion will fail.
     *     Asserts.that(0).isPositive();
     *     Asserts.that(-3.14).isPositive();
     * }</pre>
     *
     * @return this class
     */
    public SELF isPositive() {
        if (actual.compareTo(this.zero) <= 0) {
            setDefaultDescription("It is expected to be positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is zero or positive.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(0).isZeroOrPositive();
     *     Asserts.that(3.14).isZeroOrPositive();
     *
     *     // Assertion will fail.
     *     Asserts.that(-2).isZeroOrPositive();
     *     Asserts.that(-3.14).isZeroOrPositive();
     * }</pre>
     *
     * @return this class
     */
    public SELF isZeroOrPositive() {
        if (actual.compareTo(this.zero) < 0) {
            setDefaultDescription("It is expected to be zero or positive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is negative.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(-2).isNegative();
     *     Asserts.that(-3.14).isNegative();
     *
     *     // Assertion will fail.
     *     Asserts.that(0).isNegative();
     *     Asserts.that(3.14).isNegative();
     * }</pre>
     *
     * @return this class
     */
    public SELF isNegative() {
        if (actual.compareTo(this.zero) >= 0) {
            setDefaultDescription("It is expected to be negative, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is zero or negative.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(0).isZeroOrNegative();
     *     Asserts.that(-3.14).isZeroOrNegative();
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isZeroOrNegative();
     *     Asserts.that(3.14).isZeroOrNegative();
     * }</pre>
     *
     * @return this class
     */
    public SELF isZeroOrNegative() {
        if (actual.compareTo(this.zero) > 0) {
            setDefaultDescription("It is expected to be zero or negative, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is close to expected value with error rate.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).isCloseTo(2, 0.0);
     *     Asserts.that(100).isCloseTo(93, 7.01);
     *
     *     // Assertion will fail.
     *     Asserts.that(2).isCloseTo(2, 100.0);
     *     Asserts.that(100).isCloseTo(90, 99.9);
     * }</pre>
     *
     * @param expected   approximation
     * @param percentage acceptable error rate
     * @return this class
     */
    public SELF isCloseTo(NUMBER expected, double percentage) {
        Asserts.that(percentage)
                .describedAs("Error percentage must be zero or positive and less than 100, but it isn't: {0}", percentage)
                .isZeroOrPositive().isLessThan(100.0);

        // When acceptable error rate is 0%, they must be equal.
        if (percentage == 0.0) {
            return isEqualTo(expected);
        }

        // Prevents calculating actual value with expected value from resulting in NaN.
        if (Objects.deepEquals(actual, expected)) {
            return self;
        }

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
