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

class PlatformSpec extends Specification {

    def "Returns the current platform by OS name and architecture"() {
        given:
        SpyStatic(Platform)
        Platform.currentOperatingSystem >> { os.toLowerCase(Locale.US) }
        Platform.currentArchitecture >> { arch.toLowerCase(Locale.US) }
        Platform.translatedByRosetta >> { false }

        when:
        def current = Platform.currentPlatform

        then:
        current == exoected

        where:
        os           | arch      || exoected
        "Aix"        | "x86_x64" || Platform.AIX
        "SunOS"      | "x86"     || Platform.SOLARIS
        "Solaris OS" | "x86"     || Platform.SOLARIS
        "Unix"       | "x86_x64" || Platform.LINUX
        "Linux"      | "amd64"   || Platform.LINUX
        "Mac OS X"   | "aarch64" || Platform.MACOS_ARM64
        "Mac OS X"   | "arm64"   || Platform.MACOS_ARM64
        "Mac OS X"   | "x86_x64" || Platform.MACOS_X64
        "Darwin OS"  | "x86_x64" || Platform.MACOS_X64
        "Windows 10" | "amd64"   || Platform.WINDOWS
        ""           | ""        || Platform.UNKNOWN
    }

    @Requires({ os.linux })
    def "Returns the platform on linux"() {
        expect:
        Platform.LINUX.current
    }

    @Requires({ os.macOs })
    def "Returns the platform on macos"() {
        expect:
        Platform.MACOS_ARM64.current || Platform.MACOS_X64.current
    }

    @Requires({ os.windows })
    def "Returns the platform on windows"() {
        expect:
        Platform.WINDOWS.current
    }

    def "Never return unknown platform on any os"() {
        expect:
        !Platform.UNKNOWN.current
    }

}
