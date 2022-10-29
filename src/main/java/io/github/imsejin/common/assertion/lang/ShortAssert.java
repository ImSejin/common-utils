package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Descriptor;

/**
 * Assertion for {@link Short}
 *
 * @param <SELF> this class
 */
public class ShortAssert<
        SELF extends ShortAssert<SELF>>
        extends AbstractNumberAssert<SELF, Short> {

    public ShortAssert(Short actual) {
        super(actual, (short) 0);
    }

    protected ShortAssert(Descriptor<?> descriptor, Short actual) {
        super(descriptor, actual, (short) 0);
    }

}
