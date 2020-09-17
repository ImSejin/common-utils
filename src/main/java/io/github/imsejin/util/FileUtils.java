package io.github.imsejin.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * File uilities
 */
public final class FileUtils {

    private FileUtils() {}
    
    /**
     * 파일의 생성시간을 반환한다.
     * 
     * <pre>{@code
     *     File file = new File("C:\\Program Files\\Java\\jdk1.8.0_202\\README.html");
     *     getCreationTime(file); // 2020-02-29 23:06:34
     * }</pre>
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
     * 같은 경로에 해당 파일명과 같은 이름의 폴더를 생성한다.
     * 
     * <pre>
     * File file = new File("C:\\Program Files\\list_20191231.xlsx");
     * 
     * FileUtils.mkdirAsOwnName(file): new File("C:\\Program Files\\list_20191231")
     * </pre>
     */
    public static File mkdirAsOwnName(File file) {
        String dirName = FilenameUtils.baseName(file);

        File dir = new File(file.getParentFile(), dirName);
        dir.mkdir();

        return dir;
    }

}
