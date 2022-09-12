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

package org.junit.jupiter.params.converter;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.platform.commons.util.ReflectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @see JavaTimeFormat
 * @see JavaTimeArgumentConverter
 */
public class StringToJavaTimeArgumentConverter implements ArgumentConverter {

    public static final Class<String> SOURCE_TYPE = String.class;

    private static final Map<Class<?>, BiFunction<String, String, ?>> CONVERTERS;

    static {
        Map<Class<?>, BiFunction<String, String, ?>> converters = new HashMap<>();
        converters.put(Date.class, (source, format) -> {
            try {
                return new SimpleDateFormat(format).parse(source);
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        converters.put(Year.class, (source, format) -> Year.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(YearMonth.class, (source, format) -> YearMonth.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(Month.class, (source, format) -> Month.of(Integer.parseInt(source)));
        converters.put(MonthDay.class, (source, format) -> MonthDay.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(LocalDate.class, (source, format) -> LocalDate.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(ChronoLocalDate.class, (source, format) -> LocalDate.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(LocalTime.class, (source, format) -> LocalTime.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(LocalDateTime.class, (source, format) -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(ChronoLocalDateTime.class, (source, format) -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(OffsetDateTime.class, (source, format) -> OffsetDateTime.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(OffsetTime.class, (source, format) -> OffsetTime.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(ZonedDateTime.class, (source, format) -> ZonedDateTime.parse(source, DateTimeFormatter.ofPattern(format)));
        converters.put(ChronoZonedDateTime.class, (source, format) -> ZonedDateTime.parse(source, DateTimeFormatter.ofPattern(format)));

        CONVERTERS = Collections.unmodifiableMap(converters);
    }

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (source == null) return null;

        if (!SOURCE_TYPE.isInstance(source)) {
            String message = String.format(
                    "%s cannot convert objects of type [%s]. Source objects of type [%s] are supported only.",
                    getClass().getSimpleName(), source.getClass().getName(), SOURCE_TYPE.getName());
            throw new ArgumentConversionException(message);
        }

        JavaTimeFormat annotation = context.getParameter().getAnnotation(JavaTimeFormat.class);
        String format = annotation.value();

        Class<?> paramType = context.getParameter().getType();
        for (Class<?> targetType : CONVERTERS.keySet()) {
            if (ReflectionUtils.isAssignableTo(targetType, paramType)) {
                return CONVERTERS.get(targetType).apply((String) source, format);
            }
        }

        String message = String.format("%s cannot convert to type [%s]. Only target types [%s] are supported.",
                getClass().getSimpleName(), paramType.getName(), Arrays.toString(CONVERTERS.keySet().stream().map(Class::getName).toArray()));
        throw new ArgumentConversionException(message);
    }

}
