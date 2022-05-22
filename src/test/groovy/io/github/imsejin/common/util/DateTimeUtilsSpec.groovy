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

package io.github.imsejin.common.util

import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset

class DateTimeUtilsSpec extends Specification {

    def "Validates stringified date"() {
        when:
        def actual = dayOfWeek ? DateTimeUtils.validate(date, dayOfWeek) : DateTimeUtils.validate(date)

        then:
        actual == expected

        where:
        date         | dayOfWeek          || expected
        "20190229"   | null               || false
        "2019-02-29" | null               || false
        "20200229"   | null               || true
        "2020-02-29" | null               || true
        "20190228"   | DayOfWeek.THURSDAY || true
        "2019-02-29" | DayOfWeek.FRIDAY   || false
        "20200228"   | DayOfWeek.SATURDAY || false
        "2020-02-29" | DayOfWeek.SATURDAY || true
    }

    def "Generates randomized LocalDateTime"() {
        given:
        def now = LocalDateTime.now()
        def startTime = LocalDateTime.of(now.getYear() - 10, Month.JANUARY, 1, 0, 0, 0)
        def endTime = now

        when:
        def randomTime = DateTimeUtils.random(startTime, endTime)

        then:
        randomTime.isAfter startTime
        randomTime.isBefore endTime

        where:
        i << (1..4096)
    }

    def "Generates randomized LocalDateTime with ZoneOffset"() {
        given:
        def now = LocalDateTime.now()
        def startTime = LocalDateTime.of(now.getYear() - 10, Month.JANUARY, 1, 0, 0, 0)
        def endTime = now
        def offset = ZoneOffset.of(offsetId)

        when:
        def randomTime = DateTimeUtils.random(startTime, endTime, offset)

        then:
        randomTime.isAfter startTime
        randomTime.isBefore endTime

        where:
        offsetId << ["Z", "+1", "-02", "+03:30", "-04:15", "+0545", "-0605", "+07:00:00", "-08:30:00", "+092535", "-133000"]
    }

}
