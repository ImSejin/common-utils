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
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth

class DateTimeUtilsSpec extends Specification {

    @Unroll("DateTimeUtils.isLeapYear(#year)")
    def "isLeapYear"() {
        given:
        def yearMonth = YearMonth.of(year, Month.FEBRUARY)
        def numOfDays = yearMonth.lengthOfMonth()

        when:
        def leapYear = DateTimeUtils.isLeapYear(year)

        then:
        leapYear ? numOfDays == 29 : numOfDays == 28

        where:
        year << (0..2400)
    }

    def "random"() {
        given:
        def now = LocalDateTime.now()
        def startTime = LocalDateTime.of(now.getYear() - 10, Month.JANUARY, 1, 0, 0, 0)
        def endTime = now

        when:
        def randomTime = DateTimeUtils.random(startTime, endTime)

        then:
        randomTime.isAfter(startTime)
        randomTime.isBefore(endTime)

        where:
        i << (1..10_000)
    }

}
