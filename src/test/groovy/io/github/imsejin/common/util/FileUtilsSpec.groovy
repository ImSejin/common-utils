/*
 * Copyright 2021 Sejin Im
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

package io.github.imsejin.common.util

import spock.lang.Specification
import spock.lang.TempDir
import spock.lang.Unroll

import java.nio.charset.StandardCharsets
import java.nio.file.DirectoryNotEmptyException
import java.nio.file.Files
import java.nio.file.Path

class FileUtilsSpec extends Specification {

    @TempDir
    private Path tempPath

    def "Download a file with URL"() {
        given:
        def content = "Lorem ipsum dolor sit amet".getBytes(StandardCharsets.UTF_8)
        def path = Files.createTempFile(tempPath, "temp-file-", ".txt").toRealPath()
        Files.write(path, content)
        def dest = tempPath.resolve("temp-file-downloaded.txt")

        when: "Tries to download with valid URL."
        def validUrl = path.toUri().toURL()
        FileUtils.download(validUrl, dest)

        then: "Successfully downloaded with valid URL."
        content == Files.readAllBytes(dest)

        when: "Tries to download with invalid URL."
        def invalidUrl = path.resolve(UUID.randomUUID().toString()).toUri().toURL()
        FileUtils.download(invalidUrl, dest)

        then: "Failed to download with invalid URL."
        thrown RuntimeException
    }

    def "Finds all files in the path"() {
        given:
        def count = 10
        for (i in 0..<count) {
            Files.createTempFile(tempPath, "temp-file-", null)
        }

        when:
        def files = FileUtils.findAllFiles tempPath

        then:
        files.size() == count
    }

    @Unroll("Deletes a directory recursively: repeated #i time(s)")
    def "Deletes a directory recursively"() {
        given:
        Path dir = Files.createTempDirectory(tempPath, "temp-")
        Files.createTempDirectory(dir, "outer-empty-dir-")
        Files.createTempFile(dir, "outer-file-", null)
        Path outerDir = Files.createTempDirectory(dir, "outer-filled-dir-")
        Files.createTempDirectory(outerDir, "inner-empty-dir-")
        Files.createTempFile(outerDir, "inner-file-", null)

        Files.walk(dir).map(it -> it.toString().replace("$tempPath$File.separator", ""))
                .forEach(it -> printf("%s%n", it))
        println()

        when:
        Files.delete dir

        then: "Cannot delete not empty directory, as well as all its files and directories."
        thrown DirectoryNotEmptyException
        Files.walk(dir).map(Path::toFile).allMatch(File::exists)

        when:
        FileUtils.deleteRecursively dir

        then: "Directory is deleted even if not empty."
        Files.notExists dir

        where:
        i << (1..10)
    }

}
