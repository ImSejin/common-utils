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

import io.github.imsejin.common.assertion.ObjectAsserts;
import io.github.imsejin.common.util.FilenameUtils;

import java.io.File;

@SuppressWarnings("unchecked")
public class FileAsserts<SELF extends FileAsserts<SELF>> extends ObjectAsserts<SELF> {

    private final File acutal;

    public FileAsserts(File acutal) {
        super(acutal);
        this.acutal = acutal;
    }

    public SELF exists() {
        if (!this.acutal.exists()) throw getException();
        return (SELF) this;
    }

    public SELF isEmpty() {
        if (this.acutal.length() > 0) throw getException();
        return (SELF) this;
    }

    public SELF isNotEmpty() {
        if (this.acutal.length() == 0) throw getException();
        return (SELF) this;
    }

    public SELF isFile() {
        if (!this.acutal.isFile()) throw getException();
        return (SELF) this;
    }

    public SELF isNotFile() {
        if (this.acutal.isFile()) throw getException();
        return (SELF) this;
    }

    public SELF isDirectory() {
        if (!this.acutal.isDirectory()) throw getException();
        return (SELF) this;
    }

    public SELF isNotDirectory() {
        if (this.acutal.isDirectory()) throw getException();
        return (SELF) this;
    }

    public SELF isAbsolute() {
        if (!this.acutal.isAbsolute()) throw getException();
        return (SELF) this;
    }

    public SELF isNotAbsolute() {
        if (this.acutal.isAbsolute()) throw getException();
        return (SELF) this;
    }

    public SELF isHidden() {
        if (!this.acutal.isHidden()) throw getException();
        return (SELF) this;
    }

    public SELF isNotHidden() {
        if (this.acutal.isHidden()) throw getException();
        return (SELF) this;
    }

    public SELF canRead() {
        if (!this.acutal.canRead()) throw getException();
        return (SELF) this;
    }

    public SELF canNotRead() {
        if (this.acutal.canRead()) throw getException();
        return (SELF) this;
    }

    public SELF canWrite() {
        if (!this.acutal.canWrite()) throw getException();
        return (SELF) this;
    }

    public SELF canNotWrite() {
        if (this.acutal.canWrite()) throw getException();
        return (SELF) this;
    }

    public SELF canExecute() {
        if (!this.acutal.canExecute()) throw getException();
        return (SELF) this;
    }

    public SELF canNotExecute() {
        if (this.acutal.canExecute()) throw getException();
        return (SELF) this;
    }

    public SELF hasLengthOf(File expected) {
        if (this.acutal.length() != expected.length()) throw getException();
        return (SELF) this;
    }

    public SELF hasLengthOf(int expected) {
        if (this.acutal.length() != expected) throw getException();
        return (SELF) this;
    }

    public SELF isLargerThan(File expected) {
        if (this.acutal.length() <= expected.length()) throw getException();
        return (SELF) this;
    }

    public SELF isLargerThan(int expected) {
        if (this.acutal.length() <= expected) throw getException();
        return (SELF) this;
    }

    public SELF isSmallerThan(File expected) {
        if (this.acutal.length() >= expected.length()) throw getException();
        return (SELF) this;
    }

    public SELF isSmallerThan(int expected) {
        if (this.acutal.length() >= expected) throw getException();
        return (SELF) this;
    }

    public SELF hasName(String expected) {
        if (this.acutal.getName().equals(expected)) throw getException();
        return (SELF) this;
    }

    public SELF hasExtension(String expected) {
        if (FilenameUtils.extension(this.acutal).equals(expected)) throw getException();
        return (SELF) this;
    }

}
