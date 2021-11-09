package io.xp.kata;

import java.util.Properties;

class Limits {
    private int from = 1;
    private int to = 100;
    private boolean hasConfigError;

    public Limits(Properties config, String[] args) throws IllegalArgumentException {
        readConfig(config);
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

    private void readConfig(Properties config) {
        try {
            from = Integer.parseInt(config.getProperty("from"));
            to = Integer.parseInt(config.getProperty("to"));
            hasConfigError = false;
        } catch (NumberFormatException e) {
            hasConfigError = true;
        }
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean hasConfigError() {
        return hasConfigError;
    }
}
