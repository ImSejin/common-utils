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

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.util.ArrayUtils;
import io.github.imsejin.common.util.StringUtils;

import java.text.MessageFormat;
import java.util.function.Function;

public abstract class Descriptor<SELF extends Descriptor<SELF>> {

    protected final SELF self;

    private String description;

    private Object[] arguments;

    private Function<String, ? extends RuntimeException> function = IllegalArgumentException::new;

    @SuppressWarnings("unchecked")
    protected Descriptor() {
        this.self = (SELF) this;
    }

    protected static void merge(Descriptor<?> source, Descriptor<?> target) {
        target.description = source.description;
        target.arguments = source.arguments;
        target.function = source.function;
    }

    public final SELF as(String description, Object... args) {
        this.description = description;
        this.arguments = args;
        return this.self;
    }

    public final SELF exception(Function<String, ? extends RuntimeException> function) {
        this.function = function;
        return this.self;
    }

    protected final RuntimeException getException() {
        return this.function.apply(getMessage());
    }

    protected final void setDefaultDescription(String description, Object... args) {
        // If description is set already by user, ignores default description.
        if (!StringUtils.isNullOrEmpty(this.description)) return;

        this.description = description;
        this.arguments = args;
    }

    private String getMessage() {
        // Prevent NPE.
        if (StringUtils.isNullOrEmpty(this.description)) return "";

        // Escapes single quotation marks.
        String pattern = this.description.replace("'", "''");
        MessageFormat messageFormat = new MessageFormat(pattern);

        // Stringifies array in the arguments.
        String[] strings = new String[this.arguments.length];
        for (int i = 0; i < this.arguments.length; i++) {
            Object argument = this.arguments[i];
            strings[i] = ArrayUtils.toString(argument);
        }

        return messageFormat.format(strings);
    }

    /**
     * {@inheritDoc}
     *
     * <p> Deprecated not to be confused with {@link ObjectAssert#isEqualTo(Object)}.
     */
    @Override
    @Deprecated
    @ExcludeFromGeneratedJacocoReport
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
