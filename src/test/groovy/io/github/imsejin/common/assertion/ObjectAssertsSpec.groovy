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

package io.github.imsejin.common.assertion

import spock.lang.Specification

class ObjectAssertsSpec extends Specification {

    def "expecting target is null but it is not null, and throw exception"() {
        when:
        Asserts.that(target).isNull()

        then:
        thrown IllegalArgumentException

        where:
        target << [new Object(), "", 'a', 3.14, IllegalArgumentException]
    }

    def "expecting target is null and it is"() {
        given:
        def target = null

        when:
        Asserts.that(target).isNull()

        then:
        true // nothing
    }

    def "expecting target is not null but it is null, and throw exception"() {
        given:
        def target = null

        when:
        Asserts.that(target).isNotNull()

        then:
        thrown IllegalArgumentException
    }

    def "expecting target is not null but it is"() {
        when:
        Asserts.that(target).isNotNull()

        then:
        true // nothing

        where:
        target << [new Object(), "", 'a', 3.14, IllegalArgumentException]
    }

    def "expecting target and the other is the same but it isn't, and throw exception"() {
        when:
        Asserts.that(target).isSameAs other

        then:
        thrown IllegalArgumentException

        where:
        target  | other
        null    | 'b'
        "alpha" | new String("alpha")
        'b'     | 3.14
        3.14    | null
    }

    def "expecting target and the other is the same"() {
        when:
        Asserts.that(target).isSameAs other

        then:
        true // nothing

        where:
        target  | other
        null    | null
        "alpha" | "alpha"
        'b'     | 'b'
        3.14    | 3.14
    }

    def "expecting target is equal to the other but it isn't, and throw exception"() {
        when:
        Asserts.that(target).isEqualTo other

        then:
        thrown IllegalArgumentException

        where:
        target       | other
        new Object() | new Object()
        "alpha"      | "beta"
        'b'          | 'c'
        3.14         | 3.141592
    }

    def "expecting target is equal to the other but it is"() {
        when:
        Asserts.that(target).isEqualTo other

        then:
        true // nothing

        where:
        target                   | other
        "alpha"                  | new String("alpha")
        '\n'                     | '\n'
        3.14                     | 3.14
        BigInteger.valueOf(1000) | BigInteger.valueOf(1000)
    }

    def "expecting target is not equal to the other but it isn't, and throw exception"() {
        when:
        Asserts.that(target).isNotEqualTo other

        then:
        thrown IllegalArgumentException

        where:
        target                   | other
        "alpha"                  | new String("alpha")
        '\n'                     | '\n'
        3.14                     | 3.14
        BigInteger.valueOf(1000) | BigInteger.valueOf(1000)
    }

    def "expecting target is not equal to the other but it is"() {
        when:
        Asserts.that(target).isNotEqualTo other

        then:
        true // nothing

        where:
        target       | other
        new Object() | new Object()
        "alpha"      | "beta"
        'b'          | 'c'
        3.14         | 3.141592
    }

    def "expecting target is instance of the type but it is"() {
        when:
        Asserts.that(target).isInstanceOf type

        then:
        thrown IllegalArgumentException

        where:
        target                   | type
        new Object()             | BigDecimal
        "alpha"                  | Character
        3.14                     | BigInteger
        BigInteger.valueOf(1000) | String
    }

    def "expecting target is instance of the type but it isn't, and throw exception"() {
        when:
        Asserts.that(target).isInstanceOf type

        then:
        true // nothing

        where:
        target                   | type
        new Object()             | Object
        "alpha"                  | String
        3.14                     | BigDecimal
        BigInteger.valueOf(1000) | BigInteger
    }

}
