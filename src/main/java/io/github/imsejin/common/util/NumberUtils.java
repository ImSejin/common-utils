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
