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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.chars.StringAssert;
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

public class PackageAssert<SELF extends PackageAssert<SELF>> extends AbstractObjectAssert<SELF, Package> {

    public PackageAssert(Package actual) {
        super(actual);
    }

    public SELF isSuperPackageOf(Package expected) {
        if (actual.equals(expected) || !(expected.getName() + '.').startsWith(actual.getName())) {
            throw getException();
        }

        return self;
    }

    public SELF isSubPackageOf(Package expected) {
        if (actual.equals(expected) || !(actual.getName() + '.').startsWith(expected.getName())) {
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public StringAssert<?> asName() {
        return Asserts.that(actual.getName());
    }

}
