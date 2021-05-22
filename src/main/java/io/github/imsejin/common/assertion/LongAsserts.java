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

package io.github.imsejin.common.assertion;

@SuppressWarnings("unchecked")
public class LongAsserts<SELF extends LongAsserts<SELF>> extends Descriptor<SELF> {

    private final long target;

    LongAsserts(long target) {
        this.target = target;
    }

    public SELF isEqualTo(double number) {
        if (this.target != number) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThan(double number) {
        if (this.target <= number) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThanOrEqualTo(double number) {
        if (this.target < number) throw getException();
        return (SELF) this;
    }

    public SELF isLessThan(double number) {
        if (this.target >= number) throw getException();
        return (SELF) this;
    }

    public SELF isLessThanOrEqualTo(double number) {
        if (this.target > number) throw getException();
        return (SELF) this;
    }

    public SELF isPositive() {
        if (this.target < 1) throw getException();
        return (SELF) this;
    }

    public SELF isZeroOrPositive() {
        if (this.target < 0) throw getException();
        return (SELF) this;
    }

    public SELF isNegative() {
        if (this.target > -1) throw getException();
        return (SELF) this;
    }

    public SELF isZeroOrNegative() {
        if (this.target > 0) throw getException();
        return (SELF) this;
    }

}
