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

import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThatNoException;

public class CustomAssertsTest {

    @Test
    void test() {
        // given
        KanCode kanCode = new KanCode("01020300");

        // expect
        assertThatNoException().isThrownBy(() -> Asserts.that(kanCode)
                .isNotNull()
                .isEqualTo(new KanCode("01020300"))
                .isNotEqualTo(new KanCode("01020304"))
                .predicate(it -> it.isChildOf(new KanCode("01020000")))
                .returns(true, it -> it.isParentOf(new KanCode("01020304"))));
        assertThatNoException().isThrownBy(() -> MyAsserts.that(kanCode)
                .isNotNull()
                .isEqualTo("01020300")
                .isNotEqualTo("01020304")
                .isChildOf(new KanCode("01020000"))
                .isParentOf(new KanCode("01020304")));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static class MyAsserts extends Asserts {
        public static KanCodeAssert<?> that(KanCode kanCode) {
            return new KanCodeAssert<>(kanCode);
        }
    }

    private static class KanCodeAssert<SELF extends KanCodeAssert<SELF>> extends ObjectAssert<SELF, KanCode> {
        public KanCodeAssert(KanCode kanCode) {
            super(kanCode);
        }

        public SELF isEqualTo(String expected) {
            return super.isEqualTo(new KanCode(expected));
        }

        public SELF isNotEqualTo(String expected) {
            return super.isNotEqualTo(new KanCode(expected));
        }

        public SELF isParentOf(KanCode expected) {
            if (!actual.isParentOf(expected)) throw getException();
            return self;
        }

        public SELF isChildOf(KanCode expected) {
            if (!actual.isChildOf(expected)) throw getException();
            return self;
        }
    }

    @Getter
    @EqualsAndHashCode(of = "code")
    private static class KanCode {

        private static final String EMPTINESS = "00";
        private final String large;
        private final String medium;
        private final String small;
        private final String extraSmall;

        @Getter(AccessLevel.NONE)
        private final String code;
        private final int depth;

        public KanCode(String code) {
            validate0(code);

            this.large = code.substring(0, 2);
            this.medium = code.substring(2, 4);
            this.small = code.substring(4, 6);
            this.extraSmall = code.substring(6);

            // Finds depth of the kan code.
            int depth = 4;
            List<String> codes = Arrays.asList(large, medium, small, extraSmall);
            for (int i = 0; i < codes.size(); i++) {
                if (EMPTINESS.equals(codes.get(i))) {
                    depth = i;
                    break;
                }
            }

            this.depth = depth;
            this.code = code;
        }

        public KanCode(String large, String medium, String small, String extraSmall) {
            this(large + medium + small + extraSmall);
        }

        public static boolean validate(String code) {
            try {
                validate0(code);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }

        private static void validate0(String code) {
            Asserts.that(code)
                    .as("KanCode allows only 8 digits: '{0}'", code)
                    .isNotNull().hasLengthOf(8).isNumeric();

            String large = code.substring(0, 2);
            String medium = code.substring(2, 4);
            String small = code.substring(4, 6);
            String extraSmall = code.substring(6);

            BiConsumer<String, String> throwException = (target, parent) -> {
                throw new IllegalArgumentException(
                        String.format("%s code must be '00' when %s code is '00'; code is '%s'", target, parent, code));
            };

            // 00000000
            if (large.equals(EMPTINESS)) {
                // 00010000
                if (!medium.equals(EMPTINESS)) throwException.accept("Medium", "large");
                // 00000100
                if (!small.equals(EMPTINESS)) throwException.accept("Small", "large");
                // 00000001
                if (!extraSmall.equals(EMPTINESS)) throwException.accept("Extra small", "large");
            }

            // 01000000
            if (medium.equals(EMPTINESS)) {
                // 00000100
                if (!small.equals(EMPTINESS)) throwException.accept("Small", "medium");
                // 00000001
                if (!extraSmall.equals(EMPTINESS)) throwException.accept("Extra small", "medium");
            }

            // 01020000
            if (small.equals(EMPTINESS)) {
                // 00000001
                if (!extraSmall.equals(EMPTINESS)) throwException.accept("Extra small", "small");
            }
        }

        @Override
        public String toString() {
            return this.code;
        }

        /**
         * Returns this is parent of a kan code.
         *
         *
         * <pre><code>
         *     new KanCode("01020000").isParentOf(new KanCode("01020304")); // true
         *     new KanCode("01020304").isParentOf(new KanCode("01020000")); // false
         *     new KanCode("01020000").isParentOf(new KanCode("01020000")); // false
         * </code></pre>
         *
         * @param child child kan code
         */
        public boolean isParentOf(KanCode child) {
            if (this == child || this.equals(child)) return false;
            return child.code.startsWith(this.code.substring(0, this.depth * 2));
        }

        /**
         * Returns this is child of a kan code.
         *
         * <pre><code>
         *     new KanCode("01020000").isChildOf(new KanCode("01020304")); // false
         *     new KanCode("01020304").isChildOf(new KanCode("01020000")); // true
         *     new KanCode("01020000").isChildOf(new KanCode("01020000")); // false
         * </code></pre>
         *
         * @param parent parent kan code
         */
        public boolean isChildOf(KanCode parent) {
            if (this == parent || this.equals(parent)) return false;
            return this.code.startsWith(parent.code.substring(0, parent.depth * 2));
        }

        /**
         * Returns parent kan code of this.
         *
         * <pre><code>
         *     new KanCode("00000000").getParent(); // null
         *     new KanCode("01000000").getParent(); // new KanCode("00000000")
         *     new KanCode("01020000").getParent(); // new KanCode("01000000")
         *     new KanCode("01020300").getParent(); // new KanCode("01020000")
         *     new KanCode("01020304").getParent(); // new KanCode("01020300")
         * </code></pre>
         */
        @Nullable
        public KanCode getParent() {
            if (this.depth == 0) return null;

            String meaningfulCode = this.code.substring(0, this.depth * 2).replaceAll(".{2}$", EMPTINESS);
            return new KanCode(StringUtils.padEnd(8, meaningfulCode, "0"));
        }

    }

}
