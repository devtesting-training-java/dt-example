package io.xp.kata.di;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.xp.kata.outsideteamcontrol.FizzBuzz;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GoodExampleTest {
    public static final String TEMPLATE_JSON = "FizzBuzzTemplate.json";
    LineGenerator generator = new LineGenerator();
    private MockedStatic<Limits> limitsMockedStatic;
    private MockedConstruction<FizzBuzz> fizzBuzzMocked;

    @AfterEach
    void tearDown() {
        if (fizzBuzzMocked != null) fizzBuzzMocked.close();
        if (limitsMockedStatic != null) limitsMockedStatic.close();
    }

    @Test
    void testGenerate_should_feed_number_from_limits_range_to_fizzbuzz() throws IOException {
        givenLimits(1, 15);
        mockFizzBuzz("1","2", "Fizz", "4", "Buzz",
                "Fizz", "7", "8", "Fizz", "Buzz",
                "11", "Fizz", "13", "14", "FizzBuzz");

        List<String> lines = generator.generate();

        assertThat(lines).containsSequence("1", "2", "Fizz", "4", "Buzz",
                "Fizz", "7", "8", "Fizz", "Buzz",
                "11", "Fizz", "13", "14", "FizzBuzz");
    }

    private void mockFizzBuzz(String result1, String... others) {
        String[] data = Arrays.stream(others).map(this::mockFizzBuzz).toArray(String[]::new);

        fizzBuzzMocked = mockConstruction(FizzBuzz.class,
                (mock, context) -> {
                    when(mock.transform(anyInt())).thenReturn(mockFizzBuzz(result1), data);
                });
    }

    private void givenLimits(int from, int to) {
        Limits limits = mock(Limits.class);
        limitsMockedStatic = mockStatic(Limits.class);
        limitsMockedStatic.when(Limits::getInstance).thenReturn(limits);
        when(limits.getFrom()).thenReturn(from);
        when(limits.getTo()).thenReturn(to);
    }

    private String mockFizzBuzz(String result) {
        try {
            InputStream template = getClass().getResourceAsStream(TEMPLATE_JSON);
            JsonNode jsonNode = new ObjectMapper().reader().readTree(template);
            ObjectNode mutable = (ObjectNode) jsonNode;
            mutable.put("result", result);
            mutable.put("generatedTime", new Date().getTime());
            return jsonNode.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}