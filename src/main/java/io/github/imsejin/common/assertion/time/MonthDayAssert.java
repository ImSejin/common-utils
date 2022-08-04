/*
 * Copyright 2022 Sejin Im
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
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAccessorAssert;

import java.time.MonthDay;

public class MonthDayAssert<SELF extends MonthDayAssert<SELF>>
        extends AbstractTemporalAccessorAssert<SELF, MonthDay> {

    public MonthDayAssert(MonthDay actual) {
        super(actual);
    }

    // -------------------------------------------------------------------------------------------------

    public MonthAssert<?> asMonth() {
        MonthAssert<?> assertion = Asserts.that(actual.getMonth());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public NumberAssert<?, Integer> asDayOfMonth() {
        NumberAssert<?, Integer> assertion = Asserts.that(actual.getDayOfMonth());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
