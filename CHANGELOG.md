# Table of Contents

- [v0.10.0](#v0100):
- [v0.9.0](#v090): 2022-05-26
- [v0.8.0](#v080): 2022-04-10
- [v0.7.1](#v071): 2022-02-20
- [v0.7.0](#v070): 2021-10-24
- [v0.6.0](#v060): 2021-08-21
- [v0.5.0](#v050): 2021-07-11
- [v0.4.7](#v047): 2021-05-27
- [v0.4.6](#v046): 2021-05-27
- [v0.4.5](#v045): 2021-05-25
- [v0.4.4](#v044): 2021-05-23
- [v0.4.3](#v043): 2021-05-23
- [v0.4.2](#v042): 2021-05-23
- [v0.4.1](#v041): 2021-05-22
- [v0.4.0](#v040): 2021-05-22
- [v0.3.4](#v034): 2020-12-18
- [v0.3.3](#v033): 2020-11-28
- [v0.3.2](#v032): 2020-11-04
- [v0.3.1](#v031): 2020-10-11
- [v0.3.0](#v030): 2020-10-07
- [v0.2.1](#v021): 2020-10-04
- [v0.2.0](#v020): 2020-10-03
- [v0.1.2](#v012): 2020-09-27
- [v0.1.1](#v011): 2020-09-18
- [v0.1.0](#v010): 2020-09-17

# v0.10.0

### Modification

- ♻️ Refactor: tool `Stopwatch` 
- ⚡️ Improve: static code analysis on IntelliJ IDEA

### New features

- 📦️ Add: module `io`
- ✨ Add: constant `Locales`
- ✨ Add: tool `RandomString`
- ✨ Add: assertion classes `DurationAssert`, `PeriodAssert`, `UrlAssert`
- ✨ Add: method `getName(String)` in `FilenameUtils`

### Troubleshooting

- 🐞 Fix: StackOverflowException on `ArrayUtils.toString(Object)`

### Dependencies

- ➕ Add: dependencies `annotations`, `common-compress`
- ➕ Add: test dependency `memoryfilesystem`
- ⬆️ Upgrade: test dependency `assert-core` from `3.22.0` to `3.23.1`

# v0.9.0

### Modification

- 🔥 Remove: assertion methods `isBeforeMidnight()`, `isBeforeOrEqualToMidnight()`, `isAfterMidnight()`
  , `isAfterOrEqualToMidnight()` in `LocalTimeAssert`
- 🔥 Remove: assertion methods `isZero()`, `isNotZero()` in `CharacterAssert`
- 🔥 Remove: methods `isLeapYear(int)`, `today()`, `today(DateType)`, `yesterday()`, `yesterday(DateType)`, `now()`
  , `getLastDateOfMonth(int, int)`, `getLastDateOfMonth(int, Month)`, `getLastDateOfMonth(String, String)`
  , `getLastDateOfMonth(String, Month)` in `DateTimeUtils`
- 🚚 Move: package `security.crypto` to `security.crypto.aes`
- 🚚 Rename: assertion class `AbstractTemporalAssert` to `AbstractTemporalAccessorAssert`
- 🚚 Rename: assertion class `AbstractChronoLocalDateAssert` to `ChronoLocalDateAssert`
- 🚚 Rename: assertion class `AbstractChronoLocalDateTimeAssert` to `ChronoLocalDateTimeAssert`
- 🚚 Rename: assertion class `AbstractChronoZonedLocalDateAssert` to `ChronoZonedDateTimeAssert`
- ♻️ Replace: method `isPrime(int)` with `isPrime(long)` in `MathUtils`
- ⚡️ Integrate: `ArrayAssert`, `ListAssert` to `RandomAccessIterationAssertable`
- 🔨 Modify: default description of assertion

### New features

- ✨ Add: implementation `RSA` of `Crypto`
- ✨ Add: assertion classes `ListAssert`, `MonthAssert`, `MonthDayAssert`
- ✨ Add: assertion composition `RandomAccessIterationAssertable`
- ✨ Add: method `isFinalClass()` in `ClassAssert`

### Troubleshooting

- 🐞 Fix: wrong computation of `MathUtils#isPrime(int)`

### Dependencies

- ⬆️ Upgrade: test dependency `lombok` from `1.18.22` to `1.18.24`

# v0.8.0

### Modification

- 🚚 Move: all methods in `TypeClassifier` to `ClassUtils`
- 🚚 Move: package `tool.crypto` to `security.crypto`
- 🚚 Move: package of `ClassAssert`, `PackageAssert` from `assertion.reflect` to `assertion.lang`
- 🚚 Move: package of `ArrayAssert` from `assertion.array` to `assertion.lang`
- 🚚 Move: package of `StringAssert` from `assertion.chars` to `assertion.lang`
- 🚚 Move: package of `BooleanAssert`, `CharacterAssert`, `DoubleAssert`, `FloatAssert`, `NumberAssert`
  from `assertion.primitive` to `assertion.lang`
- 🔥 Remove: methods `instantiate(Class, Class[], Object[])`, `invoke(Class, Object, String, Class[], Object[])`
  in `ReflectionUtils`
- 🔥 Remove: redundant annotation `@Nonnull` on parameter
- ♻️ Replace: methods `box` with `wrap(Object)` in `ArrayUtils`
- ♻️ Replace: methods `unbox` with `unwrap(Object)` in `ArrayUtils`
- ♻️ Replace: `assertion.DecimalNumberAssertion` with `assertion.composition.DecimalNumberAssertion`
- ♻️ Replace: `assertion.object.AbstractObjectAssert` with `assertion.lang.ObjectAssert`
- ♻️ Replace: `assertion.chars.AbstractCharSequenceAssert` with `assertion.lang.CharSequenceAssert`
- ♻️ Replace: `assertion.time.OffsetAssertion` with `assertion.composition.OffsetAssertable`
- ♻️ Replace: `assertion.time.YearAssertion` with `assertion.composition.YearAssertable`
- ♻️ Replace: `assertion.collection.AbstractCollectionAssert` with `assertion.util.CollectionAssert`
- ♻️ Replace: `assertion.map.AbstractMapAssert` with `assertion.util.MapAssert`
- ✨ Support: multiple-dimensional array by `wrap(Object)`, `unwrap(Object)` in `ArrayUtils`
- ✨ Support: array by `wrap(Class)`, `unwrap(Class)` in `ClassUtils`
- ✨ Support: recursive conversion on method `ArrayUtils#toString(Object)`
- ⚡️ Improve: method `getInheritedFields(Class)` in `ReflectionUtils`
- ⚡️ Improve: formatting for array on `Descriptor`
- ⚡️ Integrate: `ArrayAssert`, `CollectionAssert` with `IterationAssertable`
- 🗑️ Deprecate: `Descriptor#equals(Object)`

### New features

- 📦️ Add: package `model.graph.traverse`
- ✨ Add: model `Graph`(implementation: `DirectedGraph`, `UndirectedGraph`)
- ✨ Add: security model `Verification`
- ✨ Add: assertion composition `IterationAssertable`
- ✨ Add: variable `EMPTY_ARRAY_MAP` in `ArrayUtils`
- ✨ Add: method `StringUtils#indexOfCurrentClosingBracket(String, int, char, char)`
- ✨ Add: method `InstantAssert#asEpochMilli()`
- ✨ Add: methods `asSecondOfDay()`, `asNanoOfDay()` in `LocalTimeAssert`
- ✨ Add: methods `asValue()`, `asLength()` in `YearAssert`
- ✨ Add: methods `asYear()`, `asMonthValue()` in `YearMonthAssert`
- ✨ Add: methods `getAllExtendedOrImplementedTypesAsSet(Class)`, `getAllExtendedOrImplementedTypesAsGraph(Class)`
  , `resolveActualTypes(Type)` in `ClassUtils`
- ✨ Add: methods `resolveArrayType(Class, int)`, `resolveActualComponentType(Class)`, `dimensionOf(Class)`
  in `ArrayUtils`
- ✨ Add: methods `instantiate(Constructor, Object...)`, `invoke(Method, Object, Object[])`
  , `execute(Executable, Object, Object[])` in `ReflectionUtils`

### Troubleshooting

- 🔒️ Fix: security issues on reflection
- 🐞 Fix: wrong computation in `ArrayUtils#toString(Object)`
- 🐞 Fix: wrong type inference in `AbstractObjectAssert`
- 🐞 Fix: wrong type inference in `ArrayAssert`

### Dependencies

- ⬆️ Upgrade: dependency `gson` from `2.8.7` to `2.9.0`
- ⬆️ Upgrade: test dependency `junit5` from `5.8.1` to `5.8.2`
- ⬆️ Upgrade: test dependency `assertj-core` from `3.21.0` to `3.22.0`
- ⬆️ Upgrade: test dependency `spock-core` from `2.0-groovy-3.0` to `2.1-groovy-3.0`
- ⬆️ Upgrade: build dependency `maven-compiler-plugin` from `3.8.1` to `3.10.1`
- ⬆️ Upgrade: build dependency `maven-jar-plugin` from `3.2.0` to `3.2.2`
- ⬆️ Upgrade: build dependency `gmavenplus-plugin` from `1.13.0` to `1.13.1`
- ⬆️ Upgrade: build dependency `jacoco-maven-plugin` from `0.8.7` to `0.8.8`

# v0.7.1

### Modification

- ⚡️ Improve: type inference of `AbstractObjectAssert`

### New features

- ✨ Add: method `ordinalIndexOf(String, char, int)` in `StringUtils`
- ✨ Add: protected method `merge(Descriptor, Descriptor)` in `Descriptor`
- ✨ Add: method `asName()` in `AbstractFileAssert`

### Troubleshooting

- 🐞 Fix: wrong computation of `ReflectionUtils#getInheritedFields(Class)` on groovy class
- 🐞 Fix: wrong computation to sustain states when convert to other assertion
- 🐞 Fix: wrong assertion of `hasName(String)`, `hasExtension(String)` in `AbstractFileAssert`
- 🐞 Fix: wrong assertion of `isEqualTo(BigDecimal)`, `isNotEqualTo(BigDecimal)` in `BigDecimalAssert`

# v0.7.0

### Modification

- 🚚 Move: package of classes `AbstractChrono*Assert` to `io.github.imsejin.common.assertion.time.chrono`
- 🔥 Remove: methods `anyNullOrBlank(Collection)`, `allNullOrBlank(Collection)` in `StringUtils`
- 🔥 Remove: methods `toPositive`, `toNegative` that has a parameter as primitive type in `NumberUtils`
- ♻️ Change: default description for assertions `AbstractObjectAssert`, `AbstractCharSequenceAssert`
  , `AbstractChronoLocalDateAssert`, `AbstractChronoLocalDateTimeAssert`, `AbstractChronoZonedDateTimeAssert`
  , `LocalTimeAssert`, `OffsetDateTimeAssert`, `OffsetTimeAssert`
- ♻️ Change: some generic type of `AbstractMapAssert`, `AbstractChronoLocalDateAssert`
  , `AbstractChronoLocalDateTimeAssert`, `AbstractChronoZonedDateTimeAssert`, `LocalTimeAssert`, `OffsetDateTimeAssert`
  , `OffsetTimeAssert`
- ♻️ Change: default assertion descriptions as variables
- ♻️ Change: method `anyContains(String, Collection)` to `anyContains(String, Iterable)` in `StringUtils`
- ♻️ Change: parameter of methods `box`, `unbox` to be non-null in `ArrayUtils`
- ♻️ Replace: method `baseName(File)` with `getBaseName(String)` in `FilenameUtils`
- ♻️ Replace: method `extension(File)` with `getExtension(String)` in `FilenameUtils`
- ♻️ Replace: method `formatComma(long)` with `formatComma(double)` in `StringUtils`
- ♻️ Replace: methods `isNullOrEmpty` with `isNullOrEmpty(Object)` in `ArrayUtils`
- ♻️ Replace: methods `exists` with `exists(Object)` in `ArrayUtils`
- ⚡️ Improve: architecture of time assertions with extension of class `AbstractTemporalAssert`
- ⚡️ Improve: performance of `anyEquals(String, Collection)` in `StringUtils`

### New features

- ✨ Support: assertions for `BigInteger`, `BigDecimal`
- ✨ Add: assertions `InstantAssert`, `YearAssert`, `YearMonthAssert`
- ✨ Add: abstract assertion `AbstractTemporalAssert`
- ✨ Add: interfaces `YearAssertion`, `OffsetAssertion` for assertion
- ✨ Add: method `asInstant()` to `AbstractChronoLocalDateTimeAssert`, `AbstractChronoZonedDateTimeAssert`
  , `OffsetDateTimeAssert`
- ✨ Add: method `isOdd(BigInteger)` in `MathUtils`
- ✨ Add: method `download(URL, File)` in `FileUtils`
- ✨ Add: methods `ifNullOrEmpty(Collection, Collection)`, `ifNullOrEmpty(Collection, Supplier)`, `exists(Map)`
  in `CollectionUtils`

### Dependencies

- ➖ Remove: useless build dependency `maven-dependency-plugin`
- ⬆️ Upgrade: test dependency `junit5` from `5.7.2` to `5.8.1`
- ⬆️ Upgrade: test dependency `assertj-core` from `3.20.2` to `3.21.0`
- ⬆️ Upgrade: test dependency `lombok` from `1.18.20` to `1.18.22`
- ⬆️ Upgrade: build dependency `maven-javadoc-plugin` from `3.2.0` to `3.3.1`
- ⬆️ Upgrade: build dependency `maven-gpg-plugin` from `1.6` to `3.0.1`
- ⬆️ Upgrade: build dependency `maven-gmavenplus-plugin` from `1.12.1` to `1.13.0`

### Troubleshooting

- 🐞 Fix: wrong type inference in `AbstractMapAssert`
- 🐞 Fix: wrong assertion condition of `isNotSameSize(Map)` in `AbstractMapAssert`
- 🐞 Fix: wrong default description when array is multi-dimensional
- 🐞 Fix: wrong comparison in `NumberUtils#hasDecimalPart(BigDecimal)`

# v0.6.0

### Modification

- 🚚 Move: method `hasDecimalPart(double)` from `MathUtils` to `NumberUtils`
- 🚚 Rename: constant `OperatingSystem` to `OS`
- 🚚 Rename: method `toWrapper(Class)` to `box(Class)` in `TypeClassifier`
- 🚚 Rename: method `toPrimitive(Class)` to `unbox(Class)` in `TypeClassifier`
- 🚚 Rename: method `toWrapper` to `box` in `ArrayUtils`
- 🚚 Rename: method `toPrimitive` to `unbox` in `ArrayUtils`
- 🔥 Remove: methods `getKeywords()`, `contains(String)`, `of(String)` in `OperatingSystem`
- ♻️ Refactor: tool `Stopwatch`
- ♻️ Change: method from `containsAny(Object...)` to `containsAny(Object, Object...)` in `ArrayAssert`

### New features

- ✨ Add: tool `ClassFinder`
- ✨ Add: utility `ReflectionUtils`
- ✨ Add: interface for two-way encryption `Crypto`
- ✨ Add: classes `AES128`, `AES192`, `AES256` that are implementations of `Crypto`
- ✨ Add: method `getTimeUnit()` in `Stopwatch`
- ✨ Add: method `prepend(Object[], Object...)`, `append(Object[], Object...)` in `ArrayUtils`
- ✨ Add: methods `findAllFiles(Path)`, `deleteRecursively(Path, FileVisitOption...)` in `FileUtils`
- ✨ Add: methods `isSuperclass(Class, Class)`, `initialValueOf(Class)` in `ClassUtils`
- ✨ Add: methods `reverse(long)`, `reverse(BigInteger)`, `hasDecimalPart(BigDecimal)` in `NumberUtils`
- ✨ Add: methods `getCurrentOs()`, `isCurrentOs()` in `OS`
- ✨ Add: method `containsAny(T, T...)` in `AbstractCollectionAssert`
- ✨ Add: method `doesNotContainNull()` in `ArrayAssert`, `AbstractCollectionAssert`
- ✨ Add: methods `isBetween(NUMBER, NUMBER)`, `isStrictlyBetween(NUMBER, NUMBER)`, `isCloseTo(NUMBER, double)`
  in `NumberAssert`
- ✨ Add: conversion methods in some assertion classes
- ✨ Add: default description for some assertion classes

### Troubleshooting

- 🐞 Fix: wrong assertion condition of `isNotSameLength(CharSequence)` in `AbstractCharSequenceAssert`
- 🐞 Fix: wrong assertion condition of `isLetter()`, `isLetterOrDigit()`, `isUppercase()`, `isLowercase()`
  , `isAlphabetic()`, `startsWith(String)`, `endsWith(String)`, `contains(String)` in `StringAssert`

# v0.5.0

### Modification

- 🚧 Prevent: utility class from instantiation
- 🚚 Rename: class `*Asserts` to `*Assert`
- 🚚 Move: package of `*Assert`
- 🚚 Rename: field and parameter of `*Asserts`
- 🚚 Rename: method from `isSameSize(T[])` to `isSameLength(Object[])` in `ArrayAssert`
- 🚚 Rename: private field name in `Descriptor`
- ⚡️ Make: assertion classes more reusable and generic
- ⚡️ Enable: to customize `Asserts` with inheritance
- ♻️ Replace: primitive number assertion classes with `NumberAssert`
- ♻️ Change: logic of `isEqualTo`, `isNotEqualTo` in `AbstractObjectAssert`
- 📈 Exclude: some constructors and methods from code coverage report

### New features

- ✨ Add: assertions for `File`, `Map`, `Class`, `ChronoLocalDate`, `ChronoLocalDateTime`, `ChronoZonedDate`, `LocalTime`
  , `OffsetDateTime`, `OffsetDate`
- ✨ Add: interface `DecimalNumberAssertion` for `DoubleAssert`, `FloatAssert`
- ✨ Add: tool `TypeClassifier`
- ✨ Add: annotation `ExcludeFromGeneratedJacocoReport`
- ✨ Add: `isEmpty()`, `hasLengthOf(int)`, `isNotSameLength(T[])` in `ArrayAssert`
- ✨ Add: `isSameLength(CharSequence)`, `isNotSameLength(CharSequence)` in `AbstractCharSequenceAssert`
- ✨ Add: `isEmpty()`, `hasSizeOf(int)`, `isNotSameSize(Collection)` in `AbstractCollectionAssert`
- ✨ Add: `isZero()`, `isNotZero()` in `CharacterAssert`
- ✨ Add: methods `predicate(Predicate)`, `returns(T, Function)` in `AbstractObjectAssert`
- ✨ Add: internal API `Descriptor#setDefaultDescription(String, Object...)`
- ✨ Extend: `AbstractObjectAssert` by primitive assertions
- ✨ Add: default assertion description of some assertion classes
- ✨ Add: utility classes `ArrayUtils`, `ClassUtils`
- ✨ Support: assertion for primitive array
- ✨ Add: conversion methods in some assertion classes

### Dependencies

- ⬆️ Upgrade: dependency `gson` from `2.8.6` to `2.8.7`
- ⬆️ Upgrade: test dependency `junit5` from `5.7.1` to `5.7.2`
- ⬆️ Upgrade: test dependency `assertj-core` from `3.19.0` to `3.20.2`
- ⬆️ Upgrade: test dependency `spock` from `2.0-M5` to `2.0`
- ⬆️ Upgrade: build plugin dependency `gmavenplus` from `1.11.1` to `1.12.1`
- ⬆️ Upgrade: build plugin dependency `jacoco` from `0.8.6` to `0.8.7`

### Troubleshooting

- 🐞 Fix: wrong computation `MathUtils#hasDecimalPart(double)`
- 🐞 Fix: wrong checking if type of instance is primitive
- 🐞 Fix: wrong comparison with equality of primitive numbers
- 🐞 Fix: wrong comparison with equality of character
- 🐞 Fix: wrong type inference in `AbstractCollectionAssert`
- 🐞 Fix: wrong type inference in `ArrayAssert`

# v0.4.7

### Troubleshooting

- 🚑️ Hotfix: wrong computation of `StringUtils#isNullOrBlank(String)`

# v0.4.6

### Modification

- 🚚 Rename: `hasText()` => `isNotEmpty()` in `CharSequenceAsserts`

### New features

- ✨ Add: `StringAsserts#hasText()`
- ✨ Add: `StringUtils#isNumeric(String)`

### Troubleshooting

- 🐞 Fix: wrong assertion of `CharSequenceAsserts#hasLengthOf(int)`

# v0.4.5

### Modification

- ♻️ Refactor: constant `OperatingSystem`
- ⚡️ Decrease: branch complexity in `Stopwatch`
- ⚡️ Decrease: visibility of constructor `Descriptor`

### New features

- ✨ Add: `CharSequenceAsserts#hasLengthOf(int)`
- ✨ Add: `isLetter()`, `isLetterOrDigit()`, `isAlphabetic()` in `StringAsserts`

### Troubleshooting

- 🐞 Fix: NPE from `MessageFormat#MessageFormat(String)`

# v0.4.4

### Modification

- 🚚 Rename: `isInstanceOf(Object)` => `isActualTypeOf(Object)` in `ClassAsserts`

### New features

- ✨ Add: `PackageAsserts`
- ✨ Add: `ObjectAsserts#isInstanceOf(Class)`
- ✨ Add: `isNotActualTypeOf(Object)`, `isEnum()`, `isArray()`, `isMemberClass()`, `isLocalClass()` in `ClassAsserts`
- ✨ Add: `startsWith(String)`, `endsWith(String)`, `contains(CharSequence)` in `StringAsserts`

# v0.4.3

### Modification

- ♻️ Change: `hasElement(T[])` to `hasElement()` in `ArrayAsserts`

### New features

- 🔧 Add: test coverage analysis `jacoco`

### Troubleshooting

- 🐞 Fix: deletion of single quotation marks in `Descriptor#getMessage()`

# v0.4.2

### Modification

- ⚡️ Improve: `Asserts` API
- 🚚 Move: package of `Asserts` from `asserts` => `assertion`
- ♻️ Change: `JsonUtils#readAllJson(BufferedReader)` to `JsonUtils#readAllJson(Reader)`
- ♻️ Replace: `DateType#of(String)` with `DateType#from(String)`

### New features

- ✨ Add: `exception(Function)` in all `Asserts`
- ✨ Add: `CharacterAsserts`
- ✨ Add: `isSuperclass(Class)`, `isSubclass(Class)` in `ClassAsserts`
- ✨ Add: `isEqualTo(double)`, `isGreaterThan(double)`, `isGreaterThanOrEqualTo(double)`, `isLessThan(double)`
  , `isLessThanOrEqualTo(double)` in `LongAsserts`

# v0.4.1

### Modification

- ♻️ Change: `CollectionAsserts#hasElement`

# v0.4.0

### Modification

- ♻️ Change: `PathnameUtils#getCurrentPathname()` to be throwable
- ♻️ Change: `JsonUtils#readAllJson(BufferedReader)` to `JsonUtils#readAllJson(Reader)`
- ♻️ Replace: `DateType#of(String)` with `DateType#from(String)`

### New features

- ✨ Add: `Asserts`
- ✨ Add: `Stopwatch#clear()`, `Stopwatch#forceClear()`
- ✨ Add: many utilities in `NumberUtils`
- ✨ Add: `MathUtils#hasDecimalPart(double)`
- 🔧 Add: maven wrapper
- 🔧 Add: configuration for Travis CI

### Dependencies

- ⬆️ Upgrade: dependencies for test
- ⬆️ Upgrade: test dependency `spock-core` --- `2.0-M5-groovy-3.0`
- ⬆️ Upgrade: test dependency `lombok` --- `1.18.20`

# v0.3.4

### Modification

- 🔥 Remove: useless interface `KeyValue`

### New features

- ✨ Add: methods in `StringUtils` --- `find(String, Pattern, int)`, `find(String, Pattern, int...)`
- ✨ Add: methods in `DateTimeUtils` --- `getSystemDefaultZoneOffset()`
  , `random(ChronoLocalDateTime, ChronoLocalDateTime)`, `random(ChronoLocalDateTime, ChronoLocalDateTime, ZoneOffset)`

### Dependencies

- ⬆️ Upgrade: dependency for test `junit5` --- `5.7.0`
- ➕ Add: dependency for test `spock`

# v0.3.3

### Modification

- ⚡️ Ensure: resources like this `ReadableByteChannel`, `ObjectOutputStream` object are closed after use
- ♻️ Replace: array with varargs for methods or constructors which take an array the last parameter.
- ♻️ Change: `DateType#value()` => `DateType#getPattern()`
- ⚡️ Specify: `Locale` when instantiating a `SimpleDateFormat` object
- ⚡️ Specify: `NumberUtils` is a utility class, adding a private constructor
- 🔥 Remove: unstable utility `ObjectUtils`
- 🔥 Remove: `DateType#key()`

### New features

- ✨ Add: `IniUtils#write(File, Map)`, `IniUtils#writeEntries(File, Map)`, `IniUtils#read(File)`
- ✨ Add: `FileUtils#download(InputStream, File)`
- ✨ Add: `DateType#getFormatter()`

# v0.3.2

### Modification

- ♻️ Change: `StringUtils#match(String, String, int)` => `StringUtils#find(String, String, int, int...)`
- ⚡️ Validate: `Stopwatch#getTotalTime()`
- ♻️ Simplify: calculation of time unit's right padding in `Stopwatch#getStatistics()`
- ♻️ Simplify: `StringUtils#formatComma(long)`, `StringUtils#formatComma(String)`

### New features

- ✨ Add: `Stopwatch#start(String, Object...)`
- ✨ Add: `CollectionUtils#isNullOrEmpty`, `CollectionUtils#ifNullOrEmpty`
- ✨ Add: `Stopwatch#hasNeverBeenStopped()`
- ✨ Add: `MathUtils#isOdd(long)`
- ✨ Add: `CollectionUtils#median(long[])`, `CollectionUtils#median(int[])`

### Dependencies

- ➖ Remove: dependency `junit.platform.launcher`
- ⬆️ Upgrade: dependency `lombok` --- `1.18.14`

# v0.3.1

### New features

- ✨ Add: `StringUtils#getLastString(String)`
- ✨ Add: `StringUtils#chop(String)`

### Dependencies

- ➕ Add: dependency `maven-surefire-plugin` for maven test

### Troubleshooting

- 🐞 Fix: `StringUtils#countOf`

# v0.3.0

### Modification

- ⚡️ Update: annotation `Nonnull` to parameter
- ♻️ Swap: `#floor(double, int)`
- ⚡️ Modify: `Stopwatch#getTotalTime`, `Stopwatch#getSummary`

### New features

- ✨ Add: utility `MathUtils`
- ✨ Add: `CollectionUtils#partitionBySize(List, int)`, `CollectionUtils#partitionByCount(List, int)`
- ✨ Add: `NumberUtils#getNumOfPlaces(long)`, `NumberUtils#getNumOfPlaces(BigInteger)`
- ✨ Add: `FileUtils#getFileAttributes(File)`

### Dependencies

- ➕ Add: dependency `jsr305`
- ➖ Remove: dependency `hamcrest-all`
- ⬆️ Upgrade: dependency `assertj-core` --- `3.17.2`

# v0.2.1

### Modification

- ⚡️ Modify: `OperatingSystem#of(String)`
- ⚡️ Modify: method's parameter type --- varargs => Collection

### New features

- ✨ Add: `StringUtils#anyContains(String, Collection)`
- ✨ Add: `CollectionUtils#isNullOrEmpty(Collection)`, `CollectionUtils#exists(Collection)`

# v0.2.0

### Modification

- 🚚 Add: root package `common`
- 🚚 Move: `util` -> `tool`
- ⚡️ Improve: utility `JsonUtils`

### New features

- ✨ Add: tool `Stopwatch`
- ✨ Add: utility `NumberUtils`

# v0.1.2

### Troubleshooting

- 🐞 Fix: `StringUtils#padStart`, `StringUtils#padEnd`

# v0.1.1

### Modification

- 📝 Update: version

# v0.1.0

### New features

- 🎉 Begin: first release
