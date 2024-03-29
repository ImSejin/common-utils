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

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.lang.IntegerAssert;
import io.github.imsejin.common.assertion.lang.LongAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

public class LocalTimeAssert<SELF extends LocalTimeAssert<SELF>>
        extends AbstractTemporalAccessorAssert<SELF, LocalTime> {

    public LocalTimeAssert(LocalTime actual) {
        super(actual);
    }

    protected LocalTimeAssert(Descriptor<?> descriptor, LocalTime actual) {
        super(descriptor, actual);
    }

    public SELF isMidnight() {
        return super.isEqualTo(LocalTime.MIDNIGHT);
    }

    public SELF isNoon() {
        return super.isEqualTo(LocalTime.NOON);
    }

    public SELF isBeforeNoon() {
        return isBefore(LocalTime.NOON);
    }

    public SELF isBeforeOrEqualToNoon() {
        return isBeforeOrEqualTo(LocalTime.NOON);
    }

    public SELF isAfternoon() {
        return isAfter(LocalTime.NOON);
    }

    public SELF isAfterOrEqualToNoon() {
        return isAfterOrEqualTo(LocalTime.NOON);
    }

    // -------------------------------------------------------------------------------------------------

    public IntegerAssert<?> asSecondOfDay() {
        class IntegerAssertImpl extends IntegerAssert<IntegerAssertImpl> {
            IntegerAssertImpl(Descriptor<?> descriptor, Integer actual) {
                super(descriptor, actual);
            }
        }

        int secondOfDay = actual.toSecondOfDay();
        return new IntegerAssertImpl(this, secondOfDay);
    }

    public LongAssert<?> asNanoOfDay() {
        class LongAssertImpl extends LongAssert<LongAssertImpl> {
            LongAssertImpl(Descriptor<?> descriptor, Long actual) {
                super(descriptor, actual);
            }
        }

        long nanoOfDay = actual.toNanoOfDay();
        return new LongAssertImpl(this, nanoOfDay);
    }

}
