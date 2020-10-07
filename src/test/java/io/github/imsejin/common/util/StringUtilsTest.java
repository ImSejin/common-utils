package io.github.imsejin.common.util;

import io.github.imsejin.common.tool.Stopwatch;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

class StringUtilsTest {

    @Test
    @DisplayName("padStart --- Collections#nCopies(int, Object)")
    void padStart1() {
        // given
        String result = "";
        Stopwatch stopwatch = new Stopwatch(TimeUnit.MILLISECONDS);
        stopwatch.start();

        // when
        for (int i = 0; i < 100_000_000; i++) {
            result = StringUtils.padStart(8, String.valueOf(i), "0");
        }

        // then
        stopwatch.stop();
        System.out.println(stopwatch.getStatistics());
    }

    @Test
    @DisplayName("padStart --- StringBuilder#append(char)")
    void padStart2() {
        // given
        String result = "";
        Stopwatch stopwatch = new Stopwatch(TimeUnit.MILLISECONDS);
        stopwatch.start();

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
        stopwatch.stop();
        System.out.println(stopwatch.getStatistics());
    }

    @Test
    @DisplayName("padStart --- StringBuffer#append(char)")
    void padStart3() {
        // given
        String result = "";
        Stopwatch stopwatch = new Stopwatch(TimeUnit.MILLISECONDS);
        stopwatch.start();

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
        stopwatch.stop();
        System.out.println(stopwatch.getStatistics());
    }

    @Test
    void countOf() {
        // given
        String origin = "<head>\n" +
                "  <link rel=\"stylesheet\" href=\"chrome-search://local-ntp/animations.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"chrome-search://local-ntp/local-ntp-common.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"chrome-search://local-ntp/customize.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"chrome-search://local-ntp/doodles.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"chrome-search://local-ntp/local-ntp.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"chrome-search://local-ntp/theme.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"chrome-search://local-ntp/voice.css\">\n" +
                "  \n" +
                "  <meta http-equiv=\"Content-Security-Policy\" content=\"object-src 'none';child-src chrome-search://most-visited/ https://*.google.com/ ;script-src 'strict-dynamic' 'sha256-1+GSDjMMklBjZY0QiWq+tGupCvajw4Xbn46ect2mZgM=' 'sha256-2mX1M62Fd0u8q0dQY2mRsK5S1NS9jJuQAvyE8tD0dkQ=' 'sha256-EtIKSV82ixJHE3AzqhoiVbUGKG+Kd8XS0fFToow29o0=' 'sha256-QSyFltV9X3gkyBrg+SMfKvZNXmqPQc6K4B6OYhTuXmw=' 'sha256-ANdtIo91Yk/zh1YKZ+IXKP1pb00awOjEFMAUld02F6A=' 'sha256-CbH+xPsBKQxVw5d9blISLDeuMSe1M+dJ4xfArFynIfw=' 'sha256-lA+EURA/fC0TZq1ATYZvxIQHBc9iTAaBcI+dFMmTn9I=' 'sha256-9RWB/dt3JdkMkmFWT1x7Bd4Mnpgba4OJivFHXr0K47I=';\">\n" +
                "  <script src=\"https://apis.google.com/_/scs/abc-static/_/js/k=gapi.gapi.en.9Ky5Gf3gP0o.O/m=gapi_iframes,googleapis_client/rt=j/sv=1/d=1/ed=1/rs=AHpOoo9ntgUgaVmSKxb6oXsk111880adyg/cb=gapi.loaded_0\" async=\"\"></script><script src=\"chrome-search://local-ntp/assert.js\" integrity=\"sha256-2mX1M62Fd0u8q0dQY2mRsK5S1NS9jJuQAvyE8tD0dkQ=\"></script>\n" +
                "  <script src=\"chrome-search://local-ntp/animations.js\" integrity=\"sha256-1+GSDjMMklBjZY0QiWq+tGupCvajw4Xbn46ect2mZgM=\"></script>\n" +
                "  <script src=\"chrome-search://local-ntp/config.js\" integrity=\"sha256-9RWB/dt3JdkMkmFWT1x7Bd4Mnpgba4OJivFHXr0K47I=\"></script>\n" +
                "  <script src=\"chrome-search://local-ntp/customize.js\" integrity=\"sha256-EtIKSV82ixJHE3AzqhoiVbUGKG+Kd8XS0fFToow29o0=\"></script>\n" +
                "  <script src=\"chrome-search://local-ntp/doodles.js\" integrity=\"sha256-QSyFltV9X3gkyBrg+SMfKvZNXmqPQc6K4B6OYhTuXmw=\"></script>\n" +
                "  <script src=\"chrome-search://local-ntp/local-ntp.js\" integrity=\"sha256-ANdtIo91Yk/zh1YKZ+IXKP1pb00awOjEFMAUld02F6A=\"></script>\n" +
                "  <script src=\"chrome-search://local-ntp/utils.js\" integrity=\"sha256-CbH+xPsBKQxVw5d9blISLDeuMSe1M+dJ4xfArFynIfw=\"></script>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"google\" value=\"notranslate\">\n" +
                "  <meta name=\"referrer\" content=\"strict-origin\">\n" +
                "<script async=\"\" type=\"text/javascript\" charset=\"UTF-8\" src=\"https://www.gstatic.com/og/_/js/k=og.qtm.en_US.68fH34DdlIo.O/rt=j/m=qgl,q_d,qdid,qmd,qcwid,qmutsd,qbd,qapid,qald/exm=qaaw,qabr,qadd,qaid,qalo,qebr,qein,qhaw,qhbr,qhch,qhga,qhid,qhin,qhlo,qhmn,qhpc,qhpr,qhsf,qhtb,qhtt/d=1/ed=1/rs=AA2YrTsKU5GOAj21ictiGAc-eFV4JaMunw\"></script><link type=\"text/css\" rel=\"stylesheet\" href=\"https://www.gstatic.com/og/_/ss/k=og.qtm.ncTi8-FP4q0.L.W.O/m=qdid,qmd,qcwid/excm=qaaw,qabr,qadd,qaid,qalo,qebr,qein,qhaw,qhbr,qhch,qhga,qhid,qhin,qhlo,qhmn,qhpc,qhpr,qhsf,qhtb,qhtt/d=1/ed=1/ct=zgms/rs=AA2YrTt__1zYHTs1-SNO12_hcg2HuU6Y2w\"></head>";
        String strToFind = "a";

        // when
        int count = StringUtils.countOf(origin, strToFind);

        // then
        System.out.println(count);
    }

    @Test
    void reverse() {
        // given
        String str = "io.github.imsejin.common.util.StringUtils#reverse(String)";

        // when
        String reversed = StringUtils.reverse(str);

        // then
        assertThat(reversed).isEqualTo(")gnirtS(esrever#slitUgnirtS.litu.nommoc.nijesmi.buhtig.oi");
    }

}
