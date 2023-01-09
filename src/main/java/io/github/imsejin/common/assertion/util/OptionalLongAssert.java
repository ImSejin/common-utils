package io.github.imsejin.common.assertion.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.OptionalLong;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

/**
 * Assertion for {@link OptionalLong}
 *
 * @param <SELF> this class
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalLongAssert<
        SELF extends OptionalLongAssert<SELF>>
        extends ObjectAssert<SELF, OptionalLong>
        implements HolderAssertable<SELF, Long> {

    public OptionalLongAssert(OptionalLong actual) {
        super(actual);
    }

    protected OptionalLongAssert(Descriptor<?> descriptor, OptionalLong actual) {
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
     *     Asserts.that(OptionalLong.of(10)).hasValue(10);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalLong.empty()).hasValue(null);
     *     Asserts.that(OptionalLong.of(10)).hasValue(null);
     *     Asserts.that(OptionalLong.of(10)).hasValue(20);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF hasValue(Long expected) {
        if (expected == null || !actual.isPresent() || actual.getAsLong() != expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.isPresent() ? actual.getAsLong() : null),
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
     *     Asserts.that(OptionalLong.empty()).doesNotHaveValue(null);
     *     Asserts.that(OptionalLong.of(10)).doesNotHaveValue(null);
     *     Asserts.that(OptionalLong.of(10)).doesNotHaveValue(20);
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalLong.of(10)).doesNotHaveValue(10);
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF doesNotHaveValue(Long expected) {
        if (expected != null && actual.isPresent() && actual.getAsLong() == expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.getAsLong()),
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
     *     Asserts.that(OptionalLong.of(10)).isPresent();
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalLong.empty()).isPresent();
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
     *     Asserts.that(OptionalLong.empty()).isAbsent();
     *
     *     // Assertion will fail.
     *     Asserts.that(OptionalLong.of(10)).isAbsent();
     * }</pre>
     *
     * @return this class
     */
    public SELF isAbsent() {
        if (actual.isPresent()) {
            setDefaultDescription("It is expected to be absent, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.value", actual.getAsLong()));

            throw getException();
        }

        return self;
    }

}
