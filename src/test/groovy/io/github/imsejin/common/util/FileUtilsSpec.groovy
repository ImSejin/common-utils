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

import java.nio.file.Files
import java.nio.file.Path

class FileUtilsSpec extends Specification {

    @TempDir
    private Path tempPath

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

}
