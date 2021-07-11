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

package io.github.imsejin.common.assertion.time;

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

public abstract class AbstractChronoLocalDateAssert<
        SELF extends AbstractChronoLocalDateAssert<SELF, ACTUAL>,
        ACTUAL extends ChronoLocalDate>
        extends AbstractObjectAssert<SELF, ACTUAL> {

    protected AbstractChronoLocalDateAssert(ACTUAL actual) {
        super(actual);
    }

    @Override
    public SELF isEqualTo(ACTUAL expected) {
        if (!actual.isEqual(expected)) throw getException();
        return self;
    }

    @Override
    public SELF isNotEqualTo(ACTUAL expected) {
        if (actual.isEqual(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isBefore(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBefore(LocalTime)
     * @see OffsetDateTimeAssert#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(ACTUAL expected) {
        if (!actual.isBefore(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isBeforeOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBeforeOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) > 0) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isAfter(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfter(LocalTime)
     * @see OffsetDateTimeAssert#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(ACTUAL expected) {
        if (!actual.isAfter(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isAfterOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
    }

    public SELF isLeapYear() {
        if (!actual.isLeapYear()) throw getException();
        return self;
    }

    public SELF isNotLeapYear() {
        if (actual.isLeapYear()) throw getException();
        return self;
    }

}
