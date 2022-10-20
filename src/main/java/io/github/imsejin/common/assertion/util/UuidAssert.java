package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.UUID;

/**
 * Assertion for {@link UUID}
 *
 * @param <SELF> this class
 */
public class UuidAssert<SELF extends UuidAssert<SELF>> extends ObjectAssert<SELF, UUID> {

    public UuidAssert(UUID actual) {
        super(actual);
    }

    protected UuidAssert(Descriptor<?> descriptor, UUID actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual value is nil.
     *
     * <pre>{@code
     *     UUID uuid = UUID.fromString("f3f5cde4-0235-4ad9-a53f-03abf35cd109");
     *
     *     // Assertion will pass.
     *     Asserts.that(new UUID(0, 0)).isNil();
     *
     *     // Assertion will fail.
     *     Asserts.that(uuid).isNil();
     * }</pre>
     *
     * @return this class
     */
    public UuidAssert<SELF> isNil() {
        if (actual.getMostSignificantBits() != 0 || actual.getLeastSignificantBits() != 0) {
            setDefaultDescription("It is expected to be nil, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is not nil.
     *
     * <pre>{@code
     *     UUID uuid = UUID.fromString("f3f5cde4-0235-4ad9-a53f-03abf35cd109");
     *
     *     // Assertion will pass.
     *     Asserts.that(uuid).isNotNil();
     *
     *     // Assertion will fail.
     *     Asserts.that(new UUID(0, 0)).isNotNil();
     * }</pre>
     *
     * @return this class
     */
    public UuidAssert<SELF> isNotNil() {
        if (actual.getMostSignificantBits() == 0 && actual.getLeastSignificantBits() == 0) {
            setDefaultDescription("It is expected not to be nil, but nil.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into its version.
     *
     * <pre>{@code
     *     Asserts.that(UUID.randomUUID())
     *             .isNotNil()
     *             .asVersion()
     *             .isBetween(0, 15);
     * }</pre>
     *
     * @return assertion for integer
     */
    public NumberAssert<?, Integer> asVersion() {
        class NumberAssertImpl extends NumberAssert<NumberAssertImpl, Integer> {
            NumberAssertImpl(Descriptor<?> descriptor, Integer actual) {
                super(descriptor, actual);
            }
        }

        int version = actual.version();
        return new NumberAssertImpl(this, version);
    }

}
