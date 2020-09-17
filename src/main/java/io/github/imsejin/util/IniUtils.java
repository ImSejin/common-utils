package io.github.imsejin.util;

import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ini utilities
 */
public final class IniUtils {

    private IniUtils() {
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

    private static Ini read(File file) {
        Config conf = new Config();
        conf.setMultiSection(true);

        try {
            Ini ini = new Ini(file);
            ini.setConfig(conf);
            ini.load();

            return ini;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
