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

import java.nio.file.Path
import java.time.Instant

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry

import io.github.imsejin.common.util.FilenameUtils

class ZipResourceSpec extends Specification {

    def "When it is a file"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/zip/$fileName").toURI())

        and: "Set ZipArchiveEntry.size manually instead of ZipArchiveInputStream"
        def entry = new ZipArchiveEntry(path, entryName)
        entry.size = size

        when:
        def resource = new ZipResource(entry, new byte[0])

        then:
        resource.path == entryName
        resource.name == FilenameUtils.getName(entryName)
        resource.lastModifiedTime > Instant.EPOCH
        !resource.directory
        resource.size == size

        and: "Check equals and hashCode"
        def other = new ZipResource(entry, new byte[0])
        resource == other
        [resource, other].toSet().size() == 1

        and: "Check toString"
        resource.toString() =~ /^ZipResource\(([a-zA-Z]+=.+)+\)$/

        where:
        fileName             | entryName                | size
        "macos-14.4.1.zip"   | "README.txt"             | 4279
        "ubuntu-18.04.3.zip" | "images/box.png"         | 6791
        "windows10-pro.zip"  | "icons/css/property.svg" | 696
    }

    def "When it is a directory"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/zip/$fileName").toURI())

        and: "Set ZipArchiveEntry.size manually instead of ZipArchiveInputStream"
        def entry = new ZipArchiveEntry(path, entryName)
        entry.size = 0

        when:
        def resource = new ZipResource(entry, new byte[0])

        then:
        resource.path == entryName
        resource.name == FilenameUtils.getName(entryName)
        resource.lastModifiedTime > Instant.EPOCH
        resource.directory
        resource.size == 0

        and: "Check equals and hashCode"
        def other = new ZipResource(entry, new byte[0])
        resource == other
        [resource, other].toSet().size() == 1

        and: "Check toString"
        resource.toString() =~ /^ZipResource\(([a-zA-Z]+=.+)+\)$/

        where:
        fileName             | entryName
        "macos-14.4.1.zip"   | "/"
        "ubuntu-18.04.3.zip" | "images/"
        "windows10-pro.zip"  | "java2d/demos/Fonts/"
    }

}
