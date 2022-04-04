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

import io.github.imsejin.common.assertion.Descriptor
import io.github.imsejin.common.assertion.composition.DecimalNumberAssertable
import io.github.imsejin.common.assertion.composition.IterationAssertable
import io.github.imsejin.common.assertion.lang.*
import io.github.imsejin.common.assertion.math.BigDecimalAssert
import io.github.imsejin.common.security.crypto.AES
import io.github.imsejin.common.security.crypto.AES256
import io.github.imsejin.common.security.crypto.Crypto
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
        int[]      | Integer[]
        long[]     | Long[]
        float[]    | Float[]
        double[]   | Double[]
        Void       | type
        Boolean    | type
        Byte       | type
        Short      | type
        Character  | type
        Integer[]  | type
        Long[]     | type
        Float[]    | type
        Double[]   | type
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
        int[]      | type
        long[]     | type
        float[]    | type
        double[]   | type
        Void       | void
        Boolean    | boolean
        Byte       | byte
        Short      | short
        Character  | char
        Integer[]  | int[]
        Long[]     | long[]
        Float[]    | float[]
        Double[]   | double[]
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
        ArrayAssert      | [clazz, IterationAssertable, ObjectAssert, Descriptor]
        StringAssert     | [clazz, CharSequenceAssert, ObjectAssert, Descriptor]
        BigDecimalAssert | [clazz, DecimalNumberAssertable, NumberAssert, ObjectAssert, Descriptor]
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
        ArrayAssert      | [clazz, IterationAssertable, ObjectAssert, Descriptor]
        StringAssert     | [clazz, CharSequenceAssert, ObjectAssert, Descriptor]
        BigDecimalAssert | [clazz, DecimalNumberAssertable, NumberAssert, ObjectAssert, Descriptor]
    }

    def "Resolves actual generic type"() {
        given:
        def field = Sample.declaredFields.find({ it.name == fieldName })
        def genericType = field?.genericType

        when:
        def actual = ClassUtils.resolveActualTypes genericType

        then:
        actual == expected

        where:
        fieldName                                          | expected
        null                                               | []
        "concrete"                                         | [String]
        "raw"                                              | [Sample]
        "typeVar"                                          | []
        "typeVarArray"                                     | [Object[]]
        "generic_unknown"                                  | [Object]
        "generic_concrete"                                 | [String]
        "generic_raw"                                      | [Sample]
        "generic_upperWildcard_concrete"                   | [String]
        "generic_lowerWildcard_concrete"                   | [String]
        "generic_typeVar"                                  | []
        "generic_typeVarArray"                             | [Object[]]
        "generic_upperWildcard_typeVar"                    | []
        "generic_lowerWildcard_typeVar"                    | []
        "generic_upperWildcard_typeVarArray"               | [Object[]]
        "generic_lowerWildcard_typeVarArray"               | [Object[]]
        "generic_generic_concrete"                         | [String]
        "generic_generic_concrete_generic_typeVar_unknown" | [String, Object]
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static class Sample<T> extends HashMap<String, T> {
        String concrete
        Sample raw
        T typeVar
        T[] typeVarArray
        List<?> generic_unknown
        List<String> generic_concrete
        List<Sample> generic_raw
        List<? extends String> generic_upperWildcard_concrete
        List<? super String> generic_lowerWildcard_concrete
        List<T> generic_typeVar
        List<T[]> generic_typeVarArray
        List<? extends T> generic_upperWildcard_typeVar
        List<? super T> generic_lowerWildcard_typeVar
        List<? extends T[]> generic_upperWildcard_typeVarArray
        List<? super T[]> generic_lowerWildcard_typeVarArray
        List<Sample<String>> generic_generic_concrete
        List<Map<String, Map<T, ?>>> generic_generic_concrete_generic_typeVar_unknown
    }

}
