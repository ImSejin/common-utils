package io.github.imsejin.common.assertion.util.concurrent.atomic;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Assertion for {@link AtomicBoolean}
 *
 * @param <SELF> this class
 */
public class AtomicBooleanAssert<
        SELF extends AtomicBooleanAssert<SELF>>
        extends ObjectAssert<SELF, AtomicBoolean>
        implements HolderAssertable<SELF, Boolean> {

    public AtomicBooleanAssert(AtomicBoolean actual) {
        super(actual);
    }

    protected AtomicBooleanAssert(Descriptor<?> descriptor, AtomicBoolean actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF hasValue(Boolean expected) {
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
    public SELF doesNotHaveValue(Boolean expected) {
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
