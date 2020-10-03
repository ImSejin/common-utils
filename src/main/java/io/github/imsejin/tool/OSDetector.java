package io.github.imsejin.tool;

import io.github.imsejin.constant.OperatingSystem;

/**
 * Operating system detector
 */
public final class OSDetector {

    private static final String CURRENT_OS = System.getProperty("os.name").toLowerCase();

    private OSDetector() {
    }

    public static OperatingSystem getOS() {
        for (OperatingSystem os : OperatingSystem.values()) {
            for (String keyword : os.getKeywords()) {
                if (CURRENT_OS.contains(keyword)) return os;
            }
        }

        return null;
    }

}
