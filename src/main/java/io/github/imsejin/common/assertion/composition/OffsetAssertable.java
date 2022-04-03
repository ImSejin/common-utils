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

package io.github.imsejin.common.assertion.composition;

import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

/**
 * Composition of assertion for offset.
 *
 * @param <SELF>   assertion class
 * @param <ACTUAL> type that implements interfaces {@link Temporal},
 *                 {@link TemporalAdjuster}, {@link Comparable} and {@link Serializable}
 */
public interface OffsetAssertable<
        SELF extends ObjectAssert<SELF, ACTUAL>,
        ACTUAL extends Temporal & TemporalAdjuster & Comparable<ACTUAL> & Serializable> {

    SELF isSameOffset(ZoneOffset expected);

    SELF isNotSameOffset(ZoneOffset expected);

}
