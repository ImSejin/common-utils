/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.constant;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Locale constants
 *
 * <p> These also provide many locales that {@link Locale} does not provide as constant.
 *
 * @see DateFormat#getAvailableLocales()
 * @see <a href="https://www.oracle.com/java/technologies/javase/jdk17-suported-locales.html">
 * Java 17 Supported Locales</a>
 */
public final class Locales {

    @ExcludeFromGeneratedJacocoReport
    private Locales() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    // Only language -----------------------------------------------------------------------------------

    public static final Locale ALBANIAN = Locale.forLanguageTag("sq");
    public static final Locale ARABIC = Locale.forLanguageTag("ar");
    public static final Locale BELARUSIAN = Locale.forLanguageTag("be");
    public static final Locale BULGARIAN = Locale.forLanguageTag("bg");
    public static final Locale CATALAN = Locale.forLanguageTag("ca");
    public static final Locale CHINESE = Locale.CHINESE;
    public static final Locale CROATIAN = Locale.forLanguageTag("hr");
    public static final Locale CZECH = Locale.forLanguageTag("cs");
    public static final Locale DANISH = Locale.forLanguageTag("da");
    public static final Locale DUTCH = Locale.forLanguageTag("nl");
    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale ESTONIAN = Locale.forLanguageTag("et");
    public static final Locale FINNISH = Locale.forLanguageTag("fi");
    public static final Locale FRENCH = Locale.FRENCH;
    public static final Locale GERMAN = Locale.GERMAN;
    public static final Locale GREEK = Locale.forLanguageTag("el");
    public static final Locale HEBREW = Locale.forLanguageTag("he");
    public static final Locale HINDI = Locale.forLanguageTag("hi");
    public static final Locale HUNGARIAN = Locale.forLanguageTag("hu");
    public static final Locale ICELANDIC = Locale.forLanguageTag("is");
    public static final Locale INDONESIAN = Locale.forLanguageTag("id");
    public static final Locale IRISH = Locale.forLanguageTag("ga");
    public static final Locale ITALIAN = Locale.ITALIAN;
    public static final Locale JAPANESE = Locale.JAPANESE;
    public static final Locale KOREAN = Locale.KOREAN;
    public static final Locale LATVIAN = Locale.forLanguageTag("lv");
    public static final Locale LITHUANIAN = Locale.forLanguageTag("lt");
    public static final Locale MACEDONIAN = Locale.forLanguageTag("mk");
    public static final Locale MALAY = Locale.forLanguageTag("ms");
    public static final Locale MALTESE = Locale.forLanguageTag("mt");
    public static final Locale NORWEGIAN = Locale.forLanguageTag("no");
    public static final Locale NORWEGIAN_BOKMAL = Locale.forLanguageTag("nb");
    public static final Locale NORWEGIAN_NYNORSK = Locale.forLanguageTag("nn");
    public static final Locale POLISH = Locale.forLanguageTag("pl");
    public static final Locale PORTUGUESE = Locale.forLanguageTag("pt");
    public static final Locale ROMANIAN = Locale.forLanguageTag("ro");
    public static final Locale RUSSIAN = Locale.forLanguageTag("ru");
    public static final Locale SERBIAN = Locale.forLanguageTag("sr");
    public static final Locale SLOVAK = Locale.forLanguageTag("sk");
    public static final Locale SLOVENIAN = Locale.forLanguageTag("sl");
    public static final Locale SPANISH = Locale.forLanguageTag("es");
    public static final Locale SWEDISH = Locale.forLanguageTag("sv");
    public static final Locale THAI = Locale.forLanguageTag("th");
    public static final Locale TURKISH = Locale.forLanguageTag("tr");
    public static final Locale UKRAINIAN = Locale.forLanguageTag("uk");
    public static final Locale VIETNAMESE = Locale.forLanguageTag("vi");

    // Countries  --------------------------------------------------------------------------------------

    public static final Locale ALBANIA = Locale.forLanguageTag("sq-AL");
    public static final Locale ALGERIA = Locale.forLanguageTag("ar-DZ");
    public static final Locale ARGENTINA = Locale.forLanguageTag("es-AR");
    public static final Locale AUSTRALIA = Locale.forLanguageTag("en-AU");
    public static final Locale AUSTRIA = Locale.forLanguageTag("de-AT");
    public static final Locale BAHRAIN = Locale.forLanguageTag("ar-BH");
    public static final Locale BELARUS = Locale.forLanguageTag("be-BY");
    public static final Locale BELGIUM_DUTCH = Locale.forLanguageTag("nl-BE");
    public static final Locale BELGIUM_FRANCE = Locale.forLanguageTag("fr-BE");
    public static final Locale BOLIVIA = Locale.forLanguageTag("es-BO");
    public static final Locale BOSNIA_AND_HERZEGOVINA = Locale.forLanguageTag("sr-BA");
    // Ignore "sr-Latn-BA".
    public static final Locale BRAZIL = Locale.forLanguageTag("pt-BR");
    public static final Locale BULGARIA = Locale.forLanguageTag("bg-BG");
    public static final Locale CANADA = Locale.CANADA;
    public static final Locale CANADA_FRENCH = Locale.CANADA_FRENCH;
    public static final Locale CHILE = Locale.forLanguageTag("es-CL");
    public static final Locale CHINA = Locale.CHINA;
    public static final Locale COLOMBIA = Locale.forLanguageTag("es-CO");
    public static final Locale COSTA_RICA = Locale.forLanguageTag("es-CR");
    public static final Locale CROATIA = Locale.forLanguageTag("hr-HR");
    public static final Locale CYPRUS = Locale.forLanguageTag("el-CY");
    public static final Locale CZECH_REPUBLIC = Locale.forLanguageTag("cs-CZ");
    public static final Locale DENMARK = Locale.forLanguageTag("da-DK");
    public static final Locale DOMINICAN_REPUBLIC = Locale.forLanguageTag("es-DO");
    public static final Locale ECUADOR = Locale.forLanguageTag("es-EC");
    public static final Locale EGYPT = Locale.forLanguageTag("ar-EG");
    public static final Locale EL_SALVADOR = Locale.forLanguageTag("es-SV");
    public static final Locale ESTONIA = Locale.forLanguageTag("et-EE");
    public static final Locale FINLAND = Locale.forLanguageTag("fi-FI");
    public static final Locale FRANCE = Locale.FRANCE;
    public static final Locale GERMANY = Locale.GERMANY;
    public static final Locale GREECE = Locale.forLanguageTag("el-GR");
    public static final Locale GUATEMALA = Locale.forLanguageTag("es-GT");
    public static final Locale HONDURAS = Locale.forLanguageTag("es-HN");
    public static final Locale HONG_KONG = Locale.forLanguageTag("zh-HK");
    public static final Locale HUNGARY = Locale.forLanguageTag("hu-HU");
    public static final Locale ICELAND = Locale.forLanguageTag("is-IS");
    public static final Locale INDIA = Locale.forLanguageTag("en-IN");
    public static final Locale INDIA_HINDI = Locale.forLanguageTag("hi-IN");
    public static final Locale INDONESIA = Locale.forLanguageTag("id-ID");
    public static final Locale IRAQ = Locale.forLanguageTag("ar-IQ");
    public static final Locale IRELAND = Locale.forLanguageTag("en-IE");
    public static final Locale IRELAND_IRISH = Locale.forLanguageTag("ga-IE");
    public static final Locale ISRAEL = Locale.forLanguageTag("he-IL");
    public static final Locale ITALY = Locale.ITALY;
    public static final Locale JAPAN = Locale.JAPAN;
    // Ignore "ja-JP-u-ca-japanese".
    // Ignore "ja-JP-x-lvariant-JP".
    public static final Locale JORDAN = Locale.forLanguageTag("ar-JO");
    public static final Locale KUWAIT = Locale.forLanguageTag("ar-KW");
    public static final Locale LATVIA = Locale.forLanguageTag("lv-LV");
    public static final Locale LEBANON = Locale.forLanguageTag("ar-LB");
    public static final Locale LIBYA = Locale.forLanguageTag("ar-LY");
    public static final Locale LITHUANIA = Locale.forLanguageTag("lt-LT");
    public static final Locale LUXEMBOURG = Locale.forLanguageTag("fr-LU");
    public static final Locale LUXEMBOURG_GERMAN = Locale.forLanguageTag("de-LU");
    public static final Locale MACEDONIA = Locale.forLanguageTag("mk-MK");
    public static final Locale MALAYSIA = Locale.forLanguageTag("ms-MY");
    public static final Locale MALTA_ENGLISH = Locale.forLanguageTag("en-MT");
    public static final Locale MALTA_MALTESE = Locale.forLanguageTag("mt-MT");
    public static final Locale MEXICO = Locale.forLanguageTag("es-MX");
    public static final Locale MONTENEGRO = Locale.forLanguageTag("sr-ME");
    // Ignore "sr-Latn-ME".
    public static final Locale MOROCCO = Locale.forLanguageTag("ar-MA");
    public static final Locale NETHERLANDS = Locale.forLanguageTag("nl-NL");
    public static final Locale NEW_ZEALAND = Locale.forLanguageTag("en-NZ");
    public static final Locale NICARAGUA = Locale.forLanguageTag("es-NI");
    public static final Locale NORWAY_NORWEGIAN = Locale.forLanguageTag("no-NO");
    public static final Locale NORWAY_NORWEGIAN_BOKMAL = Locale.forLanguageTag("nb-NO");
    public static final Locale NORWAY_NORWEGIAN_NYNORSK = Locale.forLanguageTag("nn-NO");
    // Ignore "no-NO-x-lvariant-NY".
    public static final Locale OMAN = Locale.forLanguageTag("ar-OM");
    public static final Locale PANAMA = Locale.forLanguageTag("es-PA");
    public static final Locale PARAGUAY = Locale.forLanguageTag("es-PY");
    public static final Locale PERU = Locale.forLanguageTag("es-PE");
    public static final Locale PHILIPPINES = Locale.forLanguageTag("en-PH");
    public static final Locale POLAND = Locale.forLanguageTag("pl-PL");
    public static final Locale PORTUGAL = Locale.forLanguageTag("pt-PT");
    public static final Locale PUERTO_RICO = Locale.forLanguageTag("es-PR");
    public static final Locale QATAR = Locale.forLanguageTag("ar-QA");
    public static final Locale ROMANIA = Locale.forLanguageTag("ro-RO");
    public static final Locale RUSSIA = Locale.forLanguageTag("ru-RU");
    public static final Locale SAUDI_ARABIA = Locale.forLanguageTag("ar-SA");
    public static final Locale SERBIA = Locale.forLanguageTag("sr-RS");
    // Ignore "sr-Latn-RS".
    public static final Locale SINGAPORE_ENGLISH = Locale.forLanguageTag("en-SG");
    public static final Locale SINGAPORE_CHINESE = Locale.forLanguageTag("zh-SG");
    public static final Locale SLOVAKIA = Locale.forLanguageTag("sk-SK");
    public static final Locale SLOVENIA = Locale.forLanguageTag("sl-SI");
    public static final Locale SOUTH_AFRICA = Locale.forLanguageTag("en-ZA");
    public static final Locale KOREA = Locale.KOREA;
    public static final Locale SPAIN_SPANISH = Locale.forLanguageTag("es-ES");
    public static final Locale SPAIN_CATALAN = Locale.forLanguageTag("ca-ES");
    public static final Locale SUDAN = Locale.forLanguageTag("ar-SD");
    public static final Locale SWEDEN = Locale.forLanguageTag("sv-SE");
    public static final Locale SWITZERLAND_FRANCE = Locale.forLanguageTag("fr-CH");
    public static final Locale SWITZERLAND_GERMAN = Locale.forLanguageTag("de-CH");
    public static final Locale SWITZERLAND_ITALIAN = Locale.forLanguageTag("it-CH");
    public static final Locale SYRIA = Locale.forLanguageTag("ar-SY");
    public static final Locale TAIWAN = Locale.TRADITIONAL_CHINESE;
    public static final Locale THAILAND = Locale.forLanguageTag("th-TH");
    // Ignore "th-TH-u-ca-buddhist".
    // Ignore "th-TH-u-ca-buddhist-nu-thai".
    // Ignore "th-TH-x-lvariant-TH".
    public static final Locale TUNISIA = Locale.forLanguageTag("ar-TN");
    public static final Locale TURKEY = Locale.forLanguageTag("tr-TR");
    public static final Locale UKRAINE = Locale.forLanguageTag("uk-UA");
    public static final Locale UAE = Locale.forLanguageTag("ar-AE");
    public static final Locale UK = Locale.UK;
    public static final Locale US = Locale.US;
    public static final Locale US_SPANISH = Locale.forLanguageTag("es-US");
    public static final Locale URUGUAY = Locale.forLanguageTag("es-UY");
    public static final Locale VENEZUELA = Locale.forLanguageTag("es-VE");
    public static final Locale VIETNAM = Locale.forLanguageTag("vi-VN");
    public static final Locale YEMEN = Locale.forLanguageTag("ar-YE");

}
