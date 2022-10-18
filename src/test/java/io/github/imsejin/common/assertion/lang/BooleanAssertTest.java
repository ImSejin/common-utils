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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("BooleanAssert")
class BooleanAssertTest {

    @Nested
    @DisplayName("method 'isTrue'")
    class IsTrue {
        @ParameterizedTest
        @ValueSource(booleans = true)
        @DisplayName("passes, when actual is true")
        void test0(boolean actual) {
            assertThatCode(() -> Asserts.that(actual).isTrue())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(booleans = false)
        @DisplayName("throws exception, when actual is false")
        void test1(boolean actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isTrue())
                    .withMessageMatching(Pattern.quote("It is expected to be true, but it isn't.") +
                            "\n {4}actual: 'false'" +
                            "\n {4}expected: 'true'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isFalse'")
    class IsFalse {
        @ParameterizedTest
        @ValueSource(booleans = false)
        @DisplayName("passes, when actual is false")
        void test0(boolean actual) {
            assertThatCode(() -> Asserts.that(actual).isFalse())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(booleans = true)
        @DisplayName("throws exception, when actual is true")
        void test1(boolean actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isFalse())
                    .withMessageMatching(Pattern.quote("It is expected to be false, but it isn't.") +
                            "\n {4}actual: 'true'" +
                            "\n {4}expected: 'false'");
        }
    }

}
