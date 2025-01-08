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
        Platform.currentOperatingSystem >> { os.toLowerCase(Locale.ROOT) }
        Platform.currentArchitecture >> { arch.toLowerCase(Locale.ROOT) }
        Platform.supportRosetta() >> { false }

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

    def "Gets name of operating system"() {
        given:
        System.setProperty("os.name", os)

        expect:
        Platform.currentOperatingSystem == expected

        cleanup:
        System.clearProperty("os.name")

        where:
        os           | expected
        ""           | ""
        " "          | ""
        "Aix"        | "aix"
        "SunOS"      | "sunos"
        "Solaris OS" | "solaris os"
        "Unix"       | "unix"
        "Linux"      | "linux"
        "Mac OS X"   | "mac os x"
        "Darwin OS"  | "darwin os"
        "Windows 10" | "windows 10"
    }

    def "Gets architecture"() {
        given:
        System.setProperty("os.arch", arch)

        expect:
        Platform.currentArchitecture == expected

        cleanup:
        System.clearProperty("os.arch")

        where:
        arch      | expected
        ""        | ""
        " "       | ""
        "x86_x64" | "x86_x64"
        "x86"     | "x86"
        "X86"     | "x86"
        "amd64"   | "amd64"
        "Amd64"   | "amd64"
        "AMD64"   | "amd64"
        "aarch64" | "aarch64"
        "AArch64" | "aarch64"
        "arm64"   | "arm64"
        "ARM64"   | "arm64"
    }

    def "Whether rosetta is supported through command result"() {
        given:
        SpyStatic(Runtime)
        Runtime.runtime >> {
            def process = Mock(Process)
            process.inputStream >> { new ByteArrayInputStream(result.bytes) }
            def runtime = Mock(Runtime)
            runtime.exec(new String[] {"/usr/sbin/sysctl", "sysctl.proc_translated"}) >> { process }

            runtime
        }

        when:
        def supported = Platform.supportRosetta()

        then:
        supported == expected

        where:
        result                                 | expected
        ""                                     | false
        "unknown old 'sysctl.proc_translated'" | false
        "sysctl.proc_translated: 0"            | true
        "sysctl.proc_translated: 1"            | true
    }

}
