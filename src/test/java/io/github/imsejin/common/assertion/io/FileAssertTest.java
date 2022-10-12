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
import io.github.imsejin.common.constant.DateType;
import io.github.imsejin.common.tool.RandomString;
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
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("FileAssert")
class FileAssertTest {

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0(@TempDir Path path) throws IOException {
            // given
            String filename = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", filename).toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isEmpty());
        }

        @Test
        @DisplayName("throws exception, when actual is not empty")
        void test1(@TempDir Path path) throws IOException {
            // given
            String filename = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", filename).toFile();
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isEmpty());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEmpty'")
    class IsNotEmpty {
        @Test
        @DisplayName("passes, when actual is not empty")
        void test0(@TempDir Path path) throws IOException {
            // given
            String filename = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", filename).toFile();
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotEmpty());
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1(@TempDir Path path) throws IOException {
            // given
            String filename = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", filename).toFile();

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isNotEmpty());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSize'")
    class HasSize {
        @Test
        @DisplayName("passes, when actual has the given size")
        void test0(@TempDir Path path) throws IOException {
            // given
            int fileSize = Math.max(1, new Random().nextInt(64));
            String content = new RandomString().nextString(fileSize);

            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .hasSize(content.length()));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given size")
        void test1(@TempDir Path path) throws IOException {
            // given
            int fileSize = Math.max(1, new Random().nextInt(64));
            String content = new RandomString().nextString(fileSize);

            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .hasSize(content.length() + 1))
                    .withMessageStartingWith("It is expected to have the given length, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSize'")
    class DoesNotHaveSize {
        @Test
        @DisplayName("passes, when actual doesn't have the given size")
        void test0(@TempDir Path path) throws IOException {
            // given
            int fileSize = Math.max(1, new Random().nextInt(64));
            String content = new RandomString().nextString(fileSize);

            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .doesNotHaveSize(content.length() + 1));
        }

        @Test
        @DisplayName("throws exception, when actual has the given size")
        void test1(@TempDir Path path) throws IOException {
            // given
            int fileSize = Math.max(1, new Random().nextInt(64));
            String content = new RandomString().nextString(fileSize);

            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .doesNotHaveSize(content.length()))
                    .withMessageStartingWith("It is expected not to have the given length, but it is");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSameSizeAs'")
    class HasSameSizeAs {
        @Test
        @DisplayName("passes, when actual has the same size as the given file")
        void test0(@TempDir Path path) throws IOException {
            // given
            RandomString randomString = new RandomString();
            int fileSize = Math.max(1, new Random().nextInt(64));

            File actual = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(actual.toPath(), randomString.nextString(fileSize).getBytes());

            File expected = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(expected.toPath(), randomString.nextString(fileSize).getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasSameSizeAs(expected));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the same as the given file")
        void test1(@TempDir Path path) throws IOException {
            // given
            RandomString randomString = new RandomString();
            int fileSize = Math.max(1, new Random().nextInt(64));

            File actual = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(actual.toPath(), randomString.nextString(fileSize).getBytes());

            File expected = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(expected.toPath(), randomString.nextString(fileSize + 1).getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSameSizeAs(expected))
                    .withMessageStartingWith("They are expected to have the same length, but they aren't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSameSizeAs'")
    class DoesNotHaveSameSizeAs {
        @Test
        @DisplayName("passes, when actual doesn't have the same size as the given file")
        void test0(@TempDir Path path) throws IOException {
            // given
            RandomString randomString = new RandomString();
            int fileSize = Math.max(1, new Random().nextInt(64));

            File actual = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(actual.toPath(), randomString.nextString(fileSize).getBytes());

            File expected = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(expected.toPath(), randomString.nextString(fileSize + 1).getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveSameSizeAs(expected));
        }

        @Test
        @DisplayName("throws exception, when actual has the same as the given file")
        void test1(@TempDir Path path) throws IOException {
            // given
            RandomString randomString = new RandomString();
            int fileSize = Math.max(1, new Random().nextInt(64));

            File actual = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(actual.toPath(), randomString.nextString(fileSize).getBytes());

            File expected = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(expected.toPath(), randomString.nextString(fileSize).getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveSameSizeAs(expected))
                    .withMessageStartingWith("They are expected not to have the same length, but they are.");
        }
    }

    // -------------------------------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'exists'")
    class Exists {
        @Test
        @DisplayName("passes, when actual exists")
        void test0(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp", ".txt").toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .exists());
        }

        @Test
        @DisplayName("throws exception, when actual doesn't exist")
        void test1(@TempDir Path path) {
            // given
            String fileName = new RandomString().nextString(8);
            File file = new File(path.toFile(), fileName);

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .exists());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isFile'")
    class IsFile {
        @Test
        @DisplayName("passes, when actual is file")
        void test0(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp", ".txt").toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isFile());
        }

        @Test
        @DisplayName("throws exception, when actual is not file")
        void test1(@TempDir Path path) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(path.toFile())
                    .isFile());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotFile'")
    class IsNotFile {
        @Test
        @DisplayName("passes, when actual is not file")
        void test0(@TempDir Path path) {
            assertThatNoException().isThrownBy(() -> Asserts.that(path.toFile())
                    .isNotFile());
        }

        @Test
        @DisplayName("throws exception, when actual is file")
        void test1(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp", ".txt").toFile();

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isNotFile());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isDirectory'")
    class IsDirectory {
        @Test
        @DisplayName("passes, when actual is directory")
        void test0(@TempDir Path path) {
            assertThatNoException().isThrownBy(() -> Asserts.that(path.toFile())
                    .isDirectory());
        }

        @Test
        @DisplayName("throws exception, when actual is not directory")
        void test1(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp", ".txt").toFile();

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isDirectory());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotDirectory'")
    class IsNotDirectory {
        @Test
        @DisplayName("passes, when actual is not directory")
        void test0(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp", ".txt").toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotDirectory());
        }

        @Test
        @DisplayName("throws exception, when actual is directory")
        void test1(@TempDir Path path) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(path.toFile())
                    .isNotDirectory());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAbsolute'")
    class IsAbsolute {
        @Test
        @DisplayName("passes, when actual is in absolute path")
        void test0() {
            // given
            File file = new File("/usr/local", "temp.txt").getAbsoluteFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isAbsolute());
        }

        @Test
        @DisplayName("throws exception, when actual is relative path")
        void test1() {
            // given
            File file = new File("./usr/local", "temp.txt");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isAbsolute());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isRelative'")
    class IsRelative {
        @Test
        @DisplayName("passes, when actual is in relative path")
        void test0() {
            // given
            File file = new File("./usr/local", "temp.txt");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isRelative());
        }

        @Test
        @DisplayName("throws exception, when actual is absolute path")
        void test1() {
            // given
            File file = new File("/usr/local", "temp.txt").getAbsoluteFile();

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isRelative());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isHidden'")
    class IsHidden {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        @DisplayName("passes, when actual is hidden on windows")
        void test0(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());
            Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isHidden());
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        @DisplayName("passes, when actual is hidden on non-windows")
        void test1(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), ".temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isHidden());
        }

        @Test
        @DisplayName("throws exception, when actual is not hidden")
        void test2(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isHidden());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotHidden'")
    class IsNotHidden {
        @Test
        @DisplayName("passes, when actual is not hidden")
        void test0(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotHidden());
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        @DisplayName("throws exception, when actual is not hidden on windows")
        void test1(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());
            Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isNotHidden());
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        @DisplayName("throws exception, when actual is not hidden on non-windows")
        void test2(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), ".temp.txt");
            Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isNotHidden());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasName'")
    class HasName {
        @Test
        @DisplayName("passes, when actual has the given name")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given name")
        void test1(@TempDir Path path) {
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasExtension'")
    class HasExtension {
        @Test
        @DisplayName("passes, when actual has the given extension")
        void test0(@TempDir Path path) {
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given extension")
        void test1(@TempDir Path path) {
        }
    }

    // -------------------------------------------------------------------------------------------------

}