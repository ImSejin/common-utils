package io.github.imsejin.common.util;

import io.github.imsejin.common.model.Creature;
import io.github.imsejin.common.model.Human;
import io.github.imsejin.common.model.Sex;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ObjectUtilsTest {

    @Test
    void cloneDeep() {
        // given
        Human human = new Human("imsejin", Sex.MALE, 123.12, 99.32, "developer", "Seoul");
        // when
        Human clone1 = ObjectUtils.cloneDeep(human, Human.class);
        human.setSex(Sex.FEMALE);
        human.setHometown("California");
        // then
        System.out.println("human: " + human + " / " + human.hashCode());
        System.out.println("clone: " + clone1 + " / " + clone1.hashCode());
        assertThat(human).isNotSameAs(clone1);
        assertThat(human.getSex()).isNotEqualTo(clone1.getSex());
        assertThat(human.getHometown()).isNotEqualTo(clone1.getHometown());

        // given
        Creature creature = new Creature("imsejin", Sex.MALE, 123.12, 99.32) {};
        // when
        Creature clone2 = ObjectUtils.cloneDeep(creature, Creature.class);
        creature.setSex(Sex.FEMALE);
        // then
        System.out.println("creature: " + creature + " / " + creature.hashCode());
        System.out.println("clone: " + clone2 + " / " + clone2.hashCode());
        assertThat(creature).isNotSameAs(clone2);
        assertThat(creature.getSex()).isNotEqualTo(clone2.getSex());
    }

}
