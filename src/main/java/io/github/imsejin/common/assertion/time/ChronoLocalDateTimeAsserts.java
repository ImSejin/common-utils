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
public class ChronoLocalDateTimeAsserts<
        SELF extends ChronoLocalDateTimeAsserts<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends ObjectAsserts<SELF> {

    private final ChronoLocalDateTime<DATE> actual;

    public ChronoLocalDateTimeAsserts(ChronoLocalDateTime<DATE> actual) {
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
     * @see ChronoLocalDateAsserts#isBefore(ChronoLocalDate)
     * @see ChronoZonedDateTimeAsserts#isBefore(ChronoZonedDateTime)
     * @see LocalTimeAsserts#isBefore(LocalTime)
     */
    public SELF isBefore(ChronoLocalDateTime<DATE> expected) {
        if (!this.actual.isBefore(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isBeforeOrEqualTo(ChronoLocalDate)
     * @see ChronoZonedDateTimeAsserts#isBeforeOrEqualTo(ChronoZonedDateTime)
     * @see LocalTimeAsserts#isBeforeOrEqualTo(LocalTime)
     */
    public SELF isBeforeOrEqualTo(ChronoLocalDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) > 0) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfter(ChronoLocalDate)
     * @see ChronoZonedDateTimeAsserts#isAfter(ChronoZonedDateTime)
     * @see LocalTimeAsserts#isAfter(LocalTime)
     */
    public SELF isAfter(ChronoLocalDateTime<DATE> expected) {
        if (!this.actual.isAfter(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @see ChronoLocalDateAsserts#isAfterOrEqualTo(ChronoLocalDate)
     * @see ChronoZonedDateTimeAsserts#isAfterOrEqualTo(ChronoZonedDateTime)
     * @see LocalTimeAsserts#isAfterOrEqualTo(LocalTime)
     */
    public SELF isAfterOrEqualTo(ChronoLocalDateTime<DATE> expected) {
        if (this.actual.compareTo(expected) < 0) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @see ChronoZonedDateTimeAsserts#asLocalDate()
     */
    public ChronoLocalDateAsserts<?> asLocalDate() {
        return new ChronoLocalDateAsserts<>(this.actual.toLocalDate());
    }

    /**
     * @see ChronoZonedDateTimeAsserts#asLocalTime()
     */
    public LocalTimeAsserts<?> asLocalTime() {
        return new LocalTimeAsserts<>(this.actual.toLocalTime());
    }

}
