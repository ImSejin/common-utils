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
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

class ArrayUtilsSpec extends Specification {

    def "Checks array is null or empty"() {
        expect:
        ArrayUtils.isNullOrEmpty(array) == expected

        where:
        array               | expected
        null                | true
        [].toArray()        | true
        [true] as boolean[] | false
        [0] as byte[]       | false
        [0] as char[]       | false
        [0] as double[]     | false
        [0] as float[]      | false
        [0] as int[]        | false
        [0] as long[]       | false
        [0] as short[]      | false
        [0] as Object[]     | false
    }

    def "Checks array exists"() {
        expect:
        ArrayUtils.exists(array) == expected

        where:
        array               | expected
        [true] as boolean[] | true
        [0] as byte[]       | true
        [0] as char[]       | true
        [0] as double[]     | true
        [0] as float[]      | true
        [0] as int[]        | true
        [0] as long[]       | true
        [0] as short[]      | true
        [0] as Object[]     | true
        [].toArray()        | false
        null                | false
    }

    def "Makes primitive array boxed"() {
        when:
        def actual = ArrayUtils.wrap array

        then:
        actual?.class == expected?.class
        actual == expected

        where:
        array                                                  | expected
        null                                                   | array
        String                                                 | array
        [] as Object[]                                         | array
        [new Object()] as Object[]                             | array
        [null, ["alpha"], [], ["beta"]] as String[][]          | array
        [] as boolean[]                                        | [] as Boolean[]
        [false, true] as boolean[]                             | [false, true] as Boolean[]
        [] as byte[][]                                         | [] as Byte[][]
        [[], null, [-1, 0], [1]] as byte[][]                   | [[], null, [-1, 0], [1]] as Byte[][]
        [] as short[][][]                                      | [] as Short[][][]
        [[], [[-1]], [[0], null, [1]], null] as short[][][]    | [[], [[-1]], [[0], null, [1]], null] as Short[][][]
        [] as char[][][][]                                     | [] as Character[][][][]
        [[[['a']]], [], [[['b'], ['c']]]] as char[][][][]      | [[[['a']]], [], [[['b'], ['c']]]] as Character[][][][]
        [] as int[][][][][]                                    | [] as Integer[][][][][]
        [[[[[-1, 0]]]], [[null, [[1]]]]] as int[][][][][]      | [[[[[-1, 0]]]], [[null, [[1]]]]] as Integer[][][][][]
        [] as long[][][][][][]                                 | [] as Long[][][][][][]
        [[[], [[[[-1, 0, 1], []]]]]] as long[][][][][][]       | [[[], [[[[-1, 0, 1], []]]]]] as Long[][][][][][]
        [] as float[][][][][][][]                              | [] as Float[][][][][][][]
        [[[[[[], [[-1, 0, 1]], []]]]]] as float[][][][][][][]  | [[[[[[], [[-1, 0, 1]], []]]]]] as Float[][][][][][][]
        [] as double[][][][][][][][]                           | [] as Double[][][][][][][][]
        [[[[[[[[-1], [0], [1]]]]]]]] as double[][][][][][][][] | [[[[[[[[-1], [0], [1]]]]]]]] as Double[][][][][][][][]
    }

    def "Makes wrapper array unboxed"() {
        when:
        def actual = ArrayUtils.unwrap array

        then:
        actual?.class == expected?.class
        actual == expected

        where:
        array                                                  | expected
        null                                                   | array
        String                                                 | array
        [] as Object[]                                         | array
        [new Object()] as Object[]                             | array
        [null, ["alpha"], [], ["beta"]] as String[][]          | array
        [] as Boolean[]                                        | [] as boolean[]
        [false, true] as Boolean[]                             | [false, true] as boolean[]
        [] as Byte[][]                                         | [] as byte[][]
        [[], null, [-1, 0], [1]] as Byte[][]                   | [[], null, [-1, 0], [1]] as byte[][]
        [] as Short[][][]                                      | [] as short[][][]
        [[], [[-1]], [[0], null, [1]], null] as Short[][][]    | [[], [[-1]], [[0], null, [1]], null] as short[][][]
        [] as Character[][][][]                                | [] as char[][][][]
        [[[['a']]], [], [[['b'], ['c']]]] as Character[][][][] | [[[['a']]], [], [[['b'], ['c']]]] as char[][][][]
        [] as Integer[][][][][]                                | [] as int[][][][][]
        [[[[[-1, 0]]]], [[null, [[1]]]]] as Integer[][][][][]  | [[[[[-1, 0]]]], [[null, [[1]]]]] as int[][][][][]
        [] as Long[][][][][][]                                 | [] as long[][][][][][]
        [[[], [[[[-1, 0, 1], []]]]]] as Long[][][][][][]       | [[[], [[[[-1, 0, 1], []]]]]] as long[][][][][][]
        [] as Float[][][][][][][]                              | [] as float[][][][][][][]
        [[[[[[], [[-1, 0, 1]], []]]]]] as Float[][][][][][][]  | [[[[[[], [[-1, 0, 1]], []]]]]] as float[][][][][][][]
        [] as Double[][][][][][][][]                           | [] as double[][][][][][][][]
        [[[[[[[[-1], [0], [1]]]]]]]] as Double[][][][][][][][] | [[[[[[[[-1], [0], [1]]]]]]]] as double[][][][][][][][]
    }

    def "Converts array to string"() {
        expect:
        ArrayUtils.toString(value) == expected

        where:
        value                                                     | expected
        null                                                      | "null"
        [false, true] as boolean[]                                | "[false, true]"
        [Byte.MIN_VALUE, 0, Byte.MAX_VALUE] as byte[]             | "[-128, 0, 127]"
        [Short.MIN_VALUE, 0, Short.MAX_VALUE] as short[]          | "[-32768, 0, 32767]"
        [Character.MIN_VALUE, 955, Character.MAX_VALUE] as char[] | "[\u0000, λ, \uffff]"
        [Integer.MIN_VALUE, 0, Integer.MAX_VALUE] as int[]        | "[-2147483648, 0, 2147483647]"
        [Long.MIN_VALUE, 0, Long.MAX_VALUE] as long[]             | "[-9223372036854775808, 0, 9223372036854775807]"
        [-1.141F, 0, 1.141F] as float[]                           | "[-1.141, 0.0, 1.141]"
        [-3.141592D, 0, 3.141592D] as double[]                    | "[-3.141592, 0.0, 3.141592]"
        [] as Object[]                                            | "[]"
        ['a', 'b'] as char[]                                      | "[a, b]"
        [[2], null, []] as int[][]                                | "[[2], null, []]"
        [new String[]{"a", "b"}, new int[]{2, 4, 56}, -0.5]       | "[[a, b], [2, 4, 56], -0.5]"
        [' ', [0], [0, 1]]                                        | "[ , [0], [0, 1]]"
        [:]                                                       | "{}"
        [alpha: new Object[0], beta: new String[]{"a", "b", "c"}] | "{alpha=[], beta=[a, b, c]}"
    }

    def "Gets hash code of array"() {
        when:
        ArrayUtils.hashCode(array)

        then:
        noExceptionThrown()

        where:
        array << [
                null, 10, -1.14, "", "alpha",
                [false, true] as boolean[],
                [Byte.MIN_VALUE, 0, Byte.MAX_VALUE] as byte[],
                [Short.MIN_VALUE, 0, Short.MAX_VALUE] as short[],
                [Character.MIN_VALUE, 955, Character.MAX_VALUE] as char[],
                [Integer.MIN_VALUE, 0, Integer.MAX_VALUE] as int[],
                [Long.MIN_VALUE, 0, Long.MAX_VALUE] as long[],
                [-1.141F, 0, 1.141F] as float[],
                [-3.141592D, 0, 3.141592D] as double[],
                [null, [1, 2], [], [3, 4]] as int[][],
        ]
    }

    def "Prepends elements to array"() {
        when:
        def actual = ArrayUtils.prepend(src.toArray(), elements?.toArray())

        then:
        actual == expected.toArray()

        where:
        src                        | elements           || expected
        [0]                        | null               || [0]
        [0, 1]                     | []                 || [0, 1]
        []                         | [null, null]       || [null, null]
        ['α', 'β', 'γ']            | ['δ', 'Ω']         || ['δ', 'Ω', 'α', 'β', 'γ']
        [0.0, 0.1, 0.2]            | [0.0F, 0.1F, 0.2F] || [0.0F, 0.1F, 0.2F, 0.0, 0.1, 0.2]
        ["alpha", "beta", "gamma"] | ['Y', 'Z']         || ['Y', 'Z', "alpha", "beta", "gamma"]
    }

    def "Appends elements to array"() {
        when:
        def actual = ArrayUtils.append(src.toArray(), elements?.toArray())

        then:
        actual == expected.toArray()

        where:
        src                        | elements           || expected
        [0]                        | null               || [0]
        [0, 1]                     | []                 || [0, 1]
        []                         | [null, null]       || [null, null]
        ['α', 'β', 'γ']            | ['δ', 'Ω']         || ['α', 'β', 'γ', 'δ', 'Ω']
        [0.0, 0.1, 0.2]            | [0.0F, 0.1F, 0.2F] || [0.0, 0.1, 0.2, 0.0F, 0.1F, 0.2F]
        ["alpha", "beta", "gamma"] | ['Y', 'Z']         || ["alpha", "beta", "gamma", 'Y', 'Z']
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    def "Resolves array type in short-time"() {
        given:
        def data = [
                [type: boolean, dimension: 1, expected: boolean[]],
                [type: byte, dimension: 2, expected: byte[][]],
                [type: short, dimension: 3, expected: short[][][]],
                [type: char, dimension: 4, expected: char[][][][]],
                [type: int, dimension: 5, expected: int[][][][][]],
                [type: long, dimension: 6, expected: long[][][][][][]],
                [type: float, dimension: 7, expected: float[][][][][][][]],
                [type: double, dimension: 8, expected: double[][][][][][][][]],
                [type: Void, dimension: 0, expected: Void],
                [type: Object, dimension: 1, expected: Object[]],
                [type: Boolean[], dimension: 1, expected: Boolean[][]],
                [type: Byte[], dimension: 2, expected: Byte[][][]],
                [type: Short[][], dimension: 2, expected: Short[][][][]],
                [type: Character[][], dimension: 3, expected: Character[][][][][]],
                [type: Integer[][][], dimension: 3, expected: Integer[][][][][][]],
                [type: Long[][][], dimension: 4, expected: Long[][][][][][][]],
                [type: Float[][][][], dimension: 4, expected: Float[][][][][][][][]],
                [type: Double[][][][], dimension: 5, expected: Double[][][][][][][][][]],
                [type: String[][][][][], dimension: 5, expected: String[][][][][][][][][][]],
        ]

        when:
        def result = data.collect({ ArrayUtils.resolveArrayType(it.type, it.dimension) == it.expected })

        then:
        result.stream().reduce(Boolean.TRUE, { a, b -> a && b })

        where:
        i << (0..2000)
    }

    def "Resolves the actual component type of array"() {
        when:
        def actual = ArrayUtils.resolveActualComponentType type

        then:
        actual == expected

        where:
        type                       | expected
        void                       | type
        Object                     | type
        Void[]                     | Void
        boolean[]                  | boolean
        byte[][]                   | byte
        short[][][]                | short
        char[][][][]               | char
        int[][][][][]              | int
        long[][][][][][]           | long
        float[][][][][][][]        | float
        double[][][][][][][][]     | double
        Object[]                   | Object
        Boolean[][]                | Boolean
        Byte[][][]                 | Byte
        Short[][][][]              | Short
        Character[][][][][]        | Character
        Integer[][][][][][]        | Integer
        Long[][][][][][][]         | Long
        Float[][][][][][][][]      | Float
        Double[][][][][][][][][]   | Double
        String[][][][][][][][][][] | String
    }

    def "Gets dimension of array type"() {
        when:
        def actual = ArrayUtils.dimensionOf type

        then:
        actual == expected

        where:
        type                       | expected
        boolean                    | 0
        byte[]                     | 1
        short[][]                  | 2
        char[][][]                 | 3
        int[][][][]                | 4
        long[][][][][]             | 5
        float[][][][][][]          | 6
        double[][][][][][][]       | 7
        Void                       | 0
        Object[]                   | 1
        Boolean[][]                | 2
        Byte[][][]                 | 3
        Short[][][][]              | 4
        Character[][][][][]        | 5
        Integer[][][][][][]        | 6
        Long[][][][][][][]         | 7
        Float[][][][][][][][]      | 8
        Double[][][][][][][][][]   | 9
        String[][][][][][][][][][] | 10
    }

}
