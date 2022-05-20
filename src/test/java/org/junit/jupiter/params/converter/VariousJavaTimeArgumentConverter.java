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
import org.junit.jupiter.params.provider.RandomDateTimeSource;
import org.junit.platform.commons.util.ReflectionUtils;

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
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @see ConvertJavaTime
 * @see RandomDateTimeSource
 * @see JavaTimeArgumentConverter
 */
public class VariousJavaTimeArgumentConverter implements ArgumentConverter {

    private static final Class<ZonedDateTime> SOURCE_TYPE = ZonedDateTime.class;

    private static final Map<Class<? extends TemporalAccessor>, Function<ZonedDateTime, TemporalAccessor>> CONVERTERS;

    static {
        Map<Class<? extends TemporalAccessor>, Function<ZonedDateTime, TemporalAccessor>> converters = new HashMap<>();
        converters.put(Year.class, Year::from);
        converters.put(YearMonth.class, YearMonth::from);
        converters.put(Month.class, ZonedDateTime::getMonth);
        converters.put(MonthDay.class, MonthDay::from);
        converters.put(LocalDate.class, ZonedDateTime::toLocalDate);
        converters.put(ChronoLocalDate.class, ZonedDateTime::toLocalDate);
        converters.put(LocalTime.class, ZonedDateTime::toLocalTime);
        converters.put(LocalDateTime.class, ZonedDateTime::toLocalDateTime);
        converters.put(ChronoLocalDateTime.class, ZonedDateTime::toLocalDateTime);
        converters.put(OffsetDateTime.class, ZonedDateTime::toOffsetDateTime);
        converters.put(OffsetTime.class, OffsetTime::from);
        converters.put(ZonedDateTime.class, it -> it);
        converters.put(ChronoZonedDateTime.class, it -> it);

        CONVERTERS = Collections.unmodifiableMap(converters);
    }

    /**
     * @see TypedArgumentConverter
     */
    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (source == null) return null;

        if (!SOURCE_TYPE.isInstance(source)) {
            String message = String.format(
                    "%s cannot convert objects of type [%s]. Only source objects of type [%s] are supported.",
                    getClass().getSimpleName(), source.getClass().getName(), SOURCE_TYPE.getName());
            throw new ArgumentConversionException(message);
        }

        Class<?> paramType = context.getParameter().getType();
        for (Class<?> targetType : CONVERTERS.keySet()) {
            if (ReflectionUtils.isAssignableTo(targetType, paramType)) {
                return CONVERTERS.get(targetType).apply((ZonedDateTime) source);
            }
        }

        String message = String.format("%s cannot convert to type [%s]. Only target types [%s] are supported.",
                getClass().getSimpleName(), paramType.getName(), Arrays.toString(CONVERTERS.keySet().stream().map(Class::getName).toArray()));
        throw new ArgumentConversionException(message);
    }

}
