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

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.text.NumberFormat;

public final class NumberUtils {

    private static final NumberFormat formatter = NumberFormat.getInstance();

    static {
        formatter.setGroupingUsed(false);
    }

    private NumberUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static Double toPositive(Double number) {
        return number == null || number <= 0.0 ? Double.valueOf(1.0) : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static double toPositive(double number) {
        return number <= 0.0 ? 1.0 : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static Float toPositive(Float number) {
        return number == null || number <= 0.0F ? Float.valueOf(1.0F) : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static float toPositive(float number) {
        return number <= 0.0F ? 1.0F : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static Long toPositive(Long number) {
        return number == null || number <= 0L ? Long.valueOf(1L) : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static long toPositive(long number) {
        return number <= 0L ? 1L : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static Integer toPositive(Integer number) {
        return number == null || number <= 0 ? Integer.valueOf(1) : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static int toPositive(int number) {
        return number <= 0 ? 1 : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static Short toPositive(Short number) {
        return number == null || number <= 0 ? Short.valueOf((short) 1) : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static short toPositive(short number) {
        return number <= 0 ? 1 : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static Byte toPositive(Byte number) {
        return number == null || number <= 0 ? Byte.valueOf((byte) 1) : number;
    }

    /**
     * Return 1 if number is not positive, or number as it is.
     *
     * @param number number
     * @return positive number
     */
    public static byte toPositive(byte number) {
        return number <= 0 ? 1 : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static Double toNegative(Double number) {
        return number == null || number >= 0.0 ? Double.valueOf(-1.0) : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static double toNegative(double number) {
        return number >= 0.0 ? -1.0 : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static Float toNegative(Float number) {
        return number == null || number >= 0.0F ? Float.valueOf(-1.0F) : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static float toNegative(float number) {
        return number >= 0.0F ? -1.0F : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static Long toNegative(Long number) {
        return number == null || number >= 0L ? Long.valueOf(-1L) : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static long toNegative(long number) {
        return number >= 0L ? -1L : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static Integer toNegative(Integer number) {
        return number == null || number >= 0 ? Integer.valueOf(-1) : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static int toNegative(int number) {
        return number >= 0 ? -1 : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static Short toNegative(Short number) {
        return number == null || number >= 0 ? Short.valueOf((short) -1) : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static short toNegative(short number) {
        return number >= 0 ? -1 : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static Byte toNegative(Byte number) {
        return number == null || number >= 0 ? Byte.valueOf((byte) -1) : number;
    }

    /**
     * Return -1 if number is not negative, or number as it is.
     *
     * @param number number
     * @return negative number
     */
    public static byte toNegative(byte number) {
        return number >= 0 ? -1 : number;
    }

    /**
     * Return default number if number is null, or number as it is.
     *
     * @param number        number
     * @param defaultNumber default number
     * @return number
     */
    public static Double ifNull(Double number, double defaultNumber) {
        return number == null ? defaultNumber : number;
    }

    /**
     * Return default number if number is null, or number as it is.
     *
     * @param number        number
     * @param defaultNumber default number
     * @return number
     */
    public static Float ifNull(Float number, float defaultNumber) {
        return number == null ? defaultNumber : number;
    }

    /**
     * Return default number if number is null, or number as it is.
     *
     * @param number        number
     * @param defaultNumber default number
     * @return number
     */
    public static Long ifNull(Long number, long defaultNumber) {
        return number == null ? defaultNumber : number;
    }

    /**
     * Return default number if number is null, or number as it is.
     *
     * @param number        number
     * @param defaultNumber default number
     * @return number
     */
    public static Integer ifNull(Integer number, int defaultNumber) {
        return number == null ? defaultNumber : number;
    }

    /**
     * Return default number if number is null, or number as it is.
     *
     * @param number        number
     * @param defaultNumber default number
     * @return number
     */
    public static Short ifNull(Short number, short defaultNumber) {
        return number == null ? defaultNumber : number;
    }

    /**
     * Return default number if number is null, or number as it is.
     *
     * @param number        number
     * @param defaultNumber default number
     * @return number
     */
    public static Byte ifNull(Byte number, byte defaultNumber) {
        return number == null ? defaultNumber : number;
    }

    public static String format(double decimal) {
        return formatter.format(decimal);
    }

    /**
     * Returns the number of places.
     *
     * <pre><code>
     *     getNumOfPlaces(-100); // 3
     *     getNumOfPlaces(0);    // 1
     *     getNumOfPlaces(1024); // 4
     * </code></pre>
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
     * <pre><code>
     *     getNumOfPlaces(new BigInteger("-100")); // 3
     *     getNumOfPlaces(new BigInteger("0"));    // 1
     *     getNumOfPlaces(new BigInteger("1024")); // 4
     * </code></pre>
     *
     * @param bigInt number
     * @return the number of places
     */
    public static int getNumOfPlaces(@Nonnull BigInteger bigInt) {
        return bigInt.abs().toString().length();
    }

}
