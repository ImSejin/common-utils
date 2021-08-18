package io.github.imsejin.common.util;

import org.ini4j.Ini;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class IniUtilsTest {

    private static final Map<String, Map<String, Object>> data;

    static {
        data = new HashMap<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("option", "value");
        map1.put("username", "ID");
        map1.put("password", "PW");
        data.put("test1", map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("option", "Optional option");
        map2.put("username", "tester@test.co");
        map2.put("password", " ]~ !@#$%[^&*()-=_+' \":;\\{}<>,./? ");
        data.put("test2", map2);
    }

    @Test
    @SuppressWarnings("unchecked")
    void readSection(@TempDir Path path) {
        // given
        File file = new File(path.toFile(), "test.ini");
        String sectionName = "test1";

        // when
        IniUtils.write(file, data);
        Map<String, String> section = IniUtils.readSection(file, sectionName);

        // then
        assertThat(section)
                .containsExactly(data.get(sectionName).entrySet().toArray(new Map.Entry[0]));
    }

    @Test
    void readValue(@TempDir Path path) {
        // given
        File file = new File(path.toFile(), "test.ini");

        // when
        IniUtils.write(file, data);
        String value = IniUtils.readValue(file, "test2", "option");

        // then
        assertThat(value).isEqualTo("Optional option");
    }

    @Test
    void readValues(@TempDir Path path) {
        // given
        File file = new File(path.toFile(), "test.ini");
        String sectionName = "test2";

        // when
        IniUtils.write(file, data);
        List<String> actual = IniUtils.readValues(file, sectionName);

        // then
        String[] values = data.get(sectionName).values().stream()
                .map(it -> it.toString().trim()).toArray(String[]::new);
        assertThat(actual)
                .containsExactly(values);
    }

    @Test
    void read(@TempDir Path path) {
        // given
        File file = new File(path.toFile(), "test.ini");

        // when
        IniUtils.write(file, data);
        Ini ini = IniUtils.read(file);

        // then
        Set<String> sectionNames = data.keySet();
        assertThat(ini)
                .as("Keys of map must be exactly equal to section names of ini file")
                .containsOnlyKeys(sectionNames);

        ini.forEach((sectionName, section) -> {
            Set<String> optionNames = data.get(sectionName).keySet();
            String[] optionValues = data.get(sectionName).values().stream()
                    .map(it -> it.toString().trim()).toArray(String[]::new);

            assertThat(section)
                    .as("Options' names of each section must be exactly equal to keys of all entries")
                    .containsOnlyKeys(optionNames)
                    .as("Options' values of each section must be exactly equal to values of all entries")
                    .containsValues(optionValues);
        });
    }

    @Test
    void write(@TempDir Path path) {
        // given
        File file = new File(path.toFile(), "test.ini");

        // when
        IniUtils.write(file, data);

        // then
        assertThat(file)
                .as("Ini file must be created")
                .exists()
                .canRead()
                .canWrite();

        assertThat(IniUtils.readValue(file, "test2", "username"))
                .isEqualTo("tester@test.co");
    }

}