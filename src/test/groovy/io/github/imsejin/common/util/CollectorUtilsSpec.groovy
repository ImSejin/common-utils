package io.github.imsejin.common.util

import spock.lang.Specification

import static io.github.imsejin.common.util.CollectorUtils.ranking
import static java.util.Comparator.comparingInt

class CollectorUtilsSpec extends Specification {

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "Ranking without comparator"() {
        when:
        def ranks = valuse.stream().collect(ranking())

        then:
        def rankMap = ranks.stream().collect(LinkedHashMap::new, { map, e -> map[e.key] = e.value }, Map::putAll)
        rankMap == expected

        where:
        valuse                                                 | expected
        [3.14, -1.173, 0.1, -0.2, 0.0]                         | [1: -1.173, 2: -0.2, 3: 0.0, 4: 0.1, 5: 3.14]
        [16, 0, -8, 1024, -128, 64, 256, -512, 32]             | [1: -512, 2: -128, 3: -8, 4: 0, 5: 16, 6: 32, 7: 64, 8: 256, 9: 1024]
        ['A', 'b', '9', '0', 'D', 'e', '5'] as List<Character> | [1: '0', 2: '5', 3: '9', 4: 'A', 5: 'D', 6: 'b', 7: 'e'] as Map<Integer, Character>
        ["beta", "b", "alpha", "a", "gamma", "g", ""]          | [1: "", 2: "a", 3: "alpha", 4: "b", 5: "beta", 6: "g", 7: "gamma"]
    }

    def "Ranking with comparator"() {
        when:
        def ranks = valuse.stream().collect(ranking(comparator as Comparator<? super Object>))

        then:
        def rankMap = ranks.stream().collect(LinkedHashMap::new, { map, e -> map[e.key] = e.value }, Map::putAll)
        rankMap == expected

        where:
        valuse                                        | comparator                                                                  || expected
        [16, 0, -8, 1024, -128, 64, 256, -512, 32]    | Integer::compare                                                            || [1: -512, 2: -128, 3: -8, 4: 0, 5: 16, 6: 32, 7: 64, 8: 256, 9: 1024]
        ["beta", "b", "alpha", "a", "gamma", "g", ""] | String::compareTo                                                           || [1: "", 2: "a", 3: "alpha", 4: "b", 5: "beta", 6: "g", 7: "gamma"]
        ["beta", "b", "alpha", "a", "gamma", "g", ""] | comparingInt(String::length).thenComparing(String::compareTo as Comparator) || [1: "", 2: "a", 3: "b", 4: "g", 5: "beta", 6: "alpha", 7: "gamma"]
    }

}
