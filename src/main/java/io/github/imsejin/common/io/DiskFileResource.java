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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import io.github.imsejin.common.util.FilenameUtils;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class DiskFileResource extends AbstractResource {

    @EqualsAndHashCode.Include
    private final Path realPath;

    private DiskFileResource(String path, String name, InputStream inputStream,
                             long size, boolean directory, Path realPath) {
        super(path, name, inputStream, size, directory);
        this.realPath = realPath;
    }

    public static DiskFileResource from(Path realPath) {
        try {
            String path = realPath.toString();
            String name = FilenameUtils.getName(path);
            boolean directory = Files.isDirectory(realPath);
            long size = Files.size(realPath);

            return new DiskFileResource(path, name, null, size, directory, realPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to instantiate DiskFileResource from path: " + realPath, e);
        }
    }

    // Lazy loading
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
