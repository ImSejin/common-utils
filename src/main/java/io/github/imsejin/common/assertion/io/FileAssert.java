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

import java.io.File;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.lang.LongAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.assertion.nio.file.PathAssert;
import io.github.imsejin.common.util.FilenameUtils;

/**
 * Assertion for {@link File}
 *
 * @param <SELF>   this class
 * @param <ACTUAL> type of file
 */
public class FileAssert<
        SELF extends FileAssert<SELF, ACTUAL>,
        ACTUAL extends File>
        extends ObjectAssert<SELF, ACTUAL>
        implements SizeAssertable<SELF, ACTUAL> {

    public FileAssert(ACTUAL actual) {
        super(actual);
    }

    protected FileAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    /**
     * {@inheritDoc}
     *
     * @return this class
     */
    @Override
    public SELF isEmpty() {
        long length = actual.length();

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
     * {@inheritDoc}
     *
     * @return this class
     */
    @Override
    public SELF isNotEmpty() {
        long length = actual.length();

        if (length == 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", length));

            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSize(long expected) {
        long size = actual.length();

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
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF doesNotHaveSize(long expected) {
        long size = actual.length();

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
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSameSizeAs(ACTUAL expected) {
        long actualSize = actual.length();
        Long expectedSize = expected == null ? null : expected.length();

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
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF doesNotHaveSameSizeAs(ACTUAL expected) {
        long actualSize = actual.length();
        Long expectedSize = expected == null ? null : expected.length();

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
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSizeGreaterThan(long expected) {
        if (actual.length() <= expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.length()),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSizeGreaterThanOrEqualTo(long expected) {
        if (actual.length() < expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.length()),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSizeLessThan(long expected) {
        if (actual.length() >= expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.length()),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF hasSizeLessThanOrEqualTo(long expected) {
        if (actual.length() > expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN_OR_EQUAL_TO);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.length()),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF exists() {
        if (!actual.exists()) {
            setDefaultDescription("It is expected to exist, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isFile() {
        if (!actual.isFile()) {
            setDefaultDescription("It is expected to be file, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isNotFile() {
        if (actual.isFile()) {
            setDefaultDescription("It is expected not to be file, but it is.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isDirectory() {
        if (!actual.isDirectory()) {
            setDefaultDescription("It is expected to be directory, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isNotDirectory() {
        if (actual.isDirectory()) {
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
        if (!actual.isHidden()) {
            setDefaultDescription("It is expected to be hidden, but it isn't.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF isNotHidden() {
        if (actual.isHidden()) {
            setDefaultDescription("It is expected not to be hidden, but it is.");
            setDescriptionVariables(new SimpleEntry<>("actual", actual));

            throw getException();
        }

        return self;
    }

    public SELF hasName(String expected) {
        String name = actual.getName();

        if (!name.equals(expected)) {
            setDefaultDescription("It is expected to have the given name, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.name", name),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF hasExtension(String expected) {
        String extension = FilenameUtils.getExtension(actual.getName());

        if (!extension.equals(expected)) {
            setDefaultDescription("It is expected to have the given extension, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.extension", extension),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public FileAssert<?, File> asParentFile() {
        File parentFile = actual.getParentFile();
        return new FileAssert<>(this, parentFile);
    }

    public PathAssert<?, Path> asPath() {
        class PathAssertImpl extends PathAssert<PathAssertImpl, Path> {
            PathAssertImpl(Descriptor<?> descriptor, Path actual) {
                super(descriptor, actual);
            }
        }

        Path path = actual.toPath();
        return new PathAssertImpl(this, path);
    }

    public LongAssert<?> asLength() {
        class LongAssertImpl extends LongAssert<LongAssertImpl> {
            LongAssertImpl(Descriptor<?> descriptor, Long actual) {
                super(descriptor, actual);
            }
        }

        long length = actual.length();
        return new LongAssertImpl(this, length);
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
