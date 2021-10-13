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

import javax.annotation.Nonnull;

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
     * <br>
     * If cannot find it, returns -1.
     *
     * <pre><code>
     *     File file = new File("D:/Program Files/Java/jdk1.8.0_202/README.html");
     *     indexOfExtension(file);          // 6
     *
     *     File anotherFile = new File("D:/Program Files/Java/jdk1.8.0_202/.gitignore");
     *     indexOfExtension(anotherFile);   // -1
     * </code></pre>
     *
     * @param filename filename
     * @return index of extension separator
     */
    public static int indexOfExtension(@Nonnull String filename) {
        int index = filename.lastIndexOf(EXTENSION_SEPARATOR);
        return index == 0 ? -1 : index;
    }

    /**
     * Returns the filename excluding the extension.
     * <br>
     * if file is null, returns empty string.
     *
     * <pre><code>
     *     File file = new File("D:/Program Files/Java/jdk1.8.0_202/README.html");
     *     baseName(file);          // README
     *
     *     File anotherFile = new File("D:/Program Files/Java/jdk1.8.0_202/LICENSE");
     *     baseName(anotherFile);   // LICENSE
     * </code></pre>
     *
     * @param file file
     * @return filename without extension
     */
    public static String getBaseName(@Nonnull String filename) {
        int index = indexOfExtension(filename);
        return index == -1 ? filename : filename.substring(0, index);
    }

    /**
     * Returns the file's extension.
     * <br>
     * if file is null, returns empty string.
     *
     * <pre><code>
     *     File file = new File("D:/Program Files/Java/jdk1.8.0_202/README.html");
     *     extension(file); // html
     * </code></pre>
     *
     * @param file file
     * @return extension name
     */
    public static String getExtension(@Nonnull String filename) {
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
     *     toSafeName(unallowables);                // ＊＊ ＜happy／＼new year＞ ＊＊：＂john＂ -＞ ｜＂jeremy＂｜
     *     toSafeName("where he is gone..");        // where he is gone…
     *     toSafeName("I feel happy when coding."); // I feel happy when coding．
     * }</pre>
     *
     * @param filename filename that has unallowable characters
     * @return filename in which unallowable characters are replaced with allowable characters
     */
    public static String replaceUnallowables(@Nonnull String filename) {
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
