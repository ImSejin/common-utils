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

import io.github.imsejin.common.assertion.Descriptor;

import java.util.Objects;

@SuppressWarnings("unchecked")
public abstract class AbstractNumberAsserts<
        SELF extends AbstractNumberAsserts<SELF, NUMBER>,
        NUMBER extends Number & Comparable<NUMBER>>
        extends Descriptor<SELF> {

    private final NUMBER actual;

    private final NUMBER zero;

    protected AbstractNumberAsserts(NUMBER actual) {
        this.actual = actual;
        this.zero = toNumber((NUMBER) Integer.valueOf(0), (Class<NUMBER>) actual.getClass());
    }

    /**
     * Converts a numeric string to a number of the given type.
     *
     * @param number     numeric string
     * @param numberType number type to convert numeric string to number
     * @return converted number
     */
    private static <N extends Number & Comparable<? extends Number>> N toNumber(N number, Class<N> numberType) {
        if (numberType == Byte.class) return (N) Byte.valueOf(number.byteValue());
        if (numberType == Short.class) return (N) Short.valueOf(number.shortValue());
        if (numberType == Integer.class) return (N) Integer.valueOf(number.intValue());
        if (numberType == Long.class) return (N) Long.valueOf(number.longValue());
        if (numberType == Float.class) return (N) Float.valueOf(number.floatValue());
        if (numberType == Double.class) return (N) Double.valueOf(number.doubleValue());

        return null;
    }

    public SELF isNull() {
        if (this.actual != null) {
            as("It is expected to be null, but not null. (actual: '{0}')", this.actual);
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isNotNull() {
        if (this.actual == null) {
            as("It is expected to be not null, but null. (actual: 'null')");
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isEqualTo(NUMBER expected) {
        if (!Objects.deepEquals(this.actual, expected)) throw getException();
        return (SELF) this;
    }

    public SELF isNotEqualTo(NUMBER expected) {
        if (Objects.deepEquals(this.actual, expected)) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThan(NUMBER expected) {
        if (this.actual.compareTo(expected) <= 0) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThanOrEqualTo(NUMBER expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    public SELF isLessThan(NUMBER expected) {
        if (this.actual.compareTo(expected) >= 0) throw getException();
        return (SELF) this;
    }

    public SELF isLessThanOrEqualTo(NUMBER expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    public SELF isPositive() {
        if (this.actual.compareTo(this.zero) <= 0) throw getException();
        return (SELF) this;
    }

    public SELF isZeroOrPositive() {
        if (this.actual.compareTo(this.zero) < 0) throw getException();
        return (SELF) this;
    }

    public SELF isNegative() {
        if (this.actual.compareTo(this.zero) >= 0) throw getException();
        return (SELF) this;
    }

    public SELF isZeroOrNegative() {
        if (this.actual.compareTo(this.zero) > 0) throw getException();
        return (SELF) this;
    }

}
