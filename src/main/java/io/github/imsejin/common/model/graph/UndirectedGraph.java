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

import java.util.*;
import java.util.Map.Entry;

public class UndirectedGraph<E> implements Graph<E> {

    private final Map<E, Set<E>> adjacentVertexMap;

    private final Set<Edge<E>> edges;

    public UndirectedGraph() {
        this.adjacentVertexMap = new HashMap<>();
        this.edges = new HashSet<>();
    }

    public UndirectedGraph(Graph<E> graph) {
        this();
        addAll(graph);
    }

    @Override
    public boolean addVertex(E e) {
        if (e == null) return false;

        Set<E> vertices = this.adjacentVertexMap.get(e);
        if (vertices != null) return false;

        this.adjacentVertexMap.put(e, new HashSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(E e) {
        Set<E> vertices = this.adjacentVertexMap.get(e);
        if (vertices == null) return false;

        this.adjacentVertexMap.remove(e);
        for (Set<E> them : this.adjacentVertexMap.values()) {
            them.remove(e);
        }

        this.edges.removeIf(edge -> edge.vertex1 == e || edge.vertex2 == e);

        return true;
    }

    @Override
    public boolean addEdge(E e1, E e2) {
        if (e1 == null || e2 == null || e1.equals(e2)) return false;

        Set<E> v1 = this.adjacentVertexMap.get(e1);
        Set<E> v2 = this.adjacentVertexMap.get(e2);

        if (v1 == null || v2 == null) return false;

        v1.add(e2);
        v2.add(e1);
        this.edges.add(new Edge<>(e1, e2));

        return true;
    }

    @Override
    public boolean removeEdge(E e1, E e2) {
        if (e1 == null || e2 == null || e1.equals(e2)) return false;

        Set<E> v1 = this.adjacentVertexMap.get(e1);
        Set<E> v2 = this.adjacentVertexMap.get(e2);

        if (v1 == null || v2 == null) return false;

        v1.remove(e2);
        v2.remove(e1);
        this.edges.remove(new Edge<>(e1, e2));

        return true;
    }

    @Override
    public boolean addAll(Graph<E> graph) {
        Set<E> vertices = graph.getAllVertices();

        // Graph don't need to add the empty one.
        if (vertices.isEmpty()) return false;

        for (E e : vertices) {
            Set<E> oldbie = this.adjacentVertexMap.get(e);
            Set<E> newbie = graph.getAdjacentVertices(e);

            if (oldbie == null) {
                // Adds new vertex and its edges.
                this.adjacentVertexMap.put(e, newbie);
            } else {
                // Adds new edges to the existing vertex.
                oldbie.addAll(newbie);
            }

            // Adds new edges.
            for (E it : newbie) {
                this.edges.add(new Edge<>(e, it));
            }
        }

        return true;
    }

    @Override
    public boolean containsVertex(E e) {
        return this.adjacentVertexMap.containsKey(e);
    }

    @Override
    public int getVertexSize() {
        return this.adjacentVertexMap.size();
    }

    @Override
    public int getPathLength() {
        return this.edges.size();
    }

    @Override
    public Set<E> getAllVertices() {
        return this.adjacentVertexMap.keySet();
    }

    @Override
    public Set<E> getAdjacentVertices(E e) {
        return this.adjacentVertexMap.get(e);
    }

    @Override
    public String toString() {
        Set<Entry<E, Set<E>>> entries = this.adjacentVertexMap.entrySet();

        StringBuilder sb = new StringBuilder("UndirectedGraph {");
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

    @Override
    public int hashCode() {
        return Objects.hash(adjacentVertexMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UndirectedGraph)) return false;

        UndirectedGraph<?> that = (UndirectedGraph<?>) o;
        return this.adjacentVertexMap.equals(that.adjacentVertexMap);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static class Edge<E> {
        private final E vertex1;
        private final E vertex2;

        public Edge(E vertex1, E vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;

            Edge<?> that = (Edge<?>) o;
            return this.vertex1.equals(that.vertex1) && this.vertex2.equals(that.vertex2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.vertex1, this.vertex2);
        }
    }

}
