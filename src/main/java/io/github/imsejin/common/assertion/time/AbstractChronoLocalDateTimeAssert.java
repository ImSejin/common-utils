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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

public abstract class AbstractChronoLocalDateTimeAssert<
        SELF extends AbstractChronoLocalDateTimeAssert<SELF, ACTUAL, DATE>,
        ACTUAL extends ChronoLocalDateTime<DATE>,
        DATE extends ChronoLocalDate>
        extends AbstractObjectAssert<SELF, ACTUAL> {

    protected AbstractChronoLocalDateTimeAssert(ACTUAL actual) {
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
     * @see AbstractChronoLocalDateAssert#isBefore(ChronoLocalDate)
     * @see AbstractChronoZonedDateTimeAssert#isBefore(ChronoZonedDateTime)
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
     * @see AbstractChronoLocalDateAssert#isBeforeOrEqualTo(ChronoLocalDate)
     * @see AbstractChronoZonedDateTimeAssert#isBeforeOrEqualTo(ChronoZonedDateTime)
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
     * @see AbstractChronoLocalDateAssert#isAfter(ChronoLocalDate)
     * @see AbstractChronoZonedDateTimeAssert#isAfter(ChronoZonedDateTime)
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
     * @see AbstractChronoLocalDateAssert#isAfterOrEqualTo(ChronoLocalDate)
     * @see AbstractChronoZonedDateTimeAssert#isAfterOrEqualTo(ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return another assertion
     * @see AbstractChronoZonedDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public AbstractChronoLocalDateAssert<?, ChronoLocalDate> asLocalDate() {
        return Asserts.that(actual.toLocalDate());
    }

    /**
     * @return another assertion
     * @see AbstractChronoZonedDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return Asserts.that(actual.toLocalTime());
    }

}
