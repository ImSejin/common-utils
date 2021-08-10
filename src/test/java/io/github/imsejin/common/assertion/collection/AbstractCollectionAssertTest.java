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

package io.github.imsejin.common.assertion.collection;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AbstractCollectionAssert")
class AbstractCollectionAssertTest {

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0() {
            List<Collection<?>> list = Arrays.asList(
                    Collections.emptyList(), new ArrayList<>(), new HashSet<>(), new Stack<>(), new PriorityQueue<>());

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

            // when & then
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty())
                    .withMessageStartingWith("It is expected to be empty, but it isn't."));
        }
    }

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

            // when & then
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

            // when & then
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

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).doesNotContainNull())
                    .withMessageStartingWith("It is expected not to contain null, but it isn't."));
        }
    }

    @Nested
    @DisplayName("method 'hasSizeOf'")
    class HasSizeOf {
        @Test
        @DisplayName("passes, when actual has the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Set<String> set = new HashSet<>();
                set.add("new");
                set.add("set");
                Stack<String> stack = new Stack<>();
                stack.push("new");
                stack.push("stack");
                Queue<String> queue = new PriorityQueue<>();
                queue.add("new");
                queue.add("queue");

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

    @Nested
    @DisplayName("method 'isSameSize'")
    class IsSameSize {
        @Test
        @DisplayName("passes, when actual and other have the same size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Set<String> set = new HashSet<>();
                set.add("set");
                Stack<String> stack = new Stack<>();
                stack.push("new");
                stack.push("stack");
                Queue<String> queue = new PriorityQueue<>();
                queue.add("new");
                queue.add("queue");

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

    @Nested
    @DisplayName("method 'isNotSameSize'")
    class IsNotSameSize {
        @Test
        @DisplayName("passes, when actual and other have a difference with size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Set<String> set = new HashSet<>();
                set.add("set");
                Stack<String> stack = new Stack<>();
                stack.push("new");
                stack.push("stack");
                Queue<String> queue = new PriorityQueue<>();
                queue.add("new");
                queue.add("queue");

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
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given element")
        void test1() {
            String description = "It is expected to contain the given element, but it doesn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', null, 'd', 'e')).contains('\u0000'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).contains(64))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\.")).contains(""))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'containsAny'")
    class ContainsAny {
        @Test
        @DisplayName("passes, when actual contains the given elements at least 1")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e', null)).containsAny(null);
                Asserts.that(Collections.singletonList(false)).containsAny(false, null, null);
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).containsAny(1024, null, -1);
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsAny("java", "util", "concurrent", "atomic", "lang", "reflect", "common");
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given elements")
        void test1() {
            String description = "It is expected to contain the given elements, but it doesn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e')).containsAny(null, '\u0000'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).containsAny(2))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(getClass().getPackage().getName().split("\\."))
                            .containsAny("java", "util", "concurrent", "atomic", "lang", "reflect"))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'containsAll'")
    class ContainsAll {
        @Test
        @DisplayName("passes, when actual contains the given collection")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e')).containsAll(Arrays.asList('e', 'd', 'c', 'b', 'a'));
                Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024)).containsAll(new ArrayList<>());
                Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                        .containsAll(Arrays.asList("imsejin", "github", "common", "collection", "assertion", "io"));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given collection")
        void test1() {
            String description = "It is expected to contain the given collection, but it doesn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'c', 'd', 'e'))
                            .containsAll(Arrays.asList('e', 'd', 'c', 'f', 'a')))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(-1024, -1, 0, 1, 1024))
                            .containsAll(Arrays.asList(-1024, -10, 0, 10, 1024)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Arrays.asList(getClass().getPackage().getName().split("\\.")))
                            .containsAll(Arrays.asList("IMSEJIN", "GITHUB", "COMMON", "COLLECTION", "ASSERTION", "IO")))
                    .withMessageStartingWith(description);
        }
    }

}
