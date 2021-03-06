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

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

public abstract class AbstractChronoZonedDateTimeAssert<
        SELF extends AbstractChronoZonedDateTimeAssert<SELF, ACTUAL, DATE>,
        ACTUAL extends ChronoZonedDateTime<DATE>,
        DATE extends ChronoLocalDate>
        extends AbstractObjectAssert<SELF, ACTUAL> {

    protected AbstractChronoZonedDateTimeAssert(ACTUAL actual) {
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
     * @see AbstractChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
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
     * @see AbstractChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
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
     * @see AbstractChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
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
     * @see AbstractChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
    }

    public SELF isSameZone(ZoneId expected) {
        if (!actual.getZone().equals(expected)) throw getException();
        return self;
    }

    public SELF isNotSameZone(ZoneId expected) {
        if (actual.getZone().equals(expected)) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return another assertion
     * @see AbstractChronoLocalDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public AbstractChronoLocalDateAssert<?, ChronoLocalDate> asLocalDate() {
        return Asserts.that(actual.toLocalDate());
    }

    /**
     * @return another assertion
     * @see OffsetDateTimeAssert#asLocalDateTime()
     */
    public AbstractChronoLocalDateTimeAssert<?, ChronoLocalDateTime<DATE>, DATE> asLocalDateTime() {
        return Asserts.that(actual.toLocalDateTime());
    }

    /**
     * @return another assertion
     * @see AbstractChronoLocalDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return Asserts.that(actual.toLocalTime());
    }

    public OffsetDateTimeAssert<?> asOffsetDateTime() {
        // actual.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime dateTime = LocalDateTime.ofInstant(actual.toInstant(), actual.getZone());
        ZoneOffset offset = actual.getOffset();

        return Asserts.that(OffsetDateTime.of(dateTime, offset));
    }

}
