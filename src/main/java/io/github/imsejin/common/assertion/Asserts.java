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

import io.github.imsejin.common.assertion.array.ArrayAssert;
import io.github.imsejin.common.assertion.chars.AbstractCharSequenceAssert;
import io.github.imsejin.common.assertion.chars.StringAssert;
import io.github.imsejin.common.assertion.collection.AbstractCollectionAssert;
import io.github.imsejin.common.assertion.io.AbstractFileAssert;
import io.github.imsejin.common.assertion.map.AbstractMapAssert;
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;
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

/**
 * Asserts
 * <p>
 * This is implemented similarly to AssertJ's API.
 * <p>
 * If you use the '{@code ACTUAL}' generic variable directly within an assertion class as a parameter,
 * user code that use the assertion class can't be compiled because its bound type does not match
 * the generic variable.
 * <p>
 * AssertJ solved this problem by <u>specifying the generic type of a class that inherited the assertion
 * class</u>. There is a difference between this and AssertJ. This solved the problem by <u>specifying
 * the generic type in method return type and instantiating an anonymous class that inherited
 * the assertion class <b>with raw type because the diamond operator is not supported on anonymous classes
 * until Java 8</b></u>.
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

    //////////////////////////////////////// Array ////////////////////////////////////////

    public static ArrayAssert<?> that(boolean[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static ArrayAssert<?> that(byte[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static ArrayAssert<?> that(char[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static ArrayAssert<?> that(double[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static ArrayAssert<?> that(float[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static ArrayAssert<?> that(int[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static ArrayAssert<?> that(long[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static ArrayAssert<?> that(short[] array) {
        return new ArrayAssert<>(ArrayUtils.box(array));
    }

    public static <T> ArrayAssert<?> that(T[] array) {
        return new ArrayAssert<>(array);
    }

    ///////////////////////////////////// Characters //////////////////////////////////////

    public static AbstractCharSequenceAssert<?, CharSequence> that(CharSequence charSequence) {
        return new AbstractCharSequenceAssert(charSequence) {
        };
    }

    public static StringAssert<?> that(String string) {
        return new StringAssert<>(string);
    }

    ///////////////////////////////////// Collection //////////////////////////////////////

    public static <T> AbstractCollectionAssert<?, Collection<T>, T> that(Collection<T> collection) {
        return new AbstractCollectionAssert(collection) {
        };
    }

    //////////////////////////////////// Input/Output /////////////////////////////////////

    public static AbstractFileAssert<?, File> that(File file) {
        return new AbstractFileAssert(file) {
        };
    }

    ///////////////////////////////////////// Map /////////////////////////////////////////

    public static <K, V> AbstractMapAssert<?, Map<K, V>, K, V> that(Map<K, V> map) {
        return new AbstractMapAssert(map) {
        };
    }

    /////////////////////////////////////// Object ////////////////////////////////////////

    public static AbstractObjectAssert<?, Object> that(Object object) {
        return new AbstractObjectAssert(object) {
        };
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

    public static AbstractChronoLocalDateAssert<?> that(ChronoLocalDate date) {
        return new AbstractChronoLocalDateAssert(date) {
        };
    }

    public static <DATE extends ChronoLocalDate> AbstractChronoLocalDateTimeAssert<?, ChronoLocalDateTime<DATE>, DATE> that(ChronoLocalDateTime<DATE> dateTime) {
        return new AbstractChronoLocalDateTimeAssert(dateTime) {
        };
    }

    public static <DATE extends ChronoLocalDate> AbstractChronoZonedDateTimeAssert<?, ChronoZonedDateTime<DATE>, DATE> that(ChronoZonedDateTime<DATE> dateTime) {
        return new AbstractChronoZonedDateTimeAssert(dateTime) {
        };
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
