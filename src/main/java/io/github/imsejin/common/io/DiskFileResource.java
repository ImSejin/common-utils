package io.github.imsejin.common.io;

import java.io.InputStream;
import java.util.Objects;

public class DiskFileResource extends AbstractResource {

    public DiskFileResource(String path, String name, InputStream inputStream, long size, boolean directory) {
        super(path, name, inputStream, size, directory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiskFileResource that = (DiskFileResource) o;
        return getSize() == that.getSize() && isDirectory() == that.isDirectory()
                && Objects.equals(getPath(), that.getPath()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

}
