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

import io.github.imsejin.common.tool.Stopwatch.Task
import spock.lang.Specification

import java.math.RoundingMode
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

import static java.util.stream.Collectors.toList

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
            Stopwatch returns timeUnit as it is given.
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
            Stopwatch returns timeUnit as it is given.
        """
        stopwatch.timeUnit == timeUnit

        when:
        new Stopwatch(null)

        then: """
            Stopwatch doesn't allow you to set null to timeUnit.
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

    def "Gets all the tasks"() {
        given:
        def stopwatch = new Stopwatch()
        def taskCount = 10

        expect:
        stopwatch.tasks.isEmpty()

        when:
        (0..<taskCount).each {
            stopwatch.start("task-$it")
            stopwatch.stop()
        }

        then:
        stopwatch.tasks.size() == taskCount
        for (i in 0..<taskCount) {
            def task = stopwatch.tasks[i]
            assert task.name == "task-$i"
            assert task.order == i
            assert task.elapsedNanoTime > 0
        }

        when:
        stopwatch.tasks << new Task("new task", stopwatch.tasks.size(), 0)

        then: """
            Stopwatch.tasks are unmodifiable.
        """
        thrown UnsupportedOperationException
        stopwatch.tasks.size() == taskCount
    }

    @SuppressWarnings("GroovyAccessibility")
    def "Gets total time"() {
        given:
        def stopwatch = new Stopwatch(timeUnit)
        def taskTimes = taskTimeInfo.split(",").toList().stream().map(BigDecimal.&new).collect(toList())
        (0..<taskTimes.size()).each {
            def taskTime = taskTimes[it].longValueExact()
            stopwatch.@tasks.add(new Task("task-$it", it, taskTime))
            stopwatch.@totalNanoTime += taskTime
        }

        when:
        def totalTime = stopwatch.totalTime

        then: """
            Stopwatch.totalTime is equal to expected.
        """
        totalTime == (expected as BigDecimal).setScale(Stopwatch.ROUNDING_SCALE, RoundingMode.HALF_UP)

        where:
        timeUnit              | taskTimeInfo                || expected
        TimeUnit.NANOSECONDS  | "28,654,107"                || 28 + 654 + 107
        TimeUnit.MICROSECONDS | "707,68,59,13,4"            || (707 + 68 + 59 + 13 + 4) / 1_000
        TimeUnit.MILLISECONDS | "3.7081E+8"                 || 3.7081E+8 / 1_000_000
        TimeUnit.SECONDS      | "5.8192E+10,2.204E+10"      || (5.8192E+10 + 2.204E+10) / 1_000_000_000
        TimeUnit.MINUTES      | "8.509E+9,0,7.14E+9"        || (8.509E+9 + 0 + 7.14E+9) / 1_000_000_000 / 60
        TimeUnit.HOURS        | "3.141592E+10,1.732050E+10" || (3.141592E+10 + 1.732050E+10) / 1_000_000_000 / 60 / 60
        TimeUnit.DAYS         | "1.6384E+14"                || 1.6384E+14 / 1_000_000_000 / 60 / 60 / 24
    }

    @SuppressWarnings("GroovyAccessibility")
    def "Gets average time"() {
        given:
        def stopwatch = new Stopwatch(timeUnit)
        def taskTimes = taskTimeInfo.split(",").toList().stream().map(BigDecimal.&new).collect(toList())
        (0..<taskTimes.size()).each {
            def taskTime = taskTimes[it].longValueExact()
            stopwatch.@tasks.add(new Task("task-$it", it, taskTime))
            stopwatch.@totalNanoTime += taskTime
        }

        when:
        def averageTime = stopwatch.averageTime

        then: """
            Stopwatch.averageTime is equal to expected.
        """
        averageTime == (expected as BigDecimal).setScale(Stopwatch.ROUNDING_SCALE, RoundingMode.HALF_UP)

        where:
        timeUnit              | taskTimeInfo                || expected
        TimeUnit.NANOSECONDS  | "28,654,107"                || (28 + 654 + 107) / 3
        TimeUnit.MICROSECONDS | "707,68,59,13,4"            || ((707 + 68 + 59 + 13 + 4) / 5) / 1_000
        TimeUnit.MILLISECONDS | "3.7081E+8"                 || 3.7081E+8 / 1_000_000
        TimeUnit.SECONDS      | "5.8192E+10,2.204E+10"      || ((5.8192E+10 + 2.204E+10) / 2) / 1_000_000_000
        TimeUnit.MINUTES      | "8.509E+9,0,7.14E+9"        || ((8.509E+9 + 0 + 7.14E+9) / 3) / 1_000_000_000 / 60
        TimeUnit.HOURS        | "3.141592E+10,1.732050E+10" || ((3.141592E+10 + 1.732050E+10) / 2) / 1_000_000_000 / 60 / 60
        TimeUnit.DAYS         | "1.6384E+14"                || 1.6384E+14 / 1_000_000_000 / 60 / 60 / 24
    }

    def "Gets summary of stopwatch"() {
        given:
        def stopwatch = new Stopwatch(timeUnit as TimeUnit)
        def abbreviation = Stopwatch.getTimeUnitAbbreviation(timeUnit as TimeUnit)
        def pattern = Pattern.compile("^TOTAL = (\\d+(\\.\\d{1,$Stopwatch.ROUNDING_SCALE})?|\\d+\\.\\d+E[+-]\\d+) $abbreviation, " +
                "AVERAGE = (\\d+(\\.\\d{1,$Stopwatch.ROUNDING_SCALE})?|\\d+\\.\\d+E[+-]\\d+) $abbreviation\$")

        when:
        stopwatch.start()
        sleep(50)
        stopwatch.stop()
        def summary = stopwatch.summary

        then:
        summary.matches(pattern)

        where:
        timeUnit << TimeUnit.values()
    }

    def "Gets statistics of stopwatch"() {
        given:
        def stopwatch = new Stopwatch(timeUnit)
        def randomString = new RandomString()

        when:
        (0..<taskCount).each {
            stopwatch.start("task-%d: %s", it, randomString.nextString(8))
            sleep(10)
            stopwatch.stop()
        }

        then:
        def abbreviation = Stopwatch.getTimeUnitAbbreviation(timeUnit)
        def summary = stopwatch.summary.replaceAll("([.+])", '\\\\$1')
        def pattern = Pattern.compile("^${summary}\n"
                + "-{50}\n"
                + "$abbreviation {2,}% {2,}TASK_NAME\n"
                + "-{50}\n"
                + "((\\d+(\\.\\d{1,$Stopwatch.ROUNDING_SCALE})?|\\d+\\.\\d+E[+-]\\d+) {2,}\\d{1,3}\\.\\d{2} {2,}task-\\d+: [A-Za-z]{8}\n){$taskCount}\$",
                Pattern.DOTALL)
        stopwatch.statistics.matches(pattern)

        where:
        timeUnit              | taskCount
        TimeUnit.NANOSECONDS  | 1
        TimeUnit.MICROSECONDS | 2
        TimeUnit.MILLISECONDS | 3
        TimeUnit.SECONDS      | 1
        TimeUnit.MINUTES      | 2
        TimeUnit.HOURS        | 3
        TimeUnit.DAYS         | 1
    }

    def "Converts timeUnit to other"() {
        when:
        def time = Stopwatch.convertTimeUnit(amount as BigDecimal, from, to)

        then:
        time == expected.setScale(Stopwatch.ROUNDING_SCALE, RoundingMode.HALF_UP)

        where:
        amount        | from                  | to                    || expected
        "40.765387"   | TimeUnit.NANOSECONDS  | TimeUnit.NANOSECONDS  || new BigDecimal(amount)
        "0.247772"    | TimeUnit.NANOSECONDS  | TimeUnit.MICROSECONDS || new BigDecimal(amount) / 1_000
        "707.886497"  | TimeUnit.NANOSECONDS  | TimeUnit.MILLISECONDS || new BigDecimal(amount) / 1_000_000
        "586.047980"  | TimeUnit.NANOSECONDS  | TimeUnit.SECONDS      || new BigDecimal(amount) / 1_000_000_000
        "4390.779972" | TimeUnit.NANOSECONDS  | TimeUnit.MINUTES      || new BigDecimal(amount) / 1_000_000_000 / 60
        "4.935103"    | TimeUnit.NANOSECONDS  | TimeUnit.HOURS        || new BigDecimal(amount) / 1_000_000_000 / 60 / 60
        "2759.818836" | TimeUnit.NANOSECONDS  | TimeUnit.DAYS         || new BigDecimal(amount) / 1_000_000_000 / 60 / 60 / 24
        "193.212629"  | TimeUnit.MICROSECONDS | TimeUnit.NANOSECONDS  || new BigDecimal(amount) * 1_000
        "807.799758"  | TimeUnit.MICROSECONDS | TimeUnit.MICROSECONDS || new BigDecimal(amount)
        "2.027992"    | TimeUnit.MICROSECONDS | TimeUnit.MILLISECONDS || new BigDecimal(amount) / 1_000
        "6012.986905" | TimeUnit.MICROSECONDS | TimeUnit.SECONDS      || new BigDecimal(amount) / 1_000_000
        "397.256432"  | TimeUnit.MICROSECONDS | TimeUnit.MINUTES      || new BigDecimal(amount) / 1_000_000 / 60
        "10.871100"   | TimeUnit.MICROSECONDS | TimeUnit.HOURS        || new BigDecimal(amount) / 1_000_000 / 60 / 60
        "0.751938"    | TimeUnit.MICROSECONDS | TimeUnit.DAYS         || new BigDecimal(amount) / 1_000_000 / 60 / 60 / 24
        "672.348680"  | TimeUnit.MILLISECONDS | TimeUnit.NANOSECONDS  || new BigDecimal(amount) * 1_000_000
        "512.769583"  | TimeUnit.MILLISECONDS | TimeUnit.MICROSECONDS || new BigDecimal(amount) * 1_000
        "46.998484"   | TimeUnit.MILLISECONDS | TimeUnit.MILLISECONDS || new BigDecimal(amount)
        "7.699957"    | TimeUnit.MILLISECONDS | TimeUnit.SECONDS      || new BigDecimal(amount) / 1_000
        "3202.994308" | TimeUnit.MILLISECONDS | TimeUnit.MINUTES      || new BigDecimal(amount) / 1_000 / 60
        "0.860134"    | TimeUnit.MILLISECONDS | TimeUnit.HOURS        || new BigDecimal(amount) / 1_000 / 60 / 60
        "76.511044"   | TimeUnit.MILLISECONDS | TimeUnit.DAYS         || new BigDecimal(amount) / 1_000 / 60 / 60 / 24
        "495.158337"  | TimeUnit.SECONDS      | TimeUnit.NANOSECONDS  || new BigDecimal(amount) * 1_000_000_000
        "0.776571"    | TimeUnit.SECONDS      | TimeUnit.MICROSECONDS || new BigDecimal(amount) * 1_000_000
        "1586.363102" | TimeUnit.SECONDS      | TimeUnit.MILLISECONDS || new BigDecimal(amount) * 1_000
        "40.303662"   | TimeUnit.SECONDS      | TimeUnit.SECONDS      || new BigDecimal(amount)
        "522.872456"  | TimeUnit.SECONDS      | TimeUnit.MINUTES      || new BigDecimal(amount) / 60
        "4.203538"    | TimeUnit.SECONDS      | TimeUnit.HOURS        || new BigDecimal(amount) / 60 / 60
        "33.678104"   | TimeUnit.SECONDS      | TimeUnit.DAYS         || new BigDecimal(amount) / 60 / 60 / 24
        "57.463286"   | TimeUnit.MINUTES      | TimeUnit.NANOSECONDS  || new BigDecimal(amount) * 1_000_000_000 * 60
        "258.330397"  | TimeUnit.MINUTES      | TimeUnit.MICROSECONDS || new BigDecimal(amount) * 1_000_000 * 60
        "7258.939255" | TimeUnit.MINUTES      | TimeUnit.MILLISECONDS || new BigDecimal(amount) * 1_000 * 60
        "314.186997"  | TimeUnit.MINUTES      | TimeUnit.SECONDS      || new BigDecimal(amount) * 60
        "23.641139"   | TimeUnit.MINUTES      | TimeUnit.MINUTES      || new BigDecimal(amount)
        "0.535965"    | TimeUnit.MINUTES      | TimeUnit.HOURS        || new BigDecimal(amount) / 60
        "192.511682"  | TimeUnit.MINUTES      | TimeUnit.DAYS         || new BigDecimal(amount) / 60 / 24
        "93.416033"   | TimeUnit.HOURS        | TimeUnit.NANOSECONDS  || new BigDecimal(amount) * 1_000_000_000 * 60 * 60
        "467.008415"  | TimeUnit.HOURS        | TimeUnit.MICROSECONDS || new BigDecimal(amount) * 1_000_000 * 60 * 60
        "29.966516"   | TimeUnit.HOURS        | TimeUnit.MILLISECONDS || new BigDecimal(amount) * 1_000 * 60 * 60
        "1826.635020" | TimeUnit.HOURS        | TimeUnit.SECONDS      || new BigDecimal(amount) * 60 * 60
        "911.746857"  | TimeUnit.HOURS        | TimeUnit.MINUTES      || new BigDecimal(amount) * 60
        "0.544947"    | TimeUnit.HOURS        | TimeUnit.HOURS        || new BigDecimal(amount)
        "710.017145"  | TimeUnit.HOURS        | TimeUnit.DAYS         || new BigDecimal(amount) / 24
        "0.237530"    | TimeUnit.DAYS         | TimeUnit.NANOSECONDS  || new BigDecimal(amount) * 1_000_000_000 * 60 * 60 * 24
        "653.095051"  | TimeUnit.DAYS         | TimeUnit.MICROSECONDS || new BigDecimal(amount) * 1_000_000 * 60 * 60 * 24
        "31.316865"   | TimeUnit.DAYS         | TimeUnit.MILLISECONDS || new BigDecimal(amount) * 1_000 * 60 * 60 * 24
        "66.350011"   | TimeUnit.DAYS         | TimeUnit.SECONDS      || new BigDecimal(amount) * 60 * 60 * 24
        "2030.887603" | TimeUnit.DAYS         | TimeUnit.MINUTES      || new BigDecimal(amount) * 60 * 24
        "372.505049"  | TimeUnit.DAYS         | TimeUnit.HOURS        || new BigDecimal(amount) * 24
        "74.652235"   | TimeUnit.DAYS         | TimeUnit.DAYS         || new BigDecimal(amount)
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

    def "Gets the percentage of task"() {
        given:
        def task = new Task("taskName", 0, elapsedNanoTime as long)

        when:
        def percentage = task.getPercentage(totalTime as BigDecimal, timeUnit)

        then:
        percentage == expected

        where:
        elapsedNanoTime | totalTime | timeUnit              || expected
        998             | 1000      | TimeUnit.NANOSECONDS  || 99.8
        3.14E+3         | 100       | TimeUnit.MICROSECONDS || 3.14
        1.4142135E+6    | 10        | TimeUnit.MILLISECONDS || 14.14213
        1.25E+6         | 1         | TimeUnit.SECONDS      || 0.125
        2.449489E+9     | 0.1       | TimeUnit.MINUTES      || 40.82481667
        3.6E+10         | 0.01      | TimeUnit.HOURS        || 100
        0               | 0         | TimeUnit.DAYS         || 0
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "Converts task into string"() {
        given:
        def taskName = "task-$i: ${new RandomString().nextString(8)}"
        def taskTime = new Random().nextInt(1024)
        def task = new Task(taskName, i, taskTime)

        when:
        def string = task.toString()

        then:
        string == "Task(name=${task.name}, order=${task.order}, elapsedNanoTime=${task.elapsedNanoTime})"

        where:
        i << (0..10)
    }

}
