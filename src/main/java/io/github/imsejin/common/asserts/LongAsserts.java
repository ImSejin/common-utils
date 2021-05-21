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

import java.util.function.Supplier;

public class LongAsserts {

    private final long target;

    LongAsserts(long target) {
        this.target = target;
    }

    public LongAsserts isPositive(String message) {
        if (this.target < 1) throw new IllegalArgumentException(message);

        return this;
    }

    public LongAsserts isPositive(Supplier<? extends RuntimeException> supplier) {
        if (this.target < 1) throw supplier.get();

        return this;
    }

    public LongAsserts isZeroOrPositive(String message) {
        if (this.target < 0) throw new IllegalArgumentException(message);

        return this;
    }

    public LongAsserts isZeroOrPositive(Supplier<? extends RuntimeException> supplier) {
        if (this.target < 0) throw supplier.get();

        return this;
    }

    public LongAsserts isNegative(String message) {
        if (this.target > -1) throw new IllegalArgumentException(message);

        return this;
    }

    public LongAsserts isNegative(Supplier<? extends RuntimeException> supplier) {
        if (this.target > -1) throw supplier.get();

        return this;
    }

    public LongAsserts isZeroOrNegative(String message) {
        if (this.target > 0) throw new IllegalArgumentException(message);

        return this;
    }

    public LongAsserts isZeroOrNegative(Supplier<? extends RuntimeException> supplier) {
        if (this.target > 0) throw supplier.get();

        return this;
    }

}
