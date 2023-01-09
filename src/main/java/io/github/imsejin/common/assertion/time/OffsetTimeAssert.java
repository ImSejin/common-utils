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

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.AbstractMap.SimpleEntry;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.OffsetAssertable;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoZonedDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

public class OffsetTimeAssert<SELF extends OffsetTimeAssert<SELF>>
        extends AbstractTemporalAccessorAssert<SELF, OffsetTime>
        implements OffsetAssertable<SELF, OffsetTime> {

    public OffsetTimeAssert(OffsetTime actual) {
        super(actual);
    }

    protected OffsetTimeAssert(Descriptor<?> descriptor, OffsetTime actual) {
        super(descriptor, actual);
    }

    /**
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isSameOffset(ZoneOffset expected) {
        ZoneOffset offset = actual.getOffset();

        if (!offset.equals(expected)) {
            setDefaultDescription(OffsetAssertable.DEFAULT_DESCRIPTION_IS_SAME_OFFSET);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.offset", offset),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isNotSameOffset(ZoneOffset expected) {
        ZoneOffset offset = actual.getOffset();

        if (offset.equals(expected)) {
            setDefaultDescription(OffsetAssertable.DEFAULT_DESCRIPTION_IS_NOT_SAME_OFFSET);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.offset", offset),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * @return another assertion
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see ChronoZonedDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        LocalTime localTime = actual.toLocalTime();
        return new LocalTimeAssert<>(this, localTime);
    }

}
