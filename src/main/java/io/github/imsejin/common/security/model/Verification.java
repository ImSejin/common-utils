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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;

import io.github.imsejin.common.assertion.Asserts;

/**
 * Time-limited security object for verification
 */
@Getter
public class Verification {

    /**
     * Credentials for verification.
     */
    private final Object credentials;

    /**
     * Time limit for verification.
     */
    private final Duration duration;

    /**
     * Created date and time of verification.
     */
    private final LocalDateTime createdDateTime;

    /**
     * Creates an instance.
     *
     * @param credentials credentials
     * @param duration    time limit
     */
    public Verification(Object credentials, Duration duration) {
        Asserts.that(credentials)
                .describedAs("Verification.credentials is allowed to be null")
                .isNotNull();
        Asserts.that(duration)
                .describedAs("Verification.duration is allowed to be null")
                .isNotNull()
                .describedAs("Verification.duration must be zero or positive, but it is not: '{0}'", duration)
                .isZeroOrPositive();

        this.credentials = credentials;
        this.duration = duration;
        this.createdDateTime = LocalDateTime.now();
    }

    /**
     * Verifies that credentials is valid and time limit is not expired.
     *
     * @param credentials credentials
     * @return result of verification
     */
    public boolean verify(Object credentials) {
        // We determine that the cost of comparing credentials is cheaper
        // than the cost of comparing whether the verification time has expired,
        // so compare credentials first.
        if (!Objects.deepEquals(this.credentials, credentials)) {
            return false;
        }

        return !isExpired();
    }

    /**
     * Returns whether time limit is expired or not.
     *
     * @return expiration of time limit
     */
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

}
