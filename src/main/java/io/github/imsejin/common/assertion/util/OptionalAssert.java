package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.ContainerAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.Objects;
import java.util.Optional;

/**
 * Assertion for {@link Optional}
 *
 * @param <SELF>  this class
 * @param <VALUE> value in the optional
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalAssert<
        SELF extends OptionalAssert<SELF, VALUE>,
        VALUE>
        extends ObjectAssert<SELF, Optional<VALUE>>
        implements ContainerAssertable<SELF, VALUE> {

    public OptionalAssert(Optional<VALUE> actual) {
        super(actual);
    }

    protected OptionalAssert(Descriptor<?> descriptor, Optional<VALUE> actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF contains(VALUE expected) {
        VALUE value = actual.orElse(null);

        if (!Objects.equals(value, expected)) {
            setDefaultDescription(ContainerAssertable.DEFAULT_DESCRIPTION_CONTAINS);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", value),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotContain(VALUE expected) {
        VALUE value = actual.orElse(null);

        if (Objects.equals(value, expected)) {
            setDefaultDescription(ContainerAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
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
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.orElse(null)));

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
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.orElse(null)));

            throw getException();
        }

        return self;
    }

}
