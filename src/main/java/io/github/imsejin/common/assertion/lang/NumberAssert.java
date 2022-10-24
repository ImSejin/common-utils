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
import io.github.imsejin.common.assertion.composition.AmountComparisonAssertable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap.SimpleEntry;
import java.util.Objects;

/**
 * Assertion for {@link Number}
 *
 * @param <SELF>   this class
 * @param <ACTUAL> comparable number
 */
public class NumberAssert<
        SELF extends NumberAssert<SELF, ACTUAL>,
        ACTUAL extends Number & Comparable<ACTUAL>>
        extends ObjectAssert<SELF, ACTUAL>
        implements SizeComparisonAssertable<SELF, ACTUAL> {

    private final ACTUAL zero;

    @SuppressWarnings("unchecked")
    public NumberAssert(ACTUAL actual) {
        super(actual);
        this.zero = toActualNumber((ACTUAL) BigInteger.ZERO, (Class<ACTUAL>) actual.getClass());
    }

    @SuppressWarnings("unchecked")
    protected NumberAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
        this.zero = toActualNumber((ACTUAL) BigInteger.ZERO, (Class<ACTUAL>) actual.getClass());
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
    private ACTUAL toActualNumber(ACTUAL number, Class<ACTUAL> numberType) {
        if (numberType.isAssignableFrom(Byte.class)) return (ACTUAL) Byte.valueOf(number.byteValue());
        if (numberType.isAssignableFrom(Short.class)) return (ACTUAL) Short.valueOf(number.shortValue());
        if (numberType.isAssignableFrom(Integer.class)) return (ACTUAL) Integer.valueOf(number.intValue());
        if (numberType.isAssignableFrom(Long.class)) return (ACTUAL) Long.valueOf(number.longValue());
        if (numberType.isAssignableFrom(Float.class)) return (ACTUAL) Float.valueOf(number.floatValue());
        if (numberType.isAssignableFrom(Double.class)) return (ACTUAL) Double.valueOf(number.doubleValue());
        if (numberType.isAssignableFrom(BigInteger.class)) return (ACTUAL) BigInteger.valueOf(number.longValue());
        if (numberType.isAssignableFrom(BigDecimal.class)) return (ACTUAL) BigDecimal.valueOf(number.doubleValue());

        throw new UnsupportedOperationException("NumberAssert doesn't support the type: " + numberType);
    }

    // -------------------------------------------------------------------------------------------------

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
    public SELF isEqualTo(ACTUAL expected) {
        if (!AmountComparisonAssertable.IS_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

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
    public SELF isNotEqualTo(ACTUAL expected) {
        if (!AmountComparisonAssertable.IS_NOT_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

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
    public SELF isGreaterThan(ACTUAL expected) {
        if (!AmountComparisonAssertable.IS_GREATER_THAN.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

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
    public SELF isGreaterThanOrEqualTo(ACTUAL expected) {
        if (!AmountComparisonAssertable.IS_GREATER_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

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
    public SELF isLessThan(ACTUAL expected) {
        if (!AmountComparisonAssertable.IS_LESS_THAN.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

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
    public SELF isLessThanOrEqualTo(ACTUAL expected) {
        if (!AmountComparisonAssertable.IS_LESS_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

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
            setDefaultDescription("It is expected to be positive, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

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
            setDefaultDescription("It is expected to be zero or positive, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

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
            setDefaultDescription("It is expected to be negative, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

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
            setDefaultDescription("It is expected to be zero or negative, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

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
    public SELF isCloseTo(ACTUAL expected, double percentage) {
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
            setDefaultDescription("It is expected to close to other, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", null),
                    new SimpleEntry<>("percentage", percentage));

            throw getException();
        }

        // To calculate with arguments that are type of abstract Number, converts them to double.
        double $actual = actual.doubleValue();
        double $expected = expected.doubleValue();

        if (Double.isNaN($actual) || Double.isInfinite($actual) || Double.isNaN($expected) || Double.isInfinite($expected)) {
            setDefaultDescription("It is expected to close to other, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected),
                    new SimpleEntry<>("percentage", percentage));

            throw getException();
        }

        double diff = Math.abs($actual - $expected);
        double errorRate = diff / $actual * 100.0;

        // When actual is 0, errorRate is NaN or infinite.
        boolean invalid = Double.isNaN(errorRate) || Double.isInfinite(errorRate);
        if (invalid || Math.abs(errorRate) > percentage) {
            String $percentage = BigDecimal.valueOf(percentage).stripTrailingZeros().toPlainString();

            setDefaultDescription("It is expected to close to other by less than {0}%, but difference was {1}%.",
                    $percentage,
                    invalid ? errorRate : BigDecimal.valueOf(errorRate).stripTrailingZeros().toPlainString());
            setDescriptionVariables(
                    new SimpleEntry<>("actual", BigDecimal.valueOf($actual).stripTrailingZeros().toPlainString()),
                    new SimpleEntry<>("expected", BigDecimal.valueOf($expected).stripTrailingZeros().toPlainString()),
                    new SimpleEntry<>("percentage", $percentage));

            throw getException();
        }

        return self;
    }

}
