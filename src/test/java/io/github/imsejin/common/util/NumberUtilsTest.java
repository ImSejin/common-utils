package io.github.imsejin.common.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.*;

class NumberUtilsTest {

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, Integer.MIN_VALUE, -13, -1, 0, 2, 97, 1087, 987_753, 10_451_348, Integer.MAX_VALUE, Long.MAX_VALUE})
    void getNumOfPlaces(long number) {
        // when
        int numOfPlaces = NumberUtils.getNumOfPlaces(number);

        // then
        assertThat(numOfPlaces)
                .as("Is the number of places correct?")
                .isEqualTo(String.valueOf(
                        Math.abs(number == Long.MIN_VALUE ? number + 1 : number)).length());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-115234155123123413842342342024623440", "-5", "0", "9", "1505512411489465416645571849602523405834510"})
    void getNumOfPlaces(String number) {
        // given
        BigInteger bigInt = new BigInteger(number);

        // when
        int numOfPlaces = NumberUtils.getNumOfPlaces(bigInt);

        // then
        System.out.println(bigInt);
        assertThat(numOfPlaces)
                .as("Is the number of places correct?")
                .isEqualTo(number.replace("-", "").length());
    }

}
