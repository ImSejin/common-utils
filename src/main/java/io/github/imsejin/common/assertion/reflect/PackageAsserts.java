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

package io.github.imsejin.common.assertion.reflect;

import io.github.imsejin.common.assertion.chars.StringAsserts;
import io.github.imsejin.common.assertion.object.ObjectAsserts;

@SuppressWarnings("unchecked")
public class PackageAsserts<SELF extends PackageAsserts<SELF>> extends ObjectAsserts<SELF> {

    private final Package actual;

    public PackageAsserts(Package actual) {
        super(actual);
        this.actual = actual;
    }

    public SELF isSuperPackageOf(Package expected) {
        if (this.actual.equals(expected) || !(expected.getName() + '.').startsWith(this.actual.getName())) {
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isSubPackageOf(Package expected) {
        if (this.actual.equals(expected) || !(this.actual.getName() + '.').startsWith(expected.getName())) {
            throw getException();
        }

        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public StringAsserts<?> asName() {
        return new StringAsserts<>(this.actual.getName());
    }

}
