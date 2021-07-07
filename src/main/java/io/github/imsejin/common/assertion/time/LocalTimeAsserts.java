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
import java.time.chrono.ChronoZonedDateTime;

@SuppressWarnings("unchecked")
public class LocalTimeAsserts<SELF extends LocalTimeAsserts<SELF>> extends ObjectAsserts<SELF> {

    private final LocalTime actual;

    public LocalTimeAsserts(LocalTime actual) {
        super(actual);
        this.actual = actual;
    }

    /**
     * @see ChronoLocalDateAsserts#isBefore(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBefore(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBefore(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(LocalTime expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBeforeOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(LocalTime expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfter(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfter(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfter(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(LocalTime expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfterOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(LocalTime expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    public SELF isMidnight() {
        return super.isEqualTo(LocalTime.MIDNIGHT);
    }

    public SELF isBeforeMidnight() {
        return isBefore(LocalTime.MIDNIGHT);
    }

    public SELF isBeforeOrEqualToMidnight() {
        return isBeforeOrEqualTo(LocalTime.MIDNIGHT);
    }

    public SELF isAfterMidnight() {
        return isAfter(LocalTime.MIDNIGHT);
    }

    public SELF isAfterOrEqualToMidnight() {
        return isAfterOrEqualTo(LocalTime.MIDNIGHT);
    }

    public SELF isNoon() {
        return super.isEqualTo(LocalTime.NOON);
    }

    public SELF isBeforeNoon() {
        return isBefore(LocalTime.NOON);
    }

    public SELF isBeforeOrEqualToNoon() {
        return isBeforeOrEqualTo(LocalTime.NOON);
    }

    public SELF isAfternoon() {
        return isAfter(LocalTime.NOON);
    }

    public SELF isAfterOrEqualToNoon() {
        return isAfterOrEqualTo(LocalTime.NOON);
    }

}
