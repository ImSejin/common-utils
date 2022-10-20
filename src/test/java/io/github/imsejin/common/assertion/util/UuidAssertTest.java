package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("UuidAssert")
class UuidAssertTest {

    @Nested
    @DisplayName("method 'isNil'")
    class IsNil {
        @ParameterizedTest
        @ValueSource(strings = {
                "00000000-0000-0000-0000-000000000000",
        })
        @DisplayName("passes, when actual is nil")
        void test0(UUID actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNil());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0001-0000-000000000000",
                "123e4567-e89b-12d3-a456-426614174000",
                "6a7409ee-d6c0-21cc-8fe9-0800093569b9",
                "5a7448a5-9d71-3bfa-b66d-12459ddf6e2a",
                "e2bd8ba5-0c35-4801-83cc-ee15fe76cca1",
                "7348aa4f-bf4e-5419-a0ac-2dd1ddc522d0",
        })
        @DisplayName("throws exception, when actual is not nil")
        void test1(UUID actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNil())
                    .withMessageMatching(Pattern.quote("It is expected to be nil, but not nil.") +
                            "\n {4}actual: '[a-z0-9]{8}(-[a-z0-9]{4}){3}-[a-z0-9]{12}'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotNil'")
    class IsNotNil {
        @ParameterizedTest
        @ValueSource(strings = {
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0001-0000-000000000000",
                "123e4567-e89b-12d3-a456-426614174000",
                "6a7409ee-d6c0-21cc-8fe9-0800093569b9",
                "5a7448a5-9d71-3bfa-b66d-12459ddf6e2a",
                "e2bd8ba5-0c35-4801-83cc-ee15fe76cca1",
                "7348aa4f-bf4e-5419-a0ac-2dd1ddc522d0",
        })
        @DisplayName("passes, when actual is not nil")
        void test0(UUID actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotNil());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "00000000-0000-0000-0000-000000000000",
        })
        @DisplayName("throws exception, when actual is nil")
        void test1(UUID actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNotNil())
                    .withMessageMatching(Pattern.quote("It is expected not to be nil, but nil.") +
                            "\n {4}actual: '[a-z0-9]{8}(-[a-z0-9]{4}){3}-[a-z0-9]{12}'");
        }
    }

}
