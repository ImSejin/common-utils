package io.github.imsejin.common.util;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CollectionUtilsTest {

    @Test
    public void toMap() {
        // given
        List<String> list = Arrays.asList("A", "B", "C");
        // when
        Map<Integer, String> map1 = CollectionUtils.toMap(list);
        // then
        map1.forEach((k, v) -> assertThat(v, is(list.get(k))));

        // given
        Set<String> set = new HashSet<>(Arrays.asList("A", "B", "C"));
        // when
        Map<Integer, String> map2 = CollectionUtils.toMap(set);
        // then
        map2.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}
