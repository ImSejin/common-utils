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

package io.github.imsejin.common.io.archive

import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.nio.file.Path

import org.apache.commons.io.IOUtils

import io.github.imsejin.common.io.TarResource
import io.github.imsejin.common.io.finder.ArchiveResourceFinder

class TarArchiveResourceReaderSpec extends Specification {

    def "Reads resources in tar archive"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/gzip/$fileName").toURI())

        when:
        def reader = new TarArchiveResourceReader(new GzipArchiveResourceReader())
        def finder = new ArchiveResourceFinder(reader)
        def resources = finder.getResources(path)

        then: "Consist of TarResource"
        !resources.empty
        resources.every { it instanceof TarResource }

        and:
        resources.every { it.path.endsWith("${it.name}${it.directory ? '/' : ''}") }

        and: "Make sure that it can tell if resource is a file"
        def files = resources.findAll { !it.directory }
        files.every { IOUtils.readFully(it.inputStream, it.size as int).length == it.size }
        files.count { it.path.endsWith(".$extension") } == fileCount

        where:
        fileName                | extension | fileCount
        // tar.gz
        "macos-14.4.1.tar.gz"   | "tar"     | 0
        "ubuntu-18.04.1.tar.gz" | "tar"     | 1
        "windows10-pro.tar.gz"  | "xfdl"    | 221
        // tgz
        "macos-14.4.1.tgz"      | "c"       | 373
        "ubuntu-18.04.1.tgz"    | "c"       | 373
        "windows10-pro.tgz"     | "js"      | 80
    }

    def "Reads resources in tar archive with filter"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/gzip/$fileName").toURI())

        when:
        def reader = new TarArchiveResourceReader(new GzipArchiveResourceReader(), { false }, StandardCharsets.UTF_8)
        def finder = new ArchiveResourceFinder(reader)
        def resources = finder.getResources(path)

        then: "Consist of TarResource"
        resources.empty

        where:
        fileName << [
                // tar.gz,
                "macos-14.4.1.tar.gz",
                "ubuntu-18.04.1.tar.gz",
                "windows10-pro.tar.gz",
                // tgz,
                "macos-14.4.1.tgz",
                "ubuntu-18.04.1.tgz",
                "windows10-pro.tgz",
        ]
    }

}
