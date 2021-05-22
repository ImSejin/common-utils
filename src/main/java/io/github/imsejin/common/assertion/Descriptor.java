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

import io.github.imsejin.common.util.StringUtils;

import java.text.MessageFormat;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public abstract class Descriptor<SELF extends Descriptor<SELF>> {

    private String messagePattern;

    private Object[] arguments;

    private Function<String, ? extends RuntimeException> function = IllegalArgumentException::new;

    public SELF as(String description, Object... args) {
        this.messagePattern = StringUtils.ifNullOrEmpty(description, "");
        this.arguments = args;
        return (SELF) this;
    }

    public SELF exception(Function<String, ? extends RuntimeException> function) {
        this.function = function;
        return (SELF) this;
    }

    protected final String getMessage() {
        // Escapes single quotation marks.
        String pattern = this.messagePattern.replace("'", "''");
        MessageFormat messageFormat = new MessageFormat(pattern);

        return messageFormat.format(this.arguments);
    }

    protected final RuntimeException getException() {
        return this.function.apply(getMessage());
    }

}
