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

package io.github.imsejin.common.assertion.lang;

import java.util.AbstractMap.SimpleEntry;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.ArithmeticDecimalNumberAssertable;
import io.github.imsejin.common.assertion.composition.DecimalNumberAssertable;
import io.github.imsejin.common.util.NumberUtils;

/**
 * Assertion for {@link Double}
 *
 * @param <SELF> this class
 */
public class DoubleAssert<
        SELF extends DoubleAssert<SELF>>
        extends AbstractNumberAssert<SELF, Double>
        implements ArithmeticDecimalNumberAssertable<SELF, Double> {

    public DoubleAssert(Double actual) {
        super(actual, 0.0);
    }

    protected DoubleAssert(Descriptor<?> descriptor, Double actual) {
        super(descriptor, actual, 0.0);
    }

    @Override
    public SELF hasDecimalPart() {
        if (!NumberUtils.hasDecimalPart(actual)) {
            setDefaultDescription(DecimalNumberAssertable.DEFAULT_DESCRIPTION_HAS_DECIMAL_PART);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isFinite() {
        if (!Double.isFinite(actual)) {
            setDefaultDescription(ArithmeticDecimalNumberAssertable.DEFAULT_DESCRIPTION_IS_FINITE);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isInfinite() {
        if (!Double.isInfinite(actual)) {
            setDefaultDescription(ArithmeticDecimalNumberAssertable.DEFAULT_DESCRIPTION_IS_INFINITE);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNaN() {
        if (!Double.isNaN(actual)) {
            setDefaultDescription(ArithmeticDecimalNumberAssertable.DEFAULT_DESCRIPTION_IS_NAN);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotNaN() {
        if (Double.isNaN(actual)) {
            setDefaultDescription(ArithmeticDecimalNumberAssertable.DEFAULT_DESCRIPTION_IS_NOT_NAN);
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

}
