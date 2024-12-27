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

import org.jetbrains.annotations.Nullable;

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
     *         public static FooAssert<?, Foo> that(Foo foo) {
     *             return new FooAssert(foo);
     *         }
     *     }
     *
     *     ----------------------------------------
     *
     *     // Uses assertion types supported basically.
     *     MyAsserts.that("foo")
     *             .isNotNull()
     *             .hasLengthOf(3)
     *             .isEqualTo("foo");
     *
     *     // Uses assertion custom type of Foo.
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

    public static <SELF extends ObjectAssert<SELF, T>, T> ObjectAssert<SELF, T> that(@Nullable T object) {
        return new ObjectAssert<>(object);
    }

    public static <SELF extends ArrayAssert<SELF, Boolean>> ArrayAssert<SELF, Boolean> that(
            boolean @Nullable [] array
    ) {
        return that((Boolean[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, Byte>> ArrayAssert<SELF, Byte> that(byte @Nullable [] array) {
        return that((Byte[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, Short>> ArrayAssert<SELF, Short> that(short @Nullable [] array) {
        return that((Short[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, Character>> ArrayAssert<SELF, Character> that(
            char @Nullable [] array
    ) {
        return that((Character[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, Integer>> ArrayAssert<SELF, Integer> that(int @Nullable [] array) {
        return that((Integer[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, Long>> ArrayAssert<SELF, Long> that(long @Nullable [] array) {
        return that((Long[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, Float>> ArrayAssert<SELF, Float> that(float @Nullable [] array) {
        return that((Float[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, Double>> ArrayAssert<SELF, Double> that(double @Nullable [] array) {
        return that((Double[]) ArrayUtils.wrap(array));
    }

    public static <SELF extends ArrayAssert<SELF, E>, E> ArrayAssert<SELF, E> that(@Nullable E @Nullable [] array) {
        return new ArrayAssert<>(array);
    }

    public static <SELF extends ClassAssert<SELF, T>, T> ClassAssert<SELF, T> that(@Nullable Class<T> clazz) {
        return new ClassAssert<>(clazz);
    }

    public static <SELF extends PackageAssert<SELF>> PackageAssert<SELF> that(@Nullable Package pack) {
        return new PackageAssert<>(pack);
    }

    public static <SELF extends BooleanAssert<SELF>> BooleanAssert<SELF> that(@Nullable Boolean bool) {
        return new BooleanAssert<>(bool);
    }

    public static <SELF extends CharacterAssert<SELF>> CharacterAssert<SELF> that(@Nullable Character character) {
        return new CharacterAssert<>(character);
    }

    public static <SELF extends ByteAssert<SELF>> ByteAssert<SELF> that(@Nullable Byte number) {
        return new ByteAssert<>(number);
    }

    public static <SELF extends ShortAssert<SELF>> ShortAssert<SELF> that(@Nullable Short number) {
        return new ShortAssert<>(number);
    }

    public static <SELF extends IntegerAssert<SELF>> IntegerAssert<SELF> that(@Nullable Integer number) {
        return new IntegerAssert<>(number);
    }

    public static <SELF extends LongAssert<SELF>> LongAssert<SELF> that(@Nullable Long number) {
        return new LongAssert<>(number);
    }

    public static <SELF extends FloatAssert<SELF>> FloatAssert<SELF> that(@Nullable Float number) {
        return new FloatAssert<>(number);
    }

    public static <SELF extends DoubleAssert<SELF>> DoubleAssert<SELF> that(@Nullable Double number) {
        return new DoubleAssert<>(number);
    }

    public static <SELF extends CharSequenceAssert<SELF, CharSequence, CharSequence>> CharSequenceAssert<SELF, CharSequence, CharSequence> that(
            @Nullable CharSequence charSequence
    ) {
        return new CharSequenceAssert<>(charSequence);
    }

    public static <SELF extends StringAssert<SELF>> StringAssert<SELF> that(@Nullable String string) {
        return new StringAssert<>(string);
    }

    // java.io -----------------------------------------------------------------------------------------

    public static <SELF extends FileAssert<SELF, File>> FileAssert<SELF, File> that(@Nullable File file) {
        return new FileAssert<>(file);
    }

    // java.nio.file -----------------------------------------------------------------------------------------

    public static <SELF extends PathAssert<SELF, Path>> PathAssert<SELF, Path> that(@Nullable Path path) {
        return new PathAssert<>(path);
    }

    // java.math ---------------------------------------------------------------------------------------

    public static <SELF extends BigIntegerAssert<SELF>> BigIntegerAssert<SELF> that(@Nullable BigInteger bigInteger) {
        return new BigIntegerAssert<>(bigInteger);
    }

    public static <SELF extends BigDecimalAssert<SELF>> BigDecimalAssert<SELF> that(@Nullable BigDecimal bigDecimal) {
        return new BigDecimalAssert<>(bigDecimal);
    }

    // java.net ----------------------------------------------------------------------------------------

    public static <SELF extends UrlAssert<SELF>> UrlAssert<SELF> that(@Nullable URL url) {
        return new UrlAssert<>(url);
    }

    // java.time ---------------------------------------------------------------------------------------

    public static <SELF extends YearAssert<SELF>> YearAssert<SELF> that(@Nullable Year year) {
        return new YearAssert<>(year);
    }

    public static <SELF extends MonthAssert<SELF>> MonthAssert<SELF> that(@Nullable Month month) {
        return new MonthAssert<>(month);
    }

    public static <SELF extends YearMonthAssert<SELF>> YearMonthAssert<SELF> that(@Nullable YearMonth yearMonth) {
        return new YearMonthAssert<>(yearMonth);
    }

    public static <SELF extends MonthDayAssert<SELF>> MonthDayAssert<SELF> that(@Nullable MonthDay monthDay) {
        return new MonthDayAssert<>(monthDay);
    }

    public static <SELF extends LocalTimeAssert<SELF>> LocalTimeAssert<SELF> that(@Nullable LocalTime localTime) {
        return new LocalTimeAssert<>(localTime);
    }

    public static <SELF extends OffsetTimeAssert<SELF>> OffsetTimeAssert<SELF> that(@Nullable OffsetTime offsetTime) {
        return new OffsetTimeAssert<>(offsetTime);
    }

    public static <SELF extends InstantAssert<SELF>> InstantAssert<SELF> that(@Nullable Instant instant) {
        return new InstantAssert<>(instant);
    }

    public static <SELF extends OffsetDateTimeAssert<SELF>> OffsetDateTimeAssert<SELF> that(
            @Nullable OffsetDateTime offsetDateTime) {
        return new OffsetDateTimeAssert<>(offsetDateTime);
    }

    public static <SELF extends DurationAssert<SELF>> DurationAssert<SELF> that(@Nullable Duration duration) {
        return new DurationAssert<>(duration);
    }

    public static <SELF extends PeriodAssert<SELF>> PeriodAssert<SELF> that(@Nullable Period period) {
        return new PeriodAssert<>(period);
    }

    // java.time.chrono --------------------------------------------------------------------------------

    public static <SELF extends ChronoLocalDateAssert<SELF>> ChronoLocalDateAssert<SELF> that(
            @Nullable ChronoLocalDate localDate
    ) {
        return new ChronoLocalDateAssert<>(localDate);
    }

    public static <SELF extends ChronoLocalDateTimeAssert<SELF, DATE>, DATE extends ChronoLocalDate> ChronoLocalDateTimeAssert<SELF, DATE> that(
            @Nullable ChronoLocalDateTime<DATE> localDateTime
    ) {
        return new ChronoLocalDateTimeAssert<>(localDateTime);
    }

    public static <SELF extends ChronoZonedDateTimeAssert<SELF, DATE>, DATE extends ChronoLocalDate> ChronoZonedDateTimeAssert<SELF, DATE> that(
            @Nullable ChronoZonedDateTime<DATE> zonedDateTime
    ) {
        return new ChronoZonedDateTimeAssert<>(zonedDateTime);
    }

    // java.util ---------------------------------------------------------------------------------------

    public static <SELF extends DateAssert<SELF, Date>> DateAssert<SELF, Date> that(@Nullable Date date) {
        return new DateAssert<>(date);
    }

    public static <SELF extends CollectionAssert<SELF, Collection<E>, E>, E> CollectionAssert<SELF, Collection<E>, E> that(
            @Nullable Collection<E> collection
    ) {
        return new CollectionAssert<>(collection);
    }

    public static <SELF extends ListAssert<SELF, List<E>, E>, E> ListAssert<SELF, List<E>, E> that(
            @Nullable List<E> list
    ) {
        return new ListAssert<>(list);
    }

    public static <SELF extends MapAssert<SELF, Map<K, V>, K, V>, K, V> MapAssert<SELF, Map<K, V>, K, V> that(
            @Nullable Map<K, V> map
    ) {
        return new MapAssert<>(map);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <SELF extends OptionalAssert<SELF, T>, T> OptionalAssert<SELF, T> that(
            @Nullable Optional<T> optional) {
        return new OptionalAssert<>(optional);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <SELF extends OptionalIntAssert<SELF>> OptionalIntAssert<SELF> that(
            @Nullable OptionalInt optionalInt
    ) {
        return new OptionalIntAssert<>(optionalInt);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <SELF extends OptionalLongAssert<SELF>> OptionalLongAssert<SELF> that(
            @Nullable OptionalLong optionalLong
    ) {
        return new OptionalLongAssert<>(optionalLong);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <SELF extends OptionalDoubleAssert<SELF>> OptionalDoubleAssert<SELF> that(
            @Nullable OptionalDouble optionalDouble
    ) {
        return new OptionalDoubleAssert<>(optionalDouble);
    }

    public static <SELF extends UuidAssert<SELF>> UuidAssert<SELF> that(@Nullable UUID uuid) {
        return new UuidAssert<>(uuid);
    }

    // java.util.concurrent.atomic ---------------------------------------------------------------------

    public static <SELF extends AtomicBooleanAssert<SELF>> AtomicBooleanAssert<SELF> that(
            @Nullable AtomicBoolean atomicBoolean
    ) {
        return new AtomicBooleanAssert<>(atomicBoolean);
    }

    public static <SELF extends AtomicIntegerAssert<SELF>> AtomicIntegerAssert<SELF> that(
            @Nullable AtomicInteger atomicInteger
    ) {
        return new AtomicIntegerAssert<>(atomicInteger);
    }

    public static <SELF extends AtomicLongAssert<SELF>> AtomicLongAssert<SELF> that(@Nullable AtomicLong atomicLong) {
        return new AtomicLongAssert<>(atomicLong);
    }

    public static <SELF extends AtomicReferenceAssert<SELF, AtomicReference<V>, V>, V> AtomicReferenceAssert<SELF, AtomicReference<V>, V> that(
            @Nullable AtomicReference<V> atomicReference
    ) {
        return new AtomicReferenceAssert<>(atomicReference);
    }

}
