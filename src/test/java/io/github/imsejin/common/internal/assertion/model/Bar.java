package io.github.imsejin.common.internal.assertion.model;

import java.time.Instant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public final class Bar extends Foo {

    private final Instant createdTime;

    public Bar() {
        this.createdTime = Instant.now();
    }

    public Bar(String value) {
        super(value);
        this.createdTime = Instant.now();
    }

}
