package io.github.imsejin.common.tool;

import io.github.imsejin.common.constant.OperatingSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.assertj.core.api.Assertions.assertThat;

class OSDetectorTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void getWindows() {
        // when & then
        assertThat(OSDetector.getOS()).isEqualTo(OperatingSystem.WINDOWS);
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void getMacOS() {
        // when & then
        assertThat(OSDetector.getOS()).isEqualTo(OperatingSystem.MAC);
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.AIX})
    void getUNIX() {
        // when & then
        assertThat(OSDetector.getOS()).isEqualTo(OperatingSystem.UNIX);
    }

    @Test
    @EnabledOnOs(OS.SOLARIS)
    void getSolaris() {
        // when & then
        assertThat(OSDetector.getOS()).isEqualTo(OperatingSystem.SOLARIS);
    }

}
