package io.github.imsejin.common.util;

import java.text.NumberFormat;

public final class NumberUtils {

    private static final NumberFormat formatter = NumberFormat.getInstance();

    static {
        formatter.setGroupingUsed(false);
    }

    public static String format(double decimal) {
        return formatter.format(decimal);
    }

}
