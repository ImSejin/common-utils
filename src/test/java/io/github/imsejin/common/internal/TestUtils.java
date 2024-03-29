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

package io.github.imsejin.common.internal;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;

public final class TestUtils {

    public static byte[] readAllBytes(InputStream in) {
        try (InputStream inputStream = in) {
            return IOUtils.readRange(inputStream, Integer.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
