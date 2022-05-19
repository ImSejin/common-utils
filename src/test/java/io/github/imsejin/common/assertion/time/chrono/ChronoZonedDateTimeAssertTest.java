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

package io.github.imsejin.common.assertion.time.chrono;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ChronoZonedDateTimeAssert")
class ChronoZonedDateTimeAssertTest {

    @Nested
    class IsSameZone {
        @Test
        @DisplayName("passes, when actual and other have the same zone")
        void test0() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(-18, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone)).isSameZone(zone)));
        }

        @Test
        @DisplayName("throws exception, when actual and other don't have the sane zone")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(1, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone))
                            .isSameZone(ZoneOffset.ofTotalSeconds(-zone.getTotalSeconds())))
                    .withMessageStartingWith("They are expected to have the same zone, but they aren't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class IsNotSameZone {
        @Test
        @DisplayName("passes, when actual and other don't have the sane zone")
        void test0() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(1, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone))
                            .isNotSameZone(ZoneOffset.ofTotalSeconds(-zone.getTotalSeconds()))));
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same zone")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(-18, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone)).isNotSameZone(zone))
                    .withMessageStartingWith("They are expected not to have the same zone, but they are."));
        }
    }

}
