package io.github.imsejin.common.io;

import java.io.InputStream;
import java.util.Objects;

public abstract class AbstractResource implements Resource {

    private final String path;

    private final String name;

    private final InputStream inputStream;

    private final long size;

    private final boolean directory;

    protected AbstractResource(String path, String name, InputStream inputStream, long size, boolean directory) {
        this.path = path;
        this.name = name;
        this.inputStream = inputStream;
        this.size = size;
        this.directory = directory;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public boolean isDirectory() {
        return this.directory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractResource that = (AbstractResource) o;
        return this.size == that.size && this.directory == that.directory
                && Objects.equals(this.path, that.path) && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.path, this.name, this.size, this.directory);
    }

    @Override
    public String toString() {
        return getClass().getName() + "(path=" + this.path + ", name=" + this.name + ", inputStream=" + this.inputStream
                + ", size=" + this.size + ", directory=" + this.directory + ")";
    }

}
