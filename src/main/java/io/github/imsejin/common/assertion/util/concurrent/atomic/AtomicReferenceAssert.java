package io.github.imsejin.common.assertion.util.concurrent.atomic;

import java.util.AbstractMap.SimpleEntry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

/**
 * Assertion for {@link AtomicReference}
 *
 * @param <SELF>   this class
 * @param <ACTUAL> {@link AtomicReference}
 * @param <VALUE>  value in the {@link AtomicReference}
 */
public class AtomicReferenceAssert<
        SELF extends AtomicReferenceAssert<SELF, ACTUAL, VALUE>,
        ACTUAL extends AtomicReference<VALUE>,
        VALUE>
        extends ObjectAssert<SELF, ACTUAL>
        implements HolderAssertable<SELF, VALUE> {

    public AtomicReferenceAssert(ACTUAL actual) {
        super(actual);
    }

    protected AtomicReferenceAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF hasValue(VALUE expected) {
        if (!Objects.equals(actual.get(), expected)) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveValue(VALUE expected) {
        if (Objects.equals(actual.get(), expected)) {
            setDefaultDescription(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

}
