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
        implements EnumerationAssertable<SELF, ACTUAL, ELEMENT> {

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
        if (actual.length() > 0) {
            setDefaultDescription(EnumerationAssertable.DEFAULT_DESCRIPTION_IS_EMPTY, actual);
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
        if (actual.length() <= 0) {
            setDefaultDescription(EnumerationAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY, actual);
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
    public SELF hasSize(int expected) {
        if (actual.length() != expected) {
            setDefaultDescription("It is expected to have the given length, but it isn't. (expected: '{0}', actual: '{1}')", expected, actual.length());
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
    public SELF doesNotHaveSize(int expected) {
        if (actual.length() == expected) {
            setDefaultDescription("It is expected not to have the given length, but it is. (expected: '{0}', actual: '{1}')", expected, actual.length());
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
        if (expected == null || actual.length() != expected.length()) {
            setDefaultDescription("They are expected to have the same length, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.length(), actual.length());
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
        if (expected == null || actual.length() == expected.length()) {
            setDefaultDescription("They are expected not to have the same length, but they are. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.length(), actual.length());
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
            setDefaultDescription("It is expected to contain the given character(s), but it isn't. (expected: '{0}', actual: '{1}')", expected, actual);
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
            setDefaultDescription("It is expected not to contain the given character(s), but it is. (expected: '{0}', actual: '{1}')",
                    expected, actual);
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
