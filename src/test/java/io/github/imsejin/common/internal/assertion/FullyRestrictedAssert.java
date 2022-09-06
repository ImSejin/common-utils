package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.internal.assertion.model.KanCode;

public class FullyRestrictedAssert extends ObjectAssert<FullyRestrictedAssert, KanCode> {

    public FullyRestrictedAssert(KanCode actual) {
        super(actual);
    }

    public FullyRestrictedAssert isEqualTo(String expected) {
        return super.isEqualTo(new KanCode(expected));
    }

    public FullyRestrictedAssert isNotEqualTo(String expected) {
        return super.isNotEqualTo(new KanCode(expected));
    }

    public FullyRestrictedAssert isParentOf(KanCode expected) {
        if (!actual.isParentOf(expected)) throw getException();
        return self;
    }

    public FullyRestrictedAssert isChildOf(KanCode expected) {
        if (!actual.isChildOf(expected)) throw getException();
        return self;
    }

}
