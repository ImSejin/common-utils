package io.github.imsejin.common.util;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Stream utilities
 */
public final class StreamUtils {

    private StreamUtils() {}

    public static <T> Stream<T> toStream(@Nonnull Iterator<T> iter) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED), false);
    }

}
