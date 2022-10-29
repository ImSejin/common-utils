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
import io.github.imsejin.common.assertion.composition.EnumerationAssertable;
import io.github.imsejin.common.assertion.composition.IterationAssertable;
import io.github.imsejin.common.assertion.composition.RandomAccessIterationAssertable;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.tool.Stopwatch;
import io.github.imsejin.common.util.ArrayUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ArrayAssert")
class ArrayAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @Test
        @DisplayName("passes, when actual is equal to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[]{true, false})
                        .isEqualTo(new Boolean[]{true, false});
                Asserts.that(new byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE})
                        .isEqualTo(new Byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE});
                Asserts.that(new char[0])
                        .isEqualTo(new Character[0]);
                Asserts.that(new double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE})
                        .isEqualTo(new Double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE});
                Asserts.that(new float[]{})
                        .isEqualTo(new Float[]{});
                Asserts.that(new int[0])
                        .isEqualTo(new Integer[]{});
                Asserts.that(new long[]{})
                        .isEqualTo(new Long[0]);
                Asserts.that(new short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE})
                        .isEqualTo(new Short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE});
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .isEqualTo(new String[]{"io", "github", "imsejin", "common", "assertion", "lang"});
                Asserts.that(new Class<?>[][]{null, {Object.class}, {Class[].class}, {}, {Package.class}})
                        .isEqualTo(new Class<?>[][]{null, {Object.class}, {Class[].class}, {}, {Package.class}});
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not equal to other")
        void test1() {
            String message = Pattern.quote("They are expected to be equal, but they aren't.") +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .isEqualTo(new Boolean[]{true, false}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[0])
                    .isEqualTo(new Byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[0])
                    .isEqualTo(new Character[]{Character.MIN_VALUE, Character.MAX_VALUE / 2, Character.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[0])
                    .isEqualTo(new Double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[0])
                    .isEqualTo(new Float[]{Float.MIN_VALUE, -1.1F, 0.0F, 1.1F, Float.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[0])
                    .isEqualTo(new Integer[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[0])
                    .isEqualTo(new Long[]{Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[0])
                    .isEqualTo(new Short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[0])
                    .isEqualTo(new String[]{"io", "github", "imsejin", "common", "assertion", "array"}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Class<?>[1][1])
                    .isEqualTo(new Class<?>[][]{null, {Object.class}, {Class[].class}, {}, {Package.class}}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @Test
        @DisplayName("passes, when actual is not equal to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0])
                        .isNotEqualTo(new Boolean[]{true, false});
                Asserts.that(new byte[0])
                        .isNotEqualTo(new Byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE});
                Asserts.that(new char[0])
                        .isNotEqualTo(new Character[]{Character.MIN_VALUE, Character.MAX_VALUE / 2, Character.MAX_VALUE});
                Asserts.that(new double[0])
                        .isNotEqualTo(new Double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE});
                Asserts.that(new float[0])
                        .isNotEqualTo(new Float[]{Float.MIN_VALUE, -1.1F, 0.0F, 1.1F, Float.MAX_VALUE});
                Asserts.that(new int[0])
                        .isNotEqualTo(new Integer[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE});
                Asserts.that(new long[0])
                        .isNotEqualTo(new Long[]{Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE});
                Asserts.that(new short[0])
                        .isNotEqualTo(new Short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE});
                Asserts.that(new String[0])
                        .isNotEqualTo(new String[]{"io", "github", "imsejin", "common", "assertion", "array"});
                Asserts.that(new Class<?>[1][1])
                        .isNotEqualTo(new Class<?>[][]{null, {Object.class}, {Class[].class}, {}, {Package.class}});
            });
        }

        @Test
        @DisplayName("throws exception, when actual is equal to other")
        void test1() {
            String message = Pattern.quote("They are expected to be not equal, but they are.") +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[]{true, false})
                    .isNotEqualTo(new Boolean[]{true, false}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE})
                    .isNotEqualTo(new Byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[0])
                    .isNotEqualTo(new Character[0]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE})
                    .isNotEqualTo(new Double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[]{})
                    .isNotEqualTo(new Float[]{}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[0])
                    .isNotEqualTo(new Integer[]{}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[]{})
                    .isNotEqualTo(new Long[0]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE})
                    .isNotEqualTo(new Short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that("io.github.imsejin.common.assertion.array".split("\\."))
                    .isNotEqualTo(new String[]{"io", "github", "imsejin", "common", "assertion", "array"}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Class<?>[][]{null, {Object.class}, {Class[].class}, {}, {Package.class}})
                    .isNotEqualTo(new Class<?>[][]{null, {Object.class}, {Class[].class}, {}, {Package.class}}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).isEmpty();
                Asserts.that(new byte[0]).isEmpty();
                Asserts.that(new char[0]).isEmpty();
                Asserts.that(new double[0]).isEmpty();
                Asserts.that(new float[0]).isEmpty();
                Asserts.that(new int[0]).isEmpty();
                Asserts.that(new long[0]).isEmpty();
                Asserts.that(new short[0]).isEmpty();
                Asserts.that(new String[0]).isEmpty();
            });
        }

        @Test
        @DisplayName("throws exception, when actual has element")
        void test1() {
            // given
            List<char[]> list = Arrays.asList(new char[]{1, 2}, new char[]{'0', '1', '2'}, new char[]{' ', 'F'});

            // expect
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty())
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}actual\\.size: '[0-9]+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEmpty'")
    class IsNotEmpty {
        @Test
        @DisplayName("passes, when actual has element")
        void test0() {
            // given
            List<byte[]> list = Arrays.asList(new byte[]{1, 2}, new byte[]{'0', '1', '2'}, new byte[]{' ', 'F'});

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEmpty()));
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '0'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[0])
                    .isNotEmpty())
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSize'")
    class HasSize {
        @Test
        @DisplayName("passes, when actual has the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).hasSize(0);
                Asserts.that(new byte[1]).hasSize(1);
                Asserts.that(new char[2]).hasSize(2);
                Asserts.that(new double[4]).hasSize(4);
                Asserts.that(new float[8]).hasSize(8);
                Asserts.that(new int[16]).hasSize(16);
                Asserts.that(new long[32]).hasSize(32);
                Asserts.that(new short[64]).hasSize(64);
                Asserts.that(new String[128]).hasSize(128);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasSize(128))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .hasSize(64))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .hasSize(32))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .hasSize(16))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .hasSize(-1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .hasSize(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .hasSize(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .hasSize(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .hasSize(0))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSize()'")
    class DoesNotHaveSize {
        @Test
        @DisplayName("passes, when actual doesn't have the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).doesNotHaveSize(128);
                Asserts.that(new byte[1]).doesNotHaveSize(64);
                Asserts.that(new char[2]).doesNotHaveSize(32);
                Asserts.that(new double[4]).doesNotHaveSize(16);
                Asserts.that(new float[8]).doesNotHaveSize(-8);
                Asserts.that(new int[16]).doesNotHaveSize(4);
                Asserts.that(new long[32]).doesNotHaveSize(2);
                Asserts.that(new short[64]).doesNotHaveSize(1);
                Asserts.that(new String[128]).doesNotHaveSize(0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .doesNotHaveSize(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .doesNotHaveSize(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .doesNotHaveSize(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .doesNotHaveSize(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .doesNotHaveSize(8))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .doesNotHaveSize(16))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .doesNotHaveSize(32))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .doesNotHaveSize(64))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .doesNotHaveSize(128))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSameSizeAs'")
    class HasSameSizeAs {
        @Test
        @DisplayName("passes, when actual and other have the same size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).hasSameSizeAs(new Object[0]);
                Asserts.that(new byte[1]).hasSameSizeAs(new StringBuilder[1]);
                Asserts.that(new char[2]).hasSameSizeAs(new Character[2]);
                Asserts.that(new double[4]).hasSameSizeAs(new Double[4]);
                Asserts.that(new float[8]).hasSameSizeAs(new Field[8]);
                Asserts.that(new int[16]).hasSameSizeAs(new Constructor[16]);
                Asserts.that(new long[32]).hasSameSizeAs(new Class<?>[32]);
                Asserts.that(new short[64]).hasSameSizeAs(new Stopwatch[64]);
                Asserts.that(new String[128]).hasSameSizeAs(new String[128]);
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have a difference with size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '(\\[.*]|null)'" +
                    "\n {4}expected\\.size: '([0-9]+|null)'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasSameSizeAs(new Object[1]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .hasSameSizeAs(new StringBuilder[2]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .hasSameSizeAs(new Character[3]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .hasSameSizeAs(new Double[3]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .hasSameSizeAs(new Field[10]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .hasSameSizeAs(new Constructor[32]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .hasSameSizeAs(new Class<?>[5]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .hasSameSizeAs(new Array[128]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .hasSameSizeAs(new String[256]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .hasSameSizeAs(null))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSameSizeAs'")
    class DoesNotHaveSameSizeAs {
        @Test
        @DisplayName("passes, when actual and other have a difference with size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).doesNotHaveSameSizeAs(new Object[1]);
                Asserts.that(new byte[1]).doesNotHaveSameSizeAs(new StringBuilder[2]);
                Asserts.that(new char[2]).doesNotHaveSameSizeAs(new Character[3]);
                Asserts.that(new double[4]).doesNotHaveSameSizeAs(new Double[3]);
                Asserts.that(new float[8]).doesNotHaveSameSizeAs(new Field[10]);
                Asserts.that(new int[16]).doesNotHaveSameSizeAs(new Constructor[32]);
                Asserts.that(new long[32]).doesNotHaveSameSizeAs(new Class<?>[5]);
                Asserts.that(new short[64]).doesNotHaveSameSizeAs(new Array[128]);
                Asserts.that(new String[128]).doesNotHaveSameSizeAs(new String[256]);
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '(\\[.*]|null)'" +
                    "\n {4}expected\\.size: '([0-9]+|null)'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .doesNotHaveSameSizeAs(new Object[0]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .doesNotHaveSameSizeAs(new StringBuilder[1]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .doesNotHaveSameSizeAs(new Character[2]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .doesNotHaveSameSizeAs(new Double[4]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .doesNotHaveSameSizeAs(new Field[8]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .doesNotHaveSameSizeAs(new Constructor[16]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .doesNotHaveSameSizeAs(new Class<?>[32]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .doesNotHaveSameSizeAs(new Stopwatch[64]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .doesNotHaveSameSizeAs(new String[128]))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .doesNotHaveSameSizeAs(null))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeGreaterThan'")
    class HasSizeGreaterThan {
        @Test
        @DisplayName("passes, when actual has size greater than the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).hasSizeGreaterThan(-1);
                Asserts.that(new byte[1]).hasSizeGreaterThan(0);
                Asserts.that(new char[2]).hasSizeGreaterThan(1);
                Asserts.that(new double[4]).hasSizeGreaterThan(2);
                Asserts.that(new float[8]).hasSizeGreaterThan(4);
                Asserts.that(new int[16]).hasSizeGreaterThan(8);
                Asserts.that(new long[32]).hasSizeGreaterThan(16);
                Asserts.that(new short[64]).hasSizeGreaterThan(32);
                Asserts.that(new String[128]).hasSizeGreaterThan(64);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size less than or same as the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasSizeGreaterThan(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .hasSizeGreaterThan(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .hasSizeGreaterThan(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .hasSizeGreaterThan(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .hasSizeGreaterThan(8))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .hasSizeGreaterThan(16))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .hasSizeGreaterThan(32))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .hasSizeGreaterThan(128))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .hasSizeGreaterThan(256))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .hasSizeGreaterThan(512))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeGreaterThanOrEqualTo'")
    class HasSizeGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual has size greater than or same as the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).hasSizeGreaterThanOrEqualTo(0);
                Asserts.that(new byte[1]).hasSizeGreaterThanOrEqualTo(1);
                Asserts.that(new char[2]).hasSizeGreaterThanOrEqualTo(2);
                Asserts.that(new double[4]).hasSizeGreaterThanOrEqualTo(4);
                Asserts.that(new float[8]).hasSizeGreaterThanOrEqualTo(8);
                Asserts.that(new int[16]).hasSizeGreaterThanOrEqualTo(16);
                Asserts.that(new long[32]).hasSizeGreaterThanOrEqualTo(32);
                Asserts.that(new short[64]).hasSizeGreaterThanOrEqualTo(63);
                Asserts.that(new String[128]).hasSizeGreaterThanOrEqualTo(127);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size less than the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN_OR_EQUAL_TO) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasSizeGreaterThanOrEqualTo(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .hasSizeGreaterThanOrEqualTo(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .hasSizeGreaterThanOrEqualTo(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .hasSizeGreaterThanOrEqualTo(8))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .hasSizeGreaterThanOrEqualTo(16))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .hasSizeGreaterThanOrEqualTo(32))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .hasSizeGreaterThanOrEqualTo(64))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .hasSizeGreaterThanOrEqualTo(128))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .hasSizeGreaterThanOrEqualTo(256))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .hasSizeGreaterThanOrEqualTo(512))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeLessThan'")
    class HasSizeLessThan {
        @Test
        @DisplayName("passes, when actual has size less than the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).hasSizeLessThan(1);
                Asserts.that(new byte[1]).hasSizeLessThan(2);
                Asserts.that(new char[2]).hasSizeLessThan(4);
                Asserts.that(new double[4]).hasSizeLessThan(8);
                Asserts.that(new float[8]).hasSizeLessThan(16);
                Asserts.that(new int[16]).hasSizeLessThan(32);
                Asserts.that(new long[32]).hasSizeLessThan(64);
                Asserts.that(new short[64]).hasSizeLessThan(128);
                Asserts.that(new String[128]).hasSizeLessThan(256);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size greater than or same as the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasSizeLessThan(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .hasSizeLessThan(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .hasSizeLessThan(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .hasSizeLessThan(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .hasSizeLessThan(8))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .hasSizeLessThan(16))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .hasSizeLessThan(32))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .hasSizeLessThan(63))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .hasSizeLessThan(127))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .hasSizeLessThan(255))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeLessThanOrEqualTo'")
    class HasSizeLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual has size less than or same as the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).hasSizeLessThanOrEqualTo(0);
                Asserts.that(new byte[1]).hasSizeLessThanOrEqualTo(1);
                Asserts.that(new char[2]).hasSizeLessThanOrEqualTo(2);
                Asserts.that(new double[4]).hasSizeLessThanOrEqualTo(4);
                Asserts.that(new float[8]).hasSizeLessThanOrEqualTo(8);
                Asserts.that(new int[16]).hasSizeLessThanOrEqualTo(16);
                Asserts.that(new long[32]).hasSizeLessThanOrEqualTo(32);
                Asserts.that(new short[64]).hasSizeLessThanOrEqualTo(128);
                Asserts.that(new String[128]).hasSizeLessThanOrEqualTo(256);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size greater than the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN_OR_EQUAL_TO) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasSizeLessThanOrEqualTo(-1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .hasSizeLessThanOrEqualTo(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .hasSizeLessThanOrEqualTo(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .hasSizeLessThanOrEqualTo(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .hasSizeLessThanOrEqualTo(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .hasSizeLessThanOrEqualTo(8))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .hasSizeLessThanOrEqualTo(16))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .hasSizeLessThanOrEqualTo(32))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .hasSizeLessThanOrEqualTo(64))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .hasSizeLessThanOrEqualTo(128))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'contains'")
    class Contains {
        @Test
        @DisplayName("passes, when actual contains the given element")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'}).contains('d');
                Asserts.that(new int[]{-1024, -1, 0, 1, 1024}).contains(1024);
                Asserts.that(getClass().getPackage().getName().split("\\.")).contains("imsejin");
                Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                        .contains(new String[]{"n"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given element")
        void test1() {
            String message = Pattern.quote(EnumerationAssertable.DEFAULT_DESCRIPTION_CONTAINS) +
                    "\n {4}actual: '\\[.+]'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'})
                    .contains('f'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                    .contains(1023))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .contains(""))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                    .contains(new String[]{"i"}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotContain'")
    class DoesNotContain {
        @Test
        @DisplayName("passes, when actual doesn't contain the given element")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0]).doesNotContain(null);
                Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'}).doesNotContain('f');
                Asserts.that(new int[]{-1024, -1, 0, 1, 1024}).doesNotContain(1023);
                Asserts.that(getClass().getPackage().getName().split("\\.")).doesNotContain("");
                Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                        .doesNotContain(new String[]{"i"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual contains the given element")
        void test1() {
            String message = Pattern.quote(EnumerationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN) +
                    "\n {4}actual: '\\[.+]'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'})
                    .doesNotContain('d'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                    .doesNotContain(1024))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .doesNotContain("imsejin"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                    .doesNotContain(new String[]{"n"}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsNull'")
    class ContainsNull {
        @Test
        @DisplayName("passes, when actual contains null")
        void test0() {
            // given
            List<Object[]> list = Arrays.asList(
                    new Boolean[]{true, null, false},
                    new Byte[]{23, null, -54},
                    new Character[]{'0', ' ', null, 'i'},
                    new Double[]{null, 3.14, 0.0},
                    new Float[]{1.141F, 0.0F, null},
                    new Integer[]{0, null, -10},
                    new Long[]{-128L, 64L, null},
                    new Short[]{null, 2048, -4096},
                    new String[]{"", null, "alpha"}
            );

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).containsNull()));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain null")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_NULL) +
                    "\n {4}actual: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[0])
                    .containsNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[]{"Alpha", "null", "", "BETA", "gamma"})
                    .containsNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Character[]{'A', Character.MIN_VALUE, 'b', '0'})
                    .containsNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Integer[]{0, Integer.MIN_VALUE, Integer.MAX_VALUE})
                    .containsNull())
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotContainNull'")
    class DoesNotContainNull {
        @Test
        @DisplayName("passes, when actual doesn't contain null")
        void test0() {
            // given
            List<Object[]> list = Arrays.asList(
                    new Object[0],
                    new String[]{"Alpha", "null", "", "BETA", "gamma"},
                    new Character[]{'A', Character.MIN_VALUE, 'b', '0'},
                    new Integer[]{0, Integer.MIN_VALUE, Integer.MAX_VALUE}
            );

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).doesNotContainNull()));
        }

        @Test
        @DisplayName("throws exception, when actual contains null")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL) +
                    "\n {4}actual: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Boolean[]{true, null, false})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Byte[]{23, null, -54})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Character[]{'0', ' ', null, 'i'})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Double[]{null, 3.14, 0.0})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Float[]{1.141F, 0.0F, null})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Integer[]{0, null, -10})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Long[]{-128L, 64L, null})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Short[]{null, 2048, -4096})
                    .doesNotContainNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[]{"", null, "alpha"})
                    .doesNotContainNull())
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsAny'")
    class ContainsAny {
        @Test
        @DisplayName("passes, when actual contains the given elements at least 1")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0])
                        .containsAny();
                Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                        .containsAny(null, 1023, -1);
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .containsAny("java", "util", "concurrent", "atomic", "lang", "reflect", "common");
                Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                        .containsAny(new String[]{"c"}, new String[]{"n"}, new String[]{"ut"}, new String[]{"ti", "s"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ANY) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[0])
                    .containsAny('a'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Integer[]{1, 2, 3})
                    .containsAny())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'})
                    .containsAny(null, '\u0000'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                    .containsAny(10))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .containsAny("java", "util", "concurrent", "atomic", "package", "reflect"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                    .containsAny(new String[]{"c"}, new String[]{"n", "ut"}, new String[]{"tie"}, new String[]{"s"}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsAll'")
    class ContainsAll {
        @Test
        @DisplayName("passes, when actual contains all the given elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0])
                        .containsAll(new Object[0]);
                Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'})
                        .containsAll(new Character[]{'e', 'd', 'c', 'b', 'a'});
                Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                        .containsAll(new Integer[0]);
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .containsAll(new String[]{"imsejin", "github", "common", "assertion", "lang"});
                Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                        .containsAll(new String[][]{{"n"}, {"ut", "i", "li"}, {"tie", "s"}});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ALL) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}missing: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[0])
                    .containsAll(new Character[]{'a', 'b', 'c', 'd', 'e'}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'})
                    .containsAll(new Character[]{'e', 'd', 'c', 'f', 'a'}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                    .containsAll(new Integer[]{-1024, -1, 0, 1, 1023}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .containsAll(new String[]{"IMSEJIN", "GITHUB", "COMMON", "ARRAY", "ASSERTION", "IO"}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                    .containsAll(new String[][]{{"n"}, {"ut", "li"}, {"tie", "s"}}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotContainAll'")
    class DoesNotContainAll {
        @Test
        @DisplayName("passes, when actual doesn't contain all the given elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new char[0])
                        .doesNotContainAll(new Character[]{'a', 'b', 'c', 'd', 'e'});
                Asserts.that(new char[]{'z', 'y', 'x', 'w', 'v'})
                        .doesNotContainAll(new Character[0]);
                Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                        .doesNotContainAll(new Integer[]{-512, -64, 8, 32, 128});
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .doesNotContainAll(new String[]{"IMSEJIN", "GITHUB", "COMMON", "ARRAY", "ASSERTION", "IO"});
                Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                        .doesNotContainAll(new String[][]{{}, {"ut", "li"}, {"c", "tie", "s"}});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_ALL) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}included: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'})
                    .doesNotContainAll(new Character[]{'z', 'y', 'x', 'w', 'a'}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                    .doesNotContainAll(new Integer[]{1}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .doesNotContainAll(new String[]{"imsejin", "github", "common", "assertion", "lang"}))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                    .doesNotContainAll(new String[][]{{"n"}, {"ut", "i", "li"}, {"tie", "s"}}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsOnly'")
    class ContainsOnly {
        @Test
        @DisplayName("passes, when actual contains only the given element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0])
                        .containsOnly();
                Asserts.that(new char[]{'z', 'y', 'x', 'w', 'v'})
                        .containsOnly('v', 'w', 'x', 'y', 'z');
                Asserts.that(new int[]{1, 2, 3, 1, 2, 3, 1, 2, 3})
                        .containsOnly(1, 2, 3);
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .containsOnly("io", "github", "imsejin", "common", "assertion", "lang");
                Asserts.that(new String[][]{{}, {"alpha"}, {}, {"beta"}, null})
                        .containsOnly(new String[][]{{}, {"alpha"}, null, {"beta"}});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain only the given element(s)")
        void test1() {
            String missingMessage = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_MISSING) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}missing: '.+'";
            String unexpectedMessage = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_UNEXPECTED) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}unexpected: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Number[0])
                    .containsOnly(-1, BigDecimal.ZERO, 2.5))
                    .withMessageMatching(missingMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[]{-1, 0, 1})
                    .containsOnly(-1024, -1, 0, 1, 1024))
                    .withMessageMatching(missingMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Number[]{-1, BigDecimal.ZERO, 2.5})
                    .containsOnly())
                    .withMessageMatching(unexpectedMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .containsOnly("imsejin", "github", "common", "assertion", "lang"))
                    .withMessageMatching(unexpectedMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{}, {"alpha"}, null, {"beta"}})
                    .containsOnly(new String[][]{{}, {"alpha"}, {"beta"}}))
                    .withMessageMatching(unexpectedMessage);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsOnlyNulls'")
    class ContainsOnlyNulls {
        @Test
        @DisplayName("passes, when actual contains only null element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[]{null}).containsOnlyNulls();
                Asserts.that(new String[]{null, null}).containsOnlyNulls();
                Asserts.that(new int[][]{null, null, null}).containsOnlyNulls();
                Asserts.that(new char[][][]{null, null, null, null}).containsOnlyNulls();
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain only null element(s)")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS) +
                    "\n {4}actual: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[0])
                    .containsOnlyNulls())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[]{-1, 0, 1})
                    .containsOnlyNulls())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .containsOnlyNulls())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{}, {"alpha"}, null, {"beta"}})
                    .containsOnlyNulls())
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveDuplicates'")
    class DoesNotHaveDuplicates {
        @Test
        @DisplayName("passes, when actual doesn't have duplicated elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0]).doesNotHaveDuplicates();
                Asserts.that(new Object[1]).doesNotHaveDuplicates();
                Asserts.that(new char[]{'a', 'b', 'c', 'd'}).doesNotHaveDuplicates();
                Asserts.that(new String[]{null, "", " ", "null", "undefined"}).doesNotHaveDuplicates();
                Asserts.that(new int[][]{{}, null, {1, 2}, {1, 1}, {2, 1}, {2}}).doesNotHaveDuplicates();
            });
        }

        @Test
        @DisplayName("throws exception, when actual has duplicated elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_DUPLICATES) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}duplicated: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[2])
                    .doesNotHaveDuplicates())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', '0', 'c', '0'})
                    .doesNotHaveDuplicates())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split(""))
                    .doesNotHaveDuplicates())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[][]{{}, null, {1, 2}, {1, 1}, {1, 2}, {2}})
                    .doesNotHaveDuplicates())
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'anyMatch'")
    class AnyMatch {
        @Test
        @DisplayName("passes, when actual matches the given condition with its any elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[]{new Object()})
                        .anyMatch(Objects::nonNull);
                Asserts.that(IntStream.rangeClosed(0, 100).toArray())
                        .anyMatch(it -> it == 100);
                Asserts.that(new char[]{'a', '1', 'b', 'c'})
                        .anyMatch(Character::isDigit);
                Asserts.that(new String[]{"ImSejin", "Github", "common", "asSErtIon", "lAnG"})
                        .anyMatch(it -> it.chars().allMatch(Character::isLowerCase));
                Asserts.that(new String[][]{{null}, {"alpha"}, {}, {"beta"}, {"gamma"}})
                        .anyMatch(ArrayUtils::isNullOrEmpty);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't match the given condition with its any elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_ANY_MATCH) +
                    "\n {4}actual: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[0])
                    .anyMatch(Objects::nonNull))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IntStream.range(0, 100).toArray())
                    .anyMatch(it -> it == 100))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', '1', 'b', 'c'})
                    .anyMatch(Character::isUpperCase))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[]{"ImSejin", "Github", "coMMoN", "asSErtIon", "lAnG"})
                    .anyMatch(it -> it.chars().allMatch(Character::isLowerCase)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{{null}, {"alpha"}, {"beta"}, {"gamma"}})
                    .anyMatch(ArrayUtils::isNullOrEmpty))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'allMatch'")
    class AllMatch {
        @Test
        @DisplayName("passes, when actual matches the given condition with its all elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[]{new Object()})
                        .allMatch(Objects::nonNull);
                Asserts.that(IntStream.range(0, 100).toArray())
                        .allMatch(it -> it < 100);
                Asserts.that(new char[]{'a', 'b', 'c', 'd'})
                        .allMatch(Character::isLowerCase);
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .allMatch(it -> it.chars().allMatch(Character::isLowerCase));
                Asserts.that(new String[][]{{null}, {"alpha"}, {"beta"}, {"gamma"}})
                        .allMatch(ArrayUtils::exists);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't match the given condition with its all elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}unmatched: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[0])
                    .allMatch(Objects::nonNull))
                    .withMessageMatching(Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH) +
                            "\n {4}actual: '\\[.*]'");
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IntStream.rangeClosed(0, 100).toArray())
                    .allMatch(it -> it < 100))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'C', 'd'})
                    .allMatch(Character::isLowerCase))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .allMatch(it -> it.chars().allMatch(Character::isUpperCase)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{null, {"alpha"}, {}, {"beta"}})
                    .allMatch(ArrayUtils::isNullOrEmpty))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'noneMatch'")
    class NoneMatch {
        @Test
        @DisplayName("passes, when actual doesn't match the given condition with its all elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0])
                        .noneMatch(Objects::nonNull);
                Asserts.that(IntStream.range(0, 100).toArray())
                        .noneMatch(it -> it == 100);
                Asserts.that(new char[]{'a', 'b', 'c', 'd'})
                        .noneMatch(Character::isUpperCase);
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .noneMatch(it -> it.chars().anyMatch(Character::isUpperCase));
                Asserts.that(new String[][]{{null}, {"alpha"}, {"beta"}, {"gamma"}})
                        .noneMatch(ArrayUtils::isNullOrEmpty);
            });
        }

        @Test
        @DisplayName("throws exception, when actual matches the given condition with its any elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_NONE_MATCH) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}matched: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[]{null, new Object(), null})
                    .noneMatch(Objects::isNull))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IntStream.rangeClosed(0, 100).toArray())
                    .noneMatch(it -> it == 100))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'C', 'd'})
                    .noneMatch(Character::isLowerCase))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .noneMatch(it -> it.chars().allMatch(Character::isLowerCase)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{null, {"alpha"}, {}, {"beta"}})
                    .noneMatch(ArrayUtils::isNullOrEmpty))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'startsWith'")
    class StartsWith {
        @Test
        @DisplayName("passes, when actual starts with the given element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0])
                        .startsWith((Object[]) null);
                Asserts.that((Object[]) null)
                        .startsWith();
                Asserts.that(IntStream.range(0, 10).toArray())
                        .startsWith(0);
                Asserts.that(new char[]{'a', 'b', 'c', 'd'})
                        .startsWith('a', 'b', 'c', 'd');
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .startsWith("io.github.imsejin.common".split("\\."));
                Asserts.that(new String[][]{{null}, {"alpha"}, {"beta"}, {"gamma"}})
                        .startsWith(new String[]{null}, new String[]{"alpha"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't start with the given element(s)")
        void test1() {
            String message = Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH_UNEXPECTED_ELEMENT) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}unexpected: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IntStream.range(0, 100).toArray())
                    .startsWith(IntStream.rangeClosed(0, 100).boxed().toArray(Integer[]::new)))
                    .withMessageMatching(Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH_OVER_SIZE) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '\\[.*]'" +
                            "\n {4}expected\\.size: '[0-9]+'");
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[]{null, new Object(), null})
                    .startsWith(null, new Object()))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'C', 'd'})
                    .startsWith('a', 'b', 'c', 'd'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .startsWith("io", "github", "common", "imsejin"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{null, {"alpha"}, {}, {"beta"}})
                    .startsWith(new String[]{null}, new String[]{}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'endsWith'")
    class EndsWith {
        @Test
        @DisplayName("passes, when actual ends with the given element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Object[0])
                        .endsWith((Object[]) null);
                Asserts.that((Object[]) null)
                        .endsWith();
                Asserts.that(IntStream.range(0, 10).toArray())
                        .endsWith(9);
                Asserts.that(new char[]{'a', 'b', 'c', 'd'})
                        .endsWith('a', 'b', 'c', 'd');
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .endsWith("assertion", "lang");
                Asserts.that(new String[][]{{null}, {"alpha"}, {"beta"}, {"gamma"}})
                        .endsWith(new String[]{"beta"}, new String[]{"gamma"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't end with the given element(s)")
        void test1() {
            String message = Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH_UNEXPECTED_ELEMENT) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}unexpected: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IntStream.range(0, 100).toArray())
                    .endsWith(IntStream.rangeClosed(0, 100).boxed().toArray(Integer[]::new)))
                    .withMessageMatching(Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH_OVER_SIZE) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '\\[.*]'" +
                            "\n {4}expected\\.size: '[0-9]+'");
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[]{null, new Object(), null})
                    .endsWith(new Object(), null))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'C', 'd'})
                    .endsWith('a', 'b', 'c', 'd'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                    .endsWith("lang", "assertion"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[][]{null, {"alpha"}, {}, {"beta"}})
                    .endsWith(new String[]{"alpha"}, new String[]{"beta"}))
                    .withMessageMatching(message);
        }
    }

}
