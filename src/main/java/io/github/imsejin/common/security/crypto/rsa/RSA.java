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

package io.github.imsejin.common.security.crypto.rsa;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.security.crypto.Crypto;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Implementation of {@link Crypto} using algorithm RSA.
 */
public class RSA implements Crypto {

    private static final String ALGORITHM = "RSA";

    private static final int DEFAULT_KEY_SIZE = 2048;

    private static final KeyFactory keyFactory;

    private final PublicKey publicKey;

    private final PrivateKey privateKey;

    static {
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns an instance of {@link RSA} with randomly generated
     * public key and private key as a pair.
     */
    public RSA() {
        KeyPair keyPair = generateKeyPair(DEFAULT_KEY_SIZE);
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    /**
     * Returns an instance of {@link RSA} with the given
     * public key and private key.
     * <p>
     * These key might be not pair.
     */
    public RSA(String publicKey, String privateKey) {
        this.publicKey = getPublicKeyFrom(publicKey);
        this.privateKey = getPrivateKeyFrom(privateKey);
    }

    public static KeyPair generateKeyPair(int keySize) {
        Asserts.that(keySize)
                .as("KeyPair.keySize allows 512 ~ 65536, but it isn't: {0}", keySize)
                .isBetween(512, 64 * 1024);

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
            generator.initialize(keySize, new SecureRandom());

            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey getPublicKeyFrom(String s) {
        byte[] bytes = Base64.getDecoder().decode(s);
        KeySpec keySpec = new X509EncodedKeySpec(bytes);

        try {
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getPrivateKeyFrom(String s) {
        byte[] bytes = Base64.getDecoder().decode(s);
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);

        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String text) {
        return encryptWithPrivateKey(text);
    }

    public String encryptWithPublicKey(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);

            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            byte[] encrypted = cipher.doFinal(bytes);

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptWithPrivateKey(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.privateKey);

            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            byte[] encrypted = cipher.doFinal(bytes);

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        return decryptWithPublicKey(cipherText);
    }

    public String decryptWithPublicKey(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, this.publicKey);

            byte[] bytes = cipherText.getBytes(StandardCharsets.UTF_8);
            byte[] decoded = Base64.getDecoder().decode(bytes);
            byte[] decrypted = cipher.doFinal(decoded);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String decryptWithPrivateKey(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);

            byte[] bytes = cipherText.getBytes(StandardCharsets.UTF_8);
            byte[] decoded = Base64.getDecoder().decode(bytes);
            byte[] decrypted = cipher.doFinal(decoded);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getKey() {
        return getPublicKey();
    }

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
    }

    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(this.privateKey.getEncoded());
    }

}
