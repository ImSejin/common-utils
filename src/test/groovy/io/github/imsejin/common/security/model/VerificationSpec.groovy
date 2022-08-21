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

package io.github.imsejin.common.security.model

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

class VerificationSpec extends Specification {

    def "Instantiates"() {
        given:
        def credentials = UUID.randomUUID()
        def duration = Duration.ofSeconds(5)

        when:
        def verification = new Verification(credentials, duration)

        then:
        verification.credentials == credentials
        verification.duration == duration
        verification.createdDateTime <= LocalDateTime.now()
    }

    def "Failed to instantiate"() {
        when:
        new Verification(credentials, duration)

        then:
        def e = thrown IllegalArgumentException
        e.message == message

        where:
        credentials | duration                      || message
        null        | Duration.ofMillis(500)        || "Verification.credentials is allowed to be null"
        "02b23a"    | null                          || "Verification.duration is allowed to be null"
        0x4a232f    | Duration.ZERO.minusSeconds(1) || "Verification.duration must be zero or positive, but it is not: '$duration'"
    }

    def "Verifies"() {
        given:
        def credentials = "873481"
        def milliseconds = 500
        def duration = Duration.ofMillis milliseconds

        when: "Create an instance of Verification"
        def verification = new Verification(credentials, duration)

        then: """
            1. Verify credentials within the duration
            2. Failed to verify with null as credentials
            3. Failed to verify with bad credentials
        """
        verification.verify credentials
        !verification.verify(null)
        !verification.verify(873481)

        when: "Expire duration of verification"
        sleep(milliseconds * 2)

        then: """
            1. Failed to verify credentials out of the duration
            2. Still failed to verify with null as credentials
            3. Still failed to verify with bad credentials
        """
        !verification.verify(credentials)
        !verification.verify(null)
        !verification.verify(873481)
    }

}
