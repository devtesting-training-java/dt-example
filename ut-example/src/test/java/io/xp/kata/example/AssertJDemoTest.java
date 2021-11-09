package io.xp.kata.example;

import org.assertj.core.api.Condition;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatObject;

public class AssertJDemoTest {
    @Test
    void string_assertions() {
        assertThat("")
                .isEqualTo("")
                .doesNotContain("a")
                .isEmpty();
        assertThat("Mary has a little lamb")
                .contains("lamb", "Mary")
                .containsSequence("Mary", " ", "has")
                .startsWith("M")
                .hasSize(22);
    }

    @Test
    void number_assertions() {
        assertThat(7)
                .isLessThan(9)
                .isBetween(5,8);
    }

    static class HardToComparePojo {
        int id;
        String name;
        boolean active;
        Map<String, Object> attributes;

        public HardToComparePojo(int id, String name, boolean active, Map<String, Object> attributes) {
            this.id = id;
            this.name = name;
            this.active = active;
            this.attributes = attributes;
        }
    }

    @Test
    void collection_assertions() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("attr1", 11);
        attrs.put("attr2", LocalDate.of(2021, 1, 1));

        HashSet<Object> set = new HashSet<>(Arrays.asList(1, "hashset",
                new HardToComparePojo(1, "a", true, null),
                new HardToComparePojo(2, "a1", false, Collections.emptyMap()),
                new HardToComparePojo(3, "b", true, attrs),
                LocalDate.of(2021, 11, 9), 3L, 4d));

        assertThat(set)
                .filteredOn(o -> o instanceof HardToComparePojo)
                .map(o -> (HardToComparePojo)o)
                .allSatisfy(pojo -> assertThat(pojo.name).isNotBlank())
                .anyMatch(pojo -> pojo.id == 1)
                .anySatisfy(pojo ->
                        assertThat(pojo.attributes).anySatisfy(
                                (k, v) -> assertThat(v)
                                        .isInstanceOf(LocalDate.class)
                                        .extracting("monthValue").isEqualTo(1)
                        )
                );

    }

    @Test
    void custom_condition() {
        Condition<? super Integer> odd = new Condition<>(i -> i%2==1, "be odd");
        assertThat(5).is(odd);
    }
}
