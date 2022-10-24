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

package io.github.imsejin.common.assertion

import io.github.imsejin.common.tool.ClassFinder
import io.github.imsejin.common.util.ClassUtils
import spock.lang.Specification
import spock.lang.Unroll

class DescriptorSpec extends Specification {

    @Unroll("type: #type.simpleName")
    def "Creates new instance and copies properties of the given descriptor"() {
        given:
        def origin = new Descriptor() {}
        origin.describedAs("description: {0}", origin.class.name)
        origin.thrownBy(UnsupportedOperationException.&new)

        when:
        def clone = type.newInstance(origin, null)

        then:
        def e = clone.exception
        clone.@self != origin
        clone.@self == clone
        e.message == "description: $origin.class.name"
        e.class == UnsupportedOperationException

        where: "Resolves all the implementations which has merging constructor from Descriptor"
        type << ClassFinder.getAllSubtypes(Descriptor).findAll {
            if (ClassUtils.isAbstractClass(it)) return false

            def constructor = it.declaredConstructors.find {
                it.parameterCount == 2 && it.parameterTypes[0] == Descriptor
            }
            constructor != null && it.getDeclaredConstructor(Descriptor, constructor.parameterTypes[1]) != null
        }
    }

}
