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
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import io.github.imsejin.common.io.TarResource;
import io.github.imsejin.common.util.FilenameUtils;

public class TarResourceFinder extends ArchiveResourceFinder<TarResource, TarArchiveEntry, TarArchiveInputStream> {

    protected final boolean recursive;

    protected final Predicate<ArchiveEntry> filter;

    protected final Charset charset;

    public TarResourceFinder(boolean recursive) {
        this(recursive, entry -> true, StandardCharsets.UTF_8);
    }

    public TarResourceFinder(boolean recursive, Predicate<ArchiveEntry> filter) {
        this(recursive, filter, StandardCharsets.UTF_8);
    }

    public TarResourceFinder(boolean recursive, Predicate<ArchiveEntry> filter, Charset charset) {
        this.recursive = recursive;
        this.filter = filter;
        this.charset = charset;
    }

    @Override
    protected TarArchiveEntry getNextArchiveEntry(TarArchiveInputStream in) throws IOException {
        return in.getNextTarEntry();
    }

    @Override
    protected TarResource getArchiveResource(TarArchiveEntry entry, TarArchiveInputStream in) throws IOException {
        if (!this.filter.test(entry)) {
            return null;
        }

        String path = entry.getName();
        String name = FilenameUtils.getName(path);
        boolean directory = entry.isDirectory();
        long size = entry.getSize();
        long modifiedMilliTime = entry.getLastModifiedDate().getTime();

        if (directory) {
            return new TarResource(path, name, null, size, true, modifiedMilliTime);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[16384];
        int offset;
        while ((offset = in.read(buffer)) != -1) {
            out.write(buffer, 0, offset);
        }

        byte[] bytes = out.toByteArray();

        return new TarResource(path, name, new ByteArrayInputStream(bytes), size, false, modifiedMilliTime);
    }

    @Override
    protected TarArchiveInputStream getArchiveInputStream(InputStream in) throws IOException {
        return new TarArchiveInputStream(in, this.charset.name());
    }

}
