package io.github.imsejin.common.constant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperatingSystemTest {

    @ParameterizedTest
    @ValueSource(strings = {"win", "mac", "nix", "nux", "aix", "sunos"})
    public void contains(String keyword) {
        // when
        boolean contains = OperatingSystem.contains(keyword);

        // then
        assertTrue(contains);
    }

    @ParameterizedTest
    @ValueSource(strings = {"win", "mac", "nix", "nux", "aix", "sunos"})
    public void of(String keyword) {
        // when
        OperatingSystem os = OperatingSystem.of(keyword).orElse(null);

        // then
        assertTrue(os instanceof OperatingSystem);
    }

}
