package io.github.imsejin.common.io.finder;

import io.github.imsejin.common.internal.TestFileSystemCreator;
import io.github.imsejin.common.internal.TestFileSystemCreator.PathType;
import io.github.imsejin.common.internal.TestUtils;
import io.github.imsejin.common.io.DiskFileResource;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.tool.RandomString;
import io.github.imsejin.common.util.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

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
            Map<PathType, List<Path>> pathMap = TestFileSystemCreator.createRandomEnvironment(path,
                    null, Arrays.asList(".log", ".txt", ".tmp", ".dat"));

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
                    .allMatch(resource -> resource instanceof DiskFileResource);
            assertThat(resources)
                    .filteredOn(Resource::isDirectory)
                    .doesNotContainNull()
                    .hasSameSizeAs(pathMap.get(PathType.DIRECTORY))
                    .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                    .allMatch(resource -> resource.getInputStream() == null)
                    .allMatch(resource -> resource.getSize() == -1);
            assertThat(resources)
                    .filteredOn(resource -> !resource.isDirectory())
                    .doesNotContainNull()
                    .hasSameSizeAs(pathMap.get(PathType.FILE))
                    .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                    .allMatch(resource -> TestUtils.readAllBytes(resource.getInputStream()).length == resource.getSize())
                    .allMatch(resource -> resource.getSize() >= 0)
                    .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).matches("log|txt|tmp|dat"));
        }

        @RepeatedTest(10)
        @DisplayName("gets resources recursively with default constructor on memory file system")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Map<PathType, List<Path>> pathMap = TestFileSystemCreator.createRandomEnvironment(path,
                    null, Arrays.asList(".log", ".txt", ".tmp", ".dat"));

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
                    .allMatch(resource -> resource instanceof DiskFileResource);
            assertThat(resources)
                    .filteredOn(Resource::isDirectory)
                    .doesNotContainNull()
                    .hasSameSizeAs(pathMap.get(PathType.DIRECTORY))
                    .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                    .allMatch(resource -> resource.getInputStream() == null)
                    .allMatch(resource -> resource.getSize() == -1);
            assertThat(resources)
                    .filteredOn(resource -> !resource.isDirectory())
                    .doesNotContainNull()
                    .hasSize(pathMap.get(PathType.FILE).size() + pathMap.get(PathType.FILE_IN_DIRECTORY).size())
                    .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                    .allMatch(resource -> TestUtils.readAllBytes(resource.getInputStream()).length == resource.getSize())
                    .allMatch(resource -> resource.getInputStream() != null)
                    .allMatch(resource -> resource.getSize() >= 0)
                    .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).matches("log|txt|tmp|dat"));
        }

        @RepeatedTest(10)
        @DisplayName("gets resources non-recursively with another constructor on default file system")
        void test2(@TempDir Path path) throws IOException {
            // given
            Map<PathType, List<Path>> pathMap = TestFileSystemCreator.createRandomEnvironment(path,
                    null, Arrays.asList(".log", ".txt", ".tmp", ".dat"));

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(false,
                    _path -> _path.toString().endsWith(".log") || _path.toString().endsWith(".txt"));
            List<Resource> resources = resourceFinder.getResources(path);

            // then
            assertThat(resources)
                    .isNotNull()
                    .doesNotContainNull()
                    .doesNotHaveDuplicates()
                    .allMatch(resource -> resource instanceof DiskFileResource)
                    .noneMatch(Resource::isDirectory)
                    .hasSameSizeAs(pathMap.get(PathType.FILE).stream().map(Path::toString)
                            .filter(it -> it.endsWith(".log") || it.endsWith(".txt")).toArray())
                    .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                    .allMatch(resource -> TestUtils.readAllBytes(resource.getInputStream()).length == resource.getSize())
                    .allMatch(resource -> resource.getSize() >= 0)
                    .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).matches("log|txt"));
        }

        @RepeatedTest(10)
        @DisplayName("gets resources recursively with another constructor on default file system")
        void test3(@TempDir Path path) throws IOException {
            // given
            Map<PathType, List<Path>> pathMap = TestFileSystemCreator.createRandomEnvironment(path,
                    null, Arrays.asList(".log", ".txt", ".tmp", ".dat"));

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(true,
                    _path -> _path.toString().endsWith(".dat"));
            List<Resource> resources = resourceFinder.getResources(path);

            // then
            assertThat(resources)
                    .isNotNull()
                    .doesNotContainNull()
                    .doesNotHaveDuplicates()
                    .allMatch(resource -> resource instanceof DiskFileResource)
                    .noneMatch(Resource::isDirectory)
                    .hasSameSizeAs(Stream.concat(pathMap.get(PathType.FILE).stream(), pathMap.get(PathType.FILE_IN_DIRECTORY).stream())
                            .map(Path::toString).filter(it -> it.endsWith(".dat")).toArray())
                    .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                    .allMatch(resource -> TestUtils.readAllBytes(resource.getInputStream()).length == resource.getSize())
                    .allMatch(resource -> resource.getSize() >= 0)
                    .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).equals("dat"));
        }

        @Test
        @DisplayName("gets a resource with file path on memory file system")
        void test4(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path filePath = Files.createFile(fileSystem.getPath("/", "dummy.txt"));
            byte[] bytes = new RandomString().nextString(new Random().nextInt(1024)).getBytes(StandardCharsets.UTF_8);
            Files.write(filePath, bytes);

            // when
            ResourceFinder resourceFinder = new DiskFileResourceFinder(true);
            List<Resource> resources = resourceFinder.getResources(filePath);

            // then
            assertThat(resources)
                    .isNotNull()
                    .doesNotContainNull()
                    .hasSize(1)
                    .allMatch(resource -> resource instanceof DiskFileResource)
                    .noneMatch(Resource::isDirectory)
                    .allMatch(resource -> resource.getPath().equals("/dummy.txt"))
                    .allMatch(resource -> resource.getName().equals("dummy.txt"))
                    .allMatch(resource -> TestUtils.readAllBytes(resource.getInputStream()).length == resource.getSize())
                    .allMatch(resource -> resource.getSize() == bytes.length);
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
                    .withMessage("No such path exists: " + path);
        }
    }

}
