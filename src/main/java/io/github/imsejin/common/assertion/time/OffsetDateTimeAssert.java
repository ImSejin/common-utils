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
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeAssert<SELF extends OffsetDateTimeAssert<SELF>>
        extends AbstractObjectAssert<SELF, OffsetDateTime>
        implements DateAssertion<SELF, OffsetDateTime>, OffsetAssertion<SELF> {

    public OffsetDateTimeAssert(OffsetDateTime actual) {
        super(actual);
    }

    @Override
    public SELF isEqualTo(OffsetDateTime expected) {
        if (!actual.isEqual(expected)) throw getException();
        return self;
    }

    @Override
    public SELF isNotEqualTo(OffsetDateTime expected) {
        if (actual.isEqual(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isBefore(OffsetDateTime expected) {
        if (!actual.isBefore(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isBeforeOrEqualTo(OffsetDateTime expected) {
        if (actual.compareTo(expected) > 0) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isAfter(OffsetDateTime expected) {
        if (!actual.isAfter(expected)) throw getException();
        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isAfterOrEqualTo(OffsetDateTime expected) {
        if (actual.compareTo(expected) < 0) throw getException();
        return self;
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
        return Asserts.that(actual.toLocalDate());
    }

    /**
     * @return another assertion
     * @see AbstractChronoZonedDateTimeAssert#asLocalDateTime()
     */
    public AbstractChronoLocalDateTimeAssert<?, LocalDate> asLocalDateTime() {
        return Asserts.that(actual.toLocalDateTime());
    }

    public AbstractChronoZonedDateTimeAssert<?, LocalDate> asZonedDateTime() {
        return Asserts.that(actual.toZonedDateTime());
    }

    /**
     * @return another assertion
     * @see AbstractChronoLocalDateTimeAssert#asLocalTime()
     * @see AbstractChronoZonedDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        return Asserts.that(actual.toLocalTime());
    }

    public OffsetTimeAssert<?> asOffsetTime() {
        return Asserts.that(actual.toOffsetTime());
    }

}
