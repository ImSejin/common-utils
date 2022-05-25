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

package io.github.imsejin.common.security.crypto.aes;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.security.crypto.Crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;

/**
 * Implementation of {@link Crypto} using algorithm AES.
 */
abstract class AES implements Crypto {

    /**
     * Algorithm for cipher.
     * <p>
     * {@code AES/CBC/PKCS5Padding} is vulnerable to being decrypted.
     * {@code AES/ECB/PKCS5Padding} is equal to {@code AES}.
     */
    private static final String ALGORITHM = "AES";

    private final Key key;

    private final Charset charset;

    protected AES(String key, int length, Charset charset) {
        Asserts.that(key)
                .as("{0}.key must have {1} characters, but it isn't: '{2}'", getClass().getSimpleName(), length, key)
                .isNotNull().hasText().hasLengthOf(length);
        Asserts.that(charset)
                .as("{0}.charset is not allowed to be null, but it is", getClass().getSimpleName(), charset)
                .isNotNull();

        byte[] bytes = key.getBytes(charset);

        this.key = new SecretKeySpec(bytes, ALGORITHM);
        this.charset = charset;
    }

    @Override
    public String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.key);

            byte[] encrypted = cipher.doFinal(text.getBytes(this.charset));

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, this.key);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted, this.charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getKey() {
        return new String(this.key.getEncoded(), this.charset);
    }

}
