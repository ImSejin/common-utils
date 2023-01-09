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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.FileSystemSource;
import org.junit.jupiter.api.extension.Memory;

import io.github.imsejin.common.tool.RandomString;

import static org.assertj.core.api.Assertions.*;

@FileSystemSource
@DisplayName("DiskFileResource")
class DiskFileResourceTest {

    @Test
    void test(@Memory FileSystem fileSystem) throws IOException {
        // given
        Path filePath = fileSystem.getPath("/", "temp-text.txt");
        Files.createFile(filePath);
        byte[] bytes = new RandomString().nextString(1, 1024).getBytes(StandardCharsets.UTF_8);
        Files.write(filePath, bytes);

        // when
        DiskFileResource resource = DiskFileResource.from(filePath);

        // then
        DiskFileResource expected = DiskFileResource.from(filePath);
        assertThat(resource)
                .isNotNull()
                .isEqualTo(expected)
                .returns(filePath.toString(), Resource::getPath)
                .returns("temp-text.txt", Resource::getName)
                .returns((long) bytes.length, Resource::getSize)
                .returns(filePath, DiskFileResource::getRealPath)
                .returns(String.format("%s(path=%s, name=%s, inputStream=%s, size=%d, directory=%s)",
                                resource.getClass().getName(), resource.getPath(), resource.getName(),
                                null, resource.getSize(), resource.isDirectory()),
                        DiskFileResource::toString);
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
