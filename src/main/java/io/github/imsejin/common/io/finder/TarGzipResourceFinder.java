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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

public class TarGzipResourceFinder extends TarResourceFinder {

    public TarGzipResourceFinder(boolean recursive) {
        this(recursive, entry -> true, StandardCharsets.UTF_8);
    }

    public TarGzipResourceFinder(boolean recursive, Predicate<ArchiveEntry> filter) {
        this(recursive, filter, StandardCharsets.UTF_8);
    }

    public TarGzipResourceFinder(boolean recursive, Predicate<ArchiveEntry> filter, Charset charset) {
        super(recursive, filter, charset);
    }

    @Override
    protected TarArchiveInputStream getArchiveInputStream(InputStream in) throws IOException {
        return super.getArchiveInputStream(new GzipCompressorInputStream(in));
    }

}
