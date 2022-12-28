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

/**
 * Composition of assertion for arithmetic decimal number.
 *
 * @see Float
 * @see Double
 */
public interface ArithmeticDecimalNumberAssertable<
        SELF extends ArithmeticDecimalNumberAssertable<SELF, NUMBER>,
        NUMBER extends Number>
        extends DecimalNumberAssertable<SELF, NUMBER> {

    String DEFAULT_DESCRIPTION_IS_FINITE = "It is expected to be finite, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_INFINITE = "It is expected to be infinite, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_NAN = "It is expected to be NaN (Not-a-Number), but it isn't.";
    String DEFAULT_DESCRIPTION_IS_NOT_NAN = "It is expected not to be NaN (Not-a-Number), but it is.";

    /**
     * Asserts that actual value is finite.
     *
     * @return this class
     */
    SELF isFinite();

    /**
     * Asserts that actual value is infinite.
     *
     * @return this class
     */
    SELF isInfinite();

    /**
     * Asserts that actual value is NaN (Not-a-Number).
     *
     * @return this class
     */
    SELF isNaN();

    /**
     * Asserts that actual value is not NaN (Not-a-Number).
     *
     * @return this class
     */
    SELF isNotNaN();

}
