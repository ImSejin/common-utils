package io.github.imsejin.common.io.finder;

import io.github.imsejin.common.io.Resource;

import java.nio.file.Path;
import java.util.List;

public interface ResourceFinder {

    List<Resource> getResources(Path path);

}
