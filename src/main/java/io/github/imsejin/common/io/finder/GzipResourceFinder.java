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
import io.github.imsejin.common.io.GzipResource;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.util.FilenameUtils;
import io.github.imsejin.common.util.StringUtils;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class GzipResourceFinder implements ResourceFinder {

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

        try (GzipCompressorInputStream in = new GzipCompressorInputStream(Files.newInputStream(path))) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[16384];
            int offset;
            while ((offset = in.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }

            String fileName = in.getMetaData().getFilename();
            if (StringUtils.isNullOrEmpty(fileName)) {
                String name = FilenameUtils.getName(path.toString());
                fileName = FilenameUtils.getBaseName(name);
            }

            long modifiedMilliTime = in.getMetaData().getModificationTime();
            byte[] bytes = out.toByteArray();
            GzipResource resource = new GzipResource(fileName, new ByteArrayInputStream(bytes),
                    bytes.length, in.getCompressedCount(), modifiedMilliTime);

            return Collections.singletonList(resource);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to read gzip compressor: " + path, e);
        }
    }

}
