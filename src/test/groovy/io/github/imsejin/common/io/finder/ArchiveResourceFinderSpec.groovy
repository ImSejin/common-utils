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

import io.github.imsejin.common.io.archive.GzipArchiveResourceReader
import io.github.imsejin.common.io.archive.TarArchiveResourceReader
import io.github.imsejin.common.io.archive.ZipArchiveResourceReader

class ArchiveResourceFinderSpec extends Specification {

    def "Gets resources by archive reader"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/$filePath").toURI())

        when:
        def finder = new ArchiveResourceFinder(reader)
        def resources = finder.getResources(path)

        then:
        !resources.empty

        where:
        filePath                     | reader
        // gzip
        "gzip/macos-14.4.1.gz"       | new GzipArchiveResourceReader()
        "gzip/ubuntu-18.04.1.gz"     | new GzipArchiveResourceReader()
        "gzip/windows10-pro.gz"      | new GzipArchiveResourceReader()
        "gzip/macos-14.4.1.tar.gz"   | new GzipArchiveResourceReader()
        "gzip/ubuntu-18.04.1.tar.gz" | new GzipArchiveResourceReader()
        "gzip/windows10-pro.tar.gz"  | new GzipArchiveResourceReader()
        // tar + gzip
        "gzip/macos-14.4.1.tar.gz"   | new TarArchiveResourceReader(new GzipArchiveResourceReader())
        "gzip/ubuntu-18.04.1.tar.gz" | new TarArchiveResourceReader(new GzipArchiveResourceReader())
        "gzip/windows10-pro.tar.gz"  | new TarArchiveResourceReader(new GzipArchiveResourceReader())
        "gzip/macos-14.4.1.tgz"      | new TarArchiveResourceReader(new GzipArchiveResourceReader())
        "gzip/ubuntu-18.04.1.tgz"    | new TarArchiveResourceReader(new GzipArchiveResourceReader())
        "gzip/windows10-pro.tgz"     | new TarArchiveResourceReader(new GzipArchiveResourceReader())
        // zip
        "zip/macos-14.4.1.zip"       | new ZipArchiveResourceReader()
        "zip/ubuntu-18.04.3.zip"     | new ZipArchiveResourceReader()
        "zip/windows10-pro.zip"      | new ZipArchiveResourceReader()
    }

    def "Fails to get resources by archive reader"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/$filePath").toURI())

        when:
        def finder = new ArchiveResourceFinder(reader)
        finder.getResources(path)

        then:
        def e = thrown(IllegalStateException)
        e.message == "Failed to read archive file: $path"
        e.cause instanceof IOException

        where:
        filePath                     | reader
        // gzip
        "gzip/macos-14.4.1.gz"       | new TarArchiveResourceReader()
        "gzip/ubuntu-18.04.1.gz"     | new ZipArchiveResourceReader()
        // tar + gzip
        "gzip/macos-14.4.1.tar.gz"   | new TarArchiveResourceReader()
        "gzip/ubuntu-18.04.1.tar.gz" | new TarArchiveResourceReader(new ZipArchiveResourceReader())
        "gzip/windows10-pro.tar.gz"  | new ZipArchiveResourceReader()
        // zip
        "zip/macos-14.4.1.zip"       | new TarArchiveResourceReader()
        "zip/ubuntu-18.04.3.zip"     | new GzipArchiveResourceReader()
        "zip/windows10-pro.zip"      | new TarArchiveResourceReader(new GzipArchiveResourceReader())
    }

}
