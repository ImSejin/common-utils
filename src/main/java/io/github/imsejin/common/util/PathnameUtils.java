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
import io.github.imsejin.common.constant.DateType;
import io.github.imsejin.common.constant.OperatingSystem;
import io.github.imsejin.common.tool.OSDetector;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import static io.github.imsejin.common.util.DateTimeUtils.today;

/**
 * Pathname utilities
 */
public final class PathnameUtils {

    /**
     * Path separator of Microsoft Windows.
     */
    private static final String WINDOWS_SEPARATOR = "\\\\";
    /**
     * Path separator of Unix.
     */
    private static final String UNIX_SEPARATOR = "/";

    @ExcludeFromGeneratedJacocoReport
    private PathnameUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Gets the current path where the application is.
     *
     * <pre><code>
     *     getCurrentPathname(); // /usr/local/repositories/common-utils
     * </code></pre>
     *
     * @return current path where the application is
     */
    public static String getCurrentPathname() {
        try {
            // This code can be replaced with `System.getProperty("user.dir")`.
            return Paths.get(".").toRealPath().toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Removes all the path separators.
     *
     * <pre><code>
     *     removeSeparators("C:\\Program Files\\Java"); // C:Program FilesJava
     *     removeSeparators("/usr/data/java"); // usrsdatajava
     * </code></pre>
     *
     * @param pathname pathname
     * @return pathname removed all the path separators
     */
    public static String removeSeparators(@Nonnull String pathname) {
        return pathname.replaceAll(WINDOWS_SEPARATOR, "").replaceAll(UNIX_SEPARATOR, "");
    }

    /**
     * 부적절한 경로명을 올바른 경로명으로 정정한다.<br>
     * OS가 Windows인 경우, 절대경로로 지정해도 앞에 구분자가 들어가지 않는다.
     *
     * <pre><code>
     *     String pathname1 = "\\/ / C:\\ Program Files / \\/\\ \\ Java\\jdk8 /\\/ \\ ";
     *     correct(false, pathname1);   // C:\\Program Files\\Java\\jdk8
     *
     *     String pathname2 = "/ / \\ usr / data /java/jdk8 / ";
     *     correct(true, pathname2);    // /usr/data/java/jdk8
     *
     *     String pathname3 = "/ / \\ usr / data /java/jdk8 / ";
     *     correct(false, pathname3);   // usr/data/java/jdk8
     * </code></pre>
     *
     * @param absolute whether path is absolute
     * @param pathname pathname
     * @return correct pathname
     */
    public static String correct(boolean absolute, @Nonnull String pathname) {
        String trimmed = Arrays.stream(pathname.split(WINDOWS_SEPARATOR)) // split with Windows separators.
                .map(p -> String.join("", p.split(UNIX_SEPARATOR))) // split with Unix separators.
                .filter(p -> !StringUtils.isNullOrBlank(p))
                .map(String::trim)
                .collect(Collectors.joining(File.separator));

        return absolute && OSDetector.getOS() != OperatingSystem.WINDOWS
                ? UNIX_SEPARATOR + trimmed
                : trimmed;
    }

    /**
     * Concatenates pathnames.
     *
     * <pre><code>
     *     concat(false, "C:\\", "Program Files", "Java");  // C:\\Program Files\\Java
     *     concat(true, "/usr/", "/data/", "java");         // /usr/data/java
     *     concat(false, "/usr/", "/data/", "java");        // usr/data/java
     * </code></pre>
     *
     * @param absolute  whether paths are absolute
     * @param pathnames pathnames
     * @return concatenated pathname
     */
    public static String concat(boolean absolute, @Nonnull String... pathnames) {
        return correct(absolute, String.join(File.separator, pathnames));
    }

    /**
     * 경로 끝에 현재의 연/월(yyyy/MM) 경로를 추가한다.<br>
     * Adds the current year/month (yyyy/MM) pathname to the end of the pathname.
     *
     * <pre><code>
     *     DateTimeUtils.today();                   // 20191231
     *     appendYearMonth("C:\\Program Files");    // C:\\Program Files\\2019\\12
     * </code></pre>
     *
     * @param pathname pathname
     * @return pathname appended the current year and month
     */
    public static String appendYearMonth(String pathname) {
        return concat(false, pathname, today(DateType.YEAR), today(DateType.MONTH));
    }

    /**
     * 경로 끝에 현재의 연/월/일(yyyy/MM/dd) 경로를 추가한다.<br>
     * Adds the current year/month/day (yyyy/MM/dd) pathname to the end of the pathname.
     *
     * <pre><code>
     *     DateTimeUtils.today();           // 20191231
     *     appendDate("C:\\Program Files"); // C:\\Program Files\\2019\\12\\31
     * </code></pre>
     *
     * @param pathname pathname
     * @return pathname appended the current year, month and day
     */
    public static String appendDate(String pathname) {
        return concat(false, pathname, today(DateType.YEAR), today(DateType.MONTH), today(DateType.DAY));
    }

}
