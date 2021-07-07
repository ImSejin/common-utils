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

package io.github.imsejin.common.assertion.reflect;

import io.github.imsejin.common.assertion.object.ObjectAssert;
import io.github.imsejin.common.tool.TypeClassifier;

import java.lang.reflect.Modifier;

@SuppressWarnings("unchecked")
public class ClassAssert<SELF extends ClassAssert<SELF, T>, T> extends ObjectAssert<SELF> {

    private final Class<T> actual;

    public ClassAssert(Class<T> actual) {
        super(actual);
        this.actual = actual;
    }

    /**
     * Verifies this is actual type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param expected instance of this type
     * @return whether this is actual type of the instance
     */
    public SELF isActualTypeOf(Object expected) {
        if (!TypeClassifier.toWrapper(actual).isInstance(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * Verifies this is not actual type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param expected instance of non-matched this type
     * @return whether this is not actual type of the instance
     */
    public SELF isNotActualTypeOf(Object expected) {
        if (TypeClassifier.toWrapper(actual).isInstance(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @param expected sub type
     * @return whether this is assignable from given type
     */
    public SELF isAssignableFrom(Class<?> expected) {
        if (!this.actual.isAssignableFrom(expected)) throw getException();
        return (SELF) this;
    }

    /**
     * @param expected sub type
     * @return whether this is super class of given type
     */
    public SELF isSuperclassOf(Class<?> expected) {
        if (this.actual != expected.getSuperclass()) throw getException();
        return (SELF) this;
    }

    /**
     * @param expected super type
     * @return whether this is sub class of given type
     */
    public SELF isSubclassOf(Class<?> expected) {
        if (this.actual.getSuperclass() != expected) throw getException();
        return (SELF) this;
    }

    public SELF isPrimitive() {
        if (!this.actual.isPrimitive()) throw getException();
        return (SELF) this;
    }

    public SELF isInterface() {
        if (!this.actual.isInterface()) throw getException();
        return (SELF) this;
    }

    public SELF isAnnotation() {
        if (!this.actual.isAnnotation()) throw getException();
        return (SELF) this;
    }

    public SELF isAbstractClass() {
        if (!Modifier.isAbstract(this.actual.getModifiers())) throw getException();
        return (SELF) this;
    }

    public SELF isAnonymousClass() {
        if (!this.actual.isAnonymousClass()) throw getException();
        return (SELF) this;
    }

    public SELF isEnum() {
        if (!this.actual.isEnum()) throw getException();
        return (SELF) this;
    }

    public SELF isArray() {
        if (!this.actual.isArray()) throw getException();
        return (SELF) this;
    }

    public SELF isMemberClass() {
        if (!this.actual.isMemberClass()) throw getException();
        return (SELF) this;
    }

    public SELF isLocalClass() {
        if (!this.actual.isLocalClass()) throw getException();
        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public PackageAssert<?> asPackage() {
        return new PackageAssert<>(this.actual.getPackage());
    }

}
