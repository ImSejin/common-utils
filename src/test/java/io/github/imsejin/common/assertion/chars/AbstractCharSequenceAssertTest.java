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

package io.github.imsejin.common.assertion.chars;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AbstractCharSequenceAssert")
class AbstractCharSequenceAssertTest {

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0() {
            List<? extends CharSequence> list = Arrays.asList(new StringBuilder(), new StringBuffer(10), "");

            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty()));
        }

        @Test
        @DisplayName("throws exception, when actual is not empty")
        void test1() {
            List<? extends CharSequence> list = Arrays.asList(new StringBuilder(" "), new StringBuffer("alpha"), " ");

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty())
                    .withMessageStartingWith("It is expected to be empty, but it isn't."));
        }
    }

    @Nested
    @DisplayName("method 'isNotEmpty'")
    class IsNotEmpty {
        @Test
        @DisplayName("passes, when actual is not empty")
        void test0() {
            List<? extends CharSequence> list = Arrays.asList(
                    new StringBuilder(" "), new StringBuffer("string"), " ", "\n\r\t");

            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEmpty()));
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            List<? extends CharSequence> list = Arrays.asList(new StringBuilder(), new StringBuffer(10), "");

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEmpty())
                    .withMessageStartingWith("It is expected to be not empty, but it isn't."));
        }
    }

    @Nested
    @DisplayName("method 'hasLengthOf'")
    class HasLengthOf {
        @Test
        @DisplayName("passes, when actual has the given length")
        void test0() {
            Map<CharSequence, Integer> map = new HashMap<>();
            map.put(new StringBuilder("imsejin"), new StringBuilder("imsejin").length());
            map.put(UUID.randomUUID().toString().replace("-", ""), 32);
            map.put(new StringBuffer("java"), new StringBuffer("la").append("ng").length());

            map.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).hasLengthOf(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given length")
        void test1() {
            Map<CharSequence, Integer> map = new HashMap<>();
            map.put(new StringBuilder("imsejin"), new StringBuilder("sejin").length());
            map.put(UUID.randomUUID().toString(), 32);
            map.put(new StringBuffer("java"), new StringBuffer("la").append("ng").deleteCharAt(3).length());

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).hasLengthOf(expected))
                    .withMessageStartingWith("It is expected to have the same length, but it isn't."));
        }
    }

    @Nested
    @DisplayName("method 'isSameLength'")
    class IsSameLength {
        @Test
        @DisplayName("passes, when length of the actual is equal to length of the given")
        void test0() {
            Map<CharSequence, CharSequence> map = new HashMap<>();
            map.put(new StringBuilder("imsejin"), new StringBuilder("imsejin"));
            map.put(UUID.randomUUID().toString().replace("-", ""), StringUtils.repeat('.', 32));
            map.put(new StringBuffer("java"), new StringBuffer("la").append("ng"));

            map.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isSameLength(expected)));
        }

        @Test
        @DisplayName("throws exception, when length of the actual is not equal to length of the given")
        void test1() {
            Map<CharSequence, CharSequence> map = new HashMap<>();
            map.put(new StringBuilder("imsejin"), new StringBuilder("sejin"));
            map.put(UUID.randomUUID().toString(), StringUtils.repeat('.', 32));
            map.put(new StringBuffer("java"), new StringBuffer("la").append("ng").deleteCharAt(3));

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isSameLength(expected))
                    .withMessageStartingWith("It is expected to have the same length, but it isn't."));
        }
    }

    @Nested
    @DisplayName("method 'isNotSameLength'")
    class IsNotSameLength {
        @Test
        @DisplayName("passes, when length of the actual is not equal to length of the given")
        void test0() {
            Map<CharSequence, CharSequence> map = new HashMap<>();
            map.put(new StringBuilder("imsejin"), new StringBuilder("sejin"));
            map.put(UUID.randomUUID().toString(), StringUtils.repeat('.', 32));
            map.put(new StringBuffer("java"), new StringBuffer("la").append("ng").deleteCharAt(3));

            map.forEach((actual, expected) -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotSameLength(expected)));
        }

        @Test
        @DisplayName("throws exception, when length of the actual is equal to length of the given")
        void test1() {
            Map<CharSequence, CharSequence> map = new HashMap<>();
            map.put(new StringBuilder("imsejin"), new StringBuilder("imsejin"));
            map.put(UUID.randomUUID().toString().replace("-", ""), StringUtils.repeat('.', 32));
            map.put(new StringBuffer("java"), new StringBuffer("la").append("ng"));

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotSameLength(expected))
                    .withMessageStartingWith("It is expected not to have the same length, but it is."));
        }
    }

}
