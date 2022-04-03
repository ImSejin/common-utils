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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.IterationAssertable;
import io.github.imsejin.common.util.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;

public class ArrayAssert<
        SELF extends ArrayAssert<SELF, ELEMENT>,
        ELEMENT>
        extends ObjectAssert<SELF, ELEMENT[]>
        implements IterationAssertable<SELF, ELEMENT[], ELEMENT> {

    public ArrayAssert(ELEMENT[] actual) {
        super(actual);
    }

    @Override
    public SELF isEqualTo(Object[] expected) {
        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    Arrays.deepToString(expected), Arrays.deepToString(actual));
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEqualTo(Object[] expected) {
        if (Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be not equal, but they are. (expected: '{0}', actual: '{1}')",
                    Arrays.deepToString(expected), Arrays.deepToString(actual));
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isEmpty() {
        if (actual.length > 0) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_IS_EMPTY, Arrays.deepToString(actual));
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasElement() {
        if (actual.length == 0) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_HAS_ELEMENT, Arrays.deepToString(actual));
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotContainNull() {
        for (Object element : actual) {
            if (element != null) continue;

            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL, Arrays.deepToString(actual));
            throw getException();
        }

        return self;
    }

    public SELF hasLengthOf(int expected) {
        if (actual.length != expected) {
            setDefaultDescription("It is expected to be the same length, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.length);
            throw getException();
        }

        return self;
    }

    public SELF isSameLength(Object[] expected) {
        if (expected == null || actual.length != expected.length) {
            setDefaultDescription("They are expected to be the same length, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.length, actual.length);
            throw getException();
        }

        return self;
    }

    public SELF isNotSameLength(Object[] expected) {
        if (expected == null || actual.length == expected.length) {
            setDefaultDescription("They are expected to be not the same length, but they are. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.length, actual.length);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF contains(ELEMENT expected) {
        for (Object element : actual) {
            if (Objects.deepEquals(element, expected)) return self;
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS,
                ArrayUtils.toString(expected), Arrays.deepToString(actual));
        throw getException();
    }

    /**
     * Verifies this contains the given elements at least 1.
     * <p>
     * This is faster about 10% than the below code.
     *
     * <pre><code>
     *     if (expected.length == 0) return self;
     *     if (actual.length == 0 &amp;&amp; expected.length == 0) return self;
     *
     *     if (!IntStream.range(0, Math.min(actual.length, expected.length))
     *             .anyMatch(i -&gt; Objects.deepEquals(actual[i], expected[i]))) {
     *         throw getException();
     *     }
     *
     *     return self;
     * </code></pre>
     *
     * @param expected expected values
     * @return self
     */
    @Override
    @SafeVarargs
    public final SELF containsAny(ELEMENT... expected) {
        if (expected.length == 0) return self;

        for (ELEMENT item : expected) {
            for (ELEMENT element : actual) {
                if (Objects.deepEquals(element, item)) return self;
            }
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ANY,
                Arrays.deepToString(expected), Arrays.deepToString(actual));
        throw getException();
    }

    @Override
    public SELF containsAll(ELEMENT[] expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) return self;

        for (ELEMENT element : expected) {
            contains(element);
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public NumberAssert<?, Integer> asLength() {
        NumberAssert<?, Integer> assertion = Asserts.that(actual.length);
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
