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

import io.github.imsejin.common.io.ZipResource

class ZipResourceFinderSpec extends Specification {

    def "Gets resources"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/zip/$fileName").toURI())

        when:
        def resourceFinder = new ZipResourceFinder(false)
        def resources = resourceFinder.getResources(path)

        then: "Consist of ZipResource"
        !resources.empty
        resources.every { it instanceof ZipResource }

        and:
        resources.every { it.path.endsWith("${it.name}${it.directory ? '/' : ''}") }

        and: "Make sure that it can tell if resource is a file"
        def files = resources.findAll { !it.directory }
        files.every { IOUtils.readFully(it.inputStream, it.size as int).length == it.size }
        files.count { it.path.endsWith(".$extenstion") } == fileCount

        where:
        fileName             | extenstion | fileCount
        "macos-14.4.1.zip"   | "java"     | 64
        "ubuntu-18.04.3.zip" | "java"     | 64
        "windows10-pro.zip"  | "json"     | 548
    }

}
