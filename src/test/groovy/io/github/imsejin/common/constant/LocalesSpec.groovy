package io.github.imsejin.common.constant

import spock.lang.Specification

class LocalesSpec extends Specification {

    def "Gets all the languages"() {
        given:
        def languages = Locales.languages as List<Locale>

        expect:
        languages != null
        languages.size() == 46
        languages.size() == languages.grep { it.language.length() > 0 && it.country.isEmpty() }.size()
        languages.size() == languages.unique { it.language }.size()
    }

    def "Gets all the countries"() {
        given:
        def countries = Locales.countries as List<Locale>

        expect:
        countries != null
        countries.size() == 107
        countries.size() == countries.grep { !it.language.isEmpty() && !it.country.isEmpty() }.size()
        countries.size() == countries.unique { it.toLanguageTag() }.size()
    }

}
