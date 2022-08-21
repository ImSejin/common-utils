package io.github.imsejin.common.io;

import java.io.InputStream;

public interface Resource {

    String getPath();

    String getName();

    InputStream getInputStream();

    long getSize();

    boolean isDirectory();

}
