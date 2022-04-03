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
import io.github.imsejin.common.tool.Stopwatch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

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
                Asserts.that("io.github.imsejin.common.assertion.array".split("\\."))
                        .isEqualTo(new String[]{"io", "github", "imsejin", "common", "assertion", "array"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not equal to other")
        void test1() {
            String description = "They are expected to be equal, but they aren't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new boolean[0])
                            .isEqualTo(new Boolean[]{true, false}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new byte[0])
                            .isEqualTo(new Byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new char[0])
                            .isEqualTo(new Character[]{Character.MIN_VALUE, Character.MAX_VALUE / 2, Character.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new double[0])
                            .isEqualTo(new Double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new float[0])
                            .isEqualTo(new Float[]{Float.MIN_VALUE, -1.1F, 0.0F, 1.1F, Float.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new int[0])
                            .isEqualTo(new Integer[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new long[0])
                            .isEqualTo(new Long[]{Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new short[0])
                            .isEqualTo(new Short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new String[0])
                            .isEqualTo(new String[]{"io", "github", "imsejin", "common", "assertion", "array"}))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
            });
        }

        @Test
        @DisplayName("throws exception, when actual is equal to other")
        void test1() {
            String description = "They are expected to be not equal, but they are.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new boolean[]{true, false})
                            .isNotEqualTo(new Boolean[]{true, false}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE})
                            .isNotEqualTo(new Byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new char[0])
                            .isNotEqualTo(new Character[0]))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE})
                            .isNotEqualTo(new Double[]{Double.MIN_VALUE, -1.1, 0.0, 1.1, Double.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new float[]{})
                            .isNotEqualTo(new Float[]{}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new int[0])
                            .isNotEqualTo(new Integer[]{}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new long[]{})
                            .isNotEqualTo(new Long[0]))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE})
                            .isNotEqualTo(new Short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that("io.github.imsejin.common.assertion.array".split("\\."))
                            .isNotEqualTo(new String[]{"io", "github", "imsejin", "common", "assertion", "array"}))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty())
                    .withMessageStartingWith("It is expected to be empty, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasElement'")
    class HasElement {
        @Test
        @DisplayName("passes, when actual has element")
        void test0() {
            // given
            List<byte[]> list = Arrays.asList(new byte[]{1, 2}, new byte[]{'0', '1', '2'}, new byte[]{' ', 'F'});

            // except
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).hasElement()));
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            String description = "It is expected to have element, but it isn't.";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[0])
                    .hasElement()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[0])
                    .hasElement()).withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).doesNotContainNull()));
        }

        @Test
        @DisplayName("throws exception, when actual contains null")
        void test1() {
            String description = "It is expected not to contain null, but it isn't.";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Boolean[]{true, null, false})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Byte[]{23, null, -54})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Character[]{'0', ' ', null, 'i'})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Double[]{null, 3.14, 0.0})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Float[]{1.141F, 0.0F, null})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Integer[]{0, null, -10})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Long[]{-128L, 64L, null})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Short[]{null, 2048, -4096})
                    .doesNotContainNull()).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[]{"", null, "alpha"})
                    .doesNotContainNull()).withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasLengthOf'")
    class HasLengthOf {
        @Test
        @DisplayName("passes, when actual has the given length")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).hasLengthOf(0);
                Asserts.that(new byte[1]).hasLengthOf(1);
                Asserts.that(new char[2]).hasLengthOf(2);
                Asserts.that(new double[4]).hasLengthOf(4);
                Asserts.that(new float[8]).hasLengthOf(8);
                Asserts.that(new int[16]).hasLengthOf(16);
                Asserts.that(new long[32]).hasLengthOf(32);
                Asserts.that(new short[64]).hasLengthOf(64);
                Asserts.that(new String[128]).hasLengthOf(128);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given length")
        void test1() {
            String description = "It is expected to be the same length, but it isn't.";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .hasLengthOf(128)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .hasLengthOf(64)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .hasLengthOf(32)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .hasLengthOf(16)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .hasLengthOf(-1)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .hasLengthOf(4)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .hasLengthOf(2)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .hasLengthOf(1)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .hasLengthOf(0)).withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isSameLength'")
    class IsSameLength {
        @Test
        @DisplayName("passes, when actual and other have the same length")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).isSameLength(new Object[0]);
                Asserts.that(new byte[1]).isSameLength(new StringBuilder[1]);
                Asserts.that(new char[2]).isSameLength(new Character[2]);
                Asserts.that(new double[4]).isSameLength(new Double[4]);
                Asserts.that(new float[8]).isSameLength(new Field[8]);
                Asserts.that(new int[16]).isSameLength(new Constructor[16]);
                Asserts.that(new long[32]).isSameLength(new Class<?>[32]);
                Asserts.that(new short[64]).isSameLength(new Stopwatch[64]);
                Asserts.that(new String[128]).isSameLength(new String[128]);
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have a difference with length")
        void test1() {
            String description = "They are expected to be the same length, but they aren't.";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .isSameLength(new Object[1])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .isSameLength(new StringBuilder[2])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .isSameLength(new Character[3])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .isSameLength(new Double[3])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .isSameLength(new Field[10])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .isSameLength(new Constructor[32])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .isSameLength(new Class<?>[5])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .isSameLength(new Array[128])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .isSameLength(new String[256])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .isSameLength(null)).withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotSameLength'")
    class IsNotSameLength {
        @Test
        @DisplayName("passes, when actual and other have a difference with length")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new boolean[0]).isNotSameLength(new Object[1]);
                Asserts.that(new byte[1]).isNotSameLength(new StringBuilder[2]);
                Asserts.that(new char[2]).isNotSameLength(new Character[3]);
                Asserts.that(new double[4]).isNotSameLength(new Double[3]);
                Asserts.that(new float[8]).isNotSameLength(new Field[10]);
                Asserts.that(new int[16]).isNotSameLength(new Constructor[32]);
                Asserts.that(new long[32]).isNotSameLength(new Class<?>[5]);
                Asserts.that(new short[64]).isNotSameLength(new Array[128]);
                Asserts.that(new String[128]).isNotSameLength(new String[256]);
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same length")
        void test1() {
            String description = "They are expected to be not the same length, but they are.";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new boolean[0])
                    .isNotSameLength(new Object[0])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new byte[1])
                    .isNotSameLength(new StringBuilder[1])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new char[2])
                    .isNotSameLength(new Character[2])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new double[4])
                    .isNotSameLength(new Double[4])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new float[8])
                    .isNotSameLength(new Field[8])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new int[16])
                    .isNotSameLength(new Constructor[16])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new long[32])
                    .isNotSameLength(new Class<?>[32])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new short[64])
                    .isNotSameLength(new Stopwatch[64])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new String[128])
                    .isNotSameLength(new String[128])).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new Object[256])
                    .isNotSameLength(null)).withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
            String description = "It is expected to contain the given element, but it doesn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'}).contains('f'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024}).contains(1023))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\.")).contains(""))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                            .contains(new String[]{"i"}))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'containsAny'")
    class ContainsAny {
        @Test
        @DisplayName("passes, when actual contains the given elements at least 1")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new Character[]{'a', 'b', 'c', 'd', 'e', null}).containsAny();
                Asserts.that(new int[]{-1024, -1, 0, 1, 1024}).containsAny(null, 1023, -1);
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .containsAny("java", "util", "concurrent", "atomic", "lang", "reflect", "common");
                Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                        .containsAny(new String[]{"c"}, new String[]{"n"}, new String[]{"ut"}, new String[]{"ti", "s"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String description = "It is expected to contain at least one of the given element(s), but it doesn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'}).containsAny(null, '\u0000'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024}).containsAny(10))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                            .containsAny("java", "util", "concurrent", "atomic", "package", "reflect"))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                            .containsAny(new String[]{"c"}, new String[]{"n", "ut"}, new String[]{"tie"}, new String[]{"s"}))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'containsAll'")
    class ContainsAll {
        @Test
        @DisplayName("passes, when actual contains the given array")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'}).containsAll(new Character[]{'e', 'd', 'c', 'b', 'a'});
                Asserts.that(new int[]{-1024, -1, 0, 1, 1024}).containsAll(new Integer[0]);
                Asserts.that(getClass().getPackage().getName().split("\\."))
                        .containsAll(new String[]{"imsejin", "github", "common", "assertion", "lang"});
                Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                        .containsAll(new String[][]{{"n"}, {"ut", "i", "li"}, {"tie", "s"}});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given array")
        void test1() {
            String description = "It is expected to contain the given element, but it doesn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new char[]{'a', 'b', 'c', 'd', 'e'})
                            .containsAll(new Character[]{'e', 'd', 'c', 'f', 'a'}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new int[]{-1024, -1, 0, 1, 1024})
                            .containsAll(new Integer[]{-1024, -1, 0, 1, 1023}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                            .containsAll(new String[]{"IMSEJIN", "GITHUB", "COMMON", "ARRAY", "ASSERTION", "IO"}))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new String[][]{{"c", "om", "mo"}, {"n"}, {"ut", "i", "li"}, {"tie", "s"}})
                            .containsAll(new String[][]{{"n"}, {"ut", "li"}, {"tie", "s"}}))
                    .withMessageStartingWith(description);
        }
    }

}
