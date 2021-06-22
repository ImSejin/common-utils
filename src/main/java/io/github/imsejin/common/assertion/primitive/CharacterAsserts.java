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

    private static final Character NULL_CHARACTER = '\u0000';

    private final Character actual;

    public CharacterAsserts(Character actual) {
        this.actual = actual;
    }

    public SELF isEqualTo(Character expected) {
        if (this.actual != expected) throw getException();
        return (SELF) this;
    }

    public SELF isNotEqualTo(Character expected) {
        if (this.actual == expected) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThan(Character expected) {
        if (this.actual <= expected) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThanOrEqualTo(Character expected) {
        if (this.actual < expected) throw getException();
        return (SELF) this;
    }

    public SELF isLessThan(Character expected) {
        if (this.actual >= expected) throw getException();
        return (SELF) this;
    }

    public SELF isLessThanOrEqualTo(Character expected) {
        if (this.actual > expected) throw getException();
        return (SELF) this;
    }

    public SELF isZero() {
        if (this.actual != NULL_CHARACTER) throw getException();
        return (SELF) this;
    }

    public SELF isNotZero() {
        if (this.actual == NULL_CHARACTER) throw getException();
        return (SELF) this;
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

}
