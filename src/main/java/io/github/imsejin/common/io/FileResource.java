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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import io.github.imsejin.common.util.FilenameUtils;

@Getter
@ToString
@EqualsAndHashCode
public class FileResource implements Resource {

    private final String path;

    private final String name;

    private final Instant lastModifiedTime;

    private final long size;

    private final boolean directory;

    private final Path realPath;

    public FileResource(Path realPath) {
        try {
            this.path = realPath.toString().replace('\\', '/');
            this.name = FilenameUtils.getName(this.path);
            this.lastModifiedTime = Files.getLastModifiedTime(realPath).toInstant();
            this.directory = Files.isDirectory(realPath);
            this.size = Files.size(realPath);
            this.realPath = realPath;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to instantiate FileResource from path: " + realPath, e);
        }
    }

    @Override
    public InputStream getInputStream() {
        if (isDirectory()) {
            return null;
        }

        try {
            return Files.newInputStream(this.realPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to get InputStream from path: " + this.realPath, e);
        }
    }

}
