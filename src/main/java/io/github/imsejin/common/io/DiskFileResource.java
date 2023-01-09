package io.github.imsejin.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import io.github.imsejin.common.util.FilenameUtils;

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
            long size = Files.size(realPath);

            return new DiskFileResource(path, name, null, size, directory, realPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to instantiate DiskFileResource from path: " + realPath, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }

        DiskFileResource that = (DiskFileResource) o;
        return Objects.equals(this.realPath, that.realPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.realPath);
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

    public Path getRealPath() {
        return this.realPath;
    }

}
