package io.github.imsejin.common.io.finder;

import io.github.imsejin.common.io.DiskFileResource;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.util.FilenameUtils;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@FileSystemSource
@DisplayName("DiskFileResourceFinder")
class DiskFileResourceFinderTest {

    @RepeatedTest(10)
    @DisplayName("gets resources non-recursively for location with default constructor")
    void test0(@Memory FileSystem fileSystem) throws IOException {
        // given
        Path path = fileSystem.getPath("/");
        Map<String, List<Path>> pathMap = createRandomFileSystemEnvironment(path);

        // when
        ResourceFinder resourceFinder = new DiskFileResourceFinder(false);
        List<Resource> resources = resourceFinder.getResources(path);

        // then
        assertThat(resources)
                .isNotNull()
                .isNotEmpty()
                .as("Contains only one root directory")
                .containsOnlyOnce(new DiskFileResource("/", "", null, -1, true))
                .doesNotHaveDuplicates();
        assertThat(resources)
                .filteredOn(Resource::isDirectory)
                .doesNotContainNull()
                .hasSameSizeAs(pathMap.get("directories"))
                .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                .allMatch(resource -> resource.getInputStream() == null)
                .allMatch(resource -> resource.getSize() == -1);
        assertThat(resources)
                .filteredOn(resource -> !resource.isDirectory())
                .doesNotContainNull()
                .hasSameSizeAs(pathMap.get("files"))
                .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                .allMatch(resource -> resource.getInputStream() != null)
                .allMatch(resource -> resource.getSize() >= 0)
                .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).matches("log|txt|tmp|dat"));
    }

    @RepeatedTest(10)
    @DisplayName("gets resources recursively for location with default constructor")
    void test1(@Memory FileSystem fileSystem) throws IOException {
        // given
        Path path = fileSystem.getPath("/");
        Map<String, List<Path>> pathMap = createRandomFileSystemEnvironment(path);

        // when
        ResourceFinder resourceFinder = new DiskFileResourceFinder(true);
        List<Resource> resources = resourceFinder.getResources(path);

        // then
        assertThat(resources)
                .isNotNull()
                .isNotEmpty()
                .as("Contains only one root directory")
                .containsOnlyOnce(new DiskFileResource("/", "", null, -1, true))
                .doesNotHaveDuplicates();
        assertThat(resources)
                .filteredOn(Resource::isDirectory)
                .doesNotContainNull()
                .hasSameSizeAs(pathMap.get("directories"))
                .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                .allMatch(resource -> resource.getInputStream() == null)
                .allMatch(resource -> resource.getSize() == -1);
        assertThat(resources)
                .filteredOn(resource -> !resource.isDirectory())
                .doesNotContainNull()
                .hasSize(pathMap.get("files").size() + pathMap.get("directories/files").size())
                .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                .allMatch(resource -> resource.getInputStream() != null)
                .allMatch(resource -> resource.getSize() >= 0)
                .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).matches("log|txt|tmp|dat"));
    }

    @RepeatedTest(10)
    @DisplayName("gets resources non-recursively for location with another constructor")
    void test2(@TempDir Path path) throws IOException {
        // given
        Map<String, List<Path>> pathMap = createRandomFileSystemEnvironment(path);

        // when
        ResourceFinder resourceFinder = new DiskFileResourceFinder(false,
                $path -> $path.toString().endsWith(".log") || $path.toString().endsWith(".txt"));
        List<Resource> resources = resourceFinder.getResources(path);

        // then
        assertThat(resources)
                .isNotNull()
                .doesNotContainNull()
                .doesNotHaveDuplicates()
                .noneMatch(Resource::isDirectory)
                .hasSameSizeAs(pathMap.get("files").stream().map(Path::toString)
                        .filter(it -> it.endsWith(".log") || it.endsWith(".txt")).toArray())
                .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                .allMatch(resource -> resource.getInputStream() != null)
                .allMatch(resource -> resource.getSize() >= 0)
                .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).matches("log|txt"));
    }

    @RepeatedTest(10)
    @DisplayName("gets resources recursively for location with another constructor")
    void test3(@TempDir Path path) throws IOException {
        // given
        Map<String, List<Path>> pathMap = createRandomFileSystemEnvironment(path);

        // when
        ResourceFinder resourceFinder = new DiskFileResourceFinder(true, Files::isDirectory);
        List<Resource> resources = resourceFinder.getResources(path);

        // then
        assertThat(resources)
                .isNotNull()
                .doesNotContainNull()
                .doesNotHaveDuplicates()
                .allMatch(Resource::isDirectory)
                .hasSameSizeAs(pathMap.get("directories"))
                .allMatch(resource -> resource.getPath().endsWith(resource.getName()))
                .allMatch(resource -> resource.getInputStream() == null)
                .allMatch(resource -> resource.getSize() == 4096);
    }

    // ----------------------------------------------------------------------------------------------------

    private static Map<String, List<Path>> createRandomFileSystemEnvironment(Path rootPath) throws IOException {
        Random random = new Random();
        RandomString randomNameString = new RandomString(random.nextInt(10) + 1, random);

        // Creates directories in root directory.
        List<Path> directories = new ArrayList<>();
        for (int i = 0; i < random.nextInt(10); i++) {
            String directoryName = String.format("%s-%d", randomNameString.nextString(), System.nanoTime());
            Path directoryPath = Files.createDirectory(rootPath.resolve(directoryName));

            directories.add(directoryPath);
        }

        // Creates files in root directory.
        List<Path> files = createFiles(rootPath, random);

        // Creates files in each directory.
        List<Path> dirFiles = new ArrayList<>();
        for (Path directory : directories) {
            List<Path> list = createFiles(directory, random);
            dirFiles.addAll(list);
        }

        directories.add(rootPath);

        Map<String, List<Path>> map = new HashMap<>();
        map.put("directories", directories);
        map.put("files", files);
        map.put("directories/files", dirFiles);

        return Collections.unmodifiableMap(map);
    }

    private static List<Path> createFiles(Path path, Random random) throws IOException {
        RandomString randomNameString = new RandomString(random.nextInt(10) + 1, random);

        List<String> extensions = Arrays.asList("log", "txt", "tmp", "dat");
        List<Path> files = new ArrayList<>();

        // Creates files in directory.
        for (int i = 0; i < random.nextInt(10); i++) {
            int fileLength = random.nextInt((int) Math.pow(2, 13)) + 1;
            RandomString randomContentString = new RandomString(fileLength, random);

            String baseName = randomNameString.nextString();
            String extension = extensions.get(random.nextInt(extensions.size()));
            String fileName = String.format("%s-%d.%s", baseName, System.nanoTime(), extension);

            Path filePath = Files.createFile(path.resolve(fileName));

            // Write content into file.
            String content = randomContentString.nextString();
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                int lineWidth = Math.min(100, random.nextInt(20) + 80); // min: 80, max: 100
                String[] lines = content.split("(?<=\\G.{" + lineWidth + "})");

                for (String line : lines) {
                    writer.write(line);
                }
            }

            files.add(filePath);
        }

        return files;
    }

}
