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
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.composition.SizeComparisonAssertable;
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
import java.util.regex.Pattern;

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
            String fileName = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", fileName).toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isEmpty());
        }

        @Test
        @DisplayName("throws exception, when actual is not empty")
        void test1(@TempDir Path path) throws IOException {
            // given
            String fileName = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", fileName).toFile();
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isEmpty())
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'");
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
            String fileName = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", fileName).toFile();
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotEmpty());
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1(@TempDir Path path) throws IOException {
            // given
            String fileName = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = Files.createTempFile(path, "temp", fileName).toFile();

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isNotEmpty())
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '0'");
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
            String content = new RandomString().nextString(1, 64);

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
            String content = new RandomString().nextString(1, 64);

            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .hasSize(content.length() + 1))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
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
            String content = new RandomString().nextString(1, 64);

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
            String content = new RandomString().nextString(1, 64);

            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .doesNotHaveSize(content.length()))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
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
        @DisplayName("throws exception, when the given file is null")
        void test1(@TempDir Path path) {
            // given
            File file = new File(path.toFile(), "temp-file");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .hasSameSizeAs(null))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: 'null'" +
                            "\n {4}expected\\.size: 'null'");
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the same as the given file")
        void test2(@TempDir Path path) throws IOException {
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
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[^']+'" +
                            "\n {4}expected\\.size: '[0-9]+'");
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
        @DisplayName("throws exception, when the given file is null")
        void test1(@TempDir Path path) {
            // given
            File file = new File(path.toFile(), "temp-file");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .doesNotHaveSameSizeAs(null))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: 'null'" +
                            "\n {4}expected\\.size: 'null'");
        }

        @Test
        @DisplayName("throws exception, when actual has the same as the given file")
        void test2(@TempDir Path path) throws IOException {
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
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[^']+'" +
                            "\n {4}expected\\.size: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @Test
        @DisplayName("passes, when actual is greater than other")
        void test0(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isGreaterThan((long) content.length() - 1));
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isGreaterThan((long) content.length()))
                    .withMessageMatching(Pattern.quote(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isGreaterThanOrEqualTo((long) content.length() - 1)
                    .isGreaterThanOrEqualTo((long) content.length()));
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isGreaterThanOrEqualTo((long) content.length() + 1))
                    .withMessageMatching(Pattern.quote(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @Test
        @DisplayName("passes, when actual is less than other")
        void test0(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isLessThan((long) content.length() + 1));
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isLessThan((long) content.length()))
                    .withMessageMatching(Pattern.quote(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is less than or equal to other")
        void test0(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isLessThanOrEqualTo((long) content.length() + 1)
                    .isLessThanOrEqualTo((long) content.length()));
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1(@TempDir Path path) throws IOException {
            // given
            String content = new RandomString().nextString(1, 64);
            File file = Files.createTempFile(path, "temp", ".txt").toFile();
            Files.write(file.toPath(), content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isLessThanOrEqualTo((long) content.length() - 1))
                    .withMessageMatching(Pattern.quote(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO) +
                            "\n {4}actual: '[^']+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
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
                    .exists())
                    .withMessageMatching(Pattern.quote("It is expected to exist, but it isn't.") +
                            "\n {4}actual: '.+'");
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
                    .isFile())
                    .withMessageMatching(Pattern.quote("It is expected to be file, but it isn't.") +
                            "\n {4}actual: '.+'");
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
                    .isNotFile())
                    .withMessageMatching(Pattern.quote("It is expected not to be file, but it is.") +
                            "\n {4}actual: '.+'");
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
                    .isDirectory())
                    .withMessageMatching(Pattern.quote("It is expected to be directory, but it isn't.") +
                            "\n {4}actual: '.+'");
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
                    .isNotDirectory())
                    .withMessageMatching(Pattern.quote("It is expected not to be directory, but it is.") +
                            "\n {4}actual: '.+'");
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
                    .isAbsolute())
                    .withMessageMatching(Pattern.quote("It is expected to be absolute, but it isn't.") +
                            "\n {4}actual: '.+'");
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
                    .isRelative())
                    .withMessageMatching(Pattern.quote("It is expected to be relative, but it isn't.") +
                            "\n {4}actual: '.+'");
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
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());
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
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isHidden());
        }

        @Test
        @DisplayName("throws exception, when actual is not hidden")
        void test2(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), "temp.txt");
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isHidden())
                    .withMessageMatching(Pattern.quote("It is expected to be hidden, but it isn't.") +
                            "\n {4}actual: '.+'");
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
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());

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
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());
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
            Files.write(file.toPath(), new RandomString().nextString(16, 33).getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .isNotHidden())
                    .withMessageMatching(Pattern.quote("It is expected not to be hidden, but it is.") +
                            "\n {4}actual: '.+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasName'")
    class HasName {
        @Test
        @DisplayName("passes, when actual has the given name")
        void test0(@TempDir Path path) {
            // given
            String fileName = new RandomString().nextString(1, 64);
            File file = path.resolve(fileName).toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .hasName(fileName));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given name")
        void test1(@TempDir Path path) {
            // given
            String fileName = new RandomString().nextString(1, 64);
            File file = path.resolve(fileName).toFile();

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .hasName(fileName + fileName))
                    .withMessageMatching(Pattern.quote("It is expected to have the given name, but it isn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.name: '[^/\\\\]+'" +
                            "\n {4}expected: '.+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasExtension'")
    class HasExtension {
        @Test
        @DisplayName("passes, when actual has the given extension")
        void test0(@TempDir Path path) {
            // given
            String fileName = new RandomString().nextString(1, 64) + ".dat";
            File file = path.resolve(fileName).toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .hasExtension("dat"));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given extension")
        void test1(@TempDir Path path) {
            // given
            String fileName = new RandomString().nextString(1, 64) + ".dat";
            File file = path.resolve(fileName).toFile();

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(file)
                    .hasExtension("log"))
                    .withMessageMatching(Pattern.quote("It is expected to have the given extension, but it isn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}actual\\.extension: '[^/\\\\]+'" +
                            "\n {4}expected: '.+'");
        }
    }

}
