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

package io.github.imsejin.common.assertion.nio.file;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.composition.SizeComparisonAssertable;
import io.github.imsejin.common.assertion.io.FileAssert;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.util.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathAssert<
        SELF extends PathAssert<SELF, ACTUAL>,
        ACTUAL extends Path>
        extends ObjectAssert<SELF, ACTUAL>
        implements SizeAssertable<SELF, ACTUAL>,
        SizeComparisonAssertable<SELF, Long> {

    public PathAssert(ACTUAL actual) {
        super(actual);
    }

    protected PathAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF isEmpty() {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (size > 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEmpty() {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (size == 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasSize(long expected) {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (size != expected) {
            setDefaultDescription("It is expected to have the given length, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, size);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveSize(long expected) {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (size == expected) {
            setDefaultDescription("It is expected not to have the given length, but it is. (expected: '{0}', actual: '{1}')",
                    expected, size);

            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasSameSizeAs(ACTUAL expected) {
        long actualSize;
        Long expectedSize;

        try {
            actualSize = Files.size(actual);
            expectedSize = expected == null ? null : Files.size(expected);
        } catch (IOException e) {
            throw getException();
        }

        if (expectedSize == null || actualSize != expectedSize) {
            setDefaultDescription("They are expected to have the same length, but they aren't. (expected: '{0}', actual: '{1}')",
                    expectedSize, actualSize);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveSameSizeAs(ACTUAL expected) {
        long actualSize;
        Long expectedSize;

        try {
            actualSize = Files.size(actual);
            expectedSize = expected == null ? null : Files.size(expected);
        } catch (IOException e) {
            throw getException();
        }

        if (expectedSize == null || actualSize == expectedSize) {
            setDefaultDescription("They are expected not to have the same length, but they are. (expected: '{0}', actual: '{1}')",
                    expectedSize, actualSize);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThan(Long expected) {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (!SizeComparisonAssertable.IS_GREATER_THAN.test(size, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN, expected, size);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(Long expected) {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (!SizeComparisonAssertable.IS_GREATER_THAN_OR_EQUAL_TO.test(size, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO, expected, size);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThan(Long expected) {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (!SizeComparisonAssertable.IS_LESS_THAN.test(size, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN, expected, size);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThanOrEqualTo(Long expected) {
        long size;

        try {
            size = Files.size(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (!SizeComparisonAssertable.IS_LESS_THAN_OR_EQUAL_TO.test(size, expected)) {
            setDefaultDescription(SizeComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO, expected, size);
            throw getException();
        }

        return self;
    }

    public SELF exists() {
        if (!Files.exists(actual)) {
            throw getException();
        }

        return self;
    }

    public SELF isFile() {
        if (!Files.isRegularFile(actual)) {
            throw getException();
        }

        return self;
    }

    public SELF isNotFile() {
        if (Files.isRegularFile(actual)) {
            throw getException();
        }

        return self;
    }

    public SELF isDirectory() {
        if (!Files.isDirectory(actual)) {
            throw getException();
        }

        return self;
    }

    public SELF isNotDirectory() {
        if (Files.isDirectory(actual)) {
            throw getException();
        }

        return self;
    }

    public SELF isAbsolute() {
        if (!actual.isAbsolute()) {
            throw getException();
        }

        return self;
    }

    public SELF isRelative() {
        if (actual.isAbsolute()) {
            throw getException();
        }

        return self;
    }

    public SELF isHidden() {
        boolean hidden;

        try {
            hidden = Files.isHidden(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (!hidden) {
            throw getException();
        }

        return self;
    }

    public SELF isNotHidden() {
        boolean hidden;

        try {
            hidden = Files.isHidden(actual);
        } catch (IOException e) {
            throw getException();
        }

        if (hidden) {
            throw getException();
        }

        return self;
    }

    public SELF hasFileName(String expected) {
        String fileName = actual.getFileName().toString();

        if (!fileName.equals(expected)) {
            throw getException();
        }

        return self;
    }

    public SELF hasExtension(String expected) {
        String fileName = actual.getFileName().toString();
        String extension = FilenameUtils.getExtension(fileName);

        if (!extension.equals(expected)) {
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public PathAssert<?, ?> asParent() {
        Path parent = actual.getParent();
        return new PathAssert<>(this, parent);
    }

    public FileAssert<?, ?> asFile() {
        class FileAssertImpl extends FileAssert<FileAssertImpl, File> {
            FileAssertImpl(Descriptor<?> descriptor, File actual) {
                super(descriptor, actual);
            }
        }

        File file = actual.toFile();
        return new FileAssertImpl(this, file);
    }

    public StringAssert<?> asFileName() {
        class StringAssertImpl extends StringAssert<StringAssertImpl> {
            StringAssertImpl(Descriptor<?> descriptor, String actual) {
                super(descriptor, actual);
            }
        }

        String fileName = actual.getFileName().toString();
        return new StringAssertImpl(this, fileName);
    }

    public NumberAssert<?, Integer> asNameCount() {
        class NumberAssertImpl extends NumberAssert<NumberAssertImpl, Integer> {
            NumberAssertImpl(Descriptor<?> descriptor, Integer actual) {
                super(descriptor, actual);
            }
        }

        int nameCount = actual.getNameCount();
        return new NumberAssertImpl(this, nameCount);
    }

}
