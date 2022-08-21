package io.github.imsejin.common.io;

import java.io.InputStream;
import java.time.Instant;
import java.util.Objects;

public abstract class ArchiveResource extends AbstractResource {

    private final Instant lastModifiedTime;

    public ArchiveResource(String path, String name, InputStream inputStream,
                           long size, boolean directory, long lastModifiedMilliTime) {
        super(path, name, inputStream, size, directory);
        this.lastModifiedTime = Instant.ofEpochMilli(lastModifiedMilliTime);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;

        ArchiveResource that = (ArchiveResource) o;
        return Objects.equals(this.lastModifiedTime, that.lastModifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.lastModifiedTime);
    }

    public Instant getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    @Override
    public String toString() {
        return getClass().getName() + "(path=" + getPath() + ", name=" + getName() + ", inputStream=" + getInputStream()
                + ", size=" + getSize() + ", directory=" + isDirectory() + ", lastModifiedTime=" + this.lastModifiedTime + ")";
    }

}
