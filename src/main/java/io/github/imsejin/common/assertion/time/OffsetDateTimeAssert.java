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

import io.github.imsejin.common.assertion.object.ObjectAssert;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

@SuppressWarnings("unchecked")
public class OffsetDateTimeAssert<SELF extends OffsetDateTimeAssert<SELF>> extends ObjectAssert<SELF> {

    private final OffsetDateTime actual;

    public OffsetDateTimeAssert(OffsetDateTime actual) {
        super(actual);
        this.actual = actual;
    }

    @Override
    public SELF isEqualTo(Object expected) {
        if (!(expected instanceof OffsetDateTime)) return super.isEqualTo(expected);
        if (!this.actual.isEqual((OffsetDateTime) expected)) throw getException();

        return (SELF) this;
    }

    @Override
    public SELF isNotEqualTo(Object expected) {
        if (!(expected instanceof OffsetDateTime)) return super.isNotEqualTo(expected);
        if (this.actual.isEqual((OffsetDateTime) expected)) throw getException();

        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isBefore(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isBefore(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBefore(LocalTime)
     */
    public SELF isBefore(OffsetDateTime expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isBeforeOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBeforeOrEqualTo(LocalTime)
     */
    public SELF isBeforeOrEqualTo(OffsetDateTime expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfter(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isAfter(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfter(LocalTime)
     */
    public SELF isAfter(OffsetDateTime expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isAfterOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     */
    public SELF isAfterOrEqualTo(OffsetDateTime expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see OffsetTimeAssert#isSameOffset(ZoneOffset)
     */
    public SELF isSameOffset(ZoneOffset expected) {
        if (!this.actual.getOffset().equals(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see OffsetTimeAssert#isNotSameOffset(ZoneOffset)
     */
    public SELF isNotSameOffset(ZoneOffset expected) {
        if (this.actual.getOffset().equals(expected)) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see ChronoLocalDateTimeAssert#asLocalDate()
     * @see ChronoZonedDateTimeAssert#asLocalDate()
     */
    public ChronoLocalDateAssert<?> asLocalDate() {
        return new ChronoLocalDateAssert<>(this.actual.toLocalDate());
    }

    /**
     * @see ChronoZonedDateTimeAssert#asLocalDateTime()
     */
    public ChronoLocalDateTimeAssert<?, ? extends ChronoLocalDate> asLocalDateTime() {
        return new ChronoLocalDateTimeAssert<>(this.actual.toLocalDateTime());
    }

    public ChronoZonedDateTimeAssert<?, ? extends ChronoLocalDate> asZonedDateTime() {
        return new ChronoZonedDateTimeAssert<>(this.actual.toZonedDateTime());
    }

    /**
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see ChronoZonedDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return new LocalTimeAssert<>(this.actual.toLocalTime());
    }

    public OffsetTimeAssert<?> asOffsetTime() {
        return new OffsetTimeAssert<>(this.actual.toOffsetTime());
    }

}
