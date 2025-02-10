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

package io.github.imsejin.common.io.finder

import spock.lang.Specification

import java.nio.file.Path

import org.apache.commons.io.IOUtils

import io.github.imsejin.common.io.GzipResource
import io.github.imsejin.common.io.TarResource
import io.github.imsejin.common.io.ZipResource
import io.github.imsejin.common.io.archive.GzipArchiveResourceReader
import io.github.imsejin.common.io.archive.TarArchiveResourceReader
import io.github.imsejin.common.io.archive.ZipArchiveResourceReader

class ArchiveResourceFinderSpec extends Specification {

    def "Gets resource in gzip archive"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/gzip/$fileName").toURI())

        when:
        def reader = new GzipArchiveResourceReader()
        def finder = new ArchiveResourceFinder(reader)
        def resources = finder.getResources(path)

        then: "Consist of GzipResource"
        !resources.empty
        resources.every { it instanceof GzipResource }

        and: "Make sure that it has only one file"
        resources.size() == 1
        resources.every { !it.directory }

        and:
        def file = resources[0]
        file.name == resourceName
        IOUtils.readFully(file.inputStream, file.size as int).length == file.size

        where:
        fileName                | resourceName
        // gz
        "macos-14.4.1.gz"       | "catalina.out-20210123"
        "ubuntu-18.04.1.gz"     | "catalina.out-20210123"
        "windows10-pro.gz"      | "windows10-pro"
        // tar.gz
        "macos-14.4.1.tar.gz"   | "putty-0.77.tar"
        "ubuntu-18.04.1.tar.gz" | "ubuntu-18.04.1.tar"
        "windows10-pro.tar.gz"  | "windows10-pro.tar"
    }

    def "Gets resources in tar archive"() {
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

    def "Gets resources in zip archive"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/zip/$fileName").toURI())

        when:
        def reader = new ZipArchiveResourceReader()
        def finder = new ArchiveResourceFinder(reader)
        def resources = finder.getResources(path)

        then: "Consist of ZipResource"
        !resources.empty
        resources.every { it instanceof ZipResource }

        and:
        resources.every { it.path.endsWith("${it.name}${it.directory ? '/' : ''}") }

        and: "Make sure that it can tell if resource is a file"
        def files = resources.findAll { !it.directory }
        files.every { IOUtils.readFully(it.inputStream, it.size as int).length == it.size }
        files.count { it.path.endsWith(".$extension") } == fileCount

        where:
        fileName             | extension | fileCount
        "macos-14.4.1.zip"   | "java"    | 64
        "ubuntu-18.04.3.zip" | "java"    | 64
        "windows10-pro.zip"  | "json"    | 548
    }

}
