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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ObjectAssert")
class ObjectAssertTest {

    @Nested
    @DisplayName("method 'isNull'")
    class IsNull {
        @Test
        @DisplayName("passes, when actual is null")
        void test0() {
            assertThatNoException().isThrownBy(() -> Asserts.that((Object) null).isNull());
        }

        @Test
        @DisplayName("throws exception, when actual is not null")
        void test1() {
            // given
            List<Object> params = Arrays.asList(new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            params.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNull())
                    .withMessageStartingWith("It is expected to be null, but not null."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotNull'")
    class IsNotNull {
        @Test
        @DisplayName("passes, when actual is not null")
        void test0() {
            // given
            List<Object> params = Arrays.asList(new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            params.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotNull()));
        }

        @Test
        @DisplayName("throws exception, when actual is null")
        void test1() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((Object) null).isNotNull())
                    .withMessageStartingWith("It is expected to be not null, but null.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isSameAs'")
    class IsSameAs {
        @Test
        @DisplayName("passes, when actual and other are the same instance")
        void test0() {
            // given
            List<Object> params = Arrays.asList(null, new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            // expect
            params.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isSameAs(actual)));
        }

        @Test
        @DisplayName("throws exception, when actual and other are not the same instance")
        void test1() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put(null, 'b');
            params.put("alpha", String.valueOf("alpha".toCharArray()));
            params.put('b', 3.14);
            params.put(3.14, null);

            // expect
            params.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isSameAs(expected))
                    .withMessageStartingWith("They are expected to be the same, but they aren't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotSameAs'")
    class IsNotSameAs {
        @Test
        @DisplayName("passes, when actual and other are not the same instance")
        void test0() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put(null, 'b');
            params.put("alpha", String.valueOf("alpha".toCharArray()));
            params.put('b', 3.14);
            params.put(3.14, null);

            // expect
            params.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotSameAs(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual and other are not the same instance")
        void test1() {
            // given
            List<Object> params = Arrays.asList(null, new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            // expect
            params.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotSameAs(actual))
                    .withMessageStartingWith("They are expected to be not the same, but they are."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @Test
        @DisplayName("passes, when actual is equal to other")
        void test0() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put("alpha", String.valueOf("alpha".toCharArray()));
            params.put('\n', '\n');
            params.put(3.14, 3.14);
            params.put(BigInteger.valueOf(1000), BigInteger.valueOf(1000));

            // expect
            params.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isEqualTo(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is not equal to other")
        void test1() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put(new Object(), new Object());
            params.put("alpha", "beta");
            params.put('b', 'c');
            params.put(3.14, 3.141592);

            // expect
            params.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEqualTo(expected))
                    .withMessageStartingWith("They are expected to be equal, but they aren't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @Test
        @DisplayName("passes, when actual is not equal to other")
        void test0() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put(new Object(), new Object());
            params.put("alpha", "beta");
            params.put('b', 'c');
            params.put(3.14, 3.141592);

            // expect
            params.forEach((actual, expected) -> assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is equal to other")
        void test1() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put("alpha", String.valueOf("alpha".toCharArray()));
            params.put('\n', '\n');
            params.put(3.14, 3.14);
            params.put(BigInteger.valueOf(1000), BigInteger.valueOf(1000));

            // expect
            params.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .withMessageStartingWith("They are expected to be not equal, but they are."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isInstanceOf'")
    class IsInstanceOf {
        @Test
        @DisplayName("passes, when actual is the instance of given type")
        void test0() {
            // given
            Map<Object, Class<?>> params = new HashMap<>();
            params.put(new Object(), Object.class);
            params.put("alpha", String.class);
            params.put('b', char.class);
            params.put('c', Character.class);
            params.put(3.14, double.class);
            params.put(3.141592, Double.class);

            // expect
            params.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isInstanceOf(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is not the instance of given type")
        void test1() {
            // given
            Map<Object, Class<?>> params = new HashMap<>();
            params.put("alpha", Character.class);
            params.put('\n', String.class);
            params.put(3.14, float.class);
            params.put(BigInteger.valueOf(1000), BigDecimal.class);

            // expect
            params.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isInstanceOf(expected))
                    .withMessageStartingWith("It is expected to be instance of the type, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'is'")
    class Is {
        @Test
        @DisplayName("passes, when condition result of the actual is true")
        void test0() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put(LocalDate.now(), LocalDateTime.now().toLocalDate());
            params.put("alpha", "ALPHA".toLowerCase());
            params.put('c', "c".charAt(0));
            params.put(3.14F, Float.valueOf("3.14"));
            params.put(3.141592, Double.valueOf("3.141592"));

            // expect
            params.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).is(expected::equals)));
        }

        @Test
        @DisplayName("throws exception, when condition result of the actual is false")
        void test1() {
            // given
            Map<Object, Object> params = new HashMap<>();
            params.put(LocalDate.now(), LocalDateTime.now().minusDays(1));
            params.put("alpha", "alpha".toUpperCase());
            params.put('c', "C".charAt(0));
            params.put(3.14F, 3.14);
            params.put(3.141592, 3.141592F);

            // expect
            params.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).is(expected::equals))
                    .withMessageStartingWith("It is expected to be true, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'returns'")
    class Returns {
        @Test
        @DisplayName("passes, when value returned by function using the actual is equal to the expected")
        void test0() {
            // given
            Map<Object, Function<Object, Object>> params = new HashMap<>();
            params.put(LocalDate.now(), it -> LocalDateTime.now().toLocalDate());
            params.put("alpha", it -> "ALPHA".toLowerCase());
            params.put('c', it -> Character.toLowerCase("C".charAt(0)));
            params.put(3.14F, it -> Float.valueOf("3.14"));
            params.put(3.141592, it -> Double.valueOf("3.141592"));

            // expect
            params.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).returns(actual, expected)));
        }

        @Test
        @DisplayName("throws exception, when value returned by function using the actual is not equal to the expected")
        void test1() {
            // given
            Map<String, String> params = IntStream.range(0, 10).mapToObj(n -> UUID.randomUUID().toString())
                    .collect(HashMap::new, (m, it) -> m.put(it, it.replace("-", "")), Map::putAll);

            // expect
            params.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).returns(actual, it -> it.replace('-', '_')))
                    .withMessageStartingWith("They are expected to be equal, but they aren't."));
        }
    }

}
