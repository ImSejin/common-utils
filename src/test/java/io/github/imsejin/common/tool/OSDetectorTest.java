package io.github.imsejin.common.tool;

import io.github.imsejin.common.constant.OperatingSystem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OSDetectorTest {

    @Test
    void getOS() {
        // when
        OperatingSystem os = OSDetector.getOS();

        // then
        assertThat(os).isEqualTo(OperatingSystem.WINDOWS);
    }

}
