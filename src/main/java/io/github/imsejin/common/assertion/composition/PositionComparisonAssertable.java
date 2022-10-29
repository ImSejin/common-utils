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

import java.util.function.BiPredicate;

/**
 * Composition of assertion for comparison of position.
 *
 * @param <SELF>   this class
 * @param <ACTUAL> type that can compare itself with other
 * @see Comparable
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface PositionComparisonAssertable<
        SELF extends PositionComparisonAssertable<SELF, ACTUAL>,
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

    BiPredicate<Comparable, Comparable<?>> IS_BEFORE = (actual, expected) -> actual.compareTo(expected) < 0;
    BiPredicate<Comparable, Comparable<?>> IS_BEFORE_OR_EQUAL_TO = (actual, expected) -> actual.compareTo(expected) <= 0;
    BiPredicate<Comparable, Comparable<?>> IS_AFTER_THAN = (actual, expected) -> actual.compareTo(expected) > 0;
    BiPredicate<Comparable, Comparable<?>> IS_AFTER_THAN_OR_EQUAL_TO = (actual, expected) -> actual.compareTo(expected) >= 0;

    String DEFAULT_DESCRIPTION_IS_EQUAL_TO = "They are expected to be equal, but they aren't.";
    String DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO = "They are expected to be not equal, but they are.";
    String DEFAULT_DESCRIPTION_IS_BEFORE = "It is expected to be before than the other, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_BEFORE_OR_EQUAL_TO = "It is expected to be before than or equal to the other, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_AFTER_THAN = "It is expected to be after than the other, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_AFTER_THAN_OR_EQUAL_TO = "It is expected to be after than or equal to the other, but it isn't.";

    SELF isBefore(ACTUAL expected);

    SELF isBeforeOrEqualTo(ACTUAL expected);

    SELF isAfter(ACTUAL expected);

    SELF isAfterOrEqualTo(ACTUAL expected);

    default SELF isBetween(ACTUAL startInclusive, ACTUAL endInclusive) {
        return isAfterOrEqualTo(startInclusive).isBeforeOrEqualTo(endInclusive);
    }

    default SELF isStrictlyBetween(ACTUAL startExclusive, ACTUAL endExclusive) {
        return isAfter(startExclusive).isBefore(endExclusive);
    }

}
