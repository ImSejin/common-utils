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

import io.github.imsejin.common.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public enum OperatingSystem {

    /**
     * Microsoft Windows.
     *
     * <ul>
     *     <li>win</li>
     * </ul>
     */
    WINDOWS("win"),

    /**
     * Apple macOS.
     *
     * <ul>
     *     <li>mac</li>
     * </ul>
     */
    MAC("mac"),

    /**
     * Unix including Linux and IBM AIX.
     *
     * <ul>
     *     <li>nix</li>
     *     <li>nux</li>
     *     <li>aix</li>
     * </ul>
     */
    UNIX("nix", "nux", "aix"),

    /**
     * Oracle Solaris.
     *
     * <ul>
     *     <li>sunos</li>
     * </ul>
     */
    SOLARIS("sunos");

    /**
     * Keywords that distinguish operating systems.
     */
    private final Set<String> keywords;

    OperatingSystem(String... keywords) {
        this.keywords = Arrays.stream(keywords).collect(collectingAndThen(toSet(),
                Collections::unmodifiableSet));
    }

    /**
     * Returns whether the given name is the one of the name of operating system.
     *
     * <pre><code>
     *     contains("Windows 10");  // true
     *     contains("Mac OS");      // true
     *     contains("Linux");       // true
     *     contains("Unix");        // true
     *     contains("Sun OS");      // true
     * </code></pre>
     *
     * @param osName name of operating system
     * @return whether the name is the one of the name of operating system.
     */
    public static boolean contains(@Nonnull String osName) {
        String name = osName.toLowerCase().replaceAll("\\s", "");

        for (OperatingSystem os : values()) {
            if (StringUtils.anyContains(name, os.keywords)) return true;
        }

        return false;
    }

    /**
     * Returns {@link OperatingSystem} that matches the given name of operating system.
     *
     * <pre><code>
     *     of("Win10").get();         // OperatingSystem.WINDOWS
     *     of("WINDOWS").get();       // OperatingSystem.WINDOWS
     *     of("MacOSX").get();        // OperatingSystem.MAC
     *     of("RedHat Linux").get();  // OperatingSystem.UNIX
     *     of("Unix").get();          // OperatingSystem.UNIX
     *     of("SunOS").get();         // OperatingSystem.SOLARIS
     * </code></pre>
     *
     * @param osName name of operating system
     * @return {@link OperatingSystem}
     */
    public static Optional<OperatingSystem> of(@Nonnull String osName) {
        String name = osName.toLowerCase().replaceAll("\\s", "");
        return Arrays.stream(values())
                .filter(os -> StringUtils.anyContains(name, os.keywords))
                .findFirst();
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

}
