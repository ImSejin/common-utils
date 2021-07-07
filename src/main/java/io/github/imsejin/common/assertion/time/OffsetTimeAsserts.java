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

package io.github.imsejin.common.assertion.time;

import io.github.imsejin.common.assertion.object.ObjectAsserts;

import java.time.OffsetTime;

@SuppressWarnings("unchecked")
public class OffsetTimeAsserts<SELF extends OffsetTimeAsserts<SELF>> extends ObjectAsserts<SELF> {

    private final OffsetTime actual;

    public OffsetTimeAsserts(OffsetTime actual) {
        super(actual);
        this.actual = actual;
    }

    public SELF isSuperPackageOf(OffsetTime expected) {
        if (false) {
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isSubPackageOf(OffsetTime expected) {
        if (false) {
            throw getException();
        }

        return (SELF) this;
    }

}
