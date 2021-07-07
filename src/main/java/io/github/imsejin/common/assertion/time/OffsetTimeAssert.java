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

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

@SuppressWarnings("unchecked")
public class OffsetTimeAssert<SELF extends OffsetTimeAssert<SELF>> extends ObjectAssert<SELF> {

    private final OffsetTime actual;

    public OffsetTimeAssert(OffsetTime actual) {
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
     * @see ChronoLocalDateAssert#isBefore(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isBefore(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(OffsetTime expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isBeforeOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(OffsetTime expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfter(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isAfter(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(OffsetTime expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see ChronoZonedDateTimeAssert#isAfterOrEqualTo(ChronoZonedDateTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(OffsetTime expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see OffsetDateTimeAssert#isSameOffset(ZoneOffset)
     */
    public SELF isSameOffset(ZoneOffset expected) {
        if (!this.actual.getOffset().equals(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see OffsetDateTimeAssert#isNotSameOffset(ZoneOffset)
     */
    public SELF isNotSameOffset(ZoneOffset expected) {
        if (this.actual.getOffset().equals(expected)) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see ChronoZonedDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return new LocalTimeAssert<>(this.actual.toLocalTime());
    }

}
