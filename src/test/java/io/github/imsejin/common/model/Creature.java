package io.github.imsejin.common.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class Creature implements Serializable {

    private String name;
    private Sex sex;
    private Double height;
    private Double weight;

    public Creature(String name, Sex sex, Double height, Double weight) {
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
    }

}
