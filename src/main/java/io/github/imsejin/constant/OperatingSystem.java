package io.github.imsejin.constant;

import io.github.imsejin.constant.interfaces.KeyValue;

import java.util.*;

public enum OperatingSystem implements KeyValue {

    /**
     * Microsoft Windows.
     */
    WINDOWS(Collections.singleton("win")),

    /**
     * Apple macOS.
     */
    MAC(Collections.singleton("mac")),

    /**
     * Unix including Linux and IBM AIX.
     */
    UNIX(new HashSet<>(Arrays.asList("nix", "nux", "aix"))),

    /**
     * Oracle Solaris.
     */
    SOLARIS(Collections.singleton("sunos"));

    /**
     * Keywords that distinguish operating systems.
     */
    private final Set<String> keywords;

    OperatingSystem(Set<String> keywords) {
        this.keywords = Collections.unmodifiableSet(keywords);
    }

    public static boolean contains(String keyword) {
        for (OperatingSystem os : OperatingSystem.values()) {
            if (os.keywords.contains(keyword)) return true;
        }

        return false;
    }

    public static Optional<OperatingSystem> of(String keyword) {
        return Arrays.stream(OperatingSystem.values())
                .filter(os -> os.keywords.contains(keyword))
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
