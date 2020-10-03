package io.github.imsejin.common.tool;

import io.github.imsejin.common.constant.OperatingSystem;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OSDetectorTest {

    @Test
    public void getOS() {
        // when
        OperatingSystem os = OSDetector.getOS();

        // then
        assertThat(os, is(OperatingSystem.WINDOWS));
    }

}
