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

package io.github.imsejin.common.internal;

import io.github.imsejin.common.tool.RandomString;
import io.github.imsejin.common.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class TestFileSystemCreator {

    public enum PathType {
        FILE, DIRECTORY, FILE_IN_DIRECTORY
    }

    public static Map<PathType, List<Path>> createRandomEnvironment(Path basePath) throws IOException {
        return createRandomEnvironment(basePath, Collections.emptyList(), Collections.emptyList());
    }

    public static Map<PathType, List<Path>> createRandomEnvironment(Path basePath, List<String> prefixes, List<String> suffixes) throws IOException {
        Random random = new Random();
        RandomString randomNameString = new RandomString(random);

        // Creates directories in root directory.
        List<Path> directories = new ArrayList<>();
        int directoryCount = random.nextInt(10) + 1;
        for (int i = 0; i < directoryCount; i++) {
            String directoryName = String.format("%s-%d", randomNameString.nextString(random.nextInt(10) + 1), System.nanoTime());
            Path directoryPath = Files.createDirectory(basePath.resolve(directoryName));

            directories.add(directoryPath);
        }

        // Creates files in root directory.
        List<Path> files = createFiles(basePath, random, prefixes, suffixes);

        // Creates files in each directory.
        List<Path> dirFiles = new ArrayList<>();
        for (Path directory : directories) {
            List<Path> list = createFiles(directory, random, prefixes, suffixes);
            dirFiles.addAll(list);
        }

        directories.add(basePath);

        Map<PathType, List<Path>> map = new EnumMap<>(PathType.class);
        map.put(PathType.DIRECTORY, directories);
        map.put(PathType.FILE, files);
        map.put(PathType.FILE_IN_DIRECTORY, dirFiles);

        return Collections.unmodifiableMap(map);
    }

    private static List<Path> createFiles(Path path, Random random, List<String> prefixes, List<String> suffixes) throws IOException {
        RandomString randomNameString = new RandomString(random);
        List<Path> files = new ArrayList<>();

        // Creates files in directory.
        int fileCount = random.nextInt(10) + 1;
        for (int i = 0; i < fileCount; i++) {
            int fileLength = random.nextInt((int) Math.pow(2, 13)) + 1;
            RandomString randomContentString = new RandomString(random);

            String prefix = CollectionUtils.isNullOrEmpty(prefixes) ? "" : prefixes.get(random.nextInt(prefixes.size()));
            String baseName = randomNameString.nextString(random.nextInt(10) + 1);
            String suffix = CollectionUtils.isNullOrEmpty(suffixes) ? "" : suffixes.get(random.nextInt(suffixes.size()));
            String fileName = String.format("%s%s-%d%s", prefix, baseName, System.nanoTime(), suffix);

            Path filePath = Files.createFile(path.resolve(fileName));

            // Write content into file.
            String content = randomContentString.nextString(fileLength);
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
