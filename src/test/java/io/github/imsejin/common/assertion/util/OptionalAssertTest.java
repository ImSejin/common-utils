package io.github.imsejin.common.assertion.util;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.HolderAssertable;

import static org.assertj.core.api.Assertions.*;

@DisplayName("OptionalAssert")
class OptionalAssertTest {

    @Nested
    @DisplayName("method 'hasValue'")
    class HasValue {
        @Test
        @DisplayName("passes, when actual has the given content")
        void test0() {
            // given
            List<?> values = Arrays.asList(new Object(), 1, new BigDecimal("3.14"), "foo", new Object[8]);

            // expect
            values.forEach(value -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(Optional.of(value)).hasValue(value)));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given content")
        void test1() {
            // given
            List<SimpleEntry<Object, Object>> entries = Arrays.asList(
                    new SimpleEntry<>(null, null),
                    new SimpleEntry<>(new Object(), new Object()),
                    new SimpleEntry<>(1, -1),
                    new SimpleEntry<>(new BigDecimal("3.14"), BigDecimal.ONE),
                    new SimpleEntry<>("foo", "bar"),
                    new SimpleEntry<>(new Object[8], new Object[8]));

            // expect
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE) +
                    "\n {4}actual: 'Optional(\\.empty|\\[.+])'" +
                    "\n {4}actual\\.value: '.+'" +
                    "\n {4}expected: '.*'";

            entries.forEach(entry -> assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(Optional.ofNullable(entry.getKey())).hasValue(entry.getValue())))
                    .withMessageMatching(message));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveValue'")
    class DoesNotHaveValue {
        @Test
        @DisplayName("passes, when actual doesn't have the given content")
        void test0() {
            // given
            List<SimpleEntry<Object, Object>> entries = Arrays.asList(
                    new SimpleEntry<>(null, null),
                    new SimpleEntry<>(new Object(), new Object()),
                    new SimpleEntry<>(1, -1),
                    new SimpleEntry<>(new BigDecimal("3.14"), BigDecimal.ONE),
                    new SimpleEntry<>("foo", "bar"),
                    new SimpleEntry<>(new Object[8], new Object[8]));

            // expect
            entries.forEach(entry -> assertThatNoException()
                    .isThrownBy((() -> Asserts.that(Optional.ofNullable(entry.getKey()))
                            .doesNotHaveValue(entry.getValue()))));
        }

        @Test
        @DisplayName("throws exception, when actual has the given content")
        void test1() {
            // given
            List<?> values = Arrays.asList(new Object(), 1, new BigDecimal("3.14"), "foo", new Object[8]);

            // expect
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE) +
                    "\n {4}actual: 'Optional(\\.empty|\\[.+])'" +
                    "\n {4}actual\\.value: '.+'" +
                    "\n {4}expected: '.*'";

            values.forEach(value -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Optional.of(value)).doesNotHaveValue(value))
                    .withMessageMatching(message));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isPresent'")
    class IsPresent {
        @Test
        @DisplayName("passes, when actual has content")
        void test0() {
            // given
            List<Object> values = Arrays.asList(new Object(), 1, new BigDecimal("3.14"), "foo", new Object[8]);

            // expect
            values.forEach(value -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(Optional.of(value)).isPresent()));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("throws exception, when actual doesn't have content")
        void test1(Object value) {
            String message = Pattern.quote("It is expected to be present, but it isn't.") +
                    "\n {4}actual: 'Optional\\.empty'" +
                    "\n {4}actual\\.value: '.+'";

            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(Optional.ofNullable(value)).isPresent()))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAbsent'")
    class IsAbsent {
        @ParameterizedTest
        @NullSource
        @DisplayName("passes, when actual doesn't have content")
        void test0(Object value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(Optional.ofNullable(value)).isAbsent());
        }

        @Test
        @DisplayName("throws exception, when actual has content")
        void test1() {
            // given
            List<Object> values = Arrays.asList(new Object(), 1, new BigDecimal("3.14"), "foo", new Object[8]);

            // expect
            String message = Pattern.quote("It is expected to be absent, but it isn't.") +
                    "\n {4}actual: 'Optional\\[.+]'" +
                    "\n {4}actual\\.value: '.+'";

            values.forEach(value -> assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(Optional.of(value)).isAbsent()))
                    .withMessageMatching(message));
        }
    }

}
