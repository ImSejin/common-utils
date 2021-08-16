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
import spock.lang.Unroll

class MathUtilsSpec extends Specification {

    @Unroll("MathUtils.fibonacci(#input) == #expected")
    def "fibonacci"() {
        when:
        def result = MathUtils.fibonacci(input)

        then:
        result.toString() == expected

        where:
        input | expected
        0     | "0"
        1     | "1"
        2     | "1"
        5     | "5"
        7     | "13"
        10    | "55"
        13    | "233"
        17    | "1597"
        25    | "75025"
        50    | "12586269025"
        100   | "354224848179261915075"
    }

    @Unroll("MathUtils.factorial(#input) == #expected")
    def "factorial"() {
        when:
        def result = MathUtils.factorial(input)

        then:
        result.toString() == expected

        where:
        input | expected
        5     | "120"
        7     | "5040"
        10    | "3628800"
        13    | "6227020800"
        17    | "355687428096000"
        25    | "15511210043330985984000000"
    }

    @Unroll("MathUtils.ceil(#amount, #len) == #expected")
    def "ceil"() {
        expect:
        def actual = MathUtils.ceil(amount, len)
        String.valueOf(actual) == expected

        where:
        amount                | len | expected
        345764.48395751       | -3  | "346000.0"
        -345764.48395751      | -3  | "-345000.0"
        51307.0078102         | -2  | "51400.0"
        -51307.0078102        | -2  | "-51300.0"
        48.038537052          | -1  | "50.0"
        -48.038537052         | -1  | "-40.0"
        0.1575153545          | 0   | "1.0"
        -0.1575153545         | 0   | "-0.0"
        1.248458248           | 1   | "1.3"
        -1.248458248          | 1   | "-1.2"
        854.0812738218        | 2   | "854.09"
        -854.0812738218       | 2   | "-854.08"
        97234.10398570893174  | 3   | "97234.104"
        -97234.10398570893174 | 3   | "-97234.103"
    }

    @Unroll("MathUtils.round(#amount, #len) == #expected")
    def "round"() {
        expect:
        def actual = MathUtils.round(amount, len)
        String.valueOf(actual) == expected

        where:
        amount                | len | expected
        345764.48395751       | -3  | "346000.0"
        -345764.48395751      | -3  | "-346000.0"
        51307.0078102         | -2  | "51300.0"
        -51307.0078102        | -2  | "-51300.0"
        48.038537052          | -1  | "50.0"
        -48.038537052         | -1  | "-50.0"
        0.1575153545          | 0   | "0.0"
        -0.1575153545         | 0   | "0.0"
        1.248458248           | 1   | "1.2"
        -1.248458248          | 1   | "-1.2"
        854.0812738218        | 2   | "854.08"
        -854.0812738218       | 2   | "-854.08"
        97234.10398570893174  | 3   | "97234.104"
        -97234.10398570893174 | 3   | "-97234.104"
    }

    @Unroll("MathUtils.floor(#amount, #len) == #expected")
    def "floor"() {
        expect:
        def actual = MathUtils.floor(amount, len)
        String.valueOf(actual) == expected

        where:
        amount                | len | expected
        345764.48395751       | -3  | "345000.0"
        -345764.48395751      | -3  | "-346000.0"
        51307.0078102         | -2  | "51300.0"
        -51307.0078102        | -2  | "-51400.0"
        48.038537052          | -1  | "40.0"
        -48.038537052         | -1  | "-50.0"
        0.1575153545          | 0   | "0.0"
        -0.1575153545         | 0   | "-1.0"
        1.248458248           | 1   | "1.2"
        -1.248458248          | 1   | "-1.3"
        854.0812738218        | 2   | "854.08"
        -854.0812738218       | 2   | "-854.09"
        97234.10398570893174  | 3   | "97234.103"
        -97234.10398570893174 | 3   | "-97234.104"
    }

    @Unroll("MathUtils.isOdd(#amount) == #expected")
    def "isOdd"() {
        expect:
        MathUtils.isOdd(amount) == expected

        where:
        amount  | expected
        -345764 | false
        -51307  | true
        -48     | false
        0       | false
        17      | true
        854     | false
        97231   | true
    }

}
