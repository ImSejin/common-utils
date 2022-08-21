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

package io.github.imsejin.common.security.model;

import io.github.imsejin.common.assertion.Asserts;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Verification {

    private final Object credentials;

    private final Duration duration;

    private final LocalDateTime createdDateTime = LocalDateTime.now();

    public Verification(Object credentials, Duration duration) {
        Asserts.that(credentials)
                .as("Verification.credentials is allowed to be null")
                .isNotNull();
        Asserts.that(duration)
                .as("Verification.duration is allowed to be null")
                .isNotNull()
                .as("Verification.duration must be zero or positive, but it is not: '{0}'", duration)
                .isZeroOrPositive();

        this.credentials = credentials;
        this.duration = duration;
    }

    public boolean verify(Object credentials) {
        // We determine that the cost of comparing credentials is cheaper
        // than the cost of comparing whether the verification time has expired,
        // so compare credentials first.
        if (!Objects.deepEquals(this.credentials, credentials)) return false;

        return !isExpired();
    }

    public boolean isExpired() {
        Duration duration = Duration.between(this.createdDateTime, LocalDateTime.now());

        if (duration.isNegative()) {
            return true;
        }

        // Makes comparison precise.
        BigDecimal thatDuration = new BigDecimal(duration.getSeconds() + "." + duration.getNano());
        BigDecimal thisDuration = new BigDecimal(this.duration.getSeconds() + "." + this.duration.getNano());

        return thatDuration.compareTo(thisDuration) > 0;
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public LocalDateTime getCreatedDateTime() {
        return this.createdDateTime;
    }

}
