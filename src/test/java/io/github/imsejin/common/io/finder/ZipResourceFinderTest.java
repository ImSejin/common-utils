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
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.io.ZipResource;
import io.github.imsejin.common.util.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ZipResourceFinder")
class ZipResourceFinderTest {

    @ParameterizedTest
    @CsvSource(value = {
            "ubuntu-18.04.3.zip | java | 64",
            "windows10-pro.zip  | json | 548",
    }, delimiterString = "|")
    void test0(String fileName, String extension, int resourceCount) throws URISyntaxException {
        // given
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Path path = Paths.get(classLoader.getResource("archiver/zip/" + fileName).toURI());

        // when
        ResourceFinder resourceFinder = new ZipResourceFinder(false);
        List<Resource> resources = resourceFinder.getResources(path);

        // then
        assertThat(resources)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .doesNotHaveDuplicates()
                .allMatch(resource -> resource instanceof ZipResource)
                .allMatch(resource -> resource.isDirectory()
                        ? resource.getPath().endsWith(resource.getName() + '/')
                        : resource.getPath().endsWith(resource.getName()));
        assertThat(resources)
                .filteredOn(Resource::isDirectory)
                .allMatch(resource -> resource.getInputStream() == null)
                .allMatch(resource -> resource.getSize() == 0);
        assertThat(resources)
                .filteredOn(resource -> !resource.isDirectory())
                .allMatch(resource -> resource.getInputStream() != null)
                .allMatch(resource -> TestUtils.readAllBytes(resource.getInputStream()).length == resource.getSize())
                .allMatch(resource -> resource.getSize() >= 0);
        assertThat(resources)
                .filteredOn(resource -> resource.getPath().endsWith('.' + extension))
                .hasSize(resourceCount)
                .allMatch(resource -> FilenameUtils.getExtension(resource.getName()).equals(extension))
                .allMatch(resource -> resource.getInputStream() != null)
                .allMatch(resource -> resource.getSize() >= 0);
    }

}
