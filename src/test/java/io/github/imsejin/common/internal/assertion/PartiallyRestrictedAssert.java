package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.internal.assertion.model.Foo;

public class PartiallyRestrictedAssert<
        ACTUAL extends Foo>
        extends ObjectAssert<PartiallyRestrictedAssert<ACTUAL>, ACTUAL> {

    public PartiallyRestrictedAssert(ACTUAL actual) {
        super(actual);
    }

    public PartiallyRestrictedAssert<ACTUAL> hasNumericValue() {
        String value = actual.getValue();
        if (value == null || !value.matches(".*[0-9].*")) {
            throw getException();
        }

        return self;
    }

}
