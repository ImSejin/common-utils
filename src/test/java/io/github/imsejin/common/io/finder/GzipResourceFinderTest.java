/*
 * Copyright 2022 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.io.finder;

import io.github.imsejin.common.internal.TestUtils;
import io.github.imsejin.common.io.GzipResource;
import io.github.imsejin.common.io.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GzipResourceFinder")
class GzipResourceFinderTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "ubuntu-18.04.1.gz", "ubuntu-18.04.1.tar.gz", "ubuntu-18.04.1.tgz",
            "windows10-pro.gz", "windows10-pro.tar.gz", "windows10-pro.tgz",
    })
    void test(String fileName) throws URISyntaxException {
        // given
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Path path = Paths.get(classLoader.getResource("archiver/gzip/" + fileName).toURI());

        // when
        ResourceFinder resourceFinder = new GzipResourceFinder();
        List<Resource> resources = resourceFinder.getResources(path);

        // then
        assertThat(resources)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .doesNotHaveDuplicates()
                .hasSize(1)
                .noneMatch(Resource::isDirectory)
                .allMatch(resource -> resource instanceof GzipResource)
                .allMatch(resource -> resource.getPath().equals(resource.getName()))
                .allMatch(resource -> resource.getInputStream() != null);

        GzipResource gzipResource = (GzipResource) resources.get(0);
        assertThat(gzipResource)
                .matches(it -> it.getLastModifiedTime() != null)
                .matches(it -> it.getSize() >= it.getCompressedSize())
                .returns((long) TestUtils.readAllBytes(gzipResource.getInputStream()).length, Resource::getSize);
    }

}
