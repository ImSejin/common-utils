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

package io.github.imsejin.common.asserts;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public class StringAsserts extends CharSequenceAsserts {

    private final String target;

    StringAsserts(String target) {
        super(target);
        this.target = target;
    }

    public StringAsserts isNumeric(String message) {
        hasText(message);
        for (char c : this.target.toCharArray()) {
            if (!Character.isDigit(c)) throw new IllegalArgumentException(message);
        }

        return this;
    }

    public StringAsserts isNumeric(Supplier<? extends RuntimeException> supplier) {
        hasText(supplier);
        for (char c : this.target.toCharArray()) {
            if (!Character.isDigit(c)) throw supplier.get();
        }

        return this;
    }

    public StringAsserts isUpperCase(String message) {
        hasText(message);
        for (char c : this.target.toCharArray()) {
            if (!Character.isUpperCase(c)) throw new IllegalArgumentException(message);
        }

        return this;
    }

    public StringAsserts isUpperCase(Supplier<? extends RuntimeException> supplier) {
        hasText(supplier);
        for (char c : this.target.toCharArray()) {
            if (!Character.isUpperCase(c)) throw supplier.get();
        }

        return this;
    }

    public StringAsserts isLowerCase(String message) {
        hasText(message);
        for (char c : this.target.toCharArray()) {
            if (!Character.isLowerCase(c)) throw new IllegalArgumentException(message);
        }

        return this;
    }

    public StringAsserts isLowerCase(Supplier<? extends RuntimeException> supplier) {
        hasText(supplier);
        for (char c : this.target.toCharArray()) {
            if (!Character.isLowerCase(c)) throw supplier.get();
        }

        return this;
    }

    public StringAsserts matches(String regex, String message) {
        isNotNull(message);
        if (!this.target.matches(regex)) throw new IllegalArgumentException(message);

        return this;
    }

    public StringAsserts matches(String regex, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.matches(regex)) throw supplier.get();

        return this;
    }

    public StringAsserts matches(Pattern pattern, String message) {
        isNotNull(message);
        if (!pattern.matcher(this.target).matches()) throw new IllegalArgumentException(message);

        return this;
    }

    public StringAsserts matches(Pattern pattern, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!pattern.matcher(this.target).matches()) throw supplier.get();

        return this;
    }

}
