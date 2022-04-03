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

import java.util.Objects;

public class CharacterAssert<SELF extends CharacterAssert<SELF>> extends ObjectAssert<SELF, Character> {

    private static final Character NULL_CHARACTER = '\u0000';

    public CharacterAssert(Character actual) {
        super(actual);
    }

    public SELF isGreaterThan(Character expected) {
        if (actual <= expected) throw getException();
        return self;
    }

    public SELF isGreaterThanOrEqualTo(Character expected) {
        if (actual < expected) throw getException();
        return self;
    }

    public SELF isLessThan(Character expected) {
        if (actual >= expected) throw getException();
        return self;
    }

    public SELF isLessThanOrEqualTo(Character expected) {
        if (actual > expected) throw getException();
        return self;
    }

    public SELF isZero() {
        if (!Objects.equals(actual, NULL_CHARACTER)) throw getException();
        return self;
    }

    public SELF isNotZero() {
        if (Objects.equals(actual, NULL_CHARACTER)) throw getException();
        return self;
    }

    public SELF isDigit() {
        if (!Character.isDigit(actual)) throw getException();
        return self;
    }

    public SELF isLetter() {
        if (!Character.isLetter(actual)) throw getException();
        return self;
    }

    public SELF isLetterOrDigit() {
        if (!Character.isLetterOrDigit(actual)) throw getException();
        return self;
    }

    public SELF isUpperCase() {
        if (!Character.isUpperCase(actual)) throw getException();
        return self;
    }

    public SELF isLowerCase() {
        if (!Character.isLowerCase(actual)) throw getException();
        return self;
    }

    public SELF isAlphabetic() {
        if (!Character.isAlphabetic(actual)) throw getException();
        return self;
    }

    public SELF isSpaceChar() {
        if (!Character.isSpaceChar(actual)) throw getException();
        return self;
    }

    public SELF isWhitespace() {
        if (!Character.isWhitespace(actual)) throw getException();
        return self;
    }

}
