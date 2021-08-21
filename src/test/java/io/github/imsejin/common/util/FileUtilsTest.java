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

package io.github.imsejin.common.util;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FileUtilsTest {

    @RepeatedTest(10)
    void deleteRecursively(@TempDir Path path) throws IOException {
        // given
        Path dir = Files.createTempDirectory(path, "temp-");
        Files.createTempDirectory(dir, "outer-empty-dir-");
        Files.createTempFile(dir, "outer-file-", null);
        Path outerDir = Files.createTempDirectory(dir, "outer-filled-dir-");
        Files.createTempDirectory(outerDir, "inner-empty-dir-");
        Files.createTempFile(outerDir, "inner-file-", null);

        Files.walk(dir).map(it -> it.toString().replace(path + File.separator, ""))
                .forEach(it -> System.out.printf("%s%n", it));
        System.out.println();
        assertThatExceptionOfType(DirectoryNotEmptyException.class)
                .isThrownBy(() -> Files.delete(dir)).withMessage(dir.toString());
        assertThat(Files.walk(dir))
                .as("Cannot delete not empty directory, as well as all its files and directories")
                .map(Path::toFile).allMatch(File::exists);

        // when
        FileUtils.deleteRecursively(dir);

        // then
        assertThat(Files.notExists(dir))
                .as("Directory is deleted even if not empty")
                .isTrue();
    }

}
