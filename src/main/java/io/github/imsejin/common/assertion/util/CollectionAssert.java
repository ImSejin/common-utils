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

package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.EnumerationAssertable;
import io.github.imsejin.common.assertion.composition.IterationAssertable;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.assertion.lang.ArrayAssert;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.util.ArrayUtils;
import io.github.imsejin.common.util.CollectionUtils;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * Assertion for {@link Collection}
 *
 * @param <SELF>    this class
 * @param <ACTUAL>  type of collection
 * @param <ELEMENT> type of element in collection
 */
public class CollectionAssert<
        SELF extends CollectionAssert<SELF, ACTUAL, ELEMENT>,
        ACTUAL extends Collection<? extends ELEMENT>,
        ELEMENT>
        extends ObjectAssert<SELF, ACTUAL>
        implements IterationAssertable<SELF, ACTUAL, ELEMENT> {

    public CollectionAssert(ACTUAL actual) {
        super(actual);
    }

    protected CollectionAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    /**
     * {@inheritDoc}
     *
     * @return this class
     */
    @Override
    public SELF isEmpty() {
        if (!actual.isEmpty()) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.size()));

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
        if (actual.isEmpty()) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_IS_NOT_EMPTY);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.size()));

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
        int size = actual.size();

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
        int size = actual.size();

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
    @SuppressWarnings("rawtypes") // For receiving parameter regardless of its generic type.
    public SELF hasSameSizeAs(Collection expected) {
        Integer expectedSize = expected == null ? null : expected.size();

        if (expected == null || actual.size() != expectedSize) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.size()),
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
    @SuppressWarnings("rawtypes") // For receiving parameter regardless of its generic type.
    public SELF doesNotHaveSameSizeAs(Collection expected) {
        Integer expectedSize = expected == null ? null : expected.size();

        if (expected == null || actual.size() == expectedSize) {
            setDefaultDescription(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.size()),
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
    public SELF contains(ELEMENT expected) {
        for (ELEMENT element : actual) {
            if (Objects.deepEquals(element, expected)) {
                return self;
            }
        }

        setDefaultDescription(EnumerationAssertable.DEFAULT_DESCRIPTION_CONTAINS);
        setDescriptionVariables(
                new SimpleEntry<>("actual", actual),
                new SimpleEntry<>("expected", expected));

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

            setDefaultDescription(EnumerationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected));

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

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_NULL, actual);
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

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL, actual);
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
    public final SELF containsAny(ELEMENT... expected) {
        if (actual.size() == 0 && expected.length == 0) {
            return self;
        }

        for (Object item : expected) {
            for (Object element : actual) {
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
    public SELF containsAll(ACTUAL expected) {
        if (CollectionUtils.isNullOrEmpty(expected)) {
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
    public SELF doesNotContainAll(ACTUAL expected) {
        if (actual.isEmpty() || CollectionUtils.isNullOrEmpty(expected)) {
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
     * @param expected expected values
     * @return this class
     * @see #containsAll(Collection)
     */
    @Override
    @SafeVarargs
    public final SELF containsOnly(ELEMENT... expected) {
        if (actual.size() == 0 && expected.length == 0) {
            return self;
        }

        // Checks if the actual collection contains all the given elements.
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

        // Checks if the actual collection doesn't contain elements that are not given.
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
        if (actual.isEmpty()) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS, actual);
            throw getException();
        }

        for (ELEMENT element : actual) {
            if (element == null) {
                continue;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS, actual);
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
        if (actual.isEmpty() || actual.size() == 1) {
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

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_ANY_MATCH, actual);
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
        if (actual.isEmpty()) {
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

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into array.
     *
     * <pre>{@code
     *     Asserts.that([1, 2, 3, 4])
     *             .allMatch(it -> it > 0)
     *             .asArray()
     *             .isInstanceOf(Object[].class);
     * }</pre>
     *
     * @return assertion for integer
     */
    @SuppressWarnings("unchecked")
    public ArrayAssert<?, ELEMENT> asArray() {
        // You can meet this exception by local anonymous class like this code
        // return new ArrayAssert(this, actual.toArray()) {};
        //
        // To prevent that, we use local class instead of anonymous class.
        // This is all parts of exception message.
        //
        //   java.lang.VerifyError: Bad type on operand stack
        // Exception Details:
        //   Location:
        //     io/github/imsejin/common/assertion/util/CollectionAssert$1.containsAll(Ljava/lang/Object;)Lio/github/imsejin/common/assertion/lang/ObjectAssert; @6: invokespecial
        //   Reason:
        //     Type 'java/lang/Object' (current frame, stack[1]) is not assignable to '[Ljava/lang/Object;'
        //   Current Frame:
        //     bci: @6
        //     flags: { }
        //     locals: { 'io/github/imsejin/common/assertion/util/CollectionAssert$1', 'java/lang/Object', '[Z' }
        //     stack: { 'io/github/imsejin/common/assertion/util/CollectionAssert$1', 'java/lang/Object' }
        class ArrayAssertImpl extends ArrayAssert<ArrayAssertImpl, ELEMENT> {
            ArrayAssertImpl(Descriptor<?> descriptor, ELEMENT[] actual) {
                super(descriptor, actual);
            }
        }

        ELEMENT[] elements = (ELEMENT[]) this.actual.toArray(new Object[0]);
        return new ArrayAssertImpl(this, elements);
    }

    /**
     * Converts actual value into its size.
     *
     * <pre>{@code
     *     Asserts.that([1, 2, 3, 4])
     *             .hasSize(4)
     *             .asSize()
     *             .isPositive();
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
