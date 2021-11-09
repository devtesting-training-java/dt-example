package io.xp.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class LinesGeneratorTest {
    @Mock
    Limits limits;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void mockLimits(int from, int to) {
        when(limits.getFrom()).thenReturn(from);
        when(limits.getTo()).thenReturn(to);
    }

    @Test
    void testGenerate_lines_as_limits() {
        mockLimits(1, 15);
        LinesGenerator generator = new LinesGenerator(limits);
        assertThat(generator.generate()).hasSize(15)
                .containsExactly("1", "2", "Fizz", "4", "Buzz",
                        "Fizz", "7", "8", "Fizz", "Buzz",
                        "11", "Fizz", "13", "14", "FizzBuzz");
    }
}