package io.github.imsejin.common.io;

import java.io.InputStream;
import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class ArchiveResource extends AbstractResource {

    @EqualsAndHashCode.Include
    private final Instant lastModifiedTime;

    public ArchiveResource(
            String path, String name, InputStream inputStream,
            long size, boolean directory, long lastModifiedMilliTime
    ) {
        super(path, name, inputStream, size, directory);
        this.lastModifiedTime = Instant.ofEpochMilli(lastModifiedMilliTime);
    }

}
