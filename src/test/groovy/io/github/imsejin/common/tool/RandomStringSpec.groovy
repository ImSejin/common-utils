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

    def "Generates a random string of locale with random length"() {
        given:
        def random = new Random()
        def randomString = new RandomString(random, locale)

        when:
        def length = random.nextInt(100) + 1
        def actual = randomString.nextString(length)

        then:
        actual != null
        actual.length() == length
        actual.matches(regex)

        where:
        locale           | regex
        Locale.CHINESE   | "[\u2E80-\u2FD5\u3190-\u319f\u3400-\u4DBF\u4E00-\u9FCC\uF900-\uFAAD]+"
        Locale.ENGLISH   | "[A-Za-z]+"
        new Locale("iw") | "[\u0590-\u05FF]+"
        new Locale("hi") | "[\u0900-\u097F\u0980-\u09FF\u0A00-\u0A7F\u0A80-\u0AFF\u0B00-\u0B7F\u0B80-\u0BFF\u0C00-\u0C7F\u0C80-\u0CFF\u0D00-\u0D7F]+"
        Locale.JAPANESE  | "[\u3041-\u3096\u30A1-\u30FA]+"
        Locale.KOREAN    | "[\uAC00-\uD7A3]+"
    }

    def "Failed to generate a random string"() {
        given:
        def randomString = new RandomString()

        when:
        randomString.nextString(length)

        then:
        def e = thrown IllegalArgumentException
        e.message == "The length of random string must be positive, but it isn't: $length"

        where:
        length << [-1, 0]
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
