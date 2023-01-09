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
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.time.InstantAssert;
import io.github.imsejin.common.assertion.time.LocalTimeAssert;
import io.github.imsejin.common.assertion.time.OffsetDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;
import io.github.imsejin.common.util.DateTimeUtils;

public class ChronoLocalDateTimeAssert<
        SELF extends ChronoLocalDateTimeAssert<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends AbstractTemporalAccessorAssert<SELF, ChronoLocalDateTime<?>> {

    public ChronoLocalDateTimeAssert(ChronoLocalDateTime<DATE> actual) {
        super(actual);
    }

    protected ChronoLocalDateTimeAssert(Descriptor<?> descriptor, ChronoLocalDateTime<?> actual) {
        super(descriptor, actual);
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * @return another assertion
     * @see ChronoZonedDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public ChronoLocalDateAssert<?> asLocalDate() {
        ChronoLocalDate localDate = actual.toLocalDate();
        return new ChronoLocalDateAssert<>(this, localDate);
    }

    /**
     * @return another assertion
     * @see ChronoZonedDateTimeAssert#asLocalTime()
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

    public InstantAssert<?> asInstant() {
        class InstantAssertImpl extends InstantAssert<InstantAssertImpl> {
            InstantAssertImpl(Descriptor<?> descriptor, Instant actual) {
                super(descriptor, actual);
            }
        }

        ZoneOffset zoneOffset = DateTimeUtils.getSystemDefaultZoneOffset();
        Instant instant = actual.toInstant(zoneOffset);
        return new InstantAssertImpl(this, instant);
    }

}
