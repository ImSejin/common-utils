package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.internal.assertion.model.Foo;

public class PartiallyExtensibleAssert<
        SELF extends PartiallyExtensibleAssert<SELF>>
        extends ObjectAssert<SELF, Foo> {

    public PartiallyExtensibleAssert(Foo actual) {
        super(actual);
    }

    public SELF hasNullValue() {
        if (actual.getValue() != null) throw getException();
        return self;
    }

}
