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

package io.github.imsejin.common.assertion.composition;

import io.github.imsejin.common.assertion.lang.NumberAssert;

import java.math.BigDecimal;

/**
 * Composition of assertion for decimal number.
 *
 * @see Float
 * @see Double
 * @see BigDecimal
 */
public interface DecimalNumberAssertable<
        SELF extends NumberAssert<SELF, NUMBER> & DecimalNumberAssertable<SELF, NUMBER>,
        NUMBER extends Number & Comparable<NUMBER>> {

    String DEFAULT_DESCRIPTION_HAS_DECIMAL_PART = "It is expected to have decimal part, but it isn't. (actual: '{0}')";

    /**
     * Verifies that actual value has decimal part.
     *
     * @return assertion instance
     */
    SELF hasDecimalPart();

}
