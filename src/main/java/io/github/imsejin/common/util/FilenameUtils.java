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

import jakarta.validation.constraints.Null;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

/**
 * Filename utilities
 */
public final class FilenameUtils {

    /**
     * Separator of file extension.
     */
    private static final char EXTENSION_SEPARATOR = '.';

    @ExcludeFromGeneratedJacocoReport
    private FilenameUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Returns position of the extension.
     * <p> If cannot find it, returns -1.
     *
     * <pre><code>
     *     File file0 = new File("D:\\Program Files\\Java", "README.md");
     *     indexOfExtension(file0.getName()); // 6
     *
     *     File file1 = new File("D:\\Program Files\\Java", ".gitignore");
     *     indexOfExtension(file1.getName()); // -1
     * </code></pre>
     *
     * @param filename filename
     * @return index of extension separator
     */
    public static int indexOfExtension(@Null String filename) {
        if (filename == null) {
            return -1;
        }

        int index = filename.lastIndexOf(EXTENSION_SEPARATOR);
        return index == 0 ? -1 : index;
    }

    /**
     * Returns the name including the extension.
     * <p> If file is null, returns empty string.
     *
     * <pre><code>
     *     getName("D:\\Program Files\\Java\\README.md"); // "README.md"
     *     getName("/var/lib/java/"); // "java"
     *     getName("/var/lib/java/LICENSE"); // "LICENSE"
     * </code></pre>
     *
     * @param path path to the directory or file
     * @return name
     */
    public static String getName(String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            return "";
        }

        path = path.trim();

        int index = path.lastIndexOf('\\');
        if (index == -1) {
            index = path.lastIndexOf('/');
        }

        if (index == -1) {
            return path;
        } else if (index == path.length() - 1) {
            // When path has a trailing separator.
            String chopped = StringUtils.chop(path);
            return getName(chopped);
        }

        return path.substring(index + 1);
    }

    /**
     * Returns the filename excluding the extension.
     * <p> If file is null, returns empty string.
     *
     * <pre><code>
     *     File file0 = new File("D:\\Program Files\\Java", "README.md");
     *     getBaseName(file0.getName()); // "README"
     *
     *     File file1 = new File("D:\\Program Files\\Java", "LICENSE");
     *     getBaseName(file1.getName()); // "LICENSE"
     * </code></pre>
     *
     * @param filename filename
     * @return filename without extension
     */
    public static String getBaseName(@Null String filename) {
        if (filename == null) {
            return "";
        }

        int index = indexOfExtension(filename);
        return index == -1 ? filename : filename.substring(0, index);
    }

    /**
     * Returns the file's extension.
     * <p> If file is null, returns empty string.
     *
     * <pre><code>
     *     File file = new File("D:\\Program Files\\Java", "README.md");
     *     getExtension(file.getName()); // "html"
     * </code></pre>
     *
     * @param filename filename
     * @return extension name
     */
    public static String getExtension(@Null String filename) {
        if (filename == null) {
            return "";
        }

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
     *     " --> ＂
     *     < --> ＜
     *     > --> ＞
     *     | --> ｜
     * }</pre>
     *
     * <pre>{@code
     *     String unallowables = "** <happy/\\new year> **:\"john\" -> |\"jeremy\"|";
     *
     *     toSafeName(unallowables);                // "＊＊ ＜happy／＼new year＞ ＊＊：＂john＂ -＞ ｜＂jeremy＂｜"
     *     toSafeName("where he is gone..");        // "where he is gone…"
     *     toSafeName("I feel happy when coding."); // "I feel happy when coding．"
     * }</pre>
     *
     * @param filename filename that has unallowable characters
     * @return filename in which unallowable characters are replaced with allowable characters
     */
    public static String replaceUnallowables(String filename) {
        return filename.replace('\\', '＼')
                .replace('/', '／')
                .replace(':', '：')
                .replace('*', '＊')
                .replace('?', '？')
                .replace('"', '＂')
                .replace('<', '＜')
                .replace('>', '＞')
                .replace('|', '｜')
                .replaceAll("\\.{2,}+$", "…")
                .replaceAll("\\.$", "．");
    }

}
