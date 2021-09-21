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

package io.github.imsejin.common.assertion.time.chrono;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.time.AbstractTemporalAssert;
import io.github.imsejin.common.assertion.time.LocalTimeAssert;
import io.github.imsejin.common.assertion.time.OffsetDateTimeAssert;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;

public abstract class AbstractChronoZonedDateTimeAssert<
        SELF extends AbstractChronoZonedDateTimeAssert<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends AbstractTemporalAssert<SELF, ChronoZonedDateTime<?>> {

    protected AbstractChronoZonedDateTimeAssert(ChronoZonedDateTime<DATE> actual) {
        super(actual);
    }

    public SELF isSameZone(ZoneId expected) {
        if (!actual.getZone().equals(expected)) throw getException();
        return self;
    }

    public SELF isNotSameZone(ZoneId expected) {
        if (actual.getZone().equals(expected)) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return another assertion
     * @see AbstractChronoLocalDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public AbstractChronoLocalDateAssert<?> asLocalDate() {
        return Asserts.that(actual.toLocalDate());
    }

    /**
     * @return another assertion
     * @see OffsetDateTimeAssert#asLocalDateTime()
     */
    @SuppressWarnings("unchecked")
    public AbstractChronoLocalDateTimeAssert<?, DATE> asLocalDateTime() {
        return (AbstractChronoLocalDateTimeAssert<?, DATE>) Asserts.that(actual.toLocalDateTime());
    }

    /**
     * @return another assertion
     * @see AbstractChronoLocalDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return Asserts.that(actual.toLocalTime());
    }

    public OffsetDateTimeAssert<?> asOffsetDateTime() {
        // actual.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime dateTime = LocalDateTime.ofInstant(actual.toInstant(), actual.getZone());
        ZoneOffset offset = actual.getOffset();

        return Asserts.that(OffsetDateTime.of(dateTime, offset));
    }

}
