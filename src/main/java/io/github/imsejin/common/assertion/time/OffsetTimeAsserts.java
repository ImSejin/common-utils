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

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

@SuppressWarnings("unchecked")
public class OffsetTimeAsserts<SELF extends OffsetTimeAsserts<SELF>> extends ObjectAsserts<SELF> {

    private final OffsetTime actual;

    public OffsetTimeAsserts(OffsetTime actual) {
        super(actual);
        this.actual = actual;
    }

    @Override
    public SELF isEqualTo(Object expected) {
        if (!(expected instanceof OffsetTime)) return super.isEqualTo(expected);
        if (!this.actual.isEqual((OffsetTime) expected)) throw getException();

        return (SELF) this;
    }

    @Override
    public SELF isNotEqualTo(Object expected) {
        if (!(expected instanceof OffsetTime)) return super.isNotEqualTo(expected);
        if (this.actual.isEqual((OffsetTime) expected)) throw getException();

        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isBefore(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBefore(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBefore(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(OffsetTime expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isBeforeOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(OffsetTime expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfter(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfter(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfter(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(OffsetTime expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAsserts#isAfterOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAsserts#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(OffsetTime expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see OffsetDateTimeAsserts#isSameOffset(ZoneOffset)
     */
    public SELF isSameOffset(ZoneOffset expected) {
        if (!this.actual.getOffset().equals(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see OffsetDateTimeAsserts#isNotSameOffset(ZoneOffset)
     */
    public SELF isNotSameOffset(ZoneOffset expected) {
        if (this.actual.getOffset().equals(expected)) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see ChronoLocalDateTimeAsserts#asLocalTime()
     * @see ChronoZonedDateTimeAsserts#asLocalTime()
     * @see OffsetDateTimeAsserts#asLocalTime()
     */
    public LocalTimeAsserts<?> asLocalTime() {
        return new LocalTimeAsserts<>(this.actual.toLocalTime());
    }

}
