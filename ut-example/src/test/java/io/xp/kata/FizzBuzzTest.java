package io.xp.kata;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzTest {
    FizzBuzz fizzBuzz = new FizzBuzz();

    @ParameterizedTest
    @CsvSource({
        "1, 1", "3, Fizz", "5, Buzz", "30, FizzBuzz"
    })
    void test_transform(int number, String expect) {
       assertEquals(expect, fizzBuzz.transform(number));
    }
}