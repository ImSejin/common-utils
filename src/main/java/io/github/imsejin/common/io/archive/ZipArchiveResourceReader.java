/*
 * Copyright 2025 Sejin Im
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

package io.github.imsejin.common.io.archive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.io.ZipResource;

public class ZipArchiveResourceReader implements ArchiveResourceReader {

    private final Predicate<ZipArchiveEntry> filter;

    private final Charset charset;

    public ZipArchiveResourceReader() {
        this(entry -> true, StandardCharsets.UTF_8);
    }

    public ZipArchiveResourceReader(Predicate<ZipArchiveEntry> filter) {
        this(filter, StandardCharsets.UTF_8);
    }

    public ZipArchiveResourceReader(Predicate<ZipArchiveEntry> filter, Charset charset) {
        this.filter = filter;
        this.charset = charset;
    }

    @Override
    public List<Resource> read(InputStream in, Map<String, String> props) throws IOException {
        try (ZipArchiveInputStream zis = new ZipArchiveInputStream(in, this.charset.name())) {
            List<Resource> resources = new ArrayList<>();

            zis.forEach(entry -> {
                Resource resource = toResource(entry, zis);
                if (resource != null) {
                    resources.add(resource);
                }
            });

            return Collections.unmodifiableList(resources);
        }
    }

    // -------------------------------------------------------------------------------------------------

    private Resource toResource(ZipArchiveEntry entry, ZipArchiveInputStream in) throws IOException {
        if (!this.filter.test(entry)) {
            return null;
        }

        if (entry.isDirectory()) {
            return new ZipResource(entry, new byte[0]);
        }

        byte[] bytes;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int offset;
            while ((offset = in.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }

            bytes = out.toByteArray();
        }

        return new ZipResource(entry, bytes);
    }

}
