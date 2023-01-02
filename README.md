<h1 align="center">🧰 Common Utils</h1>

<p align="center">Common utilities for java programming</p>

<p align="center">
    <!--
    <a href="https://travis-ci.com/github/ImSejin/common-utils">
        <img alt="Travis CI" src="https://img.shields.io/travis/com/ImSejin/common-utils/release?style=flat-square">
    </a>
    -->
    <a href="https://github.com/ImSejin/common-utils/actions/workflows/maven-build.yml">
        <img alt="GitHub Workflow" src="https://img.shields.io/github/actions/workflow/status/ImSejin/common-utils/maven-build.yml?branch=release&style=flat">
    </a>
    <a href="https://codecov.io/gh/ImSejin/common-utils">
        <img alt="Codecov branch" src="https://img.shields.io/codecov/c/github/ImSejin/common-utils/release?label=code%20coverage&style=flat&token=F9DCS57CAN"/>
    </a>
    <a href="https://search.maven.org/artifact/io.github.imsejin/common-utils">
        <img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.imsejin/common-utils?style=flat">
    </a>
    <br/>
    <a href="https://sonarcloud.io/summary/new_code?id=ImSejin_common-utils">
        <img alt="Sonarcloud Quality Gate Status" src="https://sonarcloud.io/api/project_badges/measure?project=ImSejin_common-utils&metric=alert_status"/>
    </a>
    <a href="https://sonarcloud.io/summary/new_code?id=ImSejin_common-utils">
        <img alt="Sonarcloud Maintainability Rating" src="https://sonarcloud.io/api/project_badges/measure?project=ImSejin_common-utils&metric=sqale_rating"/>
    </a>
    <a href="https://www.codacy.com/gh/ImSejin/common-utils/dashboard">
        <img alt="Codacy grade" src="https://img.shields.io/codacy/grade/cda840b8532940ae8c3604696da8eabe?style=flat&logo=codacy">
    </a>
    <img alt="jdk8" src="https://img.shields.io/badge/jdk-8-orange?style=flat">
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
// OS[LINUX, MAC, AIX, SOLARIS, WINDOWS, OTHER]
OS os = OS.getCurrentOS();

assert os.isCurrentOS();

// -----------------------------------------------------------------------------

// DateType[DATE, TIME, DATE_TIME, ALL, F_DATE, F_TIME, ...]
LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(12, 34, 56))
String formatted = dateTime.format(DateType.DATE_TIME.getFormatter());

assert formatted.equals("2000-01-01 12:34:56");
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
