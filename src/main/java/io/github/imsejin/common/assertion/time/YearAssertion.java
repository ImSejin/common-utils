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

import io.github.imsejin.common.assertion.time.temporal.AbstractTemporalAssert;

/**
 * Assertion for year
 */
public interface YearAssertion<SELF extends AbstractTemporalAssert<SELF, ?>> {

    String DEFAULT_DESCRIPTION_IS_LEAP_YEAR = "It is expected to be leap year, but it isn't. (actual: '{0}')";

    String DEFAULT_DESCRIPTION_IS_NOT_LEAP_YEAR = "It is expected not to be leap year, but it is. (actual: '{0}')";

    SELF isLeapYear();

    SELF isNotLeapYear();

}
