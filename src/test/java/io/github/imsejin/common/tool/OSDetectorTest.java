package io.github.imsejin.common.tool;

import io.github.imsejin.common.constant.OperatingSystem;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

public class OSDetectorTest {

    @Test
    public void getOS() {
        // when
        OperatingSystem os = OSDetector.getOS();

        // then
        assertThat(os).isEqualTo(OperatingSystem.WINDOWS);
    }

}
