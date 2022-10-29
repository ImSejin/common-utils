package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Descriptor;

/**
 * Assertion for {@link Byte}
 *
 * @param <SELF> this class
 */
public class ByteAssert<
        SELF extends ByteAssert<SELF>>
        extends AbstractNumberAssert<SELF, Byte> {

    public ByteAssert(Byte actual) {
        super(actual, (byte) 0);
    }

    protected ByteAssert(Descriptor<?> descriptor, Byte actual) {
        super(descriptor, actual, (byte) 0);
    }

}
