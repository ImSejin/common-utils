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
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import io.github.imsejin.common.io.GzipResource;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.util.StringUtils;

public class GzipArchiveResourceReader implements ArchiveResourceReader {

    @Override
    public List<Resource> read(InputStream in, Map<String, String> props) throws IOException {
        try (GzipCompressorInputStream gis = new GzipCompressorInputStream(in);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int offset;
            while ((offset = gis.read(buffer)) != -1) {
                bos.write(buffer, 0, offset);
            }

            byte[] bytes = bos.toByteArray();
            GzipResource resource = toResource(gis, bytes, props);

            return Collections.singletonList(resource);
        }
    }

    // -------------------------------------------------------------------------------------------------

    private GzipResource toResource(
            GzipCompressorInputStream in,
            byte[] bytes,
            Map<String, String> props
    ) {
        String fileName = in.getMetaData().getFileName();

        // There is no file name in metadata using some windows archive application.
        if (StringUtils.isNullOrEmpty(fileName)) {
            fileName = props.get("FNAME");
        }

        long modifiedMilliTime = in.getMetaData().getModificationTime();

        return new GzipResource(
                fileName,
                Instant.ofEpochMilli(modifiedMilliTime),
                bytes.length,
                in.getCompressedCount(),
                bytes
        );
    }

}
