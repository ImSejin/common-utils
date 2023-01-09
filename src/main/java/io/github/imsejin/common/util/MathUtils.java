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

import java.math.BigInteger;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

public final class MathUtils {

    @ExcludeFromGeneratedJacocoReport
    private MathUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    public static BigInteger fibonacci(long number) {
        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(1);

        for (long i = 0; i < number; i++) {
            BigInteger temp = a.add(b);
            a = b;
            b = temp;
        }

        return a;
    }

    public static BigInteger factorial(long number) {
        BigInteger result = BigInteger.valueOf(1);
        for (long factor = 2; factor <= number; factor++) {
            result = result.multiply(BigInteger.valueOf(factor));
        }
        return result;
    }

    /**
     * Returns the greatest common divisor with two integers.
     *
     * @param a one integer
     * @param b another integer
     * @return greatest common divisor
     */
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, Math.floorMod(a, b));
    }

    public static boolean isPrime(long number) {
        // Checks if it is greater than 1.
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        // Checks if n is a multiple of 2.
        if (number % 2 == 0) {
            return false;
        }

        // if not, then just checks the odds.
        for (long i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static double ceil(double amount, int scale) {
        double compensator = Math.pow(10, scale);
        return Math.ceil(amount * compensator) / compensator;
    }

    public static double round(double amount, int scale) {
        double compensator = Math.pow(10, scale);
        return Math.round(amount * compensator) / compensator;
    }

    public static double floor(double amount, int scale) {
        double compensator = Math.pow(10, scale);
        return Math.floor(amount * compensator) / compensator;
    }

    /**
     * Checks if number is odd.
     *
     * @param number number
     * @return whether number is odd or not
     */
    public static boolean isOdd(long number) {
        return (number & 1) == 1;
    }

    /**
     * Checks if number is odd.
     *
     * @param bigInt number
     * @return whether number is odd or not
     */
    public static boolean isOdd(BigInteger bigInt) {
        return bigInt.testBit(0);
    }

}
