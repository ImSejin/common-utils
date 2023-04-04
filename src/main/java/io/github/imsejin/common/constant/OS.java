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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Operating system.
 */
public enum OS {

    /**
     * Linux-based.
     */
    LINUX("linux"),

    /**
     * Apple Macintosh(macOS).
     */
    MAC("mac"),

    /**
     * IBM AIX.
     */
    AIX("aix"),

    /**
     * Oracle Solaris.
     */
    SOLARIS("sunos", "solaris"),

    /**
     * Microsoft Windows.
     */
    WINDOWS("win"),

    /**
     * An Operating system other than {@link #LINUX}, {@link #MAC},
     * {@link #AIX}, {@link #SOLARIS}, or {@link #WINDOWS}.
     */
    OTHER;

    private static final OS CURRENT_OS;

    private final List<String> keywords;

    static {
        String osName = System.getProperty("os.name").toLowerCase(Locale.US);

        OS currentOs = null;

        outer:
        for (OS os : values()) {
            for (String keyword : os.keywords) {
                if (osName.contains(keyword)) {
                    currentOs = os;
                    break outer;
                }
            }
        }

        CURRENT_OS = currentOs == null ? OTHER : currentOs;
    }

    OS(String... keywords) {
        this.keywords = Collections.unmodifiableList(Arrays.asList(keywords));
    }

    public static OS getCurrentOS() {
        return CURRENT_OS;
    }

    public boolean isCurrentOS() {
        return this == CURRENT_OS;
    }

}
