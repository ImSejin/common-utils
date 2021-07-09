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

class OperatingSystemSpec extends Specification {

    def "contains"() {
        expect:
        OperatingSystem.contains(keyword)

        where:
        keyword << ['win', 'mac', 'nix', 'nux', 'aix', 'sunos']
    }

    def "of"() {
        when:
        def os = OperatingSystem.of(keyword).orElse(null)

        then:
        os != null
        os.getKeywords().contains(keyword)

        where:
        keyword << ['win', 'mac', 'nix', 'nux', 'aix', 'sunos']
    }

}
