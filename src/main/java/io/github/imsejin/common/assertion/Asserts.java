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

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;
import io.github.imsejin.common.assertion.array.ArrayAssert;
import io.github.imsejin.common.assertion.chars.CharSequenceAssert;
import io.github.imsejin.common.assertion.chars.StringAssert;
import io.github.imsejin.common.assertion.collection.CollectionAssert;
import io.github.imsejin.common.assertion.io.FileAssert;
import io.github.imsejin.common.assertion.map.MapAssert;
import io.github.imsejin.common.assertion.object.ObjectAssert;
import io.github.imsejin.common.assertion.primitive.*;
import io.github.imsejin.common.assertion.reflect.ClassAssert;
import io.github.imsejin.common.assertion.reflect.PackageAssert;
import io.github.imsejin.common.assertion.time.*;
import io.github.imsejin.common.util.ArrayUtils;

import java.io.File;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Collection;
import java.util.Map;

public abstract class Asserts {

    @ExcludeFromGeneratedJacocoReport
    private Asserts() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    //////////////////////////////////////// Array ////////////////////////////////////////

    public static ArrayAssert<?, Boolean> that(boolean[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAssert<?, Byte> that(byte[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAssert<?, Character> that(char[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAssert<?, Double> that(double[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAssert<?, Float> that(float[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAssert<?, Integer> that(int[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAssert<?, Long> that(long[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAssert<?, Short> that(short[] array) {
        return new ArrayAssert<>(ArrayUtils.toWrapper(array));
    }

    public static <T> ArrayAssert<?, T> that(T[] array) {
        return new ArrayAssert<>(array);
    }

    ///////////////////////////////////// Characters //////////////////////////////////////

    public static CharSequenceAssert<?> that(CharSequence charSequence) {
        return new CharSequenceAssert<>(charSequence);
    }

    public static StringAssert<?> that(String string) {
        return new StringAssert<>(string);
    }

    ///////////////////////////////////// Collection //////////////////////////////////////

    public static <T> CollectionAssert<?, T> that(Collection<T> collection) {
        return new CollectionAssert<>(collection);
    }

    //////////////////////////////////// Input/Output /////////////////////////////////////

    public static FileAssert<?> that(File file) {
        return new FileAssert<>(file);
    }

    ///////////////////////////////////////// Map /////////////////////////////////////////

    public static <K, V> MapAssert<?, K, V> that(Map<K, V> map) {
        return new MapAssert<>(map);
    }

    /////////////////////////////////////// Object ////////////////////////////////////////

    public static ObjectAssert<?> that(Object object) {
        return new ObjectAssert<>(object);
    }

    ////////////////////////////////////// Primitive //////////////////////////////////////

    public static BooleanAssert<?> that(Boolean bool) {
        return new BooleanAssert<>(bool);
    }

    public static CharacterAssert<?> that(Character character) {
        return new CharacterAssert<>(character);
    }

    /////////////////////////////////////// Number ////////////////////////////////////////

    public static DoubleAssert<?> that(Double number) {
        return new DoubleAssert<>(number);
    }

    public static FloatAssert<?> that(Float number) {
        return new FloatAssert<>(number);
    }

    public static <NUMBER extends Number & Comparable<NUMBER>> NumberAssert<?, NUMBER> that(NUMBER number) {
        return new NumberAssert<>(number);
    }

    ///////////////////////////////////// Reflection //////////////////////////////////////

    public static <T> ClassAssert<?, T> that(Class<T> type) {
        return new ClassAssert<>(type);
    }

    public static PackageAssert<?> that(Package pack) {
        return new PackageAssert<>(pack);
    }

    //////////////////////////////////////// Time /////////////////////////////////////////

    public static ChronoLocalDateAssert<?> that(ChronoLocalDate date) {
        return new ChronoLocalDateAssert<>(date);
    }

    public static <DATE extends ChronoLocalDate> ChronoLocalDateTimeAssert<?, DATE> that(ChronoLocalDateTime<DATE> dateTime) {
        return new ChronoLocalDateTimeAssert<>(dateTime);
    }

    public static <DATE extends ChronoLocalDate> ChronoZonedDateTimeAssert<?, DATE> that(ChronoZonedDateTime<DATE> dateTime) {
        return new ChronoZonedDateTimeAssert<>(dateTime);
    }

    public static LocalTimeAssert<?> that(LocalTime time) {
        return new LocalTimeAssert<>(time);
    }

    public static OffsetDateTimeAssert<?> that(OffsetDateTime dateTime) {
        return new OffsetDateTimeAssert<>(dateTime);
    }

    public static OffsetTimeAssert<?> that(OffsetTime time) {
        return new OffsetTimeAssert<>(time);
    }

}
