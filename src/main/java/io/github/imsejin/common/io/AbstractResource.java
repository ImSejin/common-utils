package io.github.imsejin.common.io;

import java.io.InputStream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractResource implements Resource {

    @EqualsAndHashCode.Include
    private final String path;

    @EqualsAndHashCode.Include
    private final String name;

    private final InputStream inputStream;

    @EqualsAndHashCode.Include
    private final long size;

    @EqualsAndHashCode.Include
    private final boolean directory;

    protected AbstractResource(String path, String name, InputStream inputStream, long size, boolean directory) {
        this.path = path;
        this.name = name;
        this.inputStream = inputStream;
        this.size = size;
        this.directory = directory;
    }

}
