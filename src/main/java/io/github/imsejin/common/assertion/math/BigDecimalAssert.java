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

package io.github.imsejin.common.assertion.math;

import io.github.imsejin.common.assertion.DecimalNumberAssertion;
import io.github.imsejin.common.assertion.primitive.NumberAssert;
import io.github.imsejin.common.util.NumberUtils;

import java.math.BigDecimal;

public class BigDecimalAssert<SELF extends BigDecimalAssert<SELF>> extends NumberAssert<SELF, BigDecimal>
        implements DecimalNumberAssertion<SELF, BigDecimal> {

    public BigDecimalAssert(BigDecimal actual) {
        super(actual);
    }

    /**
     * To compare equality of {@link BigDecimal} with only its value,
     * we use {@link BigDecimal#compareTo(BigDecimal)}.
     * <p>
     * ({@code BigDecimal.scale} is ignored)
     *
     * @see BigDecimal#equals(Object)
     */
    @Override
    public SELF isEqualTo(BigDecimal expected) {
        if (actual.compareTo(expected) != 0) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * To compare equality of {@link BigDecimal} with only its value,
     * we use {@link BigDecimal#compareTo(BigDecimal)}.
     * <p>
     * ({@code BigDecimal.scale} is ignored)
     *
     * @see BigDecimal#equals(Object)
     */
    @Override
    public SELF isNotEqualTo(BigDecimal expected) {
        if (actual.compareTo(expected) == 0) {
            setDefaultDescription("They are expected to be not equal, but they are. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasDecimalPart() {
        if (!NumberUtils.hasDecimalPart(actual)) {
            setDefaultDescription(DecimalNumberAssertion.DEFAULT_DESCRIPTION_HAS_DECIMAL_PART, actual);
            throw getException();
        }

        return self;
    }

}
