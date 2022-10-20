package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;

/**
 * Assertion for {@link Optional}
 *
 * @param <SELF>  this class
 * @param <VALUE> value of optional
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalAssert<
        SELF extends OptionalAssert<SELF, VALUE>,
        VALUE>
        extends ObjectAssert<SELF, Optional<VALUE>> {

    public OptionalAssert(Optional<VALUE> actual) {
        super(actual);
    }

    protected OptionalAssert(Descriptor<?> descriptor, Optional<VALUE> actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual value is present.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Optional.of("alpha")).isPresent();
     *
     *     // Assertion will fail.
     *     Asserts.that(Optional.ofNullable(null)).isPresent();
     * }</pre>
     *
     * @return this class
     */
    public SELF isPresent() {
        if (!actual.isPresent()) {
            setDefaultDescription("It is expected to be present, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return this.self;
    }

    /**
     * Asserts that actual value is absent.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Optional.ofNullable(null)).isAbsent();
     *
     *     // Assertion will fail.
     *     Asserts.that(Optional.of("alpha")).isAbsent();
     * }</pre>
     *
     * @return this class
     */
    public SELF isAbsent() {
        if (actual.isPresent()) {
            setDefaultDescription("It is expected to be absent, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

}
