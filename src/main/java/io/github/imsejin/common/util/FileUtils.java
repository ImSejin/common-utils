package io.github.imsejin.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * File uilities
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * Returns creation time of the file.
     *
     * <pre>{@code
     *     File file = new File("C:\\Program Files\\Java\\jdk1.8.0_202\\README.html");
     *     getCreationTime(file); // 2020-02-29 23:06:34
     * }</pre>
     *
     * @param file file
     * @return file's creation time
     */
    public static LocalDateTime getCreationTime(File file) {
        BasicFileAttributes attributes;
        try {
            attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileTime time = attributes.creationTime();
        return LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Creates a directory whose name is the same name as the filename in the same path.
     *
     * <pre>{@code
     *     File file = new File("/usr/local/docs", "list_20191231.csv");
     *     mkdirAsOwnName(file); // new File("/usr/local/docs", "list_20191231")
     * }</pre>
     *
     * @param file file
     * @return directory whose name is the same name as the filename in the same path
     */
    public static File mkdirAsOwnName(File file) {
        String dirName = FilenameUtils.baseName(file);

        File dir = new File(file.getParentFile(), dirName);
        dir.mkdir();

        return dir;
    }

}
