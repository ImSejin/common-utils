/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.assertion.nio.file;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.tool.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@FileSystemSource
@DisplayName("PathAssert")
class PathAssertTest {

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isEmpty());
        }

        @Test
        @DisplayName("throws exception, when actual is not empty")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Files.write(actual, new RandomString().nextString(16, 33).getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isEmpty())
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEmpty'")
    class IsNotEmpty {
        @Test
        @DisplayName("passes, when actual is not empty")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Files.write(actual, new RandomString().nextString(16, 33).getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isNotEmpty());
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNotEmpty())
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '0'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSize'")
    class HasSize {
        @Test
        @DisplayName("passes, when actual has the given size")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasSize(content.length()));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given size")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSize(content.length() + 1))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
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
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveSize(content.length() + 1));
        }

        @Test
        @DisplayName("throws exception, when actual has the given size")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveSize(content.length()))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
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
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Path expected = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());
            Files.write(expected, content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasSameSizeAs(expected));
        }

        @Test
        @DisplayName("throws exception, when the given file is null")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSameSizeAs(null))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: 'null'" +
                            "\n {4}expected\\.size: 'null'");
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the same as the given file")
        void test2(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Path expected = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());
            Files.write(expected, (content + content).getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSameSizeAs(expected))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}expected\\.size: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSameSizeAs'")
    class DoesNotHaveSameSizeAs {
        @Test
        @DisplayName("passes, when actual doesn't have the same size as the given file")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Path expected = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());
            Files.write(expected, (content + content).getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveSameSizeAs(expected));
        }

        @Test
        @DisplayName("throws exception, when the given file is null")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveSameSizeAs(null))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: 'null'" +
                            "\n {4}expected\\.size: 'null'");
        }

        @Test
        @DisplayName("throws exception, when actual has the same as the given file")
        void test2(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Path expected = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());
            Files.write(expected, content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveSameSizeAs(expected))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}expected\\.size: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeGreaterThan'")
    class HasSizeGreaterThan {
        @Test
        @DisplayName("passes, when actual has size greater than the given size")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeGreaterThan(content.length() - 1));
        }

        @Test
        @DisplayName("throws exception, when actual has size less than or same as the given size")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeGreaterThan(content.length()))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeGreaterThanOrEqualTo'")
    class HasSizeGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual has size greater than or same as the given size")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeGreaterThanOrEqualTo(content.length() - 1)
                    .hasSizeGreaterThanOrEqualTo(content.length()));
        }

        @Test
        @DisplayName("throws exception, when actual has size less than the given size")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeGreaterThanOrEqualTo(content.length() + 1))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN_OR_EQUAL_TO) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeLessThan'")
    class HasSizeLessThan {
        @Test
        @DisplayName("passes, when actual has size less than the given size")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeLessThan(content.length() + 1));
        }

        @Test
        @DisplayName("throws exception, when actual has size greater than or same as the given size")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeLessThan(content.length()))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.size: '[0-9]+'" +
                            "\n {4}expected: '[0-9]+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeLessThanOrEqualTo'")
    class HasSizeLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual has size less than or same as the given size")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeLessThanOrEqualTo(content.length() + 1)
                    .hasSizeLessThanOrEqualTo(content.length()));
        }

        @Test
        @DisplayName("throws exception, when actual has size greater than the given size")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");
            String content = new RandomString().nextString(1, 64);
            Files.write(actual, content.getBytes());

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasSizeLessThanOrEqualTo(content.length() - 1))
                    .withMessageMatching(Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN_OR_EQUAL_TO) +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
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
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempDirectory(path, "temp");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .exists());
        }

        @Test
        @DisplayName("throws exception, when actual doesn't exist")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path actual = fileSystem.getPath("/", "usr");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .exists())
                    .withMessageMatching(Pattern.quote("It is expected to exist, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isRegularFile'")
    class IsRegularFile {
        @Test
        @DisplayName("passes, when actual is file")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isRegularFile());
        }

        @Test
        @DisplayName("throws exception, when actual is not file")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempDirectory(path, "temp");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isRegularFile())
                    .withMessageMatching(Pattern.quote("It is expected to be regular file, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotRegularFile'")
    class IsNotRegularFile {
        @Test
        @DisplayName("passes, when actual is not file")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempDirectory(path, "temp");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isNotRegularFile());
        }

        @Test
        @DisplayName("throws exception, when actual is file")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNotRegularFile())
                    .withMessageMatching(Pattern.quote("It is expected not to be regular file, but it is.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isDirectory'")
    class IsDirectory {
        @Test
        @DisplayName("passes, when actual is directory")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempDirectory(path, "temp");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isDirectory());
        }

        @Test
        @DisplayName("throws exception, when actual is not directory")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isDirectory())
                    .withMessageMatching(Pattern.quote("It is expected to be directory, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotDirectory'")
    class IsNotDirectory {
        @Test
        @DisplayName("passes, when actual is not directory")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isNotDirectory());
        }

        @Test
        @DisplayName("throws exception, when actual is directory")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempDirectory(path, "temp");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNotDirectory())
                    .withMessageMatching(Pattern.quote("It is expected not to be directory, but it is.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
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
            Path actual = Paths.get("/", "usr", "local");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isAbsolute());
        }

        @Test
        @DisplayName("throws exception, when actual is relative path")
        void test1() {
            // given
            Path actual = Paths.get("./", "usr", "local");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isAbsolute())
                    .withMessageMatching(Pattern.quote("It is expected to be absolute, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
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
            Path actual = Paths.get("./", "usr", "local");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isRelative());
        }

        @Test
        @DisplayName("throws exception, when actual is absolute path")
        void test1() {
            // given
            Path actual = Paths.get("/", "usr", "local");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isRelative())
                    .withMessageMatching(Pattern.quote("It is expected to be relative, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
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
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Files.setAttribute(actual, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isHidden());
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        @DisplayName("passes, when actual is hidden on non-windows")
        void test1(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, ".temp", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isHidden());
        }

        @Test
        @DisplayName("throws exception, when actual is not hidden")
        void test2(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isHidden())
                    .withMessageMatching(Pattern.quote("It is expected to be hidden, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotHidden'")
    class IsNotHidden {
        @Test
        @DisplayName("passes, when actual is not hidden")
        void test0(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, "temp", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isNotHidden());
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        @DisplayName("throws exception, when actual is not hidden on windows")
        void test1(@TempDir Path path) throws IOException {
            // given
            Path actual = Files.createTempFile(path, "temp", ".dat");
            Files.setAttribute(actual, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNotHidden());
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        @DisplayName("throws exception, when actual is not hidden on non-windows")
        void test2(@Memory FileSystem fileSystem) throws IOException {
            // given
            Path path = fileSystem.getPath("/");
            Path actual = Files.createTempFile(path, ".temp", ".dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNotHidden())
                    .withMessageMatching(Pattern.quote("It is expected not to be hidden, but it is.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasFileName'")
    class HasFileName {
        @Test
        @DisplayName("passes, when actual has the given file name")
        void test0() {
            // given
            Path actual = Paths.get("/", "usr", "local", "temp-file.dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasFileName("temp-file.dat"));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given file name")
        void test1() {
            // given
            Path actual = Paths.get("/", "usr", "local", "temp-file.dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasFileName("temp-file"))
                    .withMessageMatching(Pattern.quote("It is expected to have the given file name, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.fileName: '[^/\\\\]+'" +
                            "\n {4}expected: '.+'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasExtension'")
    class HasExtension {
        @Test
        @DisplayName("passes, when actual has the given file extension")
        void test0() {
            // given
            Path actual = Paths.get("/", "usr", "local", "temp-file.dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasExtension("dat"));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given file extension")
        void test1() {
            // given
            Path actual = Paths.get("/", "usr", "local", "temp-file.dat");

            // expect
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .hasExtension("log"))
                    .withMessageMatching(Pattern.quote("It is expected to have the given file extension, but it isn't.") +
                            "\n {4}actual: '\\S*(([/\\\\])\\S+?)+'" +
                            "\n {4}actual\\.extension: '[^/\\\\]+'" +
                            "\n {4}expected: '.+'");
        }
    }

}
