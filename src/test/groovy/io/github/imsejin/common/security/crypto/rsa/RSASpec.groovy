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

package io.github.imsejin.common.security.crypto.rsa

import spock.lang.Specification

import javax.crypto.BadPaddingException

class RSASpec extends Specification {

    def "Encrypts plaintext with public key"() {
        given:
        def plaintext = UUID.randomUUID().toString()
        def rsa = new RSA()

        when:
        def encrypted = rsa.encryptWithPublicKey plaintext

        then:
        encrypted != plaintext
        encrypted != rsa.encryptWithPrivateKey(plaintext)
        encrypted != rsa.encrypt(plaintext)
    }

    def "Encrypts plaintext with private key"() {
        given:
        def plaintext = UUID.randomUUID().toString()
        def rsa = new RSA()

        when:
        def encrypted = rsa.encryptWithPrivateKey plaintext

        then:
        encrypted != plaintext
        encrypted != rsa.encryptWithPublicKey(plaintext)
        encrypted == rsa.encrypt(plaintext)
    }

    def "Decrypts cipher text with public key"() {
        given:
        def plaintext = UUID.randomUUID().toString()
        def rsa = new RSA()

        when:
        def encrypted = rsa.encryptWithPrivateKey plaintext
        def decrypted = rsa.decryptWithPublicKey encrypted

        then:
        decrypted != encrypted
        decrypted == rsa.decrypt(encrypted)
        decrypted == plaintext
    }

    def "Decrypts cipher text with private key"() {
        given:
        def plaintext = UUID.randomUUID().toString()
        def rsa = new RSA()

        when:
        def encrypted = rsa.encryptWithPublicKey plaintext
        def decrypted = rsa.decryptWithPrivateKey encrypted

        then:
        decrypted != encrypted
        decrypted == plaintext
    }

    def "Fails to decrypt cipher text with unmatched pair key"() {
        given:
        def plaintext = UUID.randomUUID().toString()
        def rsa = new RSA()

        when: "try to decrypt cipher text with the same key that is public"
        rsa.decryptWithPublicKey rsa.encryptWithPublicKey(plaintext)

        then:
        def e0 = thrown RuntimeException
        e0.cause.class == BadPaddingException
        e0.cause.message == "Decryption error"

        when: "try to decrypt cipher text with the same key that is private"
        rsa.decryptWithPrivateKey rsa.encryptWithPrivateKey(plaintext)

        then:
        def e1 = thrown RuntimeException
        e1.cause.class == BadPaddingException
        e1.cause.message == "Decryption error"
    }

    def "Check public key and private key"() {
        given:
        def keyPair = RSA.generateKeyPair(512)
        def publicKey = Base64.encoder.encodeToString keyPair.public.encoded
        def privateKey = Base64.encoder.encodeToString keyPair.private.encoded

        when:
        def rsa = new RSA(publicKey, privateKey)

        then:
        rsa.key == publicKey
        rsa.publicKey == publicKey
        rsa.privateKey == privateKey
    }

}
