package io.github.imsejin.common.assertion.util.concurrent.atomic;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.AbstractNumberAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Comparator.comparingLong;

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
        super(actual, ZERO, comparingLong(AtomicLong::get));
    }

    protected AtomicLongAssert(Descriptor<?> descriptor, AtomicLong actual) {
        super(descriptor, actual, ZERO, comparingLong(AtomicLong::get));
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
