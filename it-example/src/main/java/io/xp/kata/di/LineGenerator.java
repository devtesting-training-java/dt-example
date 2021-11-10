package io.xp.kata.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineGenerator {
    @Autowired
    private Limits fromTo;

    @Autowired
    private FizzBuzz fizzBuzz;

    public List<String> generate() {
        List<String> lines = new ArrayList<String>();
        int from = fromTo.getFrom();
        int to = fromTo.getTo();
        for (int i = from; i <= to; i++) {
            lines.add(fizzBuzz.transform(i));
        }
        return lines;
    }
}
