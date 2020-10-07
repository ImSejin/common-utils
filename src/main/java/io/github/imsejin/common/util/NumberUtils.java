package io.github.imsejin.common.util;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.text.NumberFormat;

public final class NumberUtils {

    private static final NumberFormat formatter = NumberFormat.getInstance();

    static {
        formatter.setGroupingUsed(false);
    }

    public static String format(double decimal) {
        return formatter.format(decimal);
    }

    /**
     * Returns the number of places.
     *
     * <pre>{@code
     *     getNumOfPlaces(-100); // 3
     *     getNumOfPlaces(0);    // 1
     *     getNumOfPlaces(1024); // 4
     * }</pre>
     *
     * @param number number
     * @return the number of places
     */
    public static int getNumOfPlaces(long number) {
        if (number == Long.MIN_VALUE) number++;
        return number == 0 ? 1 : (int) (Math.log10(Math.abs(number)) + 1);
    }

    /**
     * Returns the number of places.
     *
     * <pre>{@code
     *     getNumOfPlaces(new BigInteger("-100")); // 3
     *     getNumOfPlaces(new BigInteger("0"));    // 1
     *     getNumOfPlaces(new BigInteger("1024")); // 4
     * }</pre>
     *
     * @param bigInt number
     * @return the number of places
     */
    public static int getNumOfPlaces(@Nonnull BigInteger bigInt) {
        return bigInt.abs().toString().length();
    }

}
