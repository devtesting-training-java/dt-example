package io.xp.kata.example;

import org.hamcrest.CustomTypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestDemoTest {
    @Test
    void string_assertions() {
        assertThat("", allOf(
                is(""),
                not(containsString("a")),
                emptyString()
        ));
        assertThat("Mary has a little lamb", allOf(
                containsString("has a"),
                stringContainsInOrder("Mary", "has", "lamb"),
                startsWith("M"),
                hasLength(22)

        ));
    }

    @Test
    void number_assertions() {
        assertThat(7, allOf(
                lessThan(9),
                greaterThan(5)
        ));
    }

    @Test
    void collection_assertions() {
        List<String> strings = Arrays.asList("A1", "A3", "A5", "A7", "A9");
        assertThat(strings, hasSize(5));
        assertThat(strings, everyItem(
                allOf(startsWith("A"), hasLength(2))
        ));
        assertThat(strings, allOf(
                hasItem(endsWith("5")),
                hasItems("A1", "A5", "A3"),
                not(hasItem(anyOf(is("A2"), is("B")))),
                equalTo(Arrays.asList("A1", "A3", "A5", "A7", "A9"))
        ));
    }

    public static class HardToComparePojo {
        private int id;
        private String name;
        private boolean active;
        private Map<String, Object> attributes;

        public HardToComparePojo(int id, String name, boolean active, Map<String, Object> attributes) {
            this.id = id;
            this.name = name;
            this.active = active;
            this.attributes = attributes;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }
    }

    @Test
    void complex_collection_assertions() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("attr1", 11);
        attrs.put("attr2", LocalDate.of(2021, 1, 1));

        HashSet<Object> set = new HashSet<>(Arrays.asList(1, "hashset",
                new HardToComparePojo(1, "a", true, null),
                new HardToComparePojo(2, "a1", false, Collections.emptyMap()),
                new HardToComparePojo(3, "b", true, attrs),
                LocalDate.of(2021, 11, 9), 3L, 4d));

        Set<Object> filtered = set.stream().filter(o -> o instanceof HardToComparePojo).collect(Collectors.toSet());
        assertThat(filtered, hasItems(
                hasProperty("id", is(1)),
                hasProperty("attributes", hasValue(hasProperty("monthValue")))
        ));
    }

    @Test
    void custom_matcher() {
        CustomTypeSafeMatcher<Integer> odd = new CustomTypeSafeMatcher<Integer>("odd number") {
            @Override
            protected boolean matchesSafely(Integer integer) {
                return integer % 2 == 1;
            }
        };
        assertThat(5, is(odd));
    }
}
