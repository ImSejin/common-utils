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

package io.github.imsejin.common.util

import io.github.imsejin.common.assertion.DecimalNumberAssertion
import io.github.imsejin.common.assertion.Descriptor
import io.github.imsejin.common.assertion.array.ArrayAssert
import io.github.imsejin.common.assertion.chars.AbstractCharSequenceAssert
import io.github.imsejin.common.assertion.chars.StringAssert
import io.github.imsejin.common.assertion.math.BigDecimalAssert
import io.github.imsejin.common.assertion.object.AbstractObjectAssert
import io.github.imsejin.common.assertion.primitive.NumberAssert
import io.github.imsejin.common.tool.crypto.AES
import io.github.imsejin.common.tool.crypto.AES256
import io.github.imsejin.common.tool.crypto.Crypto
import spock.lang.Specification

class ClassUtilsSpec extends Specification {

    def "Checks if type is wrapper class"() {
        when:
        def actual = ClassUtils.isWrapper type

        then:
        actual == expected

        where:
        type       | expected
        null       | false
        void       | false
        boolean    | false
        byte       | false
        short      | false
        char       | false
        int        | false
        long       | false
        float      | false
        double     | false
        Void       | true
        Boolean    | true
        Byte       | true
        Short      | true
        Character  | true
        Integer    | true
        Long       | true
        Float      | true
        Double     | true
        BigInteger | false
        BigDecimal | false
        Object     | false
        String     | false
    }

    def "Checks if type is numeric"() {
        when:
        def actual = ClassUtils.isNumeric type

        then:
        actual == expected

        where:
        type       | expected
        null       | false
        void       | false
        boolean    | false
        byte       | true
        short      | true
        char       | false
        int        | true
        long       | true
        float      | true
        double     | true
        Void       | false
        Boolean    | false
        Byte       | true
        Short      | true
        Character  | false
        Integer    | true
        Long       | true
        Float      | true
        Double     | true
        BigInteger | false
        BigDecimal | false
        Object     | false
        String     | false
    }

    def "Checks if type is numeric primitive"() {
        when:
        def actual = ClassUtils.isNumericPrimitive type

        then:
        actual == expected

        where:
        type       | expected
        null       | false
        void       | false
        boolean    | false
        byte       | true
        short      | true
        char       | false
        int        | true
        long       | true
        float      | true
        double     | true
        Void       | false
        Boolean    | false
        Byte       | false
        Short      | false
        Character  | false
        Integer    | false
        Long       | false
        Float      | false
        Double     | false
        BigInteger | false
        BigDecimal | false
        Object     | false
        String     | false
    }

    def "Checks if type is numeric wrapper class"() {
        when:
        def actual = ClassUtils.isNumericWrapper type

        then:
        actual == expected

        where:
        type       | expected
        null       | false
        void       | false
        boolean    | false
        byte       | false
        short      | false
        char       | false
        int        | false
        long       | false
        float      | false
        double     | false
        Void       | false
        Boolean    | false
        Byte       | true
        Short      | true
        Character  | false
        Integer    | true
        Long       | true
        Float      | true
        Double     | true
        BigInteger | false
        BigDecimal | false
        Object     | false
        String     | false
    }

    def "Get initial value of the given type"() {
        when:
        def actual = ClassUtils.initialValueOf type

        then:
        actual == expected

        where:
        type       | expected
        null       | null
        void       | null
        boolean    | false
        byte       | 0
        short      | 0
        char       | '\u0000' as char
        int        | 0
        long       | 0
        float      | 0
        double     | 0
        Void       | null
        Boolean    | null
        Byte       | null
        Short      | null
        Character  | null
        Integer    | null
        Long       | null
        Float      | null
        Double     | null
        BigInteger | null
        BigDecimal | null
        Object     | null
        String     | null
    }

    def "Makes primitive type boxed"() {
        when:
        def actual = ClassUtils.wrap type

        then:
        actual == expected

        where:
        type       | expected
        null       | type
        void       | Void
        boolean    | Boolean
        byte       | Byte
        short      | Short
        char       | Character
        int        | Integer
        long       | Long
        float      | Float
        double     | Double
        Void       | type
        Boolean    | type
        Byte       | type
        Short      | type
        Character  | type
        Integer    | type
        Long       | type
        Float      | type
        Double     | type
        BigInteger | type
        BigDecimal | type
        Object     | type
        String     | type
    }

    def "Makes wrapper class unboxed"() {
        when:
        def actual = ClassUtils.unwrap type

        then:
        actual == expected

        where:
        type       | expected
        null       | type
        void       | type
        boolean    | type
        byte       | type
        short      | type
        char       | type
        int        | type
        long       | type
        float      | type
        double     | type
        Void       | void
        Boolean    | boolean
        Byte       | byte
        Short      | short
        Character  | char
        Integer    | int
        Long       | long
        Float      | float
        Double     | double
        BigInteger | type
        BigDecimal | type
        Object     | type
        String     | type
    }

    @SuppressWarnings("GroovyAccessibility")
    def "Gets all the types extended or implemented by the given class as a set"() {
        when:
        def classes = ClassUtils.getAllExtendedOrImplementedTypesAsSet clazz

        then:
        expected == classes as List

        where:
        clazz            | expected
        null             | []
        Object           | [clazz]
        Runnable         | [clazz]
        List             | [clazz, Collection, Iterable]
        ArrayList        | [clazz, List, RandomAccess, Cloneable, Serializable, Collection, Iterable, AbstractList, AbstractCollection]
        TreeMap          | [clazz, NavigableMap, Cloneable, Serializable, SortedMap, Map, AbstractMap]
        AES256           | [clazz, AES, Crypto]
        ArrayAssert      | [clazz, AbstractObjectAssert, Descriptor]
        StringAssert     | [clazz, AbstractCharSequenceAssert, AbstractObjectAssert, Descriptor]
        BigDecimalAssert | [clazz, DecimalNumberAssertion, NumberAssert, AbstractObjectAssert, Descriptor]
    }

    @SuppressWarnings("GroovyAccessibility")
    def "Gets all the types extended or implemented by the given class as a graph"() {
        when:
        def graph = ClassUtils.getAllExtendedOrImplementedTypesAsGraph clazz

        then:
        graph.vertexSize == expected.size()
        graph.allVertices == expected as Set

        where:
        clazz            | expected
        null             | []
        Object           | [clazz]
        Runnable         | [clazz]
        List             | [clazz, Collection, Iterable]
        ArrayList        | [clazz, RandomAccess, List, Serializable, AbstractList, Cloneable, Collection, AbstractCollection, Iterable]
        TreeMap          | [clazz, NavigableMap, AbstractMap, Serializable, Cloneable, SortedMap, Map]
        AES256           | [clazz, AES, Crypto]
        ArrayAssert      | [clazz, AbstractObjectAssert, Descriptor]
        StringAssert     | [clazz, AbstractCharSequenceAssert, AbstractObjectAssert, Descriptor]
        BigDecimalAssert | [clazz, DecimalNumberAssertion, NumberAssert, AbstractObjectAssert, Descriptor]
    }

}
