package io.github.imsejin.common.io.finder;

import java.nio.file.Path;
import java.util.List;

import io.github.imsejin.common.io.Resource;

public interface ResourceFinder {

    List<Resource> getResources(Path path);

}
