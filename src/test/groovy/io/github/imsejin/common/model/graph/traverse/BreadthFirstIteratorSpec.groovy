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

package io.github.imsejin.common.model.graph.traverse

import io.github.imsejin.common.model.graph.Graph
import io.github.imsejin.common.model.graph.UndirectedGraph
import spock.lang.Specification

import java.util.stream.StreamSupport

import static java.util.stream.Collectors.*

class BreadthFirstIteratorSpec extends Specification {

    def "Iterate"() {
        given: "Add vertices and its edges"
        def graph = new UndirectedGraph<>() as Graph<Class<?>>
        [Iterable, Collection, List, AbstractCollection, AbstractList, ArrayList, RandomAccess, Cloneable, Serializable].forEach(graph.&addVertex)
        def edges = [[Iterable, Collection], [Collection, List], [Collection, AbstractCollection], [AbstractCollection, AbstractList],
                     [ArrayList, List], [ArrayList, AbstractList], [ArrayList, RandomAccess], [ArrayList, Cloneable], [ArrayList, Serializable]]
        edges.forEach(graph.&addEdge)

        when:
        def iterator = new BreadthFirstIterator<>(graph, root)
        def vertices = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator, Spliterator.ORDERED), false).collect toList()

        then:
        graph.vertexSize == vertices.size()
        graph.allVertices == vertices as Set
        root == vertices.first()
        second.contains vertices[1]
        last.contains vertices.last()

        where:
        root               || second                                                      | last
        ArrayList          || [Cloneable, Serializable, RandomAccess, List, AbstractList] | [Iterable]
        AbstractList       || [AbstractCollection, ArrayList]                             | [Iterable]
        AbstractCollection || [Collection, AbstractList]                                  | [Cloneable, Serializable, RandomAccess]
        List               || [Collection, ArrayList]                                     | [Cloneable, Serializable, RandomAccess, Iterable, AbstractCollection, AbstractList]
        Collection         || [Iterable, List, AbstractCollection]                        | [Cloneable, Serializable, RandomAccess]
        Iterable           || [Collection]                                                | [Cloneable, Serializable, RandomAccess]
        Cloneable          || [ArrayList]                                                 | [Iterable]
        Serializable       || [ArrayList]                                                 | [Iterable]
        RandomAccess       || [ArrayList]                                                 | [Iterable]
    }

    def "Traverse"() {
        given: "Add vertices and its edges"
        def graph = new UndirectedGraph<>() as Graph<Class<?>>
        [Iterable, Collection, List, AbstractCollection, AbstractList, ArrayList, RandomAccess, Cloneable, Serializable].forEach(graph.&addVertex)
        def edges = [[Iterable, Collection], [Collection, List], [Collection, AbstractCollection], [AbstractCollection, AbstractList],
                     [ArrayList, List], [ArrayList, AbstractList], [ArrayList, RandomAccess], [ArrayList, Cloneable], [ArrayList, Serializable]]
        edges.forEach(graph.&addEdge)

        when:
        def vertices = []
        BreadthFirstIterator.traverse(graph, root, vertices.&add)

        then:
        graph.vertexSize == vertices.size()
        graph.allVertices == vertices as Set
        root == vertices.first()
        second.contains vertices[1]
        last.contains vertices.last()

        where:
        root               || second                                                      | last
        ArrayList          || [Cloneable, Serializable, RandomAccess, List, AbstractList] | [Iterable]
        AbstractList       || [AbstractCollection, ArrayList]                             | [Iterable]
        AbstractCollection || [Collection, AbstractList]                                  | [Cloneable, Serializable, RandomAccess]
        List               || [Collection, ArrayList]                                     | [Cloneable, Serializable, RandomAccess, Iterable, AbstractCollection, AbstractList]
        Collection         || [Iterable, List, AbstractCollection]                        | [Cloneable, Serializable, RandomAccess]
        Iterable           || [Collection]                                                | [Cloneable, Serializable, RandomAccess]
        Cloneable          || [ArrayList]                                                 | [Iterable]
        Serializable       || [ArrayList]                                                 | [Iterable]
        RandomAccess       || [ArrayList]                                                 | [Iterable]
    }

}
