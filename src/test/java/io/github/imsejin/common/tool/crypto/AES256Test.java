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

package io.github.imsejin.common.tool.crypto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class AES256Test {

    private static final String KEY = "a218571d289c4e79a383620e72dd2413";

    @ParameterizedTest
    @ValueSource(strings = {" ", ",", "\"", "a", "of"})
    void test0(String delimiter) {
        // given
        String origin = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
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
                "The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.";

        for (int length : new int[]{16, 24, 32}) {
            Crypto crypto = new AES256(KEY.substring(0, length));
            assertEncryptionAndDecryption(crypto, origin.split(delimiter));
        }
    }

    private static void assertEncryptionAndDecryption(Crypto crypto, String... texts) {
        for (String text : texts) {
            // when: 1
            String encrypted = crypto.encrypt(text);
            // then: 1
            assertThat(encrypted)
                    .as("Plaintext will be encrypted: '%s' => '%s'", text, encrypted)
                    .isNotNull().isNotBlank().isNotEqualTo(text)
                    .as("Same plaintext, same ciphertext: '%s' => ('%s' == '%s')", text, encrypted, crypto.encrypt(text))
                    .isEqualTo(crypto.encrypt(text));
            System.out.printf("ciphertext: %s%n", encrypted);

            // when: 2
            String decrypted = crypto.decrypt(encrypted);
            // then: 2
            assertThat(decrypted)
                    .as("Ciphertext will be decrypted: '%s' => '%s'", encrypted, decrypted)
                    .isNotNull().isNotBlank().isEqualTo(text);
        }
    }

}
