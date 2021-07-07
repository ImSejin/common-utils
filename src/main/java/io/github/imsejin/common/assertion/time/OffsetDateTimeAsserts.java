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

import io.github.imsejin.common.assertion.object.ObjectAsserts;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

@SuppressWarnings("unchecked")
public class OffsetDateTimeAsserts<SELF extends OffsetDateTimeAsserts<SELF>> extends ObjectAsserts<SELF> {

    private final OffsetDateTime actual;

    public OffsetDateTimeAsserts(OffsetDateTime actual) {
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
     * @see ChronoLocalDateAsserts#isBefore(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBefore(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBefore(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isBefore(LocalTime)
     */
    public SELF isBefore(OffsetDateTime expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBeforeOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isBeforeOrEqualTo(LocalTime)
     */
    public SELF isBeforeOrEqualTo(OffsetDateTime expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfter(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfter(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfter(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isAfter(LocalTime)
     */
    public SELF isAfter(OffsetDateTime expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfterOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isAfterOrEqualTo(LocalTime)
     */
    public SELF isAfterOrEqualTo(OffsetDateTime expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see ChronoLocalDateTimeAsserts#asLocalDate()
     * @see ChronoZonedDateTimeAsserts#asLocalDate()
     */
    public ChronoLocalDateAsserts<?> asLocalDate() {
        return new ChronoLocalDateAsserts<>(this.actual.toLocalDate());
    }

    /**
     * @see ChronoZonedDateTimeAsserts#asLocalDateTime()
     */
    public ChronoLocalDateTimeAsserts<?, ? extends ChronoLocalDate> asLocalDateTime() {
        return new ChronoLocalDateTimeAsserts<>(this.actual.toLocalDateTime());
    }

    public ChronoZonedDateTimeAsserts<?, ? extends ChronoLocalDate> asZonedDateTime() {
        return new ChronoZonedDateTimeAsserts<>(this.actual.toZonedDateTime());
    }

    /**
     * @see ChronoLocalDateTimeAsserts#asLocalTime()
     * @see ChronoZonedDateTimeAsserts#asLocalTime()
     */
    public LocalTimeAsserts<?> asLocalTime() {
        return new LocalTimeAsserts<>(this.actual.toLocalTime());
    }

}
