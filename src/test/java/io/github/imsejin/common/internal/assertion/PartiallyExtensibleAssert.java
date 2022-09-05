package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.internal.assertion.model.Bar;
import io.github.imsejin.common.util.StringUtils;

public class PartiallyExtensibleAssert<
        SELF extends PartiallyExtensibleAssert<SELF>>
        extends FullyExtensibleAssert<SELF, Bar> {

    public PartiallyExtensibleAssert(Bar actual) {
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
