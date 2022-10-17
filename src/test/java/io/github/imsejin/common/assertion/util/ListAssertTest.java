package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.RandomAccessIterationAssertable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ListAssert")
class ListAssertTest {

    @Nested
    @DisplayName("method 'startsWith'")
    class StartsWith {
        @Test
        @DisplayName("passes, when actual starts with the given element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList())
                        .startsWith((Object[]) null);
                Asserts.that((List<?>) null)
                        .startsWith();
                Asserts.that(IntStream.range(0, 10).boxed().collect(toList()))
                        .startsWith(0);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd'))
                        .startsWith('a', 'b', 'c', 'd');
                Asserts.that(Arrays.stream(getClass().getPackage().getName().split("\\.")).collect(toList()))
                        .startsWith("io.github.imsejin.common".split("\\."));
                Asserts.that(Stream.of(null, "alpha", null, "beta")
                        .map(it -> it == null ? null : Collections.singletonList(it)).collect(toList()))
                        .startsWith(null, Collections.singletonList("alpha"));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't start with the given element(s)")
        void test1() {
            String message = Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH_UNEXPECTED_ELEMENT) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}unexpected: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IntStream.range(0, 100).boxed().collect(toList()))
                    .startsWith(IntStream.rangeClosed(0, 100).boxed().toArray(Integer[]::new)))
                    .withMessageMatching(Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH_OVER_SIZE) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '\\[.*]'" +
                            "\n {4}expected\\.size: '[0-9]+'");
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(null, new Object(), null))
                    .startsWith(null, new Object()))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'C', 'd'))
                    .startsWith('a', 'b', 'c', 'd'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.stream(getClass().getPackage().getName().split("\\.")).collect(toList()))
                    .startsWith("io", "github", "common", "imsejin"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Stream.of(null, "alpha", null, "beta").map(Collections::singletonList).collect(toList()))
                    .startsWith(Collections.emptyList(), Collections.singletonList("alpha")))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'endsWith'")
    class EndsWith {
        @Test
        @DisplayName("passes, when actual ends with the given element(s)")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Collections.emptyList())
                        .endsWith((Object[]) null);
                Asserts.that((List<?>) null)
                        .endsWith();
                Asserts.that(IntStream.range(0, 10).boxed().collect(toList()))
                        .endsWith(9);
                Asserts.that(Arrays.asList('a', 'b', 'c', 'd'))
                        .endsWith('a', 'b', 'c', 'd');
                Asserts.that(Arrays.stream(getClass().getPackage().getName().split("\\.")).collect(toList()))
                        .endsWith("assertion", "util");
                Asserts.that(Stream.of(null, "alpha", null, "beta")
                        .map(it -> it == null ? null : Collections.singletonList(it)).collect(toList()))
                        .endsWith(null, Collections.singletonList("beta"));
            });
        }

        @Test
        @DisplayName("throws exception, when actual doesn't end with the given element(s)")
        void test1() {
            String message = Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH_UNEXPECTED_ELEMENT) +
                    "\n {4}actual: '\\[.*]'" +
                    "\n {4}expected: '\\[.*]'" +
                    "\n {4}unexpected: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IntStream.range(0, 100).boxed().collect(toList()))
                    .endsWith(IntStream.rangeClosed(0, 100).boxed().toArray(Integer[]::new)))
                    .withMessageMatching(Pattern.quote(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH_OVER_SIZE) +
                            "\n {4}actual: '\\[.*]'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '\\[.*]'" +
                            "\n {4}expected\\.size: '[0-9]+'");
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList(null, new Object(), null))
                    .endsWith(new Object(), null))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.asList('a', 'b', 'C', 'd'))
                    .endsWith('a', 'b', 'c', 'd'))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Arrays.stream(getClass().getPackage().getName().split("\\.")).collect(toList()))
                    .endsWith("util", "assertion"))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Stream.of(null, "alpha", null, "beta").map(Collections::singletonList).collect(toList()))
                    .endsWith(Collections.emptyList(), Collections.singletonList("beta")))
                    .withMessageMatching(message);
        }
    }

}
