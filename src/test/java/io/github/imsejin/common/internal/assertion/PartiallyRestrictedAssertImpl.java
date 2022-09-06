package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.internal.assertion.model.Bar;

import java.time.Instant;

public class PartiallyRestrictedAssertImpl<
        ACTUAL extends Bar>
        extends PartiallyRestrictedAssert<ACTUAL> {

    public PartiallyRestrictedAssertImpl(ACTUAL actual) {
        super(actual);
    }

    @Override
    public PartiallyRestrictedAssertImpl<ACTUAL> hasNumericValue() {
        super.hasNumericValue();
        return this;
    }

    public PartiallyRestrictedAssertImpl<ACTUAL> hasWhitespaceValue() {
        String value = actual.getValue();
        if (value == null || !value.matches("\\s")) {
            throw getException();
        }

        return this;
    }

    public PartiallyRestrictedAssertImpl<ACTUAL> hasSameTimeAs(Instant expected) {
        Instant createdTime = actual.getCreatedTime();
        if (createdTime == null || createdTime.compareTo(expected) != 0) {
            throw getException();
        }

        return this;
    }

}
