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

    def "If number is not positive, returns 1"() {
        expect:
        NumberUtils.toPositive(number as Double) == expected
        NumberUtils.toPositive(number as Float) == expected
        NumberUtils.toPositive(number as Long) == expected
        NumberUtils.toPositive(number as Integer) == expected
        NumberUtils.toPositive(number as Short) == expected
        NumberUtils.toPositive(number as Byte) == expected

        where:
        number | expected
        -128   | 1
        -56.5  | 1
        0      | 1
        null   | 1
        1.0    | 1.0
        3      | 3
        98.0   | 98
    }

    def "If number is not negative, returns -1"() {
        expect:
        NumberUtils.toNegative(number as Double) == expected
        NumberUtils.toNegative(number as Float) == expected
        NumberUtils.toNegative(number as Long) == expected
        NumberUtils.toNegative(number as Integer) == expected
        NumberUtils.toNegative(number as Short) == expected
        NumberUtils.toNegative(number as Byte) == expected

        where:
        number | expected
        -128   | -128
        -56.0  | -56
        -2     | -2
        0      | -1
        null   | -1
        1      | -1
        98.0   | -1
    }

    def "If number is null, returns the other number"() {
        expect:
        NumberUtils.ifNull(number as Double, defaultNumber) == expected
        NumberUtils.ifNull(number as Float, defaultNumber) == expected
        NumberUtils.ifNull(number as Long, defaultNumber) == expected
        NumberUtils.ifNull(number as Integer, defaultNumber) == expected
        NumberUtils.ifNull(number as Short, defaultNumber) == expected
        NumberUtils.ifNull(number as Byte, defaultNumber) == expected

        where:
        number | defaultNumber || expected
        -1     | -1            || -1
        0      | 0             || 0
        null   | 5             || 5
        1      | 1             || 1
    }

    def "Gets number of places with long"() {
        when:
        def numOfPlaces = NumberUtils.getNumOfPlaces number

        then:
        if (number == Integer.MIN_VALUE || number == Long.MIN_VALUE) number++
        numOfPlaces == String.valueOf(Math.abs(number)).length()

        where:
        number << [Long.MIN_VALUE, Integer.MIN_VALUE, -50_000, -1, 0, 1, 50_000, Integer.MAX_VALUE, Long.MAX_VALUE]
    }

    def "Gets number of places with BigInteger"() {
        when:
        def numOfPlaces = NumberUtils.getNumOfPlaces(new BigInteger(number))

        then:
        numOfPlaces == number.replace("-", "").length()

        where:
        number << ["-115234155123123413842342342024623440", "-5", "0", "9", "1505512411489465416645571849602523405834510"]
    }

    def "Checks whether number has decimal part"() {
        when:
        def actual0 = NumberUtils.hasDecimalPart number as double
        def actual1 = NumberUtils.hasDecimalPart number as BigDecimal

        then:
        actual0 == expected
        actual1 == expected

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

    def "Gets reversed long number"() {
        when:
        def actual = NumberUtils.reverse number

        then:
        actual == expected

        where:
        number            | expected
        Long.MIN_VALUE    | -8085774586302733229
        Integer.MIN_VALUE | -8463847412
        Short.MIN_VALUE   | -86723
        -25000            | -52
        Byte.MIN_VALUE    | -821
        -5                | -5
        0                 | 0
        9                 | 9
        Byte.MAX_VALUE    | 721
        1000              | 1
        Short.MAX_VALUE   | 76723
        Integer.MAX_VALUE | 7463847412
        Long.MAX_VALUE    | 7085774586302733229
    }

    def "Gets reversed big integer"() {
        when:
        def actual = NumberUtils.reverse number

        then:
        actual == expected

        where:
        number                                            | expected
        new BigInteger("-987654321098765432109876543210") | new BigInteger("-12345678901234567890123456789")
        Long.MIN_VALUE.toBigInteger()                     | BigInteger.valueOf(-8085774586302733229)
        Integer.MIN_VALUE.toBigInteger()                  | BigInteger.valueOf(-8463847412)
        Short.MIN_VALUE.toBigInteger()                    | BigInteger.valueOf(-86723)
        Byte.MIN_VALUE.toBigInteger()                     | BigInteger.valueOf(-821)
        new BigInteger("-2080")                           | BigInteger.valueOf(-802)
        new BigInteger("-1")                              | BigInteger.valueOf(-1)
        BigInteger.ZERO                                   | BigInteger.ZERO
        BigInteger.ONE                                    | BigInteger.ONE
        BigInteger.TEN                                    | BigInteger.ONE
        Byte.MAX_VALUE.toBigInteger()                     | BigInteger.valueOf(721)
        Short.MAX_VALUE.toBigInteger()                    | BigInteger.valueOf(76723)
        Integer.MAX_VALUE.toBigInteger()                  | BigInteger.valueOf(7463847412)
        Long.MAX_VALUE.toBigInteger()                     | BigInteger.valueOf(7085774586302733229)
        new BigInteger("8453487412897056489434840070000") | new BigInteger("700484349846507982147843548")
    }

}
