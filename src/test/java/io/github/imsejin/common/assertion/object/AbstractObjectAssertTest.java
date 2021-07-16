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

package io.github.imsejin.common.assertion.object;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AbstractObjectAssert")
class AbstractObjectAssertTest {

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
            List<Object> list = Arrays.asList(new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNull())
                    .withMessageStartingWith("It is expected to be null, but not null."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotNull'")
    class IsNotNull {
        @Test
        @DisplayName("passes, when actual is not null")
        void test0() {
            List<Object> list = Arrays.asList(new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            list.forEach(actual -> assertThatNoException()
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

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isSameAs'")
    class IsSameAs {
        @Test
        @DisplayName("passes, when actual and other is the same instance")
        void test0() {
            List<Object> list = Arrays.asList(new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isSameAs(actual)));
        }

        @Test
        @DisplayName("throws exception, when actual and other is not the same instance")
        void test1() {
            Map<Object, Object> map = new HashMap<>();
            map.put(null, 'b');
            map.put("alpha", String.valueOf("alpha".toCharArray()));
            map.put('b', 3.14);
            map.put(3.14, null);

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isSameAs(expected))
                    .withMessageStartingWith("They are expected to be the same, but they aren't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotSameAs'")
    class IsNotSameAs {
        @Test
        @DisplayName("passes, when actual and other is not the same instance")
        void test0() {
            Map<Object, Object> map = new HashMap<>();
            map.put(null, 'b');
            map.put("alpha", String.valueOf("alpha".toCharArray()));
            map.put('b', 3.14);
            map.put(3.14, null);

            map.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotSameAs(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual and other is not the same instance")
        void test1() {
            List<Object> list = Arrays.asList(new Object(), "", 'a', 3.14, IllegalArgumentException.class);

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotSameAs(actual))
                    .withMessageStartingWith("They are expected to be not the same, but they aren't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @Test
        @DisplayName("passes, when actual is equal to other")
        void test0() {
            Map<Object, Object> map = new HashMap<>();
            map.put("alpha", String.valueOf("alpha".toCharArray()));
            map.put('\n', Character.valueOf('\n'));
            map.put(3.14, Double.valueOf(3.14));
            map.put(BigInteger.valueOf(1000), BigInteger.valueOf(1000));

            map.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isEqualTo(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is not equal to other")
        void test1() {
            Map<Object, Object> map = new HashMap<>();
            map.put(new Object(), new Object());
            map.put("alpha", "beta");
            map.put('b', 'c');
            map.put(3.14, 3.141592);

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEqualTo(expected))
                    .withMessageStartingWith("They are expected to be equal, but they aren't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @Test
        @DisplayName("passes, when actual is not equal to other")
        void test0() {
            Map<Object, Object> map = new HashMap<>();
            map.put(new Object(), new Object());
            map.put("alpha", "beta");
            map.put('b', 'c');
            map.put(3.14, 3.141592);

            map.forEach((actual, expected) -> assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is equal to other")
        void test1() {
            Map<Object, Object> map = new HashMap<>();
            map.put("alpha", String.valueOf("alpha".toCharArray()));
            map.put('\n', Character.valueOf('\n'));
            map.put(3.14, Double.valueOf(3.14));
            map.put(BigInteger.valueOf(1000), BigInteger.valueOf(1000));

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .withMessageStartingWith("They are expected to be not equal, but they aren't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isInstanceOf'")
    class IsInstanceOf {
        @Test
        @DisplayName("passes, when actual is instance of given type")
        void test0() {
            Map<Object, Class<?>> map = new HashMap<>();
            map.put(new Object(), Object.class);
            map.put("alpha", String.class);
            map.put('b', char.class);
            map.put('c', Character.class);
            map.put(3.14, double.class);
            map.put(3.141592, Double.class);

            map.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isInstanceOf(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is not instance of given type")
        void test1() {
            Map<Object, Class<?>> map = new HashMap<>();
            map.put("alpha", Character.class);
            map.put('\n', String.class);
            map.put(3.14, float.class);
            map.put(BigInteger.valueOf(1000), BigDecimal.class);

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isInstanceOf(expected))
                    .withMessageStartingWith("It is expected to be instance of the type, but it isn't."));
        }
    }

}
