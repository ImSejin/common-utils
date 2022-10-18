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

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.SizeComparisonAssertable;

import java.util.AbstractMap.SimpleEntry;

/**
 * Assertion for {@link Character}
 *
 * @param <SELF> this class
 */
public class CharacterAssert<
        SELF extends CharacterAssert<SELF>>
        extends ObjectAssert<SELF, Character>
        implements SizeComparisonAssertable<SELF, Character> {

    public CharacterAssert(Character actual) {
        super(actual);
    }

    protected CharacterAssert(Descriptor<?> descriptor, Character actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF isGreaterThan(Character expected) {
        if (!SizeComparisonAssertable.IS_GREATER_THAN.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN, expected, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(Character expected) {
        if (!SizeComparisonAssertable.IS_GREATER_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO, expected, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThan(Character expected) {
        if (!SizeComparisonAssertable.IS_LESS_THAN.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN, expected, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThanOrEqualTo(Character expected) {
        if (!SizeComparisonAssertable.IS_LESS_THAN_OR_EQUAL_TO.test(actual, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO, expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isDigit() {
        if (!Character.isDigit(actual)) {
            setDefaultDescription("It is expected to be digit, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isLetter() {
        if (!Character.isLetter(actual)) {
            setDefaultDescription("It is expected to be letter, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isLetterOrDigit() {
        if (!Character.isLetterOrDigit(actual)) {
            setDefaultDescription("It is expected to be letter or digit, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isUpperCase() {
        if (!Character.isUpperCase(actual)) {
            setDefaultDescription("It is expected to be upper case, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isLowerCase() {
        if (!Character.isLowerCase(actual)) {
            setDefaultDescription("It is expected to be lower case, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isAlphabetic() {
        if (!Character.isAlphabetic(actual)) {
            setDefaultDescription("It is expected to be alphabetic, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isSpaceChar() {
        if (!Character.isSpaceChar(actual)) {
            setDefaultDescription("It is expected to be space character, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isWhitespace() {
        if (!Character.isWhitespace(actual)) {
            setDefaultDescription("It is expected to be whitespace, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

}
