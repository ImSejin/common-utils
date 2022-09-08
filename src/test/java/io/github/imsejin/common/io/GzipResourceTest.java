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

package io.github.imsejin.common.io;

import io.github.imsejin.common.tool.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GzipResource")
class GzipResourceTest {

    @Test
    void test() {
        // given
        String fileName = "temp-file.log";
        int length = new Random().nextInt((int) Math.pow(2, 20));
        byte[] bytes = length == 0
                ? new byte[0]
                : new RandomString().nextString(length).getBytes(StandardCharsets.UTF_8);
        long modifiedTime = System.currentTimeMillis();

        // when
        GzipResource resource = new GzipResource(fileName, new ByteArrayInputStream(bytes),
                bytes.length, bytes.length / 2, modifiedTime);

        // then
        GzipResource expected = new GzipResource(fileName, new ByteArrayInputStream(bytes),
                bytes.length, bytes.length / 2, modifiedTime);
        assertThat(resource)
                .isNotNull()
                .isEqualTo(expected)
                .returns(fileName, Resource::getPath)
                .returns(fileName, Resource::getName)
                .returns((long) bytes.length, Resource::getSize)
                .returns(false, Resource::isDirectory)
                .returns((long) bytes.length / 2, GzipResource::getCompressedSize)
                .returns(Instant.ofEpochMilli(modifiedTime), GzipResource::getLastModifiedTime)
                .returns(String.format("%s(path=%s, name=%s, inputStream=%s, size=%d, directory=%s, compressedSize=%d, lastModifiedTime=%s)",
                        resource.getClass().getName(), resource.getPath(), resource.getName(),
                        resource.getInputStream(), resource.getSize(), resource.isDirectory(),
                        resource.getCompressedSize(), resource.getLastModifiedTime()),
                        GzipResource::toString);
        assertThat(resource.getInputStream())
                .isNotNull()
                .hasSameContentAs(new ByteArrayInputStream(bytes));
        assertThat(new HashSet<>(Arrays.asList(resource, expected)))
                .as("Test identity and equality")
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .doesNotHaveDuplicates();
    }

}
