package io.github.imsejin.common.internal.assertion.model;

import java.nio.file.AccessMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Qux {

    @Getter
    @Setter
    private static AccessMode mode;

    @EqualsAndHashCode.Include
    private Integer id;

    @EqualsAndHashCode.Include
    private String name;

}
