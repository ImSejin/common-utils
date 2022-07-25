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

import io.github.imsejin.common.assertion.io.AbstractFileAssert;
import io.github.imsejin.common.assertion.lang.ArrayAssert;
import io.github.imsejin.common.assertion.lang.BooleanAssert;
import io.github.imsejin.common.assertion.lang.CharSequenceAssert;
import io.github.imsejin.common.assertion.lang.CharacterAssert;
import io.github.imsejin.common.assertion.lang.ClassAssert;
import io.github.imsejin.common.assertion.lang.DoubleAssert;
import io.github.imsejin.common.assertion.lang.FloatAssert;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.PackageAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.assertion.math.BigDecimalAssert;
import io.github.imsejin.common.assertion.net.UrlAssert;
import io.github.imsejin.common.assertion.time.DurationAssert;
import io.github.imsejin.common.assertion.time.InstantAssert;
import io.github.imsejin.common.assertion.time.LocalTimeAssert;
import io.github.imsejin.common.assertion.time.MonthAssert;
import io.github.imsejin.common.assertion.time.MonthDayAssert;
import io.github.imsejin.common.assertion.time.OffsetDateTimeAssert;
import io.github.imsejin.common.assertion.time.OffsetTimeAssert;
import io.github.imsejin.common.assertion.time.PeriodAssert;
import io.github.imsejin.common.assertion.time.YearAssert;
import io.github.imsejin.common.assertion.time.YearMonthAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoZonedDateTimeAssert;
import io.github.imsejin.common.assertion.util.CollectionAssert;
import io.github.imsejin.common.assertion.util.ListAssert;
import io.github.imsejin.common.assertion.util.MapAssert;
import io.github.imsejin.common.util.ArrayUtils;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Asserts for fluent assertion not increasing branches on code coverage.
 *
 * <p> This is implemented similarly to AssertJ's API.
 *
 * <p> If you use the '{@code ACTUAL}' type variable directly within an assertion
 * class as a parameter, user code that use the assertion class can't be compiled
 * because its bound type does not match the type variable.
 *
 * <p> AssertJ solved this problem by <u>specifying the generic type of a class
 * that extends the assertion class</u>. There is a difference between this
 * and AssertJ. This solved the problem by <u>specifying the type variable in return
 * type of method and instantiating an anonymous class that extends the assertion
 * class <b>with raw type because the diamond operator is not supported on anonymous
 * classes until Java 8</b></u>.
 *
 * @see <a href="https://assertj.github.io/doc/">AssertJ API document</a>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class Asserts {

    /**
     * Enables to customize Asserts class with inheritance.
     */
    protected Asserts() {
    }

    //////////////////////////////////////// java.lang ////////////////////////////////////////

    public static <T> ObjectAssert<?, T> that(T object) {
        return new ObjectAssert<>(object);
    }

    public static ArrayAssert<?, Boolean> that(boolean[] array) {
        return that((Boolean[]) ArrayUtils.wrap(array));
    }

    public static ArrayAssert<?, Byte> that(byte[] array) {
        return that((Byte[]) ArrayUtils.wrap(array));
    }

    public static ArrayAssert<?, Short> that(short[] array) {
        return that((Short[]) ArrayUtils.wrap(array));
    }

    public static ArrayAssert<?, Character> that(char[] array) {
        return that((Character[]) ArrayUtils.wrap(array));
    }

    public static ArrayAssert<?, Integer> that(int[] array) {
        return that((Integer[]) ArrayUtils.wrap(array));
    }

    public static ArrayAssert<?, Long> that(long[] array) {
        return that((Long[]) ArrayUtils.wrap(array));
    }

    public static ArrayAssert<?, Float> that(float[] array) {
        return that((Float[]) ArrayUtils.wrap(array));
    }

    public static ArrayAssert<?, Double> that(double[] array) {
        return that((Double[]) ArrayUtils.wrap(array));
    }

    public static <T> ArrayAssert<?, T> that(T[] array) {
        return new ArrayAssert<>(array);
    }

    public static BooleanAssert<?> that(Boolean bool) {
        return new BooleanAssert<>(bool);
    }

    public static CharacterAssert<?> that(Character character) {
        return new CharacterAssert<>(character);
    }

    public static FloatAssert<?> that(Float number) {
        return new FloatAssert<>(number);
    }

    public static DoubleAssert<?> that(Double number) {
        return new DoubleAssert<>(number);
    }

    public static <NUMBER extends Number & Comparable<NUMBER>> NumberAssert<?, NUMBER> that(NUMBER number) {
        return new NumberAssert<>(number);
    }

    public static CharSequenceAssert<?, CharSequence> that(CharSequence charSequence) {
        return new CharSequenceAssert<>(charSequence);
    }

    public static StringAssert<?> that(String string) {
        return new StringAssert<>(string);
    }

    public static <T> ClassAssert<?, T> that(Class<T> clazz) {
        return new ClassAssert<>(clazz);
    }

    public static PackageAssert<?> that(Package pack) {
        return new PackageAssert<>(pack);
    }

    //////////////////////////////////////// java.util ////////////////////////////////////////

    public static <T> CollectionAssert<?, Collection<T>, T> that(Collection<T> collection) {
        return new CollectionAssert<>(collection);
    }

    public static <T> ListAssert<?, List<T>, T> that(List<T> list) {
        return new ListAssert<>(list);
    }

    public static <K, V> MapAssert<?, Map<K, V>, K, V> that(Map<K, V> map) {
        return new MapAssert<>(map);
    }

    //////////////////////////////////////// java.io ////////////////////////////////////////

    public static AbstractFileAssert<?, File> that(File file) {
        return new AbstractFileAssert(file) {
        };
    }

    //////////////////////////////////////// java.net ////////////////////////////////////////

    public static UrlAssert<?> that(URL url) {
        return new UrlAssert<>(url);
    }

    //////////////////////////////////////// java.math ////////////////////////////////////////

    public static BigDecimalAssert<?> that(BigDecimal bigDecimal) {
        return new BigDecimalAssert<>(bigDecimal);
    }

    //////////////////////////////////////// java.time ////////////////////////////////////////

    public static YearAssert<?> that(Year year) {
        return new YearAssert(year);
    }

    public static MonthAssert<?> that(Month month) {
        return new MonthAssert(month);
    }

    public static YearMonthAssert<?> that(YearMonth yearMonth) {
        return new YearMonthAssert(yearMonth);
    }

    public static MonthDayAssert<?> that(MonthDay monthDay) {
        return new MonthDayAssert(monthDay);
    }

    public static LocalTimeAssert<?> that(LocalTime localTime) {
        return new LocalTimeAssert<>(localTime);
    }

    public static OffsetTimeAssert<?> that(OffsetTime offsetTime) {
        return new OffsetTimeAssert<>(offsetTime);
    }

    public static InstantAssert<?> that(Instant instant) {
        return new InstantAssert(instant);
    }

    public static OffsetDateTimeAssert<?> that(OffsetDateTime offsetDateTime) {
        return new OffsetDateTimeAssert<>(offsetDateTime);
    }

    public static DurationAssert<?> that(Duration duration) {
        return new DurationAssert<>(duration);
    }

    public static PeriodAssert<?> that(Period period) {
        return new PeriodAssert<>(period);
    }

    //////////////////////////////////////// java.time.chrono ////////////////////////////////////////

    public static ChronoLocalDateAssert<?> that(ChronoLocalDate localDate) {
        return new ChronoLocalDateAssert<>(localDate);
    }

    public static <DATE extends ChronoLocalDate> ChronoLocalDateTimeAssert<?, DATE> that(ChronoLocalDateTime<DATE> localDateTime) {
        return new ChronoLocalDateTimeAssert(localDateTime);
    }

    public static <DATE extends ChronoLocalDate> ChronoZonedDateTimeAssert<?, DATE> that(ChronoZonedDateTime<DATE> zonedDateTime) {
        return new ChronoZonedDateTimeAssert(zonedDateTime);
    }

}
