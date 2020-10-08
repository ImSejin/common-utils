package io.github.imsejin.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON utilities
 *
 * <p>
 * Utilities that process the JSON.
 * </p>
 */
public final class JsonUtils {

    private static final Gson gson = new Gson();

    private JsonUtils() {
    }

    /**
     * Reads the JSON format string returned by the URL and converts it to {@link JsonObject}.
     *
     * <pre>{@code
     *     String uriText = "http://cdn.lezhin.com/episodes/snail/1.json?access_token=5be30a25-a044-410c-88b0-19a1da968a64";
     *     URL url = URI.create(uriText).toURL();
     *     JsonObject json = JsonUtils.readJsonFromUrl(url);
     * }</pre>
     *
     * @param url URL
     * @return JSON object
     */
    public static JsonObject readJsonFromUrl(@Nonnull URL url) {
        String jsonText;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), StandardCharsets.UTF_8))) {
            jsonText = readAllJson(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JsonParser.parseString(jsonText).getAsJsonObject();
    }

    /**
     * Converts all characters read by {@link java.io.Reader} to string.
     *
     * @param reader reader
     * @return JSON string
     */
    private static String readAllJson(@Nonnull BufferedReader reader) {
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Converts the JSON format string to the specified object.
     *
     * <pre>{@code
     *     String jsonText = "{\"id\":1011,\"list\":[{\"id\":10,\"name\":\"foo\"},{\"id\":11,\"name\":\"bar\"}]}";
     *     T t = JsonUtils.toObject(jsonText, T.class);
     * }</pre>
     *
     * @param jsonText JSON string
     * @param clazz    type
     * @param <T>      type parameter
     * @return type casted instance
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
     * @param <T>       type parameter
     * @return list that has type casted instances
     */
    public static <T> List<T> toList(@Nonnull JsonArray jsonArray, Class<T> clazz) {
        return StreamUtils.toStream(jsonArray.iterator())
                .map(jsonElement -> gson.fromJson(jsonElement, clazz))
                .collect(Collectors.toList());
    }

}
