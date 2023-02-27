package io.github.imsejin.common.assertion.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.imsejin.common.assertion.Asserts;

import static org.assertj.core.api.Assertions.*;

@DisplayName("UrlAssert")
class UrlAssertTest {

    @Nested
    @DisplayName("method 'hasHost'")
    class HasHost {
        @Test
        @DisplayName("passes, when actual has host")
        void test0() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url).hasHost("www.github.com"));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "file:///var/lib/        | ",
                "https://foo.bar         | foo",
                "http://localhost:8080   | 127.0.0.1",
                "https://www.github.com/ | github.com",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual doesn't have host")
        void test1(URL actual, String expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .hasHost(expected))
                    .withMessageMatching(Pattern.quote("It is expected to have that host, but it doesn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.host: '.*'" +
                            "\n {4}expected: '.*'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveHost'")
    class DoesNotHaveHost {
        @Test
        @DisplayName("passes, when actual doesn't have host")
        void test0() throws MalformedURLException {
            // given
            URL url = new URL("file:///var/lib/");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url).doesNotHaveHost());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "http://localhost:8080", "https://www.github.com/",
        })
        @DisplayName("throws exception, when actual has host")
        void test1(URL url) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(url)
                            .doesNotHaveHost())
                    .withMessageMatching(Pattern.quote("It is expected not to have host, but it does.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.host: '.+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasPort'")
    class HasPort {
        @ParameterizedTest
        @CsvSource(value = {
                "http://localhost:8080   | 8080",
                "https://www.github.com/ | 443",
        }, delimiter = '|')
        @DisplayName("passes, when actual has port")
        void test0(URL actual, int expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).hasPort(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "http://localhost:8080   | 80",
                "https://www.github.com/ | 8080",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual doesn't have port")
        void test1(URL actual, int expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .hasPort(expected))
                    .withMessageMatching(Pattern.quote("It is expected to have that port, but it doesn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.port: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHavePort'")
    class DoesNotHavePort {
        @Test
        @DisplayName("passes, when actual doesn't have port")
        void test0() throws MalformedURLException {
            // given
            URL url = new URL("file:///var/lib/");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url).doesNotHavePort());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "http://localhost:8080", "https://www.github.com/",
        })
        @DisplayName("throws exception, when actual has port")
        void test1(URL actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .doesNotHavePort())
                    .withMessageMatching(Pattern.quote("It is expected not to have port, but it does.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.port: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasPath'")
    class HasPath {
        @Test
        @DisplayName("passes, when actual has path")
        void test0() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/imsejin/common-utils");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url)
                    .hasPath("/imsejin/common-utils"));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "https://www.github.com     | ",
                "http://localhost:8080/apis | apis",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual doesn't have path")
        void test1(URL actual, String expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .hasPath(expected))
                    .withMessageMatching(Pattern.quote("It is expected to have that path, but it doesn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.path: '.*'" +
                            "\n {4}expected: '.*'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHavePath'")
    class DoesNotHavePath {
        @Test
        @DisplayName("passes, when actual doesn't have path")
        void test0() throws MalformedURLException {
            // given
            URL url = new URL("https:");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url).doesNotHavePath());
        }

        @Test
        @DisplayName("throws exception, when actual has path")
        void test1() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/imsejin/common-utils");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(url)
                            .doesNotHavePath())
                    .withMessageMatching(Pattern.quote("It is expected not to have path, but it does.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.path: '.+'");
        }
    }

}
