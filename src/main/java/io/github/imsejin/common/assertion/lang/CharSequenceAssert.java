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
import io.github.imsejin.common.assertion.composition.EnumerationAssertable;
import io.github.imsejin.common.assertion.composition.SizeAssertable;

import java.util.AbstractMap.SimpleEntry;

/**
 * Assertion for {@link CharSequence}
 *
 * @param <SELF>    this class
 * @param <ACTUAL>  type of character sequence
 * @param <ELEMENT> characters as elements of {@link ACTUAL}
 */
public class CharSequenceAssert<
        SELF extends CharSequenceAssert<SELF, ACTUAL, ELEMENT>,
        ACTUAL extends CharSequence,
        ELEMENT extends CharSequence>
        extends ObjectAssert<SELF, ACTUAL>
        implements SizeAssertable<SELF, ACTUAL>,
        EnumerationAssertable<SELF, ACTUAL, ELEMENT> {

    public CharSequenceAssert(ACTUAL actual) {
        super(actual);
    }

    protected CharSequenceAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual value is empty.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").isEmpty();
     *
     *     // Assertion will fail.
     *     Asserts.that(" ").isEmpty();
     *     Asserts.that("alpha").isEmpty();
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF isEmpty() {
        int length = actual.length();

        if (length > 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", length));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is not empty.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(" ").isNotEmpty();
     *     Asserts.that("alpha").isNotEmpty();
     *
     *     // Assertion will fail.
     *     Asserts.that("").isNotEmpty();
     * }</pre>
     *
     * @return this class
     */
    @Override
    public SELF isNotEmpty() {
        int length = actual.length();

        if (length <= 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", length));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value has as many characters as expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").hasSize(0);
     *     Asserts.that(" ").hasSize(1);
     *     Asserts.that("alpha").hasSize(5);
     *
     *     // Assertion will fail.
     *     Asserts.that("").hasSize(1);
     *     Asserts.that("alpha").hasSize(4);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSize(long expected) {
        int size = actual.length();

        if (size != expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value doesn't have as many characters as expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").doesNotHaveSize(1);
     *     Asserts.that("alpha").doesNotHaveSize(4);
     *
     *     // Assertion will fail.
     *     Asserts.that("").doesNotHaveSize(0);
     *     Asserts.that(" ").doesNotHaveSize(1);
     *     Asserts.that("alpha").doesNotHaveSize(5);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF doesNotHaveSize(long expected) {
        int size = actual.length();

        if (size == expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value and expected value have the same length.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").hasSameSizeAs("");
     *     Asserts.that(" ").hasSameSizeAs("a");
     *     Asserts.that("alpha").hasSameSizeAs("gamma");
     *
     *     // Assertion will fail.
     *     Asserts.that("").hasSameSizeAs(null);
     *     Asserts.that("").hasSameSizeAs(" ");
     *     Asserts.that("alpha").hasSameSizeAs("beta");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSameSizeAs(ACTUAL expected) {
        int actualSize = actual.length();
        Integer expectedSize = expected == null ? null : expected.length();

        if (expected == null || actualSize != expectedSize) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actualSize),
                    new SimpleEntry<>("expected", expected),
                    new SimpleEntry<>("expected.size", expectedSize));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value and expected value don't have the same length.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").doesNotHaveSameSizeAs(" ");
     *     Asserts.that("alpha").doesNotHaveSameSizeAs("beta");
     *
     *     // Assertion will fail.
     *     Asserts.that("").doesNotHaveSameSizeAs(null);
     *     Asserts.that("").doesNotHaveSameSizeAs("");
     *     Asserts.that(" ").doesNotHaveSameSizeAs("a");
     *     Asserts.that("alpha").doesNotHaveSameSizeAs("gamma");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF doesNotHaveSameSizeAs(ACTUAL expected) {
        int actualSize = actual.length();
        Integer expectedSize = expected == null ? null : expected.length();

        if (expected == null || actualSize == expectedSize) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actualSize),
                    new SimpleEntry<>("expected", expected),
                    new SimpleEntry<>("expected.size", expectedSize));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value contains expected value.
     *
     * <p> If expected value is empty, the assertion always passes.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").contains("");
     *     Asserts.that("alpha").contains("");
     *     Asserts.that("beta").contains("et");
     *     Asserts.that("gamma").contains("gamma");
     *
     *     // Assertion will fail.
     *     Asserts.that("").contains("a");
     *     Asserts.that("alpha").contains("beta");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF contains(ELEMENT expected) {
        if (expected == null || !actual.toString().contains(expected)) {
            setDefaultDescription("It is expected to contain the given character(s), but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value doesn't contain expected value.
     *
     * <p> If expected value is empty, the assertion always fails.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").doesNotContain("a");
     *     Asserts.that("alpha").doesNotContain("ga");
     *     Asserts.that("beta").doesNotContain("beta carotene");
     *
     *     // Assertion will fail.
     *     Asserts.that("").doesNotContain("");
     *     Asserts.that("alpha").doesNotContain("");
     *     Asserts.that("beta").doesNotContain("et");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF doesNotContain(ELEMENT expected) {
        if (expected == null || actual.toString().contains(expected)) {
            setDefaultDescription("It is expected not to contain the given character(s), but it is.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into its length.
     *
     * <pre>{@code
     *     Asserts.that("alpha")
     *             .contains("ph")
     *             .asLength()
     *             .isLessThan(10);
     * }</pre>
     *
     * @return assertion for integer
     */
    public NumberAssert<?, Integer> asLength() {
        int length = actual.length();
        return new NumberAssert<>(this, length);
    }

}
