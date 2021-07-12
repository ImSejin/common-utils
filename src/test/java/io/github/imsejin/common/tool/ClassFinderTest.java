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

package io.github.imsejin.common.tool;

import io.github.imsejin.common.assertion.Descriptor;
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
import io.github.imsejin.common.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("rawtypes")
class ClassFinderTest {

    @Test
    void getAllSubclasses0() {
        // given
        Class<Collection> superclass = Collection.class;

        // when
        Set<Class<?>> subclasses = ClassFinder.getAllSubclasses(superclass);

        // then
        assertThat(subclasses)
                .isNotEmpty()
                .doesNotContainNull()
                .contains(List.class, Set.class, ArrayList.class, Vector.class, Queue.class, Stack.class);
    }

    @Test
    void getAllSubclasses1() {
        // given
        Class<Descriptor> superclass = Descriptor.class;
        ClassFinder.SearchPolicy searchPolicy = ClassFinder.SearchPolicy.CLASS;

        // when
        Set<Class<?>> subclasses = ClassFinder.getAllSubclasses(superclass, searchPolicy);

        // then
        assertThat(subclasses)
                .isNotEmpty()
                .doesNotContainNull()
                .contains(ArrayAssert.class, AbstractCharSequenceAssert.class, StringAssert.class,
                        AbstractCollectionAssert.class, AbstractFileAssert.class, AbstractMapAssert.class,
                        AbstractObjectAssert.class, BooleanAssert.class, CharacterAssert.class, DoubleAssert.class,
                        FloatAssert.class, NumberAssert.class, ClassAssert.class, PackageAssert.class,
                        AbstractChronoLocalDateAssert.class, AbstractChronoLocalDateTimeAssert.class,
                        AbstractChronoZonedDateTimeAssert.class, LocalTimeAssert.class,
                        OffsetDateTimeAssert.class, OffsetTimeAssert.class
                );
    }

    @Test
    void getAllSubclasses2() {
        // given
        Class<AbstractQueue> superclass = AbstractQueue.class;
        ClassFinder.SearchPolicy searchPolicy = ClassFinder.SearchPolicy.CLASS;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // when
        Set<Class<?>> subclasses = ClassFinder.getAllSubclasses(superclass, searchPolicy, classLoader);

        // then
        assertThat(subclasses)
                .isNotEmpty()
                .doesNotContainNull()
                .contains(ArrayBlockingQueue.class, ConcurrentLinkedQueue.class, DelayQueue.class, PriorityQueue.class);
    }

    @Test
    void findClasses() throws IOException {
        // given
        List<String> classNames = new ArrayList<>();

        // when
        ClassFinder.findClasses(classNames::add);

        // then
        Path sourcePath = Paths.get("./src/main/java/io/github/imsejin/common").toRealPath();
        assertThat(classNames)
                .doesNotContainNull()
                .filteredOn(name -> name.startsWith("io.github.imsejin.common"))
                .isNotEmpty()
                .hasSizeGreaterThan(FileUtils.findAllFiles(sourcePath).size());
    }

}
