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

import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.function.BiPredicate;

/**
 * Composition of assertion for comparison.
 *
 * @param <SELF>   this class
 * @param <ACTUAL> type that can compare itself with other
 * @see Comparable
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface ComparisonAssertable<
        SELF extends ObjectAssert<SELF, ACTUAL> & ComparisonAssertable<SELF, ACTUAL>,
        ACTUAL> {

    /**
     * @see java.util.Date#compareTo(java.util.Date)
     * @see java.math.BigDecimal#compareTo(java.math.BigDecimal)
     */
    BiPredicate<Comparable, Comparable<?>> IS_EQUAL_TO = (actual, expected) -> actual.equals(expected) || actual.compareTo(expected) == 0;

    /**
     * @see java.util.Date#compareTo(java.util.Date)
     * @see java.math.BigDecimal#compareTo(java.math.BigDecimal)
     */
    BiPredicate<Comparable, Comparable<?>> IS_NOT_EQUAL_TO = (actual, expected) -> !actual.equals(expected) && actual.compareTo(expected) != 0;

    String DEFAULT_DESCRIPTION_IS_EQUAL_TO = "They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO = "They are expected to be not equal, but they are. (expected: '{0}', actual: '{1}')";

    SELF isEqualTo(ACTUAL expected);

    SELF isNotEqualTo(ACTUAL expected);

    SELF isBetween(ACTUAL startInclusive, ACTUAL endInclusive);

    SELF isStrictlyBetween(ACTUAL startExclusive, ACTUAL endExclusive);

}
