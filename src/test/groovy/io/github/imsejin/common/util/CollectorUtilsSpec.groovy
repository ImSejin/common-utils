package io.github.imsejin.common.util

import io.github.imsejin.common.tool.Stopwatch
import spock.lang.Specification
import spock.lang.Timeout

import java.util.Map.Entry
import java.util.concurrent.TimeUnit

import static io.github.imsejin.common.util.CollectorUtils.ranking
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.B
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.C
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.E
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.G
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.H
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.I
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.J
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.L
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.M
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.N
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.O
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.S
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.T
import static io.github.imsejin.common.util.CollectorUtilsSpec.Alphabet.U
import static java.util.Comparator.comparing
import static java.util.Comparator.comparingInt

class CollectorUtilsSpec extends Specification {

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "Collects by rank"() {
        when:
        def ranks = values.stream().collect(ranking())
        def rankMap = toMap(ranks)

        then:
        values.size() >= rankMap.size()
        rankMap == expected

        where:
        values                                                          | expected
        [true, false, false, true]                                      | [1: false, 3: true]
        [4, 3, 2, 2, 1, 1, 2]                                           | [1: 1, 3: 2, 6: 3, 7: 4]
        [3.14, -1.173, 0.1, -0.2, 0.0]                                  | [1: -1.173, 2: -0.2, 3: 0.0, 4: 0.1, 5: 3.14]
        [16, 0, -8, 1024, -128, 64, 256, -512, 32]                      | [1: -512, 2: -128, 3: -8, 4: 0, 5: 16, 6: 32, 7: 64, 8: 256, 9: 1024]
        ['A', 'b', '9', '0', 'D', 'e', '5'] as List<Character>          | [1: '0', 2: '5', 3: '9', 4: 'A', 5: 'D', 6: 'b', 7: 'e'] as Map<Integer, Character>
        ["beta", "b", "alpha", "a", "gamma", "g", ""]                   | [1: "", 2: "a", 3: "alpha", 4: "b", 5: "beta", 6: "g", 7: "gamma"]
        [I, O, G, I, T, H, U, B, I, M, S, E, J, I, N, C, O, M, M, O, N] | [1: B, 2: C, 3: E, 4: G, 5: H, 6: I, 10: J, 11: M, 14: N, 16: O, 19: S, 20: T, 21: U]
    }

    def "Collects by rank with comparator"() {
        when:
        def ranks = values.stream().collect(ranking(comparator as Comparator<? super Object>))
        def rankMap = toMap(ranks)

        then:
        values.size() >= rankMap.size()
        rankMap == expected

        where:
        values                                        | comparator                                                                  || expected
        [true, false, false, true]                    | comparingInt({ it.toString().length() })                                    || [1: true, 3: false]
        [16, 0, -8, 1024, -128, 64, 256, -512, 32]    | Integer::compare                                                            || [1: -512, 2: -128, 3: -8, 4: 0, 5: 16, 6: 32, 7: 64, 8: 256, 9: 1024]
        ["beta", "b", "alpha", "a", "gamma", "g", ""] | String::compareTo                                                           || [1: "", 2: "a", 3: "alpha", 4: "b", 5: "beta", 6: "g", 7: "gamma"]
        ["beta", "b", "alpha", "a", "gamma", "g", ""] | comparingInt(String::length).thenComparing(String::compareTo as Comparator) || [1: "", 2: "a", 3: "b", 4: "g", 5: "beta", 6: "alpha", 7: "gamma"]
        [C, O, M, M, O, N, U, T, I, L, I, T, I, E, S] | comparing(Enum::ordinal)                                                    || [1: C, 2: E, 3: I, 6: L, 7: M, 9: N, 10: O, 12: S, 13: T, 15: U]
    }

    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    def "Measures performance"() {
        def stopwatch = new Stopwatch(TimeUnit.MILLISECONDS)
        stopwatch.start("Creates 1000 random values")

        given:
        def values = (0..1000).collect { UUID.randomUUID() }
        stopwatch.stop()

        when:
        stopwatch.start("Collects them by rank")
        def ranks = values.stream().collect(ranking())
        stopwatch.stop()
        println stopwatch.statistics

        then:
        def rankMap = toMap(ranks)
        values.size() >= rankMap.size()
    }

    // -------------------------------------------------------------------------------------------------

    enum Alphabet {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
    }

    static <T> Map<Integer, T> toMap(Iterable<Entry<Integer, T>> entries) {
        Map<Integer, T> map = [:]
        entries.each {
            map[it.key] = it.value
        }
        map
    }

}
