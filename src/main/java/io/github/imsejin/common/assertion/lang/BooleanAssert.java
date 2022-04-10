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

public class BooleanAssert<SELF extends BooleanAssert<SELF>> extends ObjectAssert<SELF, Boolean> {

    public BooleanAssert(Boolean actual) {
        super(actual);
    }

    public SELF isTrue() {
        if (!actual) {
            setDefaultDescription("It is expected to be true, but it isn't. (expected: 'false')");
            throw getException();
        }

        return self;
    }

    public SELF isFalse() {
        if (actual) {
            setDefaultDescription("It is expected to be false, but it isn't. (expected: 'true')");
            throw getException();
        }

        return self;
    }

}
