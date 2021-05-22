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

@SuppressWarnings("unchecked")
public class CharacterAsserts<SELF extends CharacterAsserts<SELF>> extends Descriptor<SELF> {

    private final char target;

    CharacterAsserts(char target) {
        this.target = target;
    }

    public SELF isDigit() {
        if (!Character.isDigit(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isLetter() {
        if (!Character.isLetter(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isLetterOrDigit() {
        if (!Character.isLetterOrDigit(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isUpperCase() {
        if (!Character.isUpperCase(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isLowerCase() {
        if (!Character.isLowerCase(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isAlphabetic() {
        if (!Character.isAlphabetic(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isSpaceChar() {
        if (!Character.isSpaceChar(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isWhitespace() {
        if (!Character.isWhitespace(this.target)) throw getException();
        return (SELF) this;
    }

    public SELF isEqualTo(char c) {
        if (this.target != c) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThan(char c) {
        if (this.target <= c) throw getException();
        return (SELF) this;
    }

    public SELF isGreaterThanOrEqualTo(char c) {
        if (this.target < c) throw getException();
        return (SELF) this;
    }

    public SELF isLessThan(char c) {
        if (this.target >= c) throw getException();
        return (SELF) this;
    }

    public SELF isLessThanOrEqualTo(char c) {
        if (this.target > c) throw getException();
        return (SELF) this;
    }

}
