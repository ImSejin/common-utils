/*
 * Copyright 2021 Sejin Im
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

package io.github.imsejin.common.assertion.io;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.util.DateTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("AbstractFileAssert")
class AbstractFileAssertTest {

    @Nested
    @DisplayName("method 'exists'")
    class Exists {
        @Test
        @DisplayName("passes, when actual exists")
        void test0(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();

            assertThatCode(() -> Asserts.that(file).exists())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual doesn't exist")
        void test1(@TempDir Path path) {
            File file = new File(path.toFile(), "temp" + DateTimeUtils.now());

            assertThatCode(() -> Asserts.that(file).exists())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();

            assertThatCode(() -> Asserts.that(file).isEmpty())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not empty")
        void test1(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            assertThatCode(() -> Asserts.that(file).isEmpty())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotEmpty'")
    class IsNotEmpty {
        @Test
        @DisplayName("passes, when actual is not empty")
        void test0(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            assertThatCode(() -> Asserts.that(file).isNotEmpty())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();

            assertThatCode(() -> Asserts.that(file).isNotEmpty())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isFile'")
    class IsFile {
        @Test
        @DisplayName("passes, when actual is file")
        void test0(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();

            assertThatCode(() -> Asserts.that(file).isFile())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not file")
        void test1(@TempDir Path path) {
            assertThatCode(() -> Asserts.that(path.toFile()).isFile())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotFile'")
    class IsNotFile {
        @Test
        @DisplayName("passes, when actual is not file")
        void test0(@TempDir Path path) {
            assertThatCode(() -> Asserts.that(path.toFile()).isNotFile())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is file")
        void test1(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();

            assertThatCode(() -> Asserts.that(file).isNotFile())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isDirectory'")
    class IsDirectory {
        @Test
        @DisplayName("passes, when actual is directory")
        void test0(@TempDir Path path) {
            assertThatCode(() -> Asserts.that(path.toFile()).isDirectory())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not directory")
        void test1(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();

            assertThatCode(() -> Asserts.that(file).isDirectory())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotDirectory'")
    class IsNotDirectory {
        @Test
        @DisplayName("passes, when actual is not directory")
        void test0(@TempDir Path path) throws IOException {
            File file = Files.createTempFile(path, "temp", DateTimeUtils.now()).toFile();

            assertThatCode(() -> Asserts.that(file).isNotDirectory())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is directory")
        void test1(@TempDir Path path) {
            assertThatCode(() -> Asserts.that(path.toFile()).isNotDirectory())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAbsolute'")
    class IsAbsolute {
        @Test
        @DisplayName("passes, when actual is in absolute path")
        void test0() {
            File file = new File("/usr/local", "temp.txt").getAbsoluteFile();

            assertThatCode(() -> Asserts.that(file).isAbsolute())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is relative path")
        void test1() {
            File file = new File("./usr/local", "temp.txt");

            assertThatCode(() -> Asserts.that(file).isAbsolute())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isRelative'")
    class IsRelative {
        @Test
        @DisplayName("passes, when actual is in relative path")
        void test0() {
            File file = new File("./usr/local", "temp.txt");

            assertThatCode(() -> Asserts.that(file).isRelative())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is absolute path")
        void test1() {
            File file = new File("/usr/local", "temp.txt").getAbsoluteFile();

            assertThatCode(() -> Asserts.that(file).isRelative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isHidden'")
    class IsHidden {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        @DisplayName("passes, when actual is hidden on windows")
        void test0(@TempDir Path path) throws IOException {
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());
            Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

            assertThatCode(() -> Asserts.that(file).isHidden())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        @DisplayName("passes, when actual is hidden on non-windows")
        void test1(@TempDir Path path) throws IOException {
            File file = new File(path.toFile(), ".temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            assertThatCode(() -> Asserts.that(file).isHidden())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not hidden")
        void test2(@TempDir Path path) throws IOException {
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            assertThatCode(() -> Asserts.that(file).isHidden())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotHidden'")
    class IsNotHidden {
        @Test
        @DisplayName("passes, when actual is not hidden")
        void test0(@TempDir Path path) throws IOException {
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            assertThatCode(() -> Asserts.that(file).isNotHidden())
                    .doesNotThrowAnyException();
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        @DisplayName("throws exception, when actual is not hidden on windows")
        void test1(@TempDir Path path) throws IOException {
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());
            Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

            assertThatCode(() -> Asserts.that(file).isNotHidden())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        @DisplayName("throws exception, when actual is not hidden on non-windows")
        void test2(@TempDir Path path) throws IOException {
            File file = new File(path.toFile(), ".temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            assertThatCode(() -> Asserts.that(file).isNotHidden())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'canRead'")
    class CanRead {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'canNotRead'")
    class CanNotRead {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'canWrite'")
    class CanWrite {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'canNotWrite'")
    class CanNotWrite {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'canExecute'")
    class CanExecute {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'canNotExecute'")
    class CanNotExecute {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasLengthOf'")
    class HasLengthOf {
        @Test
        @DisplayName("passes, when actual has the given length")
        void test0(@TempDir Path path) throws IOException {
            File file0 = new File(path.toFile(), "temp0.txt");
            Files.write(file0.toPath(), UUID.randomUUID().toString().getBytes());

            File file1 = new File(path.toFile(), "temp1.txt");
            Files.write(file1.toPath(), UUID.randomUUID().toString().getBytes());

            assertThatCode(() -> Asserts.that(file0)
                    .hasLengthOf(UUID.randomUUID().toString().length())
                    .hasLengthOf(file1))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given length")
        void test1(@TempDir Path path) throws IOException {
            String content = UUID.randomUUID().toString();

            File file0 = new File(path.toFile(), "temp0.txt");
            Files.write(file0.toPath(), content.getBytes());

            File file1 = new File(path.toFile(), "temp1.txt");
            Files.write(file1.toPath(), content.replace("-", "").getBytes());

            assertThatCode(() -> Asserts.that(file0)
                    .hasLengthOf(content.length())
                    .hasLengthOf(file1))
                    .hasMessageStartingWith("It is expected to be the same length, but it isn't.")
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLargerThan'")
    class IsLargerThan {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isSmallerThan'")
    class IsSmallerThan {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasName'")
    class HasName {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasExtension'")
    class HasExtension {
        @Test
        @DisplayName("")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("")
        void test1(@TempDir Path path) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

}