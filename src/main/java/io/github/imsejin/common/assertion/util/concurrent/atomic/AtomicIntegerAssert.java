package io.github.imsejin.common.assertion.util.concurrent.atomic;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.AbstractNumberAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparingInt;

/**
 * Assertion for {@link AtomicInteger}
 *
 * @param <SELF> this class
 */
public class AtomicIntegerAssert<
        SELF extends AtomicIntegerAssert<SELF>>
        extends AbstractNumberAssert<SELF, AtomicInteger>
        implements HolderAssertable<SELF, Integer> {

    private static final AtomicInteger ZERO = new AtomicInteger(0);

    public AtomicIntegerAssert(AtomicInteger actual) {
        super(actual, ZERO, comparingInt(AtomicInteger::get));
    }

    protected AtomicIntegerAssert(Descriptor<?> descriptor, AtomicInteger actual) {
        super(descriptor, actual, ZERO, comparingInt(AtomicInteger::get));
    }

    @Override
    public SELF hasValue(Integer expected) {
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
    public SELF doesNotHaveValue(Integer expected) {
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
