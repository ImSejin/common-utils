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

import java.lang.reflect.Modifier;

@SuppressWarnings("unchecked")
public class ClassAsserts<SELF extends ClassAsserts<SELF, T>, T> extends ObjectAsserts<SELF> {

    private final Class<T> target;

    ClassAsserts(Class<T> target) {
        super(target);
        this.target = target;
    }

    public SELF isActualTypeOf(Object instance) {
        if (!this.target.isInstance(instance)) throw getException();
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

    public SELF isAbstractClass() {
        if (!Modifier.isAbstract(this.target.getModifiers())) throw getException();
        return (SELF) this;
    }

    public SELF isAnonymousClass() {
        if (!this.target.isAnonymousClass()) throw getException();
        return (SELF) this;
    }

    public SELF isAnnotation() {
        if (!this.target.isAnnotation()) throw getException();
        return (SELF) this;
    }

}
