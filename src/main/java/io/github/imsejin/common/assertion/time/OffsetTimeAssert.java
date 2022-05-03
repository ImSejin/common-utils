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
import io.github.imsejin.common.assertion.time.chrono.AbstractChronoZonedDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateTimeAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAssert;

import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeAssert<SELF extends OffsetTimeAssert<SELF>>
        extends AbstractTemporalAssert<SELF, OffsetTime>
        implements OffsetAssertable<SELF, OffsetTime> {

    public OffsetTimeAssert(OffsetTime actual) {
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
     * @see ChronoLocalDateTimeAssert#asLocalTime()
     * @see AbstractChronoZonedDateTimeAssert#asLocalTime()
     * @see OffsetDateTimeAssert#asLocalTime()
     */
    public LocalTimeAssert<?> asLocalTime() {
        LocalTimeAssert<?> assertion = Asserts.that(actual.toLocalTime());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
