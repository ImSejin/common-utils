package io.github.imsejin.common.io.finder;

import io.github.imsejin.common.io.DiskFileResource;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.util.FilenameUtils;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;

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
        Map<String, List<Path>> pathMap = createRandomFileSystemEnvironment(fileSystem);

        // when
        ResourceFinder resourceFinder = new DiskFileResourceFinder(true);
        List<Resource> resources = resourceFinder.getResources(fileSystem.getPath("/"));

        // then
        assertThat(resources)
                .isNotNull()
                .isNotEmpty()
                .as("Contains only one root directory")
                .containsOnlyOnce(new DiskFileResource("/", "", null, -1, true))
                .doesNotHaveDuplicates();
        assertThat(resources)
                .filteredOn(Resource::isDirectory)
                .isNotEmpty()
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

    @Test
    @DisplayName("gets resources non-recursively for location with default constructor")
    void test1(@Memory FileSystem fileSystem) throws IOException {
        Map<String, List<Path>> pathMap = createRandomFileSystemEnvironment(fileSystem);
    }

    // ----------------------------------------------------------------------------------------------------

    private static Map<String, List<Path>> createRandomFileSystemEnvironment(FileSystem fileSystem) throws IOException {
        Path rootPath = fileSystem.getPath("/");

        Random random = new Random();
        RandomString randomNameString = new RandomString(random.nextInt(10) + 1, random);

        // Creates directories in root directory.
        List<Path> directories = new ArrayList<>();
        for (int i = 0; i < random.nextInt(10); i++) {
            String directoryName = randomNameString.nextString();
            Path directoryPath = Files.createDirectory(rootPath.resolve(directoryName));

            directories.add(directoryPath);
        }

        // Creates files in root directory.
        List<Path> files = createFiles(rootPath, random);

        // Creates files in each directory.
        for (Path directory : directories) {
            List<Path> list = createFiles(directory, random);
            files.addAll(list);
        }

        directories.add(rootPath);

        Map<String, List<Path>> map = new HashMap<>();
        map.put("directories", directories);
        map.put("files", files);

        return Collections.unmodifiableMap(map);
    }

    private static List<Path> createFiles(Path path, Random random) throws IOException {
        RandomString randomNameString = new RandomString(random.nextInt(10) + 1, random);

        List<String> extensions = Arrays.asList("log", "txt", "tmp", "dat");
        List<Path> files = new ArrayList<>();

        // Creates files in directory.
        for (int i = 0; i < random.nextInt(10); i++) {
            int fileLength = random.nextInt((int) Math.pow(2, 13));
            RandomString randomContentString = new RandomString(fileLength, random);

            String baseName = randomNameString.nextString();
            String extension = extensions.get(random.nextInt(extensions.size()));
            String fileName = String.format("%s-%d.%s", baseName, System.currentTimeMillis(), extension);

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
