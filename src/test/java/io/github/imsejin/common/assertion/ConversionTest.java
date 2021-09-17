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

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;
import io.github.imsejin.common.assertion.reflect.ClassAssert;
import io.github.imsejin.common.util.CollectionUtils;
import io.github.imsejin.common.util.DateTimeUtils;
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ConversionTest {

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
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("asString(): Object -> String")
    void asString() {
        // given
        UUID uuid = UUID.randomUUID();

        // expect
        assertThatNoException().isThrownBy(() -> Asserts.that(uuid).isNotNull().isEqualTo(UUID.fromString(uuid.toString()))
                .asString().matches("[\\da-z]{8}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{12}")
                .isEqualTo(uuid.toString()));
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
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
    }

    @Test
    @DisplayName("asPackage(): Class -> Package")
    void asPackage() {
        assertThatNoException().isThrownBy(() -> Asserts.that(AbstractObjectAssert.class).isNotNull()
                .asPackage().isNotNull().isSubPackageOf(Asserts.class.getPackage())
                .returns("io.github.imsejin.common.assertion.object", Package::getName));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("asName(): Package -> String")
    void asName() {
        assertThatNoException().isThrownBy(() -> Asserts.that(ClassAssert.class.getPackage()).isNotNull()
                .asName().returns("io/github/imsejin/common/assertion/reflect", it -> it.replace('.', '/')));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("asLocalDate(): ChronoLocalDateTime -> ChronoLocalDate")
    void asLocalDate0() {
        assertThatNoException().isThrownBy(() -> Asserts.that(LocalDateTime.now())
                .isNotNull().isBefore(LocalDateTime.now().plusSeconds(1))
                .asLocalDate().isEqualTo(LocalDate.now())
                .predicate(it -> it.getChronology().isLeapYear(2020)));
    }

    @Test
    @DisplayName("asLocalTime(): ChronoLocalDateTime -> LocalTime")
    void asLocalTime0() {
        assertThatNoException().isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                .isNotNull().isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .asLocalTime().isAfterOrEqualToMidnight().isBeforeNoon().isMidnight());
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("asLength(): Array -> int")
    void asLength0() {
        assertThatNoException().isThrownBy(() -> Asserts.that(new String[]{"a", "b", "c", "d"})
                .isNotNull().doesNotContainNull().hasElement()
                .asLength().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(4));
    }

    @Test
    @DisplayName("asLength(): CharSequence -> int")
    void asLength1() {
        // given
        String name = getClass().getPackage().getName();

        // expect
        assertThatNoException().isThrownBy(() -> Asserts.that(name)
                .isNotNull().hasText()
                .asLength().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(name.length()));
    }

    @Test
    @DisplayName("asLength(): File -> long")
    void asLength2(@TempDir Path path) throws IOException {
        // given
        File file = new File(path.toFile(), DateTimeUtils.now());
        String content = getClass().getPackage().getName();
        Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));

        // expect
        assertThatNoException().isThrownBy(() -> Asserts.that(file)
                .isNotNull().exists().canRead()
                .asLength().isGreaterThan(1L).isLessThan(Long.MAX_VALUE).isEqualTo((long) content.length()));
    }

    @Test
    @DisplayName("asSize(): Collection -> int")
    void asSize0() {
        // given
        List<String> collection = Arrays.asList("A", "B", "C", "D", "E", "F");

        // expect
        assertThatNoException().isThrownBy(() -> Asserts.that(collection)
                .isNotNull().doesNotContainNull().hasElement()
                .asSize().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(collection.size()));
    }

    @Test
    @DisplayName("asSize(): Map -> int")
    void asSize1() {
        // given
        Map<Integer, String> map = CollectionUtils.toMap(Arrays.asList("A", "B", "C"));

        // expect
        assertThatNoException().isThrownBy(() -> Asserts.that(map)
                .isNotNull().hasEntry()
                .asSize().isGreaterThan(1).isLessThan(Integer.MAX_VALUE).isEqualTo(map.size()));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

}
