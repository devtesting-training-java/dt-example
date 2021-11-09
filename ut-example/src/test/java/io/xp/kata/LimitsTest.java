package io.xp.kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LimitsTest {

    public static Stream<Arguments> argsConfigAndExpectFromTo() {
        return Stream.of(
                Arguments.arguments(new String[]{}, config("1", "100"), 1, 100),
                Arguments.arguments(new String[]{}, config("1", "10"), 1, 10),
                Arguments.arguments(new String[]{"3"}, config("1", "100"), 1, 3),
                Arguments.arguments(new String[]{"2", "5"}, config("3", "10"), 2, 5),
                Arguments.arguments(new String[]{"-2", "5"}, config("3", "10"), -2, 5)
        );
    }

    private static Properties config(String from, String to) {
        Properties properties = new Properties();
        if (from != null) {
            properties.setProperty("from", from);
        }
        if (to != null) {
            properties.setProperty("to", to);
        }
        return properties;
    }

    @ParameterizedTest
    @MethodSource("argsConfigAndExpectFromTo")
    void testConstructor(String[] args, Properties config, int from, int to) {
        Limits limits = new Limits(config, args);
        assertEquals(limits.getFrom(), from);
        assertEquals(limits.getTo(), to);
        assertFalse(limits.hasConfigError());
    }

    public static Stream<Arguments> illegalArgs() {
        return Stream.of(
                Arguments.arguments((Object) new String[]{"a"}),
                Arguments.arguments((Object) new String[]{"1", "1.1"})
        );
    }

    @ParameterizedTest
    @MethodSource("illegalArgs")
    void testConstructor_should_throw_argument_exception_when_illegal_args(String[] args) {
        assertThrows(IllegalArgumentException.class,
                () -> new Limits(config("1", "10"), args));
    }

    public static Stream<Arguments> illegalConfig() {
        return Stream.of(
                Arguments.arguments(config("1.2", "3")),
                Arguments.arguments(config("1", "0.2")),
                Arguments.arguments(config(null, "2")),
                Arguments.arguments(config("2", null))
        );
    }

    @ParameterizedTest
    @MethodSource("illegalConfig")
    void testConstructor_should_set_warning_format_when_illegal_config(Properties config) {
        Limits limits = new Limits(config, new String[]{});
        assertTrue(limits.hasConfigError());
    }

    public static Stream<Arguments> argsExpectWithIllegalConfig() {
        return Stream.of(
                Arguments.arguments(new String[]{}, 1, 100),
                Arguments.arguments(new String[]{"3"}, 1, 3),
                Arguments.arguments(new String[]{"4", "7"}, 4, 7)
        );
    }

    @ParameterizedTest
    @MethodSource("argsExpectWithIllegalConfig")
    void testConstructor_should_task_default_config_1_100_when_illegal_config(String[] args, int from, int to) {
        Limits limits = new Limits(config("1.1", "2"), args);
        assertEquals(limits.getFrom(), from);
        assertEquals(limits.getTo(), to);
    }
}