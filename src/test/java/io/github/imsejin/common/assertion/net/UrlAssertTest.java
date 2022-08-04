package io.github.imsejin.common.assertion.net;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

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

        @Test
        @DisplayName("throws exception, when actual doesn't have host")
        void test1() throws MalformedURLException {
            // given
            URL url = new URL("file:///var/lib/");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(url).hasHost("/"))
                    .withMessageStartingWith("It is expected to have that host, but it doesn't.");
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

        @Test
        @DisplayName("throws exception, when actual has host")
        void test1() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(url).doesNotHaveHost())
                    .withMessage("It is expected not to have host, but it does.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasPort'")
    class HasPort {
        @Test
        @DisplayName("passes, when actual has port")
        void test0() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url).hasPort(443));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have port")
        void test1() throws MalformedURLException {
            // given
            URL url = new URL("file:///var/lib/");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(url).hasPort(80))
                    .withMessageStartingWith("It is expected to have that port, but it doesn't.");
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

        @Test
        @DisplayName("throws exception, when actual has port")
        void test1() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(url).doesNotHavePort())
                    .withMessage("It is expected not to have port, but it does.");
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

        @Test
        @DisplayName("throws exception, when actual doesn't have path")
        void test1() throws MalformedURLException {
            // given
            URL url = new URL("https:");

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(url).hasPath(""))
                    .withMessageStartingWith("It is expected to have that path, but it doesn't.");
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
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(url).doesNotHavePath())
                    .withMessage("It is expected not to have path, but it does.");
        }
    }

    // -------------------------------------------------------------------------------------------------

}
