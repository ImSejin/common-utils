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

    def "ReplaceUnallowables"() {
        when:
        def actual = FilenameUtils.replaceUnallowables filename

        then:
        actual == expected

        where:
        filename                                            | expected
        "where he is gone.."                                | "where he is gone…"
        "I feel happy when coding."                         | "I feel happy when coding．"
        "** <happy/\\new year> **:\"john\" -> |\"jeremy\"|" | "＊＊ ＜happy／＼new year＞ ＊＊：＂john＂ -＞ ｜＂jeremy＂｜"
    }

}