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

package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.util.ArrayUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("CollectionAssert")
class CollectionAssertTest {

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0() {
            // given
            List<Collection<?>> list = Arrays.asList(
                    Collections.emptyList(), new ArrayList<>(), new HashSet<>(), new Stack<>(), new PriorityQueue<>());

            // except
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty()));
        }

        @Test
        @DisplayName("throws exception, when actual has element")
        void test1() {
            // given
            Set<String> set = new HashSet<>();
            set.add("new");
            set.add("set");
            Stack<String> stack = new Stack<>();
            stack.push("new");
            stack.push("stack");
            Queue<String> queue = new PriorityQueue<>();
            queue.add("new");
            queue.add("queue");

            List<Collection<?>> list = Arrays.asList(Collections.singletonList(""), set, stack, queue);

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
            Set<String> set = new HashSet<>();
            set.add("new");
            set.add("set");
            Stack<String> stack = new Stack<>();
            stack.push("new");
            stack.push("stack");
            Queue<String> queue = new PriorityQueue<>();
            queue.add("new");
            queue.add("queue");

            List<Collection<?>> list = Arrays.asList(Collections.singletonList(""), set, stack, queue);

            // except
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).hasElement()));
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            List<Collection<?>> list = Arrays.asList(
                    Collections.emptyList(), new ArrayList<>(), new HashSet<>(), new Stack<>(), new PriorityQueue<>());

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).hasElement())
                    .withMessageStartingWith("It is expected to have element, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    @Nested
    @DisplayName("method 'hasSizeOf'")
    class HasSizeOf {

        @Test
        @DisplayName("passes, when actual has the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Set<String> set = new HashSet<>();
                set.add("new");
                set.add("set");
                Stack<String> stack = new Stack<>();
                stack.push("new");
                stack.push("stack");
                Queue<String> queue = new PriorityQueue<>();
                queue.add("new");
                queue.add("queue");

                // except
                Asserts.that(Collections.emptyList()).hasSizeOf(0);
                Asserts.that(Collections.singletonList(null)).hasSizeOf(1);
                Asserts.that(set).hasSizeOf(set.size());
                Asserts.that(stack).hasSizeOf(stack.size());
                Asserts.that(queue).hasSizeOf(queue.size());
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given size")
        void test1() {
            String description = "It is expected to be the same size, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList()).hasSizeOf(1))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonList(null)).hasSizeOf(0))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(1, 2, 3, 4)).hasSizeOf(3))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isSameSize'")
    class IsSameSize {
        @Test
        @DisplayName("passes, when actual and other have the same size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Set<String> set = new HashSet<>();
                set.add("set");
                Stack<String> stack = new Stack<>();
                stack.push("new");
                stack.push("stack");
                Queue<String> queue = new PriorityQueue<>();
                queue.add("new");
                queue.add("queue");

                // expect
                Asserts.that(Collections.emptyList()).isSameSize(new HashSet<>());
                Asserts.that(Collections.singletonList(null)).isSameSize(set);
                Asserts.that(set).isSameSize(set);
                Asserts.that(stack).isSameSize(stack);
                Asserts.that(queue).isSameSize(queue);
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have a difference with size")
        void test1() {
            String description = "They are expected to be the same size, but they aren't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList()).isSameSize(null))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList()).isSameSize(Collections.singletonList(null)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonList(null)).isSameSize(Collections.emptyList()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(1, 2, 3, 4)).isSameSize(Arrays.asList(1, 2)))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotSameSize'")
    class IsNotSameSize {
        @Test
        @DisplayName("passes, when actual and other have a difference with size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Set<String> set = new HashSet<>();
                set.add("set");
                Stack<String> stack = new Stack<>();
                stack.push("new");
                stack.push("stack");
                Queue<String> queue = new PriorityQueue<>();
                queue.add("new");
                queue.add("queue");

                // except
                Asserts.that(Collections.emptyList()).isNotSameSize(set);
                Asserts.that(Collections.singletonList(null)).isNotSameSize(new HashSet<>());
                Asserts.that(set).isNotSameSize(stack);
                Asserts.that(stack).isNotSameSize(set);
                Asserts.that(queue).isNotSameSize(Arrays.asList(1, 2, 3));
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same size")
        void test1() {
            String description = "They are expected to be not the same size, but they are.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList()).isNotSameSize(null))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList()).isNotSameSize(new ArrayList<>()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonList(null)).isNotSameSize(Collections.singletonList(0)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(1, 2)).isNotSameSize(Arrays.asList('a', 'b')))
                    .withMessageStartingWith(description);
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
                Asserts.that(Arrays.asList('a', 'b', null, 'd', 'e')).contains(null);
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).contains(-1);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\."))).contains("github");
                Asserts.that(Arrays.asList(new String[]{"alpha"}, new String[]{null, ""}, new String[]{"beta"}))
                        .contains(new String[]{null, ""});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given element")
        void test1() {
            String description = "It is expected to contain the given element, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', null, 'd', 'e')).contains('\u0000'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).contains(64))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\.")).contains(""))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(new String[]{"alpha"}, new String[]{null, ""}))
                            .contains(new String[0]))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'doesNotContain'")
    class DoesNotContain {
        @Test
        @DisplayName("passes, when actual doesn't contain the given element")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptySet()).doesNotContain(null);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e')).doesNotContain('f');
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).doesNotContain(1023);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\."))).doesNotContain("");
                Asserts.that(Arrays.asList(new String[]{"c", "mo"}, new String[]{"n"}, new String[]{"ut", "i", "li"}))
                        .doesNotContain(new String[]{"i"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual contains the given element")
        void test1() {
            String description = "It is expected not to contain the given element, but it is.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e'))
                            .doesNotContain('d'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .doesNotContain(1024))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .doesNotContain("imsejin"))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(new String[]{"c", "mo"}, new String[]{"n"}, new String[]{"ut", "i", "li"}))
                            .doesNotContain(new String[]{"n"}))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'containsNull'")
    class ContainsNull {
        @Test
        @DisplayName("passes, when actual contains null")
        void test0() {
            // expect
            Asserts.that(Arrays.asList(true, null, false)).containsNull();
            Asserts.that(Arrays.asList(23, null, -54)).containsNull();
            Asserts.that(Arrays.asList('0', ' ', null, 'i')).containsNull();
            Asserts.that(Arrays.asList(null, 3.14, 0.0)).containsNull();
            Asserts.that(Arrays.asList(1.141F, 0.0F, null)).containsNull();
            Asserts.that(Arrays.asList(0, null, -10)).containsNull();
            Asserts.that(Arrays.asList(-128L, 64L, null)).containsNull();
            Asserts.that(Arrays.asList("", null, "alpha")).containsNull();
            Asserts.that(Arrays.asList(null, new String[0])).containsNull();
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain null")
        void test1() {
            String description = "It is expected to contain null, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList()).containsNull())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList("Alpha", "null", "", "BETA", "gamma")).containsNull())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('A', Character.MIN_VALUE, 'b', '0')).containsNull())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(0, Integer.MIN_VALUE, Integer.MAX_VALUE)).containsNull())
                    .withMessageStartingWith(description);
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
            Set<String> set = new HashSet<>();
            set.add("new");
            set.add("set");
            Stack<String> stack = new Stack<>();
            stack.push("new");
            stack.push("stack");
            Queue<String> queue = new PriorityQueue<>();
            queue.add("new");
            queue.add("queue");

            List<Collection<?>> list = Arrays.asList(Collections.emptyList(), set, stack, queue);

            // except
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).doesNotContainNull()));
        }

        @Test
        @DisplayName("throws exception, when actual contains null")
        void test1() {
            // given
            Set<String> set = new HashSet<>();
            set.add("new");
            set.add(null);
            Stack<String> stack = new Stack<>();
            stack.push(null);
            stack.push("stack");

            List<Collection<?>> list = Arrays.asList(Collections.singletonList(null), set, stack);

            // except
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).doesNotContainNull())
                    .withMessageStartingWith("It is expected not to contain null, but it is."));
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
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e', null)).containsAny();
                Asserts.that(Collections.singletonList(false)).containsAny(false, null, null);
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).containsAny(1024, null, -1);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsAny("java", "util", "concurrent", "atomic", "lang", "reflect", "common");
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String description = "It is expected to contain at least one of the given element(s), but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e')).containsAny(null, '\u0000'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).containsAny(2))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                            .containsAny("java", "net", "concurrent", "atomic", "lang", "reflect"))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'containsAll'")
    class ContainsAll {
        @Test
        @DisplayName("passes, when actual contains the given collection")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptySet()).containsAll(null);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e')).containsAll(Arrays.asList('e', 'd', 'c', 'b', 'a'));
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).containsAll(new ArrayList<>());
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsAll(Arrays.asList("imsejin", "github", "common", "util", "assertion", "io"));
                Asserts.that(Arrays.stream(getClass().getPackage().getName().split("\\."))
                                .map(it -> new String[]{it}).collect(toList()))
                        .containsAll(Stream.of("imsejin", "github", "common", "io")
                                .map(it -> new String[]{it}).collect(toList()));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given collection")
        void test1() {
            String description = "It is expected to contain all the given elements, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .containsAll(Arrays.asList('a', 'b', 'c', 'd', 'e')))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .containsAll(Arrays.asList(-1024, -10, 0, 10, 1024)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .containsAll(Arrays.asList("IMSEJIN", "GITHUB", "COMMON", "COLLECTION", "ASSERTION", "IO")))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.stream(getClass().getPackage().getName().split("\\."))
                                    .map(it -> new String[]{it}).collect(toList()))
                            .containsAll(Stream.of("imsejin", "github", "common", "IO")
                                    .map(it -> new String[]{it}).collect(toList())))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'doesNotContainAll'")
    class DoesNotContainAll {
        @Test
        @DisplayName("passes, when actual doesn't contain all the given elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList())
                        .doesNotContainAll(Arrays.asList('a', 'b', 'c', 'd', 'e'));
                Asserts.that(Arrays.asList('z', 'y', 'x', 'w', 'v'))
                        .doesNotContainAll(new ArrayList<>());
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                        .doesNotContainAll(Arrays.asList(-512, -64, 8, 32, 128));
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .doesNotContainAll(Arrays.asList("IMSEJIN", "GITHUB", "COMMON", "ARRAY", "ASSERTION", "IO"));
                Asserts.that(Arrays.asList(new String[]{"com"}, new String[]{"tie", "s"}))
                        .doesNotContainAll(Arrays.asList(new String[0], new String[]{"n"}, new String[]{"tie"}));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String description = "It is expected not to contain all the given elements, but it is.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e'))
                            .doesNotContainAll(Arrays.asList('z', 'y', 'x', 'w', 'a')))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .doesNotContainAll(Collections.singletonList(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .doesNotContainAll(Arrays.asList("imsejin", "github", "common", "assertion", "lang")))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(new String[]{"n"}, new String[]{"li"}, new String[]{"tie", "s"}))
                            .doesNotContainAll(Arrays.asList(new String[]{"n"}, new String[]{"tie", "s"})))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'containsOnly'")
    class ContainsOnly {
        @Test
        @DisplayName("passes, when actual contains only the given element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptySet())
                        .containsOnly();
                Asserts.that(Arrays.asList(-1, BigDecimal.ZERO, 2.5))
                        .containsOnly();
                Asserts.that(Arrays.asList('z', 'y', 'x', 'w', 'v'))
                        .containsOnly('v', 'w', 'x', 'y', 'z');
                Asserts.that(Arrays.asList(1, 2, 3, 1, 2, 3, 1, 2, 3))
                        .containsOnly(1, 2, 3);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsOnly("io", "github", "imsejin", "common", "assertion", "util");
                Asserts.that(Arrays.asList(new String[]{}, new String[]{"alpha"}, new String[]{}, new String[]{"beta"}, null))
                        .containsOnly(new String[][]{{}, {"alpha"}, null, {"beta"}});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain only the given element(s)")
        void test1() {
            String missingDescription = "It is expected to contain only the given element(s), but it doesn't contain some element(s).";
            String unexpectedDescription = "It is expected to contain only the given element(s), but it contains unexpected element(s).";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .containsOnly(-1, BigDecimal.ZERO, 2.5))
                    .withMessageStartingWith(missingDescription);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1, 0, 1))
                            .containsOnly(-1024, -1, 0, 1, 1024))
                    .withMessageStartingWith(missingDescription);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .containsOnly("imsejin", "github", "common", "assertion", "util"))
                    .withMessageStartingWith(unexpectedDescription);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(new String[]{}, new String[]{"alpha"}, null, new String[]{"beta"}))
                            .containsOnly(new String[][]{{}, {"alpha"}, {"beta"}}))
                    .withMessageStartingWith(unexpectedDescription);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'containsOnlyNulls'")
    class ContainsOnlyNulls {
        @Test
        @DisplayName("passes, when actual contains only null element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.singletonList(null)).containsOnlyNulls();
                Asserts.that(Arrays.asList(null, null)).containsOnlyNulls();
                Asserts.that(Arrays.asList(null, null, null, null)).containsOnlyNulls();
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain only null element(s)")
        void test1() {
            String description = "It is expected to contain only null elements, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptySet())
                            .containsOnlyNulls())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1, 0, 1))
                            .containsOnlyNulls())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .containsOnlyNulls())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(new String[]{}, new String[]{"alpha"}, null, new String[]{"beta"}))
                            .containsOnlyNulls())
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'doesNotHaveDuplicates'")
    class DoesNotHaveDuplicates {
        @Test
        @DisplayName("passes, when actual doesn't have duplicated elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).doesNotHaveDuplicates();
                Asserts.that(Collections.singleton(null)).doesNotHaveDuplicates();
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd')).doesNotHaveDuplicates();
                Asserts.that(Arrays.asList(null, "", " ", "null", "undefined")).doesNotHaveDuplicates();
                Asserts.that(Arrays.asList(new int[0], null, new int[]{1, 2}, new int[]{1, 1}, new int[]{2, 1}, new int[]{2}))
                        .doesNotHaveDuplicates();
            });
        }

        @Test
        @DisplayName("throws exception, when actual has duplicated elements")
        void test1() {
            String description = "It is expected not to have duplicated elements, but it is.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(null, null))
                            .doesNotHaveDuplicates())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', '0', 'c', '0'))
                            .doesNotHaveDuplicates())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("")))
                            .doesNotHaveDuplicates())
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(new int[0], null, new int[]{1, 2}, new int[]{1, 1}, new int[]{1, 2}, new int[]{2}))
                            .doesNotHaveDuplicates())
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'anyMatch'")
    class AnyMatch {
        @Test
        @DisplayName("passes, when actual matches the given condition with its any elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.singleton(new Object()))
                        .anyMatch(Objects::nonNull);
                Asserts.that(IntStream.rangeClosed(0, 100).boxed().collect(toSet()))
                        .anyMatch(it -> it == 100);
                Asserts.that(Arrays.asList('a', '1', 'b', 'c'))
                        .anyMatch(Character::isDigit);
                Asserts.that(Arrays.asList("ImSejin", "Github", "common", "asSErtIon", "lAnG"))
                        .anyMatch(it -> it.chars().allMatch(Character::isLowerCase));
                Asserts.that(Arrays.asList(new String[]{null}, new String[]{"alpha"}, new String[]{}, new String[]{"beta"}, new String[]{"gamma"}))
                        .anyMatch(ArrayUtils::isNullOrEmpty);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't match the given condition with its any elements")
        void test1() {
            String description = "It is expected to match the given condition with its any elements, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptySet())
                            .anyMatch(Objects::nonNull))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(IntStream.range(0, 100).boxed().collect(toSet()))
                            .anyMatch(it -> it == 100))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', '1', 'b', 'c'))
                            .anyMatch(Character::isUpperCase))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList("ImSejin", "Github", "coMMoN", "asSErtIon", "lAnG"))
                            .anyMatch(it -> it.chars().allMatch(Character::isLowerCase)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(new String[]{null}, new String[]{"alpha"}, new String[]{"beta"}, new String[]{"gamma"}))
                            .anyMatch(ArrayUtils::isNullOrEmpty))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'allMatch'")
    class AllMatch {
        @Test
        @DisplayName("passes, when actual matches the given condition with its all elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.singletonList(new Object()))
                        .allMatch(Objects::nonNull);
                Asserts.that(IntStream.range(0, 100).boxed().collect(toList()))
                        .allMatch(it -> it < 100);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd'))
                        .allMatch(Character::isLowerCase);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .allMatch(it -> it.chars().allMatch(Character::isLowerCase));
                Asserts.that(Arrays.asList(new String[]{null}, new String[]{"alpha"}, new String[]{"beta"}, new String[]{"gamma"}))
                        .allMatch(ArrayUtils::exists);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't match the given condition with its all elements")
        void test1() {
            String description = "It is expected to match the given condition with its all elements, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .allMatch(Objects::nonNull))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(IntStream.rangeClosed(0, 100).boxed().collect(toList()))
                            .allMatch(it -> it < 100))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'C', 'd'))
                            .allMatch(Character::isLowerCase))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .allMatch(it -> it.chars().allMatch(Character::isUpperCase)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(null, new String[]{"alpha"}, new String[0], new String[]{"beta"}))
                            .allMatch(ArrayUtils::isNullOrEmpty))
                    .withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'noneMatch'")
    class NoneMatch {
        @Test
        @DisplayName("passes, when actual doesn't match the given condition with its all elements")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptySet())
                        .noneMatch(Objects::nonNull);
                Asserts.that(IntStream.range(0, 100).boxed().collect(toList()))
                        .noneMatch(it -> it == 100);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd'))
                        .noneMatch(Character::isUpperCase);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .noneMatch(it -> it.chars().anyMatch(Character::isUpperCase));
                Asserts.that(Arrays.asList(new String[]{null}, new String[]{"alpha"}, new String[]{"beta"}, new String[]{"gamma"}))
                        .noneMatch(ArrayUtils::isNullOrEmpty);
            });
        }

        @Test
        @DisplayName("throws exception, when actual matches the given condition with its any elements")
        void test1() {
            String description = "It is expected not to match the given condition with its all elements, but it is.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(null, new Object(), null))
                            .noneMatch(Objects::isNull))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(IntStream.rangeClosed(0, 100).boxed().collect(toList()))
                            .noneMatch(it -> it == 100))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'C', 'd'))
                            .noneMatch(Character::isLowerCase))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .noneMatch(it -> it.chars().allMatch(Character::isLowerCase)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(null, new String[]{"alpha"}, new String[0], new String[]{"beta"}))
                            .noneMatch(ArrayUtils::isNullOrEmpty))
                    .withMessageStartingWith(description);
        }
    }

}
