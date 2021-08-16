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

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

public class NumberAssert<
        SELF extends NumberAssert<SELF, NUMBER>,
        NUMBER extends Number & Comparable<NUMBER>>
        extends AbstractObjectAssert<SELF, NUMBER> {

    private final NUMBER zero;

    @SuppressWarnings("unchecked")
    public NumberAssert(NUMBER actual) {
        super(actual);
        this.zero = toNumber((NUMBER) Integer.valueOf(0), (Class<NUMBER>) actual.getClass());
    }

    /**
     * Converts a numeric string to a number of the given type.
     *
     * @param number     numeric string
     * @param numberType number type to convert numeric string to number
     * @return converted number
     */
    @SuppressWarnings("unchecked")
    private static <N extends Number & Comparable<? extends Number>> N toNumber(N number, Class<N> numberType) {
        if (numberType == Byte.class) return (N) Byte.valueOf(number.byteValue());
        if (numberType == Short.class) return (N) Short.valueOf(number.shortValue());
        if (numberType == Integer.class) return (N) Integer.valueOf(number.intValue());
        if (numberType == Long.class) return (N) Long.valueOf(number.longValue());
        if (numberType == Float.class) return (N) Float.valueOf(number.floatValue());
        if (numberType == Double.class) return (N) Double.valueOf(number.doubleValue());

        return null;
    }

    public SELF isGreaterThan(NUMBER expected) {
        if (actual.compareTo(expected) <= 0) throw getException();
        return self;
    }

    public SELF isGreaterThanOrEqualTo(NUMBER expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
    }

    public SELF isLessThan(NUMBER expected) {
        if (actual.compareTo(expected) >= 0) throw getException();
        return self;
    }

    public SELF isLessThanOrEqualTo(NUMBER expected) {
        if (actual.compareTo(expected) > 0) throw getException();
        return self;
    }

    public SELF isPositive() {
        if (actual.compareTo(this.zero) <= 0) throw getException();
        return self;
    }

    public SELF isZeroOrPositive() {
        if (actual.compareTo(this.zero) < 0) throw getException();
        return self;
    }

    public SELF isNegative() {
        if (actual.compareTo(this.zero) >= 0) throw getException();
        return self;
    }

    public SELF isZeroOrNegative() {
        if (actual.compareTo(this.zero) > 0) throw getException();
        return self;
    }

    public SELF isBetween(NUMBER startInclusive, NUMBER endInclusive) {
        return isGreaterThanOrEqualTo(startInclusive).isLessThanOrEqualTo(endInclusive);
    }

    public SELF isStrictlyBetween(NUMBER startExclusive, NUMBER endExclusive) {
        return isGreaterThan(startExclusive).isLessThan(endExclusive);
    }

}
