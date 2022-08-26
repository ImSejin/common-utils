/*
 * Copyright 2022 Sejin Im
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

import io.github.imsejin.common.internal.TestFileSystemCreator;
import io.github.imsejin.common.internal.TestFileSystemCreator.PathType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;

@FileSystemSource
@DisplayName("FileUtils")
class FileUtilsTest {

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'deleteRecursively'")
    class DeleteRecursively {
        @RepeatedTest(10)
        @DisplayName("passes")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Map<PathType, List<Path>> pathMap = TestFileSystemCreator.createRandomEnvironment(
                    path, Arrays.asList("alpha-", "beta-", "gamma-", "delta-"), null);

            // when
            Path[] paths = Files.list(path).toArray(Path[]::new);
            for (Path p : paths) {
                FileUtils.deleteRecursively(p);
            }

            // then
            assertThat(Files.list(path))
                    .isNotNull()
                    .isEmpty();
        }

        @RepeatedTest(10)
        @DisplayName("failed")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Map<PathType, List<Path>> pathMap = TestFileSystemCreator.createRandomEnvironment(
                    path, Arrays.asList("alpha-", "beta-", "gamma-", "delta-"), null);

            // when
            assertThatIOException().isThrownBy(() -> {
                Path[] paths = Files.list(path).sorted(comparing(Files::isDirectory)).toArray(Path[]::new);
                for (Path p : paths) {
                    Files.delete(p);
                }
            }).isExactlyInstanceOf(DirectoryNotEmptyException.class);

            // then
            assertThat(Files.list(path))
                    .isNotNull()
                    .isNotEmpty()
                    .hasSameSizeAs(pathMap.entrySet().stream()
                            .filter(entry -> entry.getKey() == PathType.DIRECTORY)
                            .flatMap(entry -> entry.getValue().stream())
                            .filter(p -> !p.equals(path)).toArray());
        }
    }

}
