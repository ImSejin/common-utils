package io.github.imsejin.common.util;

import java.io.File;

/**
 * Filename utilities
 */
public final class FilenameUtils {

    /**
     * Separator of file extension.
     */
    private static final char EXTENSION_SEPARATOR = '.';

    private FilenameUtils() {
    }

    /**
     * Gets position of the extension.
     * <br>
     * If cannot find it, returns -1.
     *
     * <pre>{@code
     *     File file = new File("D:/Program Files/Java/jdk1.8.0_202/README.html");
     *     indexOfExtension(file);          // 6
     *
     *     File anotherFile = new File("D:/Program Files/Java/jdk1.8.0_202/.gitignore");
     *     indexOfExtension(anotherFile);   // -1
     * }</pre>
     *
     * @param filename filename
     * @return index of extension separator
     */
    public static int indexOfExtension(String filename) {
        if (filename == null) return -1;

        int index = filename.lastIndexOf(EXTENSION_SEPARATOR);
        return index == 0 ? -1 : index;
    }

    /**
     * Gets the filename excluding the extension.
     * <br>
     * if file is null, returns empty string.
     *
     * <pre>{@code
     *     File file = new File("D:/Program Files/Java/jdk1.8.0_202/README.html");
     *     baseName(file);          // README
     *
     *     File anotherFile = new File("D:/Program Files/Java/jdk1.8.0_202/LICENSE");
     *     baseName(anotherFile);   // LICENSE
     * }</pre>
     *
     * @param file file
     * @return filename without extension
     */
    public static String baseName(File file) {
        if (file == null) return "";

        String filename = file.getName();
        int index = indexOfExtension(filename);
        return index == -1 ? filename : filename.substring(0, index);
    }

    /**
     * Gets the file's extension.
     * <br>
     * if file is null, returns empty string.
     *
     * <pre>{@code
     *     File file = new File("D:/Program Files/Java/jdk1.8.0_202/README.html");
     *     extension(file); // html
     * }</pre>
     *
     * @param file file
     * @return extension name
     */
    public static String extension(File file) {
        if (file == null) return "";

        String filename = file.getName();
        int index = indexOfExtension(filename);
        return index == -1 ? "" : filename.substring(index + 1);
    }

    /**
     * Replaces characters that cannot be used in a filename with allowable characters.
     *
     * <pre>{@code
     *     \ --> ＼
     *     / --> ／
     *     : --> ：
     *     * --> ＊
     *     ? --> ？
     *     " --> ˝
     *     < --> ＜
     *     > --> ＞
     *     | --> ｜
     * }</pre>
     *
     * <pre>{@code
     *     String unallowables = "** <happy/\\new year> **:\"john\" -> |\"jeremy\"|";
     *
     *     toSafeName(unallowables);                // ＊＊ ＜happy／＼new year＞ ＊＊：˝john˝ -＞ ｜˝jeremy˝｜
     *     toSafeName("where he is gone..");        // where he is gone…
     *     toSafeName("I feel happy when coding."); // I feel happy when coding．
     * }</pre>
     *
     * @param filename filename that has unallowable characters
     * @return filename in which unallowable characters are replaced with allowable characters
     */
    public static String replaceUnallowables(String filename) {
        return filename.replaceAll("\\\\", "＼")
                .replaceAll("/", "／")
                .replaceAll(":", "：")
                .replaceAll("\\*", "＊")
                .replaceAll("\\?", "？")
                .replaceAll("\"", "˝")
                .replaceAll("<", "＜")
                .replaceAll(">", "＞")
                .replaceAll("\\|", "｜")
                .replaceAll("\\.{2,}+$", "…")
                .replaceAll("\\.$", "．");
    }

}
