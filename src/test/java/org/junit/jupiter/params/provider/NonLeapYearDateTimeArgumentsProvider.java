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

import io.github.imsejin.common.util.DateTimeUtils;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NonLeapYearDateTimeArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        LocalDateTime start = LocalDate.of(0, Month.JANUARY, 1).atTime(LocalTime.MIN);
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        NonLeapYearDateTimeSource annotation = context.getRequiredTestMethod().getAnnotation(NonLeapYearDateTimeSource.class);

        return IntStream.generate(() -> 0).mapToObj(n -> DateTimeUtils.random(start, end))
                .filter(it -> !it.toLocalDate().isLeapYear()).limit(annotation.value()).map(Arguments::of);
    }

}
