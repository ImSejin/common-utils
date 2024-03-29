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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;
import org.junit.jupiter.api.io.TempDir;

import io.github.imsejin.common.internal.TestFileSystemCreator;
import io.github.imsejin.common.internal.TestFileSystemCreator.PathType;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

@FileSystemSource
@DisplayName("FileUtils")
class FileUtilsTest {

    @Nested
    @DisplayName("method 'getFileAttributes'")
    class GetFileAttributes {
        @Test
        @DisplayName("succeeds in getting attributes of file")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            List<Path> randomFilePaths = TestFileSystemCreator.builder()
                    .minimumFileCount(1)
                    .maximumFileCount(10)
                    .minimumFileLength(128)
                    .maximumFileLength(1024)
                    .fileSuffixes(".alp", ".bet", ".gam", ".del")
                    .build().create(path).get(PathType.FILE);

            // when
            List<BasicFileAttributes> attributes = Files.list(path)
                    .map(FileUtils::getFileAttributes).collect(toList());

            // then
            assertThat(path).hasNoParent();
            assertThat(attributes)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSameSizeAs(randomFilePaths)
                    .doesNotContainNull()
                    .doesNotHaveDuplicates()
                    .allMatch(BasicFileAttributes::isRegularFile)
                    .allMatch(attribute -> attribute.size() > 127)
                    .allMatch(attribute -> attribute.creationTime().toMillis() <= System.currentTimeMillis());
        }

        @Test
        @DisplayName("fails to get attributes of non-existent file")
        void test1(@Memory FileSystem fileSystem) {
            // given
            Path path = fileSystem.getPath("/", "temp-file.txt");

            // expect
            assertThatRuntimeException()
                    .isThrownBy(() -> FileUtils.getFileAttributes(path))
                    .withCauseExactlyInstanceOf(NoSuchFileException.class)
                    .withMessage(path.toString());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'download'")
    class Download {
        @Test
        @DisplayName("succeeds in downloading data in input stream as a file")
        void test0(@TempDir Path tempPath) throws IOException {
            // given
            Map<PathType, List<Path>> pathTypeMap = TestFileSystemCreator.builder()
                    .minimumFileCount(1)
                    .maximumFileCount(1)
                    .minimumFileLength(128)
                    .maximumFileLength(512)
                    .build().create(tempPath);
            Path filePath = pathTypeMap.get(PathType.FILE).get(0);

            // when
            URL url = filePath.toUri().toURL();
            Path dest = tempPath.resolve(filePath.getFileName().toString() + ".bak");
            FileUtils.download(url, dest);

            // then
            assertThat(dest)
                    .isNotNull()
                    .exists()
                    .isNotEmptyFile()
                    .hasSameBinaryContentAs(filePath);
        }

        @Test
        @DisplayName("fails to download data with invalid URL")
        void test1() throws MalformedURLException {
            // given
            Path path = Paths.get(UUID.randomUUID().toString()).toAbsolutePath();
            URL url = path.toUri().toURL();

            // expect
            assertThatRuntimeException()
                    .isThrownBy(() -> FileUtils.download(url, null))
                    .withCauseExactlyInstanceOf(FileNotFoundException.class)
                    .withMessageStartingWith(path.toString());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'deleteRecursively'")
    class DeleteRecursively {
        @RepeatedTest(10)
        @DisplayName("succeeds in deleting files and directories in recursive way")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            TestFileSystemCreator.builder()
                    .minimumFileCount(2)
                    .maximumFileCount(10)
                    .minimumDirectoryCount(2)
                    .maximumDirectoryCount(10)
                    .minimumFileLength(128)
                    .maximumFileLength(512)
                    .filePrefixes("alpha-", "beta-", "gamma-", "delta-")
                    .build().create(path);

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

        @Test
        @DisplayName("fails to delete non-existent directory in recursive way")
        void test1(@Memory FileSystem fileSystem) {
            // given
            Path path = fileSystem.getPath("/", "temp");

            // expect
            assertThatRuntimeException()
                    .isThrownBy(() -> FileUtils.deleteRecursively(path))
                    .withCauseExactlyInstanceOf(NoSuchFileException.class)
                    .withMessage(path.toString());
        }

        @Test
        @DisplayName("fails to delete directory that has file in common way")
        void test2(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Map<PathType, List<Path>> pathTypeMap = TestFileSystemCreator.builder()
                    .minimumFileCount(1)
                    .maximumFileCount(1)
                    .minimumDirectoryCount(1)
                    .maximumDirectoryCount(1)
                    .minimumFileLength(128)
                    .maximumFileLength(512)
                    .filePrefixes("alpha-", "beta-", "gamma-", "delta-")
                    .build().create(path);

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
                    .hasSameSizeAs(pathTypeMap.entrySet().stream()
                            .filter(entry -> entry.getKey() == PathType.DIRECTORY)
                            .flatMap(entry -> entry.getValue().stream())
                            .filter(p -> !p.equals(path)).toArray());
        }
    }

}
