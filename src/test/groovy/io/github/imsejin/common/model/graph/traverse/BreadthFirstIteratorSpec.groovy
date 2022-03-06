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

class BreadthFirstIteratorSpec extends Specification {

    def "Iterate"() {
        given: "Add vertexes and its edges"
        def graph = new UndirectedGraph<>() as Graph<Class<?>>
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

        when:
        def vertexes = []
        def iterator = new BreadthFirstIterator<>(graph, root)
        while (iterator.hasNext()) {
            vertexes.add iterator.next()
        }

        then:
        graph.vertexSize == vertexes.size()
        graph.allVertices == vertexes as Set
        root == vertexes.first()
        second.contains vertexes[1]
        last.contains vertexes.last()

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
