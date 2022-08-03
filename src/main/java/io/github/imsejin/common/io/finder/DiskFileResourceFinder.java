package io.github.imsejin.common.io.finder;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.io.DiskFileResource;
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.util.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
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
                .as("Invalid path to find resources: {0}", path)
                .isNotNull()
                .as("No such path exists: {0}", path)
                .predicate(Files::exists);

        if (!Files.isDirectory(path)) {
            return Collections.singletonList(toResource(path));
        }

        Stream<Path> stream;
        try {
            if (this.recursive) {
                stream = Files.walk(path);
            } else {
                stream = Files.list(path);
                stream = Stream.concat(Stream.of(path), stream);
            }
        } catch (NotDirectoryException e) {
            // Only if you call Files.list
            throw new IllegalArgumentException("Location is not a directory: " + path);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to visit location: " + path, e);
        }

        return stream.filter(this.filter).map(DiskFileResourceFinder::toResource)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    // ----------------------------------------------------------------------------------------------------

    private static DiskFileResource toResource(Path path) {
        try {
            String pathString = path.toString();
            boolean directory = Files.isDirectory(path);

            return new DiskFileResource(pathString, FilenameUtils.getName(pathString),
                    directory ? null : Files.newInputStream(path), Files.size(path), directory);
        } catch (IOException e) {
            throw new IllegalStateException("Path doesn't exist: " + path, e);
        }
    }

    private static DiskFileResource toResource(File file) {
        try {
            return new DiskFileResource(file.getPath(), file.getName(),
                    new FileInputStream(file), file.length(), file.isDirectory());
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File doesn't exist: " + file, e);
        }
    }

}
