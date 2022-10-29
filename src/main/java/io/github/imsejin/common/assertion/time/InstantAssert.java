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
import io.github.imsejin.common.assertion.lang.LongAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;
import io.github.imsejin.common.assertion.util.DateAssert;

import java.time.Instant;
import java.util.Date;

public class InstantAssert<SELF extends InstantAssert<SELF>> extends AbstractTemporalAccessorAssert<SELF, Instant> {

    public InstantAssert(Instant actual) {
        super(actual);
    }

    protected InstantAssert(Descriptor<?> descriptor, Instant actual) {
        super(descriptor, actual);
    }

    // -------------------------------------------------------------------------------------------------

    public DateAssert<?, Date> asDate() {
        class DateAssertImpl extends DateAssert<DateAssertImpl, Date> {
            DateAssertImpl(Descriptor<?> descriptor, Date actual) {
                super(descriptor, actual);
            }
        }

        return new DateAssertImpl(this, Date.from(actual));
    }

    public LongAssert<?> asEpochMilli() {
        class LongAssertImpl extends LongAssert<LongAssertImpl> {
            LongAssertImpl(Descriptor<?> descriptor, Long actual) {
                super(descriptor, actual);
            }
        }

        long epochMilli = actual.toEpochMilli();
        return new LongAssertImpl(this, epochMilli);
    }

}
