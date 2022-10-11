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
import io.github.imsejin.common.assertion.composition.EnumerationAssertable;
import io.github.imsejin.common.assertion.composition.IterationAssertable;
import io.github.imsejin.common.assertion.composition.RandomAccessIterationAssertable;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.util.ListAssert;
import io.github.imsejin.common.util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * Assertion for {@link Array}
 *
 * @param <SELF>    this class
 * @param <ELEMENT> type of element in array
 */
public class ArrayAssert<
        SELF extends ArrayAssert<SELF, ELEMENT>,
        ELEMENT>
        extends ObjectAssert<SELF, ELEMENT[]>
        implements IterationAssertable<SELF, ELEMENT[], ELEMENT>,
        RandomAccessIterationAssertable<SELF, ELEMENT[], ELEMENT> {

    public ArrayAssert(ELEMENT[] actual) {
        super(actual);
    }

    protected ArrayAssert(Descriptor<?> descriptor, ELEMENT[] actual) {
        super(descriptor, actual);
    }

    /**
     * {@inheritDoc}
     *
     * @return this class
     */
    @Override
    public SELF isEmpty() {
        if (actual.length > 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY, (Object) actual);
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
        if (actual.length == 0) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY, (Object) actual);
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
    public SELF hasSize(Integer expected) {
        if (actual.length != expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE, expected, actual.length);
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
    public SELF doesNotHaveSize(Integer expected) {
        if (actual.length == expected) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE, expected, actual.length);
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
    public SELF hasSameSizeAs(Object[] expected) {
        if (expected == null || actual.length != expected.length) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS,
                    expected == null ? "null" : expected.length, actual.length);
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
    public SELF doesNotHaveSameSizeAs(Object[] expected) {
        if (expected == null || actual.length == expected.length) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS,
                    expected == null ? "null" : expected.length, actual.length);
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
    public SELF contains(ELEMENT expected) {
        for (ELEMENT element : actual) {
            if (Objects.deepEquals(element, expected)) {
                return self;
            }
        }

        setDefaultDescription(EnumerationAssertable.DEFAULT_DESCRIPTION_CONTAINS, expected, actual);
        throw getException();
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF doesNotContain(ELEMENT expected) {
        for (ELEMENT element : actual) {
            if (!Objects.deepEquals(element, expected)) {
                continue;
            }

            setDefaultDescription(EnumerationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN, expected, actual, element);
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
    public SELF containsNull() {
        for (ELEMENT element : actual) {
            if (element == null) {
                return self;
            }
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_NULL, (Object) actual);
        throw getException();
    }

    /**
     * {@inheritDoc}
     *
     * @return this class
     */
    @Override
    public SELF doesNotContainNull() {
        for (ELEMENT element : actual) {
            if (element != null) {
                continue;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL, (Object) actual);
            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * <p> This is faster about 10% than the below code.
     *
     * <pre>{@code
     *     if (expected.length == 0) return self;
     *
     *     if (!IntStream.range(0, Math.min(actual.length, expected.length))
     *             .anyMatch(i -> Objects.deepEquals(actual[i], expected[i]))) {
     *         throw getException();
     *     }
     *
     *     return self;
     * }</pre>
     *
     * @param expected expected values
     * @return self
     */
    @Override
    @SafeVarargs
    public final SELF containsAny(ELEMENT... expected) {
        if (actual.length == 0 && expected.length == 0) {
            return self;
        }

        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (Objects.deepEquals(element, item)) {
                    return self;
                }
            }
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ANY, expected, actual);
        throw getException();
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     */
    @Override
    public SELF containsAll(ELEMENT[] expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) {
            return self;
        }

        outer:
        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (Objects.deepEquals(element, item)) {
                    continue outer;
                }
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ALL, expected, actual, item);
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
    public SELF doesNotContainAll(ELEMENT[] expected) {
        if (actual.length == 0 || ArrayUtils.isNullOrEmpty(expected)) {
            return self;
        }

        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (!Objects.deepEquals(element, item)) {
                    continue;
                }

                setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_ALL, expected, actual);
                throw getException();
            }
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected value
     * @return this class
     * @see #containsAll(Object[])
     */
    @Override
    @SafeVarargs
    public final SELF containsOnly(ELEMENT... expected) {
        if (actual.length == 0 && expected.length == 0) {
            return self;
        }

        // Checks if the actual array contains all the given elements.
        outer:
        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (Objects.deepEquals(element, item)) {
                    continue outer;
                }
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_MISSING, expected, actual, item);
            throw getException();
        }

        // Checks if the actual array doesn't contain elements that are not given.
        outer:
        for (ELEMENT element : actual) {
            for (ELEMENT item : expected) {
                if (Objects.deepEquals(element, item)) {
                    continue outer;
                }
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_UNEXPECTED, expected, actual, element);
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
    public SELF containsOnlyNulls() {
        if (actual.length == 0) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS, (Object) actual);
            throw getException();
        }

        for (ELEMENT element : actual) {
            if (element == null) {
                continue;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS, (Object) actual);
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
    public SELF doesNotHaveDuplicates() {
        if (actual.length == 0 || actual.length == 1) {
            return self;
        }

        Set<ELEMENT> noDuplicates = new TreeSet<>((o1, o2) -> {
            if (Objects.deepEquals(o1, o2)) return 0;
            return ArrayUtils.hashCode(o1) < ArrayUtils.hashCode(o2) ? -1 : 1;
        });

        for (ELEMENT element : actual) {
            if (noDuplicates.contains(element)) {
                setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_DUPLICATES, actual, element);
                throw getException();
            }

            noDuplicates.add(element);
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected condition
     * @return this class
     */
    @Override
    public SELF anyMatch(Predicate<ELEMENT> expected) {
        for (ELEMENT element : actual) {
            if (expected.test(element)) {
                return self;
            }
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_ANY_MATCH, (Object) actual);
        throw getException();
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected condition
     * @return this class
     */
    @Override
    public SELF allMatch(Predicate<ELEMENT> expected) {
        if (actual.length == 0) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH, actual, "");
            throw getException();
        }

        for (ELEMENT element : actual) {
            if (expected.test(element)) {
                continue;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH, actual, element);
            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected condition
     * @return this class
     */
    @Override
    public SELF noneMatch(Predicate<ELEMENT> expected) {
        for (ELEMENT element : actual) {
            if (!expected.test(element)) {
                continue;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_NONE_MATCH, actual, element);
            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected values
     * @return this class
     */
    @Override
    @SafeVarargs
    public final SELF startsWith(ELEMENT... expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) {
            return self;
        }

        if (actual.length < expected.length) {
            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH, expected, actual);
            throw getException();
        }

        for (int i = 0; i < expected.length; i++) {
            ELEMENT element = actual[i];
            ELEMENT item = expected[i];
            if (Objects.deepEquals(element, item)) {
                continue;
            }

            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH, expected, actual);
            throw getException();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected values
     * @return this class
     */
    @Override
    @SafeVarargs
    public final SELF endsWith(ELEMENT... expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) {
            return self;
        }

        if (actual.length < expected.length) {
            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH, expected, actual);
            throw getException();
        }

        for (int i = 0; i < expected.length; i++) {
            ELEMENT element = actual[actual.length - expected.length + i];
            ELEMENT item = expected[i];
            if (Objects.deepEquals(element, item)) {
                continue;
            }

            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH, expected, actual);
            throw getException();
        }

        return null;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into its length.
     *
     * <pre>{@code
     *     Asserts.that([1, 2, 3, 4])
     *             .hasSize(4)
     *             .asLength()
     *             .isPositive();
     * }</pre>
     *
     * @return assertion for integer
     */
    public NumberAssert<?, Integer> asLength() {
        return new NumberAssert<>(this, actual.length);
    }

    /**
     * Converts actual value into list.
     *
     * <pre>{@code
     *     Asserts.that([1, 2, 3, 4])
     *             .hasSize(4)
     *             .asList()
     *             .isInstanceOf(Collection.class);
     * }</pre>
     *
     * @return assertion for list
     */
    public ListAssert<?, List<ELEMENT>, ELEMENT> asList() {
        class ListAssertImpl extends ListAssert<ListAssertImpl, List<ELEMENT>, ELEMENT> {
            ListAssertImpl(Descriptor<?> descriptor, List<ELEMENT> actual) {
                super(descriptor, actual);
            }
        }

        List<ELEMENT> list = Arrays.asList(actual);
        return new ListAssertImpl(this, list);
    }

}
