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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class AbstractGraph<E> implements Graph<E> {

    @EqualsAndHashCode.Include
    protected final Map<E, Set<E>> adjacentVertexMap;

    protected final Set<Edge<E>> edges;

    protected AbstractGraph() {
        this.adjacentVertexMap = new HashMap<>();
        this.edges = new HashSet<>();
    }

    protected AbstractGraph(Graph<E> graph) {
        this();
        addAll(graph);
    }

    @Override
    public boolean addVertex(E e) {
        if (e == null || this.adjacentVertexMap.containsKey(e)) {
            return false;
        }

        this.adjacentVertexMap.put(e, new HashSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(E e) {
        if (e == null || !this.adjacentVertexMap.containsKey(e)) {
            return false;
        }

        this.adjacentVertexMap.remove(e);
        for (Set<E> them : this.adjacentVertexMap.values()) {
            them.remove(e);
        }
        this.edges.removeIf(edge -> edge.vertex1.equals(e) || edge.vertex2.equals(e));

        return true;
    }

    @Override
    public boolean addAll(Graph<E> graph) {
        Set<E> vertices = graph.getAllVertices();

        // Graph don't need to add the empty one.
        if (vertices.isEmpty()) {
            return false;
        }

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

    // -------------------------------------------------------------------------------------------------

    /**
     * Edge for graph.
     *
     * @param <E> element
     */
    protected record Edge<E>(E vertex1, E vertex2) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Edge<?> that)) {
                return false;
            }

            // Considers as the same even if the vertex order of edge is reversed.
            return (this.vertex1.equals(that.vertex1) && this.vertex2.equals(that.vertex2)) ||
                    (this.vertex1.equals(that.vertex2) && this.vertex2.equals(that.vertex1));
        }

        @Override
        public int hashCode() {
            // Considers as the same even if the vertex order of edge is reversed.
            return Objects.hash(this.vertex1.hashCode() + this.vertex2.hashCode());
        }

    }

}
