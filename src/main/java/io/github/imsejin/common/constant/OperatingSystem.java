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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableSet;

public enum OperatingSystem {

    /**
     * Microsoft Windows.
     *
     * <pre>{@code
     *     ["win"]
     * }</pre>
     */
    WINDOWS(singleton("win")),

    /**
     * Apple macOS.
     *
     * <pre>{@code
     *     ["mac"]
     * }</pre>
     */
    MAC(singleton("mac")),

    /**
     * Unix including Linux and IBM AIX.
     *
     * <pre>{@code
     *     ["nix", "nux", "aix"]
     * }</pre>
     */
    UNIX(new HashSet<>(Arrays.asList("nix", "nux", "aix"))),

    /**
     * Oracle Solaris.
     *
     * <pre>{@code
     *     ["sunos"]
     * }</pre>
     */
    SOLARIS(singleton("sunos"));

    /**
     * Keywords that distinguish operating systems.
     */
    private final Set<String> keywords;

    OperatingSystem(@Nonnull Set<String> keywords) {
        this.keywords = unmodifiableSet(keywords);
    }

    public static boolean contains(@Nonnull String osName) {
        return Arrays.stream(values())
                .anyMatch(os -> StringUtils.anyContains(osName, os.keywords));
    }

    public static Optional<OperatingSystem> of(@Nonnull String osName) {
        return Arrays.stream(values())
                .filter(os -> StringUtils.anyContains(osName, os.keywords))
                .findFirst();
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

}
