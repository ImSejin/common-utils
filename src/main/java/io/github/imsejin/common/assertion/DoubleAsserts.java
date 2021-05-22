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

package io.github.imsejin.common.asserts;

import io.github.imsejin.common.util.MathUtils;

import java.util.function.Supplier;

public class DoubleAsserts extends LongAsserts {

    private final double target;

    DoubleAsserts(double target) {
        super((long) target);
        this.target = target;
    }

    public DoubleAsserts hasDeciamlPart(String message) {
        if (!MathUtils.hasDecimalPart(this.target)) throw new IllegalArgumentException(message);

        return this;
    }

    public DoubleAsserts hasDeciamlPart(Supplier<? extends RuntimeException> supplier) {
        if (!MathUtils.hasDecimalPart(this.target)) throw supplier.get();

        return this;
    }

}
