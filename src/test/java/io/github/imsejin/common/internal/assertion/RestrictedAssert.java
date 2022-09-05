package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.internal.assertion.model.KanCode;

public class RestrictedAssert extends ObjectAssert<RestrictedAssert, KanCode> {

    public RestrictedAssert(KanCode actual) {
        super(actual);
    }

    public RestrictedAssert isEqualTo(String expected) {
        return super.isEqualTo(new KanCode(expected));
    }

    public RestrictedAssert isNotEqualTo(String expected) {
        return super.isNotEqualTo(new KanCode(expected));
    }

    public RestrictedAssert isParentOf(KanCode expected) {
        if (!actual.isParentOf(expected)) throw getException();
        return self;
    }

    public RestrictedAssert isChildOf(KanCode expected) {
        if (!actual.isChildOf(expected)) throw getException();
        return self;
    }

}
