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
import org.junit.jupiter.params.converter.ConvertJavaTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @see ConvertJavaTime
 * @see RandomJavaTimeSource
 */
public class RandomJavaTimeArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        RandomJavaTimeSource annotation = Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(RandomJavaTimeSource.class),
                "RandomDateTimeArgumentsProvider required @RandomDateTimeSource; you must annotate it to method");

        LocalDateTime start = LocalDateTime.parse(annotation.start());
        LocalDateTime end = LocalDateTime.parse(annotation.end());

        ZoneId timezone = ZoneId.of(annotation.timezone());

        // Resolves leap year option handling ZonedDateTime.
        Predicate<ZonedDateTime> predicate;
        switch (annotation.leapYear()) {
            case ON:
                predicate = it -> it.toLocalDate().isLeapYear();
                break;
            case OFF:
                predicate = it -> !it.toLocalDate().isLeapYear();
                break;
            case NEUTRAL:
            default:
                predicate = it -> true;
        }

        return IntStream.generate(() -> 0).mapToObj(n -> DateTimeUtils.random(start, end).atZone(timezone))
                .filter(predicate).limit(annotation.count()).map(Arguments::of);
    }

}
