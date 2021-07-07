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
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

@SuppressWarnings("unchecked")
public class ChronoLocalDateAssert<SELF extends ChronoLocalDateAssert<SELF>> extends ObjectAssert<SELF> {

    private final ChronoLocalDate actual;

    public ChronoLocalDateAssert(ChronoLocalDate actual) {
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
     * @see ChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isBefore(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBefore(LocalTime)
     * @see OffsetDateTimeAssert#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(ChronoLocalDate expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isBeforeOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isBeforeOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(ChronoLocalDate expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isAfter(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfter(LocalTime)
     * @see OffsetDateTimeAssert#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(ChronoLocalDate expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isAfterOrEqualTo(java.time.chrono.ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
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
