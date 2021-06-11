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

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * File utilities
 */
public final class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Returns creation time of the file.
     *
     * <pre><code>
     *     File file = new File("C:\\Program Files\\Java\\jdk1.8.0_202\\README.html");
     *     getCreationTime(file); // 2020-02-29 23:06:34
     * </code></pre>
     *
     * @param file file
     * @return file's creation time
     */
    public static LocalDateTime getCreationTime(@Nonnull File file) {
        FileTime time = getFileAttributes(file).creationTime();
        return LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
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
    public static File mkdirAsOwnName(@Nonnull File file) {
        String dirName = FilenameUtils.baseName(file);

        File dir = new File(file.getParentFile(), dirName);
        dir.mkdir();

        return dir;
    }

    /**
     * Returns attributes of file.
     *
     * @param file file
     * @return file's attributes
     */
    public static BasicFileAttributes getFileAttributes(@Nonnull File file) {
        try {
            return Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Downloads a file.
     *
     * @param in   input stream
     * @param dest file for destination
     * @return whether success to download file or not
     */
    public static boolean download(InputStream in, File dest) {
        try (FileOutputStream out = new FileOutputStream(dest);
             ReadableByteChannel readChannel = Channels.newChannel(in)) {
            out.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);

            // Success
            return true;
        } catch (IOException e) {
            // Fail
            return false;
        }
    }

}
