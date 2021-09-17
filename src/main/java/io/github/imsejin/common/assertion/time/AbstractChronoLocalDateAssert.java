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

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

import java.time.chrono.ChronoLocalDate;

public abstract class AbstractChronoLocalDateAssert<
        SELF extends AbstractChronoLocalDateAssert<SELF, ACTUAL>,
        ACTUAL extends ChronoLocalDate>
        extends AbstractObjectAssert<SELF, ACTUAL>
        implements DateAssertion<SELF, ACTUAL> {

    protected AbstractChronoLocalDateAssert(ACTUAL actual) {
        super(actual);
    }

    @Override
    public SELF isEqualTo(ACTUAL expected) {
        if (!actual.isEqual(expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEqualTo(ACTUAL expected) {
        if (actual.isEqual(expected)) {
            setDefaultDescription("They are expected to be not equal, but they are. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isBefore(ACTUAL expected) {
        if (!actual.isBefore(expected)) {
            setDefaultDescription("It is expected to be before than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isBeforeOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) > 0) {
            setDefaultDescription("It is expected to be before than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isAfter(ACTUAL expected) {
        if (!actual.isAfter(expected)) {
            setDefaultDescription("It is expected to be after than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * @param expected expected value
     * @return self
     */
    @Override
    public SELF isAfterOrEqualTo(ACTUAL expected) {
        if (actual.compareTo(expected) < 0) {
            setDefaultDescription("It is expected to be after than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLeapYear() {
        if (!actual.isLeapYear()) {
            setDefaultDescription("It is expected to be leap year, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotLeapYear() {
        if (actual.isLeapYear()) {
            setDefaultDescription("It is expected not to be leap year, but it is. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

}
