/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

/**
 * Assertion for {@link Class}
 *
 * @param <SELF> this class
 * @param <T>    class type
 */
public class ClassAssert<SELF extends ClassAssert<SELF, T>, T> extends ObjectAssert<SELF, Class<?>> {

    public ClassAssert(Class<T> actual) {
        super(actual);
    }

    protected ClassAssert(Descriptor<?> descriptor, Class<?> actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual type is assignable from expected type.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Number.class).isAssignableFrom(Long.class);
     *     Asserts.that(Iterable.class).isAssignableFrom(List.class);
     *     Asserts.that(CharSequence.class).isAssignableFrom(StringBuilder.class);
     *
     *     // Assertion will fail.
     *     Asserts.that(long.class).isAssignableFrom(int.class);
     *     Asserts.that(Long.class).isAssignableFrom(long.class);
     *     Asserts.that(StringBuilder.class).isAssignableFrom(CharSequence.class);
     * }</pre>
     *
     * @param expected expected type
     * @return this class
     */
    public SELF isAssignableFrom(Class<?> expected) {
        if (!actual.isAssignableFrom(expected)) {
            setDefaultDescription("It is expected to be assignable from the given type, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is superclass of expected type.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Number.class).isSuperclassOf(Long.class);
     *     Asserts.that(Object.class).isSuperclassOf(String.class);
     *     Asserts.that(AbstractList.class).isSuperclassOf(ArrayList.class);
     *
     *     // Assertion will fail.
     *     Asserts.that(long.class).isSuperclassOf(int.class);
     *     Asserts.that(Long.class).isSuperclassOf(long.class);
     *     Asserts.that(CharSequence.class).isSuperclassOf(StringBuilder.class);
     * }</pre>
     *
     * @param expected expected type
     * @return this class
     */
    public SELF isSuperclassOf(Class<?> expected) {
        if (actual == null || actual != expected.getSuperclass()) {
            setDefaultDescription("It is expected to be superclass of the given type, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is subclass of expected type.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Long.class).isSubclassOf(Number.class);
     *     Asserts.that(String.class).isSubclassOf(Object.class);
     *     Asserts.that(ArrayList.class).isSubclassOf(AbstractList.class);
     *
     *     // Assertion will fail.
     *     Asserts.that(int.class).isSubclassOf(long.class);
     *     Asserts.that(long.class).isSubclassOf(Long.class);
     *     Asserts.that(StringBuilder.class).isSubclassOf(CharSequence.class);
     * }</pre>
     *
     * @param expected expected type
     * @return this class
     */
    public SELF isSubclassOf(Class<?> expected) {
        if (expected == null || actual.getSuperclass() != expected) {
            setDefaultDescription("It is expected to be subclass of the given type, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is primitive.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(boolean.class).isPrimitive();
     *     Asserts.that(int.class).isPrimitive();
     *     Asserts.that(double.class).isPrimitive();
     *
     *     // Assertion will fail.
     *     Asserts.that(Boolean.class).isPrimitive();
     *     Asserts.that(Integer.class).isPrimitive();
     *     Asserts.that(Double.class).isPrimitive();
     * }</pre>
     *
     * @return this class
     */
    public SELF isPrimitive() {
        if (!actual.isPrimitive()) {
            setDefaultDescription("It is expected to be primitive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is interface.
     *
     * <p> If the actual value is annotation, this assertion is always passed
     * because all the annotation extends interface {@link Annotation}.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Member.class).isInterface();
     *     Asserts.that(Override.class).isInterface();
     *     Asserts.that(CharSequence.class).isInterface();
     *
     *     // Assertion will fail.
     *     Asserts.that(Object.class).isInterface();
     *     Asserts.that(Enum.class).isInterface();
     *     Asserts.that(String[].class).isInterface();
     * }</pre>
     *
     * @return this class
     * @see #isAnnotation()
     */
    public SELF isInterface() {
        if (!actual.isInterface()) {
            setDefaultDescription("It is expected to be interface, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is annotation.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Override.class).isAnnotation();
     *     Asserts.that(SafeVarargs.class).isAnnotation();
     *     Asserts.that(SuppressWarnings.class).isAnnotation();
     *
     *     // Assertion will fail.
     *     Asserts.that(Object.class).isAnnotation();
     *     Asserts.that(Annotation.class).isAnnotation();
     *     Asserts.that(FunctionalInterface[].class).isAnnotation();
     * }</pre>
     *
     * @return this class
     */
    public SELF isAnnotation() {
        if (!actual.isAnnotation()) {
            setDefaultDescription("It is expected to be annotation, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is final class.
     *
     * <p> Notice: in case of {@code enum}, result of this assertion depends on jdk version.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(int.class).isFinalClass();
     *     Asserts.that(String.class).isFinalClass();
     *     Asserts.that(LocalDate.class).isFinalClass();
     *
     *     // Assertion will fail.
     *     Asserts.that(Object.class).isFinalClass();
     *     Asserts.that(Enum.class).isFinalClass();
     *     Asserts.that(Comparable.class).isFinalClass();
     * }</pre>
     *
     * @return this class
     */
    public SELF isFinalClass() {
        if (!Modifier.isFinal(actual.getModifiers())) {
            setDefaultDescription("It is expected to be final class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is abstract class.
     *
     * <p> Notice: in case of {@code enum}, result of this assertion depends on jdk version.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Enum.class).isAbstractClass();
     *     Asserts.that(Number.class).isAbstractClass();
     *     Asserts.that(OutputStream.class).isAbstractClass();
     *
     *     // Assertion will fail.
     *     Asserts.that(int.class).isAbstractClass();
     *     Asserts.that(String.class).isAbstractClass();
     *     Asserts.that(Comparable.class).isAbstractClass();
     * }</pre>
     *
     * @return this class
     */
    public SELF isAbstractClass() {
        if (!ClassUtils.isAbstractClass(actual)) {
            setDefaultDescription("It is expected to be abstract class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is anonymous class.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(new Object() {}.class).isAnonymousClass();
     *     Asserts.that(new Random() {}.class).isAnonymousClass();
     *     Asserts.that(new Serializable() {}.class).isAnonymousClass();
     *
     *     // Assertion will fail.
     *     Asserts.that(byte.class).isAnonymousClass();
     *     Asserts.that(Object.class).isAnonymousClass();
     *     Asserts.that(Enum.class).isAnonymousClass();
     * }</pre>
     *
     * @return this class
     */
    public SELF isAnonymousClass() {
        if (!actual.isAnonymousClass()) {
            setDefaultDescription("It is expected to be anonymous class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is enum.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Enum.class).isEnum();
     *     Asserts.that(Month.class).isEnum();
     *     Asserts.that(TimeUnit.class).isEnum();
     *
     *     // Assertion will fail.
     *     Asserts.that(byte.class).isEnum();
     *     Asserts.that(Object.class).isEnum();
     *     Asserts.that(Integer.class).isEnum();
     * }</pre>
     *
     * @return this class
     */
    public SELF isEnum() {
        if (!ClassUtils.isEnumOrEnumConstant(actual)) {
            setDefaultDescription("It is expected to be enum, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is type of array.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(int[].class).isArray();
     *     Asserts.that(Object[][].class).isArray();
     *     Asserts.that(String[][][].class).isArray();
     *
     *     // Assertion will fail.
     *     Asserts.that(int.class).isArray();
     *     Asserts.that(Object.class).isArray();
     *     Asserts.that(String.class).isArray();
     * }</pre>
     *
     * @return this class
     */
    public SELF isArray() {
        if (!actual.isArray()) {
            setDefaultDescription("It is expected to be array, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is member class.
     *
     * <pre>{@code
     *     Object o = new Object() { class A {} };
     *
     *     // Assertion will pass.
     *     Asserts.that(Map.Entry.class).isMemberClass();
     *     Asserts.that(AbstractMap.SimpleEntry.class).isMemberClass();
     *     Asserts.that(o.getClass().getDeclaredClasses()[0]).isMemberClass();
     *
     *     // Assertion will fail.
     *     Asserts.that(int.class).isMemberClass();
     *     Asserts.that(Object.class).isMemberClass();
     *     Asserts.that(Map.class).isMemberClass();
     * }</pre>
     *
     * @return this class
     */
    public SELF isMemberClass() {
        if (!actual.isMemberClass()) {
            setDefaultDescription("It is expected to be member class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual type is local class.
     *
     * <pre>{@code
     *     class A {}
     *
     *     // Assertion will pass.
     *     Asserts.that(A.class).isLocalClass();
     *
     *     // Assertion will fail.
     *     Asserts.that(double.class).isLocalClass();
     *     Asserts.that(Object.class).isLocalClass();
     *     Asserts.that(new Object {}.class).isLocalClass();
     * }</pre>
     *
     * @return this class
     */
    public SELF isLocalClass() {
        if (!actual.isLocalClass()) {
            setDefaultDescription("It is expected to be local class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into its super class.
     *
     * <pre>{@code
     *     Asserts.that(Object.class)
     *             .isEqualTo(Object.class)
     *             .asSuperclass()
     *             .isNull();
     * }</pre>
     *
     * @return assertion for class
     */
    public ClassAssert<?, Class<?>> asSuperclass() {
        Class<?> superclass = actual.getSuperclass();
        return new ClassAssert<>(this, superclass);
    }

    /**
     * Converts actual value into its package.
     *
     * <pre>{@code
     *     Asserts.that(Object.class)
     *             .isEqualTo(Object.class)
     *             .asPackage()
     *             .isSuperPackageOf(Package.getPackage("java.lang.reflect"));
     * }</pre>
     *
     * @return assertion for package
     */
    public PackageAssert<?> asPackage() {
        Package pack = actual.getPackage();
        return new PackageAssert<>(this, pack);
    }

}
