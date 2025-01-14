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
import java.io.InputStream;
import java.time.Instant;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import io.github.imsejin.common.util.FilenameUtils;

@Getter
@ToString
@EqualsAndHashCode
public class ZipResource implements Resource {

    private final String path;

    private final String name;

    private final Instant lastModifiedTime;

    private final long size;

    private final boolean directory;

    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final byte[] bytes;

    public ZipResource(ZipArchiveEntry entry, byte[] bytes) {
        this.path = entry.getName();
        this.name = FilenameUtils.getName(this.path);
        this.directory = entry.isDirectory();
        this.size = entry.getSize();
        this.lastModifiedTime = entry.getLastModifiedDate().toInstant();
        this.bytes = bytes;
    }

    @Override
    public InputStream getInputStream() {
        if (this.directory) {
            return null;
        }

        return new ByteArrayInputStream(this.bytes);
    }

}
