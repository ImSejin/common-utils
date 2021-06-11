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

import io.github.imsejin.common.assertion.array.ArrayAsserts;
import io.github.imsejin.common.assertion.chars.CharSequenceAsserts;
import io.github.imsejin.common.assertion.chars.StringAsserts;
import io.github.imsejin.common.assertion.collection.CollectionAsserts;
import io.github.imsejin.common.assertion.io.FileAsserts;
import io.github.imsejin.common.assertion.map.MapAsserts;
import io.github.imsejin.common.assertion.object.ObjectAsserts;
import io.github.imsejin.common.assertion.primitive.BooleanAsserts;
import io.github.imsejin.common.assertion.primitive.CharacterAsserts;
import io.github.imsejin.common.assertion.primitive.DoubleAsserts;
import io.github.imsejin.common.assertion.primitive.LongAsserts;
import io.github.imsejin.common.assertion.reflect.ClassAsserts;
import io.github.imsejin.common.assertion.reflect.PackageAsserts;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public abstract class Asserts {

    public static ObjectAsserts<?> that(Object object) {
        return new ObjectAsserts<>(object);
    }

    public static BooleanAsserts<?> that(boolean bool) {
        return new BooleanAsserts<>(bool);
    }

    public static <T> ClassAsserts<?, T> that(Class<T> type) {
        return new ClassAsserts<>(type);
    }

    public static PackageAsserts<?> that(Package pack) {
        return new PackageAsserts<>(pack);
    }

    public static CharacterAsserts<?> that(char character) {
        return new CharacterAsserts<>(character);
    }

    public static LongAsserts<?> that(long number) {
        return new LongAsserts<>(number);
    }

    public static DoubleAsserts<?> that(double number) {
        return new DoubleAsserts<>(number);
    }

    public static CharSequenceAsserts<?> that(CharSequence charSequence) {
        return new CharSequenceAsserts<>(charSequence);
    }

    public static StringAsserts<?> that(String string) {
        return new StringAsserts<>(string);
    }

    public static <T> ArrayAsserts<?, T> that(T[] array) {
        return new ArrayAsserts<>(array);
    }

    public static <T> CollectionAsserts<?, T> that(Collection<T> collection) {
        return new CollectionAsserts<>(collection);
    }

    public static <K, V> MapAsserts<?, K, V> that(Map<K, V> map) {
        return new MapAsserts<>(map);
    }

    public static FileAsserts<?> that(File file) {
        return new FileAsserts<>(file);
    }

}
