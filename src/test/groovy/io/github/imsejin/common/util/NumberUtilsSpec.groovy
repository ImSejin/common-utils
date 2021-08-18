/*
 * Copyright 2020 Sejin Im
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

class NumberUtilsSpec extends Specification {

    def "Get number of places with long"() {
        when:
        def numOfPlaces = NumberUtils.getNumOfPlaces(number)

        then:
        if (number == Integer.MIN_VALUE || number == Long.MIN_VALUE) number++
        numOfPlaces == String.valueOf(Math.abs(number)).length()

        where:
        number << [Long.MIN_VALUE, Integer.MIN_VALUE, -50_000, -1, 0, 1, 50_000, Integer.MAX_VALUE, Long.MAX_VALUE]
    }

    def "Get number of places with BigInteger"() {
        when:
        def numOfPlaces = NumberUtils.getNumOfPlaces(new BigInteger(number))

        then:
        numOfPlaces == number.replace("-", "").length()

        where:
        number << ["-115234155123123413842342342024623440", "-5", "0", "9", "1505512411489465416645571849602523405834510"]
    }

    def "Check whether number has decimal part"() {
        when:
        def actual = NumberUtils.hasDecimalPart number

        then:
        actual == expected

        where:
        number               | expected
        -1.41421356237       | true
        0.5                  | true
        3.141592653589793238 | true
        Float.MIN_VALUE      | true
        Double.MIN_VALUE     | true
        Double.MAX_VALUE     | false
        Float.MAX_VALUE      | false
        Long.MIN_VALUE       | false
        Long.MAX_VALUE       | false
        Integer.MIN_VALUE    | false
        Integer.MAX_VALUE    | false
        64.0                 | false
        0.0                  | false
        -128.0               | false
    }

}
