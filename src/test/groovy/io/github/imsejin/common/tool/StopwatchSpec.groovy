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

package io.github.imsejin.common.tool

import spock.lang.Specification

import java.math.RoundingMode
import java.util.concurrent.TimeUnit

class StopwatchSpec extends Specification {

    def "Default timeUnit is nanoseconds"() {
        given:
        def stopwatch = new Stopwatch()

        expect:
        stopwatch.timeUnit == TimeUnit.NANOSECONDS
    }

    def "Sets timeUnit with setter"() {
        given:
        def stopwatch = new Stopwatch()

        when:
        stopwatch.timeUnit = timeUnit as TimeUnit

        then: """
            Stopwatch returns timeUnit as set.
        """
        stopwatch.timeUnit == timeUnit

        when:
        stopwatch.timeUnit = null

        then: """
            Stopwatch doesn't allow you to change timeUnit into null.
        """
        def e = thrown IllegalArgumentException
        e.message == "Stopwatch.timeUnit cannot be null"

        where:
        timeUnit << TimeUnit.values()
    }

    def "Sets timeUnit with constructor"() {
        when:
        def stopwatch = new Stopwatch(timeUnit as TimeUnit)

        then: """
            Stopwatch returns timeUnit as set.
        """
        stopwatch.timeUnit == timeUnit

        when:
        new Stopwatch(null)

        then: """
            Stopwatch doesn't allow you to change timeUnit into null.
        """
        def e = thrown IllegalArgumentException
        e.message == "Stopwatch.timeUnit cannot be null"

        where:
        timeUnit << TimeUnit.values()
    }

    def "Starts"() {
        given:
        def stopwatch = new Stopwatch()

        when:
        stopwatch.start()

        then:
        noExceptionThrown()

        when:
        stopwatch.start()

        then: """
            Stopwatch can't be started while running.
        """
        def e = thrown UnsupportedOperationException
        e.message == "Stopwatch cannot start while running"
    }

    def "Stops"() {
        given:
        def stopwatch = new Stopwatch()

        when:
        stopwatch.start()
        stopwatch.stop()

        then:
        noExceptionThrown()

        when:
        stopwatch.stop()

        then: """
            Stopwatch can't be stopped while not running.
        """
        def e = thrown UnsupportedOperationException
        e.message == "Stopwatch cannot stop while not running"
    }

    def "Checks stopwatch is running"() {
        given:
        def stopwatch = new Stopwatch()

        expect:
        !stopwatch.isRunning()

        when:
        stopwatch.start()

        then:
        stopwatch.isRunning()

        when:
        stopwatch.stop()

        then:
        !stopwatch.isRunning()
    }

    def "Checks stopwatch has never been stopped"() {
        given:
        def stopwatch = new Stopwatch()

        expect:
        stopwatch.hasNeverBeenStopped()

        when:
        stopwatch.start()

        then:
        stopwatch.hasNeverBeenStopped()

        when:
        stopwatch.stop()

        then:
        !stopwatch.hasNeverBeenStopped()
    }

    def "Clears all the tasks"() {
        given:
        def stopwatch = new Stopwatch()
        stopwatch.start()
        stopwatch.stop()

        expect:
        !stopwatch.hasNeverBeenStopped()

        when:
        stopwatch.clear()

        then: """
            Clears all the task of stopwatch. In a nutshell, makes it new one.
        """
        stopwatch.hasNeverBeenStopped()

        when:
        stopwatch.start()
        stopwatch.clear()

        then: """
            Stopwatch can't be cleared while running.
        """
        def e = thrown UnsupportedOperationException
        e.message == "Stopwatch is running; stop it first to clear"
    }

    def "Forces all the tasks to be erased"() {
        given:
        def stopwatch = new Stopwatch()
        stopwatch.start()
        stopwatch.stop()

        expect:
        !stopwatch.hasNeverBeenStopped()

        when:
        stopwatch.forceClear()

        then:
        stopwatch.hasNeverBeenStopped()

        when:
        stopwatch.start()
        stopwatch.forceClear()

        then: """
            Stopwatch can be cleared while running.
        """
        noExceptionThrown()
    }

    def "Converts timeUnit to other"() {
        when:
        def time = Stopwatch.convertTimeUnit(amount, from, to)

        then:
        time == expected.setScale(8, RoundingMode.HALF_UP)

        where:
        amount                    | from                  | to                    || expected
        40.765387 as BigDecimal   | TimeUnit.NANOSECONDS  | TimeUnit.NANOSECONDS  || amount
        0.247772 as BigDecimal    | TimeUnit.NANOSECONDS  | TimeUnit.MICROSECONDS || amount / 1_000
        707.886497 as BigDecimal  | TimeUnit.NANOSECONDS  | TimeUnit.MILLISECONDS || amount / 1_000_000
        586.047980 as BigDecimal  | TimeUnit.NANOSECONDS  | TimeUnit.SECONDS      || amount / 1_000_000_000
        4390.779972 as BigDecimal | TimeUnit.NANOSECONDS  | TimeUnit.MINUTES      || amount / 1_000_000_000 / 60
        4.935103 as BigDecimal    | TimeUnit.NANOSECONDS  | TimeUnit.HOURS        || amount / 1_000_000_000 / 60 / 60
        2759.818836 as BigDecimal | TimeUnit.NANOSECONDS  | TimeUnit.DAYS         || amount / 1_000_000_000 / 60 / 60 / 24
        193.212629 as BigDecimal  | TimeUnit.MICROSECONDS | TimeUnit.NANOSECONDS  || amount * 1_000
        807.799758 as BigDecimal  | TimeUnit.MICROSECONDS | TimeUnit.MICROSECONDS || amount
        2.027992 as BigDecimal    | TimeUnit.MICROSECONDS | TimeUnit.MILLISECONDS || amount / 1_000
        6012.986905 as BigDecimal | TimeUnit.MICROSECONDS | TimeUnit.SECONDS      || amount / 1_000_000
        397.256432 as BigDecimal  | TimeUnit.MICROSECONDS | TimeUnit.MINUTES      || amount / 1_000_000 / 60
        10.871100 as BigDecimal   | TimeUnit.MICROSECONDS | TimeUnit.HOURS        || amount / 1_000_000 / 60 / 60
        0.751938 as BigDecimal    | TimeUnit.MICROSECONDS | TimeUnit.DAYS         || amount / 1_000_000 / 60 / 60 / 24
        672.348680 as BigDecimal  | TimeUnit.MILLISECONDS | TimeUnit.NANOSECONDS  || amount * 1_000_000
        512.769583 as BigDecimal  | TimeUnit.MILLISECONDS | TimeUnit.MICROSECONDS || amount * 1_000
        46.998484 as BigDecimal   | TimeUnit.MILLISECONDS | TimeUnit.MILLISECONDS || amount
        7.699957 as BigDecimal    | TimeUnit.MILLISECONDS | TimeUnit.SECONDS      || amount / 1_000
        3202.994308 as BigDecimal | TimeUnit.MILLISECONDS | TimeUnit.MINUTES      || amount / 1_000 / 60
        0.860134 as BigDecimal    | TimeUnit.MILLISECONDS | TimeUnit.HOURS        || amount / 1_000 / 60 / 60
        76.511044 as BigDecimal   | TimeUnit.MILLISECONDS | TimeUnit.DAYS         || amount / 1_000 / 60 / 60 / 24
        495.158337 as BigDecimal  | TimeUnit.SECONDS      | TimeUnit.NANOSECONDS  || amount * 1_000_000_000
        0.776571 as BigDecimal    | TimeUnit.SECONDS      | TimeUnit.MICROSECONDS || amount * 1_000_000
        1586.363102 as BigDecimal | TimeUnit.SECONDS      | TimeUnit.MILLISECONDS || amount * 1_000
        40.303662 as BigDecimal   | TimeUnit.SECONDS      | TimeUnit.SECONDS      || amount
        522.872456 as BigDecimal  | TimeUnit.SECONDS      | TimeUnit.MINUTES      || amount / 60
        4.203538 as BigDecimal    | TimeUnit.SECONDS      | TimeUnit.HOURS        || amount / 60 / 60
        33.678104 as BigDecimal   | TimeUnit.SECONDS      | TimeUnit.DAYS         || amount / 60 / 60 / 24
        57.463286 as BigDecimal   | TimeUnit.MINUTES      | TimeUnit.NANOSECONDS  || amount * 1_000_000_000 * 60
        258.330397 as BigDecimal  | TimeUnit.MINUTES      | TimeUnit.MICROSECONDS || amount * 1_000_000 * 60
        7258.939255 as BigDecimal | TimeUnit.MINUTES      | TimeUnit.MILLISECONDS || amount * 1_000 * 60
        314.186997 as BigDecimal  | TimeUnit.MINUTES      | TimeUnit.SECONDS      || amount * 60
        23.641139 as BigDecimal   | TimeUnit.MINUTES      | TimeUnit.MINUTES      || amount
        0.535965 as BigDecimal    | TimeUnit.MINUTES      | TimeUnit.HOURS        || amount / 60
        192.511682 as BigDecimal  | TimeUnit.MINUTES      | TimeUnit.DAYS         || amount / 60 / 24
        93.416033 as BigDecimal   | TimeUnit.HOURS        | TimeUnit.NANOSECONDS  || amount * 1_000_000_000 * 60 * 60
        467.008415 as BigDecimal  | TimeUnit.HOURS        | TimeUnit.MICROSECONDS || amount * 1_000_000 * 60 * 60
        29.966516 as BigDecimal   | TimeUnit.HOURS        | TimeUnit.MILLISECONDS || amount * 1_000 * 60 * 60
        1826.635020 as BigDecimal | TimeUnit.HOURS        | TimeUnit.SECONDS      || amount * 60 * 60
        911.746857 as BigDecimal  | TimeUnit.HOURS        | TimeUnit.MINUTES      || amount * 60
        0.544947 as BigDecimal    | TimeUnit.HOURS        | TimeUnit.HOURS        || amount
        710.017145 as BigDecimal  | TimeUnit.HOURS        | TimeUnit.DAYS         || amount / 24
        0.237530 as BigDecimal    | TimeUnit.DAYS         | TimeUnit.NANOSECONDS  || amount * 1_000_000_000 * 60 * 60 * 24
        653.095051 as BigDecimal  | TimeUnit.DAYS         | TimeUnit.MICROSECONDS || amount * 1_000_000 * 60 * 60 * 24
        31.316865 as BigDecimal   | TimeUnit.DAYS         | TimeUnit.MILLISECONDS || amount * 1_000 * 60 * 60 * 24
        66.350011 as BigDecimal   | TimeUnit.DAYS         | TimeUnit.SECONDS      || amount * 60 * 60 * 24
        2030.887603 as BigDecimal | TimeUnit.DAYS         | TimeUnit.MINUTES      || amount * 60 * 24
        372.505049 as BigDecimal  | TimeUnit.DAYS         | TimeUnit.HOURS        || amount * 24
        74.652235 as BigDecimal   | TimeUnit.DAYS         | TimeUnit.DAYS         || amount
    }

    def "Converts timeUnit to abbreviation"() {
        when:
        def abbreviation = Stopwatch.getTimeUnitAbbreviation(timeUnit)

        then:
        abbreviation == expected

        where:
        timeUnit              | expected
        TimeUnit.NANOSECONDS  | "ns"
        TimeUnit.MICROSECONDS | "μs"
        TimeUnit.MILLISECONDS | "ms"
        TimeUnit.SECONDS      | "sec"
        TimeUnit.MINUTES      | "min"
        TimeUnit.HOURS        | "hrs"
        TimeUnit.DAYS         | "days"
    }

}
