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

import io.github.imsejin.common.assertion.Asserts;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * Implementation of {@link Crypto} using algorithm AES256.
 */
public class AES256 implements Crypto {

    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private final Key key;

    private final AlgorithmParameterSpec algParamSpec;

    private final Charset charset;

    public AES256(String key) {
        this(key, StandardCharsets.UTF_8);
    }

    public AES256(String key, Charset charset) {
        Asserts.that(key)
                .as("AES256.key must have 16 characters, but it isn't: '{0}'", key)
                .isNotNull().hasText().hasLengthOf(16);
        Asserts.that(charset)
                .as("AES256.charset is not allowed to be null, but it is", charset)
                .isNotNull();

        byte[] bytes = key.getBytes(charset);

        this.key = new SecretKeySpec(bytes, "AES");
        this.algParamSpec = new IvParameterSpec(bytes);
        this.charset = charset;
    }

    @Override
    public String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.key, this.algParamSpec);

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
            cipher.init(Cipher.DECRYPT_MODE, this.key, this.algParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted, this.charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
