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

package io.github.imsejin.common.tool

import spock.lang.Specification

import java.time.*
import java.time.temporal.Temporal

class TypeClassifierSpec extends Specification {

    def "Checks if type is temporal"() {
        when:
        def actual = TypeClassifier.isTemporal type

        then:
        actual == expected

        where:
        type           | expected
        LocalTime      | true
        LocalDate      | true
        LocalDateTime  | true
        ZonedDateTime  | true
        OffsetDateTime | true
        OffsetTime     | true
        Temporal       | false
        String         | false
        Date           | false
        Calendar       | false
    }

    def "Checks if type is numeric primitive"() {
        when:
        def actual = TypeClassifier.isNumericPrimitive type

        then:
        actual == expected

        where:
        type    | expected
        byte    | true
        short   | true
        int     | true
        long    | true
        float   | true
        double  | true
        Byte    | false
        Short   | false
        Integer | false
        Long    | false
        Float   | false
        Double  | false
    }

    def "Checks if type is numeric wrapper"() {
        when:
        def actual = TypeClassifier.isNumericWrapper type

        then:
        actual == expected

        where:
        type    | expected
        Byte    | true
        Short   | true
        Integer | true
        Long    | true
        Float   | true
        Double  | true
        byte    | false
        short   | false
        int     | false
        long    | false
        float   | false
        double  | false
    }

    def "Checks if type is numeric"() {
        when:
        def actual = TypeClassifier.isNumeric type

        then:
        actual == expected

        where:
        type    | expected
        byte    | true
        short   | true
        int     | true
        long    | true
        float   | true
        double  | true
        Byte    | true
        Short   | true
        Integer | true
        Long    | true
        Float   | true
        Double  | true
        String  | false
        Object  | false
    }

    def "Checks if type is string"() {
        when:
        def actual = TypeClassifier.isString type

        then:
        actual == expected

        where:
        type              | expected
        String            | true
        ''.getClass()     | true
        "".getClass()     | true
        """""".getClass() | true
        CharSequence      | false
        char              | false
        byte              | false
        short             | false
        int               | false
        long              | false
        float             | false
        double            | false
        Character         | false
        Byte              | false
        Short             | false
        Integer           | false
        Long              | false
        Float             | false
        Double            | false
        Object            | false
    }

    def "Checks if type is primitive"() {
        when:
        def actual = TypeClassifier.isPrimitive type

        then:
        actual == expected

        where:
        type      | expected
        byte      | true
        short     | true
        int       | true
        long      | true
        float     | true
        double    | true
        char      | true
        boolean   | true
        void      | true
        Byte      | false
        Short     | false
        Integer   | false
        Long      | false
        Float     | false
        Double    | false
        Character | false
        Boolean   | false
        Void      | false
    }

    def "Checks if type is wrapper"() {
        when:
        def actual = TypeClassifier.isWrapper type

        then:
        actual == expected

        where:
        type      | expected
        Byte      | true
        Short     | true
        Integer   | true
        Long      | true
        Float     | true
        Double    | true
        Character | true
        Boolean   | true
        Void      | true
        byte      | false
        short     | false
        int       | false
        long      | false
        float     | false
        double    | false
        char      | false
        boolean   | false
        void      | false
    }

    def "Makes primitive type boxed"() {
        when:
        def actual = TypeClassifier.box primitiveType

        then:
        actual == expected

        where:
        primitiveType | expected
        byte          | Byte
        short         | Short
        int           | Integer
        long          | Long
        float         | Float
        double        | Double
        char          | Character
        boolean       | Boolean
        void          | Void
    }

    def "Makes wrapper type unboxed"() {
        when:
        def actual = TypeClassifier.unbox wrapperType

        then:
        actual == expected

        where:
        wrapperType | expected
        Byte        | byte
        Short       | short
        Integer     | int
        Long        | long
        Float       | float
        Double      | double
        Character   | char
        Boolean     | boolean
        Void        | void
    }

}
