<h1 align="center">🧰 Common Utils</h1>

<p align="center">Common utilities for java programming</p>

<p align="center">
    <!--
    <a href="https://travis-ci.com/github/ImSejin/common-utils">
        <img alt="Travis CI" src="https://img.shields.io/travis/com/ImSejin/common-utils/release?style=flat-square">
    </a>
    -->
    <a href="https://github.com/ImSejin/common-utils/actions/workflows/maven-build.yml">
        <img alt="GitHub Workflow" src="https://img.shields.io/github/workflow/status/ImSejin/common-utils/Java%20CI%20with%20Maven/release?style=flat-square">
    </a>
    <a href="https://codecov.io/gh/ImSejin/common-utils">
        <img alt="Codecov branch" src="https://img.shields.io/codecov/c/github/ImSejin/common-utils/release?label=code%20coverage&style=flat-square&token=F9DCS57CAN"/>
    </a>
    <a href="https://search.maven.org/artifact/io.github.imsejin/common-utils">
        <img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.imsejin/common-utils?style=flat-square">
    </a>
    <br/>
    <a href="https://lgtm.com/projects/g/ImSejin/common-utils/context:java">
        <img alt="Lgtm grade" src="https://img.shields.io/lgtm/grade/java/github/ImSejin/common-utils.svg?label=lgtm%3A%20code%20quality&&style=flat-square"/>
    </a>
    <a href="https://www.codacy.com/gh/ImSejin/common-utils/dashboard">
        <img alt="Codacy grade" src="https://img.shields.io/codacy/grade/cda840b8532940ae8c3604696da8eabe?label=codacy%3A%20code%20quality&style=flat-square">
    </a>
    <img alt="jdk8" src="https://img.shields.io/badge/jdk-8-orange?style=flat-square">
</p>

# Getting started

### Maven

```xml
<dependency>
  <groupId>io.github.imsejin</groupId>
  <artifactId>common-utils</artifactId>
  <version>${common-utils.version}</version>
</dependency>
```

### Gradle

```groovy
implementation group: "io.github.imsejin", name: "common-utils", version: "$commonUtilsVersion"
```

# What's inside

### Assertions

```java
List<LocalDate> dates = Arrays.asList(
        LocalDate.of(1999, 12, 31), LocalDate.of(2000, 1, 1), LocalDate.of(2001, 1, 2));

Asserts.that(dates)
        .as("dates should not be null or empty") // You can set message on error.
        .exception(IllegalStateException::new) // You can set type of Exception on error.
        .isNotNull()
        .hasElement()
        .hasSizeOf(3)
        .as("dates should not have duplicated elements: '{0}'", dates)
        .doesNotHaveDuplicates()
        .as("dates should contain '2001-01-01' or '2000-01-02': '{0}'", dates)
        .containsAny(LocalDate.of(2001, 1, 1), LocalDate.of(2000, 1, 2))
        .as("dates should not have date in leap year: '{0}'", dates)
        .noneMatch(LocalDate::isLeapYear); // Will throw IllegalStateException on this step. 
```

### Constants

```java
// OS[LINUX, MAC, AIX, SOLARIS, WINDOWS, OTHER]
OS os = OS.getCurrentOS();

// DateType[DATE, TIME, DATE_TIME, ALL, F_DATE, F_TIME, ...]
LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(12, 34, 56))
String formatted = dateTime.format(DateType.DATE_TIME.getFormatter());
assert formatted.equals("2000-01-01 12:34:56");
```

### Tools

```java
Stopwatch stopwatch = new Stopwatch(TimeUnit.MILLISECONDS);

stopwatch.start("Job starts!");
TimeUnit.SECONDS.sleep(2);
stopwatch.stop();

stopwatch.getTotalTime(); // About 2000.0 ms
```

### Utilities

```java
int[] ints = {0, 1, 2, 3, 4};
Integer[] integers = (Integer[]) ArrayUtils.wrap(ints);
assert Objects.deepEquals(ints, integers);

List<Character> greekAlphabets = Arrays.asList('Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ');
// [['Α', 'Β', 'Γ'], ['Δ', 'Ε', 'Ζ']]
List<List<Character>> bySize = CollectionUtils.partitionBySize(greekAlphabets, 3);
// [['Α', 'Β'], ['Γ', 'Δ'], ['Ε', 'Ζ']]
List<List<Character>> byCount = CollectionUtils.partitionByCount(greekAlphabets, 3);
```
