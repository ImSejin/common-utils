/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Descriptor;

import java.util.AbstractMap.SimpleEntry;

/**
 * Assertion for {@link Package}
 *
 * @param <SELF> this class
 */
public class PackageAssert<SELF extends PackageAssert<SELF>> extends ObjectAssert<SELF, Package> {

    public PackageAssert(Package actual) {
        super(actual);
    }

    protected PackageAssert(Descriptor<?> descriptor, Package actual) {
        super(descriptor, actual);
    }

    /**
     * Asserts that actual value is super package of expected package.
     *
     * <pre>{@code
     *     Package pack = Package.getPackage("java.util.concurrent");
     *
     *     // Assertion will pass.
     *     Asserts.that(pack)
     *             .isSuperPackageOf(Package.getPackage("java.util.concurrent.atomic"));
     *     Asserts.that(pack)
     *             .isSuperPackageOf(Package.getPackage("java.util.concurrent.locks"));
     *
     *     // Assertion will fail.
     *     Asserts.that(pack)
     *             .isSuperPackageOf(null);
     *     Asserts.that(pack)
     *             .isSuperPackageOf(Package.getPackage("java.util"));
     *     Asserts.that(pack)
     *             .isSuperPackageOf(Package.getPackage("java.util.concurrent"));
     * }</pre>
     *
     * @param expected expected package
     * @return this class
     */
    public SELF isSuperPackageOf(Package expected) {
        if (expected == null || actual.equals(expected)) {
            setDefaultDescription("It is expected to be super-package of the given one, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        String expectedName = expected.getName();
        String packageName = expectedName.substring(0, expectedName.lastIndexOf('.'));
        if (!actual.getName().equals(packageName)) {
            setDefaultDescription("It is expected to be super-package of the given one, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is sub-package of expected package.
     *
     * <pre>{@code
     *     Package pack = Package.getPackage("java.util.concurrent");
     *
     *     // Assertion will pass.
     *     Asserts.that(pack)
     *             .isSubPackageOf(Package.getPackage("java.util"));
     *
     *     // Assertion will fail.
     *     Asserts.that(pack)
     *             .isSubPackageOf(null);
     *     Asserts.that(pack)
     *             .isSubPackageOf(Package.getPackage("java.util.concurrent.atomic"));
     *     Asserts.that(pack)
     *             .isSubPackageOf(Package.getPackage("java.util.concurrent"));
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    public SELF isSubPackageOf(Package expected) {
        if (expected == null || actual.equals(expected)) {
            setDefaultDescription("It is expected to be sub-package of the given one, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        String actualName = actual.getName();
        String packageName = actualName.substring(0, actualName.lastIndexOf('.'));
        if (!expected.getName().equals(packageName)) {
            setDefaultDescription("It is expected to be sub-package of the given one, but it isn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into its name.
     *
     * <pre>{@code
     *     Package pack = Package.getPackage("java.util.concurrent");
     *
     *     Asserts.that(pack)
     *             .isSubPackageOf(Package.getPackage("java.util"))
     *             .asName()
     *             .isEqualTo("java.util.concurrent");
     * }</pre>
     *
     * @return assertion for string
     */
    public StringAssert<?> asName() {
        String name = actual.getName();
        return new StringAssert<>(this, name);
    }

}
