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

    def "Adds vertices"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<?>

        when: "UndirectedGraph has vertices as many as the number of elements, but no edge"
        def added = elements.stream().map(graph::addVertex).reduce(Boolean.TRUE, { a, b -> a && b })

        then: """
            1. All elements are added to graph as vertices.
            2. UndirectedGraph has no edge, so its pathLength is zero.
            3. UndirectedGraph has vertices as many as the number of elements.
            4. There are no adjacent vertices.
        """
        added == expected
        graph.pathLength == 0
        graph.vertexSize == elements.stream().filter(Objects::nonNull).count()
        elements.stream().flatMap({ graph.getAdjacentVertices(it).stream() }).count() == 0

        where:
        elements                                         | expected
        [new Object(), null]                             | false
        [new Object(), new Object()]                     | true
        [0, -1, 104, -2.5, 98, -47.029]                  | true
        ["alpha", "", "gamma", "delta"]                  | true
        [String, Serializable, Comparable, CharSequence] | true
    }

    def "Removes vertices"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<?>
        vertices.forEach(graph::addVertex)
        edges.forEach({ edge -> graph.addEdge(edge[0], edge[1]) })

        expect:
        !graph.removeVertex(null)
        !graph.removeVertex(Object)
        graph.vertexSize == vertices.size()
        graph.pathLength == edges.size()

        when:
        def vertex = vertices[0]
        def removed = graph.removeVertex vertex

        then:
        removed
        graph.vertexSize == vertices.size() - 1
        graph.pathLength == edges.stream().filter({ !it.contains(vertex) }).count()

        where:
        vertices                                         | edges
        [false, true]                                    | []
        [0, 1, 2, 3, 4, 5]                               | [[0, 1], [1, 2], [2, 3], [3, 4], [4, 0], [5, 0], [5, 1], [5, 2], [5, 3], [5, 4]]
        ["A", "B", "C", "D", "E", "F"]                   | [["A", "B"], ["B", "C"], ["A", "D"], ["D", "E"], ["E", "F"]]
        [String, Serializable, Comparable, CharSequence] | [[String, Serializable], [String, Comparable], [String, CharSequence]]
    }

    def "Adds vertices and edges"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<?>

        when:
        vertices.forEach(graph::addVertex)
        def added = edges.stream().map({ edge -> graph.addEdge(edge[0], edge[1]) }).reduce(Boolean.TRUE, { a, b -> a && b })

        then: """
            1. All vertices are added to UndirectedGraph.
            2. UndirectedGraph has vertices as many as it is added.
            3. UndirectedGraph has edges as many as it is added.
        """
        added
        graph.vertexSize == vertices.size()
        graph.pathLength == edges.size()

        expect: """
            1. UndirectedGraph doesn't allow to add edge with null as vertex.
            2. UndirectedGraph doesn't allow to add edge with the same vertices.
            3. UndirectedGraph doesn't allow to add edge with vertex that is not added.
            4. UndirectedGraph doesn't allow to add edge that is already added.
            5. UndirectedGraph doesn't allow to add edge that is already added even if vertices is reversed.
            6. UndirectedGraph.pathLength is as it is.
        """
        def vertex = vertices[0]
        !graph.addEdge(null, vertex) && !graph.addEdge(vertex, null)
        !graph.addEdge(vertex, vertex)
        !graph.addEdge(Object, vertex) && !graph.addEdge(vertex, Object)
        edges.isEmpty() || !edges.stream().map({ edge -> graph.addEdge(edge[0], edge[1]) }).reduce(Boolean.TRUE, { a, b -> a && b })
        edges.isEmpty() || !edges.stream().map({ edge -> graph.addEdge(edge[1], edge[0]) }).reduce(Boolean.TRUE, { a, b -> a && b })
        graph.pathLength == edges.size()

        where:
        vertices                                                                                                         | edges
        [false, true]                                                                                                    | []
        [0, 1, 2, 3, 4, 5]                                                                                               | [[0, 1], [1, 2], [2, 3], [3, 4], [4, 0], [5, 0], [5, 1], [5, 2], [5, 3], [5, 4]]
        ["A", "B", "C", "D"]                                                                                             | [["A", "B"], ["A", "C"], ["B", "C"]]
        [String, Serializable, Comparable, CharSequence]                                                                 | [[String, Serializable], [String, Comparable], [String, CharSequence]]
        [Iterable, Collection, List, AbstractCollection, AbstractList, ArrayList, RandomAccess, Cloneable, Serializable] | [[Iterable, Collection], [Collection, List], [Collection, AbstractCollection], [AbstractCollection, AbstractList], [ArrayList, List], [ArrayList, AbstractList], [ArrayList, RandomAccess], [ArrayList, Cloneable], [ArrayList, Serializable]]
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

    def "Gets all the number of vertices"() {
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
        graph.pathLength == 0

        when: "Add vertices and its edges"
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
        def pathLength = graph.pathLength

        then: "This graph has 9 vertices and 9 edges"
        pathLength == 9
        println graph

        when: "Removes the vertex 'ArrayList' that is adjacent to 5 other vertices"
        def vertex = ArrayList
        def adjacentVertexes = graph.getAdjacentVertices vertex
        graph.removeVertex vertex

        then: "Decreases the number of edges in graph as many as the vertex that is adjacent to the other vertices"
        graph.pathLength == pathLength - adjacentVertexes.size()
    }

    def "Gets all the vertices"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect:
        graph.allVertices.size() == 0
        graph.allVertices == [] as Set

        when:
        graph.addVertex Double
        graph.addVertex Number
        graph.addVertex Comparable
        graph.addEdge(Double, Number)
        graph.addEdge(Double, Comparable)
        graph.addVertex Serializable
        graph.addEdge(Number, Serializable)

        then:
        graph.allVertices.size() == 4
        graph.allVertices == [Double, Number, Comparable, Serializable] as Set
    }

    def "Gets the adjacent vertices by vertex"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect: "Returns null if graph doesn't have the given vertex"
        graph.getAdjacentVertices(StringBuilder) == null

        when: "Add vertices and its edges"
        graph.addVertex StringBuilder
        graph.addVertex Serializable
        graph.addVertex AbstractStringBuilder
        graph.addVertex CharSequence
        graph.addEdge(StringBuilder, Serializable)
        graph.addEdge(StringBuilder, AbstractStringBuilder)
        graph.addEdge(StringBuilder, CharSequence)
        graph.addVertex Appendable
        graph.addEdge(Appendable, AbstractStringBuilder)
        def vertices = graph.getAdjacentVertices StringBuilder

        then: "Vertex 'StringBuilder' is adjacent to 3 other vertices"
        vertices.size() == 3
        vertices == [Serializable, AbstractStringBuilder, CharSequence] as Set
    }

    def "Equality and hash code"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<Class<?>>

        expect: "Empty graph is equal to another empty graph"
        graph == new UndirectedGraph<>()

        when: "Add vertices and its edges"
        graph.addVertex String
        graph.addVertex Serializable
        graph.addVertex Comparable
        graph.addVertex CharSequence
        graph.addEdge(String, Serializable)
        graph.addEdge(String, Comparable)
        graph.addEdge(String, CharSequence)

        then: """
            1. UndirectedGraph that has vertices or edges is not equal to another empty graph
            2. UndirectedGraph that has vertices or edges is equal to another graph that has the same content
        """
        graph != new UndirectedGraph<>()
        graph == new UndirectedGraph<>(graph)
    }

}
