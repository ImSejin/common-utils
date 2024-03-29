package io.github.imsejin.common.io.finder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;
import org.junit.jupiter.api.io.TempDir;

import io.github.imsejin.common.internal.TestFileSystemCreator;
import io.github.imsejin.common.internal.TestFileSystemCreator.PathType;
import io.github.imsejin.common.internal.TestUtils;
import io.github.imsejin.common.io.DiskFileResource;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.tool.RandomString;
import io.github.imsejin.common.util.FilenameUtils;

import static org.assertj.core.api.Assertions.*;

@FileSystemSource
@DisplayName("DiskFileResourceFinder")
class DiskFileResourceFinderTest {

    @Nested
    @DisplayName("when method 'getResources' is done successfully")
    class Success {
        @RepeatedTest(10)
        @DisplayName("gets resources non-recursively with default constructor on memory file system")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Map<PathType, List<Path>> pathTypeMap = TestFileSystemCreator.builder()
                    .minimumFileCount(1)
                    .maximumFileCount(10)
                    .minimumDirectoryCount(1)
                    .maximumDirectoryCount(10)
                    .minimumFileLength(512)
                    .maximumFileLength(4096)
                    .fileSuffixes(".log", ".txt", ".tmp", ".dat")
                    .build().create(path);

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(false);
            List<Resource> resources = resourceFinder.getResources(path);

            // then
            assertThat(resources)
                    .isNotNull()
                    .isNotEmpty()
                    .as("Contains only one root directory")
                    .containsOnlyOnce(DiskFileResource.from(path))
                    .doesNotHaveDuplicates()
                    .allMatch(it -> it instanceof DiskFileResource);
            assertThat(resources)
                    .filteredOn(Resource::isDirectory)
                    .doesNotContainNull()
                    .hasSameSizeAs(pathTypeMap.get(PathType.DIRECTORY))
                    .allMatch(it -> it.getPath().endsWith(it.getName()))
                    .allMatch(it -> it.getInputStream() == null)
                    .allMatch(it -> it.getSize() == -1);
            assertThat(resources)
                    .filteredOn(it -> !it.isDirectory())
                    .doesNotContainNull()
                    .hasSameSizeAs(pathTypeMap.get(PathType.FILE))
                    .allMatch(it -> it.getPath().endsWith(it.getName()))
                    .allMatch(it -> TestUtils.readAllBytes(it.getInputStream()).length == it.getSize())
                    .allMatch(it -> it.getSize() >= 0)
                    .allMatch(it -> FilenameUtils.getExtension(it.getName()).matches("log|txt|tmp|dat"));
        }

        @RepeatedTest(10)
        @DisplayName("gets resources recursively with default constructor on memory file system")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Map<PathType, List<Path>> pathTypeMap = TestFileSystemCreator.builder()
                    .minimumFileCount(1)
                    .maximumFileCount(10)
                    .minimumDirectoryCount(1)
                    .maximumDirectoryCount(10)
                    .minimumFileLength(512)
                    .maximumFileLength(4096)
                    .fileSuffixes(".log", ".txt", ".tmp", ".dat")
                    .build().create(path);

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(true);
            List<Resource> resources = resourceFinder.getResources(path);

            // then
            assertThat(resources)
                    .isNotNull()
                    .isNotEmpty()
                    .as("Contains only one root directory")
                    .containsOnlyOnce(DiskFileResource.from(path))
                    .doesNotHaveDuplicates()
                    .allMatch(it -> it instanceof DiskFileResource);
            assertThat(resources)
                    .filteredOn(Resource::isDirectory)
                    .doesNotContainNull()
                    .hasSameSizeAs(pathTypeMap.get(PathType.DIRECTORY))
                    .allMatch(it -> it.getPath().endsWith(it.getName()))
                    .allMatch(it -> it.getInputStream() == null)
                    .allMatch(it -> it.getSize() == -1);
            assertThat(resources)
                    .filteredOn(it -> !it.isDirectory())
                    .doesNotContainNull()
                    .hasSize(pathTypeMap.get(PathType.FILE).size() + pathTypeMap.get(PathType.FILE_IN_DIRECTORY).size())
                    .allMatch(it -> it.getPath().endsWith(it.getName()))
                    .allMatch(it -> TestUtils.readAllBytes(it.getInputStream()).length == it.getSize())
                    .allMatch(it -> it.getInputStream() != null)
                    .allMatch(it -> it.getSize() >= 0)
                    .allMatch(it -> FilenameUtils.getExtension(it.getName()).matches("log|txt|tmp|dat"));
        }

        @RepeatedTest(10)
        @DisplayName("gets resources non-recursively with another constructor on default file system")
        void test2(@TempDir Path path) throws IOException {
            // given
            Map<PathType, List<Path>> pathTypeMap = TestFileSystemCreator.builder()
                    .minimumFileCount(1)
                    .maximumFileCount(10)
                    .minimumDirectoryCount(1)
                    .maximumDirectoryCount(10)
                    .minimumFileLength(512)
                    .maximumFileLength(4096)
                    .fileSuffixes(".log", ".txt", ".tmp", ".dat")
                    .build().create(path);

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(false,
                    _path -> _path.toString().endsWith(".log") || _path.toString().endsWith(".txt"));
            List<Resource> resources = resourceFinder.getResources(path);

            // then
            assertThat(resources)
                    .isNotNull()
                    .doesNotContainNull()
                    .doesNotHaveDuplicates()
                    .allMatch(it -> it instanceof DiskFileResource)
                    .noneMatch(Resource::isDirectory)
                    .hasSameSizeAs(pathTypeMap.get(PathType.FILE).stream().map(Path::toString)
                            .filter(it -> it.endsWith(".log") || it.endsWith(".txt")).toArray())
                    .allMatch(it -> it.getPath().endsWith(it.getName()))
                    .allMatch(it -> TestUtils.readAllBytes(it.getInputStream()).length == it.getSize())
                    .allMatch(it -> it.getSize() >= 0)
                    .allMatch(it -> FilenameUtils.getExtension(it.getName()).matches("log|txt"));
        }

        @RepeatedTest(10)
        @DisplayName("gets resources recursively with another constructor on default file system")
        void test3(@TempDir Path path) throws IOException {
            // given
            Map<PathType, List<Path>> pathTypeMap = TestFileSystemCreator.builder()
                    .minimumFileCount(1)
                    .maximumFileCount(10)
                    .minimumDirectoryCount(1)
                    .maximumDirectoryCount(10)
                    .minimumFileLength(512)
                    .maximumFileLength(4096)
                    .fileSuffixes(".log", ".txt", ".tmp", ".dat")
                    .build().create(path);

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(true,
                    _path -> _path.toString().endsWith(".dat"));
            List<Resource> resources = resourceFinder.getResources(path);

            // then
            assertThat(resources)
                    .isNotNull()
                    .doesNotContainNull()
                    .doesNotHaveDuplicates()
                    .allMatch(it -> it instanceof DiskFileResource)
                    .noneMatch(Resource::isDirectory)
                    .hasSameSizeAs(Stream.concat(pathTypeMap.get(PathType.FILE).stream(),
                                    pathTypeMap.get(PathType.FILE_IN_DIRECTORY).stream())
                            .map(Path::toString).filter(it -> it.endsWith(".dat")).toArray())
                    .allMatch(it -> it.getPath().endsWith(it.getName()))
                    .allMatch(it -> TestUtils.readAllBytes(it.getInputStream()).length == it.getSize())
                    .allMatch(it -> it.getSize() >= 0)
                    .allMatch(it -> FilenameUtils.getExtension(it.getName()).equals("dat"));
        }

        @Test
        @DisplayName("gets a resource with file path on memory file system")
        void test4(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path filePath = Files.createFile(fileSystem.getPath("/", "dummy.txt"));
            byte[] bytes = new RandomString().nextString(1, 1024).getBytes(StandardCharsets.UTF_8);
            Files.write(filePath, bytes);

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(true);
            List<Resource> resources = resourceFinder.getResources(filePath);

            // then
            assertThat(resources)
                    .isNotNull()
                    .doesNotContainNull()
                    .hasSize(1)
                    .allMatch(it -> it instanceof DiskFileResource)
                    .noneMatch(Resource::isDirectory)
                    .allMatch(it -> it.getPath().equals("/dummy.txt"))
                    .allMatch(it -> it.getName().equals("dummy.txt"))
                    .allMatch(it -> TestUtils.readAllBytes(it.getInputStream()).length == it.getSize())
                    .allMatch(it -> it.getSize() == bytes.length);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("when method 'getResources' is failed")
    class Failure {
        @Test
        @DisplayName("failed to get resources with non-existent path on memory file system")
        void test0(@Memory FileSystem fileSystem) {
            // given
            Path path = fileSystem.getPath("/", "usr", "bin");

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(false);

            // then
            assertThatException().isThrownBy(() -> resourceFinder.getResources(path))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .withMessageStartingWith("No such path exists: " + path);
        }
    }

}
