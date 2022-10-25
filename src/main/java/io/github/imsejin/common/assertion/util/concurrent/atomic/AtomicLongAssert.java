package io.github.imsejin.common.assertion.util.concurrent.atomic;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.AbstractNumberAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Assertion for {@link AtomicLong}
 *
 * @param <SELF> this class
 */
public class AtomicLongAssert<
        SELF extends AtomicLongAssert<SELF>>
        extends AbstractNumberAssert<SELF, AtomicLong>
        implements HolderAssertable<SELF, Long> {

    private static final AtomicLong ZERO = new AtomicLong(0);

    public AtomicLongAssert(AtomicLong actual) {
        super(actual, ZERO, Comparator.comparingLong(AtomicLong::get));
    }

    protected AtomicLongAssert(Descriptor<?> descriptor, AtomicLong actual) {
        super(descriptor, actual, ZERO, Comparator.comparingLong(AtomicLong::get));
    }

    @Override
    public SELF isEqualTo(AtomicLong expected) {
        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEqualTo(AtomicLong expected) {
        if (Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be not equal, but they are.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasValue(Long expected) {
        if (expected == null || actual.get() != expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveValue(Long expected) {
        if (expected != null && actual.get() == expected) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

}
