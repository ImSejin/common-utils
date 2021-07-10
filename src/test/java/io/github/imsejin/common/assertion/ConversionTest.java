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
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
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

        // when & then
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

        // when & then
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

        // when & then
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

        // when & then
        assertThatNoException().isThrownBy(() -> Asserts.that(uuid).isNotNull().isEqualTo(UUID.fromString(uuid.toString()))
                .asString().matches("[\\da-z]{8}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{12}")
                .isEqualTo(uuid.toString()));
    }

    @Test
    @DisplayName("asClass(): Object -> Class")
    void asClass() {
        // given
        String text = "AbstractObjectAssert";

        // when & then
        assertThatNoException().isThrownBy(() -> Asserts.that(text).isNotNull().hasText()
                .asClass().isSameAs(String.class).isEqualTo(text.getClass())
                .asClass().isSameAs(Class.class).isEqualTo(text.getClass().getClass()));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

}
