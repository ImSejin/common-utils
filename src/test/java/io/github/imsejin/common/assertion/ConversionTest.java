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
import io.github.imsejin.common.util.ArrayUtils;
import io.github.imsejin.common.util.CollectionUtils;
import io.github.imsejin.common.util.DateTimeUtils;
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ConversionTest {

    @Nested
    class AbstractCollectionAssert {
        @Test
        @DisplayName("asArray(): Collection -> Array")
        void asArray() {
            // given
            String packageName = getClass().getPackage().getName();
            int count = StringUtils.countOf(packageName, ".") + 1;
            Collection<String> collection = Arrays.stream(packageName.split("\\.")).collect(toList());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(collection).hasSizeOf(count)
                    .asArray().hasElement().hasLengthOf(count).contains("github")
                    .containsAny("java", "lang", "imsejin").containsAll(new String[0]));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(collection)
                            .as("Description of assertion: {0}", collection)
                            .exception(RuntimeException::new).hasElement()
                            .asArray().isEmpty())
                    .withMessage("Description of assertion: " + collection);
        }

        @Test
        @DisplayName("asSize(): Collection -> int")
        void asSize() {
            // given
            List<String> collection = Arrays.asList("A", "B", "C", "D", "E", "F");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(collection)
                    .isNotNull().doesNotContainNull().hasElement()
                    .asSize().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(collection.size()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(collection)
                            .as("Description of assertion: {0}", collection)
                            .exception(RuntimeException::new).hasElement()
                            .asSize().isZeroOrNegative())
                    .withMessage("Description of assertion: " + collection);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class AbstractMapAssert {
        @Test
        @DisplayName("asKeySet(): Map -> Collection")
        void asKeySet() {
            // given
            String packageName = getClass().getPackage().getName();
            int count = StringUtils.countOf(packageName, ".") + 1;
            Map<Integer, String> map = CollectionUtils.toMap(Arrays.asList(packageName.split("\\.")));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(map).hasEntry().hasSizeOf(count)
                    .asKeySet().hasElement().hasSizeOf(count).contains(0)
                    .containsAll(IntStream.range(0, count).boxed().collect(toList())));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(map)
                            .as("Description of assertion: {0}", map)
                            .exception(RuntimeException::new).hasEntry()
                            .asKeySet().isEmpty())
                    .withMessage("Description of assertion: " + map);
        }

        @Test
        @DisplayName("asValues(): Map -> Collection")
        void asValues() {
            // given
            String packageName = getClass().getPackage().getName();
            int count = StringUtils.countOf(packageName, ".") + 1;
            Map<Integer, String> map = CollectionUtils.toMap(Arrays.asList(packageName.split("\\.")));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(map).hasEntry().hasSizeOf(count)
                    .asValues().hasElement().hasSizeOf(count).contains("common")
                    .isSameSize(Arrays.asList(packageName.split("\\."))));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(map)
                            .as("Description of assertion: {0}", map)
                            .exception(RuntimeException::new).hasEntry()
                            .asValues().isEmpty())
                    .withMessage("Description of assertion: " + map);
        }

        @Test
        @DisplayName("asSize(): Map -> int")
        void asSize() {
            // given
            Map<Integer, String> map = CollectionUtils.toMap(Arrays.asList("A", "B", "C"));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(map)
                    .isNotNull().hasEntry()
                    .asSize().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(map.size()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(map)
                            .as("Description of assertion: {0}", map)
                            .exception(RuntimeException::new).hasEntry()
                            .asSize().isBetween(-1, 0))
                    .withMessage("Description of assertion: " + map);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class AbstractObjectAssert {
        @Test
        @DisplayName("asString(): Object -> String")
        void asString() {
            // given
            UUID uuid = UUID.randomUUID();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(uuid).isNotNull().isEqualTo(UUID.fromString(uuid.toString()))
                    .asString().matches("[\\da-z]{8}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{12}")
                    .isEqualTo(uuid.toString()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(uuid)
                            .as("Description of assertion: {0}", uuid)
                            .exception(RuntimeException::new).isNotNull()
                            .asString().isNull())
                    .withMessage("Description of assertion: " + uuid);
        }

        @Test
        @DisplayName("asClass(): Object -> Class")
        void asClass() {
            // given
            String text = "AbstractObjectAssert";

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(text).isNotNull().hasText()
                    .asClass().isSameAs(String.class).isEqualTo(text.getClass())
                    .asClass().isSameAs(Class.class).isEqualTo(text.getClass().getClass()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(text)
                            .as("Description of assertion: {0}", text)
                            .exception(RuntimeException::new).isNotNull()
                            .asClass().isAnonymousClass())
                    .withMessage("Description of assertion: " + text);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
                    .isSuperclassOf(Throwable.class).isSubclassOf(null));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(NumberFormatException.class)
                            .as("Description of assertion")
                            .exception(RuntimeException::new).isAssignableFrom(IllegalArgumentException.class)
                            .asSuperclass().isAnonymousClass())
                    .withMessage("Description of assertion");
        }

        @Test
        @DisplayName("asPackage(): Class -> Package")
        void asPackage() {
            // given
            Class<?> clazz = io.github.imsejin.common.assertion.object.AbstractObjectAssert.class;

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(clazz).isNotNull()
                    .asPackage().isNotNull().isSubPackageOf(Asserts.class.getPackage())
                    .returns("io.github.imsejin.common.assertion.object", Package::getName));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(clazz)
                            .as("Description of assertion")
                            .exception(RuntimeException::new).isAbstractClass()
                            .asPackage().isSubPackageOf(OS.class.getPackage()))
                    .withMessage("Description of assertion");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class PackageAssert {
        @Test
        @DisplayName("asName(): Package -> String")
        void asName() {
            // given
            Package pack = io.github.imsejin.common.assertion.reflect.ClassAssert.class.getPackage();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(pack).isNotNull()
                    .asName().returns("io/github/imsejin/common/assertion/reflect", it -> it.replace('.', '/')));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(pack)
                            .as("Description of assertion: {0}", pack)
                            .exception(RuntimeException::new).isNotNull()
                            .asName().isNumeric())
                    .withMessage("Description of assertion: " + pack);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class AbstractChronoLocalDateTimeAssert {
        @Test
        @DisplayName("asLocalDate(): ChronoLocalDateTime -> ChronoLocalDate")
        void asLocalDate() {
            // given
            LocalDateTime dateTime = LocalDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(dateTime)
                    .isNotNull().isBefore(dateTime.plusSeconds(1))
                    .asLocalDate().isEqualTo(LocalDate.now())
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(dateTime)
                            .as("Description of assertion: {0}", dateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalDate().isAfter(LocalDate.now()))
                    .withMessage("Description of assertion: " + dateTime);
        }

        @Test
        @DisplayName("asLocalTime(): ChronoLocalDateTime -> LocalTime")
        void asLocalTime() {
            // given
            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(dateTime)
                    .isNotNull().isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                    .asLocalTime().isAfterOrEqualToMidnight().isBeforeNoon().isMidnight());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(dateTime)
                            .as("Description of assertion: {0}", dateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalTime().isAfter(LocalTime.MAX))
                    .withMessage("Description of assertion: " + dateTime);
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
                            .as("Description of assertion: {0}", dateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asInstant().isEqualTo(Instant.now()))
                    .withMessage("Description of assertion: " + dateTime);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class AbstractChronoZonedDateTimeAssert {
        @Test
        @DisplayName("asLocalDate(): ChronoZonedDateTime -> ChronoLocalDate")
        void asLocalDate() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asLocalDate().isEqualTo(LocalDate.now())
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalDate().isBefore(LocalDate.now()))
                    .withMessage("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asLocalDateTime(): ChronoZonedDateTime -> ChronoLocalDateTime")
        void asLocalDateTime() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asLocalDateTime().isEqualTo(LocalDateTime.now().withNano(0))
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalDateTime().isEqualTo(LocalDateTime.now()))
                    .withMessage("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asLocalTime(): ChronoZonedDateTime -> LocalTime")
        void asLocalTime() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneId.systemDefault());

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isEqualTo(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.systemDefault()))
                    .asLocalTime().isAfterOrEqualToMidnight().isBeforeNoon().isMidnight());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalTime().isEqualTo(LocalTime.now()))
                    .withMessage("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asOffsetDateTime(): ChronoZonedDateTime -> OffsetDateTime")
        void asOffsetDateTime() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asOffsetDateTime().isEqualTo(OffsetDateTime.now().withNano(0))
                    .predicate(it -> it.isAfter(OffsetDateTime.MIN)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asOffsetDateTime().isEqualTo(OffsetDateTime.now()))
                    .withMessage("Description of assertion: " + zonedDateTime);
        }

        @Test
        @DisplayName("asInstant(): ChronoZonedDateTime -> Instant")
        void asInstant() {
            // given
            ZonedDateTime zonedDateTime = ZonedDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isAfter(ZonedDateTime.from(LocalDate.MIN.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())))
                    .asInstant().isBeforeOrEqualTo(Instant.now()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asInstant().isEqualTo(Instant.now()))
                    .withMessage("Description of assertion: " + zonedDateTime);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class OffsetDateTimeAssert {
        @Test
        @DisplayName("asLocalDate(): OffsetDateTime -> ChronoLocalDate")
        void asLocalDate() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asLocalDate().isEqualTo(LocalDate.now())
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalDate().isAfter(LocalDate.now()))
                    .withMessage("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asLocalDateTime(): OffsetDateTime -> ChronoLocalDateTime")
        void asLocalDateTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asLocalDateTime().isEqualTo(LocalDateTime.now().withNano(0))
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalDateTime().isEqualTo(LocalDateTime.now()))
                    .withMessage("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asZonedDateTime(): OffsetDateTime -> ChronoZonedDateTime")
        void asZonedDateTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asZonedDateTime().isEqualTo(offsetDateTime.toZonedDateTime())
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asZonedDateTime().isEqualTo(ZonedDateTime.now()))
                    .withMessage("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asLocalTime(): OffsetDateTime -> LocalTime")
        void asLocalTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneOffset.UTC);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isEqualTo(OffsetDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneOffset.UTC))
                    .asLocalTime().isAfterOrEqualToMidnight().isBeforeNoon().isMidnight());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalTime().isAfterMidnight())
                    .withMessage("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asOffsetTime(): OffsetDateTime -> OffsetTime")
        void asOffsetTime() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asOffsetTime().isEqualTo(OffsetTime.now().withNano(0))
                    .predicate(it -> it.isAfter(OffsetTime.MIN)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asOffsetTime().isSameOffset(null))
                    .withMessage("Description of assertion: " + offsetDateTime);
        }

        @Test
        @DisplayName("asInstant(): OffsetDateTime -> Instant")
        void asInstant() {
            // given
            OffsetDateTime offsetDateTime = OffsetDateTime.now().withNano(0);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isAfter(OffsetDateTime.from(LocalDate.MIN.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())))
                    .asInstant().isBeforeOrEqualTo(Instant.now()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asInstant().isEqualTo(Instant.now()))
                    .withMessage("Description of assertion: " + offsetDateTime);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class ArrayAssert {
        @Test
        @DisplayName("asLength(): Array -> int")
        void asLength() {
            // given
            String[] array = {"a", "b", "c", "d"};

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(array)
                    .isNotNull().doesNotContainNull().hasElement()
                    .asLength().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(4));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(array)
                            .as("Description of assertion: {0}", ArrayUtils.toString(array))
                            .exception(RuntimeException::new).isNotNull()
                            .asLength().isEqualTo(array.length - 1))
                    .withMessage("Description of assertion: " + ArrayUtils.toString(array));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class AbstractCharSequenceAssert {
        @Test
        @DisplayName("asLength(): CharSequence -> int")
        void asLength() {
            // given
            CharSequence charSequence = getClass().getPackage().getName();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(charSequence)
                    .isNotNull().isNotEmpty().isNotSameLength(new StringBuilder())
                    .asLength().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(charSequence.length()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(charSequence)
                            .as("Description of assertion: {0}", charSequence)
                            .exception(RuntimeException::new).isNotNull()
                            .asLength().isCloseTo(charSequence.length() + 1, 0))
                    .withMessage("Description of assertion: " + charSequence);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    class AbstractFileAssert {
        @Test
        @DisplayName("asLength(): File -> long")
        void asLength(@TempDir Path path) throws IOException {
            // given
            File file = new File(path.toFile(), DateTimeUtils.now());
            String content = getClass().getPackage().getName();
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotNull().exists().canRead()
                    .asLength().isGreaterThan(1L).isLessThan(Long.MAX_VALUE).isEqualTo((long) content.length()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(file)
                            .as("Description of assertion: {0}", file)
                            .exception(RuntimeException::new).exists()
                            .asLength().isZeroOrNegative())
                    .withMessage("Description of assertion: " + file);
        }

        @Test
        @DisplayName("asName(): File -> String")
        void asName(@TempDir Path path) throws IOException {
            // given
            String filename = "content.txt";
            File file = new File(path.toFile(), filename);
            String content = getClass().getPackage().getName();
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(file)
                    .isNotNull().exists().canRead().canWrite().isNotEmpty()
                    .asName().hasText().contains("content").endsWith("txt"));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(file)
                            .as("Description of assertion: {0}", file)
                            .exception(RuntimeException::new).isNotNull()
                            .asName().isUpperCase())
                    .withMessage("Description of assertion: " + file);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

}
