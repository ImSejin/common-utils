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
 * Composition of assertion for comparison of size.
 *
 * @param <SELF>   this class
 * @param <ACTUAL> type that can compare itself with other
 * @see Comparable
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface AmountComparisonAssertable<
        SELF extends AmountComparisonAssertable<SELF, ACTUAL>,
        ACTUAL> {

    BiPredicate<Comparable, Comparable<?>> IS_GREATER_THAN = (actual, expected) -> actual.compareTo(expected) > 0;
    BiPredicate<Comparable, Comparable<?>> IS_GREATER_THAN_OR_EQUAL_TO = (actual, expected) -> actual.compareTo(expected) >= 0;
    BiPredicate<Comparable, Comparable<?>> IS_LESS_THAN = (actual, expected) -> actual.compareTo(expected) < 0;
    BiPredicate<Comparable, Comparable<?>> IS_LESS_THAN_OR_EQUAL_TO = (actual, expected) -> actual.compareTo(expected) <= 0;

    String DEFAULT_DESCRIPTION_IS_EQUAL_TO = "They are expected to be equal, but they aren't.";
    String DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO = "They are expected to be not equal, but they are.";
    String DEFAULT_DESCRIPTION_IS_GREATER_THAN = "It is expected to be greater than the other, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO = "It is expected to be greater than or equal to the other, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_LESS_THAN = "It is expected to be less than the other, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO = "It is expected to be less than or equal to the other, but it isn't.";

    SELF isGreaterThan(ACTUAL expected);

    SELF isGreaterThanOrEqualTo(ACTUAL expected);

    SELF isLessThan(ACTUAL expected);

    SELF isLessThanOrEqualTo(ACTUAL expected);

    default SELF isBetween(ACTUAL startInclusive, ACTUAL endInclusive) {
        return isGreaterThanOrEqualTo(startInclusive).isLessThanOrEqualTo(endInclusive);
    }

    default SELF isStrictlyBetween(ACTUAL startExclusive, ACTUAL endExclusive) {
        return isGreaterThan(startExclusive).isLessThan(endExclusive);
    }

}
