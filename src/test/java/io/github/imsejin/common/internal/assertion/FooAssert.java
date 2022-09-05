package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.internal.assertion.model.Foo;

public class FooAssert<SELF extends FooAssert<SELF, ACTUAL>, ACTUAL extends Foo> extends ObjectAssert<SELF, ACTUAL> {

    public FooAssert(ACTUAL actual) {
        super(actual);
    }

    public SELF hasNullValue() {
        if (actual.getValue() != null) throw getException();
        return self;
    }

}
