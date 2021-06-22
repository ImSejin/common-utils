/*
 * Copyright 2021 Sejin Im
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

package io.github.imsejin.common.assertion.primitive;

import io.github.imsejin.common.util.MathUtils;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class DoubleAsserts<SELF extends DoubleAsserts<SELF>> extends AbstractNumberAsserts<SELF, Double> {

    private final Double actual;

    public DoubleAsserts(Double actual) {
        super(actual);
        this.actual = actual;
    }

    @Override
    public SELF isEqualTo(Double expected) {
        if (!Objects.deepEquals(this.actual, expected)) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isNotEqualTo(Double expected) {
        if (Objects.deepEquals(this.actual, expected)) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isGreaterThan(Double expected) {
        if (this.actual <= expected) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(Double expected) {
        if (this.actual < expected) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isLessThan(Double expected) {
        if (this.actual >= expected) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isLessThanOrEqualTo(Double expected) {
        if (this.actual > expected) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isPositive() {
        if (this.actual < 1) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isZeroOrPositive() {
        if (this.actual < 0) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isNegative() {
        if (this.actual > -1) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isZeroOrNegative() {
        if (this.actual > 0) throw getException();
        return (SELF) this;
    }

    public SELF hasDeciamlPart() {
        if (!MathUtils.hasDecimalPart(this.actual)) throw getException();
        return (SELF) this;
    }

}
