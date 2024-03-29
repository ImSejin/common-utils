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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import static java.util.stream.Collectors.*;

/**
 * JSON utilities
 *
 * <p> Utilities for handling JSON.
 *
 * @see Gson
 */
public final class JsonUtils {

    private static final Gson gson = new Gson();

    @ExcludeFromGeneratedJacocoReport
    private JsonUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Reads the JSON format string returned by the URL and converts it to {@link JsonObject}.
     *
     * <pre><code>
     *     URL url = new URL("https://...");
     *     JsonObject json = JsonUtils.readJsonFromUrl(url);
     * </code></pre>
     *
     * @param url URL
     * @return JSON object
     */
    public static JsonObject readJsonFromUrl(URL url) {
        String jsonText;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), StandardCharsets.UTF_8))) {
            jsonText = readAllLines(reader);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return JsonParser.parseString(jsonText).getAsJsonObject();
    }

    /**
     * Converts all characters read by {@link java.io.BufferedReader} to string.
     *
     * @param reader reader
     * @return JSON string
     */
    private static String readAllLines(Reader reader) {
        BufferedReader bufferedReader;
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }

        return bufferedReader.lines().collect(joining(System.lineSeparator()));
    }

    /**
     * Converts the JSON format string to the specified object.
     *
     * <pre><code>
     *     String jsonText = "{\"id\":1011,\"list\":[{\"id\":10,\"name\":\"foo\"},{\"id\":11,\"name\":\"bar\"}]}";
     *     T t = JsonUtils.toObject(jsonText, T.class);
     * </code></pre>
     *
     * @param jsonText JSON string
     * @param clazz    type
     * @param <T>      any type
     * @return type cast instance
     */
    public static <T> T toObject(String jsonText, Class<T> clazz) {
        return gson.fromJson(jsonText, clazz);
    }

    /**
     * Converts {@link JsonArray} to a list of the specified objects.
     *
     * <pre>{@code
     *     String jsonText = "{\"id\":1011,\"list\":[{\"id\":10,\"name\":\"foo\"},{\"id\":11,\"name\":\"bar\"}]}";
     *     JsonObject jsonObject = JsonParser.parseString(jsonText).getAsJsonObject();
     *     JsonArray jsonArray = jsonObject.get("list").getAsJsonArray();
     *
     *     List<T> list = JsonUtils.toList(jsonArray, T.class);
     * }</pre>
     *
     * @param jsonArray JSON array
     * @param clazz     type
     * @param <T>       any type
     * @return list that has type cast instances
     */
    public static <T> List<T> toList(JsonArray jsonArray, Class<T> clazz) {
        return StreamUtils.toStream(jsonArray.iterator())
                .map(jsonElement -> gson.fromJson(jsonElement, clazz))
                .collect(Collectors.toList());
    }

}
