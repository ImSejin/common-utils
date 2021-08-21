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

package io.github.imsejin.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ArrayUtils")
class ArrayUtilsTest {

    @Test
    @DisplayName("method 'prepend'")
    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    void prepend() {
        assertThat(ArrayUtils.prepend(new Object[]{}, null))
                .isNotNull()
                .isEmpty();

        assertThat(ArrayUtils.prepend(new Object[]{}))
                .isNotNull()
                .isEmpty();

        assertThat(ArrayUtils.prepend(new Object[]{}, null, null))
                .isNotEmpty()
                .containsExactly(null, null);

        assertThat(ArrayUtils.prepend(new Object[]{}, new Long[]{-1L, 0L, 1L}))
                .isNotEmpty()
                .containsExactly(-1L, 0L, 1L);

        assertThat(ArrayUtils.prepend(new Boolean[]{true, false}, null))
                .isNotEmpty()
                .doesNotContainNull()
                .containsExactly(true, false);

        assertThat(ArrayUtils.prepend(new Integer[]{1, 2, 3}))
                .isNotEmpty()
                .containsExactly(1, 2, 3);

        assertThat(ArrayUtils.prepend(new Class[]{getClass()}, null, null))
                .isNotEmpty()
                .containsExactly(null, null, getClass());

        assertThat(ArrayUtils.prepend(new Character[]{'α', 'β', 'γ', 'δ'}, 'Ω'))
                .isNotEmpty()
                .containsExactly('Ω', 'α', 'β', 'γ', 'δ');

        assertThat(ArrayUtils.prepend(new String[]{"alpha", "beta", "gamma", "delta", "epsilon"}, 'Y', 'Z'))
                .isNotEmpty()
                .containsExactly('Y', 'Z', "alpha", "beta", "gamma", "delta", "epsilon");

        assertThat(ArrayUtils.prepend(new Double[]{0.0, 0.1, 0.2}, new Float[]{0.0F, 0.1F, 0.2F}))
                .isNotEmpty()
                .containsExactly(0.0F, 0.1F, 0.2F, 0.0, 0.1, 0.2);
    }

    @Test
    @DisplayName("method 'append'")
    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    void append() {
        assertThat(ArrayUtils.append(new Object[]{}, null))
                .isNotNull()
                .isEmpty();

        assertThat(ArrayUtils.append(new Object[]{}))
                .isNotNull()
                .isEmpty();

        assertThat(ArrayUtils.append(new Object[]{}, null, null))
                .isNotEmpty()
                .containsExactly(null, null);

        assertThat(ArrayUtils.append(new Object[]{}, new Long[]{1L, 0L, -1L}))
                .isNotEmpty()
                .containsExactly(1L, 0L, -1L);

        assertThat(ArrayUtils.append(new Boolean[]{false, true}, null))
                .isNotEmpty()
                .doesNotContainNull()
                .containsExactly(false, true);

        assertThat(ArrayUtils.append(new Integer[]{1, 2, 3}))
                .isNotEmpty()
                .containsExactly(1, 2, 3);

        assertThat(ArrayUtils.append(new Class[]{getClass()}, null, null))
                .isNotEmpty()
                .containsExactly(getClass(), null, null);

        assertThat(ArrayUtils.append(new Character[]{'Φ', 'Χ', 'Ψ', 'Ω'}, 'α'))
                .isNotEmpty()
                .containsExactly('Φ', 'Χ', 'Ψ', 'Ω', 'α');

        assertThat(ArrayUtils.append(new String[]{"UPSILON", "PHI", "CHI", "PSI", "OMEGA"}, 'a', 'b'))
                .isNotEmpty()
                .containsExactly("UPSILON", "PHI", "CHI", "PSI", "OMEGA", 'a', 'b');

        assertThat(ArrayUtils.append(new Double[]{0.0, 0.1, 0.2}, new Float[]{0.0F, 0.1F, 0.2F}))
                .isNotEmpty()
                .containsExactly(0.0, 0.1, 0.2, 0.0F, 0.1F, 0.2F);
    }

}
