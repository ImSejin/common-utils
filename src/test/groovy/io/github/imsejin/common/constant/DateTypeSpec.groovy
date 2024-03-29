/*
 * Copyright 2020 Sejin Im
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

package io.github.imsejin.common.constant

import spock.lang.Specification

import java.time.format.DateTimeFormatter

class DateTypeSpec extends Specification {

    def "contains"() {
        expect:
        DateType.contains pattern

        where:
        pattern << ['yyyy', 'MM', 'dd', 'HH', 'mm', 'ss', 'SSS',
                    'yyyyMM', 'yyyyMMdd', 'HHmmss', 'HHmmssSSS', 'yyyyMMddHHmmss', 'yyyyMMddHHmmssSSS',
                    'yyyy-MM-dd', 'HH:mm:ss', 'HH:mm:ss.SSS', 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss.SSS']
    }

    def "from"() {
        when:
        def dateType = DateType.from pattern

        then:
        dateType != null
        dateType.pattern == pattern
        dateType.formatter.toString() == DateTimeFormatter.ofPattern(pattern).toString()

        where:
        pattern << ['yyyy', 'MM', 'dd', 'HH', 'mm', 'ss', 'SSS',
                    'yyyyMM', 'yyyyMMdd', 'HHmmss', 'HHmmssSSS', 'yyyyMMddHHmmss', 'yyyyMMddHHmmssSSS',
                    'yyyy-MM-dd', 'HH:mm:ss', 'HH:mm:ss.SSS', 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss.SSS']
    }

}
