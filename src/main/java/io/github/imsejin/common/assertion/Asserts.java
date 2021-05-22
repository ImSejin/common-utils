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

import java.util.Collection;

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

    public static CharacterAsserts<?> that(char character) {
        return new CharacterAsserts<>(character);
    }

    public static LongAsserts<?> that(long number) {
        return new LongAsserts(number);
    }

    public static DoubleAsserts<?> that(double number) {
        return new DoubleAsserts(number);
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

}
