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

package io.github.imsejin.common.model.graph.traverse;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.model.graph.Graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

public class BreadthFirstIterator<E> implements Iterator<E> {

    private final Set<E> visited = new HashSet<>();

    // It has high performance than LinkedList?
    private final Queue<E> queue = new ArrayDeque<>();

    private final Graph<E> graph;

    public BreadthFirstIterator(Graph<E> graph, E root) {
        Asserts.that(graph)
                .describedAs("BreadthFirstIterator.graph is not allowed to be null")
                .isNotNull();
        Asserts.that(root)
                .describedAs("BreadthFirstIterator.root is not allowed to be null")
                .isNotNull()
                .describedAs("BreadthFirstIterator.root must be in graph as a vertex: '{0}'", root)
                .is(graph::containsVertex);

        this.graph = graph;
        this.queue.offer(root);
    }

    @Override
    public boolean hasNext() {
        return !this.queue.isEmpty();
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException("BreadthFirstIterator has no more elements");

        E vertex = this.queue.poll();

        this.visited.add(vertex);
        for (E v : this.graph.getAdjacentVertices(vertex)) {
            if (this.visited.contains(v)) continue;
            this.queue.offer(v);
            this.visited.add(v);
        }

        return vertex;
    }

    public static <E> void traverse(Graph<E> graph, E root, Consumer<E> consumer) {
        Asserts.that(graph)
                .describedAs("BreadthFirstIterator.graph is not allowed to be null")
                .isNotNull();
        Asserts.that(root)
                .describedAs("BreadthFirstIterator.root is not allowed to be null")
                .isNotNull()
                .describedAs("BreadthFirstIterator.root must be in graph as a vertex: '{0}'", root)
                .is(graph::containsVertex);

        Set<E> visited = new LinkedHashSet<>();
        Queue<E> queue = new ArrayDeque<>();

        queue.add(root);
        while (!queue.isEmpty()) {
            E vertex = queue.poll();
            if (visited.contains(vertex)) continue;

            consumer.accept(vertex);

            visited.add(vertex);
            // queue.addAll(graph.getAdjacentVertices(vertex));
            for (E v : graph.getAdjacentVertices(vertex)) {
                if (!visited.contains(v)) queue.offer(v);
            }
        }
    }

}
