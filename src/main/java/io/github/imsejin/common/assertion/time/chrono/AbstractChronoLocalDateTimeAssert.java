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
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAssert;
import io.github.imsejin.common.util.DateTimeUtils;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

public abstract class AbstractChronoLocalDateTimeAssert<
        SELF extends AbstractChronoLocalDateTimeAssert<SELF, DATE>,
        DATE extends ChronoLocalDate>
        extends AbstractTemporalAssert<SELF, ChronoLocalDateTime<?>> {

    protected AbstractChronoLocalDateTimeAssert(ChronoLocalDateTime<DATE> actual) {
        super(actual);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return another assertion
     * @see AbstractChronoZonedDateTimeAssert#asLocalDate()
     * @see OffsetDateTimeAssert#asLocalDate()
     */
    public AbstractChronoLocalDateAssert<?> asLocalDate() {
        AbstractChronoLocalDateAssert<?> assertion = Asserts.that(actual.toLocalDate());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    /**
     * @return another assertion
     * @see AbstractChronoZonedDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        LocalTimeAssert<?> assertion = Asserts.that(actual.toLocalTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public InstantAssert<?> asInstant() {
        InstantAssert<?> assertion = Asserts.that(actual.toInstant(DateTimeUtils.getSystemDefaultZoneOffset()));
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
