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
import io.github.imsejin.common.assertion.time.LocalTimeAssert;
import io.github.imsejin.common.assertion.time.OffsetDateTimeAssert;
import io.github.imsejin.common.assertion.time.OffsetTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.AbstractChronoLocalDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.AbstractChronoZonedDateTimeAssert;
import io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateAssert;
import io.github.imsejin.common.assertion.util.CollectionAssert;
import io.github.imsejin.common.assertion.util.MapAssert;
import io.github.imsejin.common.util.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractList;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ClassFinder")
@SuppressWarnings("rawtypes")
class ClassFinderTest {

    @Nested
    @DisplayName("method 'getAllSubtypes'")
    class GetAllSubtypes {
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        @DisplayName("returns all subtypes of java.util.Collection, when java version is 8")
        void test0() {
            // given
            Class<Collection> superclass = Collection.class;

            // when
            Set<Class<?>> subtypes = ClassFinder.getAllSubtypes(superclass);

            // then
            List<Class<? extends Collection>> subInterfaces = Arrays.asList(List.class, Set.class, Queue.class);
            List<Class<? extends AbstractList>> implementations = Arrays.asList(ArrayList.class, Vector.class, Stack.class);
            assertThat(subInterfaces)
                    .allMatch(superclass::isAssignableFrom)
                    .map(it -> Arrays.asList(it.getInterfaces()))
                    .allMatch(it -> it.contains(superclass));
            assertThat(implementations)
                    .allMatch(superclass::isAssignableFrom);
            assertThat(subtypes)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .containsAll(subInterfaces)
                    .containsAll(implementations);
        }

        @Test
        @DisplayName("returns subclasses of Descriptor, when superclass is in source directory")
        void test1() {
            // given
            Class<Descriptor> superclass = Descriptor.class;
            ClassFinder.SearchPolicy searchPolicy = ClassFinder.SearchPolicy.CLASS;

            // when
            Set<Class<?>> subclasses = ClassFinder.getAllSubtypes(superclass, searchPolicy);

            // then
            assertThat(subclasses)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .contains(ArrayAssert.class, CharSequenceAssert.class, StringAssert.class,
                            CollectionAssert.class, AbstractFileAssert.class, MapAssert.class,
                            ObjectAssert.class, BooleanAssert.class, CharacterAssert.class, DoubleAssert.class,
                            FloatAssert.class, NumberAssert.class, ClassAssert.class, PackageAssert.class,
                            ChronoLocalDateAssert.class, AbstractChronoLocalDateTimeAssert.class,
                            AbstractChronoZonedDateTimeAssert.class, LocalTimeAssert.class,
                            OffsetDateTimeAssert.class, OffsetTimeAssert.class);
        }

        @Test
        @EnabledOnJre(JRE.JAVA_8)
        @DisplayName("returns subclasses of java.util.AbstractQueue, when java version is 8")
        void test2() {
            // given
            Class<AbstractQueue> superclass = AbstractQueue.class;
            ClassFinder.SearchPolicy searchPolicy = ClassFinder.SearchPolicy.CLASS;
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();

            // when
            Set<Class<?>> subclasses = ClassFinder.getAllSubtypes(superclass, searchPolicy, classLoader);

            // then
            List<Class<? extends AbstractQueue>> classes = Arrays.asList(
                    ArrayBlockingQueue.class, ConcurrentLinkedQueue.class, DelayQueue.class, PriorityQueue.class);
            assertThat(subclasses)
                    .allMatch(superclass::isAssignableFrom);
            assertThat(subclasses)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .containsAll(classes);
        }
    }

    @Test
    @DisplayName("method 'findClasses'")
    void findClasses() throws IOException {
        // given
        List<String> classNames = new ArrayList<>();

        // when
        ClassFinder.findClasses(classNames::add);

        // then
        Path sourcePath = Paths.get(".", "src", "main", "java", "io", "github", "imsejin", "common").toRealPath();
        assertThat(classNames)
                .doesNotContainNull()
                .filteredOn(name -> name.startsWith("io.github.imsejin.common"))
                .isNotEmpty()
                .hasSizeGreaterThan(FileUtils.findAllFiles(sourcePath).size());
    }

}
