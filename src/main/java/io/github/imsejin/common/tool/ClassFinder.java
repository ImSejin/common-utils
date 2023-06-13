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

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;
import io.github.imsejin.common.assertion.Asserts;

/**
 * Class finder
 *
 * @see <a href="https://stackoverflow.com/questions/3222638/get-all-of-the-classes-in-the-classpath#answer-19554704">
 * Get all of the Classes in the Classpath
 * </a>
 */
public final class ClassFinder {

    @ExcludeFromGeneratedJacocoReport
    private ClassFinder() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Returns all subtypes that extends the given class.
     * <p>
     * <b>Since Java 9, this can find only {@code jdk.internal.*} or classes in own packages.</b>
     *
     * @param superclass superclass
     * @param <T>        type of superclass
     * @return all subtypes
     */
    public static <T> Set<Class<? extends T>> getAllSubtypes(Class<T> superclass) {
        return getAllSubtypes(superclass, SearchPolicy.ALL, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Returns all subtypes that extends the given class.
     * <p>
     * <b>Since Java 9, this can find only {@code jdk.internal.*} or classes in own packages.</b>
     *
     * @param superclass   superclass
     * @param searchPolicy policy of search
     * @param <T>          type of superclass
     * @return all subtypes
     */
    public static <T> Set<Class<? extends T>> getAllSubtypes(Class<T> superclass, SearchPolicy searchPolicy) {
        return getAllSubtypes(superclass, searchPolicy, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Returns all subtypes that extends the given class.
     * <p>
     * <b>Since Java 9, this can find only {@code jdk.internal.*} or classes in own packages.</b>
     *
     * @param superclass   superclass
     * @param searchPolicy policy of search
     * @param classLoader  class loader
     * @param <T>          type of superclass
     * @return all subtypes
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<Class<? extends T>> getAllSubtypes(Class<T> superclass, SearchPolicy searchPolicy,
            ClassLoader classLoader) {
        Pattern pattern = Pattern.compile("^[a-zA-Z].+\\$\\d+.*$");

        Set<Class<? extends T>> subclasses = new HashSet<>();
        findClasses(name -> {
            try {
                // Excludes anonymous classes that cannot be found.
                if (pattern.matcher(name).matches()) {
                    return true;
                }

                if (name.endsWith("package-info") || name.endsWith("module-info")) {
                    return true;
                }

                Class<?> clazz = Class.forName(name, false, classLoader);
                if (!searchPolicy.search(superclass, clazz)) {
                    return true;
                }

                return subclasses.add((Class<? extends T>) clazz);
            } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
                return false;
            }
        });

        return subclasses;
    }

    /**
     * Visits all classes in classpath.
     * <p>
     * <b>Since Java 9, this can find only {@code jdk.internal.*} or classes in own packages.</b>
     *
     * @param visitor visitor
     */
    public static void findClasses(Predicate<String> visitor) {
        File file = new File(System.getProperty("java.home"), "lib");
        if (file.exists()) {
            findClasses0(file, file, true, visitor);
        }

        String classpath = System.getProperty("java.class.path");
        String[] paths = classpath.split(File.pathSeparator); // System.getProperty("path.separator")
        for (String path : paths) {
            file = new File(path);
            if (!file.exists()) {
                continue;
            }

            findClasses0(file, file, false, visitor);
        }
    }

    private static boolean findClasses0(File root, File file, boolean includeJars, Predicate<String> visitor) {
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                if (!findClasses0(root, child, includeJars, visitor)) {
                    return false;
                }
            }
        }

        String fileName = file.getName().toLowerCase(Locale.ROOT);
        if (includeJars && fileName.endsWith(".jar")) {
            try (JarFile jar = new JarFile(file)) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    int extIndex = name.lastIndexOf(".class");
                    if (extIndex > 0) {
                        String className = name.substring(0, extIndex).replace('/', '.');
                        if (!visitor.test(className)) {
                            return false;
                        }
                    }
                }
            } catch (IOException ignored) {
                return true;
            }
        } else if (fileName.endsWith(".class")) {
            return visitor.test(createClassName(root, file));
        }

        return true;
    }

    private static String createClassName(File root, File file) {
        StringBuilder sb = new StringBuilder();
        String fileName = file.getName();
        sb.append(fileName, 0, fileName.lastIndexOf(".class"));
        file = file.getParentFile();
        while (file != null && !file.equals(root)) {
            sb.insert(0, '.').insert(0, file.getName());
            file = file.getParentFile();
        }

        return sb.toString();
    }

    public enum SearchPolicy {
        /**
         * Search all subtypes(sub-interfaces, subclasses and implementations).
         */
        ALL {
            @Override
            public boolean search(Class<?> superclass, @Nullable Class<?> subclass) {
                Asserts.that(superclass).isNotNull();
                if (subclass == null || superclass == subclass) {
                    return false;
                }

                return superclass.isAssignableFrom(subclass);
            }
        },

        /**
         * Search all subclasses, but not interface.
         */
        CLASS {
            @Override
            public boolean search(Class<?> superclass, @Nullable Class<?> subclass) {
                return ALL.search(superclass, subclass) && !subclass.isInterface();
            }
        },

        /**
         * Search all implementations, but not class.
         */
        INTERFACE {
            @Override
            public boolean search(Class<?> superclass, @Nullable Class<?> subclass) {
                return ALL.search(superclass, subclass) && subclass.isInterface();
            }
        };

        public abstract boolean search(Class<?> superclass, @Nullable Class<?> subclass);
    }

}
