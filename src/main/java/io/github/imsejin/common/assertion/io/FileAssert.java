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

import io.github.imsejin.common.assertion.object.ObjectAssert;
import io.github.imsejin.common.util.FilenameUtils;

import java.io.File;

@SuppressWarnings("unchecked")
public class FileAssert<SELF extends FileAssert<SELF>> extends ObjectAssert<SELF> {

    private final File actual;

    public FileAssert(File actual) {
        super(actual);
        this.actual = actual;
    }

    public SELF exists() {
        if (!this.actual.exists()) throw getException();
        return (SELF) this;
    }

    public SELF isEmpty() {
        if (this.actual.length() > 0) throw getException();
        return (SELF) this;
    }

    public SELF isNotEmpty() {
        if (this.actual.length() == 0) throw getException();
        return (SELF) this;
    }

    public SELF isFile() {
        if (!this.actual.isFile()) throw getException();
        return (SELF) this;
    }

    public SELF isNotFile() {
        if (this.actual.isFile()) throw getException();
        return (SELF) this;
    }

    public SELF isDirectory() {
        if (!this.actual.isDirectory()) throw getException();
        return (SELF) this;
    }

    public SELF isNotDirectory() {
        if (this.actual.isDirectory()) throw getException();
        return (SELF) this;
    }

    public SELF isAbsolute() {
        if (!this.actual.isAbsolute()) throw getException();
        return (SELF) this;
    }

    public SELF isRelative() {
        if (this.actual.isAbsolute()) throw getException();
        return (SELF) this;
    }

    public SELF isHidden() {
        if (!this.actual.isHidden()) throw getException();
        return (SELF) this;
    }

    public SELF isNotHidden() {
        if (this.actual.isHidden()) throw getException();
        return (SELF) this;
    }

    public SELF canRead() {
        if (!this.actual.canRead()) throw getException();
        return (SELF) this;
    }

    public SELF canNotRead() {
        if (this.actual.canRead()) throw getException();
        return (SELF) this;
    }

    public SELF canWrite() {
        if (!this.actual.canWrite()) throw getException();
        return (SELF) this;
    }

    public SELF canNotWrite() {
        if (this.actual.canWrite()) throw getException();
        return (SELF) this;
    }

    public SELF canExecute() {
        if (!this.actual.canExecute()) throw getException();
        return (SELF) this;
    }

    public SELF canNotExecute() {
        if (this.actual.canExecute()) throw getException();
        return (SELF) this;
    }

    public SELF hasLengthOf(File expected) {
        if (this.actual.length() != expected.length()) throw getException();
        return (SELF) this;
    }

    public SELF hasLengthOf(int expected) {
        if (this.actual.length() != expected) throw getException();
        return (SELF) this;
    }

    public SELF isLargerThan(File expected) {
        if (this.actual.length() <= expected.length()) throw getException();
        return (SELF) this;
    }

    public SELF isLargerThan(int expected) {
        if (this.actual.length() <= expected) throw getException();
        return (SELF) this;
    }

    public SELF isSmallerThan(File expected) {
        if (this.actual.length() >= expected.length()) throw getException();
        return (SELF) this;
    }

    public SELF isSmallerThan(int expected) {
        if (this.actual.length() >= expected) throw getException();
        return (SELF) this;
    }

    public SELF hasName(String expected) {
        if (this.actual.getName().equals(expected)) throw getException();
        return (SELF) this;
    }

    public SELF hasExtension(String expected) {
        if (FilenameUtils.extension(this.actual).equals(expected)) throw getException();
        return (SELF) this;
    }

}
