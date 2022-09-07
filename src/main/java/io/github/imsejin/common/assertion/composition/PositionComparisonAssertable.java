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
 * Composition of assertion for comparison of position.
 *
 * @param <SELF>   this class
 * @param <ACTUAL> type that can compare itself with other
 * @see Comparable
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface PositionComparisonAssertable<
        SELF extends ObjectAssert<SELF, ACTUAL> & PositionComparisonAssertable<SELF, ACTUAL>,
        ACTUAL>
        extends ComparisonAssertable<SELF, ACTUAL> {

    BiPredicate<Comparable, Comparable<?>> IS_BEFORE = (actual, expected) -> actual.compareTo(expected) < 0;
    BiPredicate<Comparable, Comparable<?>> IS_BEFORE_OR_EQUAL_TO = (actual, expected) -> actual.compareTo(expected) <= 0;
    BiPredicate<Comparable, Comparable<?>> IS_AFTER_THAN = (actual, expected) -> actual.compareTo(expected) > 0;
    BiPredicate<Comparable, Comparable<?>> IS_AFTER_THAN_OR_EQUAL_TO = (actual, expected) -> actual.compareTo(expected) >= 0;

    String DEFAULT_DESCRIPTION_IS_BEFORE = "It is expected to be before than the other, but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_IS_BEFORE_OR_EQUAL_TO = "It is expected to be before than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_IS_AFTER_THAN = "It is expected to be after than the other, but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_IS_AFTER_THAN_OR_EQUAL_TO = "It is expected to be after than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')";

    SELF isBefore(ACTUAL expected);

    SELF isBeforeOrEqualTo(ACTUAL expected);

    SELF isAfter(ACTUAL expected);

    SELF isAfterOrEqualTo(ACTUAL expected);

    @Override
    default SELF isBetween(ACTUAL startInclusive, ACTUAL endInclusive) {
        return isAfterOrEqualTo(startInclusive).isBeforeOrEqualTo(endInclusive);
    }

    @Override
    default SELF isStrictlyBetween(ACTUAL startExclusive, ACTUAL endExclusive) {
        return isAfter(startExclusive).isBefore(endExclusive);
    }

}
