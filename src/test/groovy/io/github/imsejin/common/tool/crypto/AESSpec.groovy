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

package io.github.imsejin.common.tool.crypto

import io.github.imsejin.common.util.ReflectionUtils
import io.github.imsejin.common.util.StringUtils
import spock.lang.Specification

class AESSpec extends Specification {

    static final def TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
            "It has survived not only five centuries, but also the leap into electronic typesetting, " +
            "remaining essentially unchanged. It was popularised in the 1960s with the release of " +
            "Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing " +
            "software like Aldus PageMaker including versions of Lorem Ipsum. Contrary to popular belief, " +
            "Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature " +
            "from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at " +
            "Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, " +
            "from a Lorem Ipsum passage, and going through the cites of the word in classical literature, " +
            "discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of " +
            "\"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. " +
            "This book is a treatise on the theory of ethics, very popular during the Renaissance. " +
            "The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."

    def "encrypt"() {
        given:
        def keySource = UUID.randomUUID().toString().replace("-", "")
        def key = keySource.substring(0, length)
        def aes = ReflectionUtils.instantiate(impl, [String] as Class<?>[], [key] as Object[])

        when:
        def encrypted = aes.encrypt TEXT

        then:
        !StringUtils.isNullOrBlank(encrypted)
        encrypted != TEXT
        encrypted == aes.encrypt(TEXT)

        where:
        length | impl
        16     | AES128
        24     | AES192
        32     | AES256
    }

    def "decrypt"() {
        given:
        def keySource = UUID.randomUUID().toString().replace("-", "")
        def key = keySource.substring(0, length)
        def aes = ReflectionUtils.instantiate(impl, [String] as Class<?>[], [key] as Object[])
        def encrypted = aes.encrypt TEXT

        when:
        def decrypted = aes.decrypt encrypted

        then:
        encrypted != decrypted
        decrypted == TEXT

        where:
        length | impl
        16     | AES128
        24     | AES192
        32     | AES256
    }

    def "getKey"() {
        given:
        def keySource = UUID.randomUUID().toString().replace("-", "")
        def key = keySource.substring(0, length)
        def aes = ReflectionUtils.instantiate(impl, [String] as Class<?>[], [key] as Object[])

        expect:
        key == aes.key

        where:
        length | impl
        16     | AES128
        24     | AES192
        32     | AES256
    }

}
