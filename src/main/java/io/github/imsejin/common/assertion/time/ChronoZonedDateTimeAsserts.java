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

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

@SuppressWarnings("unchecked")
public class ChronoZonedDateTimeAsserts<
        SELF extends ChronoZonedDateTimeAsserts<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends ObjectAsserts<SELF> {

    private final ChronoZonedDateTime<DATE> actual;

    public ChronoZonedDateTimeAsserts(ChronoZonedDateTime<DATE> actual) {
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
     * @see ChronoLocalDateAsserts#isBefore(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBefore(ChronoLocalDateTime)
     * @see LocalTimeAsserts#isBefore(LocalTime)
     * @see OffsetDateTimeAsserts#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(ChronoZonedDateTime<DATE> expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isBeforeOrEqualTo(ChronoLocalDateTime)
     * @see LocalTimeAsserts#isBeforeOrEqualTo(LocalTime)
     * @see OffsetDateTimeAsserts#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(ChronoZonedDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfter(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfter(ChronoLocalDateTime)
     * @see LocalTimeAsserts#isAfter(LocalTime)
     * @see OffsetDateTimeAsserts#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(ChronoZonedDateTime<DATE> expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoLocalDateTimeAsserts#isAfterOrEqualTo(ChronoLocalDateTime)
     * @see LocalTimeAsserts#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAsserts#isAfterOrEqualTo(java.time.OffsetDateTime)
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
     * @see ChronoLocalDateTimeAsserts#asLocalDate()
     * @see OffsetDateTimeAsserts#asLocalDate()
     */
    public ChronoLocalDateAsserts<?> asLocalDate() {
        return new ChronoLocalDateAsserts<>(this.actual.toLocalDate());
    }

    /**
     * @see OffsetDateTimeAsserts#asLocalDateTime()
     */
    public ChronoLocalDateTimeAsserts<?, DATE> asLocalDateTime() {
        return new ChronoLocalDateTimeAsserts<>(this.actual.toLocalDateTime());
    }

    /**
     * @see ChronoLocalDateTimeAsserts#asLocalTime()
     * @see OffsetDateTimeAsserts#asLocalTime()
     */
    public LocalTimeAsserts<?> asLocalTime() {
        return new LocalTimeAsserts<>(this.actual.toLocalTime());
    }

    public OffsetDateTimeAsserts<?> asOffsetDateTime() {
        // this.actual.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime dateTime = LocalDateTime.ofInstant(this.actual.toInstant(), this.actual.getZone());
        ZoneOffset offset = this.actual.getOffset();

        return new OffsetDateTimeAsserts<>(OffsetDateTime.of(dateTime, offset));
    }

}