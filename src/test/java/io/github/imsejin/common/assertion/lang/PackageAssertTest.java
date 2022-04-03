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

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.jar.JarFile;
import java.util.prefs.Preferences;
import java.util.regex.MatchResult;
import java.util.spi.ResourceBundleControlProvider;
import java.util.stream.Collector;
import java.util.zip.Checksum;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("PackageAssert")
class PackageAssertTest {

    @Nested
    @DisplayName("method 'isSuperPackageOf'")
    class IsSuperPackageOf {
        @ParameterizedTest
        @ValueSource(classes = {
                Future.class, Consumer.class, JarFile.class,
                Preferences.class, MatchResult.class, Collector.class,
                ResourceBundleControlProvider.class, Checksum.class
        })
        @DisplayName("passes, when actual is super package of given package")
        void test0(Class<?> type) {
            assertThatCode(() -> Asserts.that(List.class.getPackage()).isSuperPackageOf(type.getPackage()))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(classes = {
                Object.class, Future.class, Consumer.class, JarFile.class,
                Preferences.class, MatchResult.class, Collector.class,
                ResourceBundleControlProvider.class, Checksum.class
        })
        @DisplayName("throws exception, when actual is not super package of given package")
        void test1(Class<?> type) {
            assertThatCode(() -> Asserts.that(Object.class.getPackage()).isSuperPackageOf(type.getPackage()))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isSubPackageOf'")
    class IsSubPackageOf {
        @ParameterizedTest
        @ValueSource(classes = {
                Future.class, Consumer.class, JarFile.class,
                Preferences.class, MatchResult.class, Collector.class,
                ResourceBundleControlProvider.class, Checksum.class
        })
        @DisplayName("passes, when actual is sub package of given package")
        void test0(Class<?> type) {
            assertThatCode(() -> Asserts.that(type.getPackage()).isSubPackageOf(List.class.getPackage()))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(classes = {
                Object.class, Future.class, Consumer.class, JarFile.class,
                Preferences.class, MatchResult.class, Collector.class,
                ResourceBundleControlProvider.class, Checksum.class
        })
        @DisplayName("throws exception, when actual is not sub package of given package")
        void test1(Class<?> type) {
            assertThatCode(() -> Asserts.that(type.getPackage()).isSubPackageOf(Object.class.getPackage()))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'asName'")
    class AsName {
        @ParameterizedTest
        @ValueSource(classes = {
                BlockingQueue.class, Callable.class, Executor.class,
                ConcurrentMap.class, Future.class, TimeUnit.class,
        })
        @DisplayName("passes, when actual is equal to given package name")
        void test0(Class<?> type) {
            assertThatCode(() -> Asserts.that(type.getPackage()).asName().isEqualTo("java.util.concurrent"))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(classes = {
                BlockingQueue.class, Callable.class, Executor.class,
                ConcurrentMap.class, Future.class, TimeUnit.class,
        })
        @DisplayName("throws exception, when actual is not equal to given package name")
        void test1(Class<?> type) {
            assertThatCode(() -> Asserts.that(type.getPackage()).asName().startsWith("java.lang"))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

}
