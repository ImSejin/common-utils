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

package io.github.imsejin.common.tool

import io.github.imsejin.common.constant.Locales
import spock.lang.Specification

class RandomStringSpec extends Specification {

    def "Generates a random string with fixed length"() {
        given:
        def randomString = new RandomString()

        when:
        def actual = randomString.nextString(length)

        then:
        actual != null
        actual.length() == length
        actual.matches("[A-Za-z]{$length}")

        where:
        length << (1..1024)
    }

    def "Generates a random string with random length"() {
        given:
        def randomString = new RandomString()

        when:
        def actual = randomString.nextString(origin, bound)

        then:
        actual != null
        origin <= actual.length()
        actual.length() < bound
        actual.matches("[A-Za-z]{$origin,${bound - 1}}")

        where:
        origin | bound
        1      | 2
        2      | 10
        1      | 128
        10     | 20
        16     | 64
        1      | 1024
    }

    def "Generates a random string of locale with random length"() {
        given:
        def random = new Random()
        def randomString = new RandomString(random, locale)

        when:
        def length = Math.max(1, random.nextInt(100))
        def actual = randomString.nextString(length)

        then:
        actual != null
        actual.length() == length
        actual.matches(regex)

        where:
        locale           | regex
        Locales.ARABIC   | "[\u0600-\u06FF]+"
        Locales.CHINESE  | "[\u2E80-\u2FD5\u3190-\u319f\u3400-\u4DBF\u4E00-\u9FCC\uF900-\uFAAD]+"
        Locales.ENGLISH  | "[A-Za-z]+"
        Locales.HEBREW   | "[\u0590-\u05FF]+"
        Locales.HINDI    | "[\u0900-\u097F\u0980-\u09FF\u0A00-\u0A7F\u0A80-\u0AFF\u0B00-\u0B7F\u0B80-\u0BFF\u0C00-\u0C7F\u0C80-\u0CFF\u0D00-\u0D7F]+"
        Locales.JAPANESE | "[\u3041-\u3096\u30A1-\u30FA]+"
        Locales.KOREAN   | "[\uAC00-\uD7A3]+"
    }

    def "Failed to generate a random string"() {
        given:
        def randomString = new RandomString()
        def length = new Random().nextInt(10) * -1

        when:
        randomString.nextString(length)

        then:
        def e = thrown IllegalArgumentException
        e.message == "The length of random string must be positive, but it isn't: $length"

        when:
        def origin = length
        def bound = length * -1
        randomString.nextString(origin, bound)

        then:
        e = thrown IllegalArgumentException
        e.message == "Origin must be positive, but it isn't: $length"

        when:
        origin = length * -1 + 1
        bound = length * -1
        randomString.nextString(origin, bound)

        then:
        e = thrown IllegalArgumentException
        e.message == "Bound must be greater than origin, but it isn't. (origin: $origin, bound: $bound)"

        where:
        i << (1..1024)
    }

    def "Converts integers as ranges of code point"() {
        given:
        integers = integers.sort()

        when:
        List<String> ranges = RandomString.convertAsRanges(integers)

        then:
        ranges != null
        ranges.size() == expected.size()
        ranges == expected

        where:
        integers                                         | expected
        []                                               | []
        [0]                                              | ["0"]
        [1, 2, 6, 7, 9, 10, 105, 109]                    | ["1-2", "6-7", "9-10", "105", "109"]
        [2, 4, 8, 16, 32, 64, 128]                       | ["2", "4", "8", "16", "32", "64", "128"]
        [51, 52, 53, 54, 55, 56, 57]                     | ["51-57"]
        [6480, 6481, 6482, 6483, 6488, 6490, 6491, 6492] | ["6480-6483", "6488", "6490-6492"]
    }

}
