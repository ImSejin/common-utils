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

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

class JsonUtilsSpec extends Specification {

    @TempDir
    private Path tempDir

    private static final def JSON = """
        {
            "menu": {
                "id": "file",
                "value": "File",
                "popup": {
                    "menuItems": [
                        {"value": "New", "onclick": "CreateNewDoc()"},
                        {"value": "Open", "onclick": "OpenDoc()"},
                        {"value": "Close", "onclick": "CloseDoc()"}
                    ]
                }
            }
        }
    """

    def "Reads JSON from URL"() {
        given:
        def path = Files.createTempFile(tempDir, "temp-file-", ".json")
        Files.write(path.toRealPath(), JSON.getBytes(StandardCharsets.UTF_8))
        def url = path.toRealPath().toUri().toURL()

        when:
        def json = JsonUtils.readJsonFromUrl(url)

        then:
        def menu = json["menu"] as JsonObject
        menu.keySet() == ["id", "value", "popup"].toSet()

        def menuItems = menu["popup"]["menuItems"] as List<JsonObject>
        def onclicks = menuItems.collect { it.get("onclick").asString }
        onclicks == ["CreateNewDoc()", "OpenDoc()", "CloseDoc()"]

        cleanup:
        path.toFile().deleteOnExit()
    }

    def "Converts json object to java object"() {
        given:
        def jsonObject = JsonParser.parseString(JSON).asJsonObject
        def jsonArray = jsonObject["menu"]["popup"]["menuItems"] as JsonArray

        when:
        def menuItems = JsonUtils.toObject(jsonArray[0].toString(), MenuItem)

        then:
        menuItems.value == "New"
        menuItems.onclick == "CreateNewDoc()"
    }

    def "Converts json array to java list"() {
        given:
        def jsonObject = JsonParser.parseString(JSON).asJsonObject
        def jsonArray = jsonObject["menu"]["popup"]["menuItems"] as JsonArray

        when:
        def list = JsonUtils.toList(jsonArray, MenuItem)

        then:
        list[0].value == "New"
        list[0].onclick == "CreateNewDoc()"
        list[1].value == "Open"
        list[1].onclick == "OpenDoc()"
        list[2].value == "Close"
        list[2].onclick == "CloseDoc()"
    }

    // -------------------------------------------------------------------------------------------------

    static class MenuItem {
        String value
        String onclick
    }

}
