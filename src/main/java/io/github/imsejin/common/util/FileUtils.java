/*
 * Copyright 2020 Sejin Im
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

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toSet;

/**
 * File utilities
 */
public final class FileUtils {

    @ExcludeFromGeneratedJacocoReport
    private FileUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Returns attributes of path.
     *
     * @param path path
     * @return path's attributes
     */
    public static BasicFileAttributes getFileAttributes(Path path) {
        try {
            return Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Creates a directory whose name is the same name as the filename in the same path.
     *
     * <pre><code>
     *     File file = new File("/usr/local/docs", "list_20191231.csv");
     *     mkdirAsOwnName(file); // new File("/usr/local/docs", "list_20191231")
     * </code></pre>
     *
     * @param file file
     * @return directory whose name is the same name as the filename in the same path
     */
    public static File mkdirAsOwnName(File file) {
        String dirName = FilenameUtils.getBaseName(file.getName());

        File dir = new File(file.getParentFile(), dirName);
        dir.mkdir();

        return dir;
    }

    /**
     * Downloads a file with URL.
     *
     * @param url  URL
     * @param dest destination for file
     * @return whether success to download file or not
     */
    public static boolean download(URL url, Path dest) {
        try {
            return download(url.openStream(), dest);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Downloads a file.
     *
     * @param in   input stream
     * @param dest destination for file
     * @return whether success to download file or not
     */
    public static boolean download(InputStream in, Path dest) {
        try (
                ReadableByteChannel readChannel = Channels.newChannel(in);
                FileChannel channel = FileChannel.open(dest, StandardOpenOption.WRITE, StandardOpenOption.CREATE)
        ) {
            channel.transferFrom(readChannel, 0, Long.MAX_VALUE);

            // Success
            return true;
        } catch (IOException e) {
            // Fail
            return false;
        }
    }

    /**
     * Finds all files in directory recursively.
     *
     * @param path path
     * @return all files in directory
     */
    public static Set<File> findAllFiles(Path path) {
        try {
            return Files.find(path, Integer.MAX_VALUE, (p, bfa) -> bfa.isRegularFile())
                    .map(Path::toFile).collect(toSet());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Deletes all directories and files recursively.
     *
     * @param path    path
     * @param options options for visiting paths
     */
    public static void deleteRecursively(Path path, FileVisitOption... options) {
        try {
            if (!Files.isDirectory(path)) {
                Files.delete(path);
                return;
            }

            // Sorts as reverse order to delete directory that contains file.
            Path[] paths = Files.walk(path, options).sorted(reverseOrder()).toArray(Path[]::new);

            for (Path p : paths) {
                Files.delete(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
