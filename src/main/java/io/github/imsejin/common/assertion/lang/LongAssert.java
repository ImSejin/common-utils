package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Descriptor;

/**
 * Assertion for {@link Long}
 *
 * @param <SELF> this class
 */
public class LongAssert<
        SELF extends LongAssert<SELF>>
        extends AbstractNumberAssert<SELF, Long> {

    public LongAssert(Long actual) {
        super(actual, 0L, Long::compare);
    }

    protected LongAssert(Descriptor<?> descriptor, Long actual) {
        super(descriptor, actual, 0L, Long::compare);
    }

}
