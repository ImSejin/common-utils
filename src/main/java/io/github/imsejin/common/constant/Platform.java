/*
 * Copyright 2024 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import io.github.imsejin.common.util.StringUtils;

/**
 * Combination of operating system and CPU architecture.
 *
 * @since 0.20.0
 */
public enum Platform {

    AIX {
        @Override
        public boolean isCurrent() {
            String os = getCurrentOperatingSystem();
            return os.contains("aix");
        }
    },

    SOLARIS {
        @Override
        public boolean isCurrent() {
            String os = getCurrentOperatingSystem();
            return os.contains("sunos") || os.contains("solaris");
        }
    },

    LINUX {
        @Override
        public boolean isCurrent() {
            String os = getCurrentOperatingSystem();
            return os.contains("nix") || os.contains("nux");
        }
    },

    MACOS_ARM64 {
        @Override
        public boolean isCurrent() {
            String os = getCurrentOperatingSystem();
            if (!os.contains("mac") && !os.contains("darwin")) {
                return false;
            }

            String arch = getCurrentArchitecture();
            if (arch.contains("aarch64") || arch.contains("arm")) {
                return true;
            }

            return supportRosetta();
        }
    },

    MACOS_X64 {
        @Override
        public boolean isCurrent() {
            String os = getCurrentOperatingSystem();
            if (!os.contains("mac") && !os.contains("darwin")) {
                return false;
            }

            String arch = getCurrentArchitecture();
            if (arch.contains("aarch64") || arch.contains("arm")) {
                return false;
            }

            if (!arch.contains("x86_x64")) {
                return false;
            }

            return !supportRosetta();
        }
    },

    WINDOWS {
        @Override
        public boolean isCurrent() {
            String os = getCurrentOperatingSystem();
            return os.contains("win");
        }
    },

    UNKNOWN {
        @Override
        public boolean isCurrent() {
            return false;
        }
    };

    // -------------------------------------------------------------------------------------------------

    public static Platform getCurrentPlatform() {
        // THE ORDER OF EACH CONSTANT IS HIGHLY SENSITIVE TO RESOLUTION.
        for (Platform platform : values()) {
            if (platform.isCurrent()) {
                return platform;
            }
        }

        return UNKNOWN;
    }

    public abstract boolean isCurrent();

    // -------------------------------------------------------------------------------------------------

    private static String getCurrentOperatingSystem() {
        String os = System.getProperty("os.name");
        return StringUtils.isNullOrBlank(os) ? "" : os.toLowerCase(Locale.ROOT);
    }

    private static String getCurrentArchitecture() {
        String arch = System.getProperty("os.arch");
        return StringUtils.isNullOrBlank(arch) ? "" : arch.toLowerCase(Locale.ROOT);
    }

    private static boolean supportRosetta() {
        try {
            Process process = Runtime.getRuntime().exec(new String[] {"/usr/sbin/sysctl", "sysctl.proc_translated"});
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                // case translated: "sysctl.proc_translated: 1"
                // case not translated: "sysctl.proc_translated: 0"
                // case not arm64: "unknown old 'sysctl.proc_translated'"
                String line = reader.readLine();
                if (line != null && line.trim().matches("sysctl\\.proc_translated: [01]")) {
                    return true;
                }
            }
        } catch (IOException ignored) {
        }

        return false;
    }

}
