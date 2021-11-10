package io.xp.kata.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    @Autowired Limits fromTo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(LineGenerator generator) {
        return args -> {
            try {
                fromTo.init(args);
                if (fromTo.hasConfigError()) {
                    printConfigWarning();
                }
                for (String line : generator.generate()) {
                    System.out.println(line);
                }
            } catch (IllegalArgumentException e) {
                printArgsErrorMessage();
            }
        };
    }

    private static void printConfigWarning() {
        System.out.println("Cannot read default limits from config file, uses limits 1 to 100");
    }

    private static void printArgsErrorMessage() {
        System.err.println("Args format error");
        System.err.println("usage: main [to number]. main [form number] [to number]");
    }
}
