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

import io.github.imsejin.common.constant.DateType
import spock.lang.Specification

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth
import java.time.ZoneOffset

class DateTimeUtilsSpec extends Specification {

    def "Checks if it is leap-year"() {
        given:
        def yearMonth = YearMonth.of(year, Month.FEBRUARY)
        def numOfDays = yearMonth.lengthOfMonth()

        when:
        def leapYear = DateTimeUtils.isLeapYear year

        then:
        leapYear ? numOfDays == 29 : numOfDays == 28

        where:
        year << (0..2400)
    }

    def "Returns formatted today"() {
        when:
        def actual = dateType == null ? DateTimeUtils.today() : DateTimeUtils.today(dateType)

        then:
        actual == expected

        where:
        dateType             | expected
        null                 | new SimpleDateFormat(DateType.DATE.pattern).format(new Date())
        DateType.YEAR        | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.MONTH       | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.DAY         | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.HOUR        | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.MINUTE      | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.SECOND      | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.YEAR_MONTH  | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.DATE        | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.DATE_TIME   | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.TIME        | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.F_DATE      | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.F_TIME      | new SimpleDateFormat(dateType.pattern).format(new Date())
        DateType.F_DATE_TIME | new SimpleDateFormat(dateType.pattern).format(new Date())
    }

    def "Returns formatted yesterday"() {
        given:
        def calendar = Calendar.instance
        calendar.setTime new Date()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        def yesterday = formatter.format new Date(calendar.timeInMillis)

        when:
        def actual = dateType == null ? DateTimeUtils.yesterday() : DateTimeUtils.yesterday(dateType)

        then:
        actual == yesterday

        where:
        dateType             | formatter
        null                 | new SimpleDateFormat(DateType.DATE.pattern)
        DateType.YEAR        | new SimpleDateFormat(dateType.pattern)
        DateType.MONTH       | new SimpleDateFormat(dateType.pattern)
        DateType.DAY         | new SimpleDateFormat(dateType.pattern)
        DateType.HOUR        | new SimpleDateFormat(dateType.pattern)
        DateType.MINUTE      | new SimpleDateFormat(dateType.pattern)
        DateType.SECOND      | new SimpleDateFormat(dateType.pattern)
        DateType.YEAR_MONTH  | new SimpleDateFormat(dateType.pattern)
        DateType.DATE        | new SimpleDateFormat(dateType.pattern)
        DateType.DATE_TIME   | new SimpleDateFormat(dateType.pattern)
        DateType.TIME        | new SimpleDateFormat(dateType.pattern)
        DateType.F_DATE      | new SimpleDateFormat(dateType.pattern)
        DateType.F_TIME      | new SimpleDateFormat(dateType.pattern)
        DateType.F_DATE_TIME | new SimpleDateFormat(dateType.pattern)
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
        i << (1..10_000)
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
