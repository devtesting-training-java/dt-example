package io.xp.kata.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineGenerator {
    @Autowired
    private Limits fromTo;

    public List<String> generate() {
        List<String> lines = new ArrayList<String>();
        FizzBuzz fizzBuzz = new FizzBuzz();
        for (int i = fromTo.getFrom(); i <= fromTo.getTo(); i++) {
            lines.add(fizzBuzz.transform(i));
        }
        return lines;
    }
}
