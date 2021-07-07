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
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

@SuppressWarnings("unchecked")
public class ChronoLocalDateAsserts<SELF extends ChronoLocalDateAsserts<SELF>> extends ObjectAsserts<SELF> {

    private final ChronoLocalDate actual;

    public ChronoLocalDateAsserts(ChronoLocalDate actual) {
        super(actual);
        this.actual = actual;
    }

    @Override
    public SELF isEqualTo(Object expected) {
        if (!(expected instanceof ChronoLocalDate)) return super.isEqualTo(expected);
        if (!this.actual.isEqual((ChronoLocalDate) expected)) throw getException();

        return (SELF) this;
    }

    @Override
    public SELF isNotEqualTo(Object expected) {
        if (!(expected instanceof ChronoLocalDate)) return super.isNotEqualTo(expected);
        if (this.actual.isEqual((ChronoLocalDate) expected)) throw getException();

        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateTimeAsserts#isBefore(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBefore(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isBefore(LocalTime)
     * @see OffsetDateTimeAsserts#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(ChronoLocalDate expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateTimeAsserts#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBeforeOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isBeforeOrEqualTo(LocalTime)
     * @see OffsetDateTimeAsserts#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(ChronoLocalDate expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateTimeAsserts#isAfter(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfter(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isAfter(LocalTime)
     * @see OffsetDateTimeAsserts#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(ChronoLocalDate expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateTimeAsserts#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfterOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAsserts#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAsserts#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(ChronoLocalDate expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    public SELF isLeapYear() {
        if (!this.actual.isLeapYear()) throw getException();
        return (SELF) this;
    }

    public SELF isNotLeapYear() {
        if (this.actual.isLeapYear()) throw getException();
        return (SELF) this;
    }

}
