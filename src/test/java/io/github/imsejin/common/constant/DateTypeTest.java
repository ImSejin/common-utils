package io.github.imsejin.common.constant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DateTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"yyyy", "MM", "dd", "HH", "mm", "ss", "SSS",
            "yyyyMM", "yyyyMMdd", "HHmmss", "HHmmssSSS", "yyyyMMddHHmmss", "yyyyMMddHHmmssSSS",
            "yyyy-MM-dd", "HH:mm:ss", "HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS"})
    void contains(String pattern) {
        // when
        boolean contains = DateType.contains(pattern);

        // then
        assertThat(contains).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"yyyy", "MM", "dd", "HH", "mm", "ss", "SSS",
            "yyyyMM", "yyyyMMdd", "HHmmss", "HHmmssSSS", "yyyyMMddHHmmss", "yyyyMMddHHmmssSSS",
            "yyyy-MM-dd", "HH:mm:ss", "HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS"})
    void of(String pattern) {
        // when
        DateType dateType = DateType.of(pattern).orElse(null);

        // then
        assertThat(dateType).isNotNull();
        assertThat(pattern).isEqualTo(dateType.value());
    }

}
