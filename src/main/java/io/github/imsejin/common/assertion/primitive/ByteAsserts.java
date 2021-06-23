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

@SuppressWarnings("unchecked")
public class ByteAsserts<SELF extends ByteAsserts<SELF>> extends AbstractNumberAsserts<SELF, Byte> {

    private final Byte actual;

    public ByteAsserts(Byte actual) {
        super(actual);
        this.actual = actual;
    }

    @Override
    public SELF isGreaterThan(Byte expected) {
        if (this.actual <= expected) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(Byte expected) {
        if (this.actual < expected) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isLessThan(Byte expected) {
        if (this.actual >= expected) throw getException();
        return (SELF) this;
    }

    @Override
    public SELF isLessThanOrEqualTo(Byte expected) {
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

}
