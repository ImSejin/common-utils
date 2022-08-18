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

}
