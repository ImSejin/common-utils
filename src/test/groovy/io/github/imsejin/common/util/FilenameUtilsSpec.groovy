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

package io.github.imsejin.common.util

import spock.lang.Specification

class FilenameUtilsSpec extends Specification {

    def "Gets index of file extension"() {
        when:
        def actual = FilenameUtils.indexOfExtension filename

        then:
        actual == expected

        where:
        filename               | expected
        null                   | -1
        ""                     | -1
        ".git"                 | -1
        "data"                 | -1
        "rt.jar"               | 2
        "data.dat"             | 4
        "Specification.groovy" | 13
        "jdk_1.8.0_202.txt"    | 13
    }

    def "Gets name from path"() {
        when:
        def actual = FilenameUtils.getName path

        then:
        actual == expected

        where:
        path                                                       | expected
        null                                                       | ""
        ""                                                         | ""
        ".git"                                                     | ".git"
        "data.dat"                                                 | "data.dat"
        "../data"                                                  | "data"
        "/data.dat"                                                | "data.dat"
        "spock/lang/Specification.groovy"                          | "Specification.groovy"
        "/var/lib/jdk_1.8.0_202/jre/lib/rt.jar"                    | "rt.jar"
        "/var/lib/apache/"                                         | "apache"
        "spock\\lang\\Specification.groovy"                        | "Specification.groovy"
        "C:\\Program Files\\Java\\jdk_1.8.0_202\\jre\\lib\\rt.jar" | "rt.jar"
        "C:\\Program Files\\Apache\\"                              | "Apache"
    }

    def "Gets base name from filename"() {
        when:
        def actual = FilenameUtils.getBaseName filename

        then:
        actual == expected

        where:
        filename                 | expected
        null                     | ""
        ""                       | filename
        ".git"                   | filename
        "data.dat"               | "data"
        "Specification.groovy"   | "Specification"
        "common-utils-0.1.0.jar" | "common-utils-0.1.0"
    }

    def "Gets file extension from filename"() {
        when:
        def actual = FilenameUtils.getExtension filename

        then:
        actual == expected

        where:
        filename                 | expected
        null                     | ""
        ""                       | ""
        ".git"                   | ""
        "data.dat"               | "dat"
        "Specification.groovy"   | "groovy"
        "common-utils-0.1.0.jar" | "jar"
    }

    def "Replaces unallowable characters"() {
        when:
        def actual = FilenameUtils.replaceUnallowables filename

        then:
        actual == expected

        where:
        filename                                        | expected
        'where he is gone..'                            | 'where he is gone…'
        'I feel like... happy when coding.'             | 'I feel like... happy when coding．'
        'How am I supposed to live without you?'        | 'How am I supposed to live without you？'
        '** <happy/\\new year> **:"john" -> |"jeremy"|' | '＊＊ ＜happy／＼new year＞ ＊＊：＂john＂ -＞ ｜＂jeremy＂｜'
    }

}
