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

package io.github.imsejin.common.io.finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.io.archive.ArchiveResourceReader;
import io.github.imsejin.common.io.archive.GzipArchiveResourceReader;
import io.github.imsejin.common.io.archive.TarArchiveResourceReader;
import io.github.imsejin.common.io.archive.ZipArchiveResourceReader;
import io.github.imsejin.common.util.FilenameUtils;

@RequiredArgsConstructor
public class RecursiveResourceFinder implements ResourceFinder {

    private final ArchiveResourceReaderResolver resolver;

    public RecursiveResourceFinder() {
        this(new ExtensionBasedArchiveResourceReaderResolver());
    }

    @Override
    public List<Resource> getResources(Path path) {
        Asserts.that(path)
                .describedAs("Invalid path to find resources: {0}", path)
                .isNotNull()
                .describedAs("No such path exists: {0}", path)
                .exists()
                .describedAs("Cannot read: {0}", path)
                .is(Files::isReadable);

        List<Resource> resources = new FileResourceFinder(true).getResources(path);
        resources = findResources(resources);

        return Collections.unmodifiableList(resources);
    }

    // -------------------------------------------------------------------------------------------------

    private List<Resource> findResources(List<Resource> resources) {
        // Make them mutable.
        List<Resource> them = new ArrayList<>(resources);

        for (Resource resource : resources) {
            // Pass if it is a directory.
            if (resource.isDirectory()) {
                continue;
            }

            ArchiveResourceReader reader = this.resolver.resolve(resource);

            // Pass if it is not resolved; regarded as non-archive.
            if (reader == null) {
                continue;
            }

            try {
                // There is no file name in metadata using some windows archive application.
                String fileName = FilenameUtils.getBaseName(resource.getName());
                Map<String, String> props = Map.of("FNAME", fileName);

                List<Resource> inner = reader.read(resource.getInputStream(), props);
                them.addAll(inner);

                List<Resource> nested = findResources(inner);
                them.addAll(nested);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read archive file: " + resource.getPath(), e);
            }
        }

        return them;
    }

    // -------------------------------------------------------------------------------------------------

    private static class ExtensionBasedArchiveResourceReaderResolver implements ArchiveResourceReaderResolver {

        @Override
        public ArchiveResourceReader resolve(Resource resource) {
            String resourceName = resource.getName();
            String extensions = resourceName.substring(resourceName.indexOf('.') + 1);
            return resolveByExtension(extensions);
        }

        private ArchiveResourceReader resolveByExtension(String extensions) {
            if (extensions.endsWith("tar.gz") || extensions.endsWith("tgz")) {
                return new TarArchiveResourceReader(new GzipArchiveResourceReader());
            }

            if (extensions.endsWith("tar")) {
                return new TarArchiveResourceReader();
            }

            if (extensions.endsWith("zip")) {
                return new ZipArchiveResourceReader();
            }

            if (extensions.endsWith("gz")) {
                return new GzipArchiveResourceReader();
            }

            return null;
        }

    }

}
