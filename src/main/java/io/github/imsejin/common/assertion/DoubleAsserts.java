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

import io.github.imsejin.common.util.MathUtils;

@SuppressWarnings("unchecked")
public class DoubleAsserts<SELF extends DoubleAsserts<SELF>> extends LongAsserts<SELF> {

    private final double target;

    DoubleAsserts(double target) {
        super((long) target);
        this.target = target;
    }

    public SELF hasDeciamlPart() {
        if (!MathUtils.hasDecimalPart(this.target)) throw getException();
        return (SELF) this;
    }

}