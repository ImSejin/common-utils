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

package io.github.imsejin.common.util

import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Supplier

import static java.util.stream.Collectors.toList

class StringUtilsSpec extends Specification {

    private static final def LOREM_IPSUM = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam auctor nisl, sed tristique lacus dictum nec. Duis aliquam risus non eros dignissim, sed venenatis tellus dapibus. Nullam sapien dolor, commodo non semper ac, faucibus auctor lorem. Vestibulum suscipit enim quis ligula consectetur bibendum. Phasellus aliquet, libero vel convallis hendrerit, ante est rhoncus quam, sit amet finibus odio libero vitae lacus. Duis in dictum tellus. Sed quis turpis dictum enim ullamcorper semper et sit amet eros. Praesent posuere nisl vitae euismod malesuada. Nam accumsan est cursus leo porta, vitae bibendum metus faucibus. Pellentesque sollicitudin, lectus ut lobortis pellentesque, elit lorem pharetra diam, id molestie urna est a libero.
            Ut sit amet fringilla velit, sed pharetra velit. Morbi porttitor molestie velit ut sodales. Phasellus non tristique tortor. Maecenas leo magna, bibendum eget mi vitae, posuere dictum dolor. Donec dui sapien, pharetra id nulla vitae, euismod suscipit lorem. Nam ac vehicula ipsum. Nulla elementum neque eu ante interdum, quis fermentum odio accumsan. Pellentesque consequat sed leo in posuere. Proin convallis efficitur leo, in ultricies magna accumsan bibendum. Integer venenatis tincidunt turpis, nec ornare neque auctor quis. Integer eget est justo.
            Etiam cursus dapibus mi eget luctus. Nulla at tincidunt massa. Ut rhoncus dui sed enim tempor fermentum. In sem est, condimentum nec maximus a, pretium id lacus. Curabitur nec ipsum vel arcu mattis iaculis ac in velit. Nullam vel nunc erat. Phasellus scelerisque dignissim lacus sed fringilla. Nam congue dui a velit semper, in dictum lectus finibus. Etiam mi mi, porta sit amet orci id, pellentesque feugiat tortor. Etiam tortor ligula, consequat iaculis bibendum vitae, tristique a eros. Quisque eu erat et urna vehicula gravida. Etiam eget semper massa. Integer varius justo ante, ac fringilla magna blandit non. Sed elementum ligula odio, sit amet cursus nisi suscipit non.
            Sed fermentum non ante sit amet sodales. Phasellus placerat turpis id mattis rhoncus. Aliquam erat volutpat. Aliquam blandit libero sit amet lorem efficitur, in elementum sapien consequat. Praesent et lectus erat. Nunc posuere lacinia tortor, vel rutrum quam maximus eget. Ut et ex ac purus euismod vehicula. Fusce in risus arcu. Etiam vel risus non dolor fringilla viverra. Nullam id sollicitudin magna, non fermentum dolor. Nulla facilisi.
            Fusce bibendum lectus sed fringilla finibus. Ut faucibus faucibus turpis, ut accumsan lorem faucibus sit amet. Pellentesque varius sodales nibh, eget consectetur nisi finibus tincidunt. Integer pulvinar convallis mauris ut mollis. Phasellus ut imperdiet est. Etiam interdum ornare lacus ut vulputate. Mauris vulputate volutpat risus in ultricies. Sed tincidunt nibh vel urna ultricies, at vehicula nunc pellentesque. Duis dictum sagittis mi convallis feugiat.
            Suspendisse suscipit imperdiet quam, in finibus dui laoreet sit amet. Pellentesque sapien elit, hendrerit ut eros vel, pellentesque tincidunt magna. Mauris ac eros purus. Sed sagittis mi sit amet sodales euismod. Curabitur tempus, leo at mattis ultricies, ex urna sodales nunc, ut elementum quam libero vitae dui. Phasellus faucibus arcu eget elit imperdiet elementum. Fusce vel leo pulvinar, interdum enim vel, posuere enim. Maecenas facilisis ullamcorper ultricies. Ut ultrices faucibus tellus, id condimentum enim auctor non. Donec erat risus, venenatis et molestie id, feugiat a ipsum. Vivamus venenatis nibh neque, non aliquam odio interdum sit amet. Phasellus eu ipsum accumsan, mattis elit et, tincidunt ante. Phasellus nec eros pretium, malesuada nisi vitae, interdum mi. Nullam tincidunt rutrum bibendum. Integer id sollicitudin velit. Donec a vehicula metus.
            Curabitur condimentum bibendum mi eu consequat. Mauris fermentum urna vel tincidunt posuere. Interdum et malesuada fames ac ante ipsum primis in faucibus. Morbi dolor nulla, luctus in felis vitae, rutrum laoreet risus. Cras lacinia vitae ipsum quis elementum. Nunc ante magna, egestas eu ultrices lacinia, euismod nec odio. Maecenas mattis tortor at augue ultricies, at pulvinar quam rhoncus. Quisque sed sollicitudin metus. Etiam pellentesque ligula id nisl bibendum sodales. Aliquam ultricies quam lorem. Cras id mollis enim, quis euismod lorem. In pulvinar mi eget nulla rutrum, eu maximus eros sodales. Nulla posuere ex ac justo eleifend pharetra. Nunc malesuada urna at eleifend maximus. Sed at lacus ac ipsum facilisis vulputate eget non mi. Nulla eleifend, arcu et aliquam aliquam, augue nisi interdum velit, eget elementum ex ante nec purus.
            In congue metus eget nibh laoreet, non pellentesque ex lacinia. Nunc eleifend vitae arcu nec ultricies. Pellentesque euismod, dui eget fermentum elementum, nisi leo condimentum risus, vel euismod lectus massa quis odio. Vivamus id est eu erat iaculis posuere sit amet et arcu. Integer id dapibus purus. Quisque in lacus mi. Curabitur faucibus convallis lacus, in elementum orci blandit vel. Donec turpis augue, scelerisque nec fringilla vel, fringilla non quam. Donec arcu ipsum, fermentum eu metus id, luctus varius ligula. Aenean tempus nulla in leo aliquet, in placerat eros consequat. Vivamus eget faucibus est, non luctus odio. Pellentesque ac dui sit amet nulla feugiat lacinia in quis tortor. Sed convallis, magna vitae pellentesque scelerisque, ligula risus fermentum dui, vitae convallis neque augue at sapien. Nam ac faucibus lorem, vitae semper eros. Maecenas porta convallis turpis et aliquet.
            Nunc posuere libero non erat varius dapibus. Nulla facilisi. Ut semper sollicitudin mi id finibus. Maecenas vel scelerisque neque. Etiam condimentum hendrerit lectus ac posuere. In suscipit, quam eu vehicula facilisis, elit metus efficitur risus, eu faucibus orci felis vitae quam. Etiam id ante a risus porttitor dignissim. Fusce a tellus et ante rhoncus tempus eu eget mauris. Duis fringilla mollis eros id aliquam. Vivamus placerat tincidunt nulla, vitae blandit ante mollis id. Etiam imperdiet blandit mi eu ornare. Morbi hendrerit efficitur suscipit. Duis eleifend leo nibh, sed mattis arcu viverra a. Duis eget libero lorem.
            Nulla consequat sapien fringilla ultrices volutpat. Suspendisse eget ultrices metus. Pellentesque a volutpat sapien. Vivamus commodo congue fermentum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent sed purus mattis, faucibus dui ut, fringilla libero. Cras suscipit ante et est gravida bibendum. Fusce sed mattis libero. Fusce dapibus id nunc vel ultricies. Donec viverra volutpat pretium. Suspendisse enim augue, viverra non tortor et, maximus consectetur neque. Duis eget arcu in nulla aliquet fermentum. Nulla elementum, felis suscipit ultrices laoreet, nisl ipsum lobortis dui, mollis mollis tellus erat vitae tellus. Curabitur nec nibh a ipsum egestas pretium vel vel odio. Aenean ex urna, vehicula vel arcu eu, fringilla venenatis diam. Aenean vel diam et tortor malesuada viverra.
            """

    def "Checks if string is null or empty"() {
        expect:
        StringUtils.isNullOrEmpty(string) == expected

        where:
        string | expected
        null   | true
        ''     | true
        ""     | true
        """""" | true
        " "    | false
        "null" | false
    }

    def "If string is null or empty, returns the other string"() {
        expect:
        StringUtils.ifNullOrEmpty(string, defaultValue as String) == expected

        where:
        string | defaultValue || expected
        null   | "default"    || "default"
        ''     | ""           || ""
        ""     | null         || null
        """""" | " "          || " "
        " "    | null         || " "
        "null" | ""           || "null"
    }

    def "If string is null or empty, supplier returns the other string"() {
        expect:
        StringUtils.ifNullOrEmpty(string, supplier as Supplier) == expected

        where:
        string | supplier      || expected
        null   | { "default" } || "default"
        ''     | { "" }        || ""
        ""     | { null }      || null
        """""" | { " " }       || " "
        " "    | { null }      || " "
        "null" | { "" }        || "null"
    }

    def "Checks if string is null or blank"() {
        expect:
        StringUtils.isNullOrBlank(string) == expected

        where:
        string | expected
        null   | true
        ''     | true
        ""     | true
        """""" | true
        " "    | true
        " A"   | false
        "A "   | false
        " A "  | false
        "null" | false
    }

    def "If string is null or blank, returns the other string"() {
        expect:
        StringUtils.ifNullOrBlank(string, defaultValue as String) == expected

        where:
        string | defaultValue || expected
        null   | "default"    || "default"
        ''     | ""           || ""
        ""     | null         || null
        """""" | " "          || " "
        " "    | null         || null
        "null" | ""           || "null"
    }

    def "If string is null or blank, supplier returns the other string"() {
        expect:
        StringUtils.ifNullOrBlank(string, supplier as Supplier) == expected

        where:
        string | supplier      || expected
        null   | { "default" } || "default"
        ''     | { "" }        || ""
        ""     | { null }      || null
        """""" | { " " }       || " "
        " "    | { null }      || null
        "null" | { "" }        || "null"
    }

    def "Checks if criterial string is equal to other strings"() {
        expect:
        StringUtils.anyEquals(criterion, strings) == expected

        where:
        criterion | strings            || expected
        null      | [null]             || true
        ""        | [null, ""]         || true
        " c"      | ["a", "B", " c"]   || true
        null      | null               || false
        null      | []                 || false
        null      | ["null"]           || false
        ""        | []                 || false
        "AB"      | ['ab', "aB", "Ab"] || false
    }

    def "Checks if criterial string contains other strings"() {
        expect:
        StringUtils.anyContains(criterion, strings) == expected

        where:
        criterion | strings            || expected
        ""        | [null, ""]         || true
        " c"      | ["a", "B", "c"]    || true
        "Abs"     | ['ab', "aB", "Ab"] || true
        null      | null               || false
        null      | []                 || false
        null      | [null]             || false
        null      | ["null"]           || false
        ""        | null               || false
        ""        | []                 || false
        "A"       | ['ab', "aB", "Ab"] || false
    }

    def "Checks if string is numeric"() {
        expect:
        StringUtils.isNumeric(string) == expected

        where:
        string    | expected
        "0123"    | true
        " 0123"   | false
        "0123 "   | false
        " 0123 "  | false
        "-8"      | false
        "--1"     | false
        "+57"     | false
        "123,456" | false
        "alpha"   | false
        " "       | false
        ""        | false
        null      | false
    }

    def "Adds padding before the string"() {
        expect:
        StringUtils.padStart(len, origin) == expected0
        StringUtils.padStart(len, origin, appendix) == expected1

        where:
        origin          | len                 | appendix || expected0 | expected1
        "12"            | 3                   | "0"      || " 12"     | "012"
        "9781911223139" | origin.length()     | "-"      || origin    | origin
        "111"           | origin.length() + 1 | "10-"    || " 111"    | "10-111"
        "111"           | origin.length() + 2 | "10-"    || "  111"   | "10-10-111"
        "111"           | origin.length() + 3 | "10-"    || "   111"  | "10-10-10-111"
        "20210101"      | 0                   | ""       || origin    | origin
        "19991231"      | -1                  | null     || origin    | origin
    }

    def "Adds padding after the string"() {
        expect:
        StringUtils.padEnd(len, origin) == expected0
        StringUtils.padEnd(len, origin, appendix) == expected1

        where:
        origin          | len                 | appendix || expected0  | expected1
        "0304"          | 8                   | "0"      || "0304    " | "03040000"
        "9781911223139" | origin.length()     | "-"      || origin     | origin
        "111"           | origin.length() + 1 | "-10"    || "111 "     | "111-10"
        "111"           | origin.length() + 2 | "-10"    || "111  "    | "111-10-10"
        "111"           | origin.length() + 3 | "-10"    || "111   "   | "111-10-10-10"
        "20210101"      | 0                   | ""       || origin     | origin
        "19991231"      | -1                  | null     || origin     | origin
    }

    def "How many times the keyword is in the text?"() {
        given:
        def keyword = " "

        when:
        def count = StringUtils.countOf(LOREM_IPSUM, keyword)

        then:
        count == LOREM_IPSUM.length() - LOREM_IPSUM.replace(keyword, "").length()
    }

    def "Reverses each character's position"() {
        when:
        def reversed = StringUtils.reverse text as String

        then:
        char[] chars = text.toCharArray()
        char[] expected = new char[chars.length]
        for (int i = 0; i < chars.length; i++) {
            expected[i] = chars[chars.length - (i + 1)]
        }

        reversed == new String(expected)

        where:
        text << Arrays.stream(LOREM_IPSUM.split(" "))
                .map(String::trim).filter(it -> it.length() > 0).collect(toList())
    }

    def "Replaces the last string with other string"() {
        when:
        def actual = StringUtils.replaceLast(text, regex, replacement)

        then:
        actual == expected

        where:
        text               | regex  | replacement || expected
        "1?2?3?4?5?"       | "\\?"  | " "         || "1?2?3?4?5 "
        "ABC%DEF%GHI"      | "%"    | "-"         || "ABC%DEF-GHI"
        "alpha.beta.gamma" | "a\\." | "/"         || "alpha.bet/gamma"
        "-|=|-|=|-|=|-"    | "\\|*" | "\\\\|/"    || text + "\\|/"
        "-|=|-|=|-|=|-"    | "\\|+" | "\\\\|/"    || "-|=|-|=|-|=\\|/-"
    }

    @Unroll("StringUtils.formatComma(#num) == '#expected'")
    def "Puts a comma on every three digits"() {
        when:
        def actual = StringUtils.formatComma num

        then:
        actual == expected

        where:
        num             | expected
        "-1034784621.0" | "-1,034,784,621"
        -48450          | "-48,450"
        "-51.2"         | "-51.2"
        0               | "0"
        "102"           | "102"
        8975.1          | "8,975.1"
        "1034784621.00" | "1,034,784,621"
    }

    @Unroll("{1: '#first', 2: '#second', 3: '#third'}")
    def "Finds with groups"() {
        given:
        def pattern = '^(.+)_(.+) - ([^-]+?)( \\[COMPLETE])?$'

        when:
        def result = StringUtils.find(src, pattern, 0)
        def resultMap = StringUtils.find(src, pattern, 0, 1, 2, 3)

        then:
        result == src
        resultMap.get(1) == first
        resultMap.get(2) == second
        resultMap.get(3) == third

        where:
        src                                                          || first | second                   | third
        "CO_Felix Nelly(Loplop) - CTK [COMPLETE]"                    || "CO"  | "Felix Nelly(Loplop)"    | "CTK"
        "L_What Does the Fox Say? - Team_Gaji"                       || "L"   | "What Does the Fox Say?" | "Team_Gaji"
        "ST_Chief - Final - Jung Kiyoung, Baek Seunghoon [COMPLETE]" || "ST"  | "Chief - Final"          | "Jung Kiyoung, Baek Seunghoon"
        "L_Barber Shop Quartet - JIM, Nexcube, Bolero [COMPLETE]"    || "L"   | "Barber Shop Quartet"    | "JIM, Nexcube, Bolero"
    }

    @Unroll("StringUtils.chop('#input') == '#expected'")
    def "Removes the last character from the string"() {
        when:
        def chopped = StringUtils.chop input

        then:
        chopped == expected

        where:
        input         | expected
        ""            | ""
        "lorem"       | "lore"
        "ipsum"       | "ipsu"
        "is"          | "i"
        "simply"      | "simpl"
        "dummy"       | "dumm"
        "text"        | "tex"
        "of"          | "o"
        "the"         | "th"
        "printing"    | "printin"
        "and"         | "an"
        "typesetting" | "typesettin"
        "industry"    | "industr"
    }

    def "Gets the last character from string"() {
        expect:
        StringUtils.getLastString(string) == expected

        where:
        string   | expected
        ""       | ""
        "a "     | " "
        "null"   | "l"
        "alpha." | "."
    }

}
