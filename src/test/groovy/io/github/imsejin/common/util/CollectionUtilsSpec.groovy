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

package io.github.imsejin.common.util

import spock.lang.Specification

import java.util.stream.IntStream

import static java.util.stream.Collectors.toList

class CollectionUtilsSpec extends Specification {

    def "Checks collection is null or empty"() {
        when:
        def actual = CollectionUtils.isNullOrEmpty collection as Collection

        then:
        actual == expected

        where:
        collection               | expected
        null                     | true
        []                       | true
        Collections.EMPTY_SET    | true
        new PriorityQueue<?>()   | true
        [0]                      | false
        Collections.singleton(0) | false
        ["a", "b", "c"]          | false
    }

    def "Checks map is null or empty"() {
        when:
        def actual = CollectionUtils.isNullOrEmpty map as Map

        then:
        actual == expected

        where:
        map                              | expected
        null                             | true
        [:]                              | true
        ["0": 1]                         | false
        CollectionUtils.toMap([1, 2, 3]) | false
        [id: 101, name: "foo"]           | false
    }

    def "If collection is null or empty, returns the other collection"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(collection as Collection, defaultValue as Collection)

        then:
        actual == expected

        where:
        collection             | defaultValue || expected
        null                   | []           || []
        new PriorityQueue<?>() | null         || null
        [[1, 2], ["b", "a"]]   | []           || [[1, 2], ["b", "a"]]
    }

    def "If collection is null or empty, supplier returns the other collection"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(collection as Collection, supplier)

        then:
        actual == expected

        where:
        collection             | supplier || expected
        null                   | { [] }   || []
        new PriorityQueue<?>() | { null } || null
        [[1, 2], ["b", "a"]]   | { [] }   || [[1, 2], ["b", "a"]]
    }

    def "If list is null or empty, returns the other list"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(list as List, defaultValue)

        then:
        actual == expected

        where:
        list              | defaultValue || expected
        null              | []           || []
        []                | [1, 2]       || [1, 2]
        ["alpha", "beta"] | []           || ["alpha", "beta"]
    }

    def "If list is null or empty, supplier returns the other list"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(list as List, supplier)

        then:
        actual == expected

        where:
        list              | supplier   || expected
        null              | { [] }     || []
        []                | { [1, 2] } || [1, 2]
        ["alpha", "beta"] | { [] }     || ["alpha", "beta"]
    }

    def "If set is null or empty, returns the other set"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(set as Set, defaultValue)

        then:
        actual == expected

        where:
        set                   | defaultValue          || expected
        null                  | Collections.EMPTY_SET || Collections.EMPTY_SET
        Collections.EMPTY_SET | [2, 1, 3].toSet()     || [1, 2, 3].toSet()
        [-854].toSet()        | Collections.EMPTY_SET || [-854].toSet()
    }

    def "If set is null or empty, supplier returns the other set"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(set as Set, supplier)

        then:
        actual == expected

        where:
        set                   | supplier                  || expected
        null                  | { Collections.EMPTY_SET } || Collections.EMPTY_SET
        Collections.EMPTY_SET | { [2, 1, 3].toSet() }     || [1, 2, 3].toSet()
        [-854].toSet()        | { Collections.EMPTY_SET } || [-854].toSet()
    }

    def "If map is null or empty, returns the other map"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(map as Map, defaultValue)

        then:
        actual == expected

        where:
        map                     | defaultValue || expected
        null                    | [:]          || [:]
        [:]                     | [0: 1, 1: 2] || [0: 1, 1: 2]
        [alpha: 123, beta: 456] | [:]          || [alpha: 123, beta: 456]
    }

    def "If map is null or empty, supplier returns the other map"() {
        when:
        def actual = CollectionUtils.ifNullOrEmpty(map as Map, supplier)

        then:
        actual == expected

        where:
        map                     | supplier         || expected
        null                    | { [:] }          || [:]
        [:]                     | { [0: 1, 1: 2] } || [0: 1, 1: 2]
        [alpha: 123, beta: 456] | { [:] }          || [alpha: 123, beta: 456]
    }

    def "Checks collection exists"() {
        when:
        def actual = CollectionUtils.exists collection as Collection

        then:
        actual == expected

        where:
        collection               | expected
        null                     | false
        []                       | false
        Collections.EMPTY_SET    | false
        new PriorityQueue<?>()   | false
        [0]                      | true
        Collections.singleton(0) | true
        ["a", "b", "c"]          | true
    }

    def "Checks map exists"() {
        when:
        def actual = CollectionUtils.exists map as Map

        then:
        actual == expected

        where:
        map                              | expected
        null                             | false
        [:]                              | false
        ["0": 1]                         | true
        CollectionUtils.toMap([1, 2, 3]) | true
        [id: 101, name: "foo"]           | true
    }

    def "Converts collection to map whose keys are indexes"() {
        when:
        def actual = CollectionUtils.toMap collection

        then:
        actual == expected

        where:
        collection                                    | expected
        []                                            | [:]
        ["A", "B", "C"]                               | [0: "A", 1: "B", 2: "C"]
        [-1, 0, 1].toSet()                            | [0: -1, 1: 0, 2: 1]
        getClass().package.name.split("\\.").toList() | [0: "io", 1: "github", 2: "imsejin", 3: "common", 4: "util"]
    }

    def "Finds a max number in collection"() {
        when:
        def actual = CollectionUtils.findMax collection

        then:
        actual == expected

        where:
        collection                                     | expected
        [0L, -1024L, 7L, 1L, -1L, 7L, 0L, 1L]          | 7
        [1L, -1L, 0L, 3L, 3L, 5L, 22L, 5L, 6L].toSet() | 22
    }

    def "FindElement"() {
    }

    def "Partitions collection by size"() {
        given:
        def range = 655_362
        def integers = IntStream.range(0, range).boxed().collect(toList())

        when:
        def outer = CollectionUtils.partitionBySize(integers, chunkSize)

        then: """
            If remainder exists, size of last sub-list is equal to it.
            All sub-lists except the last are equal to chuck' size.
        """
        def outerSize = outer.size()
        def originSize = integers.size()
        boolean modExists = Math.floorMod(originSize, chunkSize) > 0
        IntStream.range(0, outerSize).mapToObj({
            def inner = outer.get(it)

            if (modExists && it == outerSize - 1) {
                return inner.size() == Math.floorMod(originSize, chunkSize)
            } else {
                return inner.size() == chunkSize
            }
        }).reduce((a, b) -> a && b).get()

        and: "All sizes of inner lists except last are the same."
        def maybeExceptLast = Math.floorMod(originSize, chunkSize) > 0 && Math.floorDiv(originSize, chunkSize) > 0
                ? outer.subList(0, outerSize - 2)
                : outer
        def maxSizeOfInnerList = maybeExceptLast.stream().mapToInt(List.&size).reduce(Integer.&max)
        def minSizeOfInnerList = maybeExceptLast.stream().mapToInt(List.&size).reduce(Integer.&min)
        maxSizeOfInnerList == minSizeOfInnerList

        and: "Sum of inner list' size and origin list' size are the same."
        def sizeOfInnerLists = outer.stream().mapToInt(List.&size).sum()
        sizeOfInnerLists == range

        where:
        chunkSize << [1, 2, 3, 10, 33, 369, 5120, 17_726, 655_362]
    }

    def "Partitions collection by count"() {
        given:
        def range = 655_362
        def integers = IntStream.range(0, range).boxed().collect(toList())

        when:
        def outer = CollectionUtils.partitionByCount(integers, count)

        then: "Outer list' size and parameter 'count' are the same."
        outer.size() == count

        and: "All sizes of inner lists except last are the same."
        def maybeExceptLast = Math.floorMod(integers.size(), count) > 0
                ? outer.subList(0, count - 2)
                : outer
        def maxSizeOfInnerList = maybeExceptLast.stream().mapToInt(List.&size).reduce(Integer.&max)
        def minSizeOfInnerList = maybeExceptLast.stream().mapToInt(List.&size).reduce(Integer.&min)
        maxSizeOfInnerList == minSizeOfInnerList

        and: "Sum of inner list' size and origin list' size are the same."
        def sizeOfInnerLists = outer.stream().mapToInt(List.&size).sum()
        sizeOfInnerLists == range

        where:
        count << [1, 2, 3, 10, 33, 369, 5120, 17_726, 655_362]
    }

    def "Finds a median value in long array"() {
        when:
        double actual = CollectionUtils.median(longs as long[])

        then:
        actual == expected

        where:
        longs                                                         | expected
        [Long.MAX_VALUE, Long.MIN_VALUE, 0, 2, 33, 369, 5120, 17_726,
         Integer.MIN_VALUE, Integer.MAX_VALUE, 8_702_145, 12_345_678] | 2744.5
        [-153, 10, -78, 985_534, 84]                                  | 10.0
    }

    def "Finds a median value in int array"() {
        when:
        double actual = CollectionUtils.median(ints as int[])

        then:
        actual == expected

        where:
        ints                                                                      | expected
        [Integer.MAX_VALUE, 0, -2, 487, 9425, 17_726, Integer.MIN_VALUE, 165_204] | 4956
        [0, 1024, -86_023, 9, 396]                                                | 9
    }

}
