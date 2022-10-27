/*
 * Copyright 2020 Sejin Im
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

package io.github.imsejin.common.util;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

/**
 * Ini utilities
 *
 * @see Ini
 */
public final class IniUtils {

    @ExcludeFromGeneratedJacocoReport
    private IniUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    private static void configure(Ini ini) {
        Config conf = new Config();
        conf.setMultiSection(true);
        conf.setFileEncoding(StandardCharsets.UTF_8);

        ini.setConfig(conf);
    }

    public static Map<String, String> readSection(File file, String sectionName) {
        return read(file).get(sectionName);
    }

    public static String readValue(File file, String sectionName, String name) {
        Section section = read(file).get(sectionName);
        return section.get(name);
    }

    public static List<String> readValues(File file, String sectionName) {
        Section section = read(file).get(sectionName);
        return new ArrayList<>(section.values());
    }

    public static Ini read(File file) {
        try {
            Ini ini = new Ini(file);
            configure(ini);
            ini.load();

            return ini;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void write(File file, Map<String, Map<String, Object>> data) {
        // Converts inner maps to sets of entries.
        Map<String, Set<Map.Entry<String, Object>>> entriesMap = data.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, it -> it.getValue().entrySet()));

        writeEntries(file, entriesMap);
    }

    public static void writeEntries(File file, Map<String, Set<Map.Entry<String, Object>>> data) {
        try {
            if (!file.exists()) file.createNewFile();

            Ini ini = new Ini(file);
            configure(ini);

            data.forEach((sectionName, entries) -> {
                for (Map.Entry<String, ?> entry : entries) {
                    ini.put(sectionName, entry.getKey(), entry.getValue());
                }
            });

            ini.store();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
