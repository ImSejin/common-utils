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
        // You can set message on error.
        .as("dates should not be null or empty")
        // You can set any sub type of RuntimeException on error.
        .exception(IllegalStateException::new)
        // First of all, you have to make sure that variable to be asserted is not null,
        // before call the other assertion methods. Otherwise, it might throw NullPointerException.
        .isNotNull()
        .hasElement()
        .hasSizeOf(3)
        .as("dates should not have duplicated elements: '{0}'", dates)
        .doesNotHaveDuplicates()
        .as("dates should contain '2001-01-01' or '2000-01-02': '{0}'", dates)
        .containsAny(LocalDate.of(2001, 1, 1), LocalDate.of(2000, 1, 2))
        .as("dates should not have date in leap year: '{0}'", dates)
        // Assertion will fail and throw IllegalStateException on this step.
        .noneMatch(LocalDate::isLeapYear);
```

### Constants

```java
// OS[LINUX, MAC, AIX, SOLARIS, WINDOWS, OTHER]
OS os = OS.getCurrentOS();

assert os.isCurrentOS();

////////////////////////////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////

List<Character> greekAlphabets = Arrays.asList('Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ');

// [['Α', 'Β', 'Γ'], ['Δ', 'Ε', 'Ζ']]
List<List<Character>> bySize = CollectionUtils.partitionBySize(greekAlphabets, 3);
// [['Α', 'Β'], ['Γ', 'Δ'], ['Ε', 'Ζ']]
List<List<Character>> byCount = CollectionUtils.partitionByCount(greekAlphabets, 3);
```
