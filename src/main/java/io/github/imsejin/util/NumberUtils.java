package io.github.imsejin.util;

public final class NumberUtils {

    public static double floor(double amount, int len) {
        return Double.parseDouble(String.format("%." + len + "f", amount));
    }

}
