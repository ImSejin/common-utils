package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.ContainerAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.OptionalDouble;

/**
 * Assertion for {@link OptionalDouble}
 *
 * @param <SELF> this class
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalDoubleAssert<
        SELF extends OptionalDoubleAssert<SELF>>
        extends ObjectAssert<SELF, OptionalDouble>
        implements ContainerAssertable<SELF, Double> {

    public OptionalDoubleAssert(OptionalDouble actual) {
        super(actual);
    }

    protected OptionalDoubleAssert(Descriptor<?> descriptor, OptionalDouble actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual value contains expected value as a content.
     *
     * <p> If actual value is empty or expected value is {@code null},
     * the assertion always fails.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(OptionalDouble.of(1.73)).contains(1.73);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalDouble.empty()).contains(null);
     *     Asserts.that(OptionalDouble.of(1.73)).contains(null);
     *     Asserts.that(OptionalDouble.of(1.73)).contains(3.14);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF contains(Double expected) {
        if (expected == null || !actual.isPresent() || actual.getAsDouble() != expected) {
            setDefaultDescription(ContainerAssertable.DEFAULT_DESCRIPTION_CONTAINS);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.isPresent() ? actual.getAsDouble() : null),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value doesn't contain expected value as a content.
     *
     * <p> If actual value is empty or expected value is {@code null},
     * the assertion always passes.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(OptionalDouble.empty()).doesNotContain(null);
     *     Asserts.that(OptionalDouble.of(1.73)).doesNotContain(null);
     *     Asserts.that(OptionalDouble.of(1.73)).doesNotContain(3.14);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalDouble.of(1.73)).doesNotContain(1.73);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF doesNotContain(Double expected) {
        if (expected != null && actual.isPresent() && actual.getAsDouble() == expected) {
            setDefaultDescription(ContainerAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN);
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
