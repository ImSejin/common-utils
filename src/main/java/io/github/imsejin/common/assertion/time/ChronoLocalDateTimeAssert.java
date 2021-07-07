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
import java.time.chrono.ChronoZonedDateTime;

@SuppressWarnings("unchecked")
public class ChronoLocalDateTimeAssert<
        SELF extends ChronoLocalDateTimeAssert<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends ObjectAssert<SELF> {

    private final ChronoLocalDateTime<DATE> actual;

    public ChronoLocalDateTimeAssert(ChronoLocalDateTime<DATE> actual) {
        super(actual);
        this.actual = actual;
    }

    @Override
    public SELF isEqualTo(Object expected) {
        if (!(expected instanceof ChronoLocalDateTime)) return super.isEqualTo(expected);
        if (!this.actual.isEqual((ChronoLocalDateTime<DATE>) expected)) throw getException();

        return (SELF) this;
    }

    @Override
    public SELF isNotEqualTo(Object expected) {
        if (!(expected instanceof ChronoLocalDateTime)) return super.isNotEqualTo(expected);
        if (this.actual.isEqual((ChronoLocalDateTime<DATE>) expected)) throw getException();

        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isBefore(ChronoLocalDate)
     * @see ChronoZonedDateTimeAssert#isBefore(ChronoZonedDateTime)
     * @see LocalTimeAssert#isBefore(LocalTime)
     * @see OffsetDateTimeAssert#isBefore(java.time.OffsetDateTime)
     */
    public SELF isBefore(ChronoLocalDateTime<DATE> expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoZonedDateTimeAssert#isBeforeOrEqualTo(ChronoZonedDateTime)
     * @see LocalTimeAssert#isBeforeOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isBeforeOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isBeforeOrEqualTo(ChronoLocalDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfter(ChronoLocalDate)
     * @see ChronoZonedDateTimeAssert#isAfter(ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfter(LocalTime)
     * @see OffsetDateTimeAssert#isAfter(java.time.OffsetDateTime)
     */
    public SELF isAfter(ChronoLocalDateTime<DATE> expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAssert#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoZonedDateTimeAssert#isAfterOrEqualTo(ChronoZonedDateTime)
     * @see LocalTimeAssert#isAfterOrEqualTo(LocalTime)
     * @see OffsetDateTimeAssert#isAfterOrEqualTo(java.time.OffsetDateTime)
     */
    public SELF isAfterOrEqualTo(ChronoLocalDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see ChronoZonedDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public ChronoLocalDateAssert<?> asLocalDate() {
        return new ChronoLocalDateAssert<>(this.actual.toLocalDate());
    }

    /**
     * @see ChronoZonedDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return new LocalTimeAssert<>(this.actual.toLocalTime());
    }

}
