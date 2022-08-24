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

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.OffsetAssertable;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoZonedDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeAssert<SELF extends OffsetDateTimeAssert<SELF>>
        extends AbstractTemporalAccessorAssert<SELF, OffsetDateTime>
        implements OffsetAssertable<SELF, OffsetDateTime> {

    public OffsetDateTimeAssert(OffsetDateTime actual) {
        super(actual);
    }

    protected OffsetDateTimeAssert(Descriptor<?> descriptor, OffsetDateTime actual) {
        super(descriptor, actual);
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isSameOffset(ZoneOffset expected) {
        ZoneOffset offset = actual.getOffset();

        if (!offset.equals(expected)) {
            setDefaultDescription("They are expected to have the same offset, but they aren't. (expected: '{0}', actual: '{1}')", expected, offset);
            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isNotSameOffset(ZoneOffset expected) {
        ZoneOffset offset = actual.getOffset();

        if (offset.equals(expected)) {
            setDefaultDescription("They are expected not to have the same offset, but they are. (expected: '{0}', actual: '{1}')", expected, offset);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalDate()
     * @see ChronoZonedDateTimeAssert#asLocalDate()
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ChronoLocalDateAssert<?> asLocalDate() {
        LocalDate localDate = actual.toLocalDate();
        return new ChronoLocalDateAssert(this, localDate) {
        };
    }

    /**
     * @return another assertion
     * @see ChronoZonedDateTimeAssert#asLocalDateTime()
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ChronoLocalDateTimeAssert<?, LocalDate> asLocalDateTime() {
        LocalDateTime localDateTime = actual.toLocalDateTime();
        return new ChronoLocalDateTimeAssert(this, localDateTime) {
        };
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ChronoZonedDateTimeAssert<?, LocalDate> asZonedDateTime() {
        ZonedDateTime zonedDateTime = actual.toZonedDateTime();
        return new ChronoZonedDateTimeAssert(this, zonedDateTime) {
        };
    }

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see ChronoZonedDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        LocalTime localTime = actual.toLocalTime();
        return new LocalTimeAssert<>(this, localTime);
    }

    public OffsetTimeAssert<?> asOffsetTime() {
        OffsetTime offsetTime = actual.toOffsetTime();
        return new OffsetTimeAssert<>(this, offsetTime);
    }

    public InstantAssert<?> asInstant() {
        Instant instant = actual.toInstant();
        return new InstantAssert<>(this, instant);
    }

}
