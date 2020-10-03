package io.github.imsejin.common.model;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Human extends Creature {

    private String job;
    private String hometown;

    public Human(String name, Sex sex, Double height, Double weight, String job, String hometown) {
        super(name, sex, height, weight);
        this.job = job;
        this.hometown = hometown;
    }

}
