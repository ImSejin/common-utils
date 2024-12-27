/*
 * Copyright 2020 Sejin Im
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

package io.github.imsejin.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.zone.ZoneRules;
import java.util.concurrent.ThreadLocalRandom;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;
import io.github.imsejin.common.constant.DateType;

/**
 * Datetime utilities
 *
 * @see DateType
 */
public final class DateTimeUtils {

    private static final Randoms randoms = new Randoms(ThreadLocalRandom.current());

    @ExcludeFromGeneratedJacocoReport
    private DateTimeUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Returns {@link ZoneOffset} of system default.
     *
     * @return offset of zone
     */
    public static ZoneOffset getSystemDefaultZoneOffset() {
        ZoneId zone = ZoneId.systemDefault();
        ZoneRules rules = zone.getRules();
        return rules.getOffset(LocalDateTime.now(zone));
    }

    /**
     * Returns randomly generated {@link LocalDateTime}.
     *
     * @param start start date time
     * @param end   end date time
     * @return random date time
     */
    public static LocalDateTime random(ChronoLocalDateTime<? extends ChronoLocalDate> start,
            ChronoLocalDateTime<? extends ChronoLocalDate> end) {
        return randoms.nextDateTime(start, end);
    }

    /**
     * Returns randomly generated {@link LocalDateTime}.
     *
     * @param start  start date time
     * @param end    end date time
     * @param offset zone offset
     * @return random date time
     */
    public static LocalDateTime random(ChronoLocalDateTime<? extends ChronoLocalDate> start,
            ChronoLocalDateTime<? extends ChronoLocalDate> end,
            ZoneOffset offset) {
        return randoms.nextDateTime(start, end, offset);
    }

    private static class Randoms {
        private final ThreadLocalRandom random;

        private Randoms(ThreadLocalRandom random) {
            this.random = random;
        }

        public LocalDateTime nextDateTime(ChronoLocalDateTime<? extends ChronoLocalDate> start,
                ChronoLocalDateTime<? extends ChronoLocalDate> end) {
            return nextDateTime(start, end, getSystemDefaultZoneOffset());
        }

        public LocalDateTime nextDateTime(ChronoLocalDateTime<? extends ChronoLocalDate> start,
                ChronoLocalDateTime<? extends ChronoLocalDate> end,
                ZoneOffset offset) {
            long randomSeconds = this.random.nextLong(start.toEpochSecond(offset), end.toEpochSecond(offset));
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(randomSeconds), offset);
        }
    }

}
