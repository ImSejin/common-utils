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

package io.github.imsejin.common.security

import spock.lang.Specification

import java.time.Duration

class VerificationSpec extends Specification {

    def "instantiate"() {
        when:
        new Verification(credentials, duration)

        then:
        def e = thrown IllegalArgumentException
        e.message.startsWith(message)

        where:
        credentials | duration                      || message
        null        | Duration.ofMillis(500)        || "Verification.credentials"
        "02b23a"    | null                          || "Verification.duration"
        0x4a232f    | Duration.ZERO.minusSeconds(1) || "Verification.duration"
    }

    def "verify"() {
        given:
        def credentials = "873481"
        def milliseconds = 500
        def duration = Duration.ofMillis milliseconds

        when: "Create a instance of Verification"
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
