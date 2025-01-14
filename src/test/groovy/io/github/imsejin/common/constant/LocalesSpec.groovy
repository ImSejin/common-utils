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

package io.github.imsejin.common.constant

import spock.lang.Specification

class LocalesSpec extends Specification {

    def "Gets all the languages"() {
        given:
        def languages = Locales.languages as List<Locale>

        expect:
        languages != null
        languages.size() == 46
        languages.size() == languages.count { it.language.length() > 0 && it.country.empty }
        languages.size() == languages.unique { it.language }.size()
    }

    def "Gets all the countries"() {
        given:
        def countries = Locales.countries as List<Locale>

        expect:
        countries != null
        countries.size() == 107
        countries.size() == countries.count { !it.language.empty && !it.country.empty }
        countries.size() == countries.unique { it.toLanguageTag() }.size()
    }

}
