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

        expect: """
            1. UndirectedGraph doesn't allow to remove null as vertex.
            2. UndirectedGraph doesn't allow to remove vertex that is not added.
            3. UndirectedGraph still has the same vertices as it was.
            4. UndirectedGraph still has the same pathLength as it was.
        """
        !graph.removeVertex(null)
        !graph.removeVertex(Object)
        graph.vertexSize == vertices.size()
        graph.pathLength == edges.size()

        when:
        def vertex = vertices[0]
        def removed = graph.removeVertex vertex

        then: """
            1. UndirectedGraph removed the first vertex.
            2. Vertices of UndirectedGraph are decreased as many as they are removed.
            3. UndirectedGraph doesn't have the removed vertex anymore.
        """
        removed
        graph.vertexSize == vertices.stream().filter({ it != vertex }).count()
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

    def "Removes edges"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<?>
        vertices.forEach(graph::addVertex)
        edges.forEach({ edge -> graph.addEdge(edge[0], edge[1]) })
        def vertex = vertices[0]

        expect: """
            1. UndirectedGraph doesn't allow to remove edge with null as vertex.
            2. UndirectedGraph doesn't allow to remove edge with the same vertices.
            3. UndirectedGraph doesn't allow to remove edge with vertex that is not added.
            4. UndirectedGraph still has the same pathLength as it was.
        """
        !graph.removeEdge(null, vertex) && !graph.removeEdge(vertex, null)
        !graph.removeEdge(vertex, vertex)
        !graph.removeEdge(Object, vertex) && !graph.removeEdge(vertex, Object)
        graph.pathLength == edges.size()

        when:
        def edge = [vertex, vertices[1]]
        def removed = graph.removeEdge(edge[0], edge[1])

        then: """
            1. UndirectedGraph removed a edge.
            2. Edges of UndirectedGraph are decreased as many as they are removed.
        """
        removed
        graph.pathLength == edges.stream().filter({ (edge as Set) != (it as Set) }).count()

        where:
        vertices                                         | edges
        [false, true]                                    | []
        [0, 1, 2, 3, 4, 5]                               | [[0, 1], [1, 2], [2, 3], [3, 4], [4, 0], [5, 0], [5, 1], [5, 2], [5, 3], [5, 4]]
        ["A", "B", "C", "D", "E", "F"]                   | [["A", "B"], ["B", "C"], ["A", "D"], ["D", "E"], ["E", "F"]]
        [String, Serializable, Comparable, CharSequence] | [[String, Serializable], [String, Comparable], [String, CharSequence]]
    }

    def "Adds the other graph"() {
        given:
        def origin = new UndirectedGraph<>() as Graph<?>
        def other = new UndirectedGraph<>() as Graph<?>

        when:
        def isEqual = origin == other
        originVertices.forEach(origin::addVertex)
        originEdges.forEach(origin::addEdge)
        otherVertices.forEach(other::addVertex)
        otherEdges.forEach(other::addEdge)
        def isNotEqual = origin != other

        then:
        isEqual
        isNotEqual

        when:
        def graph = new UndirectedGraph<>(origin) as Graph<?>

        then:
        graph == origin
        graph != other

        when:
        def addAll = graph.addAll other

        then:
        addAll
        originEdges.isEmpty() || graph != origin
        originEdges.isEmpty() || graph != other

        when:
        origin.addAll other

        then:
        graph == origin

        where:
        originVertices         | originEdges                              || otherVertices                      | otherEdges
        [false, true]          | []                                       || [false, true]                      | [[false, true]]
        [0, 1, 2, 3, 4]        | [[0, 1], [1, 2], [2, 3], [3, 4], [4, 0]] || [0, 1, 2, 3, 4, 5]                 | [[5, 0], [5, 1], [5, 2], [5, 3], [5, 4]]
        ["A", "B", "C", "D"]   | [["A", "B"], ["B", "C"], ["A", "D"]]     || ["C", "D", "E", "F"]               | [["D", "E"], ["E", "F"]]
        [String, Serializable] | [[String, Serializable]]                 || [String, Comparable, CharSequence] | [[String, Comparable], [String, CharSequence]]
    }

    def "Equality and hash code"() {
        given:
        def graph = new UndirectedGraph<>() as Graph<?>

        expect: "Empty graph is equal to another empty graph"
        graph == new UndirectedGraph<>()

        when: "Add vertices and its edges"
        [String, Serializable, Comparable, CharSequence].forEach(graph::addVertex)
        [[String, Serializable], [String, Comparable], [String, CharSequence]].forEach(graph::addEdge)

        then: """
            1. UndirectedGraph that has vertices or edges is not equal to another empty graph
            2. UndirectedGraph that has vertices or edges is equal to another graph that has the same content
        """
        graph != new UndirectedGraph<>()
        graph == new UndirectedGraph<>(graph)
    }

}
