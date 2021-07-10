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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

public class OffsetDateTimeAssert<SELF extends OffsetDateTimeAssert<SELF>> extends AbstractObjectAssert<SELF, OffsetDateTime> {

    public OffsetDateTimeAssert(OffsetDateTime actual) {
        super(actual);
    }

    @Override
    public SELF isEqualTo(OffsetDateTime expected) {
        if (!actual.isEqual(expected)) throw getException();
        return self;
    }

    @Override
    public SELF isNotEqualTo(OffsetDateTime expected) {
        if (actual.isEqual(expected)) throw getException();
        return self;
    }

    /**
     * @see AbstractChronoLocalDateAssert#isBefore(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isBefore(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBefore(LocalTime)
     */
    public SELF isBefore(OffsetDateTime expected) {
        if (!actual.isBefore(expected)) throw getException();
        return self;
    }

    /**
     * @see AbstractChronoLocalDateAssert#isBeforeOrEqualTo(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isBeforeOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBeforeOrEqualTo(LocalTime)
     */
    public SELF isBeforeOrEqualTo(OffsetDateTime expected) {
        if (actual.compareTo(expected) > 0) throw getException();
        return self;
    }

    /**
     * @see AbstractChronoLocalDateAssert#isAfter(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isAfter(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfter(LocalTime)
     */
    public SELF isAfter(OffsetDateTime expected) {
        if (!actual.isAfter(expected)) throw getException();
        return self;
    }

    /**
     * @see AbstractChronoLocalDateAssert#isAfterOrEqualTo(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isAfterOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     */
    public SELF isAfterOrEqualTo(OffsetDateTime expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
    }

    /**
     * @see OffsetTimeAssert#isSameOffset(ZoneOffset)
     */
    public SELF isSameOffset(ZoneOffset expected) {
        if (!actual.getOffset().equals(expected)) throw getException();
        return self;
    }

    /**
     * @see OffsetTimeAssert#isNotSameOffset(ZoneOffset)
     */
    public SELF isNotSameOffset(ZoneOffset expected) {
        if (actual.getOffset().equals(expected)) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see AbstractChronoLocalDateTimeAssert#asLocalDate()
     * @see AbstractChronoZonedDateTimeAssert#asLocalDate()
     */
    public AbstractChronoLocalDateAssert<?, ChronoLocalDate> asLocalDate() {
        return Asserts.that(actual.toLocalDate());
    }

    /**
     * @see AbstractChronoZonedDateTimeAssert#asLocalDateTime()
     */
    public AbstractChronoLocalDateTimeAssert<?, ChronoLocalDateTime<LocalDate>, LocalDate> asLocalDateTime() {
        return Asserts.that(actual.toLocalDateTime());
    }

    public AbstractChronoZonedDateTimeAssert<?, ChronoZonedDateTime<LocalDate>, LocalDate> asZonedDateTime() {
        return Asserts.that(actual.toZonedDateTime());
    }

    /**
     * @see AbstractChronoLocalDateTimeAssert#asLocalTime()
     * @see AbstractChronoZonedDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return Asserts.that(actual.toLocalTime());
    }

    public OffsetTimeAssert<?> asOffsetTime() {
        return Asserts.that(actual.toOffsetTime());
    }

}
