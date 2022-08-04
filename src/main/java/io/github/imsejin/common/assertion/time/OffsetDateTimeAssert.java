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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.OffsetAssertable;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoZonedDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeAssert<SELF extends OffsetDateTimeAssert<SELF>>
        extends AbstractTemporalAccessorAssert<SELF, OffsetDateTime>
        implements OffsetAssertable<SELF, OffsetDateTime> {

    public OffsetDateTimeAssert(OffsetDateTime actual) {
        super(actual);
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
    public ChronoLocalDateAssert<?> asLocalDate() {
        ChronoLocalDateAssert<?> assertion = Asserts.that(actual.toLocalDate());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    /**
     * @return another assertion
     * @see ChronoZonedDateTimeAssert#asLocalDateTime()
     */
    public ChronoLocalDateTimeAssert<?, LocalDate> asLocalDateTime() {
        ChronoLocalDateTimeAssert<?, LocalDate> assertion = Asserts.that(actual.toLocalDateTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public ChronoZonedDateTimeAssert<?, LocalDate> asZonedDateTime() {
        ChronoZonedDateTimeAssert<?, LocalDate> assertion = Asserts.that(actual.toZonedDateTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see ChronoZonedDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        LocalTimeAssert<?> assertion = Asserts.that(actual.toLocalTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public OffsetTimeAssert<?> asOffsetTime() {
        OffsetTimeAssert<?> assertion = Asserts.that(actual.toOffsetTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public InstantAssert<?> asInstant() {
        InstantAssert<?> assertion = Asserts.that(actual.toInstant());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
