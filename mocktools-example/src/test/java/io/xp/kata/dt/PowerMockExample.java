package io.xp.kata.dt;

import io.xp.kata.di.Limits;
import io.xp.kata.di.LineGenerator;
import io.xp.kata.outsideteamcontrol.FizzBuzz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareEverythingForTest;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
public class PowerMockExample {
    @Spy
    LineGenerator generator = new LineGenerator();
    @Mock
    private Limits limits;
    @Mock
    private FizzBuzz fizzbuzz;

    @Test
//    @PrepareEverythingForTest
    @PrepareForTest({Limits.class, LineGenerator.class, FizzBuzz.class})
    public void testGenerate_should_feed_number_from_limits_range_to_fizzbuzz() throws Exception {
        mockStatic(Limits.class);
        when(Limits.getInstance()).thenReturn(limits);
        when(limits.getFrom()).thenReturn(13);
        when(limits.getTo()).thenReturn(17);

        whenNew(FizzBuzz.class).withAnyArguments().thenReturn(fizzbuzz);
        when(fizzbuzz.transform(anyInt())).thenReturn("{\"result\": \"*\"}");

        doReturn(true).when(generator, "checkDto", any());

        List<String> lines = generator.generate();

        assertEquals(Collections.nCopies(5, "*"), lines);
    }

}
