package io.github.imsejin.common.internal.assertion.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Foo {

    @EqualsAndHashCode.Include
    private final String value;

    public Foo() {
        this.value = null;
    }

}
