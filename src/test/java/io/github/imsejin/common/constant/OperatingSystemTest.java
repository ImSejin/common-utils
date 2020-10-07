package io.github.imsejin.common.constant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class OperatingSystemTest {

    @ParameterizedTest
    @ValueSource(strings = {"win", "mac", "nix", "nux", "aix", "sunos"})
    public void contains(String keyword) {
        // when
        boolean contains = OperatingSystem.contains(keyword);

        // then
        assertThat(contains).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"win", "mac", "nix", "nux", "aix", "sunos"})
    public void of(String keyword) {
        // when
        OperatingSystem os = OperatingSystem.of(keyword).orElse(null);

        // then
        assertThat(os).isNotNull();
    }

}
