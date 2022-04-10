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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.IterationAssertable;
import io.github.imsejin.common.assertion.lang.ArrayAssert;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.util.ArrayUtils;
import io.github.imsejin.common.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

public class CollectionAssert<
        SELF extends CollectionAssert<SELF, ACTUAL, ELEMENT>,
        ACTUAL extends Collection<? extends ELEMENT>,
        ELEMENT>
        extends ObjectAssert<SELF, ACTUAL>
        implements IterationAssertable<SELF, ACTUAL, ELEMENT> {

    public CollectionAssert(ACTUAL target) {
        super(target);
    }

    @Override
    public SELF isEmpty() {
        if (!actual.isEmpty()) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_IS_EMPTY, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasElement() {
        if (actual.isEmpty()) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_HAS_ELEMENT, actual);
            throw getException();
        }

        return self;
    }

    public SELF hasSizeOf(int expected) {
        if (actual.size() != expected) {
            setDefaultDescription("It is expected to be the same size, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.size());
            throw getException();
        }

        return self;
    }

    public SELF isSameSize(Collection<?> expected) {
        if (expected == null || actual.size() != expected.size()) {
            setDefaultDescription("They are expected to be the same size, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    public SELF isNotSameSize(Collection<?> expected) {
        if (expected == null || actual.size() == expected.size()) {
            setDefaultDescription("They are expected to be not the same size, but they are. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    @Override
    public SELF contains(ELEMENT expected) {
        for (ELEMENT element : actual) {
            if (Objects.deepEquals(element, expected)) return self;
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS, expected, actual);
        throw getException();
    }

    @Override
    public SELF doesNotContain(ELEMENT expected) {
        for (ELEMENT element : actual) {
            if (!Objects.deepEquals(element, expected)) continue;

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN, expected, actual, element);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF containsNull() {
        for (ELEMENT element : actual) {
            if (element == null) return self;
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_NULL, actual);
        throw getException();
    }

    @Override
    public SELF doesNotContainNull() {
        for (ELEMENT element : actual) {
            if (element != null) continue;

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL, actual);
            throw getException();
        }

        return self;
    }

    @Override
    @SafeVarargs
    public final SELF containsAny(ELEMENT... expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) return self;

        for (Object item : expected) {
            for (Object element : actual) {
                if (Objects.deepEquals(element, item)) return self;
            }
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ANY, expected, actual);
        throw getException();
    }

    @Override
    public SELF containsAll(ACTUAL expected) {
        if (CollectionUtils.isNullOrEmpty(expected)) return self;

        outer:
        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (Objects.deepEquals(element, item)) continue outer;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ALL, expected, actual, item);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotContainAll(ACTUAL expected) {
        if (actual.isEmpty() || CollectionUtils.isNullOrEmpty(expected)) return self;

        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (!Objects.deepEquals(element, item)) continue;

                setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_ALL, expected, actual);
                throw getException();
            }
        }

        return self;
    }

    /**
     * @see #containsAll(Collection)
     */
    @Override
    @SafeVarargs
    public final SELF containsOnly(ELEMENT... expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) return self;

        // Checks if the actual collection contains all the given elements.
        outer:
        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (Objects.deepEquals(element, item)) continue outer;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_MISSING, expected, actual, item);
            throw getException();
        }

        // Checks if the actual collection doesn't contain elements that are not given.
        outer:
        for (ELEMENT element : actual) {
            for (ELEMENT item : expected) {
                if (Objects.deepEquals(element, item)) continue outer;
            }

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_UNEXPECTED, expected, actual, element);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF containsOnlyNulls() {
        if (actual.isEmpty()) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS, actual);
            throw getException();
        }

        for (ELEMENT element : actual) {
            if (element == null) continue;

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotHaveDuplicates() {
        if (actual.isEmpty() || actual.size() == 1) return self;

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

    @Override
    public SELF anyMatch(Predicate<ELEMENT> expected) {
        for (ELEMENT element : actual) {
            if (expected.test(element)) return self;
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_ANY_MATCH, actual);
        throw getException();
    }

    @Override
    public SELF allMatch(Predicate<ELEMENT> expected) {
        if (actual.isEmpty()) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH, actual, "");
            throw getException();
        }

        for (ELEMENT element : actual) {
            if (expected.test(element)) continue;

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_ALL_MATCH, actual, element);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF noneMatch(Predicate<ELEMENT> expected) {
        for (ELEMENT element : actual) {
            if (!expected.test(element)) continue;

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_NONE_MATCH, actual, element);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public ArrayAssert<?, ELEMENT> asArray() {
        ArrayAssert<?, ELEMENT> assertion = Asserts.that((ELEMENT[]) actual.toArray());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public NumberAssert<?, Integer> asSize() {
        NumberAssert<?, Integer> assertion = Asserts.that(actual.size());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
