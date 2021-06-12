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

package io.github.imsejin.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Annotation for exclusion from jacoco code coverage report.
 * <p>
 * Since Jacoco 0.8.2, you can exclude classes and methods by annotating them
 * with a custom annotation with the following properties.
 *
 * <ul>
 *     <li>The name of the annotation should include 'Generated'.</li>
 *     <li>The retention policy of annotation should be runtime or class.</li>
 * </ul>
 *
 * @see <a href="https://www.baeldung.com/jacoco-report-exclude">Exclusions from Jacoco Report</a>
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({TYPE, METHOD, CONSTRUCTOR})
public @interface ExcludeFromGeneratedJacocoReport {
}
