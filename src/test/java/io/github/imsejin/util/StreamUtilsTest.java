package io.github.imsejin.util;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
        assertArrayEquals(stream.toArray(), characters.toArray());
    }

}
