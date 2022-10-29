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

package io.github.imsejin.common.assertion;

import io.github.imsejin.common.constant.OS;
import io.github.imsejin.common.tool.RandomString;
import io.github.imsejin.common.util.ArrayUtils;
import io.github.imsejin.common.util.CollectionUtils;
import io.github.imsejin.common.util.MathUtils;
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

/**
 * Test methods named 'asXXX' in assertion class.
 */
class ConversionTest {

    // java.lang ---------------------------------------------------------------------------------------

    @Nested
    class ObjectAssert {
        @Test
        @DisplayName("asString(): Object -> String")
        void asString() {
            // given
            UUID uuid = UUID.randomUUID();
            Optional<UUID> optional = Optional.of(uuid);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(optional)
                    .isNotNull().isEqualTo(Optional.of(uuid)).hasValue(uuid)
                    .isPresent().is(it -> it.orElseGet(UUID::randomUUID).version() == 4)
                    .asString().matches("Optional\\[[a-z0-9]{8}(-[a-z0-9]{4}){3}-[a-z0-9]{12}]")
                    .isEqualTo("Optional[" + uuid + "]"));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(Optional.empty())
                            .describedAs("Description of assertion: {0}", Optional.empty())
                            .thrownBy(RuntimeException::new).isNotNull().isAbsent().doesNotHaveValue(null)
                            .asString().isNotNull().matches("Optional\\[.*]"))
                    .withMessageStartingWith("Description of assertion: " + Optional.empty());
        }

        @Test
        @DisplayName("asClass(): Object -> Class")
        void asClass() {
            // given
            Object object = new Object();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(object)
                    .isNotNull().isSameAs(object).isNotEqualTo(new Object())
                    .asClass().isSameAs(Object.class).isEqualTo(object.getClass())
                    .asClass().isSameAs(Class.class).isEqualTo(object.getClass().getClass()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(object)
                            .describedAs("Description of assertion: {0}", object)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asClass().isAnonymousClass())
                    .withMessageStartingWith("Description of assertion: " + object);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class ArrayAssert {
        @Test
        @DisplayName("asLength(): Array -> Integer")
        void asLength() {
            // given
            String[] array = {"a", "b", "c", "d"};

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(array)
                    .isNotNull().doesNotContainNull().isNotEmpty().doesNotContainAll(new String[]{"A", "B", "C", "D"})
                    .is(them -> Arrays.stream(them).allMatch(it -> Character.isLowerCase(it.charAt(0))))
                    .asLength().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(4));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(array)
                            .describedAs("Description of assertion: {0}", ArrayUtils.toString(array))
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLength().isEqualTo(array.length - 1))
                    .withMessageStartingWith("Description of assertion: " + ArrayUtils.toString(array));
        }

        @Test
        @DisplayName("asList(): Array -> List")
        void asList() {
            // given
            Integer[] array = {0, 2, 4, 8, 16, 32, 64, 128};

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(array)
                    .isNotNull().doesNotContainNull().isNotEmpty().doesNotHaveDuplicates()
                    .doesNotContainAll(new Integer[]{3, 9, 27, 81, 243, 1029, 3087, 10261})
                    .is(them -> Arrays.stream(them).noneMatch(MathUtils::isOdd))
                    .asList().doesNotContainNull().hasSize(array.length).doesNotHaveDuplicates()
                    .doesNotContainAll(Arrays.asList(3, 9, 27, 81, 243, 1029, 3087, 10261))
                    .is(them -> them.stream().noneMatch(MathUtils::isOdd)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(array)
                            .describedAs("Description of assertion: {0}", ArrayUtils.toString(array))
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asList().hasSize(array.length).doesNotContainAll(Arrays.asList(0, 3, 9, 27, 81, 243)))
                    .withMessageStartingWith("Description of assertion: " + ArrayUtils.toString(array));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class CharSequenceAssert {
        @Test
        @DisplayName("asLength(): CharSequence -> Integer")
        void asLength() {
            // given
            CharSequence charSequence = getClass().getPackage().getName();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(charSequence)
                    .isNotNull().isNotEmpty().doesNotHaveSameSizeAs(new StringBuilder())
                    .asLength().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(charSequence.length()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(charSequence)
                            .describedAs("Description of assertion: {0}", charSequence)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLength().isCloseTo(charSequence.length() + 1, 0))
                    .withMessageStartingWith("Description of assertion: " + charSequence);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class ClassAssert {
        @Test
        @DisplayName("asSuperclass(): Class -> Class")
        void asSuperclass() {
            assertThatNoException().isThrownBy(() -> Asserts.that(NumberFormatException.class).isNotNull()
                    .asSuperclass().isEqualTo(IllegalArgumentException.class)
                    .isSuperclassOf(NumberFormatException.class).isSubclassOf(RuntimeException.class)
                    .asSuperclass().isEqualTo(RuntimeException.class)
                    .isSuperclassOf(IllegalArgumentException.class).isSubclassOf(Exception.class)
                    .asSuperclass().isEqualTo(Exception.class)
                    .isSuperclassOf(RuntimeException.class).isSubclassOf(Throwable.class)
                    .asSuperclass().isEqualTo(Throwable.class)
                    .isSuperclassOf(Exception.class).isSubclassOf(Object.class)
                    .asSuperclass().isEqualTo(Object.class)
                    .isSuperclassOf(Throwable.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(NumberFormatException.class)
                            .describedAs("Description of assertion")
                            .thrownBy(RuntimeException::new).isAssignableFrom(IllegalArgumentException.class)
                            .asSuperclass().isAnonymousClass())
                    .withMessageStartingWith("Description of assertion");
        }

        @Test
        @DisplayName("asPackage(): Class -> Package")
        void asPackage() {
            // given
            Class<?> clazz = io.github.imsejin.common.assertion.lang.ObjectAssert.class;

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(clazz).isNotNull()
                    .asPackage().isNotNull().isSubPackageOf(Asserts.class.getPackage())
                    .returns("io.github.imsejin.common.assertion.lang", Package::getName));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(clazz)
                            .describedAs("Description of assertion")
                            .thrownBy(RuntimeException::new).isAbstractClass()
                            .asPackage().isSubPackageOf(OS.class.getPackage()))
                    .withMessageStartingWith("Description of assertion");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class PackageAssert {
        @Test
        @DisplayName("asName(): Package -> String")
        void asName() {
            // given
            Package pack = io.github.imsejin.common.assertion.lang.ClassAssert.class.getPackage();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(pack).isNotNull()
                    .asName().returns("io/github/imsejin/common/assertion/lang", it -> it.replace('.', '/')));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(pack)
                            .describedAs("Description of assertion: {0}", pack)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asName().isNumeric())
                    .withMessageStartingWith("Description of assertion: " + pack);
        }
    }

    // java.io -----------------------------------------------------------------------------------------

    @Nested
    class FileAssert {
        @Test
        @DisplayName("asParentFile(): File -> File")
        void asParentFile(@TempDir Path path) {
            // given
            String fileName = new RandomString().nextString(8);
            File file = new File(path.toFile(), fileName);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotNull().isEmpty()
                    .asParentFile().exists().isDirectory());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(file)
                            .describedAs("Description of assertion: {0}", file)
                            .thrownBy(RuntimeException::new).isEmpty()
                            .asParentFile().exists().is(File::isFile))
                    .withMessageStartingWith("Description of assertion: " + file);
        }

        @Test
        @DisplayName("asPath(): File -> Path")
        void asPath(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp-", ".dat").toFile();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotNull().isEmpty()
                    .asPath().isRegularFile().isEmpty());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(file)
                            .describedAs("Description of assertion: {0}", file)
                            .thrownBy(RuntimeException::new).isEmpty()
                            .asPath().exists().is(Files::isDirectory))
                    .withMessageStartingWith("Description of assertion: " + file);
        }

        @Test
        @DisplayName("asLength(): File -> Long")
        void asLength(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp-", ".dat").toFile();
            String content = getClass().getPackage().getName();
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotNull().exists().hasSize(content.length())
                    .asLength().isGreaterThan(1L).isLessThan(Long.MAX_VALUE).isEqualTo((long) content.length()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(file)
                            .describedAs("Description of assertion: {0}", file)
                            .thrownBy(RuntimeException::new).exists()
                            .asLength().isZeroOrNegative())
                    .withMessageStartingWith("Description of assertion: " + file);
        }

        @Test
        @DisplayName("asName(): File -> String")
        void asName(@TempDir Path path) throws IOException {
            // given
            File file = Files.createTempFile(path, "temp-", ".dat").toFile();
            String content = getClass().getPackage().getName();
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotNull().exists().isNotEmpty().hasSize(content.length())
                    .asName().hasText().startsWith("temp-").endsWith(".dat"));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(file)
                            .describedAs("Description of assertion: {0}", file)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asName().isUpperCase())
                    .withMessageStartingWith("Description of assertion: " + file);
        }
    }

    // java.nio.file ----------------------------------------------------------------------------------------

    @Nested
    class PathAssert {
        @Test
        @DisplayName("asParent(): Path -> Path")
        void asParent(@TempDir Path path) throws IOException {
            // given
            Path filePath = Files.createTempFile(path, "temp-", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(filePath)
                    .isNotNull().isEmpty()
                    .asParent().exists().isDirectory());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(filePath)
                            .describedAs("Description of assertion: {0}", filePath)
                            .thrownBy(RuntimeException::new).isEmpty()
                            .asParent().exists().is(Files::isRegularFile))
                    .withMessageStartingWith("Description of assertion: " + filePath);
        }

        @Test
        @DisplayName("asFile(): Path -> File")
        void asFile(@TempDir Path path) throws IOException {
            // given
            Path filePath = Files.createTempFile(path, "temp-", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(filePath)
                    .isNotNull().isEmpty()
                    .asFile().exists().isFile());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(filePath)
                            .describedAs("Description of assertion: {0}", filePath)
                            .thrownBy(RuntimeException::new).isEmpty()
                            .asFile().exists().is(File::isDirectory))
                    .withMessageStartingWith("Description of assertion: " + filePath);
        }

        @Test
        @DisplayName("asFileName(): Path -> String")
        void asFileName(@TempDir Path path) throws IOException {
            // given
            Path filePath = Files.createTempFile(path, "temp-", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(filePath)
                    .isNotNull().isEmpty()
                    .asFileName().isNotEmpty().startsWith("temp-"));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(filePath)
                            .describedAs("Description of assertion: {0}", filePath)
                            .thrownBy(RuntimeException::new).isEmpty()
                            .asFileName().isNotEmpty().isNot(it -> it.split("\\.").length == 2))
                    .withMessageStartingWith("Description of assertion: " + filePath);
        }

        @Test
        @DisplayName("asNameCount(): Path -> Integer")
        void asNameCount(@TempDir Path path) throws IOException {
            // given
            Path filePath = Files.createTempFile(path, "temp-", ".dat");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(filePath)
                    .isNotNull().isEmpty()
                    .asNameCount().isPositive().isGreaterThan(2));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(filePath)
                            .describedAs("Description of assertion: {0}", filePath)
                            .thrownBy(RuntimeException::new).isEmpty()
                            .asNameCount().isPositive().is(it -> it == 2))
                    .withMessageStartingWith("Description of assertion: " + filePath);
        }
    }

    // java.net ----------------------------------------------------------------------------------------

    @Nested
    class UrlAssert {
        @Test
        @DisplayName("asHost(): URL -> String")
        void asHost() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url)
                    .isNotNull().isEqualTo(new URL("https://www.github.com/"))
                    .asHost().startsWith("www.github"));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(url)
                            .describedAs("Description of assertion: {0}", url)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asHost().startsWith("github.com"))
                    .withMessageStartingWith("Description of assertion: " + url);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "http://localhost:8080  | 8080",
                "https://www.github.com | 443",
        }, delimiter = '|')
        @DisplayName("asPort(): URL -> Integer")
        void asPort(URL url, int port) {
            assertThatNoException().isThrownBy(() -> Asserts.that(url)
                    .isNotNull().isEqualTo(new URL(url.toString()))
                    .asPort().isEqualTo(port));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(url)
                            .describedAs("Description of assertion: {0}", url)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asPort().isNegative())
                    .withMessageStartingWith("Description of assertion: " + url);
        }

        @Test
        @DisplayName("asPath(): URL -> String")
        void asPath() throws MalformedURLException {
            // given
            URL url = new URL("https://www.github.com/imsejin/common-utils");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url)
                    .isNotNull().isEqualTo(new URL("https://www.github.com/imsejin/common-utils"))
                    .asPath().isEqualTo("/imsejin/common-utils"));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(url)
                            .describedAs("Description of assertion: {0}", url)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asPath().isUpperCase())
                    .withMessageStartingWith("Description of assertion: " + url);
        }
    }

    // java.time ---------------------------------------------------------------------------------------

    @Nested
    class InstantAssert {
        @Test
        @DisplayName("asDate(): Instant -> Date")
        void asDate() {
            // given
            Instant instant = Instant.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(instant)
                    .isNotNull().isAfter(Instant.from(instant.minusSeconds(10)))
                    .asDate().isEqualTo(new java.sql.Timestamp(instant.toEpochMilli()))
                    .isBefore(new Date(instant.toEpochMilli() + 1)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(instant)
                            .describedAs("Description of assertion: {0}", instant)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asDate().isEqualTo(new java.sql.Date(instant.toEpochMilli()))
                            .isEqualTo(new java.sql.Timestamp(0)))
                    .withMessageStartingWith("Description of assertion: " + instant);
        }

        @Test
        @DisplayName("asEpochMilli(): Instant -> Long")
        void asEpochMilli() {
            // given
            Instant instant = Instant.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(instant)
                    .isNotNull().isAfter(Instant.from(instant.minusSeconds(10)))
                    .asEpochMilli().isLessThanOrEqualTo(Instant.now().toEpochMilli()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(instant)
                            .describedAs("Description of assertion: {0}", instant)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asEpochMilli().isEqualTo(Long.MAX_VALUE))
                    .withMessageStartingWith("Description of assertion: " + instant);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class LocalTimeAssert {
        @Test
        @DisplayName("asSecondOfDay(): LocalTime -> Integer")
        void asSecondOfDay() {
            // given
            LocalTime time = LocalTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(time)
                    .isNotNull().isBefore(time.plusSeconds(1))
                    .asSecondOfDay().isBetween(0, Integer.MAX_VALUE)
                    .is(it -> it == time.toSecondOfDay()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(time)
                            .describedAs("Description of assertion: {0}", time)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asSecondOfDay().isGreaterThan(LocalTime.now().toSecondOfDay()))
                    .withMessageStartingWith("Description of assertion: " + time);
        }

        @Test
        @DisplayName("asNanoOfDay(): LocalTime -> Long")
        void asNanoOfDay() {
            // given
            LocalTime time = LocalTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(time)
                    .isNotNull().isBefore(time.plusSeconds(1))
                    .asNanoOfDay().isBetween(0L, Long.MAX_VALUE)
                    .is(it -> it == time.toNanoOfDay()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(time)
                            .describedAs("Description of assertion: {0}", time)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asNanoOfDay().isGreaterThan(LocalTime.now().toNanoOfDay()))
                    .withMessageStartingWith("Description of assertion: " + time);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class OffsetDateTimeAssert {
        @Test
        @DisplayName("asLocalDate(): OffsetDateTime -> ChronoLocalDate")
        void asLocalDate() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asLocalDate().isBeforeOrEqualTo(LocalDate.now())
                    .is(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .describedAs("Description of assertion: {0}", offsetDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalDate().isAfter(LocalDate.now()))
                    .withMessageStartingWith("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asLocalDateTime(): OffsetDateTime -> ChronoLocalDateTime")
        void asLocalDateTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asLocalDateTime().isBeforeOrEqualTo(LocalDateTime.now())
                    .is(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .describedAs("Description of assertion: {0}", offsetDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalDateTime().isAfter(LocalDateTime.now().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asZonedDateTime(): OffsetDateTime -> ChronoZonedDateTime")
        void asZonedDateTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asZonedDateTime().isBeforeOrEqualTo(offsetDateTime.toZonedDateTime())
                    .is(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .describedAs("Description of assertion: {0}", offsetDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asZonedDateTime().isAfter(ZonedDateTime.now().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asLocalTime(): OffsetDateTime -> LocalTime")
        void asLocalTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneOffset.UTC);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isEqualTo(OffsetDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneOffset.UTC))
                    .asLocalTime().isEqualTo(LocalTime.MIN).isBeforeNoon().isMidnight());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .describedAs("Description of assertion: {0}", offsetDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalTime().isEqualTo(LocalTime.MIN).isNoon())
                    .withMessageStartingWith("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asOffsetTime(): OffsetDateTime -> OffsetTime")
        void asOffsetTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asOffsetTime().isBeforeOrEqualTo(OffsetTime.now())
                    .is(it -> it.isAfter(OffsetTime.MIN)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .describedAs("Description of assertion: {0}", offsetDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asOffsetTime().isSameOffset(null))
                    .withMessageStartingWith("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asInstant(): OffsetDateTime -> Instant")
        void asInstant() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isAfter(OffsetDateTime.from(LocalDate.MIN.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())))
                    .asInstant().isBeforeOrEqualTo(Instant.now()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .describedAs("Description of assertion: {0}", offsetDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asInstant().isAfter(Instant.now().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + offsetDateTime);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class OffsetTimeAssert {
        @Test
        @DisplayName("asLocalTime(): OffsetTime -> ChronoLocalDate")
        void asLocalTime() {
            // given
            OffsetTime offsetTime = OffsetTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetTime)
                    .isNotNull().isBefore(offsetTime.plusSeconds(1))
                    .asLocalTime().isBeforeOrEqualTo(LocalTime.now())
                    .isInstanceOf(LocalTime.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetTime)
                            .describedAs("Description of assertion: {0}", offsetTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalTime().isAfter(LocalTime.now().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + offsetTime);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class YearAssert {
        @Test
        @DisplayName("asValue(): Year -> Integer")
        void asValue() {
            // given
            Year year = Year.of(2022);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(year)
                    .isNotNull().isBefore(year.plusYears(1))
                    .asValue().isGreaterThan(year.getValue() - 1)
                    .isInstanceOf(Integer.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(year)
                            .describedAs("Description of assertion: {0}", year)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asValue().isZeroOrNegative())
                    .withMessageStartingWith("Description of assertion: " + year);
        }

        @Test
        @DisplayName("asLength(): Year -> Integer")
        void asLength() {
            // given
            Year year = Year.of(2020);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(year)
                    .isNotNull().isBefore(year.plusYears(1))
                    .asLength().isGreaterThan(year.plusYears(1).length())
                    .isInstanceOf(Integer.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(year)
                            .describedAs("Description of assertion: {0}", year)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLength().isEqualTo(365))
                    .withMessageStartingWith("Description of assertion: " + year);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class YearMonthAssert {
        @Test
        @DisplayName("asYear(): YearMonth -> Year")
        void asYear() {
            // given
            YearMonth yearMonth = YearMonth.of(2022, Month.JANUARY);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(yearMonth)
                    .isNotNull().isBefore(yearMonth.plusMonths(1))
                    .asYear().isAfter(Year.of(yearMonth.minusYears(1).getYear()))
                    .isInstanceOf(Year.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(yearMonth)
                            .describedAs("Description of assertion: {0}", yearMonth)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asYear().isLeapYear())
                    .withMessageStartingWith("Description of assertion: " + yearMonth);
        }

        @Test
        @DisplayName("asMonth(): YearMonth -> Month")
        void asMonth() {
            // given
            YearMonth yearMonth = YearMonth.of(2020, Month.SEPTEMBER);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(yearMonth)
                    .isNotNull().isBefore(yearMonth.plusYears(1))
                    .asMonth().isAfter(Month.AUGUST)
                    .isInstanceOf(Month.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(yearMonth)
                            .describedAs("Description of assertion: {0}", yearMonth)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asMonth().is(it -> it.ordinal() == 9))
                    .withMessageStartingWith("Description of assertion: " + yearMonth);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class MonthAssert {
        @Test
        @DisplayName("asValue(): Month -> Integer")
        void asValue() {
            // given
            Month month = Month.SEPTEMBER;

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(month)
                    .isNotNull().isBefore(month.plus(1))
                    .asValue().isEqualTo(9)
                    .isInstanceOf(Integer.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(month)
                            .describedAs("Description of assertion: {0}", month)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asValue().isEqualTo(month.ordinal()))
                    .withMessageStartingWith("Description of assertion: " + month);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class MonthDayAssert {
        @Test
        @DisplayName("asMonth(): MonthDay -> Month")
        void asMonth() {
            // given
            MonthDay monthDay = MonthDay.of(Month.SEPTEMBER, 11);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(monthDay)
                    .isNotNull().isBefore(monthDay.withDayOfMonth(12))
                    .asMonth().isEqualTo(monthDay.getMonth())
                    .isInstanceOf(Month.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(monthDay)
                            .describedAs("Description of assertion: {0}", monthDay)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asMonth().isBefore(monthDay.with(Month.JUNE).getMonth()))
                    .withMessageStartingWith("Description of assertion: " + monthDay);
        }

        @Test
        @DisplayName("asDayOfMonth(): MonthDay -> Integer")
        void asDayOfMonth() {
            // given
            MonthDay monthDay = MonthDay.of(Month.SEPTEMBER, 11);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(monthDay)
                    .isNotNull().isEqualTo(monthDay.withDayOfMonth(11))
                    .asDayOfMonth().isBetween(1, 31)
                    .isInstanceOf(Integer.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(monthDay)
                            .describedAs("Description of assertion: {0}", monthDay)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asDayOfMonth().isLessThan(monthDay.getDayOfMonth()))
                    .withMessageStartingWith("Description of assertion: " + monthDay);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class DurationAssert {
        @Test
        @DisplayName("asTotalSeconds(): Duration -> BigDecimal")
        void asTotalSeconds() {
            // given
            Duration duration = Duration.ofHours(23).plusMinutes(59).plusSeconds(59).plusNanos(999999999);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(duration)
                    .isNotNull().isLessThan(Duration.ofHours(24))
                    .asTotalSeconds().isStrictlyBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(LocalTime.MAX.toSecondOfDay() + 1))
                    .isInstanceOf(BigDecimal.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(duration)
                            .describedAs("Description of assertion: {0}", duration)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asTotalSeconds().isEqualTo(new BigDecimal(LocalTime.MAX.toSecondOfDay() + ".999999998")))
                    .withMessageStartingWith("Description of assertion: " + duration);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class PeriodAssert {
        @Test
        @DisplayName("asTotalDays(): Period -> Integer")
        void asTotalDays() {
            // given
            Period period = Period.of(2, 60, 32);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(period)
                    .isNotNull().isGreaterThan(Period.ofYears(7))
                    .asTotalDays().isEqualTo(2552)
                    .isInstanceOf(int.class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(period)
                            .describedAs("Description of assertion: {0}", period)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asTotalDays().isEqualTo(period.getDays()))
                    .withMessageStartingWith("Description of assertion: " + period);
        }
    }

    // java.time.chrono --------------------------------------------------------------------------------

    @Nested
    class ChronoLocalDateAssert {
        @Test
        @DisplayName("asYearMonth(): ChronoLocalDate -> YearMonth")
        void asYearMonth() {
            // given
            LocalDate localDate = LocalDate.of(2000, 8, 31);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(localDate)
                    .isNotNull().isBefore(localDate.plusDays(1))
                    .asYearMonth().isEqualTo(YearMonth.from(localDate))
                    .is(it -> it.atDay(localDate.getDayOfMonth()).equals(localDate))
                    .isLeapYear());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(localDate)
                            .describedAs("Description of assertion: {0}", localDate)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .isNotNull().isAfter(localDate.minusDays(1))
                            .asYearMonth().isEqualTo(YearMonth.from(localDate))
                            .isNotLeapYear())
                    .withMessageStartingWith("Description of assertion: " + localDate);
        }

        @Test
        @DisplayName("asMonthDay(): ChronoLocalDate -> MonthDay")
        void asMonthDay() {
            // given
            LocalDate localDate = LocalDate.of(2015, 5, 5);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(localDate)
                    .isNotNull().isEqualTo(YearMonth.from(localDate).atDay(5))
                    .asMonthDay().isAfter(MonthDay.from(localDate).withDayOfMonth(1))
                    .is(it -> it.atYear(localDate.getYear()).equals(localDate))
                    .isEqualTo(MonthDay.from(localDate)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(localDate)
                            .describedAs("Description of assertion: {0}", localDate)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .isNotNull().isBefore(YearMonth.from(localDate).atEndOfMonth())
                            .asMonthDay().isNotEqualTo(MonthDay.from(localDate)))
                    .withMessageStartingWith("Description of assertion: " + localDate);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class ChronoLocalDateTimeAssert {
        @Test
        @DisplayName("asLocalDate(): ChronoLocalDateTime -> ChronoLocalDate")
        void asLocalDate() {
            // given
            LocalDateTime dateTime = LocalDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(dateTime)
                    .isNotNull().isBefore(dateTime.plusSeconds(1))
                    .asLocalDate().isBeforeOrEqualTo(LocalDate.now())
                    .is(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(dateTime)
                            .describedAs("Description of assertion: {0}", dateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalDate().isAfter(LocalDate.now()))
                    .withMessageStartingWith("Description of assertion: " + dateTime);
        }

        @Test
        @DisplayName("asLocalTime(): ChronoLocalDateTime -> LocalTime")
        void asLocalTime() {
            // given
            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(dateTime)
                    .isNotNull().isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                    .asLocalTime().isEqualTo(LocalTime.MIN).isBeforeNoon().isMidnight());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(dateTime)
                            .describedAs("Description of assertion: {0}", dateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalTime().isAfter(LocalTime.MAX))
                    .withMessageStartingWith("Description of assertion: " + dateTime);
        }

        @Test
        @DisplayName("asInstant(): ChronoLocalDateTime -> Instant")
        void asInstant() {
            // given
            LocalDateTime dateTime = LocalDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(dateTime)
                    .isNotNull().isAfter(LocalDateTime.from(LocalDate.MIN.atTime(LocalTime.MAX)))
                    .asInstant().isBeforeOrEqualTo(Instant.now()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(dateTime)
                            .describedAs("Description of assertion: {0}", dateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asInstant().isEqualTo(Instant.now()))
                    .withMessageStartingWith("Description of assertion: " + dateTime);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class ChronoZonedDateTimeAssert {
        @Test
        @DisplayName("asLocalDate(): ChronoZonedDateTime -> ChronoLocalDate")
        void asLocalDate() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asLocalDate().isBeforeOrEqualTo(LocalDate.now())
                    .is(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .describedAs("Description of assertion: {0}", zonedDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalDate().isBefore(LocalDate.now()))
                    .withMessageStartingWith("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asLocalDateTime(): ChronoZonedDateTime -> ChronoLocalDateTime")
        void asLocalDateTime() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asLocalDateTime().isBeforeOrEqualTo(LocalDateTime.now())
                    .is(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .describedAs("Description of assertion: {0}", zonedDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalDateTime().isAfter(LocalDateTime.now().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asLocalTime(): ChronoZonedDateTime -> LocalTime")
        void asLocalTime() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneId.systemDefault());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isEqualTo(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.systemDefault()))
                    .asLocalTime().isEqualTo(LocalTime.MIN).isBeforeNoon().isMidnight());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .describedAs("Description of assertion: {0}", zonedDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asLocalTime().isEqualTo(LocalTime.now()))
                    .withMessageStartingWith("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asOffsetDateTime(): ChronoZonedDateTime -> OffsetDateTime")
        void asOffsetDateTime() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asOffsetDateTime().isBeforeOrEqualTo(OffsetDateTime.now())
                    .is(it -> it.isAfter(OffsetDateTime.MIN)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .describedAs("Description of assertion: {0}", zonedDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asOffsetDateTime().isAfter(OffsetDateTime.now().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asInstant(): ChronoZonedDateTime -> Instant")
        void asInstant() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isAfter(ZonedDateTime.from(LocalDate.MIN.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())))
                    .asInstant().isBeforeOrEqualTo(Instant.now()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .describedAs("Description of assertion: {0}", zonedDateTime)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asInstant().isAfter(Instant.now().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + zonedDateTime);
        }
    }

    // java.util ---------------------------------------------------------------------------------------

    @Nested
    class DateAssert {
        @Test
        @DisplayName("asInstant(): Date -> Instant")
        void asInstant() {
            // given
            Date date = new Date(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(date)
                    .isNotNull().isEqualTo(new java.sql.Date(date.getTime())).isBefore(new java.sql.Time(1))
                    .asInstant().isEqualTo(Instant.EPOCH)
                    .is(it -> it.toEpochMilli() == date.getTime()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(date)
                            .describedAs("Description of assertion: {0}", date)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asInstant().isAfter(date.toInstant().plusSeconds(1)))
                    .withMessageStartingWith("Description of assertion: " + date);
        }

        @Test
        @DisplayName("asYearMonth(): Date -> YearMonth")
        void asYearMonth() throws ParseException {
            // given
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = format.parse("2013-09-01T08:00:10.543");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(date)
                    .isNotNull().isEqualTo(new java.sql.Timestamp(date.getTime())).isAfter(new java.sql.Date(0))
                    .asYearMonth().isEqualTo(YearMonth.of(2013, 9))
                    .isNotLeapYear().is(it -> it.withYear(2004).isLeapYear()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(date)
                            .describedAs("Description of assertion: {0}", date)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asYearMonth().isLeapYear())
                    .withMessageStartingWith("Description of assertion: " + date);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class CollectionAssert {
        @Test
        @DisplayName("asArray(): Collection -> Array")
        void asArray() {
            // given
            String packageName = getClass().getPackage().getName();
            int count = StringUtils.countOf(packageName, ".") + 1;
            Collection<String> collection = Arrays.stream(packageName.split("\\.")).collect(toList());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(collection).hasSize(count)
                    .asArray().hasSize(count).isEqualTo(packageName.split("\\."))
                    .containsOnly(collection.stream().sorted(reverseOrder()).toArray(String[]::new))
                    .containsAny("java", "lang", "imsejin").isInstanceOf(Object[].class));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(collection)
                            .describedAs("Description of assertion: {0}", collection)
                            .thrownBy(RuntimeException::new).isNotEmpty()
                            .asArray().doesNotHaveSameSizeAs(new Object[0]).isInstanceOf(String[].class))
                    .withMessageStartingWith("Description of assertion: " + collection);
        }

        @Test
        @DisplayName("asSize(): Collection -> Integer")
        void asSize() {
            // given
            List<String> collection = Arrays.asList("A", "B", "C", "D", "E", "F");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(collection)
                    .isNotNull().doesNotContainNull().isNotEmpty()
                    .asSize().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(collection.size()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(collection)
                            .describedAs("Description of assertion: {0}", collection)
                            .thrownBy(RuntimeException::new).isNotEmpty()
                            .asSize().isZeroOrNegative())
                    .withMessageStartingWith("Description of assertion: " + collection);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class MapAssert {
        @Test
        @DisplayName("asKeySet(): Map -> Collection")
        void asKeySet() {
            // given
            String packageName = getClass().getPackage().getName();
            int count = StringUtils.countOf(packageName, ".") + 1;
            Map<Integer, String> map = CollectionUtils.toMap(Arrays.asList(packageName.split("\\.")));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(map).isNotEmpty().hasSize(count)
                    .asKeySet().isNotEmpty().hasSize(count).contains(0)
                    .containsAll(IntStream.range(0, count).boxed().collect(toList())));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(map)
                            .describedAs("Description of assertion: {0}", map)
                            .thrownBy(RuntimeException::new).isNotEmpty()
                            .asKeySet().isEmpty())
                    .withMessageStartingWith("Description of assertion: " + map);
        }

        @Test
        @DisplayName("asValues(): Map -> Collection")
        void asValues() {
            // given
            String packageName = getClass().getPackage().getName();
            int count = StringUtils.countOf(packageName, ".") + 1;
            Map<Integer, String> map = CollectionUtils.toMap(Arrays.asList(packageName.split("\\.")));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(map).isNotEmpty().hasSize(count)
                    .asValues().isNotEmpty().hasSize(count).contains("common")
                    .hasSameSizeAs(Arrays.asList(packageName.split("\\."))));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(map)
                            .describedAs("Description of assertion: {0}", map)
                            .thrownBy(RuntimeException::new).isNotEmpty()
                            .asValues().isEmpty())
                    .withMessageStartingWith("Description of assertion: " + map);
        }

        @Test
        @DisplayName("asSize(): Map -> Integer")
        void asSize() {
            // given
            Map<Integer, String> map = CollectionUtils.toMap(Arrays.asList("A", "B", "C"));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(map)
                    .isNotNull().isNotEmpty()
                    .asSize().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(map.size()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(map)
                            .describedAs("Description of assertion: {0}", map)
                            .thrownBy(RuntimeException::new).isNotEmpty()
                            .asSize().isBetween(-1, 0))
                    .withMessageStartingWith("Description of assertion: " + map);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class UuidAssert {
        @Test
        @DisplayName("asVersion(): UUID -> Integer")
        void asVersion() {
            // given
            UUID uuid = UUID.randomUUID();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(uuid)
                    .isNotNull().isNotNil().isEqualTo(UUID.fromString(uuid.toString()))
                    .asVersion().isBetween(0, 15).is(it -> it == 4));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(uuid)
                            .describedAs("Description of assertion: {0}", uuid)
                            .thrownBy(RuntimeException::new).isNotNull()
                            .asVersion().isNegative())
                    .withMessageStartingWith("Description of assertion: " + uuid);
        }
    }

    // -------------------------------------------------------------------------------------------------

}
