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
import io.github.imsejin.common.util.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TarResource")
class TarResourceTest {

    @Test
    void test() {
        // given
        String path = "usr/bin/temp-file.log";
        String fileName = FilenameUtils.getName(path);
        byte[] bytes = new RandomString().nextString(1, (int) Math.pow(2, 20)).getBytes(StandardCharsets.UTF_8);
        long modifiedTime = System.currentTimeMillis();

        // when
        TarResource resource = new TarResource(path, fileName, new ByteArrayInputStream(bytes),
                bytes.length, false, modifiedTime);

        // then
        TarResource expected = new TarResource(path, fileName, new ByteArrayInputStream(bytes),
                bytes.length, false, modifiedTime);
        assertThat(resource)
                .isNotNull()
                .isEqualTo(expected)
                .returns(path, Resource::getPath)
                .returns(fileName, Resource::getName)
                .returns((long) bytes.length, Resource::getSize)
                .returns(false, Resource::isDirectory)
                .returns(Instant.ofEpochMilli(modifiedTime), TarResource::getLastModifiedTime)
                .returns(String.format("%s(path=%s, name=%s, inputStream=%s, size=%d, directory=%s, lastModifiedTime=%s)",
                        resource.getClass().getName(), resource.getPath(), resource.getName(),
                        resource.getInputStream(), resource.getSize(), resource.isDirectory(),
                        resource.getLastModifiedTime()),
                        TarResource::toString);
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
