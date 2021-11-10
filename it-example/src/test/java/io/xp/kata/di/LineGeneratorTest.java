package io.xp.kata.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class LineGeneratorTest {
    @Mock
    private FizzBuzz fizzBuzz;

    @Mock
    private Limits limits;

    @InjectMocks
    private LineGenerator generator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("verify example")
    @Test
    void testGenerate_should_feed_number_from_limits_range_to_fizzbuzz() {
        when(limits.getFrom()).thenReturn(3);
        when(limits.getTo()).thenReturn(8);
        when(fizzBuzz.transform(anyInt())).thenReturn("?");

        List<String> lines = generator.generate();

        assertEquals(Arrays.asList("?","?","?","?","?","?"), lines);
        verify(fizzBuzz).transform(7);
        verify(fizzBuzz, never()).transform(1);
        verify(fizzBuzz, times(6)).transform(anyInt());
        verify(fizzBuzz, times(2)).transform(intThat(lessThan(5)));
    }

    private ArgumentMatcher<Integer> lessThan(final int num) {
        return integer -> integer < num;
    }

    @Test
    void bad_mocking_implementation_example() {
        when(limits.getFrom()).thenReturn(3);
        when(limits.getTo()).thenReturn(8);
        when(fizzBuzz.transform(anyInt())).then((Answer<String>) invocation -> {
            int i = invocation.getArgument(0);
            if (i % 3 == 0) {
                return "Fizz";
            }
            if (i % 5 == 0) {
                return "Buzz";
            }
            return String.valueOf(i);
        });

        List<String> lines = generator.generate();

        assertEquals(Arrays.asList("Fizz", "4", "Buzz", "Fizz", "7", "8"), lines);
    }

}