package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Descriptor;

/**
 * Assertion for {@link Integer}
 *
 * @param <SELF> this class
 */
public class IntegerAssert<
        SELF extends IntegerAssert<SELF>>
        extends AbstractNumberAssert<SELF, Integer> {

    public IntegerAssert(Integer actual) {
        super(actual, 0);
    }

    protected IntegerAssert(Descriptor<?> descriptor, Integer actual) {
        super(descriptor, actual, 0);
    }

}
