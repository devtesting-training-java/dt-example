package io.xp.kata.di;

import io.xp.kata.outsideteamcontrol.FizzBuzz;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LineGeneratorTest {
    LineGenerator generator = new LineGenerator();

    @Test
    void testGenerate_should_feed_number_from_limits_range_to_fizzbuzz() {
        Limits limits = mock(Limits.class);
        String json = "{\n" +
                "  \"id\" : 1,\n" +
                "  \"name\" : \"name\",\n" +
                "  \"attributes\" : {\n" +
                "    \"request\" : {\n" +
                "      \"value\" : \"1\"\n" +
                "    },\n" +
                "    \"response\" : {\n" +
                "      \"original_value\" : \"1\",\n" +
                "      \"token\" : \"134567899ADFE\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\" : \"x\",\n" +
                "  \"generatedTime\" : 1636547866697,\n" +
                "  \"success\" : true\n" +
                "}";
        try (MockedStatic<Limits> limitsMockedStatic = mockStatic(Limits.class);
             MockedConstruction<FizzBuzz> fizzBuzzMocked = mockConstruction(FizzBuzz.class,
                     (mock, context) -> {
                         when(mock.transform(anyInt())).thenReturn(json);
                     })) {
            limitsMockedStatic.when(Limits::getInstance).thenReturn(limits);
            when(limits.getFrom()).thenReturn(1);
            when(limits.getTo()).thenReturn(15);

            List<String> lines = generator.generate();

            assertThat(lines).isEqualTo(Collections.nCopies(15,"x"));
        }
    }
}