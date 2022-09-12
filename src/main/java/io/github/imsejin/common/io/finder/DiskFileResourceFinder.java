package io.github.imsejin.common.io.finder;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.io.DiskFileResource;
import io.github.imsejin.common.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class DiskFileResourceFinder implements ResourceFinder {

    private final boolean recursive;

    private final Predicate<Path> filter;

    public DiskFileResourceFinder(boolean recursive) {
        this(recursive, entry -> true);
    }

    public DiskFileResourceFinder(boolean recursive, Predicate<Path> filter) {
        this.recursive = recursive;
        this.filter = filter;
    }

    @Override
    public List<Resource> getResources(Path path) {
        Asserts.that(path)
                .describedAs("Invalid path to find resources: {0}", path)
                .isNotNull()
                .describedAs("No such path exists: {0}", path)
                .predicate(Files::exists);

        if (!Files.isDirectory(path)) {
            Resource resource = DiskFileResource.from(path);
            return Collections.singletonList(resource);
        }

        Stream<Path> stream;
        try {
            if (this.recursive) {
                stream = Files.walk(path);
            } else {
                stream = Files.list(path);
                stream = Stream.concat(Stream.of(path), stream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to visit location: " + path, e);
        }

        return stream.filter(this.filter).map(DiskFileResource::from)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

}
