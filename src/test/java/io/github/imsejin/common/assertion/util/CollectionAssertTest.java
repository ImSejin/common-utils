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
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.EnumerationAssertable;
import io.github.imsejin.common.assertion.composition.IterationAssertable;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.util.ArrayUtils;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

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

            // expect
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

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEmpty()));
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            List<Collection<?>> list = Arrays.asList(
                    Collections.emptyList(), new ArrayList<>(), new HashSet<>(), new Stack<>(), new PriorityQueue<>());

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEmpty())
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}actual\\.size: '0'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSize'")
    class HasSize {
        @Test
        @DisplayName("passes, when actual has the given size")
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

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).hasSize(0);
                Asserts.that(Collections.singletonList(null)).hasSize(1);
                Asserts.that(set).hasSize(set.size());
                Asserts.that(stack).hasSize(stack.size());
                Asserts.that(queue).hasSize(queue.size());
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given size")
        void test1() {
            String description = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .hasSize(-1))
                    .withMessageMatching(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .hasSize(0))
                    .withMessageMatching(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(1, 2, 3, 4))
                            .hasSize(3))
                    .withMessageMatching(description);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSize'")
    class DoesNotHaveSize {
        @Test
        @DisplayName("passes, when actual doesn't have the given size")
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

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).doesNotHaveSize(-1);
                Asserts.that(Collections.singletonList(null)).doesNotHaveSize(0);
                Asserts.that(set).doesNotHaveSize(set.size() - 1);
                Asserts.that(stack).doesNotHaveSize(stack.size() + 1);
                Asserts.that(queue).doesNotHaveSize(queue.size() * 2L);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .doesNotHaveSize(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .doesNotHaveSize(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(1, 2, 3, 4))
                            .doesNotHaveSize(4))
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
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).hasSameSizeAs(new HashSet<>());
                Asserts.that(Collections.singletonList(null)).hasSameSizeAs(set);
                Asserts.that(set).hasSameSizeAs(set);
                Asserts.that(stack).hasSameSizeAs(stack);
                Asserts.that(queue).hasSameSizeAs(queue);
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

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .hasSameSizeAs(null))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .hasSameSizeAs(Collections.singletonList(null)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .hasSameSizeAs(Collections.emptyList()))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(1, 2, 3, 4))
                            .hasSameSizeAs(Arrays.asList(1, 2)))
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
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).doesNotHaveSameSizeAs(set);
                Asserts.that(Collections.singletonList(null)).doesNotHaveSameSizeAs(new HashSet<>());
                Asserts.that(set).doesNotHaveSameSizeAs(stack);
                Asserts.that(stack).doesNotHaveSameSizeAs(set);
                Asserts.that(queue).doesNotHaveSameSizeAs(Arrays.asList(1, 2, 3));
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

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .doesNotHaveSameSizeAs(null))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .doesNotHaveSameSizeAs(new ArrayList<>()))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .doesNotHaveSameSizeAs(Collections.singletonList(0)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(1, 2))
                            .doesNotHaveSameSizeAs(Arrays.asList('a', 'b')))
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
            // given
            List<Character> list = Arrays.asList('a', 'b');
            Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
            Queue<String> queue = new PriorityQueue<>(Arrays.asList("A", "B", "C", "D"));

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).hasSizeGreaterThan(-1);
                Asserts.that(Collections.singletonList(null)).hasSizeGreaterThan(0);
                Asserts.that(list).hasSizeGreaterThan(1);
                Asserts.that(set).hasSizeGreaterThan(2);
                Asserts.that(queue).hasSizeGreaterThan(3);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size less than or same as the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .hasSizeGreaterThan(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .hasSizeGreaterThan(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b'))
                            .hasSizeGreaterThan(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new HashSet<>(Arrays.asList(1, 2, 3)))
                            .hasSizeGreaterThan(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(new PriorityQueue<>(Arrays.asList("A", "B", "C", "D")))
                                    .hasSizeGreaterThan(8))
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
            // given
            List<Character> list = Arrays.asList('a', 'b');
            Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
            Queue<String> queue = new PriorityQueue<>(Arrays.asList("A", "B", "C", "D"));

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).hasSizeGreaterThanOrEqualTo(0);
                Asserts.that(Collections.singletonList(null)).hasSizeGreaterThanOrEqualTo(1);
                Asserts.that(list).hasSizeGreaterThanOrEqualTo(2);
                Asserts.that(set).hasSizeGreaterThanOrEqualTo(2);
                Asserts.that(queue).hasSizeGreaterThanOrEqualTo(3);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size less than the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN_OR_EQUAL_TO) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .hasSizeGreaterThanOrEqualTo(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .hasSizeGreaterThanOrEqualTo(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b'))
                            .hasSizeGreaterThanOrEqualTo(4))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new HashSet<>(Arrays.asList(1, 2, 3)))
                            .hasSizeGreaterThanOrEqualTo(8))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(new PriorityQueue<>(Arrays.asList("A", "B", "C", "D")))
                                    .hasSizeGreaterThanOrEqualTo(16))
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
            // given
            List<Character> list = Arrays.asList('a', 'b');
            Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
            Queue<String> queue = new PriorityQueue<>(Arrays.asList("A", "B", "C", "D"));

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).hasSizeLessThan(1);
                Asserts.that(Collections.singletonList(null)).hasSizeLessThan(2);
                Asserts.that(list).hasSizeLessThan(4);
                Asserts.that(set).hasSizeLessThan(8);
                Asserts.that(queue).hasSizeLessThan(16);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size greater than or same as the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .hasSizeLessThan(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .hasSizeLessThan(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b'))
                            .hasSizeLessThan(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new HashSet<>(Arrays.asList(1, 2, 3)))
                            .hasSizeLessThan(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(new PriorityQueue<>(Arrays.asList("A", "B", "C", "D")))
                                    .hasSizeLessThan(1))
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
            // given
            List<Character> list = Arrays.asList('a', 'b');
            Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
            Queue<String> queue = new PriorityQueue<>(Arrays.asList("A", "B", "C", "D"));

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList()).hasSizeLessThanOrEqualTo(0);
                Asserts.that(Collections.singletonList(null)).hasSizeLessThanOrEqualTo(1);
                Asserts.that(list).hasSizeLessThanOrEqualTo(2);
                Asserts.that(set).hasSizeLessThanOrEqualTo(4);
                Asserts.that(queue).hasSizeLessThanOrEqualTo(8);
            });
        }

        @Test
        @DisplayName("throws exception, when actual has size greater than the given size")
        void test1() {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN_OR_EQUAL_TO) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .hasSizeLessThanOrEqualTo(-1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.singletonList(null))
                            .hasSizeLessThanOrEqualTo(0))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b'))
                            .hasSizeLessThanOrEqualTo(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new HashSet<>(Arrays.asList(1, 2, 3)))
                            .hasSizeLessThanOrEqualTo(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(new PriorityQueue<>(Arrays.asList("A", "B", "C", "D")))
                                    .hasSizeLessThanOrEqualTo(3))
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
                Asserts.that(Arrays.asList('a', 'b', null, 'd', 'e')).contains(null);
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).contains(-1);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\."))).contains("github");
                Asserts.that(Arrays.asList(new String[] {"alpha"}, new String[] {null, ""}, new String[] {"beta"}))
                        .contains(new String[] {null, ""});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given element")
        void test1() {
            String message = Pattern.quote(EnumerationAssertable.DEFAULT_DESCRIPTION_CONTAINS) +
                    "\n {4}actual: '\\[.+]'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', null, 'd', 'e'))
                            .contains('\u0000'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .contains(64))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(getClass().getPackage().getName().split("\\."))
                                    .contains(""))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(new String[] {"alpha"}, new String[] {null, ""}))
                                    .contains(new String[0]))
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
                Asserts.that(Collections.emptySet()).doesNotContain(null);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e')).doesNotContain('f');
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).doesNotContain(1023);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\."))).doesNotContain("");
                Asserts.that(
                                Arrays.asList(new String[] {"c", "mo"}, new String[] {"n"}, new String[] {"ut", "i", "li"}))
                        .doesNotContain(new String[] {"i"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual contains the given element")
        void test1() {
            String message = Pattern.quote(EnumerationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN) +
                    "\n {4}actual: '\\[.+]'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e'))
                            .doesNotContain('d'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .doesNotContain(1024))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .doesNotContain("imsejin"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new String[] {"c", "mo"}, new String[] {"n"}, new String[] {"ut", "i", "li"}))
                            .doesNotContain(new String[] {"n"}))
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
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Arrays.asList(true, null, false)).containsNull();
                Asserts.that(Arrays.asList(23, null, -54)).containsNull();
                Asserts.that(Arrays.asList('0', ' ', null, 'i')).containsNull();
                Asserts.that(Arrays.asList(null, 3.14, 0.0)).containsNull();
                Asserts.that(Arrays.asList(1.141F, 0.0F, null)).containsNull();
                Asserts.that(Arrays.asList(0, null, -10)).containsNull();
                Asserts.that(Arrays.asList(-128L, 64L, null)).containsNull();
                Asserts.that(Arrays.asList("", null, "alpha")).containsNull();
                Asserts.that(Arrays.asList(null, new String[0])).containsNull();
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain null")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_NULL) +
                    "\n {4}actual: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .containsNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList("Alpha", "null", "", "BETA", "gamma"))
                                    .containsNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList('A', Character.MIN_VALUE, 'b', '0'))
                                    .containsNull())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(0, Integer.MIN_VALUE, Integer.MAX_VALUE))
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

            // expect
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

            // expect
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).doesNotContainNull())
                    .withMessageMatching(Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL) +
                            "\n {4}actual: '\\[.*]'"));
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
                Asserts.that(Collections.emptySet())
                        .containsAny();
                Asserts.that(Collections.singletonList(false))
                        .containsAny(false, null, null);
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                        .containsAny(1024, null, -1);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsAny("java", "util", "concurrent", "atomic", "lang", "reflect", "common");
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ANY) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptySet())
                            .containsAny(1, 2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(1, 2, 3))
                            .containsAny())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e'))
                            .containsAny(null, '\u0000'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .containsAny(2))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(getClass().getPackage().getName().split("\\."))
                                    .containsAny("java", "net", "concurrent", "atomic", "lang", "reflect"))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsAll'")
    class ContainsAll {
        @Test
        @DisplayName("passes, when actual contains the given collection")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptySet()).containsAll(null);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e'))
                        .containsAll(Arrays.asList('e', 'd', 'c', 'b', 'a'));
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).containsAll(new ArrayList<>());
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsAll(Arrays.asList("imsejin", "github", "common", "util", "assertion", "io"));
                Asserts.that(Arrays.stream(getClass().getPackage().getName().split("\\."))
                                .map(it -> new String[] {it}).collect(toList()))
                        .containsAll(Stream.of("imsejin", "github", "common", "io")
                                .map(it -> new String[] {it}).collect(toList()));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given collection")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ALL) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}missing: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .containsAll(Arrays.asList('a', 'b', 'c', 'd', 'e')))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .containsAll(Arrays.asList(-1024, -10, 0, 10, 1024)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .containsAll(Arrays.asList("IMSEJIN", "GITHUB", "COMMON", "COLLECTION", "ASSERTION", "IO")))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.stream(getClass().getPackage().getName().split("\\."))
                                            .map(it -> new String[] {it})
                                            .collect(toList()))
                            .containsAll(Stream.of("imsejin", "github", "common", "IO")
                                    .map(it -> new String[] {it})
                                    .collect(toList())))
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
                Asserts.that(Collections.emptyList())
                        .doesNotContainAll(Arrays.asList('a', 'b', 'c', 'd', 'e'));
                Asserts.that(Arrays.asList('z', 'y', 'x', 'w', 'v'))
                        .doesNotContainAll(new ArrayList<>());
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                        .doesNotContainAll(Arrays.asList(-512, -64, 8, 32, 128));
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .doesNotContainAll(Arrays.asList("IMSEJIN", "GITHUB", "COMMON", "ARRAY", "ASSERTION", "IO"));
                Asserts.that(Arrays.asList(new String[] {"com"}, new String[] {"tie", "s"}))
                        .doesNotContainAll(Arrays.asList(new String[0], new String[] {"n"}, new String[] {"tie"}));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_ALL) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}included: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e'))
                            .doesNotContainAll(Arrays.asList('z', 'y', 'x', 'w', 'a')))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .doesNotContainAll(Collections.singletonList(1)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .doesNotContainAll(Arrays.asList("imsejin", "github", "common", "assertion", "lang")))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new String[] {"n"}, new String[] {"li"}, new String[] {"tie", "s"}))
                            .doesNotContainAll(Arrays.asList(new String[] {"n"}, new String[] {"tie", "s"})))
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
                Asserts.that(Collections.emptySet())
                        .containsOnly();
                Asserts.that(Arrays.asList('z', 'y', 'x', 'w', 'v'))
                        .containsOnly('v', 'w', 'x', 'y', 'z');
                Asserts.that(Arrays.asList(1, 2, 3, 1, 2, 3, 1, 2, 3))
                        .containsOnly(1, 2, 3);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsOnly("io", "github", "imsejin", "common", "assertion", "util");
                Asserts.that(
                                Arrays.asList(new String[] {}, new String[] {"alpha"}, new String[] {}, new String[] {"beta"},
                                        null))
                        .containsOnly(new String[][] {{}, {"alpha"}, null, {"beta"}});
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

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .containsOnly(-1, BigDecimal.ZERO, 2.5))
                    .withMessageMatching(missingMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1, 0, 1))
                            .containsOnly(-1024, -1, 0, 1, 1024))
                    .withMessageMatching(missingMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1, BigDecimal.ZERO, 2.5))
                            .containsOnly())
                    .withMessageMatching(unexpectedMessage);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .containsOnly("imsejin", "github", "common", "assertion", "util"))
                    .withMessageMatching(unexpectedMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new String[] {}, new String[] {"alpha"}, null, new String[] {"beta"}))
                            .containsOnly(new String[][] {{}, {"alpha"}, {"beta"}}))
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
                Asserts.that(Collections.singletonList(null)).containsOnlyNulls();
                Asserts.that(Arrays.asList(null, null)).containsOnlyNulls();
                Asserts.that(Arrays.asList(null, null, null, null)).containsOnlyNulls();
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain only null element(s)")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS) +
                    "\n {4}actual: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptySet())
                            .containsOnlyNulls())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1, 0, 1))
                            .containsOnlyNulls())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .containsOnlyNulls())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new String[] {}, new String[] {"alpha"}, null, new String[] {"beta"}))
                            .containsOnlyNulls())
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsOnlyOnce'")
    class ContainsOnlyOnce {
        @Test
        @DisplayName("passes, when actual contains the given element only once")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.singleton(null))
                        .containsOnlyOnce(null);
                Asserts.that(Arrays.asList('z', 'y', 'x', 'w', 'v'))
                        .containsOnlyOnce('x');
                Asserts.that(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9))
                        .containsOnlyOnce(9);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsOnlyOnce("imsejin");
                Asserts.that(
                                Arrays.asList(new String[] {}, new String[] {"alpha"}, new String[] {}, new String[] {"beta"},
                                        null))
                        .containsOnlyOnce(new String[] {"alpha"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given element only once")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_ONCE) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '.+'" +
                    "\n {4}matched: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .containsOnlyOnce(null))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1, 0, 1, 3, 2, 1))
                            .containsOnlyOnce(1))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(0.1, -1, BigDecimal.ZERO, 2.5))
                                    .containsOnlyOnce(BigDecimal.ONE))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .containsOnlyOnce("alpha.beta.gamma.delta"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new String[] {}, new String[] {"alpha"}, null, new String[] {"beta"}))
                            .containsOnlyOnce(new String[] {"gamma"}))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'containsWithFrequency'")
    class ContainsWithFrequency {
        @Test
        @DisplayName("passes, when actual contains the given element with frequency")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptySet())
                        .containsWithFrequency(0, 'a');
                Asserts.that(Arrays.asList('z', 'y', 'x', 'w', 'v'))
                        .containsWithFrequency(1, 'x');
                Asserts.that(Arrays.asList(1, 2, 3, 1, 2, 3, 1, 2, 3))
                        .containsWithFrequency(3, 2);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsWithFrequency(1, "imsejin");
                Asserts.that(
                                Arrays.asList(new String[] {}, new String[] {"alpha"}, new String[] {}, new String[] {"beta"},
                                        null))
                        .containsWithFrequency(1, new String[] {"alpha"});
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given element with frequency")
        void test1() {
            String invalidMessage =
                    Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_WITH_FREQUENCY_INVALID) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}expected: '.+'" +
                            "\n {4}frequency: '-\\d+'";
            String differentMessage =
                    Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_WITH_FREQUENCY_DIFFERENT) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}expected: '.+'" +
                            "\n {4}matched: '\\[.*]'" +
                            "\n {4}actual-frequency: '\\d+'" +
                            "\n {4}expected-frequency: '\\d+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .containsWithFrequency(-1, null))
                    .withMessageMatching(invalidMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(-1, 0, 1, 3, 2, 1))
                            .containsWithFrequency(-10, 3))
                    .withMessageMatching(invalidMessage);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(0.1, -1, BigDecimal.ZERO, 0.1))
                                    .containsWithFrequency(1, 0.1))
                    .withMessageMatching(differentMessage);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .containsWithFrequency(2, "imsejin"))
                    .withMessageMatching(differentMessage);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new String[] {}, new String[] {"alpha"}, null, new String[] {"beta"}))
                            .containsWithFrequency(0, new String[0]))
                    .withMessageMatching(differentMessage);
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
                Asserts.that(Collections.emptyList()).doesNotHaveDuplicates();
                Asserts.that(Collections.singleton(null)).doesNotHaveDuplicates();
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd')).doesNotHaveDuplicates();
                Asserts.that(Arrays.asList(null, "", " ", "null", "undefined")).doesNotHaveDuplicates();
                Asserts.that(Arrays.asList(new int[0], null, new int[] {1, 2}, new int[] {1, 1}, new int[] {2, 1},
                                new int[] {2}))
                        .doesNotHaveDuplicates();
            });
        }

        @Test
        @DisplayName("throws exception, when actual has duplicated elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_DUPLICATES) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}duplicated: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(null, null))
                            .doesNotHaveDuplicates())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', '0', 'c', '0'))
                            .doesNotHaveDuplicates())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("")))
                                    .doesNotHaveDuplicates())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new int[0], null, new int[] {1, 2}, new int[] {1, 1}, new int[] {1, 2},
                                            new int[] {2}))
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
                Asserts.that(Collections.singleton(new Object()))
                        .anyMatch(Objects::nonNull);
                Asserts.that(IntStream.rangeClosed(0, 100).boxed().collect(toSet()))
                        .anyMatch(it -> it == 100);
                Asserts.that(Arrays.asList('a', '1', 'b', 'c'))
                        .anyMatch(Character::isDigit);
                Asserts.that(Arrays.asList("ImSejin", "Github", "common", "asSErtIon", "lAnG"))
                        .anyMatch(it -> it.chars().allMatch(Character::isLowerCase));
                Asserts.that(Arrays.asList(new String[] {null}, new String[] {"alpha"}, new String[] {},
                                new String[] {"beta"}, new String[] {"gamma"}))
                        .anyMatch(ArrayUtils::isNullOrEmpty);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't match the given condition with its any elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_ANY_MATCH) +
                    "\n {4}actual: '\\[.*]'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptySet())
                            .anyMatch(Objects::nonNull))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(IntStream.range(0, 100).boxed().collect(toSet()))
                                    .anyMatch(it -> it == 100))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', '1', 'b', 'c'))
                            .anyMatch(Character::isUpperCase))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList("ImSejin", "Github", "coMMoN", "asSErtIon", "lAnG"))
                                    .anyMatch(it -> it.chars().allMatch(Character::isLowerCase)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(new String[] {null}, new String[] {"alpha"}, new String[] {"beta"},
                                            new String[] {"gamma"}))
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
                Asserts.that(Collections.singletonList(new Object()))
                        .allMatch(Objects::nonNull);
                Asserts.that(IntStream.range(0, 100).boxed().collect(toList()))
                        .allMatch(it -> it < 100);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd'))
                        .allMatch(Character::isLowerCase);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .allMatch(it -> it.chars().allMatch(Character::isLowerCase));
                Asserts.that(Arrays.asList(new String[] {null}, new String[] {"alpha"}, new String[] {"beta"},
                                new String[] {"gamma"}))
                        .allMatch(ArrayUtils::exists);
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't match the given condition with its all elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}unmatched: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Collections.emptyList())
                            .allMatch(Objects::nonNull))
                    .withMessageMatching(Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH) +
                            "\n {4}actual: '\\[.*]'");
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(IntStream.rangeClosed(0, 100).boxed().collect(toList()))
                                    .allMatch(it -> it < 100))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'C', 'd'))
                            .allMatch(Character::isLowerCase))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .allMatch(it -> it.chars().allMatch(Character::isUpperCase)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(null, new String[] {"alpha"}, new String[0], new String[] {"beta"}))
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
                Asserts.that(Collections.emptySet())
                        .noneMatch(Objects::nonNull);
                Asserts.that(IntStream.range(0, 100).boxed().collect(toList()))
                        .noneMatch(it -> it == 100);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd'))
                        .noneMatch(Character::isUpperCase);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .noneMatch(it -> it.chars().anyMatch(Character::isUpperCase));
                Asserts.that(Arrays.asList(new String[] {null}, new String[] {"alpha"}, new String[] {"beta"},
                                new String[] {"gamma"}))
                        .noneMatch(ArrayUtils::isNullOrEmpty);
            });
        }

        @Test
        @DisplayName("throws exception, when actual matches the given condition with its any elements")
        void test1() {
            String message = Pattern.quote(IterationAssertable.DEFAULT_DESCRIPTION_NONE_MATCH) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}matched: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(null, new Object(), null))
                            .noneMatch(Objects::isNull))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(IntStream.rangeClosed(0, 100).boxed().collect(toList()))
                                    .noneMatch(it -> it == 100))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'C', 'd'))
                            .noneMatch(Character::isLowerCase))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(
                            () -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                                    .noneMatch(it -> it.chars().allMatch(Character::isLowerCase)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(
                                    Arrays.asList(null, new String[] {"alpha"}, new String[0], new String[] {"beta"}))
                            .noneMatch(ArrayUtils::isNullOrEmpty))
                    .withMessageMatching(message);
        }
    }

}
