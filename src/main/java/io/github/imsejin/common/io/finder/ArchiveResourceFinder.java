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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.io.archive.ArchiveResourceReader;
import io.github.imsejin.common.util.FilenameUtils;

@RequiredArgsConstructor
public class ArchiveResourceFinder implements ResourceFinder {

    private final ArchiveResourceReader reader;

    @Override
    public final List<Resource> getResources(Path path) {
        Asserts.that(path)
                .describedAs("Invalid path to find resources: {0}", path)
                .isNotNull()
                .describedAs("No such path exists: {0}", path)
                .exists()
                .describedAs("It is not a regular file: {0}", path)
                .isRegularFile()
                .describedAs("Cannot read file: {0}", path)
                .is(Files::isReadable);

        try (InputStream in = Files.newInputStream(path)) {
            // There is no file name in metadata using some windows archive application.
            String fileName = FilenameUtils.getBaseName(path.getFileName().toString());
            Map<String, String> props = Map.of("FNAME", fileName);

            return this.reader.read(in, props);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read archive file: " + path, e);
        }
    }

}
