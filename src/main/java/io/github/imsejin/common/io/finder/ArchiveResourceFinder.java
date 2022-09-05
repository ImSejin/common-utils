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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.io.ArchiveResource;
import io.github.imsejin.common.io.Resource;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class ArchiveResourceFinder<
        R extends ArchiveResource,
        E extends ArchiveEntry,
        I extends ArchiveInputStream>
        implements ResourceFinder {

    @Override
    public List<Resource> getResources(Path path) {
        Asserts.that(path)
                .describedAs("Invalid path to find resources: {0}", path)
                .isNotNull()
                .describedAs("No such path exists: {0}", path)
                .predicate(Files::exists)
                .describedAs("It is not a regular file: {0}", path)
                .predicate(Files::isRegularFile)
                .describedAs("Cannot read file: {0}", path)
                .predicate(Files::isReadable);

        try (I in = getArchiveInputStream(Files.newInputStream(path))) {
            List<Resource> resources = new ArrayList<>();

            E entry;
            // java.nio.charset.MalformedInputException: Input length = 1
            // java.nio.charset.CharsetDecoder.decode
            while ((entry = getNextArchiveEntry(in)) != null) {
                R resource = getArchiveResource(entry, in);
                if (resource == null) continue;

                resources.add(resource);
            }

            return resources;

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to read tar file: " + path, e);
        }
    }

    protected abstract E getNextArchiveEntry(I in) throws IOException;

    @Nullable
    protected abstract R getArchiveResource(E entry, I in) throws IOException;

    protected abstract I getArchiveInputStream(InputStream in) throws IOException;

}
