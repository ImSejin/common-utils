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

package io.github.imsejin.common.assertion;

import io.github.imsejin.common.internal.assertion.FullyExtensibleAssert;
import io.github.imsejin.common.internal.assertion.FullyRestrictedAssert;
import io.github.imsejin.common.internal.assertion.PartiallyExtensibleAssert;
import io.github.imsejin.common.internal.assertion.PartiallyRestrictedAssert;
import io.github.imsejin.common.internal.assertion.model.Bar;
import io.github.imsejin.common.internal.assertion.model.Foo;
import io.github.imsejin.common.internal.assertion.model.KanCode;
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class CustomAssertsTest {

    @Nested
    @DisplayName("Fully extensible assertion class")
    class FullyExtensibleAssertionClass {
        @Test
        @DisplayName("uses that class")
        void test0() {
            // given
            Foo foo = new Foo();

            // expect
            assertThatNoException().isThrownBy(() -> MyAsserts.that(foo)
                    .isNotNull()
                    .isEqualTo(foo)
                    .predicate(it -> it.getValue() == null)
                    .hasNullValue());
            assertThatIllegalArgumentException().isThrownBy(() -> MyAsserts.that(new Foo(""))
                    .isNotNull()
                    .predicate(it -> it.getValue().isEmpty())
                    .hasNullValue());
        }

        @Test
        @DisplayName("uses subclass of that")
        void test1() {
            // given
            class FullyExtensibleAssertImpl<
                    SELF extends FullyExtensibleAssertImpl<SELF, ACTUAL>,
                    ACTUAL extends Bar>
                    extends FullyExtensibleAssert<SELF, ACTUAL> {
                public FullyExtensibleAssertImpl(ACTUAL actual) {
                    super(actual);
                }

                @Override
                public SELF hasNullValue() {
                    if (!StringUtils.isNullOrEmpty(actual.getValue())) throw getException();
                    return self;
                }

                public SELF hasSingleValue() {
                    String value = actual.getValue();
                    if (value == null || value.length() != 1) throw getException();
                    return self;
                }
            }

            // expect
            assertThatNoException().isThrownBy(() -> new FullyExtensibleAssertImpl<>(new Bar(""))
                    .isNotNull()
                    .predicate(it -> it.getValue().isEmpty())
                    .hasNullValue());
            assertThatNoException().isThrownBy(() -> new FullyExtensibleAssertImpl<>(new Bar("a"))
                    .isNotNull()
                    .predicate(it -> it.getValue().length() == 1)
                    .hasSingleValue()
                    .predicate(it -> it.getCreatedTime().toEpochMilli() <= System.currentTimeMillis()));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("Partially extensible assertion class")
    class PartiallyExtensibleAssertionClass {
        @Test
        @DisplayName("uses that class")
        void test0() {
            // given
            Foo foo = new Foo();

            // expect
            assertThatNoException().isThrownBy(() -> new PartiallyExtensibleAssert<>(foo)
                    .isNotNull()
                    .isEqualTo(foo)
                    .predicate(it -> it.getValue() == null)
                    .hasNullValue());
            assertThatIllegalArgumentException().isThrownBy(() -> new PartiallyExtensibleAssert<>(new Foo("a"))
                    .isNotNull()
                    .predicate(it -> it.getValue().isEmpty())
                    .hasNullValue());
        }

        @Test
        @DisplayName("uses subclass of that")
        void test1() {
            // given
            class PartiallyExtensibleAssertImpl<
                    SELF extends PartiallyExtensibleAssertImpl<SELF>>
                    extends PartiallyExtensibleAssert<SELF> {
                public PartiallyExtensibleAssertImpl(Foo actual) {
                    super(actual);
                }

                @Override
                public SELF hasNullValue() {
                    if (!StringUtils.isNullOrEmpty(actual.getValue())) throw getException();
                    return self;
                }

                public SELF hasMultipleValue() {
                    String value = actual.getValue();
                    if (value == null || value.length() <= 1) throw getException();
                    return self;
                }
            }

            // expect
            assertThatNoException().isThrownBy(() -> new PartiallyExtensibleAssertImpl<>(new Foo(""))
                    .isNotNull()
                    .predicate(it -> it.getValue().isEmpty())
                    .hasNullValue());
            assertThatNoException().isThrownBy(() -> new PartiallyExtensibleAssertImpl<>(new Foo("alpha"))
                    .isNotNull()
                    .predicate(it -> it.getValue().length() > 1)
                    .hasMultipleValue()
                    .returns("alpha", Foo::getValue));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("Partially restricted assertion class")
    class PartiallyRestrictedAssertionClass {
        @Test
        @DisplayName("uses that class")
        void test0() {
            // given
            Foo foo = new Foo("a1");

            // expect
            assertThatNoException().isThrownBy(() -> new PartiallyRestrictedAssert<>(foo)
                    .isNotNull()
                    .isEqualTo(foo)
                    .predicate(it -> !it.getValue().isEmpty())
                    .hasNumericValue());
            assertThatIllegalArgumentException().isThrownBy(() -> new PartiallyRestrictedAssert<>(new Foo("a"))
                    .isNotNull()
                    .predicate(it -> !it.getValue().isEmpty())
                    .hasNumericValue());
        }

        @Test
        @DisplayName("uses subclass of that")
        void test1() {
            // given
            class PartiallyRestrictedAssertImpl<ACTUAL extends Bar> extends PartiallyRestrictedAssert<ACTUAL> {
                public PartiallyRestrictedAssertImpl(ACTUAL actual) {
                    super(actual);
                }

                @Override
                public PartiallyRestrictedAssertImpl<ACTUAL> isNotNull() {
                    super.isNotNull();
                    return this;
                }

                @Override
                public PartiallyRestrictedAssertImpl<ACTUAL> hasNumericValue() {
                    String value = actual.getValue();
                    if (value == null || !value.matches(".*[0-9].*")) {
                        throw getException();
                    }

                    return this;
                }

                public PartiallyRestrictedAssertImpl<ACTUAL> hasMultipleNumericValue() {
                    String value = actual.getValue();
                    if (value == null || !value.matches(".*[0-9].*[0-9].*")) {
                        throw getException();
                    }

                    return this;
                }
            }

            // expect
            assertThatNoException().isThrownBy(() -> new PartiallyRestrictedAssertImpl<>(new Bar("a12"))
                    .isNotNull()
                    .hasNumericValue()
                    .hasMultipleNumericValue()
                    .predicate(it -> !it.getValue().isEmpty()));
            assertThatIllegalArgumentException().isThrownBy(() -> new PartiallyRestrictedAssertImpl<>(new Bar("a1"))
                    .isNotNull()
                    .hasNumericValue()
                    .hasMultipleNumericValue()
                    .predicate(it -> !it.getValue().isEmpty()));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("Fully restricted assertion class")
    class FullyRestrictedAssertionClass {
        @Test
        @DisplayName("uses that class")
        void test0() {
            // given
            KanCode kanCode = new KanCode("03200100");

            // expect
            assertThatNoException().isThrownBy(() -> MyAsserts.that(kanCode)
                    .isNotNull()
                    .isEqualTo("03200100")
                    .isNotEqualTo("03200199")
                    .isParentOf(new KanCode("03200199"))
                    .isChildOf(new KanCode("03200000"))
                    .predicate(it -> it.getDepth() == 3));
            assertThatIllegalArgumentException().isThrownBy(() -> MyAsserts.that(kanCode)
                    .isNotNull()
                    .isEqualTo("03200100")
                    .isNotEqualTo("03200000")
                    .isParentOf(new KanCode("03200199"))
                    .isChildOf(new KanCode("03200199"))
                    .predicate(it -> it.getDepth() == 3));
        }

        @Test
        @DisplayName("uses subclass of that")
        void test1() {
            // given
            class FullyRestrictedAssertImpl extends FullyRestrictedAssert {
                public FullyRestrictedAssertImpl(KanCode actual) {
                    super(actual);
                }

                protected FullyRestrictedAssertImpl(Descriptor<?> descriptor, KanCode actual) {
                    super(descriptor, actual);
                }

                @Override
                public FullyRestrictedAssertImpl isNotNull() {
                    super.isNotNull();
                    return this;
                }

                @Override
                public FullyRestrictedAssertImpl isParentOf(KanCode expected) {
                    if (!actual.isParentOf(expected)) throw getException();
                    return this;
                }

                public FullyRestrictedAssertImpl asParent() {
                    return new FullyRestrictedAssertImpl(this, actual.getParent());
                }
            }

            // expect
            assertThatNoException().isThrownBy(() -> new FullyRestrictedAssertImpl(new KanCode("03200100"))
                    .isNotNull()
                    .asParent()
                    .isParentOf(new KanCode("03200100"))
                    .isEqualTo("03200000")
                    .isNotEqualTo("03200100")
                    .isChildOf(new KanCode("03000000"))
                    .predicate(it -> it.getDepth() == 2));
            assertThatIllegalArgumentException().isThrownBy(() -> new FullyRestrictedAssertImpl(new KanCode("03200100"))
                    .isNotNull()
                    .asParent()
                    .isParentOf(new KanCode("03200100"))
                    .isEqualTo("03200000")
                    .isNotEqualTo("03200100")
                    .isChildOf(new KanCode("03200100"))
                    .predicate(it -> it.getDepth() == 2));
        }
    }

    // -------------------------------------------------------------------------------------------------

    private static class MyAsserts extends Asserts {
        public static FullyExtensibleAssert<?, Foo> that(Foo foo) {
            return new FullyExtensibleAssert<>(foo);
        }

        public static FullyRestrictedAssert that(KanCode kanCode) {
            return new FullyRestrictedAssert(kanCode);
        }
    }

}
