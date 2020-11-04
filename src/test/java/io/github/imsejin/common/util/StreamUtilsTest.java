package io.github.imsejin.common.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class StreamUtilsTest {

    @Test
    void toStream() {
        // given
        List<Character> characters = Arrays.asList('q', 'w', 'e', 'r', 't', 'y');

        // when
        Stream<Character> stream = StreamUtils.toStream(characters.listIterator());

        // then
        characters.forEach(c -> System.out.printf("%c, ", c));
        stream = stream.peek(c -> System.out.printf("%c, ", c));
        assertThat(characters.toArray()).isEqualTo(stream.toArray());
    }

}
