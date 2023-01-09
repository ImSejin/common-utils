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

import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.github.imsejin.common.assertion.Asserts;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PackageAssert")
class PackageAssertTest {

    @Nested
    @DisplayName("method 'isSuperPackageOf'")
    class IsSuperPackageOf {
        @ParameterizedTest
        @CsvSource(value = {
                "java.lang            | java.lang.reflect",
                "java.util            | java.util.stream",
                "java.util.concurrent | java.util.concurrent.atomic",
                "java.time            | java.time.chrono",
        }, delimiter = '|')
        @DisplayName("passes, when actual is super package of given package")
        void test0(String actual, String expected) {
            // given
            Package pack = Package.getPackage(actual);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(pack)
                    .isSuperPackageOf(Package.getPackage(expected)));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "java.lang            | java",
                "java.util            | java.util.concurrent.atomic",
                "java.util.stream     | java.util",
                "java.util.concurrent | java.util.concurrent",
                "java.time.chrono     | java.time",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not super package of given package")
        void test1(String actual, String expected) {
            // given
            Package pack = Package.getPackage(actual);

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(pack)
                            .isSuperPackageOf(Package.getPackage(expected)))
                    .withMessageMatching(
                            Pattern.quote("It is expected to be super-package of the given one, but it isn't.") +
                                    "\n {4}actual: 'package [a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*.*'" +
                                    "\n {4}expected: '(null|package [a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*.*)'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isSubPackageOf'")
    class IsSubPackageOf {
        @ParameterizedTest
        @CsvSource(value = {
                "java.lang.reflect           | java.lang",
                "java.util.stream            | java.util",
                "java.util.concurrent.atomic | java.util.concurrent",
                "java.time.chrono            | java.time",
        }, delimiter = '|')
        @DisplayName("passes, when actual is sub-package of given package")
        void test0(String actual, String expected) {
            // given
            Package pack = Package.getPackage(actual);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(pack)
                    .isSubPackageOf(Package.getPackage(expected)));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "java.lang                   | java",
                "java.util.stream            | java.lang",
                "java.util.concurrent.atomic | java.util.concurrent.locks",
                "java.time.chrono            | java.time.chrono",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not sub-package of given package")
        void test1(String actual, String expected) {
            // given
            Package pack = Package.getPackage(actual);

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(pack)
                            .isSubPackageOf(Package.getPackage(expected)))
                    .withMessageMatching(
                            Pattern.quote("It is expected to be sub-package of the given one, but it isn't.") +
                                    "\n {4}actual: 'package [a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*.*'" +
                                    "\n {4}expected: '(null|package [a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*.*)'");
        }
    }

}
