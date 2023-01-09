package io.github.imsejin.common.io;

import java.io.InputStream;
import java.time.Instant;
import java.util.Objects;

public class GzipResource extends AbstractResource {

    private final long compressedSize;

    private final Instant lastModifiedTime;

    public GzipResource(String name, InputStream inputStream,
            long size, long compressedSize, long lastModifiedMilliTime) {
        super(name, name, inputStream, size, false);
        this.compressedSize = compressedSize;
        this.lastModifiedTime = Instant.ofEpochMilli(lastModifiedMilliTime);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }

        GzipResource that = (GzipResource) o;
        return Objects.equals(this.compressedSize, that.compressedSize)
                && Objects.equals(this.lastModifiedTime, that.lastModifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.compressedSize, this.lastModifiedTime);
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public Instant getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    @Override
    public String toString() {
        return getClass().getName() + "(path=" + getPath() + ", name=" + getName() + ", inputStream=" + getInputStream()
                + ", size=" + getSize() + ", directory=" + isDirectory() + ", compressedSize=" + this.compressedSize
                + ", lastModifiedTime=" + this.lastModifiedTime + ")";
    }

}
