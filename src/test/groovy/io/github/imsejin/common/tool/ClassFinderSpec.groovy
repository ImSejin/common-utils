package io.github.imsejin.common.tool

import io.github.imsejin.common.assertion.Descriptor
import io.github.imsejin.common.util.FileUtils
import io.github.imsejin.common.util.FilenameUtils
import spock.lang.Specification

import java.nio.file.Paths

import static io.github.imsejin.common.tool.ClassFinder.SearchPolicy.ALL
import static io.github.imsejin.common.tool.ClassFinder.SearchPolicy.CLASS
import static io.github.imsejin.common.tool.ClassFinder.SearchPolicy.INTERFACE

class ClassFinderSpec extends Specification {

    def "Gets all the subclasses and implementations of the given class"() {
        when:
        def subtypes = ClassFinder.getAllSubtypes(superclass).findAll(condition)

        then:
        subtypes.size() == expected.size()

        def simpleNames = subtypes.collect { it.simpleName }.toSet()
        simpleNames == expected as Set<String>

        where:
        superclass | condition                                                                        || expected
        Animal     | { true }                                                                         || [Human, Caucasoid, Mongoloid, Negroid, HomoSapiens, Father, Mother, Child].collect { it.simpleName }
        Descriptor | { Class it -> it.package.name.startsWith("io.github.imsejin.common.assertion") } || FileUtils.findAllFiles(
                Paths.get(".", "src", "main", "java", "io", "github", "imsejin", "common", "assertion"))
                .collect { FilenameUtils.getBaseName(it.fileName.toString()) }.findAll { it.matches(/^[A-Za-z]+Assert$/) }
    }

    def "Gets all the classes of the given class by policy"() {
        when:
        def subtypes = ClassFinder.getAllSubtypes(superclass, searchPolicy)

        then:
        !subtypes.contains(superclass)
        subtypes.size() == expected.size()
        subtypes == expected as Set<Class<?>>

        where:
        superclass  | searchPolicy || expected
        Animal      | ALL          || [Human, Caucasoid, Mongoloid, Negroid, HomoSapiens, Father, Mother, Child]
        Animal      | CLASS        || [HomoSapiens, Father, Mother, Child]
        Animal      | INTERFACE    || [Human, Caucasoid, Mongoloid, Negroid]
        Caucasoid   | CLASS        || [Father]
        Caucasoid   | INTERFACE    || []
        Mongoloid   | CLASS        || [Child]
        Mongoloid   | INTERFACE    || []
        Negroid     | CLASS        || [Mother]
        Negroid     | INTERFACE    || []
        HomoSapiens | ALL          || [Father, Mother, Child]
        HomoSapiens | CLASS        || [Father, Mother, Child]
    }

    def "Finds all classes"() {
        given:
        def classNames = [] as List<String>

        when:
        ClassFinder.findClasses(classNames::add)

        then:
        def sources = FileUtils.findAllFiles(Paths.get(".", "src", "main", "java", "io", "github", "imsejin", "common"))
        !classNames.contains(null)
        classNames.findAll { it.startsWith("io.github.imsejin.common") }.size() > sources.size()
    }

    // -------------------------------------------------------------------------------------------------

    interface Animal {}

    interface Human extends Animal {}

    interface Caucasoid extends Human {}

    interface Mongoloid extends Human {}

    interface Negroid extends Human {}

    abstract class HomoSapiens implements Human {}

    class Father extends HomoSapiens implements Caucasoid {}

    class Mother extends HomoSapiens implements Negroid {}

    class Child extends HomoSapiens implements Mongoloid {}

}
