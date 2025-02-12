/*
 * Copyright 2025 Sejin Im
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

package io.github.imsejin.common.io.finder

import spock.lang.Specification

import java.nio.file.Path

import org.apache.tika.Tika
import org.apache.tika.mime.MediaType

import io.github.imsejin.common.io.FileResource
import io.github.imsejin.common.io.Resource
import io.github.imsejin.common.io.TarResource
import io.github.imsejin.common.io.ZipResource
import io.github.imsejin.common.io.archive.ArchiveResourceReader
import io.github.imsejin.common.io.archive.GzipArchiveResourceReader
import io.github.imsejin.common.io.archive.TarArchiveResourceReader
import io.github.imsejin.common.io.archive.ZipArchiveResourceReader

class RecursiveResourceFinderSpec extends Specification {

    def "Gets resources recursively by extension resolver"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/nested/").toURI())

        when:
        def finder = new RecursiveResourceFinder()
        def resources = finder.getResources(path)

        then:
        !resources.empty
        resources.any { it instanceof FileResource }
        resources.any { it instanceof TarResource }
        resources.any { it instanceof ZipResource }
    }

    def "Gets resources recursively by tika resolver"() {
        given:
        def classLoader = Thread.currentThread().contextClassLoader
        def path = Path.of(classLoader.getResource("archiver/nested/").toURI())

        when:
        def finder = new RecursiveResourceFinder(new TikaArchiveResourceReaderResolver())
        def resources = finder.getResources(path)

        then:
        !resources.empty
        resources.any { it instanceof FileResource }
        resources.any { it instanceof TarResource }
        resources.any { it instanceof ZipResource }
    }

    // -------------------------------------------------------------------------------------------------

    private static class TikaArchiveResourceReaderResolver implements ArchiveResourceReaderResolver {

        private Tika tika

        TikaArchiveResourceReaderResolver() {
            this.tika = new Tika()
        }

        @Override
        ArchiveResourceReader resolve(Resource resource) {
            def detectedType = this.tika.detect(resource.inputStream)
            def mediaType = MediaType.parse(detectedType)

            if (mediaType == null || mediaType.type != "application") {
                return null
            }

            def subtype = mediaType.subtype
            if (subtype.endsWith("gzip")) {
                def reader = new GzipArchiveResourceReader()
                if (resource.name.toLowerCase(Locale.ROOT).matches(~/\.t(ar\.)?gz$/)) {
                    reader = new TarArchiveResourceReader(reader)
                }

                return reader
            }

            if (subtype.endsWith("tar")) {
                return new TarArchiveResourceReader()
            }

            if (subtype.endsWith("zip")) {
                return new ZipArchiveResourceReader()
            }

            return null
        }

    }

}
