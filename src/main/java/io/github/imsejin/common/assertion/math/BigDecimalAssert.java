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

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.DecimalNumberAssertable;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.util.NumberUtils;

import java.math.BigDecimal;

public class BigDecimalAssert<
        SELF extends BigDecimalAssert<SELF>>
        extends NumberAssert<SELF, BigDecimal>
        implements DecimalNumberAssertable<SELF, BigDecimal> {

    public BigDecimalAssert(BigDecimal actual) {
        super(actual);
    }

    protected BigDecimalAssert(Descriptor<?> descriptor, BigDecimal actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF hasDecimalPart() {
        if (!NumberUtils.hasDecimalPart(actual)) {
            setDefaultDescription(DecimalNumberAssertable.DEFAULT_DESCRIPTION_HAS_DECIMAL_PART, actual);
            throw getException();
        }

        return self;
    }

}
