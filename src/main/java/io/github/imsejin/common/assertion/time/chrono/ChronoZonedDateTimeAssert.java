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
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.time.InstantAssert;
import io.github.imsejin.common.assertion.time.LocalTimeAssert;
import io.github.imsejin.common.assertion.time.OffsetDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;

public class ChronoZonedDateTimeAssert<
        SELF extends ChronoZonedDateTimeAssert<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends AbstractTemporalAccessorAssert<SELF, ChronoZonedDateTime<?>> {

    public ChronoZonedDateTimeAssert(ChronoZonedDateTime<DATE> actual) {
        super(actual);
    }

    public SELF isSameZone(ZoneId expected) {
        ZoneId zone = actual.getZone();

        if (!zone.equals(expected)) {
            setDefaultDescription("They are expected to have the same zone, but they aren't. (expected: '{0}', actual: '{1}')", expected, zone);
            throw getException();
        }

        return self;
    }

    public SELF isNotSameZone(ZoneId expected) {
        ZoneId zone = actual.getZone();

        if (zone.equals(expected)) {
            setDefaultDescription("They are expected not to have the same zone, but they are. (expected: '{0}', actual: '{1}')", expected, zone);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public ChronoLocalDateAssert<?> asLocalDate() {
        ChronoLocalDateAssert<?> assertion = Asserts.that(actual.toLocalDate());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    /**
     * @return another assertion
     * @see OffsetDateTimeAssert#asLocalDateTime()
     */
    @SuppressWarnings("unchecked")
    public ChronoLocalDateTimeAssert<?, DATE> asLocalDateTime() {
        ChronoLocalDateTimeAssert<?, DATE> assertion = (ChronoLocalDateTimeAssert<?, DATE>) Asserts.that(actual.toLocalDateTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        LocalTimeAssert<?> assertion = Asserts.that(actual.toLocalTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public OffsetDateTimeAssert<?> asOffsetDateTime() {
        LocalDateTime dateTime = LocalDateTime.ofInstant(actual.toInstant(), actual.getZone());
        ZoneOffset offset = actual.getOffset();

        OffsetDateTimeAssert<?> assertion = Asserts.that(OffsetDateTime.of(dateTime, offset));
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public InstantAssert<?> asInstant() {
        InstantAssert<?> assertion = Asserts.that(actual.toInstant());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
