package io.github.imsejin.common.util;

import io.github.imsejin.common.tool.Stopwatch;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

class StringUtilsTest {

    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam auctor nisl, sed tristique lacus dictum nec. Duis aliquam risus non eros dignissim, sed venenatis tellus dapibus. Nullam sapien dolor, commodo non semper ac, faucibus auctor lorem. Vestibulum suscipit enim quis ligula consectetur bibendum. Phasellus aliquet, libero vel convallis hendrerit, ante est rhoncus quam, sit amet finibus odio libero vitae lacus. Duis in dictum tellus. Sed quis turpis dictum enim ullamcorper semper et sit amet eros. Praesent posuere nisl vitae euismod malesuada. Nam accumsan est cursus leo porta, vitae bibendum metus faucibus. Pellentesque sollicitudin, lectus ut lobortis pellentesque, elit lorem pharetra diam, id molestie urna est a libero.\n" +
            "Ut sit amet fringilla velit, sed pharetra velit. Morbi porttitor molestie velit ut sodales. Phasellus non tristique tortor. Maecenas leo magna, bibendum eget mi vitae, posuere dictum dolor. Donec dui sapien, pharetra id nulla vitae, euismod suscipit lorem. Nam ac vehicula ipsum. Nulla elementum neque eu ante interdum, quis fermentum odio accumsan. Pellentesque consequat sed leo in posuere. Proin convallis efficitur leo, in ultricies magna accumsan bibendum. Integer venenatis tincidunt turpis, nec ornare neque auctor quis. Integer eget est justo.\n" +
            "Etiam cursus dapibus mi eget luctus. Nulla at tincidunt massa. Ut rhoncus dui sed enim tempor fermentum. In sem est, condimentum nec maximus a, pretium id lacus. Curabitur nec ipsum vel arcu mattis iaculis ac in velit. Nullam vel nunc erat. Phasellus scelerisque dignissim lacus sed fringilla. Nam congue dui a velit semper, in dictum lectus finibus. Etiam mi mi, porta sit amet orci id, pellentesque feugiat tortor. Etiam tortor ligula, consequat iaculis bibendum vitae, tristique a eros. Quisque eu erat et urna vehicula gravida. Etiam eget semper massa. Integer varius justo ante, ac fringilla magna blandit non. Sed elementum ligula odio, sit amet cursus nisi suscipit non.\n" +
            "Sed fermentum non ante sit amet sodales. Phasellus placerat turpis id mattis rhoncus. Aliquam erat volutpat. Aliquam blandit libero sit amet lorem efficitur, in elementum sapien consequat. Praesent et lectus erat. Nunc posuere lacinia tortor, vel rutrum quam maximus eget. Ut et ex ac purus euismod vehicula. Fusce in risus arcu. Etiam vel risus non dolor fringilla viverra. Nullam id sollicitudin magna, non fermentum dolor. Nulla facilisi.\n" +
            "Fusce bibendum lectus sed fringilla finibus. Ut faucibus faucibus turpis, ut accumsan lorem faucibus sit amet. Pellentesque varius sodales nibh, eget consectetur nisi finibus tincidunt. Integer pulvinar convallis mauris ut mollis. Phasellus ut imperdiet est. Etiam interdum ornare lacus ut vulputate. Mauris vulputate volutpat risus in ultricies. Sed tincidunt nibh vel urna ultricies, at vehicula nunc pellentesque. Duis dictum sagittis mi convallis feugiat.\n" +
            "Suspendisse suscipit imperdiet quam, in finibus dui laoreet sit amet. Pellentesque sapien elit, hendrerit ut eros vel, pellentesque tincidunt magna. Mauris ac eros purus. Sed sagittis mi sit amet sodales euismod. Curabitur tempus, leo at mattis ultricies, ex urna sodales nunc, ut elementum quam libero vitae dui. Phasellus faucibus arcu eget elit imperdiet elementum. Fusce vel leo pulvinar, interdum enim vel, posuere enim. Maecenas facilisis ullamcorper ultricies. Ut ultrices faucibus tellus, id condimentum enim auctor non. Donec erat risus, venenatis et molestie id, feugiat a ipsum. Vivamus venenatis nibh neque, non aliquam odio interdum sit amet. Phasellus eu ipsum accumsan, mattis elit et, tincidunt ante. Phasellus nec eros pretium, malesuada nisi vitae, interdum mi. Nullam tincidunt rutrum bibendum. Integer id sollicitudin velit. Donec a vehicula metus.\n" +
            "Curabitur condimentum bibendum mi eu consequat. Mauris fermentum urna vel tincidunt posuere. Interdum et malesuada fames ac ante ipsum primis in faucibus. Morbi dolor nulla, luctus in felis vitae, rutrum laoreet risus. Cras lacinia vitae ipsum quis elementum. Nunc ante magna, egestas eu ultrices lacinia, euismod nec odio. Maecenas mattis tortor at augue ultricies, at pulvinar quam rhoncus. Quisque sed sollicitudin metus. Etiam pellentesque ligula id nisl bibendum sodales. Aliquam ultricies quam lorem. Cras id mollis enim, quis euismod lorem. In pulvinar mi eget nulla rutrum, eu maximus eros sodales. Nulla posuere ex ac justo eleifend pharetra. Nunc malesuada urna at eleifend maximus. Sed at lacus ac ipsum facilisis vulputate eget non mi. Nulla eleifend, arcu et aliquam aliquam, augue nisi interdum velit, eget elementum ex ante nec purus.\n" +
            "In congue metus eget nibh laoreet, non pellentesque ex lacinia. Nunc eleifend vitae arcu nec ultricies. Pellentesque euismod, dui eget fermentum elementum, nisi leo condimentum risus, vel euismod lectus massa quis odio. Vivamus id est eu erat iaculis posuere sit amet et arcu. Integer id dapibus purus. Quisque in lacus mi. Curabitur faucibus convallis lacus, in elementum orci blandit vel. Donec turpis augue, scelerisque nec fringilla vel, fringilla non quam. Donec arcu ipsum, fermentum eu metus id, luctus varius ligula. Aenean tempus nulla in leo aliquet, in placerat eros consequat. Vivamus eget faucibus est, non luctus odio. Pellentesque ac dui sit amet nulla feugiat lacinia in quis tortor. Sed convallis, magna vitae pellentesque scelerisque, ligula risus fermentum dui, vitae convallis neque augue at sapien. Nam ac faucibus lorem, vitae semper eros. Maecenas porta convallis turpis et aliquet.\n" +
            "Nunc posuere libero non erat varius dapibus. Nulla facilisi. Ut semper sollicitudin mi id finibus. Maecenas vel scelerisque neque. Etiam condimentum hendrerit lectus ac posuere. In suscipit, quam eu vehicula facilisis, elit metus efficitur risus, eu faucibus orci felis vitae quam. Etiam id ante a risus porttitor dignissim. Fusce a tellus et ante rhoncus tempus eu eget mauris. Duis fringilla mollis eros id aliquam. Vivamus placerat tincidunt nulla, vitae blandit ante mollis id. Etiam imperdiet blandit mi eu ornare. Morbi hendrerit efficitur suscipit. Duis eleifend leo nibh, sed mattis arcu viverra a. Duis eget libero lorem.\n" +
            "Nulla consequat sapien fringilla ultrices volutpat. Suspendisse eget ultrices metus. Pellentesque a volutpat sapien. Vivamus commodo congue fermentum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent sed purus mattis, faucibus dui ut, fringilla libero. Cras suscipit ante et est gravida bibendum. Fusce sed mattis libero. Fusce dapibus id nunc vel ultricies. Donec viverra volutpat pretium. Suspendisse enim augue, viverra non tortor et, maximus consectetur neque. Duis eget arcu in nulla aliquet fermentum. Nulla elementum, felis suscipit ultrices laoreet, nisl ipsum lobortis dui, mollis mollis tellus erat vitae tellus. Curabitur nec nibh a ipsum egestas pretium vel vel odio. Aenean ex urna, vehicula vel arcu eu, fringilla venenatis diam. Aenean vel diam et tortor malesuada viverra.";

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
        String strToFind = " ";

        // when
        int count = StringUtils.countOf(LOREM_IPSUM, strToFind);

        // then
        assertThat(count)
                .as("Gets the number of keyword contained by the string")
                .isEqualTo(LOREM_IPSUM.length() - LOREM_IPSUM.replace(strToFind, "").length());
    }

    @Test
    void reverse() {
        // given
        String str = "io.github.imsejin.common.util.StringUtils#reverse(String)";

        // when
        String actual = StringUtils.reverse(str);

        // then
        char[] chars = str.toCharArray();
        char[] expected = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            expected[i] = chars[chars.length - (i + 1)];
        }

        assertThat(actual)
                .as("Reverses each character's position")
                .isEqualTo(new String(expected));
    }

    @Test
    void find() {
        // given
        String textContent = "alpha";
        String src = String.format("<div>%s</div>", textContent);
        String regex = ">(.*)<\\/";

        // when
        String actual = StringUtils.find(src, regex, 1);

        // then
        assertThat(actual).isEqualTo(textContent);
    }

    @Test
    void findWithGroups() {
        // given
        String src = "ST_총수 - 최종편 - 정기영, 백승훈 [完]";
        String regex = "^(.+)_(.+) - ([^-]+).{4}?$";

        // when
        Map<Integer, String> match = StringUtils.find(src, regex, Pattern.MULTILINE, 1, 2, 3);

        // then
        assertThat(match.get(1))
                .as("#1 group")
                .isEqualTo("ST");
        assertThat(match.get(2))
                .as("#2 group")
                .isEqualTo("총수 - 최종편");
        assertThat(match.get(3))
                .as("#3 group")
                .isEqualTo("정기영, 백승훈");
    }

    @ParameterizedTest
    @ValueSource(strings = {"lorem", "ipsum", "is", "simply", "dummy", "text",
            "of", "the", "printing", "and", "typesetting", "industry", ""})
    void chop(String str) {
        // when
        String actual = StringUtils.chop(str);

        // then
        assertThat(actual)
                .as("Removes last character")
                .isEqualTo(StringUtils.isNullOrEmpty(str) ? "" : str.substring(0, str.length() - 1));
        System.out.printf("chop(\"%s\"): \"%s\"\n", str, actual);
    }

    @Test
    void test() {
        Stopwatch stopwatch = new Stopwatch(TimeUnit.MILLISECONDS);

        // given
        final List<String> strings = Arrays.asList(LOREM_IPSUM.split(""));

        // when #1
        stopwatch.start("ArrayList: %,d", strings.size());
        List<String> arrayList = new ArrayList<>(strings);
        arrayList.removeAll(strings);
        stopwatch.stop();
        assertThat(arrayList)
                .as("#1 ArrayList.removeAll(Collection)")
                .isNotNull()
                .isEmpty();

        // when #2
        stopwatch.start("LinkedList: %,d", strings.size());
        List<String> linkedList = new LinkedList<>(strings);
        linkedList.removeAll(strings);
        stopwatch.stop();
        assertThat(linkedList)
                .as("#2 LinkedList.removeAll(Collection)")
                .isNotNull()
                .isEmpty();

        // when #3
        Set<String> set = new HashSet<>(strings);
        stopwatch.start("HashSet: %,d", set.size());
        HashSet<String> hashSet = new HashSet<>(strings);
        hashSet.removeAll(strings);
        stopwatch.stop();
        assertThat(hashSet)
                .as("#3 HashSet.removeAll(Collection)")
                .isNotNull()
                .isEmpty();

        // then
        System.out.println(stopwatch.getStatistics());
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, 0L, Long.MAX_VALUE})
    void formatComma1(long amount) {
        // when
        String actual = StringUtils.formatComma(amount);

        // then
        assertThat(actual.replace(",", ""))
                .as("Formats comma with long value")
                .isEqualTo(String.valueOf(amount));
    }

    @ParameterizedTest
    @ValueSource(strings = {Long.MIN_VALUE + "", "0", Long.MAX_VALUE + ""})
    void formatComma2(String amount) {
        // when
        String actual = StringUtils.formatComma(amount);

        // then
        assertThat(actual.replace(",", ""))
                .as("Formats comma with numeric string")
                .isEqualTo(amount);
    }

}
