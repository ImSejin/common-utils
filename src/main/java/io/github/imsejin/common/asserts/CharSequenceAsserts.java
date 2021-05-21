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

public class CharSequenceAsserts extends ObjectAsserts {

    private final CharSequence target;

    CharSequenceAsserts(CharSequence target) {
        super(target);
        this.target = target;
    }

    public CharSequenceAsserts hasText(String message) {
        isNotNull(message);
        if (this.target.length() <= 0) throw new IllegalArgumentException(message);

        return this;
    }

    public CharSequenceAsserts hasText(Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (this.target.length() <= 0) throw supplier.get();

        return this;
    }

    public CharSequenceAsserts isEmpty(String message) {
        if (this.target != null && this.target.length() > 0) throw new IllegalArgumentException(message);

        return this;
    }

    public CharSequenceAsserts isEmpty(Supplier<? extends RuntimeException> supplier) {
        if (this.target != null && this.target.length() > 0) throw supplier.get();

        return this;
    }

}
