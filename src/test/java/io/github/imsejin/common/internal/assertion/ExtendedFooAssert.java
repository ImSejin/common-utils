package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.internal.assertion.model.Foo;
import io.github.imsejin.common.util.StringUtils;

public class ExtendedFooAssert<SELF extends ExtendedFooAssert<SELF>> extends FooAssert<SELF, Foo> {

    public ExtendedFooAssert(Foo actual) {
        super(actual);
    }

    @Override
    public SELF hasNullValue() {
        if (!StringUtils.isNullOrEmpty(actual.getValue())) throw getException();
        return self;
    }

    public SELF hasSingleValue() {
        String value = actual.getValue();
        if (value == null || value.length() != 1) throw getException();

        return self;
    }

}
