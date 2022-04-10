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
import io.github.imsejin.common.assertion.time.chrono.AbstractChronoLocalDateAssert;
import io.github.imsejin.common.assertion.time.chrono.AbstractChronoLocalDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.AbstractChronoZonedDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAssert;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeAssert<SELF extends OffsetDateTimeAssert<SELF>>
        extends AbstractTemporalAssert<SELF, OffsetDateTime>
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
        if (!actual.getOffset().equals(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isNotSameOffset(ZoneOffset expected) {
        if (actual.getOffset().equals(expected)) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return another assertion
     * @see AbstractChronoLocalDateTimeAssert#asLocalDate()
     * @see AbstractChronoZonedDateTimeAssert#asLocalDate()
     */
    public AbstractChronoLocalDateAssert<?> asLocalDate() {
        AbstractChronoLocalDateAssert<?> assertion = Asserts.that(actual.toLocalDate());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    /**
     * @return another assertion
     * @see AbstractChronoZonedDateTimeAssert#asLocalDateTime()
     */
    public AbstractChronoLocalDateTimeAssert<?, LocalDate> asLocalDateTime() {
        AbstractChronoLocalDateTimeAssert<?, LocalDate> assertion = Asserts.that(actual.toLocalDateTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public AbstractChronoZonedDateTimeAssert<?, LocalDate> asZonedDateTime() {
        AbstractChronoZonedDateTimeAssert<?, LocalDate> assertion = Asserts.that(actual.toZonedDateTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    /**
     * @return another assertion
     * @see AbstractChronoLocalDateTimeAssert#asLocalTime()
     * @see AbstractChronoZonedDateTimeAssert#asLocalTime()
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
