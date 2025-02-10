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

import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path

import org.junit.jupiter.api.extension.FileSystemSource
import org.junit.jupiter.api.extension.Memory

import io.github.imsejin.common.internal.TestFileSystemCreator
import io.github.imsejin.common.io.FileResource
import io.github.imsejin.common.tool.RandomString
import io.github.imsejin.common.util.FilenameUtils

@FileSystemSource
class FileResourceFinderSpec extends Specification {

    def "Gets resources non-recursively on local file system"(FileSystem fileSystem) {
        given:
        def path = fileSystem.getPath(".")

        when:
        def finder = new FileResourceFinder(false)
        def resources = finder.getResources(path)

        then: "Consist of FileResource"
        !resources.empty
        resources.every { it instanceof FileResource }

        and: "Include only one root directory"
        resources.count { it == new FileResource(path) } == 1
    }

    def "Gets resource non-recursively"(@Memory FileSystem fileSystem) {
        given:
        def path = fileSystem.getPath("/", "$baseName.$extension")
        Files.createFile(path)
        Files.writeString(path, new RandomString().nextString(length))

        when:
        def finder = new FileResourceFinder(false)
        def resources = finder.getResources(path)

        then: "Found a resource that is file"
        resources.size() == 1

        and: "Make sure that it can tell if resource is a directory"
        def file = resources[0]
        !file.directory

        and: "Make sure that it can tell if resource is a file"
        file.size == length
        file.name == "$baseName.$extension"

        where:
        baseName | extension | length
        "foo"    | "log"     | 32
        "bar"    | "txt"     | 64
        "qux"    | "tmp"     | 128
    }

    def "Gets resources non-recursively"(@Memory FileSystem fileSystem) {
        given:
        def path = fileSystem.getPath("/")
        TestFileSystemCreator.builder()
                .minimumFileCount(fileCount)
                .maximumFileCount(fileCount)
                .minimumDirectoryCount(directoryCount)
                .maximumDirectoryCount(directoryCount)
                .minimumFileLength(32)
                .maximumFileLength(128)
                .fileSuffixes(".log", ".txt", ".tmp", ".dat")
                .build()
                .create(path)

        when:
        def finder = new FileResourceFinder(false)
        def resources = finder.getResources(path)

        then: "Found resources that are in one depth (including root)"
        resources.size() == fileCount + directoryCount + 1

        and: "Make sure that it can tell if resource is a directory"
        def directories = resources.findAll { it.directory }
        directories.size() == directoryCount + 1

        and: "Make sure that it can tell if resource is a file"
        def files = resources.findAll { !it.directory }
        files.size() == fileCount
        files.every { FilenameUtils.getExtension(it.name) =~ /log|txt|tmp|dat/ }

        where:
        fileCount | directoryCount
        0         | 0
        16        | 0
        0         | 16
        64        | 8
        8         | 64
    }

    def "Gets resources recursively"(@Memory FileSystem fileSystem) {
        given:
        def path = fileSystem.getPath("/")
        TestFileSystemCreator.builder()
                .minimumFileCount(fileCount)
                .maximumFileCount(fileCount)
                .minimumDirectoryCount(directoryCount)
                .maximumDirectoryCount(directoryCount)
                .minimumFileLength(32)
                .maximumFileLength(128)
                .fileSuffixes(".log", ".txt", ".tmp", ".dat")
                .build()
                .create(path)

        when:
        def finder = new FileResourceFinder(true)
        def resources = finder.getResources(path)

        then: "Found resources are in all depths (including root)"
        resources.size() == fileCount + directoryCount + 1 + (fileCount * directoryCount)

        and: "Make sure that it can tell if resource is a directory"
        def directories = resources.findAll { it.directory }
        directories.size() == directoryCount + 1

        and: "Make sure that it can tell if resource is a file"
        def files = resources.findAll { !it.directory }
        files.size() == fileCount + (fileCount * directoryCount)
        files.every { FilenameUtils.getExtension(it.name) =~ /log|txt|tmp|dat/ }

        where:
        fileCount | directoryCount
        0         | 0
        16        | 0
        0         | 16
        64        | 8
        8         | 64
    }

    def "Gets resources recursively with custom filter"(@Memory FileSystem fileSystem) {
        given:
        def fileCount = 16
        def directoryCount = 16
        def path = fileSystem.getPath("/")
        TestFileSystemCreator.builder()
                .minimumFileCount(fileCount)
                .maximumFileCount(fileCount)
                .minimumDirectoryCount(directoryCount)
                .maximumDirectoryCount(directoryCount)
                .minimumFileLength(32)
                .maximumFileLength(128)
                .fileSuffixes(".log", ".txt", ".tmp", ".dat")
                .build()
                .create(path)

        when:
        def finder = new FileResourceFinder(true, filter)
        def resources = finder.getResources(path)

        then:
        resources.size() == expectedFileCount + expectedDirectoryCount

        and: "Make sure that it can tell if resource is a directory"
        def directories = resources.findAll { it.directory }
        directories.size() == expectedDirectoryCount

        and: "Make sure that it can tell if resource is a file"
        def files = resources.findAll { !it.directory }
        files.size() == expectedFileCount

        where:
        filter                                                             || expectedFileCount | expectedDirectoryCount
        ({ false })                                                        || 0                 | 0
        ({ true })                                                         || 272               | 17
        ({ Path it -> Files.isDirectory(it) })                             || 0                 | 17
        ({ Path it -> !Files.isDirectory(it) })                            || 272               | 0
        ({ Path it -> !it.toString().matches(~/.+\.(log|txt|tmp|dat)$/) }) || 0                 | 17
    }

}
