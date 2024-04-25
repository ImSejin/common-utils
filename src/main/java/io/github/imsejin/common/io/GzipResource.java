package io.github.imsejin.common.io;

import java.io.InputStream;
import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class GzipResource extends AbstractResource {

    private final long compressedSize;

    private final Instant lastModifiedTime;

    public GzipResource(
            String name, InputStream inputStream,
            long size, long compressedSize, long lastModifiedMilliTime
    ) {
        super(name, name, inputStream, size, false);
        this.compressedSize = compressedSize;
        this.lastModifiedTime = Instant.ofEpochMilli(lastModifiedMilliTime);
    }

}
