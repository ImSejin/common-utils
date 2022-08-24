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

import io.github.imsejin.common.constant.DateType;
import io.github.imsejin.common.constant.OS;
import io.github.imsejin.common.util.ArrayUtils;
import io.github.imsejin.common.util.CollectionUtils;
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ConversionTest {

    // java.lang ---------------------------------------------------------------------------------------

    @Nested
    class ObjectAssert {
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
            String text = "ObjectAssert";

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

    // -------------------------------------------------------------------------------------------------

    @Nested
    class ArrayAssert {
        @Test
        @DisplayName("asLength(): Array -> int")
        void asLength() {
            // given
            String[] array = {"a", "b", "c", "d"};

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(array)
                    .isNotNull().doesNotContainNull().hasElement().doesNotContainAll(new String[]{"A", "B", "C", "D"})
                    .predicate(them -> Arrays.stream(them).allMatch(it -> Character.isLowerCase(it.charAt(0))))
                    .asLength().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(4));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(array)
                            .as("Description of assertion: {0}", ArrayUtils.toString(array))
                            .exception(RuntimeException::new).isNotNull()
                            .asLength().isEqualTo(array.length - 1))
                    .withMessage("Description of assertion: " + ArrayUtils.toString(array));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class CharSequenceAssert {
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
            Class<?> clazz = io.github.imsejin.common.assertion.lang.ObjectAssert.class;

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(clazz).isNotNull()
                    .asPackage().isNotNull().isSubPackageOf(Asserts.class.getPackage())
                    .returns("io.github.imsejin.common.assertion.lang", Package::getName));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(clazz)
                            .as("Description of assertion")
                            .exception(RuntimeException::new).isAbstractClass()
                            .asPackage().isSubPackageOf(OS.class.getPackage()))
                    .withMessage("Description of assertion");
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
                            .as("Description of assertion: {0}", pack)
                            .exception(RuntimeException::new).isNotNull()
                            .asName().isNumeric())
                    .withMessage("Description of assertion: " + pack);
        }
    }

    // java.io -----------------------------------------------------------------------------------------

    @Nested
    class AbstractFileAssert {
        @Test
        @DisplayName("asLength(): File -> long")
        void asLength(@TempDir Path path) throws IOException {
            // given
            String filename = LocalDateTime.now().format(DateType.DATE_TIME.getFormatter());
            File file = new File(path.toFile(), filename);
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
                            .as("Description of assertion: {0}", url)
                            .exception(RuntimeException::new).isNotNull()
                            .asHost().startsWith("github.com"))
                    .withMessage("Description of assertion: " + url);
        }

        @Test
        @DisplayName("asPort(): URL -> Integer")
        void asPort() throws MalformedURLException {
            // given
            URL url = new URL("http://www.github.com/");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(url)
                    .isNotNull().isEqualTo(new URL("http://www.github.com/"))
                    .asPort().isEqualTo(80));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(url)
                            .as("Description of assertion: {0}", url)
                            .exception(RuntimeException::new).isNotNull()
                            .asPort().isNegative())
                    .withMessage("Description of assertion: " + url);
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
                            .as("Description of assertion: {0}", url)
                            .exception(RuntimeException::new).isNotNull()
                            .asPath().isUpperCase())
                    .withMessage("Description of assertion: " + url);
        }
    }

    // java.time ---------------------------------------------------------------------------------------

    @Nested
    class InstantAssert {
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
                            .as("Description of assertion: {0}", instant)
                            .exception(RuntimeException::new).isNotNull()
                            .asEpochMilli().isEqualTo(Long.MAX_VALUE))
                    .withMessage("Description of assertion: " + instant);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    class LocalTimeAssert {
        @Test
        @DisplayName("asSecondOfDay(): LocalTime -> int")
        void asSecondOfDay() {
            // given
            LocalTime time = LocalTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(time)
                    .isNotNull().isBefore(time.plusSeconds(1))
                    .asSecondOfDay().isBetween(0, Integer.MAX_VALUE)
                    .predicate(it -> it == time.toSecondOfDay()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(time)
                            .as("Description of assertion: {0}", time)
                            .exception(RuntimeException::new).isNotNull()
                            .asSecondOfDay().isGreaterThan(LocalTime.now().toSecondOfDay()))
                    .withMessage("Description of assertion: " + time);
        }

        @Test
        @DisplayName("asNanoOfDay(): LocalTime -> long")
        void asNanoOfDay() {
            // given
            LocalTime time = LocalTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(time)
                    .isNotNull().isBefore(time.plusSeconds(1))
                    .asNanoOfDay().isBetween(0L, Long.MAX_VALUE)
                    .predicate(it -> it == time.toNanoOfDay()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(time)
                            .as("Description of assertion: {0}", time)
                            .exception(RuntimeException::new).isNotNull()
                            .asNanoOfDay().isGreaterThan(LocalTime.now().toNanoOfDay()))
                    .withMessage("Description of assertion: " + time);
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
            OffsetDateTime offsetDateTime = OffsetDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isBefore(OffsetDateTime.now().plusSeconds(1))
                    .asLocalDateTime().isBeforeOrEqualTo(LocalDateTime.now())
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalDateTime().isAfter(LocalDateTime.now().plusSeconds(1)))
                    .withMessage("Description of assertion: " + offsetDateTime);
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
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asZonedDateTime().isAfter(ZonedDateTime.now().plusSeconds(1)))
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
                    .asLocalTime().isEqualTo(LocalTime.MIN).isBeforeNoon().isMidnight());
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalTime().isEqualTo(LocalTime.MIN).isNoon())
                    .withMessage("Description of assertion: " + offsetDateTime);
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
            OffsetDateTime offsetDateTime = OffsetDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(offsetDateTime)
                    .isNotNull().isAfter(OffsetDateTime.from(LocalDate.MIN.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())))
                    .asInstant().isBeforeOrEqualTo(Instant.now()));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(offsetDateTime)
                            .as("Description of assertion: {0}", offsetDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asInstant().isAfter(Instant.now().plusSeconds(1)))
                    .withMessage("Description of assertion: " + offsetDateTime);
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
                            .as("Description of assertion: {0}", offsetTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalTime().isAfter(LocalTime.now().plusSeconds(1)))
                    .withMessage("Description of assertion: " + offsetTime);
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
                            .as("Description of assertion: {0}", year)
                            .exception(RuntimeException::new).isNotNull()
                            .asValue().isZeroOrNegative())
                    .withMessage("Description of assertion: " + year);
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
                            .as("Description of assertion: {0}", year)
                            .exception(RuntimeException::new).isNotNull()
                            .asLength().isEqualTo(365))
                    .withMessage("Description of assertion: " + year);
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
                            .as("Description of assertion: {0}", yearMonth)
                            .exception(RuntimeException::new).isNotNull()
                            .asYear().isLeapYear())
                    .withMessage("Description of assertion: " + yearMonth);
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
                            .as("Description of assertion: {0}", yearMonth)
                            .exception(RuntimeException::new).isNotNull()
                            .asMonth().predicate(it -> it.ordinal() == 9))
                    .withMessage("Description of assertion: " + yearMonth);
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
                            .as("Description of assertion: {0}", month)
                            .exception(RuntimeException::new).isNotNull()
                            .asValue().isEqualTo(month.ordinal()))
                    .withMessage("Description of assertion: " + month);
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
                            .as("Description of assertion: {0}", monthDay)
                            .exception(RuntimeException::new).isNotNull()
                            .asMonth().isBefore(monthDay.with(Month.JUNE).getMonth()))
                    .withMessage("Description of assertion: " + monthDay);
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
                            .as("Description of assertion: {0}", monthDay)
                            .exception(RuntimeException::new).isNotNull()
                            .asDayOfMonth().isLessThan(monthDay.getDayOfMonth()))
                    .withMessage("Description of assertion: " + monthDay);
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
                            .as("Description of assertion: {0}", duration)
                            .exception(RuntimeException::new).isNotNull()
                            .asTotalSeconds().isEqualTo(new BigDecimal(LocalTime.MAX.toSecondOfDay() + ".999999998")))
                    .withMessage("Description of assertion: " + duration);
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
                            .as("Description of assertion: {0}", period)
                            .exception(RuntimeException::new).isNotNull()
                            .asTotalDays().isEqualTo(period.getDays()))
                    .withMessage("Description of assertion: " + period);
        }
    }

    // java.time.chrono --------------------------------------------------------------------------------

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
                    .asLocalTime().isEqualTo(LocalTime.MIN).isBeforeNoon().isMidnight());
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
            ZonedDateTime zonedDateTime = ZonedDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asLocalDateTime().isBeforeOrEqualTo(LocalDateTime.now())
                    .predicate(it -> it.getChronology().isLeapYear(2020)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asLocalDateTime().isAfter(LocalDateTime.now().plusSeconds(1)))
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
                    .asLocalTime().isEqualTo(LocalTime.MIN).isBeforeNoon().isMidnight());
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
            ZonedDateTime zonedDateTime = ZonedDateTime.now();

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(zonedDateTime)
                    .isNotNull().isBefore(ZonedDateTime.now().plusSeconds(1))
                    .asOffsetDateTime().isBeforeOrEqualTo(OffsetDateTime.now())
                    .predicate(it -> it.isAfter(OffsetDateTime.MIN)));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(zonedDateTime)
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asOffsetDateTime().isAfter(OffsetDateTime.now().plusSeconds(1)))
                    .withMessage("Description of assertion: " + zonedDateTime);
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
                            .as("Description of assertion: {0}", zonedDateTime)
                            .exception(RuntimeException::new).isNotNull()
                            .asInstant().isAfter(Instant.now().plusSeconds(1)))
                    .withMessage("Description of assertion: " + zonedDateTime);
        }
    }

    // java.util ---------------------------------------------------------------------------------------

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
            assertThatNoException().isThrownBy(() -> Asserts.that(collection).hasSizeOf(count)
                    .asArray().hasLengthOf(count).containsOnly(Arrays.stream(packageName.split("\\."))
                            .sorted(Collections.reverseOrder()).toArray(String[]::new))
                    .containsAny("java", "lang", "imsejin").containsAll(new String[0]));
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Asserts.that(collection)
                            .as("Description of assertion: {0}", collection)
                            .exception(RuntimeException::new).hasElement()
                            .asArray().isNotSameLength(new Object[0]).containsNull())
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

    // -------------------------------------------------------------------------------------------------

}
