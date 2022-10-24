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
 * Composition of assertion for amount.
 *
 * @param <SELF>   this class
 * @param <ACTUAL> something that has amount
 * @see Comparable
 */
public interface AmountAssertable<
        SELF extends AmountAssertable<SELF, ACTUAL>,
        ACTUAL> {

    String DEFAULT_DESCRIPTION_IS_POSITIVE = "It is expected to be positive, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_ZERO_OR_POSITIVE = "It is expected to be zero or positive, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_NEGATIVE = "It is expected to be negative, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_ZERO_OR_NEGATIVE = "It is expected to be zero or negative, but it isn't.";

    SELF isPositive();

    SELF isZeroOrPositive();

    SELF isNegative();

    SELF isZeroOrNegative();

}
