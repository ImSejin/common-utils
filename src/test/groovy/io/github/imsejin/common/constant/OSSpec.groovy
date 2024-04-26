/*
 * Copyright 2024 Sejin Im
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

package io.github.imsejin.common.constant

import spock.lang.Requires
import spock.lang.Specification
import spock.util.environment.OperatingSystem

class OSSpec extends Specification {

    @Requires({ OperatingSystem.current.linux })
    def "Checks if current os is linux"() {
        expect:
        OS.getCurrentOS() == OS.LINUX
        OS.LINUX.isCurrentOS()
        OS.values().findAll { it != OS.LINUX }.every { !it.isCurrentOS() }
    }

    @Requires({ OperatingSystem.current.macOs })
    def "Checks if current os is macos"() {
        expect:
        OS.getCurrentOS() == OS.MAC
        OS.MAC.isCurrentOS()
        OS.values().findAll { it != OS.MAC }.every { !it.isCurrentOS() }
    }

    @Requires({ OperatingSystem.current.windows })
    def "Checks if current os is windows"() {
        expect:
        OS.getCurrentOS() == OS.WINDOWS
        OS.WINDOWS.isCurrentOS()
        OS.values().findAll { it != OS.WINDOWS }.every { !it.isCurrentOS() }
    }

    @Requires({ OperatingSystem.current.solaris })
    def "Checks if current os is solaris"() {
        expect:
        OS.getCurrentOS() == OS.SOLARIS
        OS.SOLARIS.isCurrentOS()
        OS.values().findAll { it != OS.SOLARIS }.every { !it.isCurrentOS() }
    }

    @Requires({ OperatingSystem.current.other })
    def "Checks if current os is unknown one"() {
        expect:
        OS.getCurrentOS() == OS.OTHER
        OS.OTHER.isCurrentOS()
        OS.values().findAll { it != OS.OTHER }.every { !it.isCurrentOS() }
    }

}
