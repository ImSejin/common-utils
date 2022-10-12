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
        SizeComparisonAssertable<SELF, ACTUAL> {

    public FileAssert(ACTUAL actual) {
        super(actual);
    }

    protected FileAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    public SELF exists() {
        if (!actual.exists()) {
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isEmpty() {
        if (actual.length() > 0) {
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEmpty() {
        if (actual.length() == 0) {
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasSize(Long expected) {
        if (actual.length() != expected) {
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveSize(Long expected) {
        if (actual.length() == expected) {
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasSameSizeAs(ACTUAL expected) {
        if (expected == null || actual.length() != expected.length()) {
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveSameSizeAs(ACTUAL expected) {
        if (expected == null || actual.length() == expected.length()) {
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

    @Deprecated
    public SELF canRead() {
        if (!actual.canRead()) throw getException();
        return self;
    }

    @Deprecated
    public SELF canNotRead() {
        if (actual.canRead()) throw getException();
        return self;
    }

    @Deprecated
    public SELF canWrite() {
        if (!actual.canWrite()) throw getException();
        return self;
    }

    @Deprecated
    public SELF canNotWrite() {
        if (actual.canWrite()) throw getException();
        return self;
    }

    @Deprecated
    public SELF canExecute() {
        if (!actual.canExecute()) throw getException();
        return self;
    }

    @Deprecated
    public SELF canNotExecute() {
        if (actual.canExecute()) throw getException();
        return self;
    }

    public SELF hasLengthOf(ACTUAL expected) {
        return hasLengthOf(expected.length());
    }

    public SELF hasLengthOf(long expected) {
        if (actual.length() != expected) {
            setDefaultDescription("It is expected to be the same length, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.length());
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThan(ACTUAL expected) {
        return isLargerThan(expected.length());
    }

    @Override
    public SELF isGreaterThanOrEqualTo(ACTUAL expected) {
        return self;
    }

    @Override
    public SELF isLessThan(ACTUAL expected) {
        return isSmallerThan(expected.length());
    }

    @Override
    public SELF isLessThanOrEqualTo(ACTUAL expected) {
        return self;
    }

    @Deprecated
    public SELF isLargerThan(ACTUAL expected) {
        return isLargerThan(expected.length());
    }

    @Deprecated
    public SELF isLargerThan(long expected) {
        if (actual.length() <= expected) {
            throw getException();
        }

        return self;
    }

    @Deprecated
    public SELF isSmallerThan(ACTUAL expected) {
        return isSmallerThan(expected.length());
    }

    @Deprecated
    public SELF isSmallerThan(long expected) {
        if (actual.length() >= expected) {
            throw getException();
        }

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
