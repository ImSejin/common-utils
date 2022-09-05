package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.internal.assertion.model.KanCode;

public class KanCodeAssert extends ObjectAssert<KanCodeAssert, KanCode> {

    public KanCodeAssert(KanCode actual) {
        super(actual);
    }

    public KanCodeAssert isEqualTo(String expected) {
        return super.isEqualTo(new KanCode(expected));
    }

    public KanCodeAssert isNotEqualTo(String expected) {
        return super.isNotEqualTo(new KanCode(expected));
    }

    public KanCodeAssert isParentOf(KanCode expected) {
        if (!actual.isParentOf(expected)) throw getException();
        return self;
    }

    public KanCodeAssert isChildOf(KanCode expected) {
        if (!actual.isChildOf(expected)) throw getException();
        return self;
    }

}
