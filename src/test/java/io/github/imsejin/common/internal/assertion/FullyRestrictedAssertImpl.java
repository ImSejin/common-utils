/*
 * Copyright 2022 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.internal.assertion;

import io.github.imsejin.common.internal.assertion.model.KanCode;

import java.util.function.Function;
import java.util.function.Predicate;

public class FullyRestrictedAssertImpl extends FullyRestrictedAssert {

    public FullyRestrictedAssertImpl(KanCode actual) {
        super(actual);
    }

    public FullyRestrictedAssertImpl hasDepth(int expected) {
        if (actual.getDepth() != expected) throw getException();
        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FullyRestrictedAssertImpl isNull() {
        super.isNull();
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isSameAs(KanCode expected) {
        super.isSameAs(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isNotSameAs(KanCode expected) {
        super.isNotSameAs(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isEqualTo(KanCode expected) {
        super.isEqualTo(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isNotEqualTo(KanCode expected) {
        super.isNotEqualTo(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isInstanceOf(Class<?> expected) {
        super.isInstanceOf(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl predicate(Predicate<KanCode> predicate) {
        super.predicate(predicate);
        return this;
    }

    @Override
    public <T> FullyRestrictedAssertImpl returns(T expected, Function<KanCode, T> from) {
        super.returns(expected, from);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isEqualTo(String expected) {
        super.isEqualTo(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isNotEqualTo(String expected) {
        super.isNotEqualTo(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isParentOf(KanCode expected) {
        super.isParentOf(expected);
        return this;
    }

    @Override
    public FullyRestrictedAssertImpl isChildOf(KanCode expected) {
        super.isChildOf(expected);
        return this;
    }

}
