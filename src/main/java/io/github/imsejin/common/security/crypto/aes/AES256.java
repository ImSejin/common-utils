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

import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.Subst;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of {@link AES} using algorithm AES256.
 */
public class AES256 extends AES {

    public AES256(@Subst("c05b07aae16341429539e261c8d0c6e6") @Pattern(".{32}") String key) {
        this(key, StandardCharsets.UTF_8);
    }

    public AES256(@Subst("c05b07aae16341429539e261c8d0c6e6") @Pattern(".{32}") String key, Charset charset) {
        super(key, 32, charset);
    }

}
