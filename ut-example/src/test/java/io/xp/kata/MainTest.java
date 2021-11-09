package io.xp.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    private ByteArrayOutputStream out;
    private ByteArrayOutputStream err;

    @BeforeEach
    void setUp() throws FileNotFoundException, URISyntaxException {
        monitorOut();
        monitorErr();
        givenConfig("from=1", "to=100");
    }

    @Test
    void testMain_should_print_1_to_100() {
        Main.main(new String[]{});
        assertThat(outputLines()).hasSize(100)
                .startsWith("1", "2", "Fizz", "4", "Buzz")
                .containsSequence("14", "FizzBuzz", "16")
                .endsWith("98","Fizz","Buzz");
    }

    @Test
    void testMain_with_different_config() throws FileNotFoundException, URISyntaxException {
        givenConfig("from=5", "to=6");

        Main.main(new String[]{});
        assertThat(outputLines()).hasSize(2).containsExactly("Buzz", "Fizz");
    }

    @Test
    void testMain_should_accept_to_limits() {
        Main.main(new String[]{"4"});
        assertThat(outputLines()).containsExactly("1", "2", "Fizz", "4");
    }

    @Test
    void testMain_should_accept_to_and_from_limit() {
        Main.main(new String[]{"4", "8"});
        assertThat(outputLines()).containsExactly("4", "Buzz", "Fizz", "7", "8");
    }

    @Test
    void testMain_should_print_err_when_illegal_args() {
        Main.main(new String[]{"4, 8"});
        assertThat(err.toString())
                .contains("Args format error")
                .contains("usage: main [to number]. main [form number] [to number]");
        assertThat(outputLines()).containsExactly("");
    }

    @Test
    void testMain_should_print_warning_when_illegal_config() throws FileNotFoundException, URISyntaxException {
        givenConfig("from=5.1", "to=6");
        Main.main(new String[]{});

        assertThat(err.toString()).isEmpty();
        assertThat(outputLines())
                .startsWith("Cannot read default limits from config file, uses limits 1 to 100")
                .endsWith("98","Fizz","Buzz");
    }

    private void monitorOut() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    private void monitorErr() {
        err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));
    }

    private void givenConfig(String... items) throws URISyntaxException, FileNotFoundException {
        File file = new File(this.getClass().getResource("/config.properties").toURI());
        PrintStream printStream = new PrintStream(file);
        for (String item : items) {
            printStream.println(item);
        }
    }

    private List<String> outputLines() {
        String[] lines = out.toString().split(System.lineSeparator());
        return Arrays.asList(lines);
    }

}
