/*
 * Copyright 2023 Sejin Im
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
import spock.lang.TempDir

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

class IniUtilsSpec extends Specification {

    @TempDir
    private Path tempPath

    def "Reads a section"() {
        given:
        def file = new File(tempPath.toFile(), "test.ini")
        IniUtils.write(file, data)

        when:
        def section = IniUtils.readSection(file, sectionName)

        then:
        section == expected

        where:
        sectionName | data                                                       || expected
        "alpha"     | [(sectionName): [a: "1", b: "A"], beta: [a: "2", b: "B"]]  || [a: "1", b: "A"]
        "beta"      | [alpha: [a: "1", b: "A"], (sectionName): [a: "2", b: "B"]] || [a: "2", b: "B"]
        "gamma"     | [alpha: [a: "1", b: "A"], beta: [a: "2", b: "B"]]          || null
    }

    def "Reads the value by section and name"() {
        given:
        def file = new File(tempPath.toFile(), "test.ini")
        IniUtils.write(file, data)

        when:
        def value = IniUtils.readValue(file, sectionName, name)

        then:
        value == expected

        where:
        sectionName | name | data                                                       || expected
        "alpha"     | "a"  | [(sectionName): [a: "1", b: "A"], beta: [a: "2", b: "B"]]  || "1"
        "beta"      | "b"  | [alpha: [a: "1", b: "A"], (sectionName): [a: "2", b: "B"]] || "B"
        "alpha"     | "c"  | [(sectionName): [a: "1", b: "A"], beta: [a: "2", b: "B"]]  || null
        "gamma"     | "a"  | [alpha: [a: "1", b: "A"], beta: [a: "2", b: "B"]]          || null
    }

    def "Reads the values by section"() {
        given:
        def file = new File(tempPath.toFile(), "test.ini")
        IniUtils.write(file, data)

        when:
        def values = IniUtils.readValues(file, sectionName)

        then:
        values == expected

        where:
        sectionName | data                                                       || expected
        "alpha"     | [(sectionName): [a: "1", b: "A"], beta: [a: "2", b: "B"]]  || ["1", "A"]
        "beta"      | [alpha: [a: "1", b: "A"], (sectionName): [a: "2", b: "B"]] || ["2", "B"]
        "gamma"     | [alpha: [a: "1", b: "A"], beta: [a: "2", b: "B"]]          || null
    }

    def "Reads and gets an ini instance"() {
        given:
        def file = new File(tempPath.toFile(), "test.ini")
        IniUtils.write(file, data)

        when:
        def ini = IniUtils.read(file)

        then:
        ini == data

        where:
        data << [
                [alpha: [a: "1", b: "A"], beta: [a: "2", b: "B"]],
                [alpha: [a: "1"], gamma: [b: "B"]],
                [gamma: [a: "1", c: "C"], delta: [c: "4", d: "D"]],
        ]
    }

    def "Writes data into ini file"() {
        given:
        def file = new File(tempPath.toFile(), "test.ini")

        when:
        IniUtils.write(file, data)

        then:
        file.exists()

        and:
        def lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)
        data.keySet().every { lines.contains("[$it]".toString()) }

        where:
        data << [
                [alpha: [a: "1", b: "A"], beta: [a: "2", b: "B"]],
                [alpha: [a: "1"], gamma: [b: "B"]],
                [gamma: [a: "1", c: "C"], delta: [c: "4", d: "D"]],
        ]
    }

}
