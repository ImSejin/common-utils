package io.github.imsejin.common.assertion.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.OptionalInt;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

/**
 * Assertion for {@link OptionalInt}
 *
 * @param <SELF> this class
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalIntAssert<
        SELF extends OptionalIntAssert<SELF>>
        extends ObjectAssert<SELF, OptionalInt>
        implements HolderAssertable<SELF, Integer> {

    public OptionalIntAssert(OptionalInt actual) {
        super(actual);
    }

    protected OptionalIntAssert(Descriptor<?> descriptor, OptionalInt actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual value has expected value as a content.
     *
     * <p> If actual value is empty or expected value is {@code null},
     * the assertion always fails.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(OptionalInt.of(1)).hasValue(1);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalInt.empty()).hasValue(null);
     *     Asserts.that(OptionalInt.of(1)).hasValue(null);
     *     Asserts.that(OptionalInt.of(1)).hasValue(2);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF hasValue(Integer expected) {
        if (expected == null || !actual.isPresent() || actual.getAsInt() != expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.isPresent() ? actual.getAsInt() : null),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value doesn't have expected value as a content.
     *
     * <p> If actual value is empty or expected value is {@code null},
     * the assertion always passes.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(OptionalInt.empty()).doesNotHaveValue(null);
     *     Asserts.that(OptionalInt.of(1)).doesNotHaveValue(null);
     *     Asserts.that(OptionalInt.of(1)).doesNotHaveValue(2);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalInt.of(1)).doesNotHaveValue(1);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF doesNotHaveValue(Integer expected) {
        if (expected != null && actual.isPresent() && actual.getAsInt() == expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.getAsInt()),
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
     *     Asserts.that(OptionalInt.of(1)).isPresent();
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalInt.empty()).isPresent();
     * }</pre>
     *
     * @return this class
     */
    public SELF isPresent() {
        if (!actual.isPresent()) {
            setDefaultDescription("It is expected to be present, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", null));

            throw getException();
        }

        return this.self;
    }

    /**
     * Asserts that actual value is absent.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(OptionalInt.empty()).isAbsent();
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalInt.of(1)).isAbsent();
     * }</pre>
     *
     * @return this class
     */
    public SELF isAbsent() {
        if (actual.isPresent()) {
            setDefaultDescription("It is expected to be absent, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.getAsInt()));

            throw getException();
        }

        return self;
    }

}
