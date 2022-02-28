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

package io.github.imsejin.common.model.graph

import io.github.imsejin.common.assertion.Descriptor
import io.github.imsejin.common.assertion.chars.AbstractCharSequenceAssert
import io.github.imsejin.common.assertion.chars.StringAssert
import io.github.imsejin.common.assertion.object.AbstractObjectAssert
import spock.lang.Specification

@SuppressWarnings("GroovyAccessibility")
class UndirectedGraphSpec extends Specification {

    def "Adds vertexes and edges"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        when:
        graph.addVertex Iterable
        graph.addVertex Collection
        graph.addEdge(Iterable, Collection)
        graph.addVertex List
        graph.addEdge(Collection, List)
        graph.addVertex AbstractCollection
        graph.addEdge(Collection, AbstractCollection)
        graph.addVertex AbstractList
        graph.addEdge(AbstractCollection, AbstractList)
        graph.addVertex ArrayList
        graph.addVertex RandomAccess
        graph.addVertex Cloneable
        graph.addVertex Serializable
        graph.addEdge(ArrayList, List)
        graph.addEdge(ArrayList, RandomAccess)
        graph.addEdge(ArrayList, Cloneable)
        graph.addEdge(ArrayList, Serializable)

        then:
        graph.vertexSize == 9
        graph.vertexSize == graph.allVertexes.size()
        println graph
    }

    def "Adds the other graph"() {
        given:
        def graph0 = new UndirectedGraph<>() as Graph<Class<?>>
        def graph1 = new UndirectedGraph<>() as Graph<Class<?>>

        when:
        graph0.addVertex StringBuilder
        graph0.addVertex Serializable
        graph0.addVertex AbstractStringBuilder
        graph0.addVertex CharSequence
        graph0.addEdge(StringBuilder, Serializable)
        graph0.addEdge(StringBuilder, AbstractStringBuilder)
        graph0.addEdge(StringBuilder, CharSequence)
        graph0.addVertex Appendable
        graph0.addEdge(Appendable, AbstractStringBuilder)

        then:
        graph0.vertexSize == 5
        println graph0

        when:
        graph1.addVertex String
        graph1.addVertex Serializable
        graph1.addVertex Comparable
        graph1.addVertex CharSequence
        graph1.addEdge(String, Serializable)
        graph1.addEdge(String, Comparable)
        graph1.addEdge(String, CharSequence)

        then:
        graph1.vertexSize == 4

        when:
        graph0.addAll graph1

        then:
        graph0.vertexSize == 7
        graph1.vertexSize == 4
        graph0 == new UndirectedGraph<>(graph0)
        graph1 == new UndirectedGraph<>(graph1)
        println graph0
    }

    def "Gets all the number of vertexes"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect:
        graph.vertexSize == 0

        when:
        graph.addVertex Descriptor
        graph.addVertex AbstractObjectAssert
        graph.addEdge(Descriptor, AbstractObjectAssert)
        graph.addVertex AbstractCharSequenceAssert
        graph.addEdge(AbstractObjectAssert, AbstractCharSequenceAssert)
        graph.addVertex StringAssert
        graph.addEdge(AbstractCharSequenceAssert, StringAssert)

        then:
        graph.vertexSize == 4
        println graph
    }

    def "Gets all the number of edges"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect: "Empty graph has no edge"
        graph.edgeSize == 0

        when: "Add vertexes and its edges"
        graph.addVertex Iterable
        graph.addVertex Collection
        graph.addEdge(Iterable, Collection)
        graph.addVertex List
        graph.addEdge(Collection, List)
        graph.addVertex AbstractCollection
        graph.addEdge(Collection, AbstractCollection)
        graph.addVertex AbstractList
        graph.addEdge(AbstractCollection, AbstractList)
        graph.addVertex ArrayList
        graph.addVertex RandomAccess
        graph.addVertex Cloneable
        graph.addVertex Serializable
        graph.addEdge(ArrayList, AbstractList)
        graph.addEdge(ArrayList, List)
        graph.addEdge(ArrayList, RandomAccess)
        graph.addEdge(ArrayList, Cloneable)
        graph.addEdge(ArrayList, Serializable)
        def edgeSize = graph.edgeSize

        then: "This graph has 9 vertexes and 9 edges"
        edgeSize == 9
        println graph

        when: "Removes the vertex 'ArrayList' that is adjacent to 5 other vertexes"
        def vertex = ArrayList
        def adjacentVertexes = graph.getAdjacentVertexes vertex
        graph.removeVertex vertex

        then: "Decreases the number of edges in graph as many as the vertex that is adjacent to the other vertexes"
        graph.edgeSize == edgeSize - adjacentVertexes.size()
    }

    def "Gets all the vertexes"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect:
        graph.allVertexes.size() == 0
        graph.allVertexes == [] as Set

        when:
        graph.addVertex Double
        graph.addVertex Number
        graph.addVertex Comparable
        graph.addEdge(Double, Number)
        graph.addEdge(Double, Comparable)
        graph.addVertex Serializable
        graph.addEdge(Number, Serializable)

        then:
        graph.allVertexes.size() == 4
        graph.allVertexes == [Double, Number, Comparable, Serializable] as Set
    }

    def "Gets the adjacent vertexes by vertex"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect: "Returns null if graph doesn't have the given vertex"
        graph.getAdjacentVertexes(StringBuilder) == null

        when: "Add vertexes and its edges"
        graph.addVertex StringBuilder
        graph.addVertex Serializable
        graph.addVertex AbstractStringBuilder
        graph.addVertex CharSequence
        graph.addEdge(StringBuilder, Serializable)
        graph.addEdge(StringBuilder, AbstractStringBuilder)
        graph.addEdge(StringBuilder, CharSequence)
        graph.addVertex Appendable
        graph.addEdge(Appendable, AbstractStringBuilder)
        def vertexes = graph.getAdjacentVertexes StringBuilder

        then: "Vertex 'StringBuilder' is adjacent to 3 other vertexes"
        vertexes.size() == 3
        vertexes == [Serializable, AbstractStringBuilder, CharSequence] as Set
    }

    def "Equality and hash code"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect: "Empty graph is equal to another empty graph"
        graph == new UndirectedGraph<>()

        when: "Add vertexes and its edges"
        graph.addVertex String
        graph.addVertex Serializable
        graph.addVertex Comparable
        graph.addVertex CharSequence
        graph.addEdge(String, Serializable)
        graph.addEdge(String, Comparable)
        graph.addEdge(String, CharSequence)

        then: """
            1. Graph that has vertexes or edges is not equal to another empty graph
            2. Graph that has vertexes or edges is equal to another graph that has the same content
        """
        graph != new UndirectedGraph<>()
        graph == new UndirectedGraph<>(graph)
    }

}
