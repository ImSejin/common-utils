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
import java.util.Objects;
import java.util.function.Function;

/**
 * Description manager for assertion classes
 *
 * @param <SELF> subclass of this class
 */
public abstract class Descriptor<SELF extends Descriptor<SELF>> {

    /**
     * Subclass of {@link Descriptor}.
     *
     * <p> This is the instance of oneself wrapped in generic type.
     */
    protected final SELF self;

    private String description;

    private Object[] arguments;

    private Function<String, ? extends RuntimeException> exception = IllegalArgumentException::new;

    @SuppressWarnings("unchecked")
    protected Descriptor() {
        this.self = (SELF) this;
    }

    @SuppressWarnings("CopyConstructorMissesField")
    protected Descriptor(Descriptor<?> descriptor) {
        this();

        // Overwrites properties of this descriptor from the given descriptor.
        this.description = descriptor.description;
        this.arguments = descriptor.arguments;
        this.exception = descriptor.exception;
    }

    /**
     * Describes assertion message with arguments.
     *
     * <p> On assertion failure, exception has the description as message.
     *
     * <pre>{@code
     *     Asserts.that(4)
     *             .isNotNull()
     *             // number > 5 (number: '4')
     *             .describedAs("number > 5 (number: '{0}')", 4)
     *             .isGreaterThan(5);
     * }</pre>
     *
     * @param description assertion message
     * @param args        arguments for description
     * @return subclass of this class
     */
    public final SELF describedAs(String description, Object... args) {
        this.description = Objects.requireNonNull(description, "Descriptor.description cannot be null");
        this.arguments = Objects.requireNonNull(args, "Descriptor.arguments cannot be null");
        return this.self;
    }

    /**
     * Sets type of exception on assertion failure.
     *
     * <p> Default type of exception is {@link IllegalArgumentException}.
     *
     * <pre>{@code
     *     Asserts.that(4)
     *             .isNotNull()
     *             // Will be throw ArithmeticException.
     *             .thrownBy(ArithmeticException::new)
     *             .isGreaterThan(5);
     * }</pre>
     *
     * @param function function that changes string from exception
     * @return subclass of this class
     */
    public final SELF thrownBy(Function<String, ? extends RuntimeException> function) {
        this.exception = Objects.requireNonNull(function, "Descriptor.exception cannot be null");
        return this.self;
    }

    // -------------------------------------------------------------------------------------------------

    protected final RuntimeException getException() {
        String message = getMessage();
        return this.exception.apply(message);
    }

    protected final void setDefaultDescription(String description, Object... args) {
        // Ignores default description when there is no description set by user.
        if (!StringUtils.isNullOrEmpty(this.description)) {
            return;
        }

        describedAs(description, args);
    }

    // -------------------------------------------------------------------------------------------------

    private String getMessage() {
        // Avoids NPE.
        if (StringUtils.isNullOrEmpty(this.description)) {
            return "";
        }

        // Escapes single quotation marks.
        String pattern = this.description.replace("'", "''");
        MessageFormat messageFormat = new MessageFormat(pattern);

        // Stringifies array in the arguments.
        String[] strings = new String[this.arguments.length];
        for (int i = 0; i < this.arguments.length; i++) {
            Object argument = this.arguments[i];
            String string = ArrayUtils.toString(argument);

            strings[i] = string;
        }

        return messageFormat.format(strings);
    }

    // -------------------------------------------------------------------------------------------------

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
