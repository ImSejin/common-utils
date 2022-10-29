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

import io.github.imsejin.common.assertion.io.FileAssert;
import io.github.imsejin.common.assertion.lang.ArrayAssert;
import io.github.imsejin.common.assertion.lang.BooleanAssert;
import io.github.imsejin.common.assertion.lang.ByteAssert;
import io.github.imsejin.common.assertion.lang.CharSequenceAssert;
import io.github.imsejin.common.assertion.lang.CharacterAssert;
import io.github.imsejin.common.assertion.lang.ClassAssert;
import io.github.imsejin.common.assertion.lang.DoubleAssert;
import io.github.imsejin.common.assertion.lang.FloatAssert;
import io.github.imsejin.common.assertion.lang.IntegerAssert;
import io.github.imsejin.common.assertion.lang.LongAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.PackageAssert;
import io.github.imsejin.common.assertion.lang.ShortAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.assertion.math.BigDecimalAssert;
import io.github.imsejin.common.assertion.math.BigIntegerAssert;
import io.github.imsejin.common.assertion.net.UrlAssert;
import io.github.imsejin.common.assertion.nio.file.PathAssert;
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
import io.github.imsejin.common.assertion.util.DateAssert;
import io.github.imsejin.common.assertion.util.ListAssert;
import io.github.imsejin.common.assertion.util.MapAssert;
import io.github.imsejin.common.assertion.util.OptionalAssert;
import io.github.imsejin.common.assertion.util.OptionalDoubleAssert;
import io.github.imsejin.common.assertion.util.OptionalIntAssert;
import io.github.imsejin.common.assertion.util.OptionalLongAssert;
import io.github.imsejin.common.assertion.util.UuidAssert;
import io.github.imsejin.common.assertion.util.concurrent.atomic.AtomicBooleanAssert;
import io.github.imsejin.common.assertion.util.concurrent.atomic.AtomicIntegerAssert;
import io.github.imsejin.common.assertion.util.concurrent.atomic.AtomicLongAssert;
import io.github.imsejin.common.assertion.util.concurrent.atomic.AtomicReferenceAssert;
import io.github.imsejin.common.util.ArrayUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

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
public abstract class Asserts {

    /**
     * You can customize {@link Asserts} with this constructor.
     *
     * <pre>{@code
     *     public class MyAsserts extends Asserts {
     *         public static FooAssert<?, Foo>(Foo foo) {
     *             return new FooAssert(foo);
     *         }
     *     }
     *
     *     ----------------------------------------
     *
     *     MyAsserts.that("foo")
     *             .isNotNull()
     *             .hasLengthOf(3)
     *             .isEqualTo("foo");
     *     MyAsserts.that(new Foo())
     *             .isNotNull()
     *             .isBar();
     * }</pre>
     *
     * <p> If you add the assertion method and its return type has {@code ACTUAL} type variable,
     * you must specify the concrete type, not the wildcard like this.
     *
     * <pre>{@code
     *     // This is seriously bad. Change to FooAssert<?, Foo>.
     *     public static FooAssert<?, ?> that(Foo foo) {
     *         // ...
     *     }
     * }</pre>
     *
     * <p> In this case, compiler can't infer appropriate type from captured parameter.
     * When you put argument into assertion method, that causes compile error.
     */
    protected Asserts() {
    }

    // java.lang ---------------------------------------------------------------------------------------

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

    public static <E> ArrayAssert<?, E> that(E[] array) {
        return new ArrayAssert<>(array);
    }

    public static <T> ClassAssert<?, T> that(Class<T> clazz) {
        return new ClassAssert<>(clazz);
    }

    public static PackageAssert<?> that(Package pack) {
        return new PackageAssert<>(pack);
    }

    public static BooleanAssert<?> that(Boolean bool) {
        return new BooleanAssert<>(bool);
    }

    public static CharacterAssert<?> that(Character character) {
        return new CharacterAssert<>(character);
    }

    public static ByteAssert<?> that(Byte number) {
        return new ByteAssert<>(number);
    }

    public static ShortAssert<?> that(Short number) {
        return new ShortAssert<>(number);
    }

    public static IntegerAssert<?> that(Integer number) {
        return new IntegerAssert<>(number);
    }

    public static LongAssert<?> that(Long number) {
        return new LongAssert<>(number);
    }

    public static FloatAssert<?> that(Float number) {
        return new FloatAssert<>(number);
    }

    public static DoubleAssert<?> that(Double number) {
        return new DoubleAssert<>(number);
    }

    public static CharSequenceAssert<?, CharSequence, CharSequence> that(CharSequence charSequence) {
        return new CharSequenceAssert<>(charSequence);
    }

    public static StringAssert<?> that(String string) {
        return new StringAssert<>(string);
    }

    // java.io -----------------------------------------------------------------------------------------

    public static FileAssert<?, File> that(File file) {
        return new FileAssert<>(file);
    }

    // java.nio.file -----------------------------------------------------------------------------------------

    public static PathAssert<?, Path> that(Path path) {
        return new PathAssert<>(path);
    }

    // java.math ---------------------------------------------------------------------------------------

    public static BigIntegerAssert<?> that(BigInteger bigInteger) {
        return new BigIntegerAssert<>(bigInteger);
    }

    public static BigDecimalAssert<?> that(BigDecimal bigDecimal) {
        return new BigDecimalAssert<>(bigDecimal);
    }

    // java.net ----------------------------------------------------------------------------------------

    public static UrlAssert<?> that(URL url) {
        return new UrlAssert<>(url);
    }

    // java.time ---------------------------------------------------------------------------------------

    public static YearAssert<?> that(Year year) {
        return new YearAssert<>(year);
    }

    public static MonthAssert<?> that(Month month) {
        return new MonthAssert<>(month);
    }

    public static YearMonthAssert<?> that(YearMonth yearMonth) {
        return new YearMonthAssert<>(yearMonth);
    }

    public static MonthDayAssert<?> that(MonthDay monthDay) {
        return new MonthDayAssert<>(monthDay);
    }

    public static LocalTimeAssert<?> that(LocalTime localTime) {
        return new LocalTimeAssert<>(localTime);
    }

    public static OffsetTimeAssert<?> that(OffsetTime offsetTime) {
        return new OffsetTimeAssert<>(offsetTime);
    }

    public static InstantAssert<?> that(Instant instant) {
        return new InstantAssert<>(instant);
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

    // java.time.chrono --------------------------------------------------------------------------------

    public static ChronoLocalDateAssert<?> that(ChronoLocalDate localDate) {
        return new ChronoLocalDateAssert<>(localDate);
    }

    public static <DATE extends ChronoLocalDate> ChronoLocalDateTimeAssert<?, DATE> that(ChronoLocalDateTime<DATE> localDateTime) {
        return new ChronoLocalDateTimeAssert<>(localDateTime);
    }

    public static <DATE extends ChronoLocalDate> ChronoZonedDateTimeAssert<?, DATE> that(ChronoZonedDateTime<DATE> zonedDateTime) {
        return new ChronoZonedDateTimeAssert<>(zonedDateTime);
    }

    // java.util ---------------------------------------------------------------------------------------

    public static DateAssert<?, Date> that(Date date) {
        return new DateAssert<>(date);
    }

    public static <E> CollectionAssert<?, Collection<E>, E> that(Collection<E> collection) {
        return new CollectionAssert<>(collection);
    }

    public static <E> ListAssert<?, List<E>, E> that(List<E> list) {
        return new ListAssert<>(list);
    }

    public static <K, V> MapAssert<?, Map<K, V>, K, V> that(Map<K, V> map) {
        return new MapAssert<>(map);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> OptionalAssert<?, T> that(Optional<T> optional) {
        return new OptionalAssert<>(optional);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static OptionalIntAssert<?> that(OptionalInt optionalInt) {
        return new OptionalIntAssert<>(optionalInt);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static OptionalLongAssert<?> that(OptionalLong optionalLong) {
        return new OptionalLongAssert<>(optionalLong);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static OptionalDoubleAssert<?> that(OptionalDouble optionalDouble) {
        return new OptionalDoubleAssert<>(optionalDouble);
    }

    public static UuidAssert<?> that(UUID uuid) {
        return new UuidAssert<>(uuid);
    }

    // java.util.concurrent.atomic ---------------------------------------------------------------------

    public static AtomicBooleanAssert<?> that(AtomicBoolean atomicBoolean) {
        return new AtomicBooleanAssert<>(atomicBoolean);
    }

    public static AtomicIntegerAssert<?> that(AtomicInteger atomicInteger) {
        return new AtomicIntegerAssert<>(atomicInteger);
    }

    public static AtomicLongAssert<?> that(AtomicLong atomicLong) {
        return new AtomicLongAssert<>(atomicLong);
    }

    public static <V> AtomicReferenceAssert<?, AtomicReference<V>, V> that(AtomicReference<V> atomicReference) {
        return new AtomicReferenceAssert<>(atomicReference);
    }

}
