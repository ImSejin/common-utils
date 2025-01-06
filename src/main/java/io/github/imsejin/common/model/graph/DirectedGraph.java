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

package io.github.imsejin.common.model.graph;

import java.util.Map.Entry;
import java.util.Set;

public class DirectedGraph<E> extends AbstractGraph<E> {

    public DirectedGraph() {
        super();
    }

    public DirectedGraph(Graph<E> graph) {
        super(graph);
    }

    @Override
    public boolean addEdge(E e1, E e2) {
        if (e1 == null || e2 == null || e1.equals(e2)) {
            return false;
        }

        Set<E> v1 = super.adjacentVertexMap.get(e1);
        Set<E> v2 = super.adjacentVertexMap.get(e2);

        if (v1 == null || v2 == null) {
            return false;
        }

        Edge<E> edge = new Edge<>(e1, e2);
        if (super.edges.contains(edge)) {
            return false;
        }

        v1.add(e2);
        super.edges.add(edge);

        return true;
    }

    @Override
    public boolean removeEdge(E e1, E e2) {
        if (e1 == null || e2 == null || e1.equals(e2)) {
            return false;
        }

        Set<E> v1 = super.adjacentVertexMap.get(e1);
        Set<E> v2 = super.adjacentVertexMap.get(e2);

        if (v1 == null || v2 == null) {
            return false;
        }

        v1.remove(e2);
        super.edges.remove(new Edge<>(e1, e2));

        return true;
    }

    @Override
    public String toString() {
        Set<Entry<E, Set<E>>> entries = super.adjacentVertexMap.entrySet();

        StringBuilder sb = new StringBuilder("DirectedGraph {");
        if (entries.isEmpty()) {
            return sb.append("}").toString();
        }
        sb.append('\n');

        int i = 0;
        for (Entry<E, Set<E>> entry : entries) {
            sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue());

            if (i == entries.size() - 1) {
                sb.append("\n");
            } else {
                sb.append(",\n");
            }

            i++;
        }

        return sb.append('}').toString();
    }

}
