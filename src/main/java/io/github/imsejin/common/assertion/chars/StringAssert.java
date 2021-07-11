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

package io.github.imsejin.common.assertion.chars;

import java.util.regex.Pattern;

public class StringAssert<SELF extends StringAssert<SELF>> extends AbstractCharSequenceAssert<SELF, String> {

    public StringAssert(String actual) {
        super(actual);
    }

    public SELF hasText() {
        for (char c : actual.toCharArray()) {
            if (!Character.isWhitespace(c)) return self;
        }

        throw getException();
    }

    public SELF isNumeric() {
        if (actual.isEmpty()) throw getException();

        for (char c : actual.toCharArray()) {
            if (!Character.isDigit(c)) throw getException();
        }

        return self;
    }

    public SELF isLetter() {
        for (char c : actual.toCharArray()) {
            if (!Character.isLetter(c)) throw getException();
        }

        return self;
    }

    public SELF isLetterOrDigit() {
        for (char c : actual.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) throw getException();
        }

        return self;
    }

    public SELF isUpperCase() {
        for (char c : actual.toCharArray()) {
            if (!Character.isUpperCase(c)) throw getException();
        }

        return self;
    }

    public SELF isLowerCase() {
        for (char c : actual.toCharArray()) {
            if (!Character.isLowerCase(c)) throw getException();
        }

        return self;
    }

    public SELF isAlphabetic() {
        for (char c : actual.toCharArray()) {
            if (!Character.isAlphabetic(c)) throw getException();
        }

        return self;
    }

    public SELF startsWith(String expected) {
        if (!actual.startsWith(expected)) throw getException();
        return self;
    }

    public SELF endsWith(String expected) {
        if (!actual.endsWith(expected)) throw getException();
        return self;
    }

    public SELF contains(CharSequence expected) {
        if (!actual.contains(expected)) throw getException();
        return self;
    }

    public SELF matches(String expected) {
        if (!actual.matches(expected)) throw getException();
        return self;
    }

    public SELF matches(Pattern expected) {
        if (!expected.matcher(actual).matches()) throw getException();
        return self;
    }

}
