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
import io.github.imsejin.common.assertion.composition.AmountComparisonAssertable;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.io.FileAssert;
import io.github.imsejin.common.assertion.lang.IntegerAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.util.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;

public class PathAssert<
        SELF extends PathAssert<SELF, ACTUAL>,
        ACTUAL extends Path>
        extends ObjectAssert<SELF, ACTUAL>
        implements SizeAssertable<SELF, ACTUAL>,
        AmountComparisonAssertable<SELF, Long> {

    public PathAssert(ACTUAL actual) {
        super(actual);
    }

    protected PathAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    @Override
    public SELF isEmpty() {
        long size = getSize(actual);

        if (size > 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEmpty() {
        long size = getSize(actual);

        if (size == 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasSize(long expected) {
        long size = getSize(actual);

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

    @Override
    public SELF doesNotHaveSize(long expected) {
        long size = getSize(actual);

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

    @Override
    public SELF hasSameSizeAs(ACTUAL expected) {
        long actualSize = getSize(actual);
        Long expectedSize = expected == null ? null : getSize(expected);

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

    @Override
    public SELF doesNotHaveSameSizeAs(ACTUAL expected) {
        long actualSize = getSize(actual);
        Long expectedSize = expected == null ? null : getSize(expected);

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

    @Override
    public SELF isGreaterThan(Long expected) {
        long size = getSize(actual);

        if (!AmountComparisonAssertable.IS_GREATER_THAN.test(size, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(Long expected) {
        long size = getSize(actual);

        if (!AmountComparisonAssertable.IS_GREATER_THAN_OR_EQUAL_TO.test(size, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_GREATER_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThan(Long expected) {
        long size = getSize(actual);

        if (!AmountComparisonAssertable.IS_LESS_THAN.test(size, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    @Override
    public SELF isLessThanOrEqualTo(Long expected) {
        long size = getSize(actual);

        if (!AmountComparisonAssertable.IS_LESS_THAN_OR_EQUAL_TO.test(size, expected)) {
            setDefaultDescription(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_LESS_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", size),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF exists() {
        if (!Files.exists(actual)) {
            setDefaultDescription("It is expected to exist, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isRegularFile() {
        if (!Files.isRegularFile(actual)) {
            setDefaultDescription("It is expected to be regular file, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isNotRegularFile() {
        if (Files.isRegularFile(actual)) {
            setDefaultDescription("It is expected not to be regular file, but it is.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isDirectory() {
        if (!Files.isDirectory(actual)) {
            setDefaultDescription("It is expected to be directory, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isNotDirectory() {
        if (Files.isDirectory(actual)) {
            setDefaultDescription("It is expected not to be directory, but it is.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isAbsolute() {
        if (!actual.isAbsolute()) {
            setDefaultDescription("It is expected to be absolute, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isRelative() {
        if (actual.isAbsolute()) {
            setDefaultDescription("It is expected to be relative, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isHidden() {
        boolean hidden;

        try {
            hidden = Files.isHidden(actual);
        } catch (IOException e) {
            setDefaultDescription("Failed to resolve whether path is hidden.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        if (!hidden) {
            setDefaultDescription("It is expected to be hidden, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isNotHidden() {
        boolean hidden;

        try {
            hidden = Files.isHidden(actual);
        } catch (IOException e) {
            setDefaultDescription("Failed to resolve whether path is hidden.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        if (hidden) {
            setDefaultDescription("It is expected not to be hidden, but it is.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF hasFileName(String expected) {
        String fileName = actual.getFileName().toString();

        if (!fileName.equals(expected)) {
            setDefaultDescription("It is expected to have the given file name, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.fileName", fileName));

            throw getException();
        }

        return self;
    }

    public SELF hasExtension(String expected) {
        String fileName = actual.getFileName().toString();
        String extension = FilenameUtils.getExtension(fileName);

        if (!extension.equals(expected)) {
            setDefaultDescription("It is expected to have the given file extension, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.extension", extension));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public PathAssert<?, Path> asParent() {
        Path parent = actual.getParent();
        return new PathAssert<>(this, parent);
    }

    public FileAssert<?, File> asFile() {
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

    public IntegerAssert<?> asNameCount() {
        class IntegerAssertImpl extends IntegerAssert<IntegerAssertImpl> {
            IntegerAssertImpl(Descriptor<?> descriptor, Integer actual) {
                super(descriptor, actual);
            }
        }

        int nameCount = actual.getNameCount();
        return new IntegerAssertImpl(this, nameCount);
    }

    // -------------------------------------------------------------------------------------------------

    private long getSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw getException();
        }
    }

}
