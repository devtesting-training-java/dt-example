package io.xp.kata.di;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Limits {
    @Value("${limits.from}")
    private String configFrom;
    @Value("${limits.to}")
    private String configTo;

    private int from = 1;
    private int to = 100;
    private boolean hasConfigError;

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean hasConfigError() {
        return hasConfigError;
    }

    public void init(String[] args) {
        readConfig();
        readArgs(args);
    }

    private void readArgs(String[] values) {
        try {
            if (values.length == 1) {
                to = Integer.parseInt(values[0]);
            }
            if (values.length >= 2) {
                from = Integer.parseInt(values[0]);
                to = Integer.parseInt(values[1]);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private void readConfig() {
        try {
            int from = Integer.parseInt(configFrom);
            int to = Integer.parseInt(configTo);
            this.from = from;
            this.to = to;
            hasConfigError = false;
        } catch (NumberFormatException e) {
            hasConfigError = true;
        }
    }
}
