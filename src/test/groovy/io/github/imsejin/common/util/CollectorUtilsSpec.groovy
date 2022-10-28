package io.github.imsejin.common.util

import io.github.imsejin.common.tool.RandomString
import io.github.imsejin.common.tool.Stopwatch
import spock.lang.Specification
import spock.lang.Timeout

import java.util.Map.Entry
import java.util.concurrent.TimeUnit
import java.util.function.Function

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

class CollectorUtilsSpec extends Specification {

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "Collects by rank"() {
        when:
        def rankMap = values.stream().collect(ranking())

        then: "It can't have more keys than the number of origin values."
        rankMap.size() <= values.size()

        then: "Is it sorted by rank?"
        def keys = rankMap.entrySet().collect(Entry::getKey) as List<Integer>
        keys == keys.sort(false, Integer::compare)

        then: "it skips rank order as many as the number of previous rank items."
        def rank = 1
        keys.each { assert it == rank; rank += rankMap[it].size() }

        then: "Is it equal to the expected?"
        rankMap == expected

        where:
        values                                                          | expected
        [true, false, false, true]                                      | [1: [false, false], 3: [true, true]]
        [4, 3, 2, 2, 1, 1, 2]                                           | [1: [1, 1], 3: [2, 2, 2], 6: [3], 7: [4]]
        [3.14, -1.173, 0.1, -0.2, 0.0]                                  | [1: [-1.173], 2: [-0.2], 3: [0.0], 4: [0.1], 5: [3.14]]
        [16, 0, -8, 1024, -128, 64, 256, -512, 32]                      | [1: [-512], 2: [-128], 3: [-8], 4: [0], 5: [16], 6: [32], 7: [64], 8: [256], 9: [1024]]
        ['A', 'b', '9', '0', 'D', 'e', '5'] as List<Character>          | [1: ['0'], 2: ['5'], 3: ['9'], 4: ['A'], 5: ['D'], 6: ['b'], 7: ['e']] as Map<Integer, Character>
        ["beta", "b", "alpha", "a", "gamma", "g", ""]                   | [1: [""], 2: ["a"], 3: ["alpha"], 4: ["b"], 5: ["beta"], 6: ["g"], 7: ["gamma"]]
        [I, O, G, I, T, H, U, B, I, M, S, E, J, I, N, C, O, M, M, O, N] | [1: [B], 2: [C], 3: [E], 4: [G], 5: [H], 6: [I, I, I, I], 10: [J], 11: [M, M, M], 14: [N, N], 16: [O, O, O], 19: [S], 20: [T], 21: [U]]
    }

    def "Collects by rank with comparator"() {
        when:
        def rankMap = values.stream().collect(ranking(extractor as Function, comparator as Comparator)) as SortedMap<Integer, List<?>>

        then: "It can't have more keys than the number of origin values."
        rankMap.size() <= values.size()

        then: "Is it sorted by rank?"
        def keys = rankMap.entrySet().collect(Entry::getKey) as List<Integer>
        keys == keys.sort(false, Integer::compare)

        then: "it skips rank order as many as the number of previous rank items."
        def rank = 1
        keys.each { assert it == rank; rank += rankMap[it].size() }

        then: "Is it equal to the expected?"
        rankMap == expected

        where:
        values                                        | extractor                  | comparator        || expected
        [true, false, false, true]                    | { it.toString().length() } | Integer::compare  || [1: [true, true], 3: [false, false]]
        [16, 0, -8, 1024, -128, 64, 256, -512, 32]    | Function.identity()        | Integer::compare  || [1: [-512], 2: [-128], 3: [-8], 4: [0], 5: [16], 6: [32], 7: [64], 8: [256], 9: [1024]]
        ["beta", "b", "alpha", "a", "gamma", "g", ""] | Function.identity()        | String::compareTo || [1: [""], 2: ["a"], 3: ["alpha"], 4: ["b"], 5: ["beta"], 6: ["g"], 7: ["gamma"]]
        ["beta", "b", "alpha", "a", "gamma", "g", ""] | String::length             | Integer::compare  || [1: [""], 2: ["b", "a", "g"], 5: ["beta"], 6: ["alpha", "gamma"]]
        [C, O, M, M, O, N, U, T, I, L, I, T, I, E, S] | Enum::ordinal              | Integer::compare  || [1: [C], 2: [E], 3: [I, I, I], 6: [L], 7: [M, M], 9: [N], 10: [O, O], 12: [S], 13: [T, T], 15: [U]]
    }

    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    def "Measures performance on parallel stream"() {
        def stopwatch = new Stopwatch(TimeUnit.MILLISECONDS)
        stopwatch.start("Creates %,d random values", 10_000_000)

        given:
        def randomString = new RandomString()
        def values = (1..10_000_000).collect { randomString.nextString(2) }
        stopwatch.stop()

        when:
        stopwatch.start("Collects them by rank")
        def rankMap = values.parallelStream().collect(ranking())
        stopwatch.stop()
        println stopwatch.statistics

        then: "It can't have more keys than the number of origin values."
        rankMap.size() <= values.size()

        then: "Is it sorted by rank?"
        def keys = rankMap.entrySet().collect(Entry::getKey) as List<Integer>
        keys == keys.sort(false, Integer::compare)

        then: "it skips rank order as many as the number of previous rank items."
        def rank = 1
        keys.each { assert it == rank; rank += rankMap[it].size() }
    }

    // -------------------------------------------------------------------------------------------------

    enum Alphabet {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
    }

}
