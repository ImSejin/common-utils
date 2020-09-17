package io.github.imsejin.util;

import java.io.*;

/**
 * Object utilities
 */
public final class ObjectUtils {

    private ObjectUtils() {}

    public static Object cloneDeep(Object obj) {
        return cloneDeep(obj, Object.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cloneDeep(Object obj, Class<T> type) {
        Object clone;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);

            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            clone = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return (T) clone;
    }

}
