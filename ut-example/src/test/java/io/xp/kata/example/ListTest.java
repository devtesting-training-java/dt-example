package io.xp.kata.example;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListTest {

    private static List<String> staticList = new ArrayList<>();
    private List<String> classInitList = new ArrayList<>();
    private List<String> beforeInitList;

    @BeforeAll
    static void beforeAll() {
        staticList.add("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        staticList.add("afterAll");
        assertEquals(
            Arrays.asList("beforeAll", "setUp", "tearDown", "setUp", "tearDown", "afterAll"),
                staticList);
    }

    @BeforeEach
    void setUp() {
        staticList.add("setUp");
        beforeInitList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        staticList.add("tearDown");
    }

    @Test
    void assertJ_example() {
        assertEquals(Collections.emptyList(), classInitList);
        assertEquals(Collections.emptyList(), beforeInitList);

        classInitList.add("A1");
        classInitList.add("A3");
        classInitList.add("A5");
        classInitList.add("A7");
        classInitList.add("A9");

        beforeInitList.add("B");

        org.assertj.core.api.Assertions.assertThat(classInitList)
                .hasSize(5)
                .allMatch(s -> s.startsWith("A"), "starts with A")
                .as("ends with 5").anyMatch(s -> s.endsWith("5"))
                .as("length == 2").allSatisfy(s -> assertEquals(2, s.length()))
                .contains("A1", "A5", "A3")
                .doesNotContain("A2", "B")
                .containsOnly("A1", "A9", "A7", "A5", "A3")
                .containsExactly("A1", "A3", "A5", "A7", "A9");
    }

    @Test
    void test_field_life_cycle() {
        assertEquals(Collections.emptyList(), classInitList);
        assertEquals(Collections.emptyList(), beforeInitList);

        classInitList.add("a");
        beforeInitList.add("b0");
        beforeInitList.add("b2");
        beforeInitList.add("b4");
        beforeInitList.add("b6");
        beforeInitList.add("b8");

    }
}
