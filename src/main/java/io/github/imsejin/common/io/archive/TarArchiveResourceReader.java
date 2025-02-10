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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.io.TarResource;

public class TarArchiveResourceReader implements ArchiveResourceReader {

    private final ArchiveResourceReader reader;

    private final Predicate<TarArchiveEntry> filter;

    private final Charset charset;

    public TarArchiveResourceReader() {
        this(new BypassArchiveResourceReader(), entry -> true, StandardCharsets.UTF_8);
    }

    public TarArchiveResourceReader(ArchiveResourceReader reader) {
        this(reader, entry -> true, StandardCharsets.UTF_8);
    }

    public TarArchiveResourceReader(Predicate<TarArchiveEntry> filter) {
        this(new BypassArchiveResourceReader(), filter, StandardCharsets.UTF_8);
    }

    public TarArchiveResourceReader(ArchiveResourceReader reader, Predicate<TarArchiveEntry> filter, Charset charset) {
        this.reader = Objects.requireNonNull(reader, "TarArchiveResourceReader.reader cannot be null");
        this.filter = Objects.requireNonNull(filter, "TarArchiveResourceReader.filter cannot be null");
        this.charset = Objects.requireNonNull(charset, "TarArchiveResourceReader.charset cannot be null");
    }

    @Override
    public List<Resource> read(InputStream in, Map<String, String> props) throws IOException {
        Resource compressed = this.reader.read(in, props).stream().findFirst().orElse(null);
        if (compressed != null) {
            in = compressed.getInputStream();
        }

        try (TarArchiveInputStream tis = new TarArchiveInputStream(in, this.charset.name())) {
            List<Resource> resources = new ArrayList<>();

            tis.forEach(entry -> {
                Resource resource = toResource(entry, tis);
                if (resource != null) {
                    resources.add(resource);
                }
            });

            return Collections.unmodifiableList(resources);
        }
    }

    // -------------------------------------------------------------------------------------------------

    private Resource toResource(TarArchiveEntry entry, TarArchiveInputStream in) throws IOException {
        if (!this.filter.test(entry)) {
            return null;
        }

        if (entry.isDirectory()) {
            return new TarResource(entry, new byte[0]);
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

        return new TarResource(entry, bytes);
    }

    private static class BypassArchiveResourceReader implements ArchiveResourceReader {

        @Override
        public List<Resource> read(InputStream in, Map<String, String> props) throws IOException {
            Resource resource = new Resource() {
                @Override
                public String getPath() {
                    return "";
                }

                @Override
                public String getName() {
                    return "";
                }

                @Override
                public Instant getLastModifiedTime() {
                    return Instant.now();
                }

                @Override
                public InputStream getInputStream() {
                    // Return bypass.
                    return in;
                }

                @Override
                public long getSize() {
                    return 0;
                }

                @Override
                public boolean isDirectory() {
                    return false;
                }
            };

            return Collections.singletonList(resource);
        }

    }

}
