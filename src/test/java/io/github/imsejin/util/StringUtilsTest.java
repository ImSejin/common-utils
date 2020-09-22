package io.github.imsejin.util;

import org.junit.jupiter.api.*;

class StringUtilsTest {

    @Test
    void padStart1() {
        // given
        long startTime = System.nanoTime();
        String result = "";

        // when
        for (int i = 0; i < 100_000_000; i++) {
            result = StringUtils.padStart(8, String.valueOf(i), "0");
        }

        // then
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000D;
        System.out.println(elapsedTime + " ms");
    }

    @Test
    void padStart2() {
        // given
        long startTime = System.nanoTime();
        String result = "";

        // when
        for (int i = 0; i < 100_000_000; i++) {
            StringBuilder sb = new StringBuilder();
            String s = String.valueOf(i);
            for (int j = 0; j < 8 - s.length(); j++) {
                sb.append('0');
            }
            sb.append(i);
            result = sb.toString();
        }

        // then
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000D;
        System.out.println(elapsedTime + " ms");
    }

    @Test
    void padStart3() {
        // given
        long startTime = System.nanoTime();
        String result = "";

        // when
        for (int i = 0; i < 100_000_000; i++) {
            StringBuffer sb = new StringBuffer();
            String s = String.valueOf(i);
            for (int j = 0; j < 8 - s.length(); j++) {
                sb.append('0');
            }
            sb.append(i);
            result = sb.toString();
        }

        // then
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000D;
        System.out.println(elapsedTime + " ms");
    }

}