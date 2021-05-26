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

    private final String target;

    StringAsserts(String target) {
        super(target);
        this.target = target;
    }

    public SELF hasText() {
        for (char c : this.target.toCharArray()) {
            if (!Character.isWhitespace(c)) return (SELF) this;
        }

        throw getException();
    }

    public SELF isNumeric() {
        if (this.target.isEmpty()) throw getException();

        for (char c : this.target.toCharArray()) {
            if (!Character.isDigit(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isLetter() {
        for (char c : this.target.toCharArray()) {
            if (!Character.isLetter(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isLetterOrDigit() {
        for (char c : this.target.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isUpperCase() {
        for (char c : this.target.toCharArray()) {
            if (!Character.isUpperCase(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isLowerCase() {
        for (char c : this.target.toCharArray()) {
            if (!Character.isLowerCase(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF isAlphabetic() {
        for (char c : this.target.toCharArray()) {
            if (!Character.isAlphabetic(c)) throw getException();
        }

        return (SELF) this;
    }

    public SELF startsWith(String prefix) {
        if (!this.target.startsWith(prefix)) throw getException();
        return (SELF) this;
    }

    public SELF endsWith(String suffix) {
        if (!this.target.endsWith(suffix)) throw getException();
        return (SELF) this;
    }

    public SELF contains(CharSequence charSequence) {
        if (!this.target.contains(charSequence)) throw getException();
        return (SELF) this;
    }

    public SELF matches(String regex) {
        if (!this.target.matches(regex)) throw getException();
        return (SELF) this;
    }

    public SELF matches(Pattern pattern) {
        if (!pattern.matcher(this.target).matches()) throw getException();
        return (SELF) this;
    }

}
