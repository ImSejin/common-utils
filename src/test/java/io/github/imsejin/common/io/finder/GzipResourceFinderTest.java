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

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.github.imsejin.common.internal.TestUtils;
import io.github.imsejin.common.io.GzipResource;
import io.github.imsejin.common.io.Resource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("GzipResourceFinder")
class GzipResourceFinderTest {

    @ParameterizedTest
    @CsvSource(value = {
            "ubuntu-18.04.1.gz     | catalina.out-20210123",
            "ubuntu-18.04.1.tar.gz | ubuntu-18.04.1.tar",
            "ubuntu-18.04.1.tgz    | ubuntu-18.04.1",
            "windows10-pro.gz      | windows10-pro",
            "windows10-pro.tar.gz  | windows10-pro.tar",
            "windows10-pro.tgz     | windows10-pro",
    }, delimiterString = "|")
    void test(String fileName, String resourceName) throws URISyntaxException {
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
                .allMatch(it -> it instanceof GzipResource)
                .allMatch(it -> it.getPath().equals(it.getName()))
                .allMatch(it -> it.getInputStream() != null);

        GzipResource gzipResource = (GzipResource) resources.get(0);
        assertThat(gzipResource)
                .matches(it -> it.getLastModifiedTime() != null)
                .matches(it -> it.getSize() >= it.getCompressedSize())
                .returns(resourceName, Resource::getName)
                .returns((long) TestUtils.readAllBytes(gzipResource.getInputStream()).length, Resource::getSize);
    }

}
