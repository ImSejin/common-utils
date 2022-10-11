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

package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.EnumerationAssertable;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapAssert<
        SELF extends MapAssert<SELF, ACTUAL, K, V>,
        ACTUAL extends Map<K, V>,
        K,
        V>
        extends ObjectAssert<SELF, ACTUAL> {

    public MapAssert(ACTUAL actual) {
        super(actual);
    }

    protected MapAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    public SELF isEmpty() {
        if (!actual.isEmpty()) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY, actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotEmpty() {
        if (actual.isEmpty()) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY, actual);
            throw getException();
        }

        return self;
    }

    public SELF hasSize(int expected) {
        if (actual.size() != expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE, expected, actual.size());
            throw getException();
        }

        return self;
    }

    public SELF hasSameSizeAs(Map<?, ?> expected) {
        if (expected == null || actual.size() != expected.size()) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS,
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    public SELF doesNotHaveSameSizeAs(Map<?, ?> expected) {
        if (expected == null || actual.size() == expected.size()) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS,
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    public SELF containsKey(K expected) {
        if (!actual.containsKey(expected)) {
            setDefaultDescription("It is expected to contain the given key, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.keySet());
            throw getException();
        }

        return self;
    }

    public SELF containsValue(V expected) {
        if (!actual.containsValue(expected)) {
            setDefaultDescription("It is expected to contain the given value, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.values());
            throw getException();
        }

        return self;
    }

    public SELF containsAllKeys(Map<? extends K, ?> expected) {
        return containsAllKeys(expected.keySet());
    }

    public SELF containsAllKeys(Collection<? extends K> expected) {
        Set<K> actualKeys = actual.keySet();

        if (!actualKeys.containsAll(expected)) {
            setDefaultDescription("It is expected to contain all the given keys, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actualKeys);
            throw getException();
        }

        return self;
    }

    public SELF containsAllValues(Map<?, ? extends V> expected) {
        return containsAllValues(expected.values());
    }

    public SELF containsAllValues(Collection<? extends V> expected) {
        Collection<V> actualValues = actual.values();

        if (!actualValues.containsAll(expected)) {
            setDefaultDescription("It is expected to contain all the given values, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actualValues);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into its keys.
     *
     * <pre>{@code
     *     Asserts.that(["alpha": 1, "beta": 2])
     *             .hasSize(2)
     *             .asKeySet()
     *             .isEqualTo(["alpha", "beta"]);
     * }</pre>
     *
     * @return assertion for collection
     */
    public CollectionAssert<?, Collection<K>, K> asKeySet() {
        return new CollectionAssert<>(this, actual.keySet());
    }

    /**
     * Converts actual value into its values.
     *
     * <pre>{@code
     *     Asserts.that(["alpha": 1, "beta": 2])
     *             .hasSize(2)
     *             .asValues()
     *             .isEqualTo([1, 2]);
     * }</pre>
     *
     * @return assertion for collection
     */
    public CollectionAssert<?, Collection<V>, V> asValues() {
        return new CollectionAssert<>(this, actual.values());
    }

    /**
     * Converts actual value into its size.
     *
     * <pre>{@code
     *     Asserts.that(["alpha": 1, "beta": 2])
     *             .hasSize(2)
     *             .asSize()
     *             .isGreaterThanOrEqualTo(2);
     * }</pre>
     *
     * @return assertion for integer
     */
    public NumberAssert<?, Integer> asSize() {
        class NumberAssertImpl extends NumberAssert<NumberAssertImpl, Integer> {
            NumberAssertImpl(Descriptor<?> descriptor, Integer actual) {
                super(descriptor, actual);
            }
        }

        int size = actual.size();
        return new NumberAssertImpl(this, size);
    }

}
