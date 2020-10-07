package io.github.imsejin.common.constant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DateTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"yyyy", "MM", "dd", "HH", "mm", "ss", "SSS",
            "yyyyMM", "yyyyMMdd", "HHmmss", "HHmmssSSS", "yyyyMMddHHmmss", "yyyyMMddHHmmssSSS",
            "yyyy-MM-dd", "HH:mm:ss", "HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS"})
    void contains(String pattren) {
        // when
        boolean contains = DateType.contains(pattren);

        // then
        assertTrue(contains);
    }

    @ParameterizedTest
    @ValueSource(strings = {"yyyy", "MM", "dd", "HH", "mm", "ss", "SSS",
            "yyyyMM", "yyyyMMdd", "HHmmss", "HHmmssSSS", "yyyyMMddHHmmss", "yyyyMMddHHmmssSSS",
            "yyyy-MM-dd", "HH:mm:ss", "HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS"})
    void of(String pattren) {
        // when
        DateType dateType = DateType.of(pattren).orElse(null);

        // then
        assertThat(pattren).isEqualTo(dateType.value());
    }

}
