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

package io.github.imsejin.common.assertion.io;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.composition.SizeComparisonAssertable;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.util.FilenameUtils;

import java.io.File;

public class FileAssert<
        SELF extends FileAssert<SELF, ACTUAL>,
        ACTUAL extends File>
        extends ObjectAssert<SELF, ACTUAL>
        implements SizeAssertable<SELF, ACTUAL>,
        SizeComparisonAssertable<SELF, Long> {

    public FileAssert(ACTUAL actual) {
        super(actual);
    }

    protected FileAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF isEmpty() {
        if (actual.length() > 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEmpty() {
        if (actual.length() == 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasSize(long expected) {
        if (actual.length() != expected) {
            setDefaultDescription("It is expected to have the given length, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.length());
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveSize(long expected) {
        if (actual.length() == expected) {
            setDefaultDescription("It is expected not to have the given length, but it is. (expected: '{0}', actual: '{1}')",
                    expected, actual.length());

            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasSameSizeAs(ACTUAL expected) {
        if (expected == null || actual.length() != expected.length()) {
            setDefaultDescription("They are expected to have the same length, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual.length());
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveSameSizeAs(ACTUAL expected) {
        if (expected == null || actual.length() == expected.length()) {
            setDefaultDescription("They are expected not to have the same length, but they are. (expected: '{0}', actual: '{1}')",
                    expected, actual.length());
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThan(Long expected) {
        long length = actual.length();

        if (!SizeComparisonAssertable.IS_GREATER_THAN.test(length, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN, expected, length);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(Long expected) {
        long length = actual.length();

        if (!SizeComparisonAssertable.IS_GREATER_THAN_OR_EQUAL_TO.test(length, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO, expected, length);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThan(Long expected) {
        long length = actual.length();

        if (!SizeComparisonAssertable.IS_LESS_THAN.test(length, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN, expected, length);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThanOrEqualTo(Long expected) {
        long length = actual.length();

        if (!SizeComparisonAssertable.IS_LESS_THAN_OR_EQUAL_TO.test(length, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO, expected, length);
            throw getException();
        }

        return self;
    }

    public SELF exists() {
        if (!actual.exists()) {
            throw getException();
        }

        return self;
    }

    public SELF isFile() {
        if (!actual.isFile()) throw getException();
        return self;
    }

    public SELF isNotFile() {
        if (actual.isFile()) throw getException();
        return self;
    }

    public SELF isDirectory() {
        if (!actual.isDirectory()) throw getException();
        return self;
    }

    public SELF isNotDirectory() {
        if (actual.isDirectory()) throw getException();
        return self;
    }

    public SELF isAbsolute() {
        if (!actual.isAbsolute()) throw getException();
        return self;
    }

    public SELF isRelative() {
        if (actual.isAbsolute()) throw getException();
        return self;
    }

    public SELF isHidden() {
        if (!actual.isHidden()) throw getException();
        return self;
    }

    public SELF isNotHidden() {
        if (actual.isHidden()) throw getException();
        return self;
    }

    public SELF hasName(String expected) {
        if (!actual.getName().equals(expected)) {
            throw getException();
        }

        return self;
    }

    public SELF hasExtension(String expected) {
        String extension = FilenameUtils.getExtension(actual.getName());

        if (!extension.equals(expected)) {
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public NumberAssert<?, Long> asLength() {
        class NumberAssertImpl extends NumberAssert<NumberAssertImpl, Long> {
            NumberAssertImpl(Descriptor<?> descriptor, Long actual) {
                super(descriptor, actual);
            }
        }

        long length = actual.length();
        return new NumberAssertImpl(this, length);
    }

    public StringAssert<?> asName() {
        class StringAssertImpl extends StringAssert<StringAssertImpl> {
            StringAssertImpl(Descriptor<?> descriptor, String actual) {
                super(descriptor, actual);
            }
        }

        String name = actual.getName();
        return new StringAssertImpl(this, name);
    }

}
