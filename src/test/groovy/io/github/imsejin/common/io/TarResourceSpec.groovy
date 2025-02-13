/*
 * Copyright 2025 Sejin Im
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

package io.github.imsejin.common.io

import spock.lang.Specification

import java.time.Instant

import org.apache.commons.compress.archivers.tar.TarArchiveEntry

class TarResourceSpec extends Specification {

    def "When it is a file"() {
        given: "Set TarArchiveEntry.size manually instead of TarArchiveInputStream"
        def entry = new TarArchiveEntry(entryName)
        entry.size = size

        when:
        def resource = new TarResource(entry, new byte[0])

        then:
        resource.path == entryName
        resource.name == name
        resource.lastModifiedTime > Instant.EPOCH
        !resource.directory
        resource.size == size

        and: "Check equals and hashCode"
        def other = new TarResource(entry, new byte[0])
        resource == other
        [resource, other].toSet().size() == 1

        and: "Check toString"
        resource.toString() =~ /^TarResource\(([a-zA-Z]+=.+)+\)$/

        where:
        entryName                          || name             | size
        "README"                           || "README"         | 1619
        "config.c"                         || "config.c"       | 134885
        "charset/CMakeLists.txt"           || "CMakeLists.txt" | 873
        "nexacro14lib/framework/Device.js" || "Device.js"      | 21613
    }

    def "When it is a directory"() {
        given: "Set TarArchiveEntry.size manually instead of TarArchiveInputStream"
        def entry = new TarArchiveEntry(entryName)
        entry.size = 0

        when:
        def resource = new TarResource(entry, new byte[0])

        then:
        resource.path == entryName.replaceAll("^/", "")
        resource.name == name
        resource.lastModifiedTime > Instant.EPOCH
        resource.directory
        resource.size == 0

        and: "Check equals and hashCode"
        def other = new TarResource(entry, new byte[0])
        resource == other
        [resource, other].toSet().size() == 1

        and: "Check toString"
        resource.toString() =~ /^TarResource\(([a-zA-Z]+=.+)+\)$/

        where:
        entryName                  | name
        // Absolute
        "/charset/"                | "charset"
        "/doc/html/"               | "html"
        "/nexacro14lib/framework/" | "framework"
        // Relative
        "charset/"                 | "charset"
        "doc/html/"                | "html"
        "nexacro14lib/framework/"  | "framework"
    }

}
