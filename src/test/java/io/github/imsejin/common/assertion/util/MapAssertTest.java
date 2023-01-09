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

package io.github.imsejin.common.assertion.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.util.CollectionUtils;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("AbstractMapAssert")
class MapAssertTest {

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
    @DisplayName("method 'isNotEmpty'")
    class IsNotEmpty {
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
                    .isThrownBy(() -> Asserts.that(actual).isNotEmpty()));
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            List<Map<?, ?>> list = Arrays.asList(
                    Collections.emptyMap(), Collections.emptyNavigableMap(), Collections.emptySortedMap(),
                    new HashMap<>(), new TreeMap<>(), new Properties(), new ConcurrentHashMap<>());

            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEmpty())
                    .withMessageStartingWith("It is expected not to be empty, but it is."));
        }
    }

    @Nested
    @DisplayName("method 'hasSize'")
    class HasSize {
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
                Asserts.that(Collections.emptyMap()).hasSize(0);
                Asserts.that(Collections.singletonMap("foo", "bar")).hasSize(1);
                Asserts.that(hashMap).hasSize(hashMap.size());
                Asserts.that(treeMap).hasSize(treeMap.size());
                Asserts.that(props).hasSize(props.size());
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given size")
        void test1() {
            // given
            String description = "It is expected to have the given size, but it isn't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            hashMap.put('c', new Object());

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).hasSize(1))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap(null, null)).hasSize(0))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap).hasSize(4))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'hasSameSizeAs'")
    class HasSameSizeAs {
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
                Asserts.that(Collections.emptyMap()).hasSameSizeAs(new LinkedHashMap<>());
                Asserts.that(Collections.singletonMap("foo", "bar")).hasSameSizeAs(hashMap);
                Asserts.that(hashMap).hasSameSizeAs(Collections.singletonMap(null, null));
                Asserts.that(treeMap).hasSameSizeAs(props);
                Asserts.that(props).hasSameSizeAs(treeMap);
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have a difference with size")
        void test1() {
            // given
            String description = "They are expected to have the same size, but they aren't.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            hashMap.put('c', new Object());

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).hasSameSizeAs(null))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap())
                            .hasSameSizeAs(Collections.singletonMap(null, null)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap(null, null))
                            .hasSameSizeAs(Collections.emptyMap()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(
                            () -> Asserts.that(hashMap).hasSameSizeAs(CollectionUtils.toMap(Arrays.asList(1, 2, 3, 4))))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'doesNotHaveSameSizeAs'")
    class DoesNotHaveSameSizeAs {
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
                Asserts.that(Collections.emptyMap()).doesNotHaveSameSizeAs(hashMap);
                Asserts.that(Collections.singletonMap(null, null)).doesNotHaveSameSizeAs(new Properties());
                Asserts.that(hashMap).doesNotHaveSameSizeAs(treeMap);
                Asserts.that(treeMap).doesNotHaveSameSizeAs(hashMap);
                Asserts.that(props).doesNotHaveSameSizeAs(Collections.singletonMap("foo", "bar"));
            });
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same size")
        void test1() {
            // given
            String description = "They are expected not to have the same size, but they are.";
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put('a', new Object());
            hashMap.put('b', new Object());
            hashMap.put('c', new Object());

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).doesNotHaveSameSizeAs(null))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.emptyMap()).doesNotHaveSameSizeAs(new Properties()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap(null, null))
                            .doesNotHaveSameSizeAs(Collections.singletonMap("foo", "bar")))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(hashMap)
                            .doesNotHaveSameSizeAs(CollectionUtils.toMap(Arrays.asList(0, 1, 2))))
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
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap("foo", "bar"))
                            .containsAllKeys(Collections.singletonList("f")))
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
                Asserts.that(Collections.singletonMap("foo", "bar"))
                        .containsAllValues(Collections.singletonList("bar"));
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
                    .isThrownBy(() -> Asserts.that(Collections.singletonMap("foo", "bar"))
                            .containsAllValues(Collections.singletonList("b")))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(
                            () -> Asserts.that(hashMap).containsAllValues(Arrays.asList(new Object(), new Object())))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(treeMap)
                            .containsAllValues(props.values().stream().map(Object::toString).collect(toList())))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(props).containsAllValues(props.keySet()))
                    .withMessageStartingWith(description);
        }
    }

}
