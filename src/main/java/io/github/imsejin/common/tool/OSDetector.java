package io.github.imsejin.common.tool;

import io.github.imsejin.common.constant.OperatingSystem;

/**
 * Operating system detector
 */
public final class OSDetector {

    private static final String CURRENT_OS_NAME = System.getProperty("os.name").toLowerCase();

    private OSDetector() {
    }

    public static OperatingSystem getOS() {
        return OperatingSystem.of(CURRENT_OS_NAME).orElse(null);
    }

}
