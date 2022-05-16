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

public class CharacterAssert<SELF extends CharacterAssert<SELF>> extends ObjectAssert<SELF, Character> {

    public CharacterAssert(Character actual) {
        super(actual);
    }

    public SELF isGreaterThan(Character expected) {
        if (actual <= expected) {
            setDefaultDescription("It is expected to be greater than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isGreaterThanOrEqualTo(Character expected) {
        if (actual < expected) {
            setDefaultDescription("It is expected to be greater than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLessThan(Character expected) {
        if (actual >= expected) {
            setDefaultDescription("It is expected to be less than the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isLessThanOrEqualTo(Character expected) {
        if (actual > expected) {
            setDefaultDescription("It is expected to be less than or equal to the other, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

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
