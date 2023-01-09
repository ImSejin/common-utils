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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.time.InstantAssert;
import io.github.imsejin.common.assertion.time.LocalTimeAssert;
import io.github.imsejin.common.assertion.time.OffsetDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

public class ChronoZonedDateTimeAssert<
        SELF extends ChronoZonedDateTimeAssert<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends AbstractTemporalAccessorAssert<SELF, ChronoZonedDateTime<?>> {

    public ChronoZonedDateTimeAssert(ChronoZonedDateTime<DATE> actual) {
        super(actual);
    }

    protected ChronoZonedDateTimeAssert(Descriptor<?> descriptor, ChronoZonedDateTime<?> actual) {
        super(descriptor, actual);
    }

    public SELF isSameZone(ZoneId expected) {
        ZoneId zone = actual.getZone();

        if (!zone.equals(expected)) {
            setDefaultDescription(
                    "They are expected to have the same zone, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, zone);
            throw getException();
        }

        return self;
    }

    public SELF isNotSameZone(ZoneId expected) {
        ZoneId zone = actual.getZone();

        if (zone.equals(expected)) {
            setDefaultDescription(
                    "They are expected not to have the same zone, but they are. (expected: '{0}', actual: '{1}')",
                    expected, zone);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public ChronoLocalDateAssert<?> asLocalDate() {
        ChronoLocalDate localDate = actual.toLocalDate();
        return new ChronoLocalDateAssert<>(this, localDate);
    }

    /**
     * @return another assertion
     * @see OffsetDateTimeAssert#asLocalDateTime()
     */
    public ChronoLocalDateTimeAssert<?, DATE> asLocalDateTime() {
        ChronoLocalDateTime<?> localDateTime = actual.toLocalDateTime();
        return new ChronoLocalDateTimeAssert<>(this, localDateTime);
    }

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        class LocalTimeAssertImpl extends LocalTimeAssert<LocalTimeAssertImpl> {
            LocalTimeAssertImpl(Descriptor<?> descriptor, LocalTime actual) {
                super(descriptor, actual);
            }
        }

        LocalTime localTime = actual.toLocalTime();
        return new LocalTimeAssertImpl(this, localTime);
    }

    public OffsetDateTimeAssert<?> asOffsetDateTime() {
        class OffsetDateTimeAssertImpl extends OffsetDateTimeAssert<OffsetDateTimeAssertImpl> {
            OffsetDateTimeAssertImpl(Descriptor<?> descriptor, OffsetDateTime actual) {
                super(descriptor, actual);
            }
        }

        LocalDateTime dateTime = LocalDateTime.ofInstant(actual.toInstant(), actual.getZone());
        ZoneOffset offset = actual.getOffset();
        OffsetDateTime offsetDateTime = OffsetDateTime.of(dateTime, offset);
        return new OffsetDateTimeAssertImpl(this, offsetDateTime);
    }

    public InstantAssert<?> asInstant() {
        class InstantAssertImpl extends InstantAssert<InstantAssertImpl> {
            InstantAssertImpl(Descriptor<?> descriptor, Instant actual) {
                super(descriptor, actual);
            }
        }

        Instant instant = actual.toInstant();
        return new InstantAssertImpl(this, instant);
    }

}
