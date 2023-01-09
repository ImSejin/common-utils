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
import io.github.imsejin.common.io.Resource;
import io.github.imsejin.common.io.TarResource;
import io.github.imsejin.common.util.FilenameUtils;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TarGzipResourceFinder")
class TarGzipResourceFinderTest {

    @ParameterizedTest
    @CsvSource(value = {
            "ubuntu-18.04.1.tar.gz | tar  | 1",
            "ubuntu-18.04.1.tgz    | c    | 373",
            "windows10-pro.tar.gz  | xfdl | 221",
            "windows10-pro.tgz     | js   | 80",
    }, delimiterString = "|")
    void test0(String fileName, String extension, int resourceCount) throws URISyntaxException {
        // given
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Path path = Paths.get(classLoader.getResource("archiver/gzip/" + fileName).toURI());

        // when
        ResourceFinder resourceFinder = new TarGzipResourceFinder(false);
        List<Resource> resources = resourceFinder.getResources(path);

        // then
        assertThat(resources)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .doesNotHaveDuplicates()
                .allMatch(it -> it instanceof TarResource)
                .allMatch(it -> it.getPath().endsWith(it.getName() + (it.isDirectory() ? "/" : "")));
        assertThat(resources)
                .filteredOn(Resource::isDirectory)
                .allMatch(it -> it.getInputStream() == null)
                .allMatch(it -> it.getSize() == 0);
        assertThat(resources)
                .filteredOn(it -> !it.isDirectory())
                .allMatch(it -> it.getInputStream() != null)
                .allMatch(it -> TestUtils.readAllBytes(it.getInputStream()).length == it.getSize())
                .allMatch(it -> it.getSize() >= 0);
        assertThat(resources)
                .filteredOn(it -> it.getPath().endsWith('.' + extension))
                .hasSize(resourceCount)
                .allMatch(it -> FilenameUtils.getExtension(it.getName()).equals(extension))
                .allMatch(it -> it.getInputStream() != null)
                .allMatch(it -> it.getSize() >= 0);
    }

}
