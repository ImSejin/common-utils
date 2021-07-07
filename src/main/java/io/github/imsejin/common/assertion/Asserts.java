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
import io.github.imsejin.common.assertion.array.ArrayAsserts;
import io.github.imsejin.common.assertion.chars.CharSequenceAsserts;
import io.github.imsejin.common.assertion.chars.StringAsserts;
import io.github.imsejin.common.assertion.collection.CollectionAsserts;
import io.github.imsejin.common.assertion.io.FileAsserts;
import io.github.imsejin.common.assertion.map.MapAsserts;
import io.github.imsejin.common.assertion.object.ObjectAsserts;
import io.github.imsejin.common.assertion.primitive.*;
import io.github.imsejin.common.assertion.reflect.ClassAsserts;
import io.github.imsejin.common.assertion.reflect.PackageAsserts;
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

    public static ArrayAsserts<?, Boolean> that(boolean[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAsserts<?, Byte> that(byte[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAsserts<?, Character> that(char[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAsserts<?, Double> that(double[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAsserts<?, Float> that(float[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAsserts<?, Integer> that(int[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAsserts<?, Long> that(long[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static ArrayAsserts<?, Short> that(short[] array) {
        return new ArrayAsserts<>(ArrayUtils.toWrapper(array));
    }

    public static <T> ArrayAsserts<?, T> that(T[] array) {
        return new ArrayAsserts<>(array);
    }

    ///////////////////////////////////// Characters //////////////////////////////////////

    public static CharSequenceAsserts<?> that(CharSequence charSequence) {
        return new CharSequenceAsserts<>(charSequence);
    }

    public static StringAsserts<?> that(String string) {
        return new StringAsserts<>(string);
    }

    ///////////////////////////////////// Collection //////////////////////////////////////

    public static <T> CollectionAsserts<?, T> that(Collection<T> collection) {
        return new CollectionAsserts<>(collection);
    }

    //////////////////////////////////// Input/Output /////////////////////////////////////

    public static FileAsserts<?> that(File file) {
        return new FileAsserts<>(file);
    }

    ///////////////////////////////////////// Map /////////////////////////////////////////

    public static <K, V> MapAsserts<?, K, V> that(Map<K, V> map) {
        return new MapAsserts<>(map);
    }

    /////////////////////////////////////// Object ////////////////////////////////////////

    public static ObjectAsserts<?> that(Object object) {
        return new ObjectAsserts<>(object);
    }

    ////////////////////////////////////// Primitive //////////////////////////////////////

    public static BooleanAsserts<?> that(Boolean bool) {
        return new BooleanAsserts<>(bool);
    }

    public static CharacterAsserts<?> that(Character character) {
        return new CharacterAsserts<>(character);
    }

    /////////////////////////////////////// Number ////////////////////////////////////////

    public static DoubleAsserts<?> that(Double number) {
        return new DoubleAsserts<>(number);
    }

    public static FloatAsserts<?> that(Float number) {
        return new FloatAsserts<>(number);
    }

    public static <NUMBER extends Number & Comparable<NUMBER>> NumberAsserts<?, NUMBER> that(NUMBER number) {
        return new NumberAsserts<>(number);
    }

    ///////////////////////////////////// Reflection //////////////////////////////////////

    public static <T> ClassAsserts<?, T> that(Class<T> type) {
        return new ClassAsserts<>(type);
    }

    public static PackageAsserts<?> that(Package pack) {
        return new PackageAsserts<>(pack);
    }

    //////////////////////////////////////// Time /////////////////////////////////////////

    public static ChronoLocalDateAsserts<?> that(ChronoLocalDate date) {
        return new ChronoLocalDateAsserts<>(date);
    }

    public static <DATE extends ChronoLocalDate> ChronoLocalDateTimeAsserts<?, DATE> that(ChronoLocalDateTime<DATE> dateTime) {
        return new ChronoLocalDateTimeAsserts<>(dateTime);
    }

    public static <DATE extends ChronoLocalDate> ChronoZonedDateTimeAsserts<?, DATE> that(ChronoZonedDateTime<DATE> dateTime) {
        return new ChronoZonedDateTimeAsserts<>(dateTime);
    }

    public static LocalTimeAsserts<?> that(LocalTime time) {
        return new LocalTimeAsserts<>(time);
    }

    public static OffsetDateTimeAsserts<?> that(OffsetDateTime dateTime) {
        return new OffsetDateTimeAsserts<>(dateTime);
    }

    public static OffsetTimeAsserts<?> that(OffsetTime time) {
        return new OffsetTimeAsserts<>(time);
    }

}
