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
import java.time.chrono.ChronoZonedDateTime;

public class LocalTimeAssert<SELF extends LocalTimeAssert<SELF>> extends AbstractObjectAssert<SELF, LocalTime> {

    public LocalTimeAssert(LocalTime actual) {
        super(actual);
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateAssert#isBefore(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isBefore(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(LocalTime expected) {
        if (!actual.isBefore(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateAssert#isBeforeOrEqualTo(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isBeforeOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(LocalTime expected) {
        if (actual.compareTo(expected) > 0) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateAssert#isAfter(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isAfter(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(LocalTime expected) {
        if (!actual.isAfter(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     * @see AbstractChronoLocalDateAssert#isAfterOrEqualTo(ChronoLocalDate)
     * @see AbstractChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see AbstractChronoZonedDateTimeAssert#isAfterOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(LocalTime expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
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
