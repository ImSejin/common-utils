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

package io.github.imsejin.common.assertion.map;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.util.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AbstractMapAssert")
class AbstractMapAssertTest {

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0() {
            List<Map<?, ?>> list = Arrays.asList(
                    Collections.emptyMap(), Collections.emptyNavigableMap(), Collections.emptySortedMap(),
                    new HashMap<>(), new TreeMap<>(), new Properties(), new ConcurrentHashMap<>());

            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty()));
        }

        @Test
        @DisplayName("throws exception, when actual has entry")
        void test1() {
            // given
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            Map<Integer, String> treeMap = new TreeMap<>();
            treeMap.put(0, "new");
            treeMap.put(1, "tree-map");
            Properties props = new Properties();
            props.setProperty("new", "property");
            props.setProperty("news", "properties");

            List<Map<?, ?>> list = Arrays.asList(Collections.singletonMap("foo", "bar"), hashMap, treeMap, props);

            // when & then
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEmpty())
                    .withMessageStartingWith("It is expected to be empty, but it isn't."));
        }
    }

    @Nested
    @DisplayName("method 'hasEntry'")
    class HasEntry {
        @Test
        @DisplayName("passes, when actual has entry")
        void test0() {
            // given
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            Map<Integer, String> treeMap = new TreeMap<>();
            treeMap.put(0, "new");
            treeMap.put(1, "tree-map");
            Properties props = new Properties();
            props.setProperty("new", "property");
            props.setProperty("news", "properties");

            List<Map<?, ?>> list = Arrays.asList(Collections.singletonMap("foo", "bar"), hashMap, treeMap, props);

            // when & then
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).hasEntry()));
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            List<Map<?, ?>> list = Arrays.asList(
                    Collections.emptyMap(), Collections.emptyNavigableMap(), Collections.emptySortedMap(),
                    new HashMap<>(), new TreeMap<>(), new Properties(), new ConcurrentHashMap<>());

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).hasEntry())
                    .withMessageStartingWith("It is expected to have entry, but it isn't."));
        }
    }

    @Nested
    @DisplayName("method 'hasSizeOf'")
    class HasSizeOf {
        @Test
        @DisplayName("passes, when actual has the given size")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put('a', new Object());
                hashMap.put('b', new Object());
                Map<Integer, String> treeMap = new TreeMap<>();
                treeMap.put(0, "new");
                treeMap.put(1, "tree-map");
                Properties props = new Properties();
                props.setProperty("new", "property");
                props.setProperty("news", "properties");

                // expect
                Asserts.that(Collections.emptyMap()).hasSizeOf(0);
                Asserts.that(Collections.singletonMap("foo", "bar")).hasSizeOf(1);
                Asserts.that(hashMap).hasSizeOf(hashMap.size());
                Asserts.that(treeMap).hasSizeOf(treeMap.size());
                Asserts.that(props).hasSizeOf(props.size());
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given size")
        void test1() {
            // given
            String description = "It is expected to be the same size, but it isn't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            hashMap.put('c', new Object());

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).hasSizeOf(1))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap(null, null)).hasSizeOf(0))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).hasSizeOf(4))
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
                // given
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put('a', new Object());
                Map<Integer, String> treeMap = new TreeMap<>();
                treeMap.put(0, "new");
                treeMap.put(1, "tree-map");
                Properties props = new Properties();
                props.setProperty("new", "property");
                props.setProperty("news", "properties");

                // expect
                Asserts.that(Collections.emptyMap()).isSameSize(new LinkedHashMap<>());
                Asserts.that(Collections.singletonMap("foo", "bar")).isSameSize(hashMap);
                Asserts.that(hashMap).isSameSize(Collections.singletonMap(null, null));
                Asserts.that(treeMap).isSameSize(props);
                Asserts.that(props).isSameSize(treeMap);
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have a difference with size")
        void test1() {
            // given
            String description = "They are expected to be the same size, but they aren't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            hashMap.put('c', new Object());

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).isSameSize(null))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).isSameSize(Collections.singletonMap(null, null)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap(null, null)).isSameSize(Collections.emptyMap()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).isSameSize(CollectionUtils.toMap(Arrays.asList(1, 2, 3, 4))))
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
                // given
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put('a', new Object());
                Map<Integer, String> treeMap = new TreeMap<>();
                treeMap.put(0, "new");
                treeMap.put(1, "tree-map");
                Properties props = new Properties();
                props.setProperty("new", "property");
                props.setProperty("news", "properties");

                // expect
                Asserts.that(Collections.emptyMap()).isNotSameSize(hashMap);
                Asserts.that(Collections.singletonMap(null, null)).isNotSameSize(new Properties());
                Asserts.that(hashMap).isNotSameSize(treeMap);
                Asserts.that(treeMap).isNotSameSize(hashMap);
                Asserts.that(props).isNotSameSize(Collections.singletonMap("foo", "bar"));
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same size")
        void test1() {
            // given
            String description = "They are expected to be not the same size, but they are.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            hashMap.put('c', new Object());

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).isNotSameSize(null))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).isNotSameSize(new Properties()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap(null, null)).isNotSameSize(Collections.singletonMap("foo", "bar")))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).isNotSameSize(CollectionUtils.toMap(Arrays.asList(0, 1, 2))))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'containsKey'")
    class ContainsKey {
        @Test
        @DisplayName("passes, when actual contains the given key")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put('a', new Object());
                hashMap.put('b', new Object());
                Map<Integer, String> treeMap = new TreeMap<>();
                treeMap.put(0, "new");
                treeMap.put(1, "tree-map");
                Properties props = new Properties();
                props.setProperty("new", "property");
                props.setProperty("news", "properties");

                // expect
                Asserts.that(Collections.singletonMap("foo", "bar")).containsKey("foo");
                Asserts.that(hashMap).containsKey('b');
                Asserts.that(treeMap).containsKey(1);
                Asserts.that(props).containsKey("news");
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given key")
        void test1() {
            // given
            String description = "It is expected to contain the given key, but it doesn't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            Map<Integer, String> treeMap = new TreeMap<>();
            treeMap.put(0, "new");
            treeMap.put(1, "tree-map");
            Properties props = new Properties();
            props.setProperty("new", "property");
            props.setProperty("news", "properties");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap("foo", "bar")).containsKey("f"))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).containsKey('c'))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(treeMap).containsKey(-1))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(props).containsKey("newspaper"))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'containsValue'")
    class ContainsValue {
        @Test
        @DisplayName("passes, when actual contains the given value")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put('a', hashMap);
                hashMap.put('b', new Object());
                Map<Integer, String> treeMap = new TreeMap<>();
                treeMap.put(0, "new");
                treeMap.put(1, "tree-map");
                Properties props = new Properties();
                props.setProperty("new", "property");
                props.setProperty("news", "properties");

                // expect
                Asserts.that(Collections.singletonMap("foo", "bar")).containsValue("bar");
                Asserts.that(hashMap).containsValue(hashMap);
                Asserts.that(treeMap).containsValue("tree-map");
                Asserts.that(props).containsValue("properties");
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain the given value")
        void test1() {
            // given
            String description = "It is expected to contain the given value, but it doesn't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            Map<Integer, String> treeMap = new TreeMap<>();
            treeMap.put(0, "new");
            treeMap.put(1, "tree-map");
            Properties props = new Properties();
            props.setProperty("new", "property");
            props.setProperty("news", "properties");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap("foo", "bar")).containsValue("b"))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).containsValue(new Object()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(treeMap).containsValue("newspaper"))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(props).containsValue("props"))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'containsAllKeys'")
    class ContainsAllKeys {
        @Test
        @DisplayName("passes, when actual contains all the given keys")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put(0, new Object());
                hashMap.put(1, new Object());
                Map<Integer, String> treeMap = new TreeMap<>();
                treeMap.put(0, "new");
                treeMap.put(1, "tree-map");
                Properties props = new Properties();
                props.setProperty("new", "property");
                props.setProperty("news", "properties");
                props.setProperty("newspaper", "properties");

                // expect
                Asserts.that(Collections.singletonMap("foo", "bar")).containsAllKeys(Collections.singletonList("foo"));
                Asserts.that(hashMap).containsAllKeys(treeMap);
                Asserts.that(treeMap).containsAllKeys(Arrays.asList(0, 1));
                Asserts.that(props).containsAllKeys(Arrays.asList("new", "news", "newspaper"));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given keys")
        void test1() {
            // given
            String description = "It is expected to contain all the given keys, but it doesn't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            Map<Character, String> treeMap = new TreeMap<>();
            treeMap.put('a', "new");
            treeMap.put('b', "tree-map");
            Properties props = new Properties();
            props.setProperty("new", "property");
            props.setProperty("news", "properties");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap("foo", "bar")).containsAllKeys(Collections.singletonList("f")))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).containsAllKeys(props))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(treeMap).containsAllKeys(Arrays.asList('a', 'c')))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(props).containsAllKeys(props.values()))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'containsAllValues'")
    class ContainsAllValues {
        @Test
        @DisplayName("passes, when actual contains all the given values")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put(0, new Object());
                hashMap.put(1, new Object());
                Map<Integer, String> treeMap = new TreeMap<>();
                treeMap.put(0, "new");
                treeMap.put(1, "tree-map");
                Properties props = new Properties();
                props.setProperty("new", "property");
                props.setProperty("news", "properties");
                props.setProperty("newspaper", "properties");

                // expect
                Asserts.that(Collections.singletonMap("foo", "bar")).containsAllValues(Collections.singletonList("bar"));
                Asserts.that(hashMap).containsAllValues(hashMap);
                Asserts.that(treeMap).containsAllValues(Arrays.asList("new", "tree-map"));
                Asserts.that(props).containsAllValues(props.values());
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't contain all the given values")
        void test1() {
            // given
            String description = "It is expected to contain all the given values, but it doesn't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put(0, new Object());
            hashMap.put(1, new Object());
            Map<Character, String> treeMap = new TreeMap<>();
            treeMap.put('a', "new");
            treeMap.put('b', "tree-map");
            Properties props = new Properties();
            props.setProperty("new", "property");
            props.setProperty("news", "properties");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap("foo", "bar")).containsAllValues(Collections.singletonList("b")))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).containsAllValues(Arrays.asList(new Object(), new Object())))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(treeMap).containsAllValues(props.values().stream().map(Object::toString).collect(toList())))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(props).containsAllValues(props.keySet()))
                    .withMessageStartingWith(description);
        }
    }

}
