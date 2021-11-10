package io.xp.kata.di;

import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommandLineRunnerIntegrationTest {
    private ByteArrayOutputStream out;

    @MockBean
    FizzBuzz fizzBuzz;

    @Test
    void testCommandLineRunner(@Autowired CommandLineRunner runner) throws Exception {
        MockitoAnnotations.openMocks(this);
        when(fizzBuzz.transform(anyInt())).thenReturn("x");
        monitorOut();
        runner.run("14", "16");
        assertThat(out.toString()).containsSubsequence("x", "x", "x");
    }

    private void monitorOut() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }
}
