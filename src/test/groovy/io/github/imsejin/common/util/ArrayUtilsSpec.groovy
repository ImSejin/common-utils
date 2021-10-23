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
        def actual = ArrayUtils.box array

        then:
        actual == expected

        where:
        array                      | expected
        [] as boolean[]            | [] as Boolean[]
        [false, true] as boolean[] | [false, true] as Boolean[]
        [] as byte[]               | [] as Byte[]
        [-1, 0, 1] as byte[]       | [-1, 0, 1] as Byte[]
        [] as char[]               | [] as Character[]
        ['a', 'b', 'c'] as char[]  | ['a', 'b', 'c'] as Character[]
        [] as double[]             | [] as Double[]
        [-1, 0, 1] as double[]     | [-1, 0, 1] as Double[]
        [] as float[]              | [] as Float[]
        [-1, 0, 1] as float[]      | [-1, 0, 1] as Float[]
        [] as int[]                | [] as Integer[]
        [-1, 0, 1] as int[]        | [-1, 0, 1] as Integer[]
        [] as long[]               | [] as Long[]
        [-1, 0, 1] as long[]       | [-1, 0, 1] as Long[]
        [] as short[]              | [] as Short[]
        [-1, 0, 1] as short[]      | [-1, 0, 1] as Short[]
    }

    def "Makes wrapper array unboxed"() {
        when:
        def actual = ArrayUtils.unbox array

        then:
        actual == expected

        where:
        array                          | expected
        [] as Boolean[]                | [] as boolean[]
        [false, true] as Boolean[]     | [false, true] as boolean[]
        [] as Byte[]                   | [] as byte[]
        [-1, 0, 1] as Byte[]           | [-1, 0, 1] as byte[]
        [] as Character[]              | [] as char[]
        ['a', 'b', 'c'] as Character[] | ['a', 'b', 'c'] as char[]
        [] as Double[]                 | [] as double[]
        [-1, 0, 1] as Double[]         | [-1, 0, 1] as double[]
        [] as Float[]                  | [] as float[]
        [-1, 0, 1] as Float[]          | [-1, 0, 1] as float[]
        [] as Integer[]                | [] as int[]
        [-1, 0, 1] as Integer[]        | [-1, 0, 1] as int[]
        [] as Long[]                   | [] as long[]
        [-1, 0, 1] as Long[]           | [-1, 0, 1] as long[]
        [] as Short[]                  | [] as short[]
        [-1, 0, 1] as Short[]          | [-1, 0, 1] as short[]
    }

    def "Converts array to string"() {
        expect:
        ArrayUtils.toString(value) == expected

        where:
        value              | expected
        null               | "null"
        [].toArray()       | "[]"
        [1, "alpha", -0.5] | "[1, alpha, -0.5]"
        [' ', [0], [0, 1]] | "[ , [0], [0, 1]]"
        new HashMap<>()    | "{}"
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

}
