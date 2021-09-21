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

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;

public abstract class AbstractTemporalAssert<
        SELF extends AbstractTemporalAssert<SELF, ACTUAL>,
        ACTUAL extends Temporal & Comparable<ACTUAL>>
        extends AbstractObjectAssert<SELF, ACTUAL> {

    protected AbstractTemporalAssert(ACTUAL actual) {
        super(actual);
    }

    /**
     * @param expected expected value
     * @return self
     * @see ChronoLocalDate#isEqual(ChronoLocalDate)
     * @see ChronoLocalDateTime#isEqual(ChronoLocalDateTime)
     */
    @Override
    public SELF isEqualTo(ACTUAL expected) {
        if (actual == null || actual.compareTo(expected) != 0) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see ChronoLocalDate#isEqual(ChronoLocalDate)
     * @see ChronoLocalDateTime#isEqual(ChronoLocalDateTime)
     */
    @Override
    public SELF isNotEqualTo(ACTUAL expected) {
        if (actual == null || actual.compareTo(expected) == 0) {
            setDefaultDescription("They are expected to be not equal, but they are. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isBefore(ACTUAL expected) {
        if (actual.compareTo(expected) >= 0) {
            setDefaultDescription("It is expected to be before than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isBeforeOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) > 0) {
            setDefaultDescription("It is expected to be before than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isAfter(ACTUAL expected) {
        if (actual.compareTo(expected) <= 0) {
            setDefaultDescription("It is expected to be after than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) < 0) {
            setDefaultDescription("It is expected to be after than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

}
