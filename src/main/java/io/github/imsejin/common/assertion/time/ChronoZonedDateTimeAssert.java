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

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

@SuppressWarnings("unchecked")
public class ChronoZonedDateTimeAssert<
        SELF extends ChronoZonedDateTimeAssert<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends ObjectAssert<SELF> {

    private final ChronoZonedDateTime<DATE> actual;

    public ChronoZonedDateTimeAssert(ChronoZonedDateTime<DATE> actual) {
        super(actual);
        this.actual = actual;
    }

    @Override
    public SELF isEqualTo(Object expected) {
        if (!(expected instanceof ChronoZonedDateTime)) return super.isEqualTo(expected);
        if (!this.actual.isEqual((ChronoZonedDateTime<DATE>) expected)) throw getException();

        return (SELF) this;
    }

    @Override
    public SELF isNotEqualTo(Object expected) {
        if (!(expected instanceof ChronoZonedDateTime)) return super.isNotEqualTo(expected);
        if (this.actual.isEqual((ChronoZonedDateTime<DATE>) expected)) throw getException();

        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isBefore(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isBefore(ChronoLocalDateTime)
     * @see LocalTimeAssert#isBefore(LocalTime)
     * @see OffsetDateTimeAssert#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(ChronoZonedDateTime<DATE> expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see LocalTimeAssert#isBeforeOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(ChronoZonedDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfter(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isAfter(ChronoLocalDateTime)
     * @see LocalTimeAssert#isAfter(LocalTime)
     * @see OffsetDateTimeAssert#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(ChronoZonedDateTime<DATE> expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAssert#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(ChronoZonedDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    public SELF isSameZone(ZoneId expected) {
        if (!this.actual.getZone().equals(expected)) throw getException();
        return (SELF) this;
    }

    public SELF isNotSameZone(ZoneId expected) {
        if (this.actual.getZone().equals(expected)) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see ChronoLocalDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public ChronoLocalDateAssert<?> asLocalDate() {
        return new ChronoLocalDateAssert<>(this.actual.toLocalDate());
    }

    /**
     * @see OffsetDateTimeAssert#asLocalDateTime()
     */
    public ChronoLocalDateTimeAssert<?, DATE> asLocalDateTime() {
        return new ChronoLocalDateTimeAssert<>(this.actual.toLocalDateTime());
    }

    /**
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return new LocalTimeAssert<>(this.actual.toLocalTime());
    }

    public OffsetDateTimeAssert<?> asOffsetDateTime() {
        // this.actual.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime dateTime = LocalDateTime.ofInstant(this.actual.toInstant(), this.actual.getZone());
        ZoneOffset offset = this.actual.getOffset();

        return new OffsetDateTimeAssert<>(OffsetDateTime.of(dateTime, offset));
    }

}
