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

import io.github.imsejin.common.tool.TypeClassifier;

import java.lang.reflect.Modifier;

@SuppressWarnings("unchecked")
public class ClassAsserts<SELF extends ClassAsserts<SELF, T>, T> extends ObjectAsserts<SELF> {

    private final Class<T> target;

    ClassAsserts(Class<T> target) {
        super(target);
        this.target = target;
    }

    /**
     * Verifies this is actual type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param instance instance of this type
     * @return whether this is actual type of the instance
     */
    public SELF isActualTypeOf(Object instance) {
        if (!TypeClassifier.toWrapper(target).isInstance(instance)) throw getException();
        return (SELF) this;
    }

    /**
     * Verifies this is not actual type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param instance instance of non-matched this type
     * @return whether this is not actual type of the instance
     */
    public SELF isNotActualTypeOf(Object instance) {
        if (TypeClassifier.toWrapper(target).isInstance(instance)) throw getException();
        return (SELF) this;
    }

    public SELF isAssignableFrom(Class<?> subType) {
        if (!this.target.isAssignableFrom(subType)) throw getException();
        return (SELF) this;
    }

    public SELF isSuperclass(Class<?> subType) {
        if (this.target != subType.getSuperclass()) throw getException();
        return (SELF) this;
    }

    public SELF isSubclass(Class<?> superType) {
        if (this.target.getSuperclass() != superType) throw getException();
        return (SELF) this;
    }

    public SELF isPrimitive() {
        if (!this.target.isPrimitive()) throw getException();
        return (SELF) this;
    }

    public SELF isInterface() {
        if (!this.target.isInterface()) throw getException();
        return (SELF) this;
    }

    public SELF isAnnotation() {
        if (!this.target.isAnnotation()) throw getException();
        return (SELF) this;
    }

    public SELF isAbstractClass() {
        if (!Modifier.isAbstract(this.target.getModifiers())) throw getException();
        return (SELF) this;
    }

    public SELF isAnonymousClass() {
        if (!this.target.isAnonymousClass()) throw getException();
        return (SELF) this;
    }

    public SELF isEnum() {
        if (!this.target.isEnum()) throw getException();
        return (SELF) this;
    }

    public SELF isArray() {
        if (!this.target.isArray()) throw getException();
        return (SELF) this;
    }

    public SELF isMemberClass() {
        if (!this.target.isMemberClass()) throw getException();
        return (SELF) this;
    }

    public SELF isLocalClass() {
        if (!this.target.isLocalClass()) throw getException();
        return (SELF) this;
    }

}
