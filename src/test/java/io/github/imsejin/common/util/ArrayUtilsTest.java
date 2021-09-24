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
    @DisplayName("method 'isNullOrEmpty'")
    void isNullOrEmpty() {
        boolean[] booleans = null;
        assertThat(ArrayUtils.isNullOrEmpty(booleans)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new boolean[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new boolean[]{false, true})).isFalse();

        byte[] bytes = null;
        assertThat(ArrayUtils.isNullOrEmpty(bytes)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new byte[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new byte[]{-128, 0, 127})).isFalse();

        char[] chars = null;
        assertThat(ArrayUtils.isNullOrEmpty(chars)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new char[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new char[]{'a', 'b', 'c'})).isFalse();

        double[] doubles = null;
        assertThat(ArrayUtils.isNullOrEmpty(doubles)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new double[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new double[]{-3.14, 0.0, 3.14})).isFalse();

        float[] floats = null;
        assertThat(ArrayUtils.isNullOrEmpty(floats)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new float[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new float[]{-3.14F, 0.0F, 3.14F})).isFalse();

        int[] ints = null;
        assertThat(ArrayUtils.isNullOrEmpty(ints)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new int[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new int[]{-128, 0, 127})).isFalse();

        long[] longs = null;
        assertThat(ArrayUtils.isNullOrEmpty(longs)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new long[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new long[]{-128, 0, 127})).isFalse();

        short[] shorts = null;
        assertThat(ArrayUtils.isNullOrEmpty(shorts)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new short[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new short[]{-32768, 0, 32767})).isFalse();

        String[] strings = null;
        assertThat(ArrayUtils.isNullOrEmpty(strings)).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new String[0])).isTrue();
        assertThat(ArrayUtils.isNullOrEmpty(new String[]{"alpha", "beta", "delta"})).isFalse();
    }

    @Test
    @DisplayName("method 'exists'")
    void exists() {
        boolean[] booleans = null;
        assertThat(ArrayUtils.exists(booleans)).isFalse();
        assertThat(ArrayUtils.exists(new boolean[0])).isFalse();
        assertThat(ArrayUtils.exists(new boolean[]{false, true})).isTrue();

        byte[] bytes = null;
        assertThat(ArrayUtils.exists(bytes)).isFalse();
        assertThat(ArrayUtils.exists(new byte[0])).isFalse();
        assertThat(ArrayUtils.exists(new byte[]{-128, 0, 127})).isTrue();

        char[] chars = null;
        assertThat(ArrayUtils.exists(chars)).isFalse();
        assertThat(ArrayUtils.exists(new char[0])).isFalse();
        assertThat(ArrayUtils.exists(new char[]{'a', 'b', 'c'})).isTrue();

        double[] doubles = null;
        assertThat(ArrayUtils.exists(doubles)).isFalse();
        assertThat(ArrayUtils.exists(new double[0])).isFalse();
        assertThat(ArrayUtils.exists(new double[]{-3.14, 0.0, 3.14})).isTrue();

        float[] floats = null;
        assertThat(ArrayUtils.exists(floats)).isFalse();
        assertThat(ArrayUtils.exists(new float[0])).isFalse();
        assertThat(ArrayUtils.exists(new float[]{-3.14F, 0.0F, 3.14F})).isTrue();

        int[] ints = null;
        assertThat(ArrayUtils.exists(ints)).isFalse();
        assertThat(ArrayUtils.exists(new int[0])).isFalse();
        assertThat(ArrayUtils.exists(new int[]{-128, 0, 127})).isTrue();

        long[] longs = null;
        assertThat(ArrayUtils.exists(longs)).isFalse();
        assertThat(ArrayUtils.exists(new long[0])).isFalse();
        assertThat(ArrayUtils.exists(new long[]{-128, 0, 127})).isTrue();

        short[] shorts = null;
        assertThat(ArrayUtils.exists(shorts)).isFalse();
        assertThat(ArrayUtils.exists(new short[0])).isFalse();
        assertThat(ArrayUtils.exists(new short[]{-32768, 0, 32767})).isTrue();

        String[] strings = null;
        assertThat(ArrayUtils.exists(strings)).isFalse();
        assertThat(ArrayUtils.exists(new String[0])).isFalse();
        assertThat(ArrayUtils.exists(new String[]{"alpha", "beta", "delta"})).isTrue();
    }

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
