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

import java.nio.charset.StandardCharsets
import java.nio.file.FileSystem
import java.nio.file.Files
import java.time.Instant

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.extension.FileSystemSource
import org.junit.jupiter.api.extension.Memory

import io.github.imsejin.common.tool.RandomString

@FileSystemSource
class FileResourceSpec extends Specification {

    def "When it is a file"(@Memory FileSystem fileSystem) {
        given:
        def filePath = fileSystem.getPath("/", "tmp")
        Files.createDirectory(filePath)

        and:
        filePath = filePath.resolve("temp-text.txt")
        Files.createFile(filePath)

        and:
        def bytes = new RandomString().nextString(1, 1024).getBytes(StandardCharsets.UTF_8)
        Files.write(filePath, bytes)

        when:
        def resource = new FileResource(filePath)

        then:
        resource.path == filePath.toString()
        resource.name == filePath.fileName.toString()
        resource.path.endsWith(resource.name)
        resource.lastModifiedTime > Instant.EPOCH
        resource.size == bytes.length
        resource.realPath == filePath

        and: "It returns new InputStream for each invocation"
        3.times {
            assert IOUtils.readFully(resource.inputStream, resource.size as int) == bytes
        }

        and: "Check equals and hashCode"
        def other = new FileResource(filePath)
        resource == other
        [resource, other].toSet().size() == 1

        and: "Check toString"
        resource.toString() =~ /^FileResource\(([a-zA-Z]+=.+)+\)$/
    }

    def "When it is a directory"(@Memory FileSystem fileSystem) {
        given:
        def filePath = fileSystem.getPath("/")

        when:
        def resource = new FileResource(filePath)

        then:
        resource.inputStream == null
        resource.size == -1
    }

}
