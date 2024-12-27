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

import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Pattern;

import org.intellij.lang.annotations.Language;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.AmountComparisonAssertable;

/**
 * Assertion for {@link String}
 *
 * @param <SELF> this class
 */
public class StringAssert<
        SELF extends StringAssert<SELF>>
        extends CharSequenceAssert<SELF, String, CharSequence>
        implements AmountComparisonAssertable<SELF, String> {

    public StringAssert(String actual) {
        super(actual);
    }

    protected StringAssert(Descriptor<?> descriptor, String actual) {
        super(descriptor, actual);
    }

    public SELF hasText() {
        for (char c : actual.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return self;
            }
        }

        setDefaultDescription("It is expected to have text, but it isn't.");
        setDescriptionVariables(new SimpleEntry<>("actual", actual));

        throw getException();
    }

    public SELF isNumeric() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to be numeric, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        for (char c : actual.toCharArray()) {
            if (!Character.isDigit(c)) {
                setDefaultDescription("It is expected to be numeric, but it isn't.");
                setDescriptionVariables(new SimpleEntry<>("actual", actual));

                throw getException();
            }
        }

        return self;
    }

    public SELF isLetter() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to be letter, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        for (char c : actual.toCharArray()) {
            if (!Character.isLetter(c)) {
                setDefaultDescription("It is expected to be letter, but it isn't.");
                setDescriptionVariables(new SimpleEntry<>("actual", actual));

                throw getException();
            }
        }

        return self;
    }

    public SELF isLetterOrDigit() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to be letter or digit, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        for (char c : actual.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                setDefaultDescription("It is expected to be letter or digit, but it isn't.");
                setDescriptionVariables(new SimpleEntry<>("actual", actual));

                throw getException();
            }
        }

        return self;
    }

    public SELF isUpperCase() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to have only uppercase letter(s), but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        for (char c : actual.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                setDefaultDescription("It is expected to have only uppercase letter(s), but it isn't.");
                setDescriptionVariables(new SimpleEntry<>("actual", actual));

                throw getException();
            }
        }

        return self;
    }

    public SELF isLowerCase() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to have only lowercase letter(s), but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        for (char c : actual.toCharArray()) {
            if (!Character.isLowerCase(c)) {
                setDefaultDescription("It is expected to have only lowercase letter(s), but it isn't.");
                setDescriptionVariables(new SimpleEntry<>("actual", actual));

                throw getException();
            }
        }

        return self;
    }

    public SELF isAlphabetic() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to be alphabetic, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        for (char c : actual.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                setDefaultDescription("It is expected to be alphabetic, but it isn't.");
                setDescriptionVariables(new SimpleEntry<>("actual", actual));

                throw getException();
            }
        }

        return self;
    }

    public SELF startsWith(String expected) {
        if (expected == null || !actual.startsWith(expected)) {
            setDefaultDescription("It is expected to start with the given string, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF endsWith(String expected) {
        if (expected == null || !actual.endsWith(expected)) {
            setDefaultDescription("It is expected to end with the given string, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF matches(@Language("RegExp") String expected) {
        if (!actual.matches(expected)) {
            setDefaultDescription("It is expected to match the given regular expression, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF matches(Pattern expected) {
        if (!expected.matcher(actual).matches()) {
            setDefaultDescription("It is expected to match the given pattern, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is greater than expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("b").isGreaterThan("a");
     *     Asserts.that("ZZZ").isGreaterThan("A");
     *
     *     // Assertion will fail.
     *     Asserts.that("a").isGreaterThan("a");
     *     Asserts.that("a").isGreaterThan("b");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isGreaterThan(String expected) {
        if (actual.compareTo(expected) <= 0) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is greater than or equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("a").isGreaterThanOrEqualTo("a");
     *     Asserts.that("ZZZ").isGreaterThanOrEqualTo("A");
     *
     *     // Assertion will fail.
     *     Asserts.that("a").isGreaterThanOrEqualTo("b");
     *     Asserts.that("A").isGreaterThanOrEqualTo("ZZZ");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isGreaterThanOrEqualTo(String expected) {
        if (actual.compareTo(expected) < 0) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is less than expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("a").isLessThan("b");
     *     Asserts.that("A").isLessThan("ZZZ");
     *
     *     // Assertion will fail.
     *     Asserts.that("a").isLessThan("a");
     *     Asserts.that("ZZZ").isLessThan("A");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isLessThan(String expected) {
        if (actual.compareTo(expected) >= 0) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is less than or equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("a").isLessThanOrEqualTo("a");
     *     Asserts.that("A").isLessThanOrEqualTo("ZZZ");
     *
     *     // Assertion will fail.
     *     Asserts.that("b").isLessThanOrEqualTo("a");
     *     Asserts.that("ZZZ").isLessThanOrEqualTo("A");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF isLessThanOrEqualTo(String expected) {
        if (actual.compareTo(expected) > 0) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

}
