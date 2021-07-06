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

import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
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

    public SELF isBefore(ChronoZonedDateTime<DATE> expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    public SELF isBeforeOrEqualTo(ChronoZonedDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    public SELF isAfter(ChronoZonedDateTime<DATE> expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

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

    /**
     * @see ChronoLocalDateTimeAsserts#asLocalDate()
     */
    public ChronoLocalDateAsserts<?> asLocalDate() {
        return new ChronoLocalDateAsserts<>(this.actual.toLocalDate());
    }

    public ChronoLocalDateTimeAsserts<?, DATE> asLocalDateTime() {
        return new ChronoLocalDateTimeAsserts<>(this.actual.toLocalDateTime());
    }

    /**
     * @see ChronoLocalDateTimeAsserts#asLocalTime()
     */
    public LocalTimeAsserts<?> asLocalTime() {
        return new LocalTimeAsserts<>(this.actual.toLocalTime());
    }

}
