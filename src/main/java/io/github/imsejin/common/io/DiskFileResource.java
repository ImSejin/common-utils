package io.github.imsejin.common.io;

import io.github.imsejin.common.util.FilenameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class DiskFileResource extends AbstractResource {

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
            InputStream inputStream = directory ? null : Files.newInputStream(realPath);
            long size = Files.size(realPath);

            return new DiskFileResource(path, name, inputStream, size, directory, realPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to instantiate DiskFileResource from path: " + realPath, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiskFileResource that = (DiskFileResource) o;
        return getSize() == that.getSize() && isDirectory() == that.isDirectory()
                && Objects.equals(this.realPath, that.realPath)
                && Objects.equals(getPath(), that.getPath()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.realPath);
    }

}
