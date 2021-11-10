package io.xp.kata.di;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${limits.from}")
    private
    String from;
    @Value("${limits.to}")
    private
    String to;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}