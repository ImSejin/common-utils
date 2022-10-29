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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestFileSystemCreator {

    private Random random;
    private int minimumFileLength;
    private int maximumFileLength;
    private int minimumFileCount;
    private int maximumFileCount;
    private int minimumDirectoryCount;
    private int maximumDirectoryCount;
    private List<String> filePrefixes;
    private List<String> fileSuffixes;
    private List<String> directoryPrefixes;
    private List<String> directorySuffixes;

    public enum PathType {
        FILE, DIRECTORY, FILE_IN_DIRECTORY
    }

    public static Builder builder() {
        return new Builder();
    }

    // -------------------------------------------------------------------------------------------------

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Builder {
        private Random randomInstance;
        private int minimumFileLength;
        private int maximumFileLength;
        private int minimumFileCount;
        private int maximumFileCount;
        private int minimumDirectoryCount;
        private int maximumDirectoryCount;
        private List<String> filePrefixes;
        private List<String> fileSuffixes;
        private List<String> directoryPrefixes;
        private List<String> directorySuffixes;

        public Builder randomInstance(Random random) {
            this.randomInstance = random;
            return this;
        }

        public Builder minimumFileLength(int length) {
            this.minimumFileLength = length;
            return this;
        }

        public Builder maximumFileLength(int length) {
            this.maximumFileLength = length;
            return this;
        }

        public Builder minimumFileCount(int count) {
            this.minimumFileCount = count;
            return this;
        }

        public Builder maximumFileCount(int count) {
            this.maximumFileCount = count;
            return this;
        }

        public Builder minimumDirectoryCount(int count) {
            this.minimumDirectoryCount = count;
            return this;
        }

        public Builder maximumDirectoryCount(int count) {
            this.maximumDirectoryCount = count;
            return this;
        }

        public Builder filePrefixes(String... prefixes) {
            this.filePrefixes = Collections.unmodifiableList(Arrays.asList(prefixes));
            return this;
        }

        public Builder fileSuffixes(String... suffixes) {
            this.fileSuffixes = Collections.unmodifiableList(Arrays.asList(suffixes));
            return this;
        }

        public Builder directoryPrefixes(String... prefixes) {
            this.directoryPrefixes = Collections.unmodifiableList(Arrays.asList(prefixes));
            return this;
        }

        public Builder directorySuffixes(String... suffixes) {
            this.directorySuffixes = Collections.unmodifiableList(Arrays.asList(suffixes));
            return this;
        }

        public TestFileSystemCreator build() {
            TestFileSystemCreator creator = new TestFileSystemCreator();

            creator.random = this.randomInstance;
            if (creator.random == null) creator.random = new Random();

            creator.minimumFileLength = Math.max(0, this.minimumFileLength);
            creator.maximumFileLength = Math.max(0, this.maximumFileLength);
            creator.minimumFileCount = Math.max(0, this.minimumFileCount);
            creator.maximumFileCount = Math.max(0, this.maximumFileCount);
            creator.minimumDirectoryCount = Math.max(0, this.minimumDirectoryCount);
            creator.maximumDirectoryCount = Math.max(0, this.maximumDirectoryCount);
            creator.filePrefixes = CollectionUtils.ifNullOrEmpty(this.filePrefixes, Collections.emptyList());
            creator.fileSuffixes = CollectionUtils.ifNullOrEmpty(this.fileSuffixes, Collections.emptyList());
            creator.directoryPrefixes = CollectionUtils.ifNullOrEmpty(this.directoryPrefixes, Collections.emptyList());
            creator.directorySuffixes = CollectionUtils.ifNullOrEmpty(this.directorySuffixes, Collections.emptyList());

            return creator;
        }
    }

    // -------------------------------------------------------------------------------------------------

    public Map<PathType, List<Path>> create(Path basePath) throws IOException {
        RandomString randomString = new RandomString(this.random);

        // Creates directories in base path.
        List<Path> directories = createDirectories(basePath, randomString);

        // Creates files in root directory.
        List<Path> files = createFiles(basePath, randomString);

        // Creates files in each directory.
        List<Path> dirFiles = new ArrayList<>();
        for (Path directory : directories) {
            List<Path> list = createFiles(directory, randomString);
            dirFiles.addAll(list);
        }

        directories.add(basePath);

        Map<PathType, List<Path>> map = new EnumMap<>(PathType.class);
        map.put(PathType.DIRECTORY, directories);
        map.put(PathType.FILE, files);
        map.put(PathType.FILE_IN_DIRECTORY, dirFiles);

        return Collections.unmodifiableMap(map);
    }

    public List<Path> createDirectories(Path path, RandomString randomString) throws IOException {
        List<Path> directories = new ArrayList<>();

        int directoryCount = Math.max(this.random.nextInt(this.maximumDirectoryCount + 1), this.minimumDirectoryCount);
        for (int i = 0; i < directoryCount; i++) {
            String prefix = CollectionUtils.isNullOrEmpty(this.directoryPrefixes)
                    ? "" : this.directoryPrefixes.get(this.random.nextInt(this.directoryPrefixes.size()));
            String baseName = randomString.nextString(1, 11);
            String suffix = CollectionUtils.isNullOrEmpty(this.directorySuffixes)
                    ? "" : this.directorySuffixes.get(this.random.nextInt(this.directorySuffixes.size()));

            String directoryName = String.format("%s%s-%d%s", prefix, baseName, System.nanoTime(), suffix);
            Path directoryPath = Files.createDirectory(path.resolve(directoryName));

            directories.add(directoryPath);
        }

        return directories;
    }

    public List<Path> createFiles(Path path, RandomString randomString) throws IOException {
        List<Path> files = new ArrayList<>();

        // Creates files in directory.
        int fileCount = Math.max(this.random.nextInt(this.maximumFileCount + 1), this.minimumFileCount);
        for (int i = 0; i < fileCount; i++) {
            String prefix = CollectionUtils.isNullOrEmpty(this.filePrefixes)
                    ? "" : this.filePrefixes.get(this.random.nextInt(this.filePrefixes.size()));
            String baseName = randomString.nextString(1, 11);
            String suffix = CollectionUtils.isNullOrEmpty(this.fileSuffixes)
                    ? "" : this.fileSuffixes.get(this.random.nextInt(this.fileSuffixes.size()));
            String fileName = String.format("%s%s-%d%s", prefix, baseName, System.nanoTime(), suffix);

            Path filePath = Files.createFile(path.resolve(fileName));

            // Write content into file.
            String content = new RandomString(this.random).nextString(this.minimumFileLength, this.maximumFileLength + 1);
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                int lineWidth = Math.min(100, this.random.nextInt(20) + 80); // min: 80, max: 100
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
