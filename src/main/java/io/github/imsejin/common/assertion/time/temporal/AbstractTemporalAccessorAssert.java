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

package io.github.imsejin.common.assertion.time.temporal;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.PositionComparisonAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.AbstractMap.SimpleEntry;

/**
 * Abstract assertion for {@link TemporalAccessor}
 *
 * <p> Unlike {@link io.github.imsejin.common.assertion.Asserts#that(Number)},
 * implementations of {@link TemporalAccessor} are limited to comparison by
 * {@link java.time.temporal.TemporalAccessor#isSupported(TemporalField)}.
 *
 * @param <SELF>   assertion class that extends {@link AbstractTemporalAccessorAssert}
 * @param <ACTUAL> type that implements interfaces {@link TemporalAccessor} and {@link Comparable}
 */
public abstract class AbstractTemporalAccessorAssert<
        SELF extends AbstractTemporalAccessorAssert<SELF, ACTUAL>,
        ACTUAL extends TemporalAccessor & Comparable<ACTUAL>>
        extends ObjectAssert<SELF, ACTUAL>
        implements PositionComparisonAssertable<SELF, ACTUAL> {

    protected AbstractTemporalAccessorAssert(ACTUAL actual) {
        super(actual);
    }

    protected AbstractTemporalAccessorAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    /**
     * @param expected expected value
     * @return this class
     * @see ChronoLocalDate#isEqual(ChronoLocalDate)
     * @see ChronoLocalDateTime#isEqual(ChronoLocalDateTime)
     */
    @Override
    public SELF isEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return this class
     * @see ChronoLocalDate#isEqual(ChronoLocalDate)
     * @see ChronoLocalDateTime#isEqual(ChronoLocalDateTime)
     */
    @Override
    public SELF isNotEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_NOT_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isBefore(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_BEFORE.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_BEFORE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isBeforeOrEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_BEFORE_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_BEFORE_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isAfter(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_AFTER_THAN.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_AFTER_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (!PositionComparisonAssertable.IS_AFTER_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(PositionComparisonAssertable.DEFAULT_DESCRIPTION_IS_AFTER_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

}
