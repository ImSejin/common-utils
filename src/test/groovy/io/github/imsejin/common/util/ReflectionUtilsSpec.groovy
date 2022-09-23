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

import io.github.imsejin.common.util.ReflectionUtilsSpec.A.AA
import io.github.imsejin.common.util.ReflectionUtilsSpec.A.AB
import io.github.imsejin.common.util.ReflectionUtilsSpec.B.BA
import io.github.imsejin.common.util.ReflectionUtilsSpec.Parent.Child
import lombok.AccessLevel
import lombok.Getter
import lombok.RequiredArgsConstructor
import spock.lang.Specification

import java.time.LocalDateTime

import static java.util.stream.Collectors.toList

class ReflectionUtilsSpec extends Specification {

    def "GetInheritedFields"() {
        when:
        def fields = ReflectionUtils.getInheritedFields type
        def fieldNames = fields.stream().map({ it.name }).collect(toList())

        then:
        fieldNames == expected

        where:
        type   | expected
        Parent | ["id", "name", "createdAt", "modifiedAt"]
        Child  | ["id", "name", "createdAt", "modifiedAt", "id", "title"]
        A      | []
        AA     | []
        AB     | []
        B      | []
        BA     | []
    }

    def "GetFieldValue"() {
    }

    def "SetFieldValue"() {
    }

    def "GetDeclaredConstructor"() {
    }

    def "Instantiate"() {
    }

    def "TestInstantiate"() {
    }

    def "GetDeclaredMethod"() {
    }

    def "Invoke"() {
    }

    // -------------------------------------------------------------------------------------------------

    private static class A {
        private static Long id

        private static class AA {
            private static String name
        }

        private class AB {
        }
    }

    private class B {
        private class BA {
        }
    }

    class Parent implements Serializable {
        static final long serialVersionUID = 8099680797687427324L
        long id
        String name
        LocalDateTime createdAt
        LocalDateTime modifiedAt

        @Getter
        @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
        private static class Child extends Parent {
            private final int id
            final String title
        }
    }

}
