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

import java.nio.file.AccessMode

import io.github.imsejin.common.internal.assertion.model.Bar
import io.github.imsejin.common.internal.assertion.model.Foo
import io.github.imsejin.common.internal.assertion.model.KanCode
import io.github.imsejin.common.internal.assertion.model.Qux
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
        given:
        Qux.mode = AccessMode.READ
        def qux = new Qux(id: 100, name: "alpha")

        when:
        def id = ReflectionUtils.getFieldValue(qux, qux.class.getDeclaredField("id"))

        then:
        qux.id == id

        when:
        def mode = ReflectionUtils.getFieldValue(null, qux.class.getDeclaredField("mode"))

        then:
        Qux.mode == mode
    }

    def "Sets field value"() {
        given:
        Qux.mode = AccessMode.READ
        def qux = new Qux(id: 100, name: "alpha")

        when:
        ReflectionUtils.setFieldValue(qux, qux.class.getDeclaredField("id"), 200)

        then:
        qux.id == 200

        when:
        ReflectionUtils.setFieldValue(null, qux.class.getDeclaredField("mode"), AccessMode.WRITE)

        then:
        Qux.mode == AccessMode.WRITE
    }

    def "Gets declared constructor"() {
        when:
        def constructor = ReflectionUtils.getDeclaredConstructor(type, params as Class<?>[])

        then:
        constructor.declaringClass == type

        where:
        type    | params
        KanCode | [String]
        Foo     | []
        Foo     | [String]
        Bar     | []
        Bar     | [String]
        Qux     | []
        Qux     | [Integer, String]
        Parent  | []
        Child   | []
        A       | []
        AA      | []
        AB      | [A]
        B       | [this.class]
        BA      | [B]
    }

    def "Creates an instance"() {
        when:
        def instance = {
            if (args.empty) {
                return ReflectionUtils.instantiate(type)
            } else {
                def constructor = ReflectionUtils.getDeclaredConstructor(type, params as Class<?>[])
                return ReflectionUtils.instantiate(constructor, args as Object[])
            }
        }.call()

        then:
        instance != null
        instance.class == type

        where:
        type    | params            | args
        KanCode | [String]          | ["01020304"]
        Foo     | []                | []
        Foo     | [String]          | ["alpha"]
        Bar     | []                | []
        Bar     | [String]          | ["beta"]
        Qux     | []                | []
        Qux     | [Integer, String] | [100, "gamma"]
    }

    def "Gets declared method"() {
        when:
        def method = ReflectionUtils.getDeclaredMethod(type, name, params as Class<?>[])

        then:
        method.declaringClass == type

        where:
        type    | name             | params
        KanCode | "getDepth"       | []
        Foo     | "getValue"       | []
        Bar     | "getCreatedTime" | []
        Qux     | "getMode"        | []
        Qux     | "setMode"        | [AccessMode]
        Qux     | "getId"          | []
        Qux     | "setId"          | [Integer]
        Qux     | "getName"        | []
        Qux     | "setName"        | [String]
    }

    def "Calls the executable"() {
        given:
        def executable = name
                ? ReflectionUtils.getDeclaredMethod(type, name, params as Class<?>[])
                : ReflectionUtils.getDeclaredConstructor(type, params as Class<?>[])

        when:
        ReflectionUtils.execute(executable, instance, args as Object[])

        then:
        noExceptionThrown()

        where:
        type    | name             | params            | instance                | args
        // Constructors
        KanCode | null             | [String]          | null                    | ["01020304"]
        Foo     | null             | []                | null                    | []
        Foo     | null             | [String]          | null                    | ["foo"]
        Bar     | null             | []                | null                    | []
        Bar     | null             | [String]          | null                    | ["bar"]
        Qux     | null             | []                | null                    | []
        Qux     | null             | [Integer, String] | null                    | [200, "qux"]
        // Methods
        KanCode | "getDepth"       | []                | new KanCode("01020304") | []
        Foo     | "getValue"       | []                | new Foo()               | []
        Bar     | "getCreatedTime" | []                | new Bar()               | []
        Qux     | "setMode"        | [AccessMode]      | null                    | [AccessMode.EXECUTE]
        Qux     | "setId"          | [Integer]         | new Qux()               | [300]
        Qux     | "setName"        | [String]          | new Qux()               | ["beta"]
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

    private static class Parent {
        private static final int a = 809968079
        private char b
        private String c

        private static class Child extends Parent {
            private final long a = 9876543210
        }
    }

}
