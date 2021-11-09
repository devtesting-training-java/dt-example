package io.xp.kata.example;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LifeCycleDemoTest {

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

        classInitList.add("A");
        beforeInitList.add("B");
    }

    @Test
    void test_field_life_cycle() {
        assertEquals(Collections.emptyList(), classInitList);
        assertEquals(Collections.emptyList(), beforeInitList);

        classInitList.add("a");
        beforeInitList.add("b");
    }
}
