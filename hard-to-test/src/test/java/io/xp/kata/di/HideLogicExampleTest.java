package io.xp.kata.di;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xp.kata.outsideteamcontrol.FizzBuzz;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HideLogicExampleTest {
    LineGenerator generator = new LineGenerator();
    private MockedStatic<Limits> limitsMockedStatic;
    private MockedConstruction<FizzBuzz> fizzBuzzMocked;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        fizzBuzzMocked.close();
        limitsMockedStatic.close();
    }

    @Test
    void testGenerate_should_feed_number_from_limits_range_to_fizzbuzz() throws IOException {
        mockData();

        List<String> lines = generator.generate();

        assertThat(lines).containsSequence("1", "2", "Fizz", "4", "Buzz",
                "Fizz", "7", "8", "Fizz", "Buzz",
                "11", "Fizz", "13", "14", "FizzBuzz");
    }

    private void mockData() throws IOException {
        String[] mockData = readMockData("MockFizzBuzzData.json");
        limitsMockedStatic = mockStatic(Limits.class);
        fizzBuzzMocked = mockConstruction(FizzBuzz.class,
                (mock, context) -> {
                    when(mock.transform(anyInt())).thenReturn(mockData[0], subArray(mockData, 1));
                });


        Limits limits = mock(Limits.class);
        limitsMockedStatic.when(Limits::getInstance).thenReturn(limits);
        when(limits.getFrom()).thenReturn(1);
        when(limits.getTo()).thenReturn(15);
    }

    private String[] subArray(String[] mockData, int n) {
        return Arrays.stream(mockData).skip(n).toArray(String[]::new);
    }

    private String[] readMockData(String name) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.reader().readTree(getClass().getResourceAsStream(name));
        String[] jsonStrs = new String[jsonNode.size()];
        for (int i = 0; i < jsonNode.size(); i++) {
            jsonStrs[i] = jsonNode.get(i).toString();
        }
        return jsonStrs;
    }
}