package io.xp.kata;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            Properties config = new ConfigLoader().loadConfig("/config.properties");
            Limits fromTo = new Limits(config, args);
            if (fromTo.hasConfigError()) {
                printConfigWarning();
            }
            LinesGenerator linesGenerator = new LinesGenerator(fromTo);
            for (String line : linesGenerator.generate()) {
                System.out.println(line);
            }
        } catch (IllegalArgumentException e) {
            printArgsErrorMessage();
        }
    }

    private static void printConfigWarning() {
        System.out.println("Cannot read default limits from config file, uses limits 1 to 100");
    }

    private static void printArgsErrorMessage() {
        System.err.println("Args format error");
        System.err.println("usage: main [to number]. main [form number] [to number]");
    }

}
