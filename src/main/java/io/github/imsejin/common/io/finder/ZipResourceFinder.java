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

package io.github.imsejin.common.io.finder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import io.github.imsejin.common.io.ZipResource;
import io.github.imsejin.common.util.FilenameUtils;

public class ZipResourceFinder extends ArchiveResourceFinder<ZipResource, ZipArchiveEntry, ZipArchiveInputStream> {

    protected final boolean recursive;

    protected final Predicate<ArchiveEntry> filter;

    protected final Charset charset;

    public ZipResourceFinder(boolean recursive) {
        this(recursive, entry -> true, StandardCharsets.UTF_8);
    }

    public ZipResourceFinder(boolean recursive, Predicate<ArchiveEntry> filter) {
        this(recursive, filter, StandardCharsets.UTF_8);
    }

    public ZipResourceFinder(boolean recursive, Predicate<ArchiveEntry> filter, Charset charset) {
        this.recursive = recursive;
        this.filter = filter;
        this.charset = charset;
    }

    @Override
    protected ZipArchiveEntry getNextArchiveEntry(ZipArchiveInputStream in) throws IOException {
        return in.getNextZipEntry();
    }

    @Override
    protected ZipResource getArchiveResource(ZipArchiveEntry entry, ZipArchiveInputStream in) throws IOException {
        if (!this.filter.test(entry)) {
            return null;
        }

        String path = entry.getName();
        String name = FilenameUtils.getName(path);
        boolean directory = entry.isDirectory();
        long size = entry.getSize();
        long modifiedMilliTime = entry.getLastModifiedDate().getTime();

        if (directory) {
            return new ZipResource(path, name, null, size, true, modifiedMilliTime);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[16384];
        int offset;
        while ((offset = in.read(buffer)) != -1) {
            out.write(buffer, 0, offset);
        }

        byte[] bytes = out.toByteArray();

        return new ZipResource(path, name, new ByteArrayInputStream(bytes), size, false, modifiedMilliTime);
    }

    @Override
    protected ZipArchiveInputStream getArchiveInputStream(InputStream in) {
        return new ZipArchiveInputStream(in, this.charset.name());
    }

}
