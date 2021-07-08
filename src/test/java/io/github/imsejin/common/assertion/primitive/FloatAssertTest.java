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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("FloatAssert")
class FloatAssertTest {

    private static final String EQUALITY = "io.github.imsejin.common.assertion.primitive.FloatAssertTest#equality";
    private static final String NON_EQUALITY = "io.github.imsejin.common.assertion.primitive.FloatAssertTest#nonEquality";

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

    ///////////////////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @Test
        @DisplayName("passes, when actual is greater than other")
        void test0() {
            Map<Float, Float> map = new HashMap<>();
            map.put(1.1F, (float) Character.valueOf('\u0000'));
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(32), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), 0F);
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(31));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0() {
            Map<Float, Float> map = new HashMap<>();
            map.put(1.14F, (float) Character.valueOf('\u0000'));
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(31), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            Map<Float, Float> map = new HashMap<>();
            map.put(-1.16F, (float) Character.valueOf('\u0000'));
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(32));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @Test
        @DisplayName("passes, when actual is less than other")
        void test0() {
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), 1.141F);
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(32));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            Map<Float, Float> map = new HashMap<>();
            map.put(1.99F, (float) Character.valueOf('\u0000'));
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(31), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is less than or equal to other")
        void test0() {
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), 1.0F);
            map.put(-1.024F, 1.024F);
            map.put(31F, Float.valueOf(31));
            map.put(0F, Float.MAX_VALUE);
            map.put(-Float.MAX_VALUE, 0F);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            Map<Float, Float> map = new HashMap<>();
            map.put((float) Character.valueOf('\u0000'), -1.001F);
            map.put(1.024F, -1.024F);
            map.put(Float.valueOf(32), 31F);
            map.put(Float.MAX_VALUE, 0F);
            map.put(0F, -Float.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative()'")
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

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative()'")
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

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasDecimalPart()'")
    class HasDecimalPart {
        @ParameterizedTest
        @ValueSource(floats = {
                -123.456F, -0.123456F, -Float.MIN_VALUE, Float.MIN_VALUE, 0.123456F, 123.456F
        })
        @DisplayName("passes, when actual has decimal part")
        void test0(float actual) {
            assertThatCode(() -> Asserts.that(actual).hasDecimalPart())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(floats = {
                -123456.0F, -Float.MAX_VALUE, 0, Float.MAX_VALUE, 1.0F, 123456.0F
        })
        @DisplayName("throws exception, when actual doesn't have decimal part")
        void test1(float actual) {
            assertThatCode(() -> Asserts.that(actual).hasDecimalPart())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
