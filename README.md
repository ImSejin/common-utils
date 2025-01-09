<h1 align="center">🧰 Common Utils</h1>

<p align="center">Common utilities for java programming</p>

<p align="center">
    <a href="https://github.com/ImSejin/common-utils/actions/workflows/maven-build.yml">
        <img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/imsejin/common-utils/maven-build.yml?branch=release&style=flat-square&logo=github&label=Build">
    </a>
    <a href="https://codecov.io/gh/ImSejin/common-utils">
        <img alt="Codecov branch" src="https://img.shields.io/codecov/c/github/ImSejin/common-utils/release?logo=codecov&style=flat-square&token=F9DCS57CAN&label=CodeCoverage"/>
    </a>
    <a href="https://central.sonatype.com/artifact/io.github.imsejin/common-utils">
        <img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.imsejin/common-utils?logo=apachemaven&style=flat-square&label=MavenCentral">
    </a>
    <br/>
    <a href="https://sonarcloud.io/summary/overall?id=ImSejin_common-utils">
        <img alt="Sonarcloud Quality Gate Status" src="https://img.shields.io/sonar/quality_gate/ImSejin_common-utils?server=https%3A%2F%2Fsonarcloud.io&style=flat-square&logo=sonarcloud&label=QualityGate"/>
    </a>
    <a href="https://sonarcloud.io/summary/overall?id=ImSejin_common-utils">
        <img alt="Sonarcloud Maintainability Rating" src="https://img.shields.io/sonar/sqale_rating/ImSejin_common-utils?server=https%3A%2F%2Fsonarcloud.io&style=flat-square&logo=sonarcloud&label=Maintainability"/>
    </a>
    <img alt="java17" src="https://img.shields.io/badge/Java-17-orange?style=flat-square">
</p>

# Getting started

### Maven

```xml
<dependency>
    <groupId>io.github.imsejin</groupId>
    <artifactId>common-utils</artifactId>
    <version>x.y.z</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.imsejin:common-utils:x.y.z'
```

# What's inside

### Assertions

```java
List<LocalDate> dates = Arrays.asList(
        LocalDate.of(1999, 12, 31), LocalDate.of(2000, 1, 1), LocalDate.of(2001, 1, 2));

Asserts.that(dates)
        // You can describe error message on assertion failure.
        .describedAs("dates should not be null or empty")
        // You can set what exception will be thrown on assertion failure.
        .thrownBy(IllegalStateException::new)
        // First of all, you have to make sure that variable to be asserted is not null,
        // before call the other assertion methods. Otherwise, it might throw NullPointerException.
        .isNotNull()
        .isNotEmpty()
        .hasSize(3)
        .is(them -> them.get(2).getYear() == 2001)
        .describedAs("dates should not have duplicated elements: '{0}'", dates)
        .doesNotHaveDuplicates()
        .describedAs("dates should contain '2000-01-01' or '2001-01-01': '{0}'", dates)
        .containsAny(LocalDate.of(2000, 1, 1), LocalDate.of(2001, 1, 1))
        .describedAs("dates should not have date in leap year: '{0}'", dates)
        .anyMatch(LocalDate::isLeapYear)
        // Target of assertion is changed from List to Integer.
        .asSize()
        .isPositive()
        // Assertion will fail and throw IllegalStateException on this step.
        .isGreaterThan(3);
```

### Constants

```java
// Locale[ALBANIAN, ARABIC, BELARUSIAN, BULGARIAN, ...]
Locale[] languages = Locales.getLanguages();
// Locale[ALBANIA, ALGERIA, ARGENTINA, AUSTRALIA, ...]
Locale[] countries = Locales.getCountries();

// -----------------------------------------------------------------------------

// Platform[AIX, SOLARIS, LINUX, MACOS_ARM64, MACOS_X64, WINDOWS, UNKNOWN]
Platform platform = Platform.getCurrentPlatform();

assert platform.isCurrent();
```

### Tools

```java
Stopwatch stopwatch = new Stopwatch(TimeUnit.MILLISECONDS);

stopwatch.start("First task");
TimeUnit.SECONDS.sleep(2);
stopwatch.stop();

stopwatch.start("Second task");
TimeUnit.SECONDS.sleep(1);
stopwatch.stop();

stopwatch.getTotalTime(); // About 3000.0 ms
stopwatch.setTimeUnit(TimeUnit.SECONDS);
stopwatch.getTotalTime(); // About 3.0 sec
```

### Utilities

```java
int[][] numbers = {{0, 1}, null, {2}, {}, {3, 4, 5}};
Integer[][] integers = (Integer[][]) ArrayUtils.wrap(numbers);
int[][] ints = (int[][]) ArrayUtils.unwrap(integers);

assert Objects.deepEquals(ints, numbers);

// -----------------------------------------------------------------------------

List<Character> greekAlphabets = Arrays.asList('Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ');

// [['Α', 'Β', 'Γ'], ['Δ', 'Ε', 'Ζ']]
List<List<Character>> bySize = CollectionUtils.partitionBySize(greekAlphabets, 3);
// [['Α', 'Β'], ['Γ', 'Δ'], ['Ε', 'Ζ']]
List<List<Character>> byCount = CollectionUtils.partitionByCount(greekAlphabets, 3);
```
