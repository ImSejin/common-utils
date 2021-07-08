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

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;
import io.github.imsejin.common.util.FilenameUtils;

import java.io.File;

public abstract class AbstractFileAssert<
        SELF extends AbstractFileAssert<SELF, ACTUAL>,
        ACTUAL extends File>
        extends AbstractObjectAssert<SELF, ACTUAL> {

    protected AbstractFileAssert(ACTUAL actual) {
        super(actual);
    }

    public SELF exists() {
        if (!actual.exists()) throw getException();
        return self;
    }

    public SELF isEmpty() {
        if (actual.length() > 0) throw getException();
        return self;
    }

    public SELF isNotEmpty() {
        if (actual.length() == 0) throw getException();
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

    public SELF canRead() {
        if (!actual.canRead()) throw getException();
        return self;
    }

    public SELF canNotRead() {
        if (actual.canRead()) throw getException();
        return self;
    }

    public SELF canWrite() {
        if (!actual.canWrite()) throw getException();
        return self;
    }

    public SELF canNotWrite() {
        if (actual.canWrite()) throw getException();
        return self;
    }

    public SELF canExecute() {
        if (!actual.canExecute()) throw getException();
        return self;
    }

    public SELF canNotExecute() {
        if (actual.canExecute()) throw getException();
        return self;
    }

    public SELF hasLengthOf(ACTUAL expected) {
        return hasLengthOf(expected.length());
    }

    public SELF hasLengthOf(long expected) {
        if (actual.length() != expected) {
            as("It is expected to be the same length, but it isn't. (expected: '{0}', actual: '{1}')", expected, actual.length());
            throw getException();
        }

        return self;
    }

    public SELF isLargerThan(ACTUAL expected) {
        return isLargerThan(expected.length());
    }

    public SELF isLargerThan(long expected) {
        if (actual.length() <= expected) throw getException();
        return self;
    }

    public SELF isSmallerThan(ACTUAL expected) {
        return isSmallerThan(expected.length());
    }

    public SELF isSmallerThan(long expected) {
        if (actual.length() >= expected) throw getException();
        return self;
    }

    public SELF hasName(String expected) {
        if (actual.getName().equals(expected)) throw getException();
        return self;
    }

    public SELF hasExtension(String expected) {
        if (FilenameUtils.extension(actual).equals(expected)) throw getException();
        return self;
    }

}
