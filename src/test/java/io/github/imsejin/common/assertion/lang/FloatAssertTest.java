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
import io.github.imsejin.common.assertion.composition.DecimalNumberAssertable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("FloatAssert")
class FloatAssertTest {

    private static final String FQCN = "io.github.imsejin.common.assertion.lang.FloatAssertTest";
    private static final String EQUALITY = FQCN + "#equality";
    private static final String NON_EQUALITY = FQCN + "#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when actual is equal to other")
        void test0(float actual, float expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(float actual, float expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("passes, when actual is not equal to other")
        void test0(float actual, float expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(float actual, float expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @Test
        @DisplayName("passes, when actual is greater than other")
        void test0() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put(1.1F, (float) Character.valueOf('\u0000'));
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(32), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), 0F);
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(31));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put(1.14F, (float) Character.valueOf('\u0000'));
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(31), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put(-1.16F, (float) Character.valueOf('\u0000'));
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(32));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @Test
        @DisplayName("passes, when actual is less than other")
        void test0() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), 1.141F);
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(32));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put(1.99F, (float) Character.valueOf('\u0000'));
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(31), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is less than or equal to other")
        void test0() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), 1.0F);
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(31));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            // given
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), -1.001F);
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(32), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isPositive'")
    class IsPositive {
        @ParameterizedTest
        @ValueSource(floats = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE})
        @DisplayName("passes, when actual is positive")
        void test0(float actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(floats = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(float actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(floats = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE})
        @DisplayName("passes, when actual is zero or positive")
        void test0(float actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(floats = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE})
        @DisplayName("throws exception, when actual is negative")
        void test1(float actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(floats = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE})
        @DisplayName("passes, when actual is negative")
        void test0(float actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(floats = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(float actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(floats = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE})
        @DisplayName("passes, when actual is zero or negative")
        void test0(float actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(floats = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE})
        @DisplayName("throws exception, when actual is positive")
        void test1(float actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isBetween'")
    class IsBetween {
        @Test
        @DisplayName("passes, when actual is between x and y inclusively")
        void test0() {
            new Random().doubles(-100, 50).limit(10_000).mapToObj(n -> (float) n)
                    .forEach(n -> assertThatNoException().isThrownBy(() -> {
                        Asserts.that(n).isBetween(n, n);
                        Asserts.that(n).isBetween(n, n + 0.1F);
                        Asserts.that(n).isBetween(n - 0.1F, n);
                        Asserts.that(n).isBetween(n - 0.1F, n + 0.1F);
                    }));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y inclusively")
        void test1() {
            // given
            List<Float> floats = new Random().doubles(-100, 50)
                    .limit(10_000).mapToObj(n -> (float) n).collect(toList());

            // expect
            floats.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n, n - 0.1F)));
            floats.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n + 0.1F, n)));
            floats.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n + 0.1F, n - 0.1F)));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'IsStrictlyBetween'")
    class IsStrictlyBetween {
        @Test
        @DisplayName("passes, when actual is between x and y exclusively")
        void test0() {
            new Random().doubles(-100, 50)
                    .limit(10_000).mapToObj(n -> (float) n).forEach(n -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n - 0.1F, n + 0.1F)));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y exclusively")
        void test1() {
            // given
            List<Float> floats = new Random().doubles(-100, 50)
                    .limit(10_000).mapToObj(n -> (float) n).collect(toList());

            // expect
            floats.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n)));
            floats.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n + 0.1F)));
            floats.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n - 0.1F, n)));
            floats.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n + 0.1F, n - 0.1F)));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isCloseTo'")
    class IsCloseTo {
        @Test
        @DisplayName("passes, when actual is close to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Float.MAX_VALUE).isCloseTo(Float.MAX_VALUE, 0);
                Asserts.that(Float.MAX_VALUE).isCloseTo(Float.MAX_VALUE * 0.9F, 10.001);
                Asserts.that(123_456_789.012F).isCloseTo(98_765.432F, 99.93);
                Asserts.that(1024.0F).isCloseTo(32.0F, 96.875);
                Asserts.that(100.05F).isCloseTo(93.99F, 7.01);
                Asserts.that(64.0F).isCloseTo(16.0F, 75);
                Asserts.that(5.0F).isCloseTo(4.5F, 12.5);
                Asserts.that((float) Math.PI).isCloseTo((float) Math.PI, Float.MIN_VALUE);
                Asserts.that((float) Math.sqrt(2)).isCloseTo(1.414213F, 4.3E-5);
                Asserts.that((float) -Math.PI).isCloseTo(-3.141592F, 2.3E-5);
                Asserts.that(-5.0F).isCloseTo(-4.0F, 20);
                Asserts.that(-5.0F).isCloseTo(-4.5F, 10);
                Asserts.that(-33.701F).isCloseTo(-3.64F, 90.91);
                Asserts.that(-100.0F).isCloseTo(-125.0F, 25);
                Asserts.that(-500.0F).isCloseTo(-499.3F, 0.2);
                Asserts.that(-87_654_321.098F).isCloseTo(-12_345.678F, 99.986);
                Asserts.that(4.9E-30F).isCloseTo(4.8E-30F, 2.05);
                Asserts.that(Float.MIN_VALUE).isCloseTo(Float.MIN_VALUE, 0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not close to other")
        void test1() {
            String regex = "^It is expected to close to other by less than [0-9.]+%, but difference was -?[0-9.]+%\\.[\\s\\S]+";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(36.5F).isCloseTo(null, 15))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Float.MAX_VALUE).isCloseTo(Float.MAX_VALUE * 0.9F, 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Float.MAX_VALUE).isCloseTo(Float.MIN_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(64.01F).isCloseTo(32.01F, 49.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(1.0F).isCloseTo(0.0F, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(-1.0F).isCloseTo(0.0F, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(-20.6F).isCloseTo(-15.0F, 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(4.9E-30F).isCloseTo(4.8E-30F, 2))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Float.NaN).isCloseTo(0.0F, 99.9))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Float.NEGATIVE_INFINITY).isCloseTo(0.0F, 99.9))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Float.POSITIVE_INFINITY).isCloseTo(0.1F, 99.9))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Float.MIN_VALUE).isCloseTo(Float.MAX_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(0.0F).isCloseTo(1.0F, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was Infinity%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(0.0F).isCloseTo(-1.0F, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was Infinity%.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasDecimalPart'")
    class HasDecimalPart {
        @ParameterizedTest
        @ValueSource(floats = {
                -123.456F, -0.123456F, -Float.MIN_VALUE, Float.MIN_VALUE, 0.123456F, 123.456F
        })
        @DisplayName("passes, when actual has decimal part")
        void test0(float actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).hasDecimalPart());
        }

        @ParameterizedTest
        @ValueSource(floats = {
                -123456.0F, -Float.MAX_VALUE, 0, Float.MAX_VALUE, 1.0F, 123456.0F
        })
        @DisplayName("throws exception, when actual doesn't have decimal part")
        void test1(float actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).hasDecimalPart())
                    .withMessageMatching(Pattern.quote(DecimalNumberAssertable.DEFAULT_DESCRIPTION_HAS_DECIMAL_PART) +
                            "\n {4}actual: '-?[0-9]+(\\.[0-9]+(E[0-9]+)?)?'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    private static Stream<Arguments> equality() {
        Map<Float, Float> map = new HashMap<>();
        map.put((float) Character.valueOf('\u0000'), 0F);
        map.put(-1.024F, 1.024F * -1);
        map.put(31F, Float.valueOf(31));
        map.put(Float.MIN_VALUE, Float.MIN_VALUE);
        map.put(Float.MAX_VALUE, Float.MAX_VALUE);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> nonEquality() {
        Map<Float, Float> map = new HashMap<>();
        map.put((float) Character.valueOf('a'), 0F);
        map.put(1.024F, -1.024F);
        map.put(31F, (float) (31 >> 7));
        map.put(Float.MIN_VALUE, -3.4028235E38F);
        map.put(Float.MAX_VALUE, 1.4E-45F);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

}
