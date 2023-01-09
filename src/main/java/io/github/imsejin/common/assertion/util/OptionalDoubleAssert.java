package io.github.imsejin.common.assertion.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.OptionalDouble;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

/**
 * Assertion for {@link OptionalDouble}
 *
 * @param <SELF> this class
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalDoubleAssert<
        SELF extends OptionalDoubleAssert<SELF>>
        extends ObjectAssert<SELF, OptionalDouble>
        implements HolderAssertable<SELF, Double> {

    public OptionalDoubleAssert(OptionalDouble actual) {
        super(actual);
    }

    protected OptionalDoubleAssert(Descriptor<?> descriptor, OptionalDouble actual) {
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
     *     Asserts.that(OptionalDouble.of(1.73)).hasValue(1.73);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalDouble.empty()).hasValue(null);
     *     Asserts.that(OptionalDouble.of(1.73)).hasValue(null);
     *     Asserts.that(OptionalDouble.of(1.73)).hasValue(3.14);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF hasValue(Double expected) {
        if (expected == null || !actual.isPresent() || actual.getAsDouble() != expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.isPresent() ? actual.getAsDouble() : null),
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
     *     Asserts.that(OptionalDouble.empty()).doesNotHaveValue(null);
     *     Asserts.that(OptionalDouble.of(1.73)).doesNotHaveValue(null);
     *     Asserts.that(OptionalDouble.of(1.73)).doesNotHaveValue(3.14);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalDouble.of(1.73)).doesNotHaveValue(1.73);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF doesNotHaveValue(Double expected) {
        if (expected != null && actual.isPresent() && actual.getAsDouble() == expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.getAsDouble()),
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
     *     Asserts.that(OptionalDouble.of(1.73)).isPresent();
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalDouble.empty()).isPresent();
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
     *     Asserts.that(OptionalDouble.empty()).isAbsent();
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalDouble.of(1.73)).isAbsent();
     * }</pre>
     *
     * @return this class
     */
    public SELF isAbsent() {
        if (actual.isPresent()) {
            setDefaultDescription("It is expected to be absent, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.getAsDouble()));

            throw getException();
        }

        return self;
    }

}
