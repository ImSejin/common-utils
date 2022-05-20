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

package org.junit.jupiter.params.provider;

import org.junit.jupiter.params.common.Switch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(RandomDateTimeArgumentsProvider.class)
public @interface RandomDateTimeSource {

    /**
     * The number of sources.
     *
     * @return the number of sources
     */
    int count() default 10;

    /**
     * Timezone.
     *
     * @return timezone of source
     */
    String timezone() default "UTC";

    /**
     * Whether source is leap year or not.
     *
     * @return whether source is leap year or not.
     */
    Switch leapYear() default Switch.NEUTRAL;

}
