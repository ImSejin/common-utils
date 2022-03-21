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
        array                      | expected
        null                       | array
        [] as Object[]             | array
        [new Object()] as Object[] | array
        [] as boolean[]            | [] as Boolean[]
        [false, true] as boolean[] | [false, true] as Boolean[]
        [] as byte[]               | [] as Byte[]
        [-1, 0, 1] as byte[]       | [-1, 0, 1] as Byte[]
        [] as short[]              | [] as Short[]
        [-1, 0, 1] as short[]      | [-1, 0, 1] as Short[]
        [] as char[]               | [] as Character[]
        ['a', 'b', 'c'] as char[]  | ['a', 'b', 'c'] as Character[]
        [] as int[]                | [] as Integer[]
        [-1, 0, 1] as int[]        | [-1, 0, 1] as Integer[]
        [] as long[]               | [] as Long[]
        [-1, 0, 1] as long[]       | [-1, 0, 1] as Long[]
        [] as float[]              | [] as Float[]
        [-1, 0, 1] as float[]      | [-1, 0, 1] as Float[]
        [] as double[]             | [] as Double[]
        [-1, 0, 1] as double[]     | [-1, 0, 1] as Double[]
    }

    def "Makes wrapper array unboxed"() {
        when:
        def actual = ArrayUtils.unwrap array

        then:
        actual?.class == expected?.class
        actual == expected

        where:
        array                          | expected
        null                           | array
        [] as Object[]                 | array
        [new Object()] as Object[]     | array
        [] as Boolean[]                | [] as boolean[]
        [false, true] as Boolean[]     | [false, true] as boolean[]
        [] as Byte[]                   | [] as byte[]
        [-1, 0, 1] as Byte[]           | [-1, 0, 1] as byte[]
        [] as Short[]                  | [] as short[]
        [-1, 0, 1] as Short[]          | [-1, 0, 1] as short[]
        [] as Character[]              | [] as char[]
        ['a', 'b', 'c'] as Character[] | ['a', 'b', 'c'] as char[]
        [] as Integer[]                | [] as int[]
        [-1, 0, 1] as Integer[]        | [-1, 0, 1] as int[]
        [] as Long[]                   | [] as long[]
        [-1, 0, 1] as Long[]           | [-1, 0, 1] as long[]
        [] as Double[]                 | [] as double[]
        [-1, 0, 1] as Double[]         | [-1, 0, 1] as double[]
        [] as Float[]                  | [] as float[]
        [-1, 0, 1] as Float[]          | [-1, 0, 1] as float[]
    }

    def "Converts array to string"() {
        expect:
        ArrayUtils.toString(value) == expected

        where:
        value                                                                     | expected
        null                                                                      | "null"
        [false, true] as boolean[]                                                | "[false, true]"
        [Byte.MIN_VALUE, 0, Byte.MAX_VALUE] as byte[]                             | "[-128, 0, 127]"
        [Short.MIN_VALUE, 0, Short.MAX_VALUE] as short[]                          | "[-32768, 0, 32767]"
        [Character.MIN_VALUE, (char) (955 as int), Character.MAX_VALUE] as char[] | "[\u0000, λ, \uffff]"
        [Integer.MIN_VALUE, 0, Integer.MAX_VALUE] as int[]                        | "[-2147483648, 0, 2147483647]"
        [Long.MIN_VALUE, 0, Long.MAX_VALUE] as long[]                             | "[-9223372036854775808, 0, 9223372036854775807]"
        [-1.141F, 0, 1.141F] as float[]                                           | "[-1.141, 0.0, 1.141]"
        [-3.141592D, 0, 3.141592D] as double[]                                    | "[-3.141592, 0.0, 3.141592]"
        [].toArray()                                                              | "[]"
        ['a', 'b'] as char[]                                                      | "[a, b]"
        [[2], null, []] as int[][]                                                | "[[2], null, []]"
        [1, "alpha", -0.5]                                                        | "[1, alpha, -0.5]"
        [' ', [0], [0, 1]]                                                        | "[ , [0], [0, 1]]"
        new HashMap<>()                                                           | "{}"
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
    @Timeout(value = 15, unit = TimeUnit.MILLISECONDS)
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
                [type: Object, dimension: 1, expected: Object[]],
                [type: Boolean, dimension: 2, expected: Boolean[][]],
                [type: Byte, dimension: 3, expected: Byte[][][]],
                [type: Short, dimension: 4, expected: Short[][][][]],
                [type: Character, dimension: 5, expected: Character[][][][][]],
                [type: Integer, dimension: 6, expected: Integer[][][][][][]],
                [type: Long, dimension: 7, expected: Long[][][][][][][]],
                [type: Float, dimension: 8, expected: Float[][][][][][][][]],
                [type: Double, dimension: 9, expected: Double[][][][][][][][][]],
                [type: String, dimension: 10, expected: String[][][][][][][][][][]],
                [type: List[], dimension: 11, expected: List[][][][][][][][][][][][]],
        ]

        when:
        def result = data.collect({ ArrayUtils.resolveArrayType(it.type, it.dimension) == it.expected })

        then:
        result.stream().reduce(Boolean.TRUE, { a, b -> a && b })

        where:
        i << (0..2000)
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
