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

public class RestrictedAssertImpl extends RestrictedAssert {

    public RestrictedAssertImpl(KanCode actual) {
        super(actual);
    }

    public RestrictedAssertImpl hasDepth(int expected) {
        if (actual.getDepth() != expected) throw getException();
        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public RestrictedAssertImpl isNull() {
        super.isNull();
        return this;
    }

    @Override
    public RestrictedAssertImpl isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public RestrictedAssertImpl isSameAs(KanCode expected) {
        super.isSameAs(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl isNotSameAs(KanCode expected) {
        super.isNotSameAs(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl isEqualTo(KanCode expected) {
        super.isEqualTo(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl isNotEqualTo(KanCode expected) {
        super.isNotEqualTo(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl isInstanceOf(Class<?> expected) {
        super.isInstanceOf(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl predicate(Predicate<KanCode> predicate) {
        super.predicate(predicate);
        return this;
    }

    @Override
    public <T> RestrictedAssertImpl returns(T expected, Function<KanCode, T> from) {
        super.returns(expected, from);
        return this;
    }

    @Override
    public RestrictedAssertImpl isEqualTo(String expected) {
        super.isEqualTo(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl isNotEqualTo(String expected) {
        super.isNotEqualTo(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl isParentOf(KanCode expected) {
        super.isParentOf(expected);
        return this;
    }

    @Override
    public RestrictedAssertImpl isChildOf(KanCode expected) {
        super.isChildOf(expected);
        return this;
    }

}
