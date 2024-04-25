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

import io.github.imsejin.common.util.ReflectionUtilsSpec.A.AA
import io.github.imsejin.common.util.ReflectionUtilsSpec.A.AB
import io.github.imsejin.common.util.ReflectionUtilsSpec.B.BA
import io.github.imsejin.common.util.ReflectionUtilsSpec.Parent.Child

class ReflectionUtilsSpec extends Specification {

    def "Gets inherited fields"() {
        when:
        def fields = ReflectionUtils.getInheritedFields(type)

        then:
        fields.every { field ->
            expected.find { it.name == field.name && it.type == field.type }
        }

        where:
        type   | expected
        Parent | [[name: "a", type: int], [name: "b", type: char], [name: "c", type: String]]
        Child  | [[name: "a", type: int], [name: "b", type: char], [name: "c", type: String], [name: "a", type: long]]
        A      | []
        AA     | []
        AB     | []
        B      | []
        BA     | []
    }

    def "Gets field value"() {
    }

    def "Sets field value"() {
    }

    def "Gets declared constructor"() {
    }

    def "Instantiates"() {
    }

    def "Tests instantiate"() {
    }

    def "Gets declared method"() {
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

    private class Parent {
        private static final int a = 809968079
        private char b
        private String c

        private static class Child extends Parent {
            private final long a = 9876543210
        }
    }

}
