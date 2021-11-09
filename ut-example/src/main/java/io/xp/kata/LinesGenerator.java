package io.xp.kata;

import java.util.ArrayList;
import java.util.List;

public class LinesGenerator {
    private Limits fromTo;

    public LinesGenerator(Limits fromTo) {
        this.fromTo = fromTo;
    }

    public List<String> generate() {
        List<String> lines = new ArrayList<String>();
        int from = fromTo.getFrom();
        int to = fromTo.getTo();
        FizzBuzz fizzBuzz = new FizzBuzz();
        for (int i = from; i <= to; i++) {
            lines.add(fizzBuzz.transform(i));
        }
        return lines;
    }
}