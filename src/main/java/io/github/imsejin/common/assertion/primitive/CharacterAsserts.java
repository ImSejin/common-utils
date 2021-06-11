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

package io.github.imsejin.common.assertion.primitive;

import io.github.imsejin.common.assertion.Descriptor;

@SuppressWarnings("unchecked")
public class CharacterAsserts<SELF extends CharacterAsserts<SELF>> extends Descriptor<SELF> {

    private final char actual;

    public CharacterAsserts(char actual) {
        this.actual = actual;
    }

    public SELF isDigit() {
        if (!Character.isDigit(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isLetter() {
        if (!Character.isLetter(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isLetterOrDigit() {
        if (!Character.isLetterOrDigit(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isUpperCase() {
        if (!Character.isUpperCase(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isLowerCase() {
        if (!Character.isLowerCase(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isAlphabetic() {
        if (!Character.isAlphabetic(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isSpaceChar() {
        if (!Character.isSpaceChar(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isWhitespace() {
        if (!Character.isWhitespace(this.actual)) throw getException();
        return (SELF) this;
    }

    public SELF isEqualTo(char expected) {
        if (this.actual != expected) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThan(char expected) {
        if (this.actual <= expected) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThanOrEqualTo(char expected) {
        if (this.actual < expected) throw getException();
        return (SELF) this;
    }

    public SELF isLessThan(char expected) {
        if (this.actual >= expected) throw getException();
        return (SELF) this;
    }

    public SELF isLessThanOrEqualTo(char expected) {
        if (this.actual > expected) throw getException();
        return (SELF) this;
    }

}
