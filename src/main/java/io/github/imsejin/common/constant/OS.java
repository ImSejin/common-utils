/*
 * Copyright 2020 Sejin Im
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

import java.util.Locale;

/**
 * Operating system.
 */
public enum OS {

    /**
     * Linux-based.
     */
    LINUX,

    /**
     * Apple Macintosh(macOS).
     */
    MAC,

    /**
     * IBM AIX.
     */
    AIX,

    /**
     * Oracle Solaris.
     */
    SOLARIS,

    /**
     * Microsoft Windows.
     */
    WINDOWS,

    /**
     * An Operating system other than {@link #LINUX}, {@link #MAC},
     * {@link #AIX}, {@link #SOLARIS}, or {@link #WINDOWS}.
     */
    OTHER;

    private static final OS CURRENT_OS;

    static {
        String osName = System.getProperty("os.name").toLowerCase(Locale.US);

        OS os;
        if (osName.contains("linux")) {
            os = LINUX;
        } else if (osName.contains("mac")) {
            os = MAC;
        } else if (osName.contains("aix")) {
            os = AIX;
        } else if (osName.contains("sunos") || osName.contains("solaris")) {
            os = SOLARIS;
        } else if (osName.contains("win")) {
            os = WINDOWS;
        } else {
            os = OTHER;
        }

        CURRENT_OS = os;
    }

    public static OS getCurrentOS() {
        return CURRENT_OS;
    }

    public boolean isCurrentOS() {
        return this == CURRENT_OS;
    }

}
