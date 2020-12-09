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

import io.github.imsejin.common.constant.interfaces.KeyValue;
import io.github.imsejin.common.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.*;

public enum OperatingSystem implements KeyValue {

    /**
     * Microsoft Windows.
     *
     * <pre>{@code
     *     ["win"]
     * }</pre>
     */
    WINDOWS(Collections.singleton("win")),

    /**
     * Apple macOS.
     *
     * <pre>{@code
     *     ["mac"]
     * }</pre>
     */
    MAC(Collections.singleton("mac")),

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
    SOLARIS(Collections.singleton("sunos"));

    /**
     * Keywords that distinguish operating systems.
     */
    private final Set<String> keywords;

    OperatingSystem(@Nonnull Set<String> keywords) {
        this.keywords = Collections.unmodifiableSet(keywords);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String key() {
        return name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value() {
        return new ArrayList<>(this.keywords).get(0);
    }

    public Set<String> getKeywords() {
        return keywords;
    }

}
