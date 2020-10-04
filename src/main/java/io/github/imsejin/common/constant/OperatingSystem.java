package io.github.imsejin.common.constant;

import io.github.imsejin.common.constant.interfaces.KeyValue;
import io.github.imsejin.common.util.StringUtils;

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

    OperatingSystem(Set<String> keywords) {
        this.keywords = Collections.unmodifiableSet(keywords);
    }

    public static boolean contains(@Nonnull String osName) {
        return Arrays.stream(OperatingSystem.values())
                .anyMatch(os -> StringUtils.anyContains(osName, os.keywords));
    }

    public static Optional<OperatingSystem> of(String osName) {
        return Arrays.stream(OperatingSystem.values())
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
