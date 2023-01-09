package io.github.imsejin.common.assertion.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

/**
 * Assertion for {@link Optional}
 *
 * @param <SELF>  this class
 * @param <VALUE> value in the {@link Optional}
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalAssert<
        SELF extends OptionalAssert<SELF, VALUE>,
        VALUE>
        extends ObjectAssert<SELF, Optional<VALUE>>
        implements HolderAssertable<SELF, VALUE> {

    public OptionalAssert(Optional<VALUE> actual) {
        super(actual);
    }

    protected OptionalAssert(Descriptor<?> descriptor, Optional<VALUE> actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual value has expected value as a content.
     *
     * <p> If actual value is empty, the assertion always fails.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Optional.of("alpha")).hasValue("alpha");
     *
     *     // Assertion will fail.
     *     Asserts.that(Optional.empty()).hasValue(null);
     *     Asserts.that(Optional.of("alpha")).hasValue("beta");
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF hasValue(VALUE expected) {
        VALUE value = actual.orElse(null);

        if (value == null || !value.equals(expected)) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", value),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value doesn't have expected value as a content.
     *
     * <p> If actual value is empty, the assertion always passes.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Optional.of("alpha")).doesNotHaveValue("beta");
     *
     *     // Assertion will fail.
     *     Asserts.that(Optional.empty()).doesNotHaveValue(null);
     *     Asserts.that(Optional.of("alpha")).doesNotHaveValue("alpha");
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF doesNotHaveValue(VALUE expected) {
        VALUE value = actual.orElse(null);

        if (value != null && value.equals(expected)) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", value),
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
     *     Asserts.that(Optional.empty()).isPresent();
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
     *     Asserts.that(Optional.empty()).isAbsent();
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
