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

import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class StringAsserts<SELF extends StringAsserts<SELF>> extends CharSequenceAsserts<SELF> {

    private final String actual;

    StringAsserts(String actual) {
        super(actual);
        this.actual = actual;
    }

    public SELF hasText() {
        for (char c : this.actual.toCharArray()) {
            if (!Character.isWhitespace(c)) return (SELF) this;
        }

        throw getException();
    }

    public SELF isNumeric() {
        if (this.actual.isEmpty()) throw getException();

        for (char c : this.actual.toCharArray()) {
            if (!Character.isDigit(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isLetter() {
        for (char c : this.actual.toCharArray()) {
            if (!Character.isLetter(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isLetterOrDigit() {
        for (char c : this.actual.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isUpperCase() {
        for (char c : this.actual.toCharArray()) {
            if (!Character.isUpperCase(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isLowerCase() {
        for (char c : this.actual.toCharArray()) {
            if (!Character.isLowerCase(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isAlphabetic() {
        for (char c : this.actual.toCharArray()) {
            if (!Character.isAlphabetic(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF startsWith(String expected) {
        if (!this.actual.startsWith(expected)) throw getException();
        return (SELF) this;
    }

    public SELF endsWith(String expected) {
        if (!this.actual.endsWith(expected)) throw getException();
        return (SELF) this;
    }

    public SELF contains(CharSequence expected) {
        if (!this.actual.contains(expected)) throw getException();
        return (SELF) this;
    }

    public SELF matches(String expected) {
        if (!this.actual.matches(expected)) throw getException();
        return (SELF) this;
    }

    public SELF matches(Pattern expected) {
        if (!expected.matcher(this.actual).matches()) throw getException();
        return (SELF) this;
    }

}
