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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.io.FileResource;
import io.github.imsejin.common.io.Resource;

public class FileResourceFinder implements ResourceFinder {

    private final boolean recursive;

    private final Predicate<Path> filter;

    public FileResourceFinder(boolean recursive) {
        this(recursive, entry -> true);
    }

    public FileResourceFinder(boolean recursive, Predicate<Path> filter) {
        this.recursive = recursive;
        this.filter = filter;
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

        if (!Files.isDirectory(path)) {
            FileResource resource = new FileResource(path);
            return Collections.singletonList(resource);
        }

        try {
            Stream<Path> stream;
            if (this.recursive) {
                stream = Files.walk(path);
            } else {
                stream = Files.list(path);
                stream = Stream.concat(Stream.of(path), stream);
            }

            return stream.filter(this.filter)
                    .map(FileResource::new)
                    .map(Resource.class::cast)
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to visit location: " + path, e);
        }
    }

}
