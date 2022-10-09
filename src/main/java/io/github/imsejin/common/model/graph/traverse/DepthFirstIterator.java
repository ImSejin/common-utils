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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

public class DepthFirstIterator<E> implements Iterator<E> {

    private final Set<E> visited = new HashSet<>();

//    private final Stack<E> stack = new Stack<>();

    private final Graph<E> graph;

    private final Stack<Iterator<E>> stack = new Stack<>();

    private E next;

    public DepthFirstIterator(Graph<E> graph, E root) {
        Asserts.that(graph)
                .describedAs("DepthFirstIterator.graph is not allowed to be null")
                .isNotNull();
        Asserts.that(root)
                .describedAs("DepthFirstIterator.root is not allowed to be null")
                .isNotNull()
                .describedAs("DepthFirstIterator.root must be in graph as a vertex: '{0}'", root)
                .is(graph::containsVertex);

        this.graph = graph;
//        this.stack.push(root);
        this.stack.push(graph.getAdjacentVertices(root).iterator());
        this.next = root;
    }

    @Override
    public boolean hasNext() {
        return !this.stack.isEmpty();
    }

//    @Override
//    public E next() {
//        if (!hasNext()) throw new NoSuchElementException("DepthFirstIterator has no more elements");
//
//        E vertex = this.stack.pop();
//        if (this.visited.contains(vertex)) return next();
//
//        this.visited.add(vertex);
//        for (E v : this.graph.getAdjacentVertexes(vertex)) {
//            // Pushes the adjacent vertexes that have never been visited.
//            // If not, will occur NoSuchElementException because of recursive logic.
//            if (!this.visited.contains(v)) this.stack.push(v);
//        }
//
//        return vertex;
//    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException("DepthFirstIterator has no more elements");

        try {
            this.visited.add(this.next);
            return this.next;
        } finally {
            this.advance();
        }
    }

    private void advance() {
        Iterator<E> neighbors = this.stack.peek();

        do {
            while (!neighbors.hasNext()) {  // No more nodes -> back out a level
                this.stack.pop();
                if (!hasNext()) { // All done!
                    this.next = null;
                    return;
                }

                neighbors = this.stack.peek();
            }

            this.next = neighbors.next();
        } while (this.visited.contains(this.next));

        this.stack.push(this.graph.getAdjacentVertices(this.next).iterator());
    }

    public static <E> void traverse(Graph<E> graph, E root, Consumer<E> consumer) {
        Asserts.that(graph)
                .describedAs("DepthFirstIterator.graph is not allowed to be null")
                .isNotNull();
        Asserts.that(root)
                .describedAs("DepthFirstIterator.root is not allowed to be null")
                .isNotNull()
                .describedAs("DepthFirstIterator.root must be in graph as a vertex: '{0}'", root)
                .is(graph::containsVertex);

        Set<E> visited = new LinkedHashSet<>();
        Stack<E> stack = new Stack<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            E vertex = stack.pop();
            if (visited.contains(vertex)) continue;

            consumer.accept(vertex);

            visited.add(vertex);
            for (E v : graph.getAdjacentVertices(vertex)) {
                stack.push(v);
            }
        }
    }

}
